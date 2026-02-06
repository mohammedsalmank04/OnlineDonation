package com.practice.onlinedonation.Repository;

import com.practice.onlinedonation.Model.DonationCategory;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DonationCategoryRepository extends JpaRepository<DonationCategory, Long> {


    DonationCategory findByCategoryName(String categoryName);

    @Transactional
    @Modifying
    @Query("UPDATE DonationCategory  c SET c.categoryName = :categoryName WHERE c.DonationCategoryId = :categoryId")
    int updateCategory(
            @Param("categoryId") long categoryId,
            @Param("categoryName") String categoryName
    );


}

