package com.gnardini.lolmatchups.utils;

import android.util.Log;

import com.gnardini.lolmatchups.model.temp_models.GameChampionNames;
import com.gnardini.lolmatchups.model.Role;
import com.gnardini.lolmatchups.model.temp_models.GameChampionRoles;
import com.gnardini.lolmatchups.repository.champion_gg.model.ChampionRole;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChampionsRoleSorter {

    public GameChampionRoles sortChampionRoles(
            GameChampionNames gameChampionNames,
            Map<String, List<ChampionRole>> championRoles) {
        Map<Role, String> friendlyTeamRoles =
                assignTeamRoles(gameChampionNames.getFriendlyTeam(), championRoles);
        Map<Role, String> enemyTeamRoles =
            assignTeamRoles(gameChampionNames.getEnemyTeam(), championRoles);
        return new GameChampionRoles(friendlyTeamRoles, enemyTeamRoles);
    }

    private Map<Role, String> assignTeamRoles(
            List<String> championNames,
            Map<String, List<ChampionRole>> championRoles) {
        Map<Role, String> assignedRoles = new HashMap<>();

        // This is an edge case that should correct itself shortly. If there's a Ziggs in the game
        // and no ADC, set Ziggs as ADC.
        checkZiggsAdc(assignedRoles, championRoles, championNames);

        // Set all roles that can be logically inferred.
        while (assignChampionsWithOneRole(assignedRoles, championRoles, championNames)
                || assignRolesWithOneChampion(assignedRoles, championRoles, championNames));

        // Now that all defined roles were set, set the rest based on probabilities.
        assignChampionsByProbability(assignedRoles, championRoles, championNames);

        // If some roles still weren't set, set them randomly.
        assignChampionsRandomly(assignedRoles, championNames);

        printTeamOnConsole(assignedRoles);

        return assignedRoles;
    }

    private void checkZiggsAdc(
            Map<Role, String> assignedRoles,
            Map<String, List<ChampionRole>> championRoles,
            List<String> championNames) {
        if (!thereIsAdc(championRoles, championNames)) {
            for (String championName : championNames) {
                if (championName.equals("Ziggs")) {
                    assignedRoles.put(Role.ADC, "Ziggs");
                }
            }
        }
    }

    private boolean thereIsAdc(
            Map<String, List<ChampionRole>> championRoles,
            List<String> championNames) {
        for (String championName : championNames) {
            for (ChampionRole championRole : championRoles.get(championName)) {
                if (championRole.getRole() == Role.ADC) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean assignChampionsWithOneRole(
            Map<Role, String> assignedRoles,
            Map<String, List<ChampionRole>> championRoles,
            List<String> championNames) {
        boolean championSet = false;
        for (String championName : championNames) {
            if (!assignedRoles.values().contains(championName)) {
                List<ChampionRole> possibleRoles = championRoles.get(championName);
                int possibleRolesCount = 0;
                Role selectedRole = null;
                for (ChampionRole possibleRole : possibleRoles) {
                    if (!assignedRoles.containsKey(possibleRole.getRole())) {
                        possibleRolesCount++;
                        selectedRole = possibleRole.getRole();
                    }
                }
                if (possibleRolesCount == 1) {
                    assignedRoles.put(selectedRole, championName);
                    championSet = true;
                }
            }
        }
        return championSet;
    }

    private boolean assignRolesWithOneChampion(
            Map<Role, String> assignedRoles,
            Map<String, List<ChampionRole>> championRoles,
            List<String> championNames) {
        boolean championSet = false;
        for (Role role : Role.values()) {
            if (!assignedRoles.containsKey(role)) {
                int possibleChampionCount = 0;
                String selectedChampion = null;
                for (String championName : championNames) {
                    if (!assignedRoles.values().contains(championName)) {
                        List<ChampionRole> possibleRoles = championRoles.get(championName);
                        for (ChampionRole possibleRole : possibleRoles) {
                            if (role == possibleRole.getRole()) {
                                possibleChampionCount++;
                                selectedChampion = championName;
                            }
                        }
                    }
                }
                if (possibleChampionCount == 1) {
                    assignedRoles.put(role, selectedChampion);
                    championSet = true;
                }
            }
        }
        return championSet;
    }

    private void assignChampionsByProbability(
            Map<Role, String> assignedRoles,
            Map<String, List<ChampionRole>> championRoles,
            List<String> championNames) {
        for (Role role : Role.values()) {
            if (!assignedRoles.containsKey(role)) {
                double playRate = -1;
                String selectedChampionName = null;
                for (String championName : championNames) {
                    if (!assignedRoles.values().contains(championName)) {
                        for (ChampionRole possibleRole : championRoles.get(championName)) {
                            if (role == possibleRole.getRole()
                                    && possibleRole.getPercentPlayed() > playRate) {
                                playRate = possibleRole.getPercentPlayed();
                                selectedChampionName = championName;
                            }
                        }
                    }
                }
                if (playRate > 0) {
                    assignedRoles.put(role, selectedChampionName);
                }
            }
        }

    }

    private void assignChampionsRandomly(
            Map<Role, String> assignedRoles,
            List<String> championNames) {
        for (Role role : Role.values()) {
            if (!assignedRoles.containsKey(role)) {
                for (String championName : championNames) {
                    if (!assignedRoles.values().contains(championName)) {
                        assignedRoles.put(role, championName);
                        break;
                    }
                }
            }
        }
    }

    private void printTeamOnConsole(Map<Role, String> assignedRoles) {
        Log.e("Matchup", "Printing team");
        for (Map.Entry<Role, String> entry : assignedRoles.entrySet()) {
            Log.e("Matchup", entry.getKey()+ ": " + entry.getValue());
        }
    }

}
