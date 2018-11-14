package ru.lightsoff.database.DAO.QueryObjects;

public class QueryResponse<T> {
    private String status = "";
    private long time = 0;
    private T data;

    public String getStatus() {
        return status;
    }

    public Long getTime() {
        return time;
    }

    public T getData() {
        return data;
    }

    public QueryResponse<T> withStatus(String status){
        this.status = status;
        return this;
    }

    public QueryResponse<T> withTime(long time){
        this.time = time;
        return this;
    }

    public QueryResponse<T> withData(T data){
        this.data = data;
        return this;
    }
}
