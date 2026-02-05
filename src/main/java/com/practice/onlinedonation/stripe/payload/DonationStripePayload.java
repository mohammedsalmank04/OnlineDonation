package com.practice.onlinedonation.stripe.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DonationStripePayload {

    private Long userId;
    private Long organizationId;
    private Long donationAmount;
    private String description;
    private String status;
}
