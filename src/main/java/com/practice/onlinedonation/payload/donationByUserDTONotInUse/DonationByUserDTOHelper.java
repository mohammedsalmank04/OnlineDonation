package com.practice.onlinedonation.payload.donationByUserDTONotInUse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DonationByUserDTOHelper {

    /*private String description;
    private Long donationAmount;
    private String organizationName;*/

    private DonationHelperDTO donationHelperDTO;
    private DonationCategoryHelperDTO donationCategoryHelperDTO;
    private DonationOrganizationHelperDTO donationOrganizationHelperDTO;

}
