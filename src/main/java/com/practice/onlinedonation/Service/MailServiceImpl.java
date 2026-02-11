package com.practice.onlinedonation.Service;

import com.practice.onlinedonation.invoice.service.DonationInvoiceDataService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private DonationInvoiceDataService donationInvoiceDataService;
    @Override
    public void sendDonationEmail(String to, String name, Long invoiceId) throws MessagingException {

        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg,true);

        helper.setTo(to);
        helper.setSubject("Donation Invoice");
        helper.setText("Thank you for Donating");
        byte[] pdfByte = donationInvoiceDataService.generatePdf(invoiceId);

        helper.addAttachment("invoice.pdf", new ByteArrayResource(pdfByte));

        javaMailSender.send(msg);



    }
}
