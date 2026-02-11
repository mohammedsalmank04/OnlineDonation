package com.practice.onlinedonation.Service;

import com.practice.onlinedonation.Model.Payment;
import com.practice.onlinedonation.Repository.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService{

    @Autowired
    private PaymentRepository paymentRepository;
    @Override
    public Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }
}
