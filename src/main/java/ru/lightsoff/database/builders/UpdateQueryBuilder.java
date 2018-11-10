package ru.lightsoff.database.builders;

import java.sql.SQLException;

public class UpdateQueryBuilder {
    private String from = "";
    private String where = "";
    private String set = "";
    private boolean updateAll = false;

    public UpdateQueryBuilder from(String table) {
        from = table;
        return this;
    }

    public UpdateQueryBuilder set(String pattern, String ... args) {
        String buffer = pattern.replace("$", "%s");
        set = String.format(buffer, args);
        return this;
    }

    public UpdateQueryBuilder where(String pattern, String ... args){
        String buffer = pattern.replace("$", "%s");
        where = String.format(buffer, args);
        return this;
    }

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

