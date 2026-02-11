package com.practice.onlinedonation.Model;

import jakarta.persistence.*;
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
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long paymentId;

    private String stripePaymentIntentId;
    private String stripeChargeId;
    private Integer amount;
    private String stripeUserId;
    private String currency;
    private String status;
    private String email;
    private String paymentMethod;
    private LocalDateTime paymentDate;
    private String invoiceNumber;

    @OneToOne
    @JoinColumn(name = "donation_id")
    private Donation donation;

}
