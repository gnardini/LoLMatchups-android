package com.gnardini.lolmatchups.repository.champion_gg.services;

import com.gnardini.lolmatchups.repository.champion_gg.model.ChampionStats;
import com.gnardini.lolmatchups.repository.champion_gg.model.MatchupRawInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ChampionStatsService {

    @GET("champion")
    Call<List<ChampionStats>> fetchAllChampions(@Query("api_key") String apiKey);

    @GET("champion/{championName}/matchup/{enemyName}")
    Call<List<MatchupRawInfo>> fetchMatchupInfo(
            @Path("championName") String championName,
            @Path("enemyName") String enemyName,
            @Query("api_key") String apiKey);

}
