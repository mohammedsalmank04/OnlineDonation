package com.practice.onlinedonation.payload.donationByUserDTO;

import com.practice.onlinedonation.payload.DonationDTO;
import com.practice.onlinedonation.payload.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DonationByUserDTO {

    private UserDTO user;
    List<DonationByUserDTOHelper> li;

}
