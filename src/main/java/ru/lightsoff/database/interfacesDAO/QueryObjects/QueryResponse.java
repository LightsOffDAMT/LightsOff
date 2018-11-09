package ru.lightsoff.database.interfacesDAO.QueryObjects;

public interface QueryResponse<T> {
    String getStatus();
    Long getTime();
    T getData();
}
