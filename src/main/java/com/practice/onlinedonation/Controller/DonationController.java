package com.practice.onlinedonation.Controller;

import com.practice.onlinedonation.Model.Donation;
import com.practice.onlinedonation.Service.DonationCategoryService;
import com.practice.onlinedonation.Service.DonationService;
import com.practice.onlinedonation.Service.OrganizationService;
import com.practice.onlinedonation.payload.*;
import com.practice.onlinedonation.payload.donationByUserDTO.DonationByUserDTO;
import com.practice.onlinedonation.payload.donationByUserDTO.DonationDetailsByUserDTOLi;
import com.practice.onlinedonation.security.UserDetails.UserDetailsImpl;
import com.practice.onlinedonation.stripe.config.StatusCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DonationController {

    @Autowired
    private DonationService donationService;
    @Autowired
    private DonationCategoryService donationCategoryService;
    @Autowired
    private OrganizationService organizationService;



    //Method handles making a donation
    @PostMapping("/donation/{userId}/{organizationId}")
    public ResponseEntity<String> makeDonation(
            @PathVariable Long userId,
            @RequestBody DonationDTO donationDTO,
            @PathVariable Long organizationId
    ){

        donationService.makeDonation(userId,donationDTO,organizationId);
        return new ResponseEntity<>("Donation success", HttpStatus.OK);
    }

    // Gets all the donation along with the user and also organization
    @GetMapping("/donation")
    public ResponseEntity<DonationResponseDTO> getAllDonation(){

        DonationResponseDTO donations = donationService.getAllDonations();
        return new ResponseEntity<>(donations,HttpStatus.OK);
    }

    // This method Create donation category
    @PostMapping("/donation/category")
    public ResponseEntity<?> createDonationCategory(
            @RequestBody DonationCategoryDTO donationCategory){
        DonationCategoryDTO donationCategoryCreated =
                donationCategoryService.createDonationCategory(donationCategory);
        return new ResponseEntity<>(donationCategoryCreated,HttpStatus.CREATED);

    }
    //This method creates organization name and map it to a category
    @PostMapping("/organization/{categoryId}")
    public ResponseEntity<OrganizationDTO> createOrganization(
            @PathVariable Long categoryId,@RequestBody OrganizationDTO organizationDTO){
        OrganizationDTO organization = organizationService.createOrganization(categoryId, organizationDTO);
        return new ResponseEntity<>(organization,HttpStatus.CREATED);
    }

    //This method get All categories
    @GetMapping("/categories")
    public ResponseEntity<CategoryResponseDTO> getAllCategories(){
        CategoryResponseDTO categoryResponseDTO = donationCategoryService.getALlCategory();
        return new ResponseEntity<>(categoryResponseDTO, HttpStatus.OK);
    }

    //This method edits the name of category
    @PutMapping("/admin/category/{categoryId}")
    public ResponseEntity<DonationCategoryDTO> editCategory(
            @PathVariable Long categoryId,
            @RequestBody DonationCategoryDTO donationCategoryDTO
            ){
        DonationCategoryDTO editedCategory = donationCategoryService.editCategory(categoryId, donationCategoryDTO);
        return new ResponseEntity<>(editedCategory,HttpStatus.CREATED);
    }

    // This method gets all the organization
    @GetMapping("/public/organization")
    public ResponseEntity<OrganizationResponseDTO> getAllOrganization(){
        OrganizationResponseDTO organizationList = organizationService.getAllOrganization();

        return new ResponseEntity<>(organizationList, HttpStatus.OK);
    }

    //This method edit organization
    @PutMapping("/admin/organization/{organizationId}")
    public ResponseEntity<OrganizationDTO> editOrganization(
            @PathVariable Long organizationId,
           // @PathVariable Long categoryId,
            @RequestBody OrganizationDTO organization
            ){
        OrganizationDTO newOrganization = organizationService.editOrganization1(organizationId,organization);
        return new ResponseEntity<>(newOrganization, HttpStatus.CREATED);

    }

    //This method deletes category
    @DeleteMapping("/admin/category/{categoryId}")
    public ResponseEntity<String> deleteCategory(
            @PathVariable Long categoryId){
        String status = donationCategoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(status,HttpStatus.OK);
    }

    //This method gets a donation made by a specific user
    /*@GetMapping("/public/donation/{userId}")
    public ResponseEntity<DonationResponseDTO> getDonationMadeByUser(@PathVariable Long userId){
        DonationResponseDTO donationList = donationService.getDonationByUsername(userId);
        return new ResponseEntity<>(donationList,HttpStatus.OK);
    }*/

   /* @GetMapping("/public/donation/{userId}")
    public ResponseEntity<DonationByUserDTO> getDonationMadeByUser(@PathVariable Long userId){
        DonationByUserDTO donationList = donationService.getDonationByUser1(userId);
        return new ResponseEntity<>(donationList,HttpStatus.OK);
    }*/

    /*@GetMapping("/public/donation/{userId}")
    public ResponseEntity<DonationDetailsByUserResponseDTO> getDonationMadeByUser(@PathVariable Long userId){
        DonationDetailsByUserResponseDTO donationList = donationService.getDonationByUserByQuery(userId);
        return new ResponseEntity<>(donationList,HttpStatus.OK);
    }*/

    /*@GetMapping("/public/donation/{userId}")
    public ResponseEntity<List<Donation>> getDonationMadeByUser(@PathVariable Long userId){
        List<Donation> li = donationService.getDonationByUserEntity(userId);
        return new ResponseEntity<>(li,HttpStatus.OK);
    }*/

    @GetMapping("/public/donation")
    public ResponseEntity<DonationDetailsByUserResponseDTO> getDonationByUserEntity(Authentication authentication){
        UserDetailsImpl currentUser = (UserDetailsImpl) authentication.getPrincipal();
        String code = String.valueOf(StatusCodes.succeeded);

        DonationDetailsByUserResponseDTO ddByUser = donationService.getDonationByUserEntitySucceeded(currentUser.getId(), code);
        return new ResponseEntity<>(ddByUser,HttpStatus.OK);
    }




}
