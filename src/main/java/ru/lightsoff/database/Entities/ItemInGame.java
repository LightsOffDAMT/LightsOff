package ru.lightsoff.database.Entities;

import java.awt.*;

public class ItemInGame {
    private Long id;
    private Point position;

    public ItemInGame(){
        super();
    }

    public Long getId() {
        return id;
    }

    public Point getPosition() {
        return position;
    }

    public ItemInGame withId(Long id){
        this.id = id;
        return this;
    }

    public ItemInGame withPosition(Point position){
        this.position = position;
        return this;
    }
}
