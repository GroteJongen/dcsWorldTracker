package com.company.persistence;

import com.company.Input.InputContext;
import com.company.Mission;
import com.company.display.DisplayContext;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
@AllArgsConstructor
public class MissionService {
    private static final String AVAILABLE_PLAYERS_MSG = "Available players";
    private static final String AVAILABLE_PLAYER_MODULES_MSG = "Available player modules";
    private static final String ADD_PLAYER_NAME_MSG = "Put the player name in";
    private static final String ADD_MISSION_NAME_MSG = "Put the mission name in";
    private static final String ADD_MISSION_SCORE_MSG = "Put the score in";
    private static final String ADD_MISSION_AIR_KILLS_MSG = "Put the air kills in";
    private static final String ADD_MISSION_GROUND_KILLS_MSG = "Put the ground kills in";
    private static final String ADD_MISSION_LANDINGS_MSG = "Put the amount of landings in";
    private static final String ADD_MISSION_DEATHS_MSG = "Put the amount of deaths in";
    private static final String ADD_MISSION_WIN_OR_LOOSE_MSG = "Put the win or loose in";
    private static final String ADD_AIRCRAFT_NAME_MSG = "Put the aircraft name in";
    private static final String PLAYER_FILE = "players";
    private static final String MISSIONS_FILE_SUFFIX = "missions";
    private static final String NO_SUCH_MODULE_MSG = "There is no such'a module on your list";
    private static final String NO_SUCH_PLAYER_MSG = "Player does not exist";
    private DisplayContext displayContext;
    private InputContext inputContext;
    private BasicValidationService basicValidationService;
    private FileReaderService fileReaderService;
    private FileWriterService fileWriterService;

    public void addMission(List<String> availablePlayers) {
        displayContext.printMsg(AVAILABLE_PLAYERS_MSG);
        displayContext.printOptions(availablePlayers);
        displayContext.printMsg(AVAILABLE_PLAYER_MODULES_MSG);
        printModules();
        displayContext.printMsg(ADD_PLAYER_NAME_MSG);
        String playerName = inputContext.getPlayerName();
        if (!isPlayerAvailable(playerName)) {
            return;
        }
        displayContext.printMsg(ADD_MISSION_NAME_MSG);
        String missionName = inputContext.getMissionName();
        String aircraftName = getValidAircraft();
        displayContext.printMsg(ADD_MISSION_SCORE_MSG);
        int missionScore = basicValidationService.getValidInteger();
        displayContext.printMsg(ADD_MISSION_AIR_KILLS_MSG);
        int airKills = basicValidationService.getValidInteger();
        displayContext.printMsg(ADD_MISSION_GROUND_KILLS_MSG);
        int groundKills = basicValidationService.getValidInteger();
        displayContext.printMsg(ADD_MISSION_DEATHS_MSG);
        int deaths = basicValidationService.getValidInteger();
        displayContext.printMsg(ADD_MISSION_LANDINGS_MSG);
        int landings = basicValidationService.getValidInteger();
        displayContext.printMsg(ADD_MISSION_WIN_OR_LOOSE_MSG);
        String missionResult = inputContext.getMissionResult();
        Mission mission = new Mission(playerName, missionName, aircraftName, groundKills, airKills, missionScore, landings, deaths, missionResult);
        fileWriterService.writeLine(mission, playerName + MISSIONS_FILE_SUFFIX);
    }

    private boolean isModuleAvailable(String moduleName) {
        List<String> modules =
                Arrays.stream(Modules.values()).map(Modules::getModuleName).collect(Collectors.toList());
        return modules.contains(moduleName);
    }

    private void printModules() {
        List<String> modules =
                Arrays.stream(Modules.values()).map(Modules::getModuleName).collect(Collectors.toList());
        displayContext.printOptions(modules);
    }

    private boolean isPlayerAvailable(String playerName) {
        List<String> players = fileReaderService.readFile(PLAYER_FILE);
        if (!players.contains(playerName)) {
            displayContext.printMsg(NO_SUCH_PLAYER_MSG);
            return false;
        }
        return true;
    }

    private String getValidAircraft() {
        String validAircraft;
        while (true) {
            displayContext.printMsg(ADD_AIRCRAFT_NAME_MSG);
            validAircraft = inputContext.getAircraftName();
            if (!isModuleAvailable(validAircraft)) {
                displayContext.printMsg(NO_SUCH_MODULE_MSG);
                continue;
            }
            return validAircraft;
        }
    }
}
