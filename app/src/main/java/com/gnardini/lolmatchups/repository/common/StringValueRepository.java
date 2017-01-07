package com.gnardini.lolmatchups.repository.common;

import com.gnardini.lolmatchups.utils.StorageUtils;

public class StringValueRepository {

    private final String key;
    private final String defaultValue;

    public StringValueRepository(String key, String defaultValue) {
        this.key = key;
        this.defaultValue = defaultValue;
    }

    public String getSavedValue() {
        return StorageUtils.getStringFromSharedPreferences(key, defaultValue);
    }

    public void updateValue(String value) {
        StorageUtils.storeInSharedPreferences(key, value);
    }

}
