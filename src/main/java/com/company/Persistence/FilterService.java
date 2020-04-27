package com.company.Persistence;

import com.company.Mission;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FilterService {
    List<Mission> getMissionsByAircraft(List<Mission> missions, String aircraftName) {
        List<Mission> filteredMissions = new ArrayList<>();
        for (Mission mission : missions) {
            if (mission.getAircraftName().equals(aircraftName)) {
                filteredMissions.add(mission);
            }
        }

        return filteredMissions;

    }

    List<Mission> sortByPoints(List<Mission> missions) {
        List<Mission> sortedMission;
        Collections.sort(missions);
        sortedMission = missions;
        return sortedMission;
    }

    List<Mission> sortByAirKills(List<Mission> missions) {
        List<Mission> sortedMissions;
        missions.sort(compareByAirKills);
        sortedMissions = missions;
        return sortedMissions;
    }

    List<Mission> sortByGroundKills(List<Mission> missions) {
        List<Mission> sortedMissions;
        missions.sort(compareByGroundKills);
        sortedMissions = missions;
        return sortedMissions;
    }

    List<Mission> sortByDeaths(List<Mission> missions) {
        List<Mission> sortedMissions;
        missions.sort(compareByDeaths);
        sortedMissions = missions;
        return sortedMissions;
    }

    List<Mission> sortByLandings(List<Mission> missions) {
        List<Mission> sortedMissions;
        missions.sort(compareByLandings);
        sortedMissions = missions;
        return sortedMissions;
    }

    private Comparator<Mission> compareByAirKills = (mission1, mission2) -> Integer.compare(mission2.getAirKills(), mission1.getAirKills());
    private Comparator<Mission> compareByGroundKills = (mission1, mission2) -> Integer.compare(mission2.getGroundKills(), mission1.getGroundKills());
    private Comparator<Mission> compareByDeaths = (mission1, mission2) -> Integer.compare(mission2.getDeaths(), mission1.getDeaths());
    private Comparator<Mission> compareByLandings = (mission1, mission2) -> Integer.compare(mission2.getLandings(), mission1.getLandings());

}
