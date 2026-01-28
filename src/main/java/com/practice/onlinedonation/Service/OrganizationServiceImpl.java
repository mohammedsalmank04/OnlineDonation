package com.practice.onlinedonation.Service;

import com.practice.onlinedonation.Exception.ResourceNotFoundException;
import com.practice.onlinedonation.Model.DonationCategory;
import com.practice.onlinedonation.Model.Organization;
import com.practice.onlinedonation.Repository.DonationCategoryRepository;
import com.practice.onlinedonation.Repository.OrganizationRepository;
import com.practice.onlinedonation.payload.OrganizationDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationServiceImpl implements OrganizationService {
    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private DonationCategoryRepository donationCategoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public OrganizationDTO createOrganization(Long categoryId, OrganizationDTO organizationDTO) {
        DonationCategory donationCategory = donationCategoryRepository.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("Category","CategoryID",categoryId)
        );
        organizationDTO.setDonationCategory(donationCategory);
        Organization organization = modelMapper.map(organizationDTO,Organization.class);
        return modelMapper.map(organizationRepository.save(organization), OrganizationDTO.class);
    }
}
