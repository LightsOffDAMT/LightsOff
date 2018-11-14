package ru.lightsoff.database.builders;

// Check if these returns are even necessary

/**
 * Provides methods to choose which SQL operation to start building.
 */
public class QueryFactory {
    public QueryFactory(){
        super();
    }

    /**
     * Start building SELECT query
     * @return SelectQueryBuilder object
     */
    public static SelectQueryBuilder select(){
        return new SelectQueryBuilder();
    }

    /**
     * Start building DELETE query
     * @return DeleteQueryBuilder object
     */
    public static DeleteQueryBuilder delete(){
        return new DeleteQueryBuilder();
    }

    /**
     * Start building UPDATE query
     * @return DeleteQueryBuilder object
     */
    public static UpdateQueryBuilder update(){
        return new UpdateQueryBuilder();
    }
    /**
     * Start building CREATE query
     * @return CreateQueryBuilder object
     */
    public static CreateQueryBuilder create(){
        return new CreateQueryBuilder();
    }
    /**
     * Start building INSERT query
     * @return InsertQueryBuilder object
     */
    public static InsertQueryBuilder insert(){
        return new InsertQueryBuilder();
    }
}

