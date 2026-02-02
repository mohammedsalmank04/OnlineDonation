package com.practice.onlinedonation.payload.donationByUserDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonationHelperDTO {

    private String description;
    private Long donationAmount;
}
