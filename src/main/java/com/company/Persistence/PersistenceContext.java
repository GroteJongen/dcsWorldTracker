package com.company.Persistence;

import com.company.Mission;


import java.util.List;

public class PersistenceContext {
    private PersistenceStrategy persistenceStrategy;

    public PersistenceContext(PersistenceStrategy persistenceStrategy) {
        this.persistenceStrategy = persistenceStrategy;
    }

    public boolean addMission(Mission mission) {
        return persistenceStrategy.addMission(mission);
    }

    public Mission searchMission(String missionName) {
        return persistenceStrategy.searchMission(missionName);
    }

    public boolean update(String missionName, Mission updatedMission) {
        return persistenceStrategy.update(updatedMission, missionName);
    }

    public void remove(String missionName) {
        persistenceStrategy.remove(missionName);
    }

    public List<Mission> getMissionByAircraft(String aircraftName) {
        return persistenceStrategy.getMissionByAircraft(aircraftName);
    }

    public List<Mission> sortByScore() {
        return persistenceStrategy.sortByPoints();
    }

    public List<Mission> sortByAirKills() {
        return persistenceStrategy.sortByAirKills();
    }

    public List<Mission> sortByGroundKills() {
        return persistenceStrategy.sortByGroundKills();
    }

    public List<Mission> sortByDeaths() {
        return persistenceStrategy.sortByDeaths();
    }

    public List<Mission> sortByLandings() {
        return persistenceStrategy.sortByLandings();
    }

    public List<Mission> getMissionsByPlayerName(String playerName) {
        return persistenceStrategy.getMissionsByPlayerName(playerName);
    }
    public void clearAll(){
        persistenceStrategy.removeAllMissions();
    }

}
