package com.luminor.pay_process.components;

import com.luminor.pay_process.utils.Constants;
import com.luminor.pay_process.utils.Paths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class PaymentServiceInterceptor implements HandlerInterceptor {
    @Autowired
    Constants constants;
    public boolean preHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getRequestURI().substring(request.getContextPath().length());
        if(request.getMethod().equals("POST") && constants.PathMap.get(path) == Paths.createPayment || constants.PathMap.get(path) == Paths.bulkCreatePayment){
            System.out.println("Print");
        }
        return true;
    }
}
