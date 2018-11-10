package ru.lightsoff.database.builders;

import java.sql.SQLException;

/**
 * Provides methods for building UPDATE SQL request.
 * Every method except {@link #toString() toString()} returns an object of UpdateQueryBuilder for method chaining.
 * Building result is returned by calling {@link #toString() toString()} method.
 */
public class UpdateQueryBuilder {
    private String from = "";
    private String where = "";
    private String set = "";
    private boolean updateAll = false;


    /**
     * Set table to update rows in.
     * @param table Table to update rows in.
     * @return
     */
    public UpdateQueryBuilder from(String table) {
        from = table;
        return this;
    }

    /**
     * Create SET statement to update rows. Example of usage: where("$ > 10", something);
     * @param pattern String pattern.
     * @param args Field names to be included in place of $.
     */
    public UpdateQueryBuilder set(String pattern, String ... args) {
        String buffer = pattern.replace("$", "%s");
        set = String.format(buffer, args);
        return this;
    }

    /**
     * Set WHERE condition. Example of usage: where("$ > 10", something);
     * @param pattern String pattern.
     * @param args Field names to be included in place of $.
     */
    public UpdateQueryBuilder where(String pattern, String ... args){
        String buffer = pattern.replace("$", "%s");
        where = String.format(buffer, args);
        return this;
    }

    /**
     * Delete all rows in table.
     */
    public UpdateQueryBuilder all(){
        updateAll = true;
        return this;
    }

    @Override
    public String toString() {
        try {
            validation();
        } catch (SQLException e) {
            e.printStackTrace();
            return "Sosat'";
        }
        String query = "UPDATE ";
        query += from;
        query += " SET ";
        query += set;
        if(where.length() > 0)
            query += " WHERE " + where;
        return query + ";";
    }

    private void validation() throws SQLException{
        if(set.contains(";"))
            throw new SQLException();
        if(where.contains(";"))
            throw new SQLException();
        if(from.contains(";"))
            throw new SQLException();
        if(from.length() == 0)
            throw new SQLException("FROM table is missing");
        if(!updateAll && set.length() == 0)
            throw new SQLException("Choose columns to update");
        if(!updateAll && where.length() == 0)
            throw new SQLException("Choose columns in WHERE or call all()");
    }
}

