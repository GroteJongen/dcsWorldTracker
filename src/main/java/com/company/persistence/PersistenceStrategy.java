package com.company.persistence;

import com.company.Mission;

import java.util.List;

public interface PersistenceStrategy {

    boolean update(Mission updatedMission, String missionName);

    void remove(String missionName);

    boolean addMission(Mission mission);

    Mission searchMission(String missionName);

    List<Mission> sortByPoints();

    List<Mission> sortByAirKills();

    List<Mission> sortByGroundKills();

    List<Mission> sortByLandings();

    List<Mission> sortByDeaths();

    List<Mission> getMissionByAircraft(String aircraftName);

    List<Mission> getMissionsByPlayerName(String playerName);

    void removeAllMissions();
}
