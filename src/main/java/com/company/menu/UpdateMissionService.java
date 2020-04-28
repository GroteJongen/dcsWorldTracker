package com.company.menu;

import com.company.Input.ConsoleInput;
import com.company.Mission;
import com.company.display.NormalDisplay;
import com.company.display.UpdateMenuOptions;
import com.company.persistence.FileReaderService;
import com.company.persistence.FileWriterService;
import com.company.persistence.FormatterService;
import com.company.persistence.ListPersistence;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class UpdateMissionService {


    NormalDisplay normalDisplay;
    ConsoleInput consoleInput;
    ListPersistence listPersistence;
    FileReaderService fileReaderService;
    FormatterService formatterService;
    BasicValidationService basicValidationService;
    FileWriterService fileWriterService;

    public void updateMission(List<String> players) {
        final String changesSavedMsg = "Changes have been saved";
        final String abortingUpdates = "Aborting updates";
        normalDisplay.printMsg(PlayerMessages.ADD_PLAYER_NAME_MSG);
        String playerName = consoleInput.getPlayerName();
        if (!basicValidationService.isPlayerAvailable(playerName, players)) {
            return;
        }
        List<Mission> missions = formatterService.convertMissionInStringFormatToObject(fileReaderService.readFile(playerName + MissionMessages.MISSIONS_FILE_SUFFIX));
        addAllMissions(missions);
        normalDisplay.printMsg(MissionMessages.ADD_MISSION_NAME_MSG);
        String missionName = consoleInput.getMissionName();
        if (listPersistence.searchMission(missionName) == null) {
            normalDisplay.printMsg(MissionMessages.MISSION_DOES_NOT_EXIST);
            return;
        }
        Mission missionToUpdate = listPersistence.searchMission(missionName);
        boolean isRunning = true;
        while (isRunning) {
            printMenuOptions();
            String command = consoleInput.getCommand();

            switch (command) {
                case "1":
                    normalDisplay.printMsg(MissionMessages.ADD_MISSION_NAME_MSG);
                    missionToUpdate.setMissionName(consoleInput.getMissionName());
                    break;
                case "2":
                    normalDisplay.printMsg(PlayerMessages.ADD_PLAYER_NAME_MSG);
                    missionToUpdate.setPlayerName(consoleInput.getPlayerName());
                    break;
                case "3":
                    normalDisplay.printMsg(MissionMessages.ADD_MISSION_SCORE_MSG);
                    missionToUpdate.setScore(basicValidationService.getValidInteger());
                    break;
                case "4":
                    normalDisplay.printMsg(MissionMessages.ADD_AIRCRAFT_NAME_MSG);
                    missionToUpdate.setAircraftName(consoleInput.getAircraftName());
                    break;
                case "5":
                    normalDisplay.printMsg(MissionMessages.ADD_MISSION_GROUND_KILLS_MSG);
                    missionToUpdate.setGroundKills(basicValidationService.getValidInteger());
                    break;
                case "6":
                    normalDisplay.printMsg(MissionMessages.ADD_MISSION_AIR_KILLS_MSG);
                    missionToUpdate.setAirKills(basicValidationService.getValidInteger());
                    break;
                case "7":
                    normalDisplay.printMsg(MissionMessages.ADD_MISSION_DEATHS_MSG);
                    missionToUpdate.setDeaths(basicValidationService.getValidInteger());
                    break;
                case "8":
                    normalDisplay.printMsg(MissionMessages.ADD_MISSION_LANDINGS_MSG);
                    missionToUpdate.setLandings(basicValidationService.getValidInteger());
                    break;
                case "9":
                    listPersistence.update(missionToUpdate, missionName);
                    fileWriterService.writeLines(listPersistence.getMissionsByPlayerName(playerName), playerName + MissionMessages.MISSIONS_FILE_SUFFIX);
                    normalDisplay.printMsg(changesSavedMsg);
                    listPersistence.clearAll();
                    isRunning = false;
                    break;
                case "10":
                    isRunning = false;
                    normalDisplay.printMsg(abortingUpdates);
                    listPersistence.clearAll();
                    break;
            }

        }
    }

    private void addAllMissions(List<Mission> missions) {
        for (Mission mission : missions) {
            listPersistence.addMission(mission);
        }
    }

    private void printMenuOptions() {
        List<String> updateMenuOptions = Arrays.stream(UpdateMenuOptions.values())
                .map(UpdateMenuOptions::getOption).collect(Collectors.toList());
        normalDisplay.printList(updateMenuOptions);
    }
}
