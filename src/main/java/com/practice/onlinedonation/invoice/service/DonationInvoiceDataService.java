package com.practice.onlinedonation.invoice.service;

import com.practice.onlinedonation.invoice.payload.DonationInvoiceData;

public interface DonationInvoiceDataService {

    DonationInvoiceData save (DonationInvoiceData data);



    byte[] generatePdf(Long invoiceId);
}
