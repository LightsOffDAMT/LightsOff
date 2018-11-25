package ru.lightsoff.database.DAO.QueryObjects;

import reactor.core.publisher.Mono;

public class QueryResponse<T> {
    private String status = "";
    private long time = 0;
    private Mono<T> data;
    private T rawData;

    public String getStatus() {
        return status;
    }

    public Long getTime() {
        return time;
    }

    public Mono<T> getData() {
        return data;
    }

    public QueryResponse<T> withStatus(String status){
        this.status = status;
        return this;
    }

    public QueryResponse<T> withTime(long time){
        this.time = System.currentTimeMillis() - time;
        return this;
    }

    public QueryResponse<T> withData(Mono<T> data){
        this.data = data;
        return this;
    }

    public QueryResponse<T> withRawData(T data){
        this.rawData = data;
        return this;
    }

    public T getRawData() {
        return rawData;
    }
}
