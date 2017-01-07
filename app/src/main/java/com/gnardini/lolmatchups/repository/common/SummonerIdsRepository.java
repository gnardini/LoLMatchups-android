package com.gnardini.lolmatchups.repository.common;

import com.gnardini.lolmatchups.utils.StorageUtils;

public class SummonerIdsRepository {

    public int getIdForSummoner(String summonerName) {
        return StorageUtils.getIntFromSharedPreferences(summonerName, -1);
    }

    public boolean isSummonerStored(String summonerName) {
        return StorageUtils.keyExists(summonerName);
    }

    public void saveSummonerId(String summonerName, int id) {
        StorageUtils.storeInSharedPreferences(summonerName, id);
    }

}
