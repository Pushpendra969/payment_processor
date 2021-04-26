package com.luminor.pay_process.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Component
public class PaymentServiceInterceptorAppConfig extends WebMvcConfigurerAdapter {
    @Autowired
    PaymentServiceInterceptor paymentServiceInterceptor;

    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(paymentServiceInterceptor);
    }
}
