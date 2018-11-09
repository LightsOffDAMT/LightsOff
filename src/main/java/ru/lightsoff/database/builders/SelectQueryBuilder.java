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
        if(primaryKey.equals(""))
            return this;
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
        if(primaryKey.equals(""))
            return this;
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
            return "Sosat\'";
        }
        String query = "SELECT";
        if(fields.size() == 0 && primaryKey.length() == 0){
            query += " *";
        } else {
            for(String it: fields)
                query += " " + it + ",";
            query = query.substring(0, query.length() - 1);
        }
        query += " FROM " + from;
        if(asc || desc)
            query += " ORDER BY ";
        if (asc)
            query += ascField + " ASC, ";
        if(asc && !desc)
            query = query.substring(0, query.length() - 2);
        if(desc)
            query += descField + " DESC";
        return query + ";";
    }

    private boolean isPresent(String key){
        for(String it: fields)
           if(it.equals(key))
               return true;
        return false;
    }

    private void validation() throws SQLException{
        if(asc && !isPresent(ascField))
            throw new SQLException("Field" + ascField + " - not found");
        if(desc && !isPresent(descField))
            throw new SQLException("Field" + descField + " - not found");
        if(from.equals(""))
            throw new SQLException("Missing FROM table");
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
