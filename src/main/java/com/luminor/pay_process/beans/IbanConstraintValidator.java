package com.luminor.pay_process.beans;

import org.iban4j.*;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class IbanConstraintValidator implements ConstraintValidator<Iban, String> {
    List<String> balticMap = List.of("LT", "LV", "EE");

    public Boolean isBaltic(String s){
        s = s.substring(0,2);
        return balticMap.contains(s);
    }

    public boolean isIbanValid(String s){
        try {
            IbanUtil.validate(s);
            return this.isBaltic(s);

        } catch (IbanFormatException |
                InvalidCheckDigitException |
                UnsupportedCountryException e) {
            return false;
        }
    }
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return this.isIbanValid(s);
    }
}
