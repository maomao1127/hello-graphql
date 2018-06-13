package com.example.hellographql.common;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import graphql.servlet.DefaultObjectMapperConfigurer;
import graphql.servlet.GraphQLContext;
import graphql.servlet.GraphQLContextBuilder;
import graphql.servlet.LazyObjectMapperBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

// @Component
public class PetClinicGraphQLContextBuilder implements GraphQLContextBuilder {

    private final static Logger logger = LoggerFactory.getLogger(PetClinicGraphQLContextBuilder.class);

    @Override
    public GraphQLContext build(Optional<HttpServletRequest> req, Optional<HttpServletResponse> resp) {
        String userId = (String) req.get().getAttribute("userId");
        // GraphQLRequest request = null;
        // try {
        //     request = getGraphQLRequestMapper().readValue(req.get().getInputStream());
        // } catch (IOException e) {
        //     logger.error(e.getMessage());
        // }
        //
        // logger.info("request#{\"query\":{}, \"operationName\":{},\"variables\":{}, \"userId\":{}", request.getQuery(), request.getOperationName(), request.getVariables(), userId);
        return new GraphQLContext(req, resp);
    }

    private final LazyObjectMapperBuilder lazyObjectMapperBuilder = new LazyObjectMapperBuilder(new DefaultObjectMapperConfigurer());

    private ObjectReader getGraphQLRequestMapper() {
        // Add object mapper to injection so VariablesDeserializer can access it...
        InjectableValues.Std injectableValues = new InjectableValues.Std();
        injectableValues.addValue(ObjectMapper.class, getMapper());

        return getMapper().reader(injectableValues).forType(GraphQLRequest.class);
    }

    protected ObjectMapper getMapper() {
        return lazyObjectMapperBuilder.getMapper();
    }

    protected static class GraphQLRequest {
        private String query;
        @JsonDeserialize(using = VariablesDeserializer.class)
        private Map<String, Object> variables = new HashMap<>();
        private String operationName;

        public String getQuery() {
            return query;
        }

        public void setQuery(String query) {
            this.query = query;
        }

        public Map<String, Object> getVariables() {
            return variables;
        }

        public void setVariables(Map<String, Object> variables) {
            this.variables = variables;
        }

        public String getOperationName() {
            return operationName;
        }

        public void setOperationName(String operationName) {
            this.operationName = operationName;
        }
    }

    protected static class VariablesDeserializer extends JsonDeserializer<Map<String, Object>> {

        @Override
        public Map<String, Object> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            return deserializeVariablesObject(p.readValueAs(Object.class), (ObjectMapper) ctxt.findInjectableValue(ObjectMapper.class.getName(), null, null));
        }
    }

    private static Map<String, Object> deserializeVariablesObject(Object variables, ObjectMapper mapper) {
        if (variables instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<String, Object> genericVariables = (Map<String, Object>) variables;
            return genericVariables;
        } else if (variables instanceof String) {
            try {
                return mapper.readValue((String) variables, new TypeReference<Map<String, Object>>() {
                });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new RuntimeException("variables should be either an object or a string");
        }
    }
}
