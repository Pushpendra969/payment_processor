package com.luminor.pay_process.models;

import com.luminor.pay_process.beans.Iban;
import lombok.*;
import org.hibernate.validator.constraints.Range;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PaymentModel extends BaseModel{
    @Id
    @GeneratedValue
    private UUID id = UUID.randomUUID();

    @NotNull
    @Range(min = 1)
//    @CsvBindByName(column = "amount")
    private Long amount;

    @NotNull
    @Iban()
//    @CsvBindByName(column = "debtorIban")
    private String debtorIban;

    private String country;
}
