package com.gnardini.lolmatchups.repository.riot.model;

import java.util.HashMap;
import java.util.Map;

public class ChampionsData {

    private Map<String, ChampionId> data;

    public Map<Integer, String> getKeys() {
        final Map<Integer, String> keys = new HashMap<>();
        for (Map.Entry<String, ChampionId> entry : data.entrySet()) {
            keys.put(entry.getValue().getId(), entry.getKey());
        }
        return keys;
    }

}
