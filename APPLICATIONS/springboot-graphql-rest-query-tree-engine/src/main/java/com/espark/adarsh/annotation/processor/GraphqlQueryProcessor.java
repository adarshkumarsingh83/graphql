package com.espark.adarsh.annotation.processor;


import com.espark.adarsh.annotation.GraphRootQuery;
import com.espark.adarsh.annotation.GraphQuery;
import com.espark.adarsh.annotation.QueryNode;
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


    final static String DOUBLE_START="{*.*}";
    final static String SINGLE_START="{*.*}";

    @Autowired
    private ConfigurableApplicationContext context;


    private Map<String, String> subQueryMap = new HashMap<>();

    @PostConstruct
    public void init() {
        loadAnnotatedService();
    }

    private Map<String, QueryNode> queryNodeTree = new HashMap<>();

    private void loadAnnotatedService() {
        final Map<String, Object> beans = context.getBeanFactory().getBeansWithAnnotation(GraphRootQuery.class);
        for (final Map.Entry<String, Object> bean : beans.entrySet()) {
            final Object object = bean.getValue();
            log.info("Beans {}", object.getClass().getName());
            final Field[] fields = object.getClass().getDeclaredFields();
            QueryNode root = new QueryNode(true);
            String query = processForClassFields(bean.getKey(), fields, root);
            subQueryMap.put(bean.getKey()+"{*.*}", query);
            queryNodeTree.put(bean.getKey(), root);
            GraphRootQuery graphRootQuery =  bean.getValue().getClass().getAnnotation(GraphRootQuery.class);
            int level = graphRootQuery.level();
            if(level>0){
                while(level>=0){
                    String levelQuery = processTreeForLevel(level,root);
                    subQueryMap.put(bean.getKey()+"{*."+level+"}",levelQuery );
                    level-=1;
                }
            }
        }

        subQueryMap.entrySet().forEach(stringStringEntry -> {
            log.info("regex {} query {}", stringStringEntry.getKey(), stringStringEntry.getValue());
        });

        queryNodeTree.entrySet().forEach(stringQueryNodeEntry -> System.out.println(stringQueryNodeEntry.getKey()
                + " " + stringQueryNodeEntry.getValue()));
    }

    public String processForClassFields(String rootFieldName, Field[] fields, QueryNode node) {
        String queryWithDepth = " { ";
        String queryWithoutDepth = " { ";
        if (fields != null && fields.length > 0) {
            for (final Field field : fields) {
                final GraphQuery graphQuery = field.getDeclaredAnnotation(GraphQuery.class);
                if (graphQuery != null) {
                    GraphQueryData graphQueryData = new GraphQueryData(field.getName(), graphQuery.value(), graphQuery.classType());
                    log.debug("Pushing to Stack Field Name: {}, GraphQuery Regex: {},  GraphQuery Type: {}",
                            graphQueryData.getFieldName(), graphQueryData.getRegex(), graphQueryData.getClazz().getName());
                    Field[] declaredFields = graphQueryData.getClazz().getDeclaredFields();
                    QueryNode root = new QueryNode(false);
                    String subQuery = processForClassFields(graphQueryData.getRegex(), declaredFields, root);
                    log.debug("regex {} queryWithDepth{}", graphQueryData.getRegex(), subQuery);

                    subQueryMap.put(graphQueryData.getRegex()+"{*.*}", graphQuery.value() + subQuery);
                    queryWithDepth += graphQueryData.getFieldName() + "  " + subQuery;
                    node.setQueryWithChild(queryWithDepth);
                    node.setChild(graphQuery.value(), root);
                } else {
                    log.debug("Field Name: {}  ", field.getName());
                    queryWithDepth += " " + field.getName() + " ";
                    queryWithoutDepth += " " + field.getName() + " ";
                }
            }
            queryWithoutDepth += " }";
            subQueryMap.put(rootFieldName+"{*}", rootFieldName + queryWithoutDepth);
            if (node.isRoot()) {
                node.setQuery(queryWithoutDepth);
            } else {
                node.setQuery(rootFieldName + queryWithoutDepth);
            }
        }
        return queryWithDepth += " } ";
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

    public Map<String, String> getSubQueryMap() {
        return subQueryMap;
    }



   private String processTreeForLevel(int level, QueryNode node) {
        if (level > 0 && node != null) {
            Map<String, QueryNode> child = node.getChild();
            if (child != null && !child.isEmpty()) {
                String response = "";
                String data = "";
                for (Map.Entry<String, QueryNode> entry : child.entrySet()) {
                    data = processTreeForLevel(level-1, entry.getValue());
                    if (data != null) {
                        response += " "+data;
                    }
                }
                return node.getQuery().replace("}", response+" }  ");
            }
        }
        return node.getQuery();
    }
}
