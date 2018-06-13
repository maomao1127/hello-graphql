package com.example.hellographql.common.error.provider;

import com.example.hellographql.common.error.handler.PetClinicDataFetcherExceptionHandler;
import graphql.execution.AsyncExecutionStrategy;
import graphql.execution.AsyncSerialExecutionStrategy;
import graphql.execution.ExecutionStrategy;
import graphql.execution.SubscriptionExecutionStrategy;
import graphql.servlet.ExecutionStrategyProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PetClinicExecutionStrategyProvider implements ExecutionStrategyProvider {

    //TODO: batch策略和ExecutorService策略

    @Autowired
    private PetClinicDataFetcherExceptionHandler petClinicDataFetcherExceptionHandler;


    @Override
    public ExecutionStrategy getQueryExecutionStrategy() {
        return new AsyncExecutionStrategy(petClinicDataFetcherExceptionHandler);
    }

    @Override
    public ExecutionStrategy getMutationExecutionStrategy() {
        return new AsyncSerialExecutionStrategy(petClinicDataFetcherExceptionHandler);
    }

    @Override
    public ExecutionStrategy getSubscriptionExecutionStrategy() {
        return new SubscriptionExecutionStrategy(petClinicDataFetcherExceptionHandler);
    }
}
