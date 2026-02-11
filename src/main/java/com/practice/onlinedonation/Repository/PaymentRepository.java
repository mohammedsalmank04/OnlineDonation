package com.practice.onlinedonation.Repository;

import com.practice.onlinedonation.Model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment,Integer> {
}
