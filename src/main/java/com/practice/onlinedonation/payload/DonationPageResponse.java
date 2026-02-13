package com.practice.onlinedonation.payload;

import com.practice.onlinedonation.Model.Donation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonationPageResponse {

    List<DonationDetailsByUserDTO> donationList;
    private UserDTO userDTO;
    private Integer pageNumber;
    private Integer pageSize;
    private Long totalElements;
    private Integer totalPages;
    private boolean lastPage;
}
