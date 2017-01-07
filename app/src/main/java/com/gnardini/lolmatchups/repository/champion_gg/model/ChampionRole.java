package com.gnardini.lolmatchups.repository.champion_gg.model;

import com.gnardini.lolmatchups.model.Role;

public class ChampionRole {

    private int games;
    private double percentPlayed;
    private Role name;

    public int getGames() {
        return games;
    }

    public double getPercentPlayed() {
        return percentPlayed;
    }

    public Role getRole() {
        return name;
    }
}
