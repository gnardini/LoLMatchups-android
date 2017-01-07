package com.gnardini.lolmatchups.repository.riot.services;

import com.gnardini.lolmatchups.repository.riot.model.ChampionsData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ChampionsService {

    @GET("champion")
    Call<ChampionsData> fetchChampions(@Query("api_key") String apiKey);

}
