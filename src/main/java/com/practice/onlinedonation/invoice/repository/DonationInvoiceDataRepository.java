package com.practice.onlinedonation.invoice.repository;

import com.practice.onlinedonation.invoice.payload.DonationInvoiceData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonationInvoiceDataRepository extends JpaRepository<DonationInvoiceData,Long> {
}
