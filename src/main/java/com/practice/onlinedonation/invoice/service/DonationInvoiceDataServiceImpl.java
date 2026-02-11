package com.practice.onlinedonation.invoice.service;

import com.practice.onlinedonation.Exception.ResourceNotFoundException;
import com.practice.onlinedonation.invoice.payload.DonationInvoiceData;
import com.practice.onlinedonation.invoice.repository.DonationInvoiceDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DonationInvoiceDataServiceImpl implements DonationInvoiceDataService{
    @Autowired
    private DonationInvoiceDataRepository donationInvoiceDataRepository;
    @Autowired
    private InvoicePdfService invoicePdfService;

    @Override
    public DonationInvoiceData save(DonationInvoiceData data) {
        return donationInvoiceDataRepository.save(data);
    }

    @Override
    public byte[] generatePdf(Long invoiceId){
        DonationInvoiceData data = donationInvoiceDataRepository.findById(invoiceId).orElseThrow(
                () -> new ResourceNotFoundException("Invoice", "InvoiceID: ", invoiceId)
        );
      return  invoicePdfService.generateInvoice(data);
    }


}
