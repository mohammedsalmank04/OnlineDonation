package com.practice.onlinedonation.Service;

import com.practice.onlinedonation.Exception.ResourceNotFoundException;
import com.practice.onlinedonation.Model.Donation;
import com.practice.onlinedonation.Model.DonationCategory;
import com.practice.onlinedonation.Model.Organization;
import com.practice.onlinedonation.Model.User;
import com.practice.onlinedonation.Repository.DonationCategoryRepository;
import com.practice.onlinedonation.Repository.DonationRepository;
import com.practice.onlinedonation.Repository.OrganizationRepository;
import com.practice.onlinedonation.Repository.UserRepository;
import com.practice.onlinedonation.payload.*;
import com.practice.onlinedonation.payload.donationByUserDTONotInUse.*;
import com.practice.onlinedonation.stripe.payload.PaymentReqeuestDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DonationServiceImpl implements DonationService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    DonationRepository donationRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private DonationCategoryRepository donationCategoryRepository;


    @Override
    public void makeDonation(
            Long userId,
            DonationDTO donationDTO,
            Long organizationId
    ) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("user", "user ID: ", userId)
        );
        Organization organization = organizationRepository.findById(organizationId).orElseThrow(
                () -> new ResourceNotFoundException("Organization", "Organization ID: ", organizationId)
        );

        Donation donation = modelMapper.map(donationDTO, Donation.class);
        donation.setOrganization(organization);
        donation.setUser(user);

        donationRepository.save(donation);
    }

    @Override
    public DonationResponseDTO getAllDonations() {
        List<Donation> donations = donationRepository.findAll();
        List<DonationDTO> donationDTOList = donations
                .stream().map(
                        donation -> modelMapper.map(donation, DonationDTO.class)
                ).toList();
        DonationResponseDTO donationResponseDTO = new DonationResponseDTO();
        donationResponseDTO.setDonations(donationDTOList);
        return donationResponseDTO;
    }

    @Override
    public DonationResponseDTO getDonationByUsername(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", "User ID;", userId)
        );
        List<Donation> donationList = donationRepository.findByUser(user);
        List<DonationDTO> donationDTOList = donationList.stream().map(
                donation -> modelMapper.map(donation, DonationDTO.class)
        ).toList();
        return new DonationResponseDTO(donationDTOList);
    }

    public DonationByUserDTO getDonationByUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", "User ID;", userId)
        );
        DonationByUserDTO donationByUserDTO = new DonationByUserDTO();
        donationByUserDTO.setUser(modelMapper.map(user, UserDTO.class));
        List<Donation> donationList = donationRepository.findByUser(user);

        List<DonationByUserDTOHelper> donationDTOList = donationList.stream().map(
                donation -> modelMapper.map(donation, DonationByUserDTOHelper.class)
        ).toList();
        donationByUserDTO.setLi(donationDTOList);
        return donationByUserDTO;
    }

    public DonationByUserDTO getDonationByUser1(Long userId) {
        Long startTime = System.currentTimeMillis();
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", "User ID;", userId)
        );
        DonationByUserDTO donationByUserDTO = new DonationByUserDTO();
        donationByUserDTO.setUser(modelMapper.map(user, UserDTO.class));
        List<Donation> donationList = donationRepository.findByUser(user);
        List<DonationByUserDTOHelper> donationDTOList = new ArrayList<>();
        for (Donation d : donationList) {
            DonationHelperDTO donationHelperDTO = modelMapper.map(d, DonationHelperDTO.class);
            Organization organization = organizationRepository.findById(d.getOrganization().getOrganizationId()).orElseThrow(
                    () -> new ResourceNotFoundException("Organization", "organzation ID", d.getOrganization().getOrganizationId())
            );
            DonationCategory donationCategory = donationCategoryRepository.findById(organization.getDonationCategory().getDonationCategoryId()).orElseThrow(
                    () -> new ResourceNotFoundException("Category", "CategoryId", organization.getDonationCategory().getDonationCategoryId())
            );
            DonationByUserDTOHelper donationByUserDTOHelper = new DonationByUserDTOHelper();
            donationByUserDTOHelper.setDonationHelperDTO(donationHelperDTO);
            donationByUserDTOHelper.setDonationOrganizationHelperDTO(modelMapper.map(organization, DonationOrganizationHelperDTO.class));
            donationByUserDTOHelper.setDonationCategoryHelperDTO(modelMapper.map(donationCategory, DonationCategoryHelperDTO.class));

            donationDTOList.add(donationByUserDTOHelper);
        }

        donationByUserDTO.setLi(donationDTOList);
        Long endTime = System.currentTimeMillis();

        return donationByUserDTO;
    }

    public DonationDetailsByUserResponseDTO getDonationByUserByQuery(Long userId) {
        Long startTime = System.currentTimeMillis();
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", "User ID;", userId)
        );
        DonationDetailsByUserResponseDTO ddByUser = new DonationDetailsByUserResponseDTO();
        ddByUser.setUserDTO(modelMapper.map(user, UserDTO.class));
        List<DonationDetailsByUserDTO> li = donationRepository.findDonationMadeByUser(userId);
        ddByUser.setLi(li);
        Long endTime = System.currentTimeMillis();

        getDonationByUserEntity(userId);
        return ddByUser;
    }

    @Override
    public DonationDetailsByUserResponseDTO getDonationByUserEntity(Long userId) {


        List<Donation> li = donationRepository.findByUser_userId(userId);


        if (li.isEmpty()) {
            throw new RuntimeException("There are no donation made by user with user ID: " + userId);
        }
        DonationDetailsByUserResponseDTO ddByUser = new DonationDetailsByUserResponseDTO();
        User user = li.getFirst().getUser();
        ddByUser.setUserDTO(modelMapper.map(user, UserDTO.class));

        List<DonationDetailsByUserDTO> resultList = li.stream().map(
                donation -> new DonationDetailsByUserDTO(
                        donation.getDonationId(),
                        donation.getDescription(),
                        donation.getDonationAmount(),
                        donation.getOrganization().getOrganizationName(),
                        donation.getOrganization()
                                .getDonationCategory()
                                .getCategoryName()
                )

        ).toList();
        ddByUser.setLi(resultList);
        return ddByUser;
    }

    @Override
    public Donation makeDonation(Donation donation) {
        return donationRepository.save(donation);
    }

    @Override
    public Donation createDonation(PaymentReqeuestDTO prDTO) {
        Donation d = new Donation();
        d.setUser(modelMapper.map(prDTO.getUser(), User.class));
        d.setDonationAmount(prDTO.getDonationAmount() * 100);
        Organization o = organizationRepository.findById(prDTO.getOrganizationId()).orElseThrow(
                () -> new ResourceNotFoundException("Organization", "Orgnization ID: ", prDTO.getOrganizationId())

        );
        d.setOrganization(o);
       // d.setStatus("PAYMENT_INTENT");
        d.setStatus("succeeded");
        d.setDescription(prDTO.getDescription());
        return donationRepository.save(d);

    }

    @Override
    public void updateStatusOfPayment(long donationId, String status) {
        donationRepository.updateStatus(donationId, status);
    }

    //Method to get only succeeded payments
    @Override
    public DonationDetailsByUserResponseDTO getDonationByUserEntitySucceeded(Long userId, String status) {


        List<Donation> li = donationRepository.findByStatusAndUser_UserId(status,userId);


        if (li.isEmpty()) {
            throw new RuntimeException("There are no donation made by user with user ID: " + userId);
        }

        DonationDetailsByUserResponseDTO ddByUser = new DonationDetailsByUserResponseDTO();
        User user = li.getFirst().getUser();
        ddByUser.setUserDTO(modelMapper.map(user, UserDTO.class));

        List<DonationDetailsByUserDTO> resultList = li.stream().map(
                donation -> new DonationDetailsByUserDTO(
                        donation.getDonationId(),
                        donation.getDescription(),
                        donation.getDonationAmount(),
                        donation.getOrganization().getOrganizationName(),
                        donation.getOrganization()
                                .getDonationCategory()
                                .getCategoryName()
                )

        ).toList();
        ddByUser.setLi(resultList);
        return ddByUser;
    }

    @Override
    public Donation findDonationById(String donationId) {

        return donationRepository.findById(Long.parseLong(donationId)).orElseThrow(
                () -> new ResourceNotFoundException("Donation", "Donation ID: ", Long.parseLong(donationId))
        );
    }

    }

