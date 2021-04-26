package com.luminor.pay_process.services;

import com.luminor.pay_process.models.PaymentModel;
import com.luminor.pay_process.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class PaymentService {

    @Autowired
    PaymentRepository paymentRepository;

    public PaymentModel createPayment(PaymentModel paymentModel){
        PaymentModel savedPayment = paymentRepository.save(paymentModel);
        return savedPayment;
    }

    public List<PaymentModel> listPayments(String debtorIban){
        List<PaymentModel> paymentModels;
        if(debtorIban !=null){
            paymentModels = paymentRepository.findAllByDebtorIban(debtorIban);
        }
        else {
            paymentModels = paymentRepository.findAll();
        }
        return paymentModels;
    }
}
