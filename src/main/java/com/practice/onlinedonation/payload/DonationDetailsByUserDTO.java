package com.practice.onlinedonation.payload;

import com.practice.onlinedonation.Model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonationDetailsByUserDTO {

   /* private Integer donationId;
    private String description;
    private Long donationAmount;
    private String username;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String organizationName;*/

    private Integer donationId;
    private String description;
    private Long donationAmount;
    private String organizationName;
    private String categoryName;



}
