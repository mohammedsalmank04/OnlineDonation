package com.practice.onlinedonation.Service;

import com.practice.onlinedonation.Exception.ResourceNotFoundException;
import com.practice.onlinedonation.Model.Donation;
import com.practice.onlinedonation.Model.User;
import com.practice.onlinedonation.Repository.DonationRepository;
import com.practice.onlinedonation.Repository.UserRepository;
import com.practice.onlinedonation.payload.DonationDTO;
import com.practice.onlinedonation.payload.DonationResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonationServiceImpl implements DonationService{
    @Autowired
    UserRepository userRepository;
    @Autowired
    DonationRepository donationRepository;
    @Autowired
    ModelMapper modelMapper;
    @Override
    public void makeDonation(Long userId, DonationDTO donationDTO) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("user","user ID: ", userId)
        );
        Donation donation = modelMapper.map(donationDTO,Donation.class);
        donation.setUser(user);

        donationRepository.save(donation);
    }

    @Override
    public DonationResponseDTO getAllDonations() {
        List<Donation> donations = donationRepository.findAll();
        List<DonationDTO> donationDTOList = donations
                .stream().map(
                    donation -> modelMapper.map(donation,DonationDTO.class)
                ).toList();
        DonationResponseDTO donationResponseDTO = new DonationResponseDTO();
        donationResponseDTO.setDonations(donationDTOList);
        return donationResponseDTO;
    }
}
