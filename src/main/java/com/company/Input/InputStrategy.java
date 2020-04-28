package com.company.Input;

public interface InputStrategy {

    String getCommand();

    String etPlayerName();

    String getMissionName();

    String getAircraftName();

    int getScore();

    int getGroundKills();

    int getAirKills();

    int getLandings();

    int getDeaths();

    String getMissionResult();
}
