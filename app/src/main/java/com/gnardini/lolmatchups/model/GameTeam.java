package com.gnardini.lolmatchups.model;

import java.util.Map;

public class GameTeam {

    private Map<Role, InGameChampion> champions;
    private double winRate;

    public GameTeam(Map<Role, InGameChampion> champions, double winRate) {
        this.champions = champions;
        this.winRate = winRate;
    }

    public InGameChampion getRole(Role role) {
        return champions.get(role);
    }

    public double getWinRate() {
        return winRate;
    }
}
