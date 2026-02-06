package com.practice.onlinedonation.Repository;

import com.practice.onlinedonation.Model.Organization;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrganizationRepository extends JpaRepository<Organization,Long> {

    @Transactional
    @Modifying
    @Query("UPDATE Organization  o SET o.organizationName = :organizationName WHERE o.organizationId = :organizationId")
    int updateOrganization(
            @Param("organizationId") long organizationId,
            @Param("organizationName") String organizationName
    );
}
