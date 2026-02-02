package com.practice.onlinedonation.Service;

import com.practice.onlinedonation.Model.Donation;
import com.practice.onlinedonation.payload.DonationDetailsByUserResponseDTO;
import com.practice.onlinedonation.payload.donationByUserDTO.DonationByUserDTO;
import com.practice.onlinedonation.payload.DonationDTO;
import com.practice.onlinedonation.payload.DonationResponseDTO;
import com.practice.onlinedonation.payload.donationByUserDTO.DonationDetailsByUserDTOLi;

import java.util.List;

public interface DonationService{


    void makeDonation(Long userId, DonationDTO donationDTO, Long organizationId);

    DonationResponseDTO getAllDonations();

    DonationResponseDTO getDonationByUsername(Long userId);

    DonationByUserDTO getDonationByUser(Long userId);

    DonationByUserDTO getDonationByUser1(Long userId);

    DonationDetailsByUserResponseDTO getDonationByUserByQuery(Long userId);


    DonationDetailsByUserResponseDTO getDonationByUserEntity(Long userId);
}
