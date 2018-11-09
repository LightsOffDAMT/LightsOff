package ru.lightsoff.database.builders;

import java.sql.SQLException;
import java.util.ArrayList;

public class QueryBuilder {
    private String primaryKey = "";
    private ArrayList<String> fields = new ArrayList<>();
    private String ascField = "";
    private String descField = "";
    private String from = "";
    private boolean asc = false;
    private boolean desc = false;

    public QueryBuilder(){
        super();
    }

    public QueryBuilder withPrimaryKey(String primaryKey){
        this.primaryKey = primaryKey;
        return this;
    }

    public QueryBuilder withField(String field){
        this.fields.add(field);
        return this;
    }

    public QueryBuilder withFields(ArrayList<String> fields){
        this.fields.addAll(fields);
        return this;
    }

    public QueryBuilder desc(String field){
        desc = true;
        descField = field;
        return this;
    }

    public QueryBuilder desc(){
        desc = true;
        descField = primaryKey;
        return this;
    }

    public QueryBuilder asc(String field){
        asc = true;
        ascField = field;
        return this;
    }

    public QueryBuilder asc(){
        asc = true;
        ascField = primaryKey;
        return this;
    }

    public QueryBuilder from(String table){
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
