package com.gnardini.lolmatchups.repository.riot;

import android.util.Log;

import com.gnardini.lolmatchups.Configuration;
import com.gnardini.lolmatchups.model.RepoError;
import com.gnardini.lolmatchups.model.temp_models.GameChampionNames;
import com.gnardini.lolmatchups.repository.BaseCallback;
import com.gnardini.lolmatchups.repository.RepoCallback;
import com.gnardini.lolmatchups.repository.common.RegionRepository;
import com.gnardini.lolmatchups.repository.common.SummonerIdsRepository;
import com.gnardini.lolmatchups.repository.riot.model.GameInfo;
import com.gnardini.lolmatchups.repository.riot.model.GameParticipant;
import com.gnardini.lolmatchups.repository.riot.model.SummonerId;
import com.gnardini.lolmatchups.repository.riot.services.SummonerService;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;

public class SummonerRepository {

    private final SummonerIdsRepository summonerIdsRepository;
    private final SummonerService summonerService;
    private final ChampionsRepository championsRepository;
    private final RegionRepository regionRepository;

    public SummonerRepository(
            SummonerService summonerService,
            SummonerIdsRepository summonerIdsRepository,
            ChampionsRepository championsRepository,
            RegionRepository regionRepository) {
        this.summonerService = summonerService;
        this.summonerIdsRepository = summonerIdsRepository;
        this.championsRepository = championsRepository;
        this.regionRepository = regionRepository;
    }

    public void fetchSummonerId(String summonerName, RepoCallback<Integer> repoCallback) {
        if (summonerIdsRepository.isSummonerStored(summonerName)) {
            repoCallback.onSuccess(summonerIdsRepository.getIdForSummoner(summonerName));
            return;
        }
        String regionCode = regionRepository.getSavedRegion().getRegionCode();
        summonerService
                .getSummonerId(regionCode, summonerName, Configuration.RIOT_API_KEY)
                .enqueue(new BaseCallback<Map<String, SummonerId>>() {
                    @Override
                    public void onResponseSuccessful(Map<String, SummonerId> response) {
                        SummonerId summonerId = response.get(summonerName);
                        if (summonerId == null) {
                            Log.e("Summoners", "Summoner with name " + summonerName + " not found");
                            repoCallback.onError(RepoError.NO_SUMMONER_NAME, 0);
                        } else {
                            summonerIdsRepository.saveSummonerId(summonerName, summonerId.getId());
                            repoCallback.onSuccess(summonerId.getId());
                        }
                    }

                    @Override
                    public void onResponseFailed(ResponseBody responseBody, int code) {
                        Log.e(getClass().getSimpleName(),
                                "Failed to fetch summoner. Code: " + code);
                        repoCallback.onError(RepoError.NO_SUMMONER_NAME, code);
                    }
                });
    }

    public void fetchMatchData(String summonerName, RepoCallback<GameChampionNames> repoCallback) {
        fetchSummonerId(summonerName, new RepoCallback<Integer>() {
            @Override
            public void onSuccess(Integer id) {
                fetchCurrentGameForSummoner(id, repoCallback);
            }

            @Override
            public void onError(RepoError repoError, int code) {
                repoCallback.onError(repoError, code);
            }
        });
    }

    private void fetchCurrentGameForSummoner(int summonerId, RepoCallback<GameChampionNames> repoCallback) {
        String regionCode = regionRepository.getSavedRegion().getRegionCode();
        String platformId = regionRepository.getSavedRegion().getPlatformId();
        summonerService
                .getGameInfo(regionCode, platformId, summonerId, Configuration.RIOT_API_KEY)
                .enqueue(new BaseCallback<GameInfo>() {
                    @Override
                    public void onResponseSuccessful(GameInfo gameInfo) {
                        GameChampionNames gameChampions = transformToMatchData(gameInfo);
                        if (gameChampions != null) {
                            repoCallback.onSuccess(gameChampions);
                        } else {
                            repoCallback.onError(RepoError.NO_CURRENT_GAME, 0);
                        }
                    }

                    @Override
                    public void onResponseFailed(ResponseBody responseBody, int code) {
                        repoCallback.onError(RepoError.NO_CURRENT_GAME, code);
                        Log.e("Summoners", "Failed to find a current game. Code: " + code);
                    }
                });
    }

    private GameChampionNames transformToMatchData(GameInfo gameInfo) {
        List<GameParticipant> participants = gameInfo.getParticipants();
        if (participants.size() != 10) {
            Log.e("Summoner", "Incorrect number of game participants: " + participants.size());
            return null;
        }

        Map<String, String> championSummoners = new HashMap<>();

        // Calculate teams ids.
        int team1Id = participants.get(0).getTeamId();
        int team2Id = -1;
        for (GameParticipant participant : participants) {
            championSummoners.put(
                    championsRepository.getChampionName(participant.getChampionId()),
                    participant.getSummonerName());
            if (participant.getTeamId() != team1Id) {
                team2Id = participant.getTeamId();
            }
        }

        List<String> team1Champions = championsOfTeam(participants, team1Id);
        List<String> team2Champions = championsOfTeam(participants, team2Id);
        return new GameChampionNames(championSummoners, team1Champions, team2Champions);
    }

    private List<String> championsOfTeam(List<GameParticipant> participants, int teamId) {
        List<String> champions = new LinkedList<>();
        for (GameParticipant participant : participants) {
            if (participant.getTeamId() == teamId) {
                champions.add(championsRepository.getChampionName(participant.getChampionId()));
            }
        }
        return champions;
    }

}
