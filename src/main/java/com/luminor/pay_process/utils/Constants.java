package com.luminor.pay_process.utils;

import org.springframework.stereotype.Component;
import java.util.Map;

@Component("Constants")
public class Constants {
    public Map<String, Paths> PathMap = Map.of(
            "/payment", Paths.createPayment
    );
}
