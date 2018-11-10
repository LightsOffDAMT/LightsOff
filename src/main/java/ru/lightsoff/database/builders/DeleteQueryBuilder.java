package ru.lightsoff.database.builders;

import com.fasterxml.jackson.databind.deser.std.DateDeserializers;

import java.sql.SQLException;
import java.util.ArrayList;

public class DeleteQueryBuilder {
    private String from = "";
    private String where = "";

    public DeleteQueryBuilder(){
        super();
    }

    public DeleteQueryBuilder from(String table){
        from = table;
        return this;
    }

    public DeleteQueryBuilder where(String pattern, String ... args){
        try{
            String buffer = pattern.replace("$", "%s");
            where = String.format(buffer, args).trim();
        } catch (Exception e){
            e.printStackTrace();
            where = "";
        }
        return this;
    }

    @Override
    public String toString() {
        try{
            validation();
        } catch (SQLException e){
            e.printStackTrace();
            return "Sosat'";
        }
        String query = "DELETE FROM ";
        query += from + " ";
        if(where.length() == 0){
            query += "*;";
            return query;
        }
        query += "WHERE " + where + ";";
        return query;
    }

    private void validation() throws SQLException{
        if(from.equals(""))
            throw new SQLException("FROM table is missing");
        if(from.contains(";"))
            throw new SQLException("Forbidden symbol - \';\' in from()");
        if(where.contains(";"))
            throw new SQLException("Forbidden symbol - \';\' in where()");
    }
}
