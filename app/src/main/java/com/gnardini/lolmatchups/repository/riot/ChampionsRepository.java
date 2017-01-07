package com.gnardini.lolmatchups.repository.riot;

import android.util.Log;

import com.gnardini.lolmatchups.Configuration;
import com.gnardini.lolmatchups.model.RepoError;
import com.gnardini.lolmatchups.repository.BaseCallback;
import com.gnardini.lolmatchups.repository.RepoCallback;
import com.gnardini.lolmatchups.repository.riot.model.ChampionsData;
import com.gnardini.lolmatchups.repository.riot.services.ChampionsService;

import java.util.Map;

import okhttp3.ResponseBody;

public class ChampionsRepository {

    private final ChampionsService championsService;
    private Map<Integer, String> championKeys;

    public ChampionsRepository(ChampionsService championsService) {
        this.championsService = championsService;
    }

    public void fetchChampions(RepoCallback<Boolean> repoCallback) {
        if (championKeys != null) {
            repoCallback.onSuccess(true);
            return;
        }
        championsService.fetchChampions(Configuration.RIOT_API_KEY)
                .enqueue(new BaseCallback<ChampionsData>() {
                    @Override
                    public void onResponseSuccessful(ChampionsData response) {
                        championKeys = response.getKeys();
                        repoCallback.onSuccess(true);
                    }

                    @Override
                    public void onResponseFailed(ResponseBody responseBody, int code) {
                        Log.e(getClass().getSimpleName(),
                                "Failed to fetch champions. Code: " + code);
                        repoCallback.onError(RepoError.NO_CHAMPION_INFO, code);
                    }
                });
    }

    public String getChampionName(int championId) {
        if (championKeys == null) {
            return "";
        }
        return championKeys.get(championId);
    }

}
