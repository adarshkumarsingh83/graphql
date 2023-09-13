package com.espark.adarsh.config;


import com.espark.adarsh.bean.Gender;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.GraphQL;
import graphql.language.StringValue;
import graphql.schema.*;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

import static graphql.schema.idl.RuntimeWiring.newRuntimeWiring;

@Configuration
public class ApplicationConfig {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    GraphqlProcessor graphqlProcessor;

    @Bean
    public GraphQL graphQL() throws IOException {
        SchemaParser schemaParser = new SchemaParser();
        TypeDefinitionRegistry typeDefinitionRegistry = new TypeDefinitionRegistry();
        getSchemaFile().forEach(file -> typeDefinitionRegistry.merge(schemaParser.parse(file)));
        RuntimeWiring runtimeWiring = newRuntimeWiring()
                .scalar(dateScalar())
                .scalar(jsonScalar())
                .type("Query", builder -> builder.dataFetcher("message",
                        graphqlProcessor.getMessageFetcher()))
                .type("Query", builder -> builder.dataFetcher("data",
                        graphqlProcessor.getDataFetcher()))
                .type("Query", builder -> builder.dataFetcher("employee",
                        graphqlProcessor.getEmployeeFetcher()))
                .type("Query", builder -> builder.dataFetcher("employees",
                        graphqlProcessor.getEmployeesFetcher()))
                .build();

        SchemaGenerator schemaGenerator = new SchemaGenerator();
        GraphQLSchema graphQLSchema = schemaGenerator
                .makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);
        return GraphQL.newGraphQL(graphQLSchema)
                .build();
    }

    private List<File> getSchemaFile() throws IOException {
        Resource resource1 = resourceLoader.getResource("classpath:graphql/employee-schema.graphql");
        Resource resource2 = resourceLoader.getResource("classpath:graphql/schema.graphql");
        return List.of(resource1.getFile(), resource2.getFile());
    }


    @Bean
    public GraphQLEnumType graphQLEnumType() {
        return GraphQLEnumType.newEnum()
                .name("Gender")
                .value(Gender.MALE.getValue())
                .value(Gender.FEMALE.getValue())
                .build();
    }

    @Bean
    public GraphQLScalarType dateScalar() {
        return GraphQLScalarType.newScalar()
                .name("Date") //graphql type define in the schema file
                .description("Java 8 LocalDate as scalar.")
                .coercing(new Coercing<LocalDate, String>() {
                    @Override
                    public String serialize(final Object dataFetcherResult) {
                        if (dataFetcherResult instanceof LocalDate) {
                            return dataFetcherResult.toString();
                        } else {
                            throw new CoercingSerializeException("Expected a LocalDate object.");
                        }
                    }

                    @Override
                    public LocalDate parseValue(final Object input) {
                        try {
                            if (input instanceof String) {
                                return LocalDate.parse((String) input);
                            } else {
                                throw new CoercingParseValueException("Expected a String");
                            }
                        } catch (DateTimeParseException e) {
                            throw new CoercingParseValueException(String.format("Not a valid date: '%s'.", input), e
                            );
                        }
                    }

                    @Override
                    public LocalDate parseLiteral(final Object input) {
                        if (input instanceof StringValue) {
                            try {
                                return LocalDate.parse(((StringValue) input).getValue());
                            } catch (DateTimeParseException e) {
                                throw new CoercingParseLiteralException(e);
                            }
                        } else {
                            throw new CoercingParseLiteralException("Expected a StringValue.");
                        }
                    }
                })
                .build();
    }

    @Bean
    public GraphQLScalarType jsonScalar() {

        return GraphQLScalarType.newScalar()
                .name("JSON") //graphql type define in the schema file
                .description("Java MAP as scalar.")
                .coercing(new Coercing<Map<String, String>, String>() {
                    @Override
                    public String serialize(final Object dataFetcherResult) {
                        if (dataFetcherResult instanceof Map) {
                            try {
                                return objectMapper.writeValueAsString(dataFetcherResult);
                            } catch (JsonProcessingException e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            throw new CoercingSerializeException("Expected a Map object.");
                        }
                    }

                    @Override
                    public Map<String, String> parseValue(final Object input) {

                        if (input instanceof StringValue) {
                            try {
                                return objectMapper.readValue(input.toString()
                                        , new TypeReference<Map<String, String>>() {
                                        });


                            } catch (JsonProcessingException e) {
                                throw new CoercingParseLiteralException(e);
                            }
                        } else {
                            throw new CoercingParseValueException("Expected a String");
                        }
                    }


                    @Override
                    public Map<String, String> parseLiteral(final Object input) {
                        if (input instanceof StringValue) {
                            try {
                                return objectMapper.readValue(((StringValue) input).getValue()
                                        , new TypeReference<Map<String, String>>() {
                                        });
                            } catch (JsonProcessingException e) {
                                throw new CoercingParseLiteralException(e);
                            }
                        } else {
                            throw new CoercingParseLiteralException("Expected a StringValue.");
                        }
                    }
                }).build();

    }
}
