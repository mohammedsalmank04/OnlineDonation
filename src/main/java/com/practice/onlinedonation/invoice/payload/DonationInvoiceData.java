package com.practice.onlinedonation.invoice.payload;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DonationInvoiceData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long invoiceId;
    private String name;
    private Integer amount;
    private String organization;
    private String status;
    private String currency;
    private String email;
    private String paymentMethod;
    private LocalDateTime paymentDate;
    private String invoiceNumber;

}
