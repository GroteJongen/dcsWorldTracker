package com.company;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Mission implements Comparable<Mission>{
    private String missionName;
    private String aircraftName;
    private int groundKills;
    private int airKills;
    private int score;
    private int landings;
    private int deaths;
    private String isVictory;
    private String playerName;

    public Mission( String playerName,String missionName, String aircraftName, int groundKills, int airKills, int score, int landings, int deaths, String isVictory) {
        this.playerName = playerName;
        this.missionName = missionName;
        this.aircraftName = aircraftName;
        this.groundKills = groundKills;
        this.airKills = airKills;
        this.score = score;
        this.landings = landings;
        this.deaths = deaths;
        this.isVictory = isVictory;

    }

    @Override
    public int compareTo(Mission o) {
       if(this.getScore() > o.getScore()){
           return -1;
       }else if(this.getScore() == o.getScore()){
           return 0;
       }else {
           return 1;
       }
    }
}
