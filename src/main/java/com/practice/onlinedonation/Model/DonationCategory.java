package com.practice.onlinedonation.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DonationCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long DonationCategoryId;

    private String categoryName;


}
