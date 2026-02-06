package com.practice.onlinedonation.Service;

import com.practice.onlinedonation.Model.Organization;
import com.practice.onlinedonation.payload.OrganizationDTO;
import com.practice.onlinedonation.payload.OrganizationResponseDTO;

import java.util.Optional;

public interface OrganizationService {
    OrganizationDTO createOrganization(Long categoryId, OrganizationDTO organizationDTO);

    OrganizationResponseDTO getAllOrganization();

    OrganizationDTO editOrganization(Long organizationId,Long categoryId ,OrganizationDTO organization);

    int editOrganization1(Long organizationId, OrganizationDTO organization);

    Optional<Organization> findById(Long id);
}
