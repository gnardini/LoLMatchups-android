package com.gnardini.lolmatchups.model;

import com.google.gson.annotations.SerializedName;

public enum Role {

    @SerializedName("Top") TOP,
    @SerializedName("Jungle") JUNGLE,
    @SerializedName("Middle") MID,
    @SerializedName("ADC") ADC,
    @SerializedName("Support") SUPPORT;

}
