package com.example.hellographql.common;

import com.example.hellographql.common.validation.PetClinicFieldValidation;
import com.example.hellographql.common.log.LogInstrumentation;
import graphql.execution.instrumentation.ChainedInstrumentation;
import graphql.execution.instrumentation.Instrumentation;
import graphql.execution.instrumentation.fieldvalidation.FieldValidationInstrumentation;
import graphql.servlet.InstrumentationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PetClinicInstrumentationProvider implements InstrumentationProvider {

    @Autowired
    private PetClinicFieldValidation fieldValidation;

    @Override
    @Bean
    public Instrumentation getInstrumentation() {
        List<Instrumentation> chainedList = new ArrayList<>();
        chainedList.add(new LogInstrumentation());
        chainedList.add(new FieldValidationInstrumentation(fieldValidation));
        ChainedInstrumentation chainedInstrumentation = new ChainedInstrumentation(chainedList);
        return chainedInstrumentation;
    }
}
