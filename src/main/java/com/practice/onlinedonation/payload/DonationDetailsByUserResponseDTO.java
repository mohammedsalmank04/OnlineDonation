package com.practice.onlinedonation.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonationDetailsByUserResponseDTO {

    private UserDTO userDTO;
    private List<DonationDetailsByUserDTO> li;
}
