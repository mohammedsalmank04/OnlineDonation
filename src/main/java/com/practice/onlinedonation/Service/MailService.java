package com.practice.onlinedonation.Service;

import jakarta.mail.MessagingException;

public interface MailService {



    void sendDonationEmail(String to, String name, Long invoiceId) throws MessagingException;
}
