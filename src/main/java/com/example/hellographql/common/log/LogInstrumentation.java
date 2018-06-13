package com.example.hellographql.common.log;

import graphql.ExecutionResult;
import graphql.execution.instrumentation.InstrumentationContext;
import graphql.execution.instrumentation.NoOpInstrumentation;
import graphql.execution.instrumentation.parameters.InstrumentationExecutionParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogInstrumentation extends NoOpInstrumentation {

    private final static Logger logger = LoggerFactory.getLogger(LogInstrumentation.class);

    @Override
    public InstrumentationContext<ExecutionResult> beginExecution(InstrumentationExecutionParameters parameters) {
        long startNanos = System.currentTimeMillis();
        logger.info("beginExecution");
        logger.info("{}.request#{ \"query\":{}, \"variables\":{}}",
                parameters.getOperation(), parameters.getQuery(), parameters.getVariables());
        return (result, t) -> {
            logger.info("{}.response#{}", parameters.getOperation(), result.toSpecification());
            logger.info("{}.time-cost#:<{}>ms", parameters.getOperation(), (System.currentTimeMillis() - startNanos));
        };
    }
}
