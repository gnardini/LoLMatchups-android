package com.gnardini.lolmatchups.repository.champion_gg;

import android.util.Log;

import com.gnardini.lolmatchups.Configuration;
import com.gnardini.lolmatchups.model.RepoError;
import com.gnardini.lolmatchups.model.Role;
import com.gnardini.lolmatchups.repository.BaseCallback;
import com.gnardini.lolmatchups.repository.RepoCallback;
import com.gnardini.lolmatchups.repository.champion_gg.model.ChampionRole;
import com.gnardini.lolmatchups.repository.champion_gg.model.ChampionStats;
import com.gnardini.lolmatchups.repository.champion_gg.model.MatchupInfo;
import com.gnardini.lolmatchups.repository.champion_gg.model.MatchupRawInfo;
import com.gnardini.lolmatchups.repository.champion_gg.services.ChampionStatsService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;

public class ChampionStatsRepository {

    private final ChampionStatsService championStatsService;
    private final Map<String, List<ChampionRole>> championRoles;

    public ChampionStatsRepository(ChampionStatsService championStatsService) {
        this.championStatsService = championStatsService;
        this.championRoles = new HashMap<>();
    }

    public void fetchChampionsRoles(RepoCallback<Map<String, List<ChampionRole>>> repoCallback) {
        if (!championRoles.isEmpty()) {
            repoCallback.onSuccess(championRoles);
            return;
        }
        championStatsService
                .fetchAllChampions(Configuration.CHAMPIONGG_API_KEY)
                .enqueue(new BaseCallback<List<ChampionStats>>() {
                    @Override
                    public void onResponseSuccessful(List<ChampionStats> championStats) {
                        for (ChampionStats championStat : championStats) {
                            championRoles.put(championStat.getName(), championStat.getRoles());
                        }
                        repoCallback.onSuccess(championRoles);
                    }

                    @Override
                    public void onResponseFailed(ResponseBody responseBody, int code) {
                        Log.e("Champion roles",
                                "Failed to fetch champion roles information. Code: " + code);
                        repoCallback.onError(RepoError.NO_CHAMPION_GG_ROLES, code);
                    }
                });
    }

    public void getMatchupStats(
            String champion1,
            String champion2,
            final Role role,
            RepoCallback<MatchupInfo> repoCallback) {
        championStatsService
                .fetchMatchupInfo(champion1, champion2, Configuration.CHAMPIONGG_API_KEY)
                .enqueue(new BaseCallback<List<MatchupRawInfo>>() {
                    @Override
                    public void onResponseSuccessful(List<MatchupRawInfo> rawInfo) {
                        for (MatchupRawInfo matchupRawInfo : rawInfo) {
                            if (matchupRawInfo.getRole() == role) {
                                MatchupInfo matchupInfo = new MatchupInfo(
                                        matchupRawInfo.getWinRate(),
                                        matchupRawInfo.getGames());
                                repoCallback.onSuccess(matchupInfo);
                                return;
                            }
                        }
                        repoCallback.onSuccess(new MatchupInfo(50, 0));
                    }

                    @Override
                    public void onResponseFailed(ResponseBody responseBody, int code) {
                        Log.e("Champion matchup",
                                "Failed to fetch matchup info between champions. Code: " + code);
                        repoCallback.onError(RepoError.NO_MATCHUP_FOUND, code);
                    }
                });
    }

}
