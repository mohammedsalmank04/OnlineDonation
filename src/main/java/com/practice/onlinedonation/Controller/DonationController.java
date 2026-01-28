package com.practice.onlinedonation.Controller;

import com.practice.onlinedonation.Service.DonationCategoryService;
import com.practice.onlinedonation.Service.DonationService;
import com.practice.onlinedonation.Service.OrganizationService;
import com.practice.onlinedonation.payload.DonationCategoryDTO;
import com.practice.onlinedonation.payload.DonationDTO;
import com.practice.onlinedonation.payload.DonationResponseDTO;
import com.practice.onlinedonation.payload.OrganizationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class DonationController {

    @Autowired
    private DonationService donationService;
    @Autowired
    private DonationCategoryService donationCategoryService;
    @Autowired
    private OrganizationService organizationService;


    @PostMapping("/donation/{userId}")
    public ResponseEntity<String> makeDonation(
            @PathVariable Long userId,
            @RequestBody DonationDTO donationDTO ){

        donationService.makeDonation(userId,donationDTO);
        return new ResponseEntity<>("Donation success", HttpStatus.OK);
    }

    @GetMapping("/donation")
    public ResponseEntity<DonationResponseDTO> getAllDonation(){
        DonationResponseDTO donations = donationService.getAllDonations();
        return new ResponseEntity<>(donations,HttpStatus.OK);
    }

    @PostMapping("/donation/category")
    public ResponseEntity<?> createDonationCategory(
            @RequestBody DonationCategoryDTO donationCategory){
        DonationCategoryDTO donationCategoryCreated =
                donationCategoryService.createDonationCategory(donationCategory);
        return new ResponseEntity<>(donationCategoryCreated,HttpStatus.CREATED);

    }

    @PostMapping("/organization/{categoryId}")
    public ResponseEntity<?> createOrganization(
            @PathVariable Long categoryId,@RequestBody OrganizationDTO organizationDTO){
        OrganizationDTO organization = organizationService.createOrganization(categoryId, organizationDTO);
        return new ResponseEntity<>(organization,HttpStatus.CREATED);
    }

}
