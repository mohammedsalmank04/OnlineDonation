package com.practice.onlinedonation.stripe.util;

import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.CustomerCollection;
import com.stripe.param.CustomerListParams;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerUtil {

    public static Customer findOrCreateCustomer(String email, String userName) throws StripeException {

        // Try to find existing customer by email

        CustomerListParams listParams =
                CustomerListParams.builder()
                        .setEmail(email)
                        .setLimit(1L).build();
        CustomerCollection customer = Customer.list(listParams);

        // Retrieve the first customer if the Customer collection is not empty(If there is a customer present)
        if(!customer.getData().isEmpty()){
            return customer.getData().getFirst();
        }

        //Create a new customer in stripe if we did not receive a customer with email
        Map<String, Object> params = new HashMap<>();
        params.put("email", email);

        return Customer.create(params);
    }
}
