package com.example.hellographql.common.validation;

import graphql.execution.ExecutionPath;
import graphql.execution.instrumentation.fieldvalidation.SimpleFieldValidation;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Optional;

@Component
public class PetClinicFieldValidation extends SimpleFieldValidation {

    public PetClinicFieldValidation() {
        addRule(ExecutionPath.parse("/addOwner"), (fieldAndArguments, environment) -> {

            Map<String, Object> map = fieldAndArguments.getArgumentValuesByName();
            Map<String, Object> input = (Map<String, Object>) map.get("input");

            if (StringUtils.isEmpty(input.get("firstName"))) {
                return Optional.of(environment.mkError("firstName不能为空", fieldAndArguments));
            }
            if (StringUtils.isEmpty(input.get("lastName"))) {
                return Optional.of(environment.mkError("lastName不能为空", fieldAndArguments));
            }
            if (StringUtils.isEmpty(input.get("address"))) {
                return Optional.of(environment.mkError("address不能为空", fieldAndArguments));
            }
            if (StringUtils.isEmpty(input.get("city"))) {
                return Optional.of(environment.mkError("city不能为空", fieldAndArguments));
            }
            if (StringUtils.isEmpty(input.get("telephone"))) {
                return Optional.of(environment.mkError("telephone不能为空", fieldAndArguments));
            }
            return Optional.empty();
        });

        addRule(ExecutionPath.parse("/addPet"), (fieldAndArguments, environment) -> {

            Map<String, Object> map = fieldAndArguments.getArgumentValuesByName();
            Map<String, Object> input = (Map<String, Object>) map.get("input");

            if (StringUtils.isEmpty(input.get("name"))) {
                return Optional.of(environment.mkError("name不能为空", fieldAndArguments));
            }
            return Optional.empty();
        });
    }
}
