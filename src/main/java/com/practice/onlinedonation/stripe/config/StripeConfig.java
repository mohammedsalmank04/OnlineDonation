package com.practice.onlinedonation.stripe.config;

import com.stripe.Stripe;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("!test")
@Configuration
public class StripeConfig {

    @Value("${stripe.api.secrey.key}")
    private String STRIPE_API_KEYS;

    @PostConstruct
    public void init(){
        Stripe.apiKey = STRIPE_API_KEYS;
    }
}
