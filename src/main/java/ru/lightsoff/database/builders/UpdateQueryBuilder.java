package ru.lightsoff.database.builders;

public class UpdateQueryBuilder {
    // Not sure about "from" naming because there is no FROM in the result query
    public UpdateQueryBuilder from(String table) {

        return this;
    }

    public UpdateQueryBuilder set(String pattern, String ... args) {

        return this;
    }

    public UpdateQueryBuilder where(String pattern, String ... args){

        return this;
    }

    public UpdateQueryBuilder all(){

        return this;
    }

    @Override
    public String toString() {

        return "";
    }
}

