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

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setInventory(int[][] inventory) {
        this.inventory = inventory;
    }

    public void setStats(int[] stats) {
        this.stats = stats;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }
}
