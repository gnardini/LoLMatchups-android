package com.gnardini.lolmatchups.model.temp_models;

import java.util.List;

public class GameChampionNames {

    private final List<String> friendlyTeam;
    private final List<String> enemyTeam;

    public GameChampionNames(List<String> friendlyTeam, List<String> enemyTeam) {
        this.friendlyTeam = friendlyTeam;
        this.enemyTeam = enemyTeam;
    }

    public List<String> getFriendlyTeam() {
        return friendlyTeam;
    }

    public List<String> getEnemyTeam() {
        return enemyTeam;
    }

}
