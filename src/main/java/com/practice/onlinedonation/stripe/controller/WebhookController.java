package com.practice.onlinedonation.stripe.controller;

import com.practice.onlinedonation.Model.Donation;
import com.practice.onlinedonation.Model.Payment;
import com.practice.onlinedonation.Service.DonationService;
import com.practice.onlinedonation.Service.MailService;
import com.practice.onlinedonation.Service.PaymentService;
import com.practice.onlinedonation.invoice.payload.DonationInvoiceData;
import com.practice.onlinedonation.invoice.service.DonationInvoiceDataService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.net.Webhook;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/stripe-webhooks")
public class WebhookController {

    @Value("${stripe.api.webhook.key}")
    private String webhookSecret;

    @Autowired
    private DonationService donationService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private DonationInvoiceDataService donationInvoiceDataService;
    @Autowired
    private MailService mailService;


    @PostMapping
    public ResponseEntity<String> handleWebhookEvents(
            @RequestBody String payload,
            @RequestHeader("stripe-signature") String sigHeader
    ) throws StripeException, MessagingException {
        Event event;
        StripeObject stripeObject = null;


        try{
            event = Webhook.constructEvent(
                    payload,
                    sigHeader,
                    webhookSecret
            );

            EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();

            if(dataObjectDeserializer != null){
                stripeObject = dataObjectDeserializer.getObject().orElse(null);
                            }

        } catch (SignatureVerificationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid signature");
        }

        log.info("Listening to webhook");

        switch (event.getType()){
            case "payment_intent.succeeded":

                if(stripeObject instanceof PaymentIntent){
                    handleSucceedPayment((PaymentIntent) stripeObject);
                }
                break;
            case "payment_intent.payment_failed":
                if(stripeObject instanceof PaymentIntent)
                    handleFailedPayment( (PaymentIntent)stripeObject);
                break;

        }
        return new ResponseEntity<>("Received", HttpStatus.OK);
    }

    private void handleFailedPayment(PaymentIntent pi) {
        Map<String, String> metadata = pi.getMetadata();
        StripeError se = pi.getLastPaymentError();
        donationService.updateStatusOfPayment(Long.parseLong(metadata.get("DonationId")), se.getCode());
        //log.info(se.toString());
        log.info("Listening to Payment failed");
    }

    private void handleSucceedPayment(PaymentIntent pi) throws StripeException, MessagingException {

        //log.info("Handling success payment");
        Map<String, String> metadata = pi.getMetadata();
        //Update the donation status
        donationService.updateStatusOfPayment(Long.parseLong(metadata.get("DonationId")), pi.getStatus());
        //Get the updated donation
        Donation donation = donationService.findDonationById(metadata.get("DonationId"));
        //Save the payment
        Payment savedPayment = savePayment(pi,donation);
        //save invoice
        DonationInvoiceData invoice = saveInvoice(savedPayment,donation);

        //Send email notification
        mailService.sendDonationEmail(invoice.getEmail(),invoice.getName(),invoice.getInvoiceId());


    }

    public Payment savePayment(PaymentIntent pi, Donation donation ) throws StripeException {


        String random = UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 6);

        Charge charge = Charge.retrieve(pi.getLatestCharge());
        Instant paymentTime = Instant.ofEpochSecond(charge.getCreated());
        LocalDateTime localPaymentTime = LocalDateTime.ofInstant(paymentTime, ZoneId.of("Asia/Kolkata"));
        Payment payment = paymentService.savePayment(
                Payment.builder()
                        .email(charge.getBillingDetails().getEmail())
                        .paymentDate(localPaymentTime)

                        .paymentMethod(PaymentMethod.retrieve(pi.getPaymentMethod()).getCard().getBrand())
                        .amount(Math.toIntExact(pi.getAmount()))
                        .currency(pi.getCurrency())
                        .stripePaymentIntentId(pi.getId())
                        .stripeChargeId(pi.getLatestCharge())
                        .status(pi.getStatus())
                        .stripeUserId(pi.getCustomer())
                        .donation(donation)
                        .invoiceNumber(donation.getDonationId() + random )
                        .build()
        );
        return paymentService.savePayment(payment);

    }

    public DonationInvoiceData saveInvoice(Payment payment, Donation donation) throws StripeException {
        DonationInvoiceData data = DonationInvoiceData.builder()
                .name(donation.getUser().getFirstName() + " " + donation.getUser().getLastName())
                .paymentDate(payment.getPaymentDate())
                .amount(payment.getAmount()/100)
                .organization(donation.getOrganization().getOrganizationName())
                .currency(payment.getCurrency())
                .invoiceNumber(payment.getInvoiceNumber())
                .email(donation.getUser().getEmail())
                .paymentMethod(payment.getPaymentMethod())
                .status(payment.getStatus())
                .build();
       return donationInvoiceDataService.save(data);

    }




}
