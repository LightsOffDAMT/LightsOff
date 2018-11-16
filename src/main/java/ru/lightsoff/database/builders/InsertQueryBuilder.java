package ru.lightsoff.database.builders;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Provides methods for building INSERT SQL request.
 * Rows with values are added using {@link #withRow()} method, which returns an FieldContructor objects.
 * Fields can than be configured using withValue method.
 * In the end of field building and() method should be called which returns InsertQueryBuilder object.
 * Building result is returned by calling {@link #toString() toString()} method.
 */
public class InsertQueryBuilder {
    private String into = "";
    private ArrayList<String> columns = new ArrayList<>();
    private ArrayList<ArrayList<String>> properties = new ArrayList<>();

    public InsertQueryBuilder(){
        super();
    }

    /**
     * Auxiliary class for field building
     */
    public class FieldConstructor{
        private ArrayList<String> fields = new ArrayList<>();

        /**
         * Adds a value to the VALUES (..) part
         * @param value Value in row
         * @return FieldConstructor
         */
        public FieldConstructor withValue(String value){
            fields.add(value);
            return this;
        }

        /**
         * Ends the building of the row
         * @return CreateQueryBuilder
         */
        public InsertQueryBuilder and(){
            properties.add(fields);
            return InsertQueryBuilder.this;
        }
    }

    /**
     * Sets the table in which to insert
     * @param table Table in which to insert
     * @return
     */
    public InsertQueryBuilder into(String table){
        into = table;
        return this;
    }

    /**
     * Adds a column to the query. INSERT INTO TABLE (...)
     *                                                ^^^ this part
     * @param column Column
     * @return InsertQueryBuilder
     */
    public InsertQueryBuilder withColumn(String column){
        columns.add(column);
        return this;
    }


    /**
     * Add a row to the table and start field building phase.
     * @return FieldConstructor
     */
    public FieldConstructor withRow(){
        return new FieldConstructor();
    }

    /**
     * Builds the result query.
     * @return String as a result query
     */
    @Override
    public String toString() {
        try{
            validate();
        } catch (SQLException e){
            e.printStackTrace();
            return "Error";
        }
        String query = "INSERT INTO ";
        query += into + " ";
        if(columns.size() != 0){
            query += "(";
            for(String it: columns)
                query += it + ", ";
            query = query.substring(0, query.length() - 2);
            query += ") ";
        }
        query += "VALUES ";
        for(ArrayList<String> it: properties){
            query += "(";
            for(String itInner: it){
                query += "\'" + itInner + "\'" + ", ";
            }
            query = query.substring(0, query.length() - 2);
            query += "), ";
        }
        query = query.substring(0, query.length() - 2);
        return query + ";";
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
        if(into.length() == 0)
            throw new SQLException("INTO table is missing");
    }
}
