package com.practice.onlinedonation.payload.donationByUserDTO;

import com.practice.onlinedonation.payload.DonationDetailsByUserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonationDetailsByUserDTOLi {

    List<DonationDetailsByUserDTO> li;
}
