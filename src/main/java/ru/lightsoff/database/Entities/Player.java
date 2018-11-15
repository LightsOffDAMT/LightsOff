package ru.lightsoff.database.Entities;

import java.awt.*;
import java.util.ArrayList;

public class Player {
    private long id;
    private String name;
    private ArrayList<ArrayList<Integer>> inventory;
    private ArrayList<Integer> stats;
    private long userID;
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

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public void setInventory(ArrayList<ArrayList<Integer>> inventory) {
        this.inventory = inventory;
    }

    public void setStats(ArrayList<Integer> stats) {
        this.stats = stats;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }
}
