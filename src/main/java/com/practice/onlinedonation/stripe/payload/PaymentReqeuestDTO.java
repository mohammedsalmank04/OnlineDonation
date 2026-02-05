package com.practice.onlinedonation.stripe.payload;

import com.practice.onlinedonation.payload.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentReqeuestDTO {

    UserDTO user;
    private long organizationId;
    private long donationAmount;
    private String description;



}
