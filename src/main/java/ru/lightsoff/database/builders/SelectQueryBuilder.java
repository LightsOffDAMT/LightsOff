package ru.lightsoff.database.builders;

import java.sql.SQLException;
import java.util.ArrayList;

public class SelectQueryBuilder {
    private String primaryKey = "";
    private ArrayList<String> fields = new ArrayList<>();
    private String ascField = "";
    private String descField = "";
    private String from = "";
    private boolean asc = false;
    private boolean desc = false;

    public SelectQueryBuilder(){
        super();
    }

    public SelectQueryBuilder withPrimaryKey(String primaryKey){
        this.primaryKey = primaryKey;
        return this;
    }

    public SelectQueryBuilder withField(String field){
        this.fields.add(field);
        return this;
    }

    public SelectQueryBuilder withFields(ArrayList<String> fields){
        this.fields.addAll(fields);
        return this;
    }

    public SelectQueryBuilder desc(String field){
        desc = true;
        descField = field;
        return this;
    }

    public SelectQueryBuilder desc(){
        desc = true;
        descField = primaryKey;
        return this;
    }

    public SelectQueryBuilder asc(String field){
        asc = true;
        ascField = field;
        return this;
    }

    public SelectQueryBuilder asc(){
        asc = true;
        ascField = primaryKey;
        return this;
    }

    public SelectQueryBuilder from(String table){
        from = table;
        return this;
    }

    public String getRaw(){
        try{
            validation();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    private void validation() throws SQLException{
        if(primaryKey.contains(";"))
            throw new SQLException();
        if(ascField.contains(";"))
            throw new SQLException();
        if(descField.contains(";"))
            throw new SQLException();
        for(String it: fields)
            if(it.contains(";"))
                throw new SQLException();
    }
}
