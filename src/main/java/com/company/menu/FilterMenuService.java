package com.company.menu;

import com.company.Input.ConsoleInput;
import com.company.Mission;
import com.company.display.NormalDisplay;
import com.company.display.FilterMenuOptions;
import com.company.persistence.FileReaderService;
import com.company.persistence.FormatterService;
import com.company.persistence.ListPersistence;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class FilterMenuService {

    NormalDisplay normalDisplay;
    ConsoleInput consoleInput;
    FormatterService formatterService;
    ListPersistence listPersistence;
    FileReaderService fileReaderService;
    BasicValidationService basicValidationService;


    public void filterMissions(List<String> availablePlayers) {
        boolean exit = false;
        final String wayOfFilteringMissions = "How would you like to filter all the missions";
        normalDisplay.printMsg(PlayerMessages.ADD_PLAYER_NAME_MSG);
        String playerName = consoleInput.getPlayerName();
        if (!basicValidationService.isPlayerAvailable(playerName, availablePlayers)) {
            return;
        }
        List<Mission> missions = formatterService.convertMissionInStringFormatToObject(fileReaderService.readFile(playerName + MissionMessages.MISSIONS_FILE_SUFFIX));
        addAllMissions(missions);
        normalDisplay.printMsg(wayOfFilteringMissions);
        while (!exit) {
            printFilterMenuOptions();
            String action = consoleInput.getPlayerName();
            switch (action) {
                case "1":
                    normalDisplay.printMsg(MissionMessages.ADD_AIRCRAFT_NAME_MSG);
                    String aircraftName = consoleInput.getAircraftName();
                    if (basicValidationService.isModuleAvailable(aircraftName)) {
                        if (listPersistence.getMissionByAircraft(aircraftName).isEmpty()) {
                            normalDisplay.printMsg(GenericMessages.EMPTY_MISSION_LIST_MSG);
                            break;
                        }
                        normalDisplay.printMissions(listPersistence.getMissionByAircraft(aircraftName));
                    }
                    break;
                case "2":
                    if (listPersistence.sortByScore().isEmpty()) {
                        normalDisplay.printMsg(GenericMessages.EMPTY_MISSION_LIST_MSG);
                        break;
                    }
                    normalDisplay.printMissions(listPersistence.sortByScore());
                    break;
                case "3":
                    if (listPersistence.sortByAirKills().isEmpty()) {
                        normalDisplay.printMsg(GenericMessages.EMPTY_MISSION_LIST_MSG);
                        break;
                    }
                    normalDisplay.printMissions(listPersistence.sortByAirKills());
                    break;
                case "4":
                    if (listPersistence.sortByGroundKills().isEmpty()) {
                        normalDisplay.printMsg(GenericMessages.EMPTY_MISSION_LIST_MSG);
                        break;
                    }
                    normalDisplay.printMissions(listPersistence.sortByGroundKills());
                    break;
                case "5":
                    if (listPersistence.sortByDeaths().isEmpty()) {
                        normalDisplay.printMsg(GenericMessages.EMPTY_MISSION_LIST_MSG);
                        break;
                    }
                    normalDisplay.printMissions(listPersistence.sortByDeaths());
                case "6":
                    if (listPersistence.sortByLandings().isEmpty()) {
                        normalDisplay.printMsg(GenericMessages.EMPTY_MISSION_LIST_MSG);
                        break;
                    }
                    normalDisplay.printMissions(listPersistence.sortByLandings());
                    break;
                case "7":
                    listPersistence.clearAll();
                    exit = true;
                    break;
            }
        }
    }

    private void addAllMissions(List<Mission> missions) {
        for (Mission mission : missions) {
            listPersistence.addMission(mission);
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
