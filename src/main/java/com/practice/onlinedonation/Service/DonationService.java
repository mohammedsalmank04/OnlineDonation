package com.practice.onlinedonation.Service;

import com.practice.onlinedonation.Model.Donation;
import com.practice.onlinedonation.payload.DonationDetailsByUserResponseDTO;
import com.practice.onlinedonation.payload.donationByUserDTONotInUse.DonationByUserDTO;
import com.practice.onlinedonation.payload.DonationDTO;
import com.practice.onlinedonation.payload.DonationResponseDTO;
import com.practice.onlinedonation.stripe.payload.PaymentReqeuestDTO;

public interface DonationService{


    void makeDonation(Long userId, DonationDTO donationDTO, Long organizationId);

    DonationResponseDTO getAllDonations();

    DonationResponseDTO getDonationByUsername(Long userId);

    DonationByUserDTO getDonationByUser(Long userId);

    DonationByUserDTO getDonationByUser1(Long userId);

    DonationDetailsByUserResponseDTO getDonationByUserByQuery(Long userId);


    DonationDetailsByUserResponseDTO getDonationByUserEntity(Long userId);

    Donation makeDonation(Donation donation);

    Donation createDonation(PaymentReqeuestDTO prDTO);

    void updateStatusOfPayment(long donationId, String status);

    //Method to get only succeeded payments
    DonationDetailsByUserResponseDTO getDonationByUserEntitySucceeded(Long userId, String status);

    Donation findDonationById(String donationId);
}
