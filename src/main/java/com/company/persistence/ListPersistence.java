package com.company.persistence;

import com.company.Mission;

import java.util.ArrayList;
import java.util.List;

public class ListPersistence implements PersistenceStrategy {
    private FilterService filterService;

    private List<Mission> mainListOfMissions = new ArrayList<>();

    public ListPersistence(FilterService filterService) {
        this.filterService = filterService;
    }


    @Override
    public boolean update(Mission newMission, String missionName) {
        if (mainListOfMissions.contains(newMission)) {
            for (int i = 0; i < mainListOfMissions.size(); i++) {
                if (searchMission(missionName) != null) {
                    mainListOfMissions.set(i, newMission);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void remove(String missionName) {
        for (int i = 0; i < mainListOfMissions.size(); i++) {
            if (mainListOfMissions.get(i).getMissionName().equals(missionName)) {
                mainListOfMissions.remove(mainListOfMissions.get(i));
            }
        }
    }

    @Override
    public boolean addMission(Mission mission) {
        if (mainListOfMissions.contains(mission)) {
            return false;
        }
        mainListOfMissions.add(mission);
        return true;
    }

    @Override
    public Mission searchMission(String missionName) {
        for (Mission mission : mainListOfMissions) {
            if (mission.getMissionName().equals(missionName))
                return mission;
        }
        return null;
    }

    @Override
    public List<Mission> sortByPoints() {
        return filterService.sortByPoints(mainListOfMissions);
    }

    @Override
    public List<Mission> sortByAirKills() {
        return filterService.sortByAirKills(mainListOfMissions);
    }

    @Override
    public List<Mission> sortByGroundKills() {
        return filterService.sortByGroundKills(mainListOfMissions);
    }

    @Override
    public List<Mission> sortByLandings() {
        return filterService.sortByLandings(mainListOfMissions);
    }

    @Override
    public List<Mission> sortByDeaths() {
        return filterService.sortByDeaths(mainListOfMissions);
    }

    @Override
    public List<Mission> getMissionByAircraft(String aircraftName) {
        return filterService.getMissionsByAircraft(mainListOfMissions, aircraftName);
    }

    public List<Mission> getMissionsByPlayerName(String playerName) {
        List<Mission> filteredByPlayersList = new ArrayList<>();
        for (Mission mission : mainListOfMissions) {
            if (mission.getPlayerName().equals(playerName)) {
                filteredByPlayersList.add(mission);
            }
        }
        return filteredByPlayersList;
    }

    @Override
    public void removeAllMissions() {
        mainListOfMissions.clear();
    }

}
