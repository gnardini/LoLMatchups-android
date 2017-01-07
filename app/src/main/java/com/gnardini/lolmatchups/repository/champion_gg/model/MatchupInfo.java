package com.gnardini.lolmatchups.repository.champion_gg.model;

public class MatchupInfo {

    private double champion1WinRate;
    private int gamesPlayed;

    public MatchupInfo(double champion1WinRate, int gamesPlayed) {
        this.champion1WinRate = champion1WinRate;
        this.gamesPlayed = gamesPlayed;
    }

    public double getChampion1WinRate() {
        return champion1WinRate;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

}
