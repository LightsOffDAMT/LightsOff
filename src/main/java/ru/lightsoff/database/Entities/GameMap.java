package ru.lightsoff.database.Entities;

import java.util.ArrayList;

public class GameMap {
    private Long id;
    private String name;
    private ArrayList<ArrayList<Integer>> map;

    public GameMap(){
        super();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<ArrayList<Integer>> getMap() {
        return map;
    }

    public GameMap withId(Long id){
        this.id = id;
        return this;
    }

    public GameMap withName(String name){
        this.name = name;
        return this;
    }

    public GameMap withMap(ArrayList<ArrayList<Integer>> map){
        this.map = map;
        return this;
    }
}
