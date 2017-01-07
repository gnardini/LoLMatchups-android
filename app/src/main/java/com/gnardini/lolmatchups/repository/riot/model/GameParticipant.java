package com.gnardini.lolmatchups.repository.riot.model;

public class GameParticipant {

    private String summonerName;
    private int championId;
    private int teamId;
    private int summonerId;

    public String getSummonerName() {
        return summonerName;
    }

    public int getChampionId() {
        return championId;
    }

    public int getTeamId() {
        return teamId;
    }

    public int getSummonerId() {
        return summonerId;
    }
}
