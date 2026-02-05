package com.practice.onlinedonation.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonationCategoryDTO {

    private Long DonationCategoryId;

    private String categoryName;
}
