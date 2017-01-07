package com.gnardini.lolmatchups.model.temp_models;

import com.gnardini.lolmatchups.model.Role;

import java.util.Map;

public class GameChampionRoles {

    private final Map<String, String> championSummoners;
    private final Map<Role, String> friendlyTeam;
    private final Map<Role, String> enemyTeam;

    public GameChampionRoles(
            Map<String, String> championSummoners,
            Map<Role, String> friendlyTeam,
            Map<Role, String> enemyTeam) {
        this.championSummoners = championSummoners;
        this.friendlyTeam = friendlyTeam;
        this.enemyTeam = enemyTeam;
    }

    public Map<String, String> getChampionSummoners() {
        return championSummoners;
    }

    public Map<Role, String> getFriendlyTeam() {
        return friendlyTeam;
    }

    public Map<Role, String> getEnemyTeam() {
        return enemyTeam;
    }

}
