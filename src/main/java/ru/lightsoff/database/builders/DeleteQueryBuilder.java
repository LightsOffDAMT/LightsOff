package ru.lightsoff.database.builders;

import com.fasterxml.jackson.databind.deser.std.DateDeserializers;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Provides methods for building DELETE SQL request.
 * Every method except {@link #toString() toString()} returns an object of DeleteQueryBuilder for method chaining.
 * Building result is returned by calling {@link #toString() toString()} method.
 */
public class DeleteQueryBuilder {
    private String from = "";
    private String where = "";
    private boolean deleteAll = false;

    public DeleteQueryBuilder(){
        super();
    }

    /**
     * Sets table to delete from.
     * @param table
     */
    public DeleteQueryBuilder from(String table){
        from = table;
        return this;
    }


    /**
     * Set WHERE condition. Example of usage: where("$ > 10", something);
     * @param pattern String pattern.
     * @param args Field names to be included in place of $.
     */
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

    /**
     * Delete all rows in table.
     */
    public DeleteQueryBuilder all(){
        deleteAll = true;
        return this;
    }

    /**
     * Builds the result query.
     * @return String as a result query.
     */
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
        if(deleteAll){
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
        if(where.length() == 0 && !deleteAll)
            throw new SQLException("Columns to delete is missing");
    }
}
