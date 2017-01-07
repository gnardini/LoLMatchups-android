package com.gnardini.lolmatchups.model;

public enum Region {

    NA("na", "NA1"),
    EUW("euw", "EUW1"),
    EUNE("eune", "EUN1"),
    BR("br", "BR1"),
    LAS("las", "LA2"),
    LAN("lan", "LA1"),
    KR("kr", "KR"),
    JP("jp", "JP1"),
    OCE("oce", "OC1"),
    TR("tr", "TR1"),
    RU("ru", "RU");

    private final String regionCode;
    private final String platformId;

    Region(String regionCode, String platformId) {
        this.regionCode = regionCode;
        this.platformId = platformId;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public String getPlatformId() {
        return platformId;
    }
}
