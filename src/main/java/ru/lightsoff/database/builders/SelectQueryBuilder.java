package ru.lightsoff.database.builders;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

enum Join{
    INNER(0), LEFT(1), RIGHT(2), FULL(3);

    private int id;
    private String sql;

    Join(int id){
        this.id = id;
        switch (id){
            case 0:
                sql = "INNER JOIN";
                break;
            case 1:
                sql = "LEFT OUTER JOIN";
                break;
            case 2:                sql = "RIGHT OUTER JOIN";
                break;
            case 3:
                sql = "FULL OUTER JOIN";
                break;
        }
    }

    public int id(){
        return id;
    }

    public String sql(){
        return sql;
    }
}

/**
 * Provides methods for building SELECT SQL request.
 * Every method except {@link #toString() toString()} returns an object of SelectQueryBuilder for method chaining.
 * Note: {@link SelectQueryBuilder is mutable}
 * Building result is returned by calling {@link #toString() toString()} method.
 */
public class SelectQueryBuilder {

    private String primaryKey = "";
    private ArrayList<String> fields = new ArrayList<>();
    private String ascField = "";
    private String descField = "";
    private String from = "";
    private boolean selectAll = false;
    private boolean asc = false;
    private boolean desc = false;
    private String where = "";
    private String join = "";
    private String joinOn = "";
    private Boolean[] joinType = Collections.nCopies(4, false).toArray(new Boolean[4]);

    public SelectQueryBuilder(){
        super();
    }

    /**
     * Define primaryKey and add it to the query.
     * @param primaryKey Name of the primary key
     * @return SelectQueryBuilder
     */
    public SelectQueryBuilder withPrimaryKey(String primaryKey){
        this.primaryKey = primaryKey;
        return this;
    }

    /**
     * Add field to the query.
     * @param field Name of the field
     * @return SelectQueryBuilder
     */
    public SelectQueryBuilder withField(String field){
        this.fields.add(field);
        return this;
    }

    /**
     * Add multiple fields as an ArrayList of Strings.
     * @param fields An ArrayList of Strings
     * @return SelectQueryBuilder
     */
    public SelectQueryBuilder withFields(ArrayList<String> fields){
        this.fields.addAll(fields);
        return this;
    }

    /**
     * Set ORDER BY specific field DESC. Field should be added using {@link #withField(String) withField()} or {@link #withFields(ArrayList) withFields()} method.
     * @param field Field to order by
     * @return SelectQueryBuilder
     */
    public SelectQueryBuilder desc(String field){
        desc = true;
        descField = field;
        return this;
    }

    /**
     * Set ORDER BY primary key DESC.
     * Primary key should be added using {@link #withPrimaryKey(String) withPrimaryKey()} method.
     * @return SelectQueryBuilder
     */
    public SelectQueryBuilder desc(){
        if(primaryKey.equals(""))
            return this;
        desc = true;
        descField = primaryKey;
        return this;
    }

    /**
     * Set ORDER BY specific field ASC. Field should be added using withField() or withFields() methods
     * @param field Field to order by
     * @return SelectQueryBuilder
     */
    public SelectQueryBuilder asc(String field){
        asc = true;
        ascField = field;
        return this;
    }

    /**
     * Set ORDER BY primary key ASC.
     * Primary key should be added using {@link #withPrimaryKey(String) withPrimaryKey()} method.
     * @return SelectQueryBuilder
     */
    public SelectQueryBuilder asc(){
        if(primaryKey.equals(""))
            return this;
        asc = true;
        ascField = primaryKey;
        return this;
    }

    /**
     * Set the table to retrieve rows from.
     * @param table Table to retrieve rows from.
     * @return SelectQueryBuilder
     */
    public SelectQueryBuilder from(String table){
        from = table;
        return this;
    }

    /**
     * Select all fields in table.
     */
    public SelectQueryBuilder all(){
        selectAll = true;
        return this;
    }

    /**
     * Builds the result query.
     * @return String as a result query
     */
    @Override
    public String toString(){
        try{
            validation();
        } catch (SQLException e){
            e.printStackTrace();
            return "Error";
        }
        String query = "SELECT";
        if(selectAll){
            query += " *";
        } else {
            if(primaryKey.length() > 0)
                query += " " + primaryKey + ",";
            for(String it: fields)
                query += " " + it + ",";
            query = query.substring(0, query.length() - 1);
        }
        query += " FROM " + from;
        if(join.length() > 0){
            String currentJoin = "";
            for (int i = 0; i < joinType.length; i++){
                if(joinType[i]){
                    currentJoin = Join.values()[i].sql();
                    break;
                }
            }
            query += " " + currentJoin + " " + join;
            query += " ON " + joinOn;
        }
        if(where.length() > 0)
            query += " WHERE " + where;
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

    public SelectQueryBuilder where(String pattern, String ... args){
        String buffer = pattern.replace("$", "%s");
        where = String.format(buffer, args);
        return this;
    }

    public SelectQueryBuilder innerJoin(String table){
        join = table;
        joinType[Join.INNER.id()] =  true;
        return this;
    }
    public SelectQueryBuilder leftJoin(String table){
        join = table;
        joinType[Join.LEFT.id()] =  true;
        return this;
    }
    public SelectQueryBuilder rightJoin(String table){
        join = table;
        joinType[Join.RIGHT.id()] =  true;
        return this;
    }
    public SelectQueryBuilder fullJoin(String table){
        join = table;
        joinType[Join.FULL.id()] =  true;
        return this;
    }

    public SelectQueryBuilder joinOn(String condition){
        joinOn = condition;
        return this;
    }

    private boolean isFieldPresent(String key){
        if(primaryKey.equals(key))
            return true;
        for(String it: fields)
           if(it.equals(key))
               return true;
        return false;
    }

    private void validation() throws SQLException{
        if(!selectAll && fields.size() == 0)
            throw new SQLException("Choose fields to select");
        if(asc && !isFieldPresent(ascField))
            throw new SQLException("Field" + ascField + " - not found");
        if(desc && !isFieldPresent(descField))
            throw new SQLException("Field" + descField + " - not found");
        if(from.equals(""))
            throw new SQLException("Missing FROM table");
        if(from.contains(";"))
            throw new SQLException();
        if(primaryKey.contains(";"))
            throw new SQLException();
        if(ascField.contains(";"))
            throw new SQLException();
        if(descField.contains(";"))
            throw new SQLException();
        if(where.contains(";"))
            throw new SQLException();
        if(join.contains(";"))
            throw new SQLException();
        if(joinOn.contains(";"))
            throw new SQLException();
        long joined = Stream.of(joinType).filter(val -> val).count();
        if(joined > 1)
            throw new SQLException();
        else
            if(joined == 1 && (joinOn.length() == 0 || join.length() == 0))
                throw new SQLException();
        for(String it: fields)
            if(it.contains(";"))
                throw new SQLException();
    }
}
