package com.practice.onlinedonation.invoice.controller;

import com.practice.onlinedonation.invoice.payload.DonationInvoiceData;
import com.practice.onlinedonation.invoice.service.DonationInvoiceDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/invoice")
public class invoiceController {
    @Autowired
    private DonationInvoiceDataService donationInvoiceDataService;

    @GetMapping("/download/{invoiceId}")
    public ResponseEntity<byte[]> downloadPdf(@PathVariable Long invoiceId){
        byte[] pdf = donationInvoiceDataService.generatePdf(invoiceId);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename = invoice.pdf"
                ).contentType(MediaType.APPLICATION_PDF).body(pdf);

    }
}
