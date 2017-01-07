package com.gnardini.lolmatchups.model;

public enum RepoError {

    NO_CURRENT_GAME(10, "The summoner is not on an active game."),
    NO_SUMMONER_NAME(20, "There doesn't seem to be a summoner with that name."),
    NO_CHAMPION_GG_ROLES(30, "Looks like we couldn't fetch champion roles information."),
    NO_MATCHUP_FOUND(40, "We couldn't find information on this matchup :("),
    NO_CHAMPION_INFO(50, "We couldn't get data on the champions, sorry!");

    private final String message;
    private final int baseCode;

    RepoError(int baseCode, String message) {
        this.baseCode = baseCode;
        this.message = message;
    }

    public int getBaseCode() {
        return baseCode;
    }

    public String getMessage() {
        return message;
    }
}
