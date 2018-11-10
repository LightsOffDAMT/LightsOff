package ru.lightsoff.database.builders;

// Check if these returns are even necessary
/**
 * Provides methods to choose which SQL operation to start building.
 */
public class QueryBuilder {
    public QueryBuilder(){
        super();
    }

    /**
     * Start building SELECT query
     * @return SelectQueryBuilder object
     */
    public SelectQueryBuilder select(){
        return new SelectQueryBuilder();
    }

    /**
     * Start building DELETE query
     * @return DeleteQueryBuilder object
     */
    public DeleteQueryBuilder delete(){
        return new DeleteQueryBuilder();
    }

    /**
     * Start building UPDATE query
     * @return DeleteQueryBuilder object
     */
    public UpdateQueryBuilder update(){
        return new UpdateQueryBuilder();
    }
}

