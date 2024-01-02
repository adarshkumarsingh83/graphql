package com.espark.adarsh.annotation;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class QueryNode {

    boolean isRoot = false;
    String query;
    Map<String, QueryNode> child = new HashMap<>();


    public QueryNode(boolean isRoot) {
        this.isRoot = isRoot;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public void setChild(String colName, QueryNode child) {
        this.child.put(colName, child);
    }

    public boolean isRoot() {
        return isRoot;
    }

    public void setRoot(boolean root) {
        isRoot = root;
    }

    public QueryNode getChild(String colName) {
        if (child.containsKey(colName)) {
            return child.get(colName);
        }
        return null;
    }

    @Override
    public String toString() {
        return "QueryNode{" +
                "isRoot=" + isRoot +
                ", query='" + query + '\'' +
                ", child=" + child.entrySet().stream().map(entry-> {
                    return entry.getKey()+ " "+entry.getValue(); })
                .collect(Collectors.joining())+
                '}';
    }
}
