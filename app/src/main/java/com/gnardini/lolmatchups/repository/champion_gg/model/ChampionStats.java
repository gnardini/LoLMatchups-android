package com.gnardini.lolmatchups.repository.champion_gg.model;

import java.util.List;

public class ChampionStats {

    private String key;
    private List<ChampionRole> roles;

    public String getName() {
        return key;
    }

    public List<ChampionRole> getRoles() {
        return roles;
    }
}
