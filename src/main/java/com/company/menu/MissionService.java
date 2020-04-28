package com.company.menu;

import com.company.Input.InputContext;
import com.company.Mission;
import com.company.display.NormalDisplay;
import com.company.persistence.FileReaderService;
import com.company.persistence.FileWriterService;
import com.company.persistence.Modules;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class MissionService {
    private NormalDisplay normalDisplay;
    private InputContext inputContext;
    private BasicValidationService basicValidationService;
    private FileReaderService fileReaderService;
    private FileWriterService fileWriterService;

    public void addMission(List<String> availablePlayers) {
        normalDisplay.printMsg(PlayerMessages.AVAILABLE_PLAYERS_MSG);
        normalDisplay.printList(availablePlayers);
        normalDisplay.printMsg(PlayerMessages.AVAILABLE_PLAYER_MODULES_MSG);
        printModules();
        normalDisplay.printMsg(PlayerMessages.ADD_PLAYER_NAME_MSG);
        String playerName = inputContext.getPlayerName();
        if (!isPlayerAvailable(playerName)) {
            return;
        }
        normalDisplay.printMsg(MissionMessages.ADD_MISSION_NAME_MSG);
        String missionName = inputContext.getMissionName();
        String aircraftName = getValidAircraft();
        normalDisplay.printMsg(MissionMessages.ADD_MISSION_SCORE_MSG);
        int missionScore = basicValidationService.getValidInteger();
        normalDisplay.printMsg(MissionMessages.ADD_MISSION_AIR_KILLS_MSG);
        int airKills = basicValidationService.getValidInteger();
        normalDisplay.printMsg(MissionMessages.ADD_MISSION_GROUND_KILLS_MSG);
        int groundKills = basicValidationService.getValidInteger();
        normalDisplay.printMsg(MissionMessages.ADD_MISSION_DEATHS_MSG);
        int deaths = basicValidationService.getValidInteger();
        normalDisplay.printMsg(MissionMessages.ADD_MISSION_LANDINGS_MSG);
        int landings = basicValidationService.getValidInteger();
        normalDisplay.printMsg(MissionMessages.ADD_MISSION_WIN_OR_LOOSE_MSG);
        String missionResult = inputContext.getMissionResult();
        Mission mission = new Mission(playerName, missionName, aircraftName, groundKills, airKills, missionScore, landings, deaths, missionResult);
        fileWriterService.writeLine(mission, playerName + MissionMessages.MISSIONS_FILE_SUFFIX);
    }

    private boolean isModuleAvailable(String moduleName) {
        List<String> modules =
                Arrays.stream(Modules.values()).map(Modules::getModuleName).collect(Collectors.toList());
        return modules.contains(moduleName);
    }

    private void printModules() {
        List<String> modules =
                Arrays.stream(Modules.values()).map(Modules::getModuleName).collect(Collectors.toList());
        normalDisplay.printList(modules);
    }

    private boolean isPlayerAvailable(String playerName) {
        List<String> players = fileReaderService.readFile(PlayerMessages.PLAYER_FILE);
        if (!players.contains(playerName)) {
            normalDisplay.printMsg(PlayerMessages.NO_SUCH_PLAYER_MSG);
            return false;
        }
        return true;
    }

    private String getValidAircraft() {
        String validAircraft;
        while (true) {
            normalDisplay.printMsg(MissionMessages.ADD_AIRCRAFT_NAME_MSG);
            validAircraft = inputContext.getAircraftName();
            if (!isModuleAvailable(validAircraft)) {
                normalDisplay.printMsg(GenericMessages.NO_SUCH_MODULE_MSG);
                continue;
            }
            return validAircraft;
        }
    }
}
