package com.espark.adarsh.config;


import com.espark.adarsh.processer.EmployeeProcessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
public class GraphQlConfig {


    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    EmployeeProcessor employeeProcessor;

    @Autowired
    ResourceLoader resourceLoader;




    @Bean
    public GraphQL graphqlQl() throws IOException {
        SchemaParser schemaParser = new SchemaParser();
        TypeDefinitionRegistry typeDefinitionRegistry = new TypeDefinitionRegistry();
        getSchemaFile().forEach(file -> typeDefinitionRegistry.merge(schemaParser.parse(file)));
        RuntimeWiring runtimeWiring = newRuntimeWiring()
                .scalar(dateScalar())
                .scalar(jsonScalar())
                .type("Query", builder ->
                        builder.dataFetcher("getAllEmployee", employeeProcessor.getAllEmployee()))
                .type("Query", builder ->
                        builder.dataFetcher("getEmployee", employeeProcessor.getEmployee()))
                .type("Query", builder ->
                        builder.dataFetcher("employeesFilter", employeeProcessor.employeesFilter()))
                .type("Query", builder ->
                        builder.dataFetcher("saveEmployee", employeeProcessor.saveEmployee()))
                .type("Query", builder ->
                        builder.dataFetcher("updateEmployee", employeeProcessor.updateEmployee()))
                .type("Query", builder ->
                        builder.dataFetcher("removeEmployee", employeeProcessor.removeEmployee()))
                .type("Query", builder ->
                        builder.dataFetcher("updatePartialEmployee", employeeProcessor.updatePartialEmployee()))
                .build();
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);
        GraphQL build = GraphQL.newGraphQL(graphQLSchema).build();
        return build;
    }


    private List<File> getSchemaFile() throws IOException {
        Resource resource1 = resourceLoader.getResource("classpath:graphql/schema.graphqls");
        return List.of(resource1.getFile());
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
