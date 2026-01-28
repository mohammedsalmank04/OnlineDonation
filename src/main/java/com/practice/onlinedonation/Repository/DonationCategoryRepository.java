package com.practice.onlinedonation.Repository;

import com.practice.onlinedonation.Model.DonationCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonationCategoryRepository extends JpaRepository<DonationCategory,Long> {


    DonationCategory findByCategoryName(String categoryName);
}
