package com.practice.onlinedonation.Service;

import com.practice.onlinedonation.payload.CategoryResponseDTO;
import com.practice.onlinedonation.payload.DonationCategoryDTO;

public interface DonationCategoryService {
    DonationCategoryDTO createDonationCategory(DonationCategoryDTO donationCategory);

    CategoryResponseDTO getALlCategory();

    int editCategory(Long categoryId, DonationCategoryDTO donationCategoryDTO);

    String deleteCategory(Long categoryId);
}
