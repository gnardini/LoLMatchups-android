package com.gnardini.lolmatchups.model;

import java.util.Map;

public class GameData {

    private final Map<String, String> championSummoners;
    private final GameTeam friendlyTeam;
    private final GameTeam enemyTeam;

    public GameData(
            Map<String, String> championSummoners,
            GameTeam friendlyTeam,
            GameTeam enemyTeam) {
        this.championSummoners = championSummoners;
        this.friendlyTeam = friendlyTeam;
        this.enemyTeam = enemyTeam;
    }

    public Map<String, String> getChampionSummoners() {
        return championSummoners;
    }

    public GameTeam getFriendlyTeam() {
        return friendlyTeam;
    }

    public GameTeam getEnemyTeam() {
        return enemyTeam;
    }

}
