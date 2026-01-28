package com.practice.onlinedonation.Repository;

import com.practice.onlinedonation.Model.Donation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonationRepository extends JpaRepository<Donation, Long> {
}
