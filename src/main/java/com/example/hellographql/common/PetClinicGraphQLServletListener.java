package com.example.hellographql.common;

import graphql.GraphQLError;
import graphql.servlet.GraphQLContext;
import graphql.servlet.GraphQLServletListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

// @Component
public class PetClinicGraphQLServletListener implements GraphQLServletListener {

    private final static Logger logger = LoggerFactory.getLogger(PetClinicGraphQLServletListener.class);

    @Override
    public RequestCallback onRequest(HttpServletRequest request, HttpServletResponse response) {
        return new RequestCallback() {
            @Override
            public void onSuccess(HttpServletRequest request, HttpServletResponse response) {

            }

            @Override
            public void onError(HttpServletRequest request, HttpServletResponse response, Throwable throwable) {

            }

            @Override
            public void onFinally(HttpServletRequest request, HttpServletResponse response) {

            }
        };
    }

    @Override
    public OperationCallback onOperation(GraphQLContext context, String operationName, String query, Map<String, Object> variables) {
        return new OperationCallback() {
            @Override
            public void onSuccess(GraphQLContext context, String operationName, String query, Map<String, Object> variables, Object data) {
                logger.info("{}.response#{\"operationName\":{}, \"query\":{}, \"variables\":{}, \"data\":{}}", operationName, operationName, query, variables, data);
            }

            @Override
            public void onError(GraphQLContext context, String operationName, String query, Map<String, Object> variables, Object data, List<GraphQLError> errors) {
                logger.info("{}.response#{\"operationName\":{}, \"query\":{}, \"variables\":{}, \"data\":{},\"errors\":{}}", operationName, operationName, query, variables, data, errors.toString());
            }

            @Override
            public void onFinally(GraphQLContext context, String operationName, String query, Map<String, Object> variables, Object data) {

            }
        };
    }
}
