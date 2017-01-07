package com.gnardini.lolmatchups.repository.common;

import com.gnardini.lolmatchups.model.GameData;
import com.gnardini.lolmatchups.model.GameTeam;
import com.gnardini.lolmatchups.model.InGameChampion;
import com.gnardini.lolmatchups.model.RepoError;
import com.gnardini.lolmatchups.model.Role;
import com.gnardini.lolmatchups.model.temp_models.GameChampionNames;
import com.gnardini.lolmatchups.model.temp_models.GameChampionRoles;
import com.gnardini.lolmatchups.repository.RepoCallback;
import com.gnardini.lolmatchups.repository.champion_gg.ChampionStatsRepository;
import com.gnardini.lolmatchups.repository.champion_gg.model.ChampionRole;
import com.gnardini.lolmatchups.repository.champion_gg.model.MatchupInfo;
import com.gnardini.lolmatchups.repository.riot.ChampionsRepository;
import com.gnardini.lolmatchups.repository.riot.SummonerRepository;
import com.gnardini.lolmatchups.utils.ChampionsRoleSorter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MatchCalculatorRepository {

    private final ChampionsRepository championsRepository;
    private final SummonerRepository summonerRepository;
    private final ChampionStatsRepository championStatsRepository;
    private final ChampionsRoleSorter championsRoleSorter;

    public MatchCalculatorRepository(
            ChampionsRepository championsRepository,
            SummonerRepository summonerRepository,
            ChampionStatsRepository championStatsRepository,
            ChampionsRoleSorter championsRoleSorter) {
        this.championsRepository = championsRepository;
        this.summonerRepository = summonerRepository;
        this.championStatsRepository = championStatsRepository;
        this.championsRoleSorter = championsRoleSorter;
    }

    public void getDataForPlayer(
            final String playerName,
            final RepoCallback<GameData> repoCallback) {
        // First, we make sure that champion information is available.
        championsRepository.fetchChampions(new RepoCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean value) {
                fetchSummonerData(playerName, repoCallback);
            }

            @Override
            public void onError(RepoError repoError, int code) {
                repoCallback.onError(repoError, code);
            }
        });
    }

    // Here we fetch the information about the game currently being played by |playerName|.
    private void fetchSummonerData(
            final String playerName,
            final RepoCallback<GameData> repoCallback) {
        summonerRepository.fetchMatchData(playerName, new RepoCallback<GameChampionNames>() {
            @Override
            public void onSuccess(GameChampionNames gameChampions) {
                assignChampionsRoles(gameChampions, repoCallback);
            }

            @Override
            public void onError(RepoError repoError, int code) {
                repoCallback.onError(repoError, code);
            }
        });
    }

    // Here we fetch all data for champion role usage and assign a role for each champion in the
    // game.
    private void assignChampionsRoles(
            final GameChampionNames gameChampionNames,
            final RepoCallback<GameData> repoCallback) {
        championStatsRepository.fetchChampionsRoles(
                new RepoCallback<Map<String, List<ChampionRole>>>() {
                    @Override
                    public void onSuccess(Map<String, List<ChampionRole>> championRoles) {
                        GameChampionRoles gameChampionRoles =
                                championsRoleSorter.sortChampionRoles(
                                        gameChampionNames, championRoles);
                        calculateMatchupWinrates(gameChampionRoles, repoCallback);
                    }

                    @Override
                    public void onError(RepoError repoError, int code) {
                        repoCallback.onError(repoError, code);
                    }
                });
    }

    // Here we calculate the win rate of the matchup in each lane. Note that if said data is
    // unavailable, we fill in a 50. This should probably be changed.
    private void calculateMatchupWinrates(
            final GameChampionRoles gameChampionRoles,
            final RepoCallback<GameData> repoCallback) {
        Map<Role, String> friendlyTeamChampions = gameChampionRoles.getFriendlyTeam();
        Map<Role, String> enemyTeamChampions = gameChampionRoles.getEnemyTeam();

        Map<Role, InGameChampion> friendlyTeam = new HashMap<>();
        Map<Role, InGameChampion> enemyTeam = new HashMap<>();

        for (Role role : Role.values()) {
            fetchMatchupInfoForRole(
                    role,
                    friendlyTeamChampions.get(role),
                    enemyTeamChampions.get(role),
                    new RepoCallback<MatchupInfo>() {
                        @Override
                        public void onSuccess(MatchupInfo matchupInfo) {
                            InGameChampion friendlyInGameChampion = new InGameChampion(
                                    friendlyTeamChampions.get(role),
                                    matchupInfo.getChampion1WinRate(),
                                    matchupInfo.getGamesPlayed());
                            friendlyTeam.put(role, friendlyInGameChampion);
                            InGameChampion enemyInGameChampion = new InGameChampion(
                                    enemyTeamChampions.get(role),
                                    100 - matchupInfo.getChampion1WinRate(),
                                    matchupInfo.getGamesPlayed());
                            enemyTeam.put(role, enemyInGameChampion);
                            if (enemyTeam.size() == 5) {
                                calculateTeamWinRates(
                                        gameChampionRoles.getChampionSummoners(),
                                        friendlyTeam,
                                        enemyTeam,
                                        repoCallback);
                            }
                        }

                        @Override
                        public void onError(RepoError repoError, int code) {
                            repoCallback.onError(repoError, code);
                        }
                    });
        }
    }

    private void calculateTeamWinRates(
            Map<String, String> championSummoners,
            Map<Role, InGameChampion> friendlyTeam,
            Map<Role, InGameChampion> enemyTeam,
            RepoCallback<GameData> repoCallback) {
        int totalGames = calculateTotalGames(friendlyTeam);
        double friendlyTeamWinRate = 0;
        for (Role role : Role.values()) {
            InGameChampion friendlyChampion = friendlyTeam.get(role);
            friendlyTeamWinRate +=
                    ((double) friendlyChampion.getGames() / totalGames)
                            * friendlyChampion.getWinRate();
        }

        GameTeam friendlyGameTeam = new GameTeam(friendlyTeam, friendlyTeamWinRate);
        GameTeam enemyGameTeam = new GameTeam(enemyTeam, 100 - friendlyTeamWinRate);
        repoCallback.onSuccess(new GameData(championSummoners, friendlyGameTeam, enemyGameTeam));
    }

    private void fetchMatchupInfoForRole(
            Role role,
            String champion1,
            String champion2,
            RepoCallback<MatchupInfo> repoCallback) {
        championStatsRepository.getMatchupStats(champion1, champion2, role,
                new RepoCallback<MatchupInfo>() {
                    @Override
                    public void onSuccess(MatchupInfo matchupInfo) {
                        repoCallback.onSuccess(matchupInfo);
                    }

                    @Override
                    public void onError(RepoError repoError, int errorCode) {
                        repoCallback.onSuccess(new MatchupInfo(50, 0));
                    }
                });
    }

    private int calculateTotalGames(Map<Role, InGameChampion> friendlyTeam) {
        int totalGames = 0;
        for (InGameChampion inGameChampion : friendlyTeam.values()) {
            totalGames += inGameChampion.getGames();
        }
        return totalGames;
    }

}
