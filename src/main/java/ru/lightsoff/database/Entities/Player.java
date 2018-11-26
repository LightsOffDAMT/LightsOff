package ru.lightsoff.database.Entities;

import java.awt.*;
import java.util.ArrayList;

/**
 * Player entity
 */
public class Player {
    private Long id;
    private String name;
    private ArrayList<ArrayList<Integer>> inventory;
    private ArrayList<Integer> stats;
    private Long userID;
    private Point position;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<ArrayList<Integer>> getInventory() {
        return inventory;
    }

    public ArrayList<Integer> getStats() {
        return stats;
    }

    public Long getUserID() {
        return userID;
    }

    public Point getPosition() {
        return position;
    }


    public Player withId(long id) {
        this.id = id;
        return this;
    }

    public Player withName(String name) {
        this.name = name;
        return this;
    }



    public Player withUserID(long userID) {
        this.userID = userID;
        return this;
    }

    public Player withInventory(ArrayList<ArrayList<Integer>> inventory) {
        this.inventory = inventory;
        return this;
    }

    public Player withStats(ArrayList<Integer> stats) {
        this.stats = stats;
        return this;
    }

    public Player withPosition(Point position) {
        this.position = position;
        return this;
    }
}
