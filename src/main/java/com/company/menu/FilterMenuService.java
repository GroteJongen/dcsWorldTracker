package com.company.menu;

import com.company.Input.InputContext;
import com.company.Mission;
import com.company.display.NormalDisplay;
import com.company.display.FilterMenuOptions;
import com.company.persistence.FileReaderService;
import com.company.persistence.FormatterService;
import com.company.persistence.PersistenceContext;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class FilterMenuService {

    NormalDisplay normalDisplay;
    InputContext inputContext;
    FormatterService formatterService;
    PersistenceContext persistenceContext;
    FileReaderService fileReaderService;
    BasicValidationService basicValidationService;


    public void filterMissions(List<String> availablePlayers) {
        boolean exit = false;
        final String wayOfFilteringMissions = "How would you like to filter all the missions";
        normalDisplay.printMsg(PlayerMessages.ADD_PLAYER_NAME_MSG);
        String playerName = inputContext.getPlayerName();
        if (!basicValidationService.isPlayerAvailable(playerName, availablePlayers)) {
            return;
        }
        List<Mission> missions = formatterService.convertMissionInStringFormatToObject(fileReaderService.readFile(playerName + MissionMessages.MISSIONS_FILE_SUFFIX));
        addAllMissions(missions);
        normalDisplay.printMsg(wayOfFilteringMissions);
        while (!exit) {
            printFilterMenuOptions();
            String action = inputContext.getPlayerName();
            switch (action) {
                case "1":
                    normalDisplay.printMsg(MissionMessages.ADD_AIRCRAFT_NAME_MSG);
                    String aircraftName = inputContext.getAircraftName();
                    if (basicValidationService.isModuleAvailable(aircraftName)) {
                        if (persistenceContext.getMissionByAircraft(aircraftName).isEmpty()) {
                            normalDisplay.printMsg(GenericMessages.EMPTY_MISSION_LIST_MSG);
                            break;
                        }
                        normalDisplay.printMissions(persistenceContext.getMissionByAircraft(aircraftName));
                    }
                    break;
                case "2":
                    if (persistenceContext.sortByScore().isEmpty()) {
                        normalDisplay.printMsg(GenericMessages.EMPTY_MISSION_LIST_MSG);
                        break;
                    }
                    normalDisplay.printMissions(persistenceContext.sortByScore());
                    break;
                case "3":
                    if (persistenceContext.sortByAirKills().isEmpty()) {
                        normalDisplay.printMsg(GenericMessages.EMPTY_MISSION_LIST_MSG);
                        break;
                    }
                    normalDisplay.printMissions(persistenceContext.sortByAirKills());
                    break;
                case "4":
                    if (persistenceContext.sortByGroundKills().isEmpty()) {
                        normalDisplay.printMsg(GenericMessages.EMPTY_MISSION_LIST_MSG);
                        break;
                    }
                    normalDisplay.printMissions(persistenceContext.sortByGroundKills());
                    break;
                case "5":
                    if (persistenceContext.sortByDeaths().isEmpty()) {
                        normalDisplay.printMsg(GenericMessages.EMPTY_MISSION_LIST_MSG);
                        break;
                    }
                    normalDisplay.printMissions(persistenceContext.sortByDeaths());
                case "6":
                    if (persistenceContext.sortByLandings().isEmpty()) {
                        normalDisplay.printMsg(GenericMessages.EMPTY_MISSION_LIST_MSG);
                        break;
                    }
                    normalDisplay.printMissions(persistenceContext.sortByLandings());
                    break;
                case "7":
                    persistenceContext.clearAll();
                    exit = true;
                    break;
            }
        }
    }

    private void addAllMissions(List<Mission> missions) {
        for (Mission mission : missions) {
            persistenceContext.addMission(mission);
        }
    }

    private void printFilterMenuOptions() {
        List<String> filterMenuOptions =
                Arrays.stream(FilterMenuOptions.values())
                        .map(FilterMenuOptions::getFilterMenuOptions)
                        .collect(Collectors.toList());
        normalDisplay.printList(filterMenuOptions);
    }

}
