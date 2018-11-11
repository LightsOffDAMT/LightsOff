package ru.lightsoff.database.interfacesDAO.QueryObjects;

public class QueryResponse<T> {
    private String status = "";
    private long time = 0;
    private T data;

    public String getStatus() {
        return null;
    }

    public Long getTime() {
        return null;
    }

    public T getData() {
        return null;
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
