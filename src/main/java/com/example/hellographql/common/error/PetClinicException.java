package com.example.hellographql.common.error;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.List;

public class PetClinicException extends RuntimeException implements GraphQLError {

    private final Integer code;
    private final String message;

    public PetClinicException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    // @Override
    // public Map<String, Object> getExtensions() {
    //     Map<String, Object> customAttributes = new LinkedHashMap<>();
    //     customAttributes.put("code", this.code);
    //     customAttributes.put("message", this.message);
    //     return customAttributes;
    // }

    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.DataFetchingException;
    }

    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
