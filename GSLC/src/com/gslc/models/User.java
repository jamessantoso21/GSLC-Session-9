package com.gslc.models;

public class User {
    private String nim;
    private String name;
    private int teamId;

    public User(String nim, String name, int teamId) {
        this.nim = nim;
        this.name = name;
        this.teamId = teamId;
    }

    // Getters
    public String getNim() {
        return nim;
    }

    public String getName() {
        return name;
    }

    public int getTeamId() {
        return teamId;
    }

    // Setters
    public void setNim(String nim) {
        this.nim = nim;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }
}
