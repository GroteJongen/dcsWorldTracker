package com.company.persistence;

import com.company.Mission;

import java.util.List;

public class CalculateTotalPlayerScoreService {

    private int calculateTotalPlayerScore(List<Mission> missions) {
        int totalScore = 0;
        for (Mission mission : missions) {
            totalScore += mission.getScore();
        }
        return totalScore;
    }

    private int calculateTotalPlayerAirKills(List<Mission> missions) {
        int totalAirKills = 0;
        for (Mission mission : missions) {
            totalAirKills += mission.getAirKills();
        }
        return totalAirKills;
    }

    private int calculateTotalPlayerGroundKills(List<Mission> missions) {
        int totalGroundKills = 0;
        for (Mission mission : missions) {
            totalGroundKills += mission.getGroundKills();
        }
        return totalGroundKills;
    }

    private int calculateTotalPlayerDeaths(List<Mission> missions) {
        int totalDeaths = 0;
        for (Mission mission : missions) {
            totalDeaths += mission.getDeaths();
        }
        return totalDeaths;
    }

    private int calculateTotalPlayerLandings(List<Mission> missions) {
        int totalLandings = 0;
        for (Mission mission : missions) {
            totalLandings += mission.getLandings();
        }
        return totalLandings;
    }

    private int calculateTotalLooses(List<Mission> missions) {
        int totalLooses = 0;
        for (Mission mission : missions) {
            if (mission.getIsVictory().equals("LOOSE")) {
                totalLooses++;
            }

        }
        return totalLooses;
    }

    private int calculateTotalVictories(List<Mission> missions) {
        int totalVictories = 0;
        for (Mission mission : missions) {
            if (mission.getIsVictory().equals("WIN")) {
                totalVictories++;
            }

        }
        return totalVictories;
    }

   /* public double calculateKillDeathRatio(List<Mission> missions) {
        int totalKills = calculateTotalPlayerAirKills(missions) + calculateTotalPlayerGroundKills(missions);
        int totalDeaths = calculateTotalPlayerDeaths(missions);
        if (totalDeaths <= 0) {
            return totalKills;
        }
        return totalKills / totalDeaths;
    }*/

    private int calculateMissionsFlown(List<Mission> missions) {
        return missions.size();
    }

    public void setPlayerLog(Player player, List<Mission> missions) {
        player.setTotalScore(calculateTotalPlayerScore(missions));
        player.setTotalAirKills(calculateTotalPlayerAirKills(missions));
        player.setTotalGroundKills(calculateTotalPlayerGroundKills(missions));
        player.setTotalMissions(calculateMissionsFlown(missions));
        player.setTotalVictories(calculateTotalVictories(missions));
        player.setTotalLooses(calculateTotalLooses(missions));
        player.setTotalLandings(calculateTotalPlayerLandings(missions));
        player.setTotalDeaths(calculateTotalPlayerDeaths(missions));
    }

}
