package com.practice.onlinedonation.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long organizationId;

    private String organizationName;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private DonationCategory donationCategory;
}
