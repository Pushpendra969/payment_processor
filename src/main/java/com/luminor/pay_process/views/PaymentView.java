package com.luminor.pay_process.views;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.luminor.pay_process.beans.IbanConstraintValidator;
import com.luminor.pay_process.models.PaymentModel;
import com.luminor.pay_process.repositories.PaymentRepository;
import com.luminor.pay_process.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class PaymentView {
//    Inject Repository
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentService paymentService;

    private static final CsvMapper mapper = new CsvMapper();


    @PostMapping(value = "/payments")
    public ResponseEntity<PaymentModel> savePayment(HttpServletRequest request, @Valid @RequestBody PaymentModel paymentModel){
        String country;
        country = request.getHeader("X-FORWARDED-FOR");
        if (country == null) {
            country = request.getRemoteAddr();
        }
        paymentModel.setCountry(country);
        PaymentModel savedPayment = paymentService.createPayment(paymentModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPayment);
    }

    public static <T> List<T> readCSV(Class<T> clazz, InputStream stream) throws IOException {
        CsvSchema schema = mapper.schemaFor(clazz).withHeader().withColumnReordering(true);
        ObjectReader reader = mapper.readerFor(clazz).with(schema);
        return reader.<T>readValues(stream).readAll();
    }

    @PostMapping(value = "/payment-files")
    public ResponseEntity<List<PaymentModel>> bulkSavePayment(HttpServletRequest request, @RequestParam("file") MultipartFile file) {
        String country;
        List<PaymentModel> finalSavedPaymentList = new java.util.ArrayList<>(List.of());

        country = request.getHeader("X-FORWARDED-FOR");
        if (country == null) {
            country = request.getRemoteAddr();
        }

        if(!file.getContentType().equals("text/csv")){
            return ResponseEntity.badRequest().build();
        }

        try {
            AtomicInteger paymentCounter = new AtomicInteger(1);
            IbanConstraintValidator ibanConstraintValidator = new IbanConstraintValidator();
            String finalCountry = country;
            List<PaymentModel> finalSavedPaymentList1 = finalSavedPaymentList;
            readCSV(PaymentModel.class,file.getInputStream()).forEach((paymentModel) -> {
                if(!ibanConstraintValidator.isIbanValid(paymentModel.getDebtorIban())){
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("At Row %s Invalid Iban", paymentCounter));
                }
                if(paymentModel.getAmount() <1){
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Amount Should be more than 0");
                }

                paymentModel.setCountry(finalCountry);
                finalSavedPaymentList1.add(paymentModel);
                paymentCounter.getAndIncrement();
            });
            finalSavedPaymentList = paymentRepository.saveAll(finalSavedPaymentList1);
        } catch (Exception ex) {
            System.out.println("An error occurred while processing the CSV file.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Error in Parsing CSV",ex);

        }

       return ResponseEntity.ok(finalSavedPaymentList);
    }

    @GetMapping(value = "/payments")
    public ResponseEntity<List<PaymentModel>> getPayments(@RequestParam(required = false) String debtorIban){
        List<PaymentModel> paymentModels = paymentService.listPayments(debtorIban);
        return ResponseEntity.ok(paymentModels);
    }

}
