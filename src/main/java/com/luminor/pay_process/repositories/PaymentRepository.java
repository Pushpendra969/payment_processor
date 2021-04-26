package com.luminor.pay_process.repositories;

import com.luminor.pay_process.models.PaymentModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<PaymentModel,Long> {
    List<PaymentModel> findAllByDebtorIban(String debtorIban);
}
