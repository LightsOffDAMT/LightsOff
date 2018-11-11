package ru.lightsoff.database.builders;

import javafx.util.Pair;

import java.util.ArrayList;

public class CreateQueryBuilder {
    private ArrayList<String> columns = new ArrayList<>();
    private ArrayList<ColumnType> types = new ArrayList<>();
    private ArrayList<String> contraints = new ArrayList<>();
    private String name = "";

    public class FieldConstructor{
        private String columnName = "";
        private ColumnType columnType;
        private String columnConstraint = "";
        FieldConstructor(){
            super();
        }

        public FieldConstructor name(String name){
            columnName = name;
            return this;
        }

        public FieldConstructor type(ColumnType type){
            columnType = type;
            return this;
        }

        public FieldConstructor constraint(String constraint){
            columnConstraint = constraint;
            return this;
        }

        public CreateQueryBuilder and(){
            columns.add(columnName);
            types.add(columnType);
            contraints.add(columnConstraint);
            return CreateQueryBuilder.this;
        }
    }

    public CreateQueryBuilder withName(String name){
        this.name = name;
        return this;
    }

    public FieldConstructor withField(){
        return new FieldConstructor();
    }

    @Override
    public String toString() {
        String query = "CREATE TABLE ";
        query += name + " (\n";
        for(int i = 0; i < columns.size(); i++){
            query += "\t" + columns.get(i) + "\t" + types.get(i);
            if(contraints.get(i).length() > 0)
                query += "\t" + contraints.get(i);
            if(i == columns.size() - 1)
                query += "\n";
            else
                query += ",\n";
        }
        query += ");";
        return query;
    }
}
