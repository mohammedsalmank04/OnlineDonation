package com.practice.onlinedonation.Service;

import com.practice.onlinedonation.Exception.ApiException;
import com.practice.onlinedonation.Exception.ResourceNotFoundException;
import com.practice.onlinedonation.Model.DonationCategory;
import com.practice.onlinedonation.Repository.DonationCategoryRepository;
import com.practice.onlinedonation.payload.CategoryResponseDTO;
import com.practice.onlinedonation.payload.DonationCategoryDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public CategoryResponseDTO getALlCategory() {
        List<DonationCategory> donationCategoryList = donationCategoryRepository.findAll();
        List<DonationCategoryDTO> li = donationCategoryList.stream().map(
                category -> modelMapper.map(category,DonationCategoryDTO.class)
        ).toList();

        return new CategoryResponseDTO(li);
    }

    @Override
    public DonationCategoryDTO editCategory(Long categoryId,
                                            DonationCategoryDTO donationCategoryDTO) {
        DonationCategory oldCategory = donationCategoryRepository.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("Category","Category ID: ",categoryId)
        );
        oldCategory.setCategoryName(donationCategoryDTO.getCategoryName());
        DonationCategory newCategory = donationCategoryRepository.save(oldCategory);
        return modelMapper.map(newCategory, DonationCategoryDTO.class);
    }

    @Override
    public String deleteCategory(Long categoryId) {
        donationCategoryRepository.deleteById(categoryId);
        return "Category Deleted";
    }
}
