package ru.lightsoff.database.builders;

public enum ColumnType {
    TEXT("text"), DATE("date"), INTEGER("integer"), INTERVAL("interval"), SERIAL("serial");
    String string = "";

    ColumnType(String string){
        this.string = string;
    }

    @Override
    public String toString() {
        return string;
    }
}
