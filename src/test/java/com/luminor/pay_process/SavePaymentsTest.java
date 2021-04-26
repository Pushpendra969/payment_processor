package com.luminor.pay_process;

import com.luminor.pay_process.models.PaymentModel;
import com.luminor.pay_process.services.PaymentService;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
@SpringBootTest
@AutoConfigureMockMvc
public class SavePaymentsTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    PaymentService paymentService;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }


    @Test
    public void postSavePayment(){
        PaymentModel paymentModel = new PaymentModel();
        paymentModel.setAmount(100L);

        paymentModel.setDebtorIban("LT356437978869712537");
        paymentModel.setId(null);
        Mockito.when(paymentService.createPayment(Mockito.any(PaymentModel.class))).thenReturn(paymentModel);

        RestAssuredMockMvc
                .given()
                .contentType(ContentType.JSON)
                .body(paymentModel).when()

                .post("/payments")
                .then()
                .statusCode(201);

    }

    @Test
    public void getAllPayments(){
        List<PaymentModel> paymentModelList = new ArrayList<>();
        PaymentModel paymentModel = new PaymentModel();
        paymentModel.setAmount(100L);
        paymentModel.setDebtorIban("LT356437978869712537");

        paymentModelList.add(paymentModel);

        Mockito.when(paymentService.listPayments(null)).thenReturn(paymentModelList);
        RestAssuredMockMvc
                .given()
                .contentType(ContentType.JSON)
                .when()
                .get("/payments")
                .then()
                .statusCode(200);
    }

    @Test
    public void trySavePaymentWithInvalidIBAN(){
        PaymentModel paymentModel = new PaymentModel();
        paymentModel.setAmount(100L);

        paymentModel.setDebtorIban("CY45002003579876543210987654");
        paymentModel.setId(null);

        RestAssuredMockMvc
                .given()
                .contentType(ContentType.JSON)
                .body(paymentModel).when()

                .post("/payments")
                .then()
                .statusCode(400);

    }

    @Test
    public void trySavePaymentWithInvalidAmount(){
        PaymentModel paymentModel = new PaymentModel();
        paymentModel.setAmount(0L);

        paymentModel.setDebtorIban("LT356437978869712537");
        paymentModel.setId(null);

        RestAssuredMockMvc
                .given()
                .contentType(ContentType.JSON)
                .body(paymentModel).when()

                .post("/payments")
                .then()
                .statusCode(400);

    }
}
