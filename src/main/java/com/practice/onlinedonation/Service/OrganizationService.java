package com.practice.onlinedonation.Service;

import com.practice.onlinedonation.payload.OrganizationDTO;

public interface OrganizationService {
    OrganizationDTO createOrganization(Long categoryId, OrganizationDTO organizationDTO);
}
