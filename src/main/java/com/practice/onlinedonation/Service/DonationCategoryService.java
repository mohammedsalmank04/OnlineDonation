package com.practice.onlinedonation.Service;

import com.practice.onlinedonation.payload.DonationCategoryDTO;
import com.practice.onlinedonation.payload.DonationResponseDTO;

public interface DonationCategoryService {
    DonationCategoryDTO createDonationCategory(DonationCategoryDTO donationCategory);
}
