package com.practice.onlinedonation.Service;

import com.practice.onlinedonation.Exception.ResourceNotFoundException;
import com.practice.onlinedonation.Model.DonationCategory;
import com.practice.onlinedonation.Model.Organization;
import com.practice.onlinedonation.Repository.DonationCategoryRepository;
import com.practice.onlinedonation.Repository.OrganizationRepository;
import com.practice.onlinedonation.payload.OrganizationDTO;
import com.practice.onlinedonation.payload.OrganizationResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        Organization organization = modelMapper.map(organizationDTO,Organization.class);
        organization.setDonationCategory(donationCategory);
        Organization savedOrganization = organizationRepository.save(organization);

        return modelMapper.map(organization, OrganizationDTO.class);
    }

    @Override
    public OrganizationResponseDTO getAllOrganization() {
        List<Organization> organizationList = organizationRepository.findAll();
        List<OrganizationDTO> organizationDTO = organizationList.stream().map(
                organization -> modelMapper.map(organization,OrganizationDTO.class)
        ).toList();
        OrganizationResponseDTO organizationResponseDTO = new OrganizationResponseDTO();
        organizationResponseDTO.setBuild(organizationDTO);
        return organizationResponseDTO;
    }

    @Override
    public OrganizationDTO editOrganization(
            Long organizationId,
            Long categoryId,
            OrganizationDTO organization) {
        Organization oldOrganization = organizationRepository.findById(organizationId).orElseThrow(
                () -> new ResourceNotFoundException("Organization","Organization ID: " , organizationId)
        );
        DonationCategory category = donationCategoryRepository.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("Category", "Category ID:",categoryId)
        );

        oldOrganization.setOrganizationName(organization.getOrganizationName());
        oldOrganization.setDonationCategory(category);
        Organization savedOrganization = organizationRepository.save(oldOrganization);
        return modelMapper.map(savedOrganization,OrganizationDTO.class);
    }

    public int editOrganization1(
            Long organizationId,

            OrganizationDTO organization) {
        return organizationRepository.updateOrganization(organizationId,organization.getOrganizationName());
    }

    @Override
    public Optional<Organization> findById(Long id){
        return organizationRepository.findById(id);
    }

}
