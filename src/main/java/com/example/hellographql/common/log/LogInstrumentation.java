package com.example.hellographql.common.log;

import graphql.ExecutionResult;
import graphql.execution.instrumentation.InstrumentationContext;
import graphql.execution.instrumentation.SimpleInstrumentation;
import graphql.execution.instrumentation.parameters.InstrumentationExecutionParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

public class LogInstrumentation extends SimpleInstrumentation {

    private final static Logger logger = LoggerFactory.getLogger(LogInstrumentation.class);

    @Override
    public InstrumentationContext<ExecutionResult> beginExecution(InstrumentationExecutionParameters parameters) {
        long startNanos = System.currentTimeMillis();
        logger.info("beginExecution");
        logger.info("{}.request#{ \"query\":{}, \"variables\":{}}",
                parameters.getOperation(), parameters.getQuery(), parameters.getVariables());

        return new InstrumentationContext<ExecutionResult>() {
            @Override
            public void onDispatched(CompletableFuture<ExecutionResult> completableFuture) {

            }

            @Override
            public void onCompleted(ExecutionResult executionResult, Throwable throwable) {
                logger.info("{}.response#{}", parameters.getOperation(), executionResult.toSpecification());
                logger.info("{}.time-cost#:<{}>ms", parameters.getOperation(), (System.currentTimeMillis() - startNanos));
            }
        };
    }
}
