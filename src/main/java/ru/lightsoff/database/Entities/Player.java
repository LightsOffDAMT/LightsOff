package ru.lightsoff.database.Entities;

public class Player {
    private long id;
    private String name;
    private int[][] inventory;
    private int[] stats;
    private long userID;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int[][] getInventory() {
        return inventory;
    }

    public int[] getStats() {
        return stats;
    }

    public long getUserID() {
        return userID;
    }

    public int getPosiiton() {
        return posiiton;
    }

    private int posiiton;
}
