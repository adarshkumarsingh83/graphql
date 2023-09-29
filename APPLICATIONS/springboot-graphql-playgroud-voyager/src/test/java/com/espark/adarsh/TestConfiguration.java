package com.espark.adarsh;

import com.espark.adarsh.config.GraphqlScalarConfiguration;
import graphql.schema.GraphQLScalarType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfiguration {

    @Bean
    public GraphQLScalarType jsonType(){
        return  new GraphqlScalarConfiguration().jsonScalar();
    }

    @Bean
    public GraphQLScalarType dateType(){
        return  new GraphqlScalarConfiguration().dateScalar();
    }

}
