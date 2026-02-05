package com.practice.onlinedonation.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonationDTO {

    private String description;
    private Long donationAmount;
    private Long userId;
    //private String username;
    private String organizationName;

}
