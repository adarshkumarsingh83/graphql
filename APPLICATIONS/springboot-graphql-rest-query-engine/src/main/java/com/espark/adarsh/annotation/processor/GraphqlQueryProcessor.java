package com.espark.adarsh.annotation.processor;


import com.espark.adarsh.annotation.GraphQueries;
import com.espark.adarsh.annotation.GraphQuery;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Map;

@Slf4j
@Component
public class GraphqlQueryProcessor {


    @Autowired
    private ConfigurableApplicationContext context;

    @PostConstruct
    public void init(){
        loadAnnotatedService();
    }

    private void loadAnnotatedService() {
        final Map<String, Object> beans = context.getBeanFactory().getBeansWithAnnotation(GraphQueries.class);
        for (final Map.Entry<String, Object> bean : beans.entrySet()) {
            final Object object = bean.getValue();
            log.info("Beans {}", object.getClass().getName());

            final Field[] fields = object.getClass().getDeclaredFields();
            for (final Field field : fields) {
                final GraphQuery graphQuery = field.getDeclaredAnnotation(GraphQuery.class);
                if (graphQuery != null) {
                    //todo if fields haas @GraphQuery which is nested class
                    log.info("Field Name: {}, GraphQuery Regex: {},  GraphQuery Type: {}",field.getName(),graphQuery.value(),graphQuery.classType());
                }else{
                    // todo add field name in query string
                    log.info("Field Name: {}  ",field.getName());
                }
            }

        }
    }

}
