package com.mikovic.altma.validation;

import com.mikovic.altma.services.aop.ObscenityFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Component
public class WordValidator implements  ConstraintValidator<ValidWord, String>  {
    @Autowired
    private ObscenityFilter obscenityFilter;



    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(obscenityFilter.containsObscenities(s)){
            return false;
        }else return true;

    }
}
