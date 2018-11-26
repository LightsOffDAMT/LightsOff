package ru.lightsoff.database.DAO.QueryObjects;

import reactor.core.publisher.Mono;

/**
 * A wrapper around query response with additional information about query
 * @param <T> Class of entity corresponding to DAO
 */
public class QueryResponse<T> {
    private String status = "";
    private long time = 0;
    private Mono<T> data;
    private T rawData;

    /**
     * Get status of the query response
     * @return String
     */
    public String getStatus() {
        return status;
    }

    /**
     * Get execution time of the query
     * @return Lonf
     */
    public Long getTime() {
        return time;
    }

    /**
     * Get data from query response
     * @return Mono<T>
     */
    public Mono<T> getData() {
        return data;
    }

    /**
     * Configure queryResponse with specified status
     * @param status Status of queryResponse
     * @return QueryResponse<T>
     */
    public QueryResponse<T> withStatus(String status){
        this.status = status;
        return this;
    }

    /**
     * Configure queryResponse with execution time of the query
     * @param time Start time
     * @return QueryResponse<T>
     */
    public QueryResponse<T> withTime(long time){
        this.time = System.currentTimeMillis() - time;
        return this;
    }

    /**
     * Configure queryResponse with response data
     * @param data Response data
     * @return QueryResponse<T>
     */
    public QueryResponse<T> withData(Mono<T> data){
        this.data = data;
        return this;
    }

    /**
     * Configure queryResponse with synchronous response data
     * @param data Response data
     * @return QueryResponse<T>
     */
    public QueryResponse<T> withRawData(T data){
        this.rawData = data;
        return this;
    }

    public T getRawData() {
        return rawData;
    }
}
