package com.company.menu;

import com.company.Input.InputContext;
import com.company.Mission;
import com.company.display.DisplayContext;
import com.company.display.UpdateMenuOptions;
import com.company.persistence.FileReaderService;
import com.company.persistence.FileWriterService;
import com.company.persistence.FormatterService;
import com.company.persistence.PersistenceContext;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class UpdateMissionService {


    DisplayContext displayContext;
    InputContext inputContext;
    PersistenceContext persistenceContext;
    FileReaderService fileReaderService;
    FormatterService formatterService;
    BasicValidationService basicValidationService;
    FileWriterService fileWriterService;

    public void updateMission(List<String> players) {
        final String changesSavedMsg = "Changes have been saved";
        final String abortingUpdates = "Aborting updates";
        displayContext.printMsg(PlayerMessages.ADD_PLAYER_NAME_MSG);
        String playerName = inputContext.getPlayerName();
        if (!basicValidationService.isPlayerAvailable(playerName, players)) {
            return;
        }
        List<Mission> missions = formatterService.convertMissionInStringFormatToObject(fileReaderService.readFile(playerName + MissionMessages.MISSIONS_FILE_SUFFIX));
        addAllMissions(missions);
        displayContext.printMsg(MissionMessages.ADD_MISSION_NAME_MSG);
        String missionName = inputContext.getMissionName();
        if (persistenceContext.searchMission(missionName) == null) {
            displayContext.printMsg(MissionMessages.MISSION_DOES_NOT_EXIST);
            return;
        }
        Mission missionToUpdate = persistenceContext.searchMission(missionName);
        boolean isRunning = true;
        while (isRunning) {
            printMenuOptions();
            String command = inputContext.getCommand();

            switch (command) {
                case "1":
                    displayContext.printMsg(MissionMessages.ADD_MISSION_NAME_MSG);
                    missionToUpdate.setMissionName(inputContext.getMissionName());
                    break;
                case "2":
                    displayContext.printMsg(PlayerMessages.ADD_PLAYER_NAME_MSG);
                    missionToUpdate.setPlayerName(inputContext.getPlayerName());
                    break;
                case "3":
                    displayContext.printMsg(MissionMessages.ADD_MISSION_SCORE_MSG);
                    missionToUpdate.setScore(basicValidationService.getValidInteger());
                    break;
                case "4":
                    displayContext.printMsg(MissionMessages.ADD_AIRCRAFT_NAME_MSG);
                    missionToUpdate.setAircraftName(inputContext.getAircraftName());
                    break;
                case "5":
                    displayContext.printMsg(MissionMessages.ADD_MISSION_GROUND_KILLS_MSG);
                    missionToUpdate.setGroundKills(basicValidationService.getValidInteger());
                    break;
                case "6":
                    displayContext.printMsg(MissionMessages.ADD_MISSION_AIR_KILLS_MSG);
                    missionToUpdate.setAirKills(basicValidationService.getValidInteger());
                    break;
                case "7":
                    displayContext.printMsg(MissionMessages.ADD_MISSION_DEATHS_MSG);
                    missionToUpdate.setDeaths(basicValidationService.getValidInteger());
                    break;
                case "8":
                    displayContext.printMsg(MissionMessages.ADD_MISSION_LANDINGS_MSG);
                    missionToUpdate.setLandings(basicValidationService.getValidInteger());
                    break;
                case "9":
                    persistenceContext.update(missionName, missionToUpdate);
                    fileWriterService.writeLines(persistenceContext.getMissionsByPlayerName(playerName), playerName + MissionMessages.MISSIONS_FILE_SUFFIX);
                    displayContext.printMsg(changesSavedMsg);
                    persistenceContext.clearAll();
                    isRunning = false;
                    break;
                case "10":
                    isRunning = false;
                    displayContext.printMsg(abortingUpdates);
                    persistenceContext.clearAll();
                    break;
            }

        }
    }

    private void addAllMissions(List<Mission> missions) {
        for (Mission mission : missions) {
            persistenceContext.addMission(mission);
        }
    }

    private void printMenuOptions() {
        List<String> updateMenuOptions = Arrays.stream(UpdateMenuOptions.values())
                .map(UpdateMenuOptions::getOption).collect(Collectors.toList());
        displayContext.printOptions(updateMenuOptions);
    }
}
