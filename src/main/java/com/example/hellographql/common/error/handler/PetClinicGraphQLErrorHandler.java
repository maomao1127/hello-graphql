package com.example.hellographql.common.error.handler;

import graphql.ErrorType;
import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.servlet.GraphQLErrorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PetClinicGraphQLErrorHandler implements GraphQLErrorHandler {
    public static final Logger log = LoggerFactory.getLogger(PetClinicGraphQLErrorHandler.class);

    @Override
    public List<GraphQLError> processErrors(List<GraphQLError> errors) {
        final List<GraphQLError> clientErrors = filterGraphQLErrors(errors);
        if (clientErrors.size() < errors.size()) {

            // Some errors were filtered out to hide implementation - put a generic error in place.
            // clientErrors.add(new GenericGraphQLError("Internal Server Error(s) while executing query"));

            errors.stream()
                    .filter(error -> !isClientError(error))
                    .forEach(error -> {
                        if (error instanceof Throwable) {
                            log.error("Error executing query!", (Throwable) error);
                        } else {
                            log.error("Error executing query ({}): {}", error.getClass().getSimpleName(), error.getMessage());
                        }
                    });
        }

        return clientErrors;
    }

    protected List<GraphQLError> filterGraphQLErrors(List<GraphQLError> errors) {
        return errors.stream()
                .filter(this::isClientError)
                .collect(Collectors.toList());
    }

    /**
     * 不管是鉴权失败抛出了AccessDeniedException，还是数据库查询没有找到数据抛出了PetClinicException，在被ExceptionHandler转换成PetClinicError之后，
     * resolver返回的结果就是null了，graphql接着会校验结果是否为空，所以产生了NonNullableFieldWasNullError
     * 我们不希望客户端看到这个error，所以添加过滤条件ErrorType.DataFetchingException.equals(error.getErrorType())将其过滤掉
     **/
    protected boolean isClientError(GraphQLError error) {
        return !(error instanceof ExceptionWhileDataFetching || error instanceof Throwable || ErrorType.DataFetchingException.equals(error.getErrorType()));
    }
}
