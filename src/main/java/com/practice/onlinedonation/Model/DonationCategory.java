package com.practice.onlinedonation.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DonationCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long DonationCategoryId;

    private String categoryName;

    @OneToMany(
            mappedBy = "donationCategory",
            cascade = {CascadeType.MERGE,CascadeType.PERSIST}, orphanRemoval = true
    )
    private List<Organization> organizationList;


}
