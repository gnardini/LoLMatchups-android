package com.gnardini.lolmatchups.repository.common;

import com.gnardini.lolmatchups.model.Region;
import com.gnardini.lolmatchups.utils.StorageUtils;

import static com.gnardini.lolmatchups.model.Region.NA;

public class RegionRepository {

    private static final String REGION_KEY = "RegionKey";

    public Region getSavedRegion() {
        String regionString =
                StorageUtils.getStringFromSharedPreferences(REGION_KEY, NA.toString());
        return Region.valueOf(regionString);
    }

    public void updateRegion(Region region) {
        StorageUtils.storeInSharedPreferences(REGION_KEY, region.toString());
    }

}
