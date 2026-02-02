package com.practice.onlinedonation.payload;

import com.practice.onlinedonation.Model.DonationCategory;
import com.practice.onlinedonation.Repository.OrganizationRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationDTO {
    private Long organizationId;
    private String organizationName;



}
