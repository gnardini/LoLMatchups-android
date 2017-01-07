package com.gnardini.lolmatchups.model;

public class GameData {

    private GameTeam friendlyTeam;
    private GameTeam enemyTeam;

    public GameData(GameTeam friendlyTeam, GameTeam enemyTeam) {
        this.friendlyTeam = friendlyTeam;
        this.enemyTeam = enemyTeam;
    }

    public GameTeam getFriendlyTeam() {
        return friendlyTeam;
    }

    public GameTeam getEnemyTeam() {
        return enemyTeam;
    }

}
