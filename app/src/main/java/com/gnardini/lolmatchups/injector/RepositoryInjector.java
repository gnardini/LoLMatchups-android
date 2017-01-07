package com.gnardini.lolmatchups.injector;

import com.gnardini.lolmatchups.repository.champion_gg.ChampionStatsRepository;
import com.gnardini.lolmatchups.repository.common.MatchCalculatorRepository;
import com.gnardini.lolmatchups.repository.riot.ChampionsRepository;
import com.gnardini.lolmatchups.repository.riot.SummonerRepository;
import com.gnardini.lolmatchups.utils.ChampionsRoleSorter;

public class RepositoryInjector {

    private final LocalRepositoryInjector localRepositoryInjector;
    private final NetworkInjector networkInjector;

    public RepositoryInjector(
            LocalRepositoryInjector localRepositoryInjector,
            NetworkInjector networkInjector) {
        this.localRepositoryInjector = localRepositoryInjector;
        this.networkInjector = networkInjector;
    }

    private ChampionsRepository championsRepository;
    public ChampionsRepository getChampionsRepository() {
        if (championsRepository == null) {
            championsRepository = new ChampionsRepository(networkInjector.getChampionsService());
        }
        return championsRepository;
    }

    private SummonerRepository summonerRepository;
    public SummonerRepository getSummonerRepository() {
        if (summonerRepository == null) {
            summonerRepository = new SummonerRepository(
                    networkInjector.getSummonerService(),
                    localRepositoryInjector.getSummonerIdsRepository(),
                    getChampionsRepository(),
                    localRepositoryInjector.getRegionRepository());
        }
        return summonerRepository;
    }

    private ChampionStatsRepository championStatsRepository;
    public ChampionStatsRepository getChampionStatsRepository() {
        if (championStatsRepository == null) {
            championStatsRepository =
                    new ChampionStatsRepository(networkInjector.getChampionStatsService());
        }
        return championStatsRepository;
    }

    private MatchCalculatorRepository matchCalculatorRepository;
    public MatchCalculatorRepository getMatchCalculatorRepository() {
        if (matchCalculatorRepository == null) {
            matchCalculatorRepository = new MatchCalculatorRepository(
                    getChampionsRepository(),
                    getSummonerRepository(),
                    getChampionStatsRepository(),
                    new ChampionsRoleSorter());
        }
        return matchCalculatorRepository;
    }

}
