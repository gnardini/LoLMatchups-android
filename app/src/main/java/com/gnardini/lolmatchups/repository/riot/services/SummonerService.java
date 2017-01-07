package com.gnardini.lolmatchups.repository.riot.services;

import com.gnardini.lolmatchups.Configuration;
import com.gnardini.lolmatchups.repository.riot.model.GameInfo;
import com.gnardini.lolmatchups.repository.riot.model.SummonerId;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SummonerService {

    @GET(Configuration.RIOT_API_ENDPOINT + "api/lol/{region}/v1.4/summoner/by-name/{summoner}")
    Call<Map<String, SummonerId>> getSummonerId(
            @Path("region") String region,
            @Path("summoner") String summonerName,
            @Query("api_key") String apiKey);

    @GET(Configuration.RIOT_API_ENDPOINT
            + "observer-mode/rest/consumer/getSpectatorGameInfo/{platformId}/{summonerId}")
    Call<GameInfo> getGameInfo(
            @Path("region") String region,
            @Path("platformId") String platformId,
            @Path("summonerId") int summonerId,
            @Query("api_key") String apiKey);

}
