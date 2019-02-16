package ru.lightsoff.database.Entities;

import java.awt.*;

/**
 * ItemInGame entity
 */
public class ItemInGame {
    private Long id;
    private Long itemID;
    private Point position;

    public ItemInGame(){
        super();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setItemID(Long itemID) {
        this.itemID = itemID;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public Long getId() {
        return id;
    }

    public Point getPosition() {
        return position;
    }

    public Long getItemID() {
        return itemID;
    }

    public ItemInGame withId(Long id){
        this.id = id;
        return this;
    }

    public ItemInGame withPosition(Point position){
        this.position = position;
        return this;
    }

    public ItemInGame withItemId(Long id){
        this.itemID = id;
        return this;
    }


}
