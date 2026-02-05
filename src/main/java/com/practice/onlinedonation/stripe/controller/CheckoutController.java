package com.practice.onlinedonation.stripe.controller;

import com.practice.onlinedonation.Model.Donation;
import com.practice.onlinedonation.Model.User;
import com.practice.onlinedonation.Service.DonationService;
import com.practice.onlinedonation.payload.DonationDTO;
import com.practice.onlinedonation.stripe.payload.DonationStripePayload;
import com.practice.onlinedonation.stripe.payload.PaymentReqeuestDTO;
import com.practice.onlinedonation.stripe.util.CustomerUtil;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.checkout.Session;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class CheckoutController {
    @Autowired
    DonationService donationService;


    @PostMapping("/api/payment/checkout")
    public ResponseEntity<Map<String, String>> checkout(@RequestBody PaymentReqeuestDTO paymentReqeuestDTO) throws StripeException {

        //Create a customer in stripe or get the customer from stripe associated with email

        Customer customer = CustomerUtil.findOrCreateCustomer(paymentReqeuestDTO.getUser().getEmail(), paymentReqeuestDTO.getUser().getUsername());

        Map<String, String> mp = new HashMap<>();

        //Create a donation object and attach its id to paymentIntentData
        Donation d = crreateDonation(paymentReqeuestDTO);

        mp.put("DonationId", "" + d.getDonationId());

        //Create a checkout Session by adding details of the checkout

        SessionCreateParams sessionBuilder = SessionCreateParams.builder().setMode(SessionCreateParams.Mode.PAYMENT).setCustomer(customer.getId())
                .setSuccessUrl("http://localhost:8080/api/payment/success").setCancelUrl("http://localhost:8080/api/payment/cancel").setPaymentIntentData(SessionCreateParams.PaymentIntentData.builder().putAllMetadata(mp).build())


                .addLineItem(SessionCreateParams.LineItem.builder()

                        .setQuantity(1L).setPriceData(SessionCreateParams.LineItem.PriceData.builder().setCurrency("usd").setUnitAmount(paymentReqeuestDTO.getDonationAmount() * 100).setProductData(
                                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                        .setName("Donation")
                                        .build()
                                ).build()

                        ).

                        build())

                .build();

        Session session = Session.create(sessionBuilder);



        Map<String, String> response = new HashMap<>();
        response.put("sessionId", session.getId());
        response.put("Payment Intent ID", session.getPaymentIntent());
        response.put("Client Secret", session.getClientSecret());

        return new ResponseEntity<>(response, HttpStatus.OK);


    }

    @GetMapping("/success")
    public String success() {
        return "Payment Successful!";
    }

    @GetMapping("/cancel")
    public String cancel() {
        return "Payment Cancelled!";
    }

    public Donation crreateDonation(PaymentReqeuestDTO prDTO) {
        return donationService.createDonation(prDTO);

    }
}
