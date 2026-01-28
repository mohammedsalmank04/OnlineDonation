package com.practice.onlinedonation.Service;

import com.practice.onlinedonation.Exception.ApiException;
import com.practice.onlinedonation.Model.DonationCategory;
import com.practice.onlinedonation.Repository.DonationCategoryRepository;
import com.practice.onlinedonation.Repository.DonationRepository;
import com.practice.onlinedonation.payload.DonationCategoryDTO;
import com.practice.onlinedonation.payload.DonationResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DonationCategoryServiceImpl implements DonationCategoryService {
    @Autowired
    private DonationCategoryRepository donationCategoryRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public DonationCategoryDTO createDonationCategory(DonationCategoryDTO donationCategory) {
        DonationCategory categoryFromDb = donationCategoryRepository.findByCategoryName(donationCategory.getCategoryName());
        if(categoryFromDb != null){
            throw new ApiException("Category with name: " + donationCategory.getCategoryName() + " Already Exists");
        }
        DonationCategory newDonationCategory = modelMapper.map(donationCategory,DonationCategory.class);
        donationCategoryRepository.save(newDonationCategory);

        return modelMapper.map(newDonationCategory,DonationCategoryDTO.class);
    }
}
