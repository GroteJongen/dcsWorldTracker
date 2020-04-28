package com.company.persistence;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Player {
    private String playerName;
    private int totalScore;
    private int totalGroundKills;
    private int totalAirKills;
    private int totalDeaths;
    private int totalLandings;
    private int totalMissions;
    private int totalVictories;
    private int totalLooses;

    public Player(String playerName) {
        this.playerName = playerName;
    }

    public int getTotalAirKills() {
        return totalAirKills;
    }
}
