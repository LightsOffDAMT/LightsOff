package ru.lightsoff.database.builders;

public class QueryBuilder {
    public QueryBuilder(){
        super();
    }

    public SelectQueryBuilder select(){
        return new SelectQueryBuilder();
    }

    public DeleteQueryBuilder delete(){
        return new DeleteQueryBuilder();
    }
}

