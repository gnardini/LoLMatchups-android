package com.gnardini.lolmatchups.injector;

import com.gnardini.lolmatchups.Configuration;
import com.gnardini.lolmatchups.repository.RetrofitServices;
import com.gnardini.lolmatchups.repository.champion_gg.services.ChampionStatsService;
import com.gnardini.lolmatchups.repository.riot.services.ChampionsService;
import com.gnardini.lolmatchups.repository.riot.services.SummonerService;

public class NetworkInjector {

    private final LocalRepositoryInjector localRepositoryInjector;

    public NetworkInjector(LocalRepositoryInjector localRepositoryInjector) {
        this.localRepositoryInjector = localRepositoryInjector;
    }

    private RetrofitServices staticRiotRetrofitServices;
    public RetrofitServices getStaticRiotRetrofitServices() {
        if (staticRiotRetrofitServices == null) {
            staticRiotRetrofitServices =
                    new RetrofitServices(Configuration.RIOT_STATIC_DATA_ENDPOINT);
        }
        return staticRiotRetrofitServices;
    }

    private RetrofitServices riotRetrofitServices;
    public RetrofitServices getRiotRetrofitServices() {
        if (riotRetrofitServices == null) {
            String regionCode =
                    localRepositoryInjector.getRegionRepository().getSavedRegion().getRegionCode();
            riotRetrofitServices =
                    new RetrofitServices(
                            String.format(Configuration.RIOT_API_ENDPOINT, regionCode));
        }
        return riotRetrofitServices;
    }

    private RetrofitServices championGgRetrofitServices;
    public RetrofitServices getChampionGgRetrofitServices() {
        if (championGgRetrofitServices == null) {
            championGgRetrofitServices =
                    new RetrofitServices(Configuration.CHAMPION_GG_API_ENDPOINT);
        }
        return championGgRetrofitServices;
    }

    private ChampionsService championsService;
    public ChampionsService getChampionsService() {
        if (championsService == null) {
            championsService = getStaticRiotRetrofitServices().getService(ChampionsService.class);
        }
        return championsService;
    }

    private SummonerService summonerService;
    public SummonerService getSummonerService() {
        if (summonerService == null) {
            summonerService = getRiotRetrofitServices().getService(SummonerService.class);
        }
        return summonerService;
    }

    private ChampionStatsService championStatsService;
    public ChampionStatsService getChampionStatsService() {
        if (championStatsService == null) {
            championStatsService =
                    getChampionGgRetrofitServices().getService(ChampionStatsService.class);
        }
        return championStatsService;
    }

}
