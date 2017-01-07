package com.gnardini.lolmatchups.model.temp_models;

import java.util.List;
import java.util.Map;

public class GameChampionNames {

    private final Map<String, String> championSummoners;
    private final List<String> friendlyTeam;
    private final List<String> enemyTeam;

    public GameChampionNames(
            Map<String, String> championSummoners,
            List<String> friendlyTeam,
            List<String> enemyTeam) {
        this.championSummoners = championSummoners;
        this.friendlyTeam = friendlyTeam;
        this.enemyTeam = enemyTeam;
    }

    public Map<String, String> getChampionSummoners() {
        return championSummoners;
    }

    public List<String> getFriendlyTeam() {
        return friendlyTeam;
    }

    public List<String> getEnemyTeam() {
        return enemyTeam;
    }

}
