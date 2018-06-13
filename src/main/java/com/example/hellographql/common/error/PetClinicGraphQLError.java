package com.example.hellographql.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.List;
import java.util.Map;

public class PetClinicGraphQLError implements GraphQLError {

    private final Integer code;
    private final String message;

    public PetClinicGraphQLError(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    @JsonIgnore
    public List<SourceLocation> getLocations() {
        return null;
    }

    @Override
    @JsonIgnore
    public ErrorType getErrorType() {
        return null;
    }

    @Override
    @JsonIgnore
    public List<Object> getPath() {
        return null;
    }

    @Override
    @JsonIgnore
    public Map<String, Object> getExtensions() {
        return null;
    }

    @Override
    public String toString() {
        return "PetClinicGraphQLError{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
