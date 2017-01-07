package com.gnardini.lolmatchups.model;

public class InGameChampion {

    private String name;
    private double winRate;
    private int games;

    public InGameChampion(String name, double winRate, int games) {
        this.name = name;
        this.winRate = winRate;
        this.games = games;
    }

    public String getName() {
        return name;
    }

    public double getWinRate() {
        return winRate;
    }

    public int getGames() {
        return games;
    }
}
