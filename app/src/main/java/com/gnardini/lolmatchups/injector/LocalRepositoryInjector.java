package com.gnardini.lolmatchups.injector;

import com.gnardini.lolmatchups.repository.common.RegionRepository;
import com.gnardini.lolmatchups.repository.common.StringValueRepository;
import com.gnardini.lolmatchups.repository.common.SummonerIdsRepository;

public class LocalRepositoryInjector {

    private SummonerIdsRepository summonerIdsRepository;
    public SummonerIdsRepository getSummonerIdsRepository() {
        if (summonerIdsRepository == null) {
            summonerIdsRepository = new SummonerIdsRepository();
        }
        return summonerIdsRepository;
    }

    private RegionRepository regionRepository;
    public RegionRepository getRegionRepository() {
        if (regionRepository == null) {
            regionRepository = new RegionRepository();
        }
        return regionRepository;
    }

    private StringValueRepository summonerSearchRepository;
    public StringValueRepository getSummonerSearchRepository() {
        if (summonerSearchRepository == null) {
            summonerSearchRepository = new StringValueRepository("SummonerSearchKey", "");
        }
        return summonerSearchRepository;
    }

}
