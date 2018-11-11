package ru.lightsoff.database.builders;

import java.sql.SQLException;
import java.util.ArrayList;

public class InsertQueryBuilder {
    private String into = "";
    private ArrayList<String> columns = new ArrayList<>();
    private ArrayList<ArrayList<String>> properties = new ArrayList<>();

    public InsertQueryBuilder(){
        super();
    }

    public class FieldConstructor{
        private ArrayList<String> fields = new ArrayList<>();

        public FieldConstructor with(String value){
            fields.add(value);
            return this;
        }

        public InsertQueryBuilder and(){
            properties.add(fields);
            return InsertQueryBuilder.this;
        }
    }

    public InsertQueryBuilder into(String table){
        into = table;
        return this;
    }

    public InsertQueryBuilder withColumn(String column){
        columns.add(column);
        return this;
    }

    public FieldConstructor withRow(){
        return new FieldConstructor();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    private boolean enoughValues(ArrayList<ArrayList<String>> array){
        if(columns.size() != 0)
            for(ArrayList<String> it: array)
                if(it.size() != columns.size())
                    return false;
        return true;
    }

    private boolean sqlInjectionCheck(){
        if(into.contains(";"))
            return false;
        for(String it: columns)
            if(it.contains(";"))
                return false;
        for(ArrayList<String> it: properties)
            for(String itInner: it)
                if(itInner.contains(";"))
                    return false;
        return true;
    }

    private void validate() throws SQLException{
        if(!enoughValues(properties))
            throw new SQLException("amount of VALUES should be equal to " + columns.size());
        if(!sqlInjectionCheck())
            throw new SQLException();
    }
}