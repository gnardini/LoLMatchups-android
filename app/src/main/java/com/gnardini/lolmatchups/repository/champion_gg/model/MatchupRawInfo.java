package com.gnardini.lolmatchups.repository.champion_gg.model;

import com.gnardini.lolmatchups.model.Role;

public class MatchupRawInfo {

    private int games;
    private double statScore;
    private double winRate;
    private Role role;

    public int getGames() {
        return games;
    }

    public double getStatScore() {
        return statScore;
    }

    public double getWinRate() {
        return winRate;
    }

    public Role getRole() {
        return role;
    }

}
