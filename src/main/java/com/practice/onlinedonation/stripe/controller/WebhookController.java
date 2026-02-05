package com.practice.onlinedonation.stripe.controller;

import com.practice.onlinedonation.Service.DonationService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.*;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/stripe-webhooks")
public class WebhookController {

    @Value("${stripe.api.webhook.key}")
    private String webhookSecret;

    @Autowired
    private DonationService donationService;


    @PostMapping
    public ResponseEntity<String> handleWebhookEvents(
            @RequestBody String payload,
            @RequestHeader("stripe-signature") String sigHeader
    ){
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



        switch (event.getType()){
            case "payment_intent.succeeded":

                if(stripeObject instanceof PaymentIntent){
                    handleCheckoutSession((PaymentIntent) stripeObject);
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

    private void handleCheckoutSession(PaymentIntent pi) {

        //log.info("Handling success payment");
        Map<String, String> metadata = pi.getMetadata();
        donationService.updateStatusOfPayment(Long.parseLong(metadata.get("DonationId")), pi.getStatus());


    }
}
