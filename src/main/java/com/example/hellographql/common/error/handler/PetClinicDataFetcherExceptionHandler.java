package com.example.hellographql.common.error.handler;


import com.example.hellographql.common.error.PetClinicException;
import com.example.hellographql.common.error.PetClinicGraphQLError;
import graphql.ExceptionWhileDataFetching;
import graphql.execution.DataFetcherExceptionHandler;
import graphql.execution.DataFetcherExceptionHandlerParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * The standard handling of data fetcher error involves placing a {@link ExceptionWhileDataFetching} error
 * into the error collection
 */
@Component
public class PetClinicDataFetcherExceptionHandler implements DataFetcherExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(PetClinicDataFetcherExceptionHandler.class);

    @Override
    public void accept(DataFetcherExceptionHandlerParameters handlerParameters) {
        Throwable exception = handlerParameters.getException();
        // SourceLocation sourceLocation = handlerParameters.getField().getSourceLocation();
        // ExecutionPath path = handlerParameters.getPath();

        // ExceptionWhileDataFetching error = new ExceptionWhileDataFetching(path, exception, sourceLocation);
        PetClinicGraphQLError error = null;
        try {
            if ("AccessDeniedException".equals(exception.getClass().getSimpleName())) {
                error = new PetClinicGraphQLError(99, exception.getMessage());
            } else {
                // 直接使用PetClinicException的话，无法屏蔽掉exception字段
                error = new PetClinicGraphQLError(((PetClinicException) exception).getCode(), exception.getMessage());
            }
        } catch (Exception e) {
            error = new PetClinicGraphQLError(100, "未知的错误");
        }

        handlerParameters.getExecutionContext().addError(error, handlerParameters.getPath());
        log.warn(error.getMessage(), exception);
    }
}
