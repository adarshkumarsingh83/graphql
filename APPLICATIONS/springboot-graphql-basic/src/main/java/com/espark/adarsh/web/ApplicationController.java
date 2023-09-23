package com.espark.adarsh.web;

import com.espark.adarsh.bean.AcademicClass;
import com.espark.adarsh.bean.Difficulty;
import com.espark.adarsh.bean.Teacher;
import graphql.GraphQLContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.ContextValue;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Controller
public class ApplicationController {

    @QueryMapping
    public List<AcademicClass> schedule(@Argument Teacher teacher,
                                        GraphQLContext graphQLContext) {
        graphQLContext.put("key1","value1");
        log.info("schedule() executed ");
        return List.of(AcademicClass.builder()
                .id(UUID.randomUUID())
                .startAt(LocalDateTime.now())
                .endsAt(LocalDateTime.now().plusHours(2))
                .teacher(Teacher.TEACHER1)
                .difficulty(Difficulty.BEGINNER)
                .build());
    }

    @BatchMapping
    public Callable<Map<AcademicClass, Difficulty>> difficulty(List<AcademicClass> academicClasses,
                                                               @ContextValue String key1,
                                                               GraphQLContext context) {
        log.info("difficulty() executed ");
        log.info(key1);
        String value = context.get("key1");
        log.info(value);


        return () -> {
           return academicClasses.stream()
                    .collect(Collectors.toUnmodifiableMap(
                            Function.identity(),
                            ignore -> Difficulty.BEGINNER
                    ));
        };
    }
}
