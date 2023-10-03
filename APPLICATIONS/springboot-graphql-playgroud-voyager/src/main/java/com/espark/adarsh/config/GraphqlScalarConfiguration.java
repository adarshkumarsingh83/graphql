package com.espark.adarsh.config;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.GraphQLScalarType;
import graphql.schema.idl.RuntimeWiring;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.regex.Pattern;

@Configuration
public class GraphqlScalarConfiguration {


    @Bean
    public RuntimeWiring runtimeWiring() {
      return   RuntimeWiring.newRuntimeWiring()
                .scalar(dateScalar())
                .scalar(jsonScalar())
                .scalar(emailScalar())
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
        ObjectMapper objectMapper = new ObjectMapper();
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

    @Bean
    public GraphQLScalarType emailScalar() {
        return GraphQLScalarType.newScalar()
                .name("Email") //graphql type define in the schema file
                .description("Email as scalar.")
                .coercing(new Coercing<Object, String>() {
                    @Override
                    public String serialize(final Object dataFetcherResult) {
                        if (dataFetcherResult instanceof String) {
                            return serializeEmail(dataFetcherResult);
                        } else {
                            throw new CoercingSerializeException("Expected a Email object.");
                        }
                    }

                    @Override
                    public Object parseValue(final Object input) {
                        try {
                            if (input instanceof String) {
                                return parseEmailFromAstLiteral(input);
                            } else {
                                throw new CoercingParseValueException("Expected a String");
                            }
                        } catch (DateTimeParseException e) {
                            throw new CoercingParseValueException(String.format("Not a valid date: '%s'.", input), e
                            );
                        }
                    }

                    @Override
                    public Object parseLiteral(final Object input) {
                        if (input instanceof StringValue) {
                            try {
                                return parseEmailFromAstLiteral(input);
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

    private static boolean looksLikeAnEmailAddress(String possibleEmailValue) {
        // ps.  I am not trying to replicate RFC-3696 clearly
        return Pattern.matches( "^([\\w-\\.]+){1,64}@([\\w&&[^_]]+){2,255}.[a-z]{2,}$", possibleEmailValue);
    }

    private static String serializeEmail(Object dataFetcherResult) {
        String possibleEmailValue = String.valueOf(dataFetcherResult);
        if (looksLikeAnEmailAddress(possibleEmailValue)) {
            return possibleEmailValue;
        } else {
            throw new CoercingSerializeException("Unable to serialize " + possibleEmailValue + " as an email address");
        }
    }

    private static Object parseEmailFromVariable(Object input) {
        if (input instanceof String) {
            String possibleEmailValue = input.toString();
            if (looksLikeAnEmailAddress(possibleEmailValue)) {
                return possibleEmailValue;
            }
        }
        throw new CoercingParseValueException("Unable to parse variable value " + input + " as an email address");
    }

    private static Object parseEmailFromAstLiteral(Object input) {
        if (input instanceof StringValue) {
            String possibleEmailValue = ((StringValue) input).getValue();
            if (looksLikeAnEmailAddress(possibleEmailValue)) {
                return possibleEmailValue;
            }
        }
        throw new CoercingParseLiteralException(
                "Value is not any email address : '" + String.valueOf(input) + "'"
        );
    }

}
