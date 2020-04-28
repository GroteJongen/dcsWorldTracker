package com.company.menu;

import com.company.Input.InputContext;
import com.company.Mission;
import com.company.display.DisplayContext;
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

    DisplayContext displayContext;
    InputContext inputContext;
    FormatterService formatterService;
    PersistenceContext persistenceContext;
    FileReaderService fileReaderService;
    BasicValidationService basicValidationService;


    public void filterMissions(List<String> availablePlayers) {
        boolean exit = false;
        final String wayOfFilteringMissions = "How would you like to filter all the missions";
        displayContext.printMsg(PlayerMessages.ADD_PLAYER_NAME_MSG);
        String playerName = inputContext.getPlayerName();
        if (!basicValidationService.isPlayerAvailable(playerName, availablePlayers)) {
            return;
        }
        List<Mission> missions = formatterService.convertMissionInStringFormatToObject(fileReaderService.readFile(playerName + MissionMessages.MISSIONS_FILE_SUFFIX));
        addAllMissions(missions);
        displayContext.printMsg(wayOfFilteringMissions);
        while (!exit) {
            printFilterMenuOptions();
            String action = inputContext.getPlayerName();
            switch (action) {
                case "1":
                    displayContext.printMsg(MissionMessages.ADD_AIRCRAFT_NAME_MSG);
                    String aircraftName = inputContext.getAircraftName();
                    if (basicValidationService.isModuleAvailable(aircraftName)) {
                        if (persistenceContext.getMissionByAircraft(aircraftName).isEmpty()) {
                            displayContext.printMsg(GenericMessages.EMPTY_MISSION_LIST_MSG);
                            break;
                        }
                        displayContext.printMissions(persistenceContext.getMissionByAircraft(aircraftName));
                    }
                    break;
                case "2":
                    if (persistenceContext.sortByScore().isEmpty()) {
                        displayContext.printMsg(GenericMessages.EMPTY_MISSION_LIST_MSG);
                        break;
                    }
                    displayContext.printMissions(persistenceContext.sortByScore());
                    break;
                case "3":
                    if (persistenceContext.sortByAirKills().isEmpty()) {
                        displayContext.printMsg(GenericMessages.EMPTY_MISSION_LIST_MSG);
                        break;
                    }
                    displayContext.printMissions(persistenceContext.sortByAirKills());
                    break;
                case "4":
                    if (persistenceContext.sortByGroundKills().isEmpty()) {
                        displayContext.printMsg(GenericMessages.EMPTY_MISSION_LIST_MSG);
                        break;
                    }
                    displayContext.printMissions(persistenceContext.sortByGroundKills());
                    break;
                case "5":
                    if (persistenceContext.sortByDeaths().isEmpty()) {
                        displayContext.printMsg(GenericMessages.EMPTY_MISSION_LIST_MSG);
                        break;
                    }
                    displayContext.printMissions(persistenceContext.sortByDeaths());
                case "6":
                    if (persistenceContext.sortByLandings().isEmpty()) {
                        displayContext.printMsg(GenericMessages.EMPTY_MISSION_LIST_MSG);
                        break;
                    }
                    displayContext.printMissions(persistenceContext.sortByLandings());
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
        displayContext.printOptions(filterMenuOptions);
    }

}
