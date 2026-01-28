package com.practice.onlinedonation.Service;

import com.practice.onlinedonation.Model.Donation;
import com.practice.onlinedonation.payload.DonationDTO;
import com.practice.onlinedonation.payload.DonationResponseDTO;

import java.util.List;

public interface DonationService{


    void makeDonation(Long userId, DonationDTO donationDTO);

    DonationResponseDTO getAllDonations();
}
