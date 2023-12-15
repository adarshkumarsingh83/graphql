package com.espark.adarsh.annotation.processor;


import com.espark.adarsh.annotation.GraphRootQuery;
import com.espark.adarsh.annotation.GraphQuery;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class GraphqlQueryProcessor {


    @Autowired
    private ConfigurableApplicationContext context;


     Map<String,String> subQueryMap = new HashMap<>();

    @PostConstruct
    public void init() {
        loadAnnotatedService();
    }

    private void loadAnnotatedService() {
        final Map<String, Object> beans = context.getBeanFactory().getBeansWithAnnotation(GraphRootQuery.class);
        for (final Map.Entry<String, Object> bean : beans.entrySet()) {
            final Object object = bean.getValue();
            log.info("Beans {}", object.getClass().getName());
            final Field[] fields = object.getClass().getDeclaredFields();
            String query = processForClassFields(fields);
            subQueryMap.put(bean.getKey(),query);
        }
        subQueryMap.entrySet().forEach(stringStringEntry -> {
            log.info("regex {} query {}",stringStringEntry.getKey(),stringStringEntry.getValue());
        });
    }

    public String processForClassFields(Field[] fields) {
        String query=" { ";
        if (fields != null && fields.length > 0) {
            for (final Field field : fields) {
                final GraphQuery graphQuery = field.getDeclaredAnnotation(GraphQuery.class);
                if (graphQuery != null) {
                    //todo if fields haas @GraphQuery which is nested class
                    GraphQueryData graphQueryData = new GraphQueryData(field.getName(), graphQuery.value(), graphQuery.classType());
                    log.debug("Pushing to Stack Field Name: {}, GraphQuery Regex: {},  GraphQuery Type: {}", graphQueryData.getFieldName(), graphQueryData.getRegex(), graphQueryData.getClazz().getName());
                    Field[] declaredFields = graphQueryData.getClazz().getDeclaredFields();
                    String subQuery = processForClassFields(declaredFields);
                    log.debug("regex {} query{}",graphQueryData.getRegex(),subQuery);
                    subQueryMap.put(graphQueryData.getRegex(),subQuery);
                    query+= graphQueryData.getFieldName()+"  " +subQuery;
                } else {
                    log.debug("Field Name: {}  ", field.getName());
                    query+=" "+field.getName()+" ";
                }
            }
        }

        return query+=" } ";
    }

    @lombok.Data
    @AllArgsConstructor
    class GraphQueryData {
        String fieldName;
        String regex;
        Class clazz;

        @Override
        public String toString() {
            return "GraphQueryData{" +
                    "fieldName='" + fieldName + '\'' +
                    ", regex='" + regex + '\'' +
                    ", clazz=" + clazz.getName() +
                    '}';
        }
    }
}
