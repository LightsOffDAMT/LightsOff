package ru.lightsoff.database.builders;

import java.util.ArrayList;

/**
 * Provides methods for building CREATE SQL request.
 * Fields are added using {@link #withField()} method, which returns an FieldContructor objects.
 * Fields can than be configured using name(String), type(ColumnType and constraint(String) methods.
 * In the end of field building and() method should be called which returns CreateQueryBuilder object.
 * Building result is returned by calling {@link #toString() toString()} method.
 * @see ColumnType
 */
public class CreateQueryBuilder {
    private ArrayList<String> columns = new ArrayList<>();
    private ArrayList<ColumnType> types = new ArrayList<>();
    private ArrayList<String> constraints = new ArrayList<>();
    private String name = "";

    /**
     * Auxiliary class for field building
     */
    public class FieldConstructor{
        private String columnName = "";
        private ColumnType columnType;
        private String columnConstraint = "";
        FieldConstructor(){
            super();
        }

        /**
         * Set name of the field
         * @param name Name of the field
         * @return FieldConstructor
         */
        public FieldConstructor name(String name){
            columnName = name;
            return this;
        }

        /**
         * Set the type of the field
         * @param type Field type
         * @return FieldConstructor
         * @see ColumnType
         */
        public FieldConstructor type(ColumnType type){
            columnType = type;
            return this;
        }

        /**
         * Set the constraint for the field.
         * Such as PRIMARY KEY, NOT NULL, REFERENCES ...
         * @param constraint Field constraint
         * @return FieldConstructor
         */
        public FieldConstructor constraint(String constraint){
            columnConstraint = constraint;
            return this;
        }

        /**
         * Ends the building of the field
         * @return CreateQueryBuilder
         */
        public CreateQueryBuilder and(){
            columns.add(columnName);
            types.add(columnType);
            constraints.add(columnConstraint);
            return CreateQueryBuilder.this;
        }
    }

    /**
     * Sets the table name.
     * @param name Table name
     * @return CreateQueryBuilder
     */
    public CreateQueryBuilder withName(String name){
        this.name = name;
        return this;
    }

    /**
     * Add a field to the table and start field building phase.
     * @return FieldConstructor
     */
    public FieldConstructor withField(){
        return new FieldConstructor();
    }

    /**
     * Builds the result query.
     * @return String as a result query
     */
    @Override
    public String toString() {
        String query = "CREATE TABLE ";
        query += name + " (\n";
        for(int i = 0; i < columns.size(); i++){
            query += "\t" + columns.get(i) + "\t" + types.get(i);
            if(constraints.get(i).length() > 0)
                query += "\t" + constraints.get(i);
            if(i == columns.size() - 1)
                query += "\n";
            else
                query += ",\n";
        }
        query += ");";
        return query;
    }
}
