package ru.lightsoff.database.interfacesDAO.QueryObjects;

import java.util.ArrayList;

public interface AbstractQueryObject<T> {
    ArrayList<String> getFields();
    String getPrimaryKey();
}
