package com.practice.onlinedonation.Repository;

import com.practice.onlinedonation.Model.Donation;
import com.practice.onlinedonation.Model.User;
import com.practice.onlinedonation.payload.DonationDetailsByUserDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DonationRepository extends JpaRepository<Donation, Long> {
    List<Donation> findByUser(User userId);


    @Query("""
            SELECT new com.practice.onlinedonation.payload.DonationDetailsByUserDTO (d.donationId,
                   d.description,
                   d.donationAmount,
                   o.organizationName,
                   dc.categoryName
                               )
            FROM Donation d
            JOIN d.user u
            JOIN d.organization o
            JOIN o.donationCategory dc
            WHERE u.userId = :userId
           \s""")
    List<DonationDetailsByUserDTO> findDonationMadeByUser(@Param("userId") Long userId);

    @EntityGraph(
            attributePaths = {
                    "user",
                    "organization",
                    "organization.donationCategory"
            }
    )
    List<Donation> findByUser_userId(Long userId);

    @EntityGraph(
            attributePaths = {
                    "user",
                    "organization",
                    "organization.donationCategory"
            }
    )
    List<Donation> findAll();

    /*@EntityGraph(
            attributePaths = {
                    "user",
                    "organization",
                    "organization.donationCategory"
            }
    )
    List<Donation> findByStatusAndUser_UserId(String status, Long userId);*/

    @EntityGraph(
            attributePaths = {
                    "user",
                    "organization",
                    "organization.donationCategory"
            }
    )
    Page<Donation> findByStatusAndUser_UserId(String status, Long userId, Pageable pageDetails);

    @Transactional
    @Modifying
    @Query("UPDATE Donation  d SET d.status = :status WHERE d.donationId = :donationId")
    void updateStatus(
            @Param("donationId") long donationId,
            @Param("status") String status
                      );

    @Transactional
    void deleteByDescription(String description);
}





