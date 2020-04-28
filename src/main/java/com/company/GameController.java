package com.company;

import com.company.Input.InputContext;
import com.company.persistence.*;
import com.company.display.DisplayContext;
import lombok.AllArgsConstructor;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
@AllArgsConstructor
class GameController {
    private static final String ADD_PLAYER_NAME_MSG = "Put the player name in";
    private static final String ADD_MISSION_NAME_MSG = "Put the mission name in";
    private static final String ADD_MISSION_SCORE_MSG = "Put the score in";
    private static final String ADD_MISSION_AIR_KILLS_MSG = "Put the air kills in";
    private static final String ADD_MISSION_GROUND_KILLS_MSG = "Put the ground kills in";
    private static final String ADD_MISSION_LANDINGS_MSG = "Put the amount of landings in";
    private static final String ADD_MISSION_DEATHS_MSG = "Put the amount of deaths in";
    private static final String ADD_AIRCRAFT_NAME_MSG = "Put the aircraft name in";
    private static final String NO_SUCH_PLAYER_MSG = "Player does not exist";
    private static final String PLAYER_FILE = "players";
    private static final String MISSIONS_FILE_SUFFIX = "missions";
    private static final String EMPTY_MISSION_LIST_MSG = "You don't have any missions";
    private static final String MISSION_DOES_NOT_EXIST = "Mission does not exist";
    private InputContext inputContext;
    private DisplayContext displayContext;
    private PersistenceContext persistenceContext;
    private FileWriterService fileWriterService;
    private FileReaderService fileReaderService;
    private FormatterService formatterService;
    private CalculateTotalPlayerScoreService calculateTotalPlayerScoreService;
    private PlayerService playerService;
    private MissionService missionService;


    void startTheApp() {
        boolean isRunning = true;
        List<String> listOfPlayers =  playerService.loadPlayers();
        displayContext.printGreeting();
        while (isRunning) {
            displayContext.printMainMenu();
            String command = inputContext.getCommand();
            switch (command) {
                case "1":
                    missionService.addMission(listOfPlayers);
                    break;
                case "2":
                    removeMission();
                    break;
                case "3":
                    updateMission();
                    break;
                case "4":
                    searchMission();
                    break;
                case "5":
                    displayPlayerLog();
                    break;
                case "6":
                    addPlayer(listOfPlayers);
                    break;
                case "7":
                    displayPlayerMissions();
                    break;
                case "8":
                    displayPlayersAndModules();
                    break;
                case "9":
                    filterMissions();
                    break;
                case "10":
                    isRunning = false;
                    displayContext.printGoodbye();
                    break;
            }

        }
    }


    private void displayPlayersAndModules() {
        displayContext.printOptions(fileReaderService.readFile(PLAYER_FILE));
        printModules();

    }

    private void removeMission() {
        displayContext.printMsg(ADD_PLAYER_NAME_MSG);
        String playerName = inputContext.getPlayerName();
        if (!isPlayerAvailable(playerName)) {
            return;
        }
        List<Mission> missions = formatterService.convertMissionInStringFormatToObject(fileReaderService.readFile(playerName + MISSIONS_FILE_SUFFIX));
        for (Mission mission : missions) {
            persistenceContext.addMission(mission);
        }
        displayContext.printMsg(ADD_MISSION_NAME_MSG);
        String missionName = inputContext.getMissionName();
        if (persistenceContext.searchMission(missionName) == null) {
            displayContext.printMsg(MISSION_DOES_NOT_EXIST);
            return;
        }
        fileWriterService.writeLines(persistenceContext.getMissionsByPlayerName(playerName), playerName + MISSIONS_FILE_SUFFIX);
        persistenceContext.remove(missionName);
        persistenceContext.clearAll();
    }

    private void updateMission() {
        final String changesSavedMsg = "Changes have been saved";
        final String abortingUpdates = "Aborting updates";
        displayContext.printMsg(ADD_PLAYER_NAME_MSG);
        String playerName = inputContext.getPlayerName();
        if (!isPlayerAvailable(playerName)) {
            return;
        }
        List<Mission> missions = formatterService.convertMissionInStringFormatToObject(fileReaderService.readFile(playerName + MISSIONS_FILE_SUFFIX));
        addAllMissions(missions);
        displayContext.printMsg(ADD_MISSION_NAME_MSG);
        String missionName = inputContext.getMissionName();
        if (persistenceContext.searchMission(missionName) == null) {
            displayContext.printMsg(MISSION_DOES_NOT_EXIST);
            return;
        }
        Mission missionToUpdate = persistenceContext.searchMission(missionName);
        boolean isRunning = true;
        while (isRunning) {
            displayContext.printUpdateOptions();
            String command = inputContext.getCommand();

            switch (command) {
                case "1":
                    displayContext.printMsg(ADD_MISSION_NAME_MSG);
                    missionToUpdate.setMissionName(inputContext.getMissionName());
                    break;
                case "2":
                    displayContext.printMsg(ADD_PLAYER_NAME_MSG);
                    missionToUpdate.setPlayerName(inputContext.getPlayerName());
                    break;
                case "3":
                    displayContext.printMsg(ADD_MISSION_SCORE_MSG);
                    missionToUpdate.setScore(getValidInteger());
                    break;
                case "4":
                    displayContext.printMsg(ADD_AIRCRAFT_NAME_MSG);
                    missionToUpdate.setAircraftName(inputContext.getAircraftName());
                    break;
                case "5":
                    displayContext.printMsg(ADD_MISSION_GROUND_KILLS_MSG);
                    missionToUpdate.setGroundKills(getValidInteger());
                    break;
                case "6":
                    displayContext.printMsg(ADD_MISSION_AIR_KILLS_MSG);
                    missionToUpdate.setAirKills(getValidInteger());
                    break;
                case "7":
                    displayContext.printMsg(ADD_MISSION_DEATHS_MSG);
                    missionToUpdate.setDeaths(getValidInteger());
                    break;
                case "8":
                    displayContext.printMsg(ADD_MISSION_LANDINGS_MSG);
                    missionToUpdate.setLandings(getValidInteger());
                    break;
                case "9":
                    persistenceContext.update(missionName, missionToUpdate);
                    fileWriterService.writeLines(persistenceContext.getMissionsByPlayerName(playerName), playerName + MISSIONS_FILE_SUFFIX);
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

    private void addPlayer(List<String> players){
        playerService.addPlayer(players);
    }
    private void filterMissions() {
        boolean exit = false;
        final String wayOfFilteringMissions = "How would you like to filter all the missions";
        displayContext.printMsg(ADD_PLAYER_NAME_MSG);
        String playerName = inputContext.getPlayerName();
        if (!isPlayerAvailable(playerName)) {
            return;
        }
        List<Mission> missions = formatterService.convertMissionInStringFormatToObject(fileReaderService.readFile(playerName + MISSIONS_FILE_SUFFIX));
        addAllMissions(missions);
        displayContext.printMsg(wayOfFilteringMissions);
        while (!exit) {
            displayContext.printFilterMenuOptions();
            String action = inputContext.getPlayerName();
            switch (action) {
                case "1":
                    displayContext.printMsg(ADD_AIRCRAFT_NAME_MSG);
                    String aircraftName = inputContext.getAircraftName();
                    if (isModuleAvailable(aircraftName)) {
                        if (persistenceContext.getMissionByAircraft(aircraftName).isEmpty()) {
                            displayContext.printMsg(EMPTY_MISSION_LIST_MSG);
                            break;
                        }
                        displayContext.printMissions(persistenceContext.getMissionByAircraft(aircraftName));
                    }
                    break;
                case "2":
                    if (persistenceContext.sortByScore().isEmpty()) {
                        displayContext.printMsg(EMPTY_MISSION_LIST_MSG);
                        break;
                    }
                    displayContext.printMissions(persistenceContext.sortByScore());
                    break;
                case "3":
                    if (persistenceContext.sortByAirKills().isEmpty()) {
                        displayContext.printMsg(EMPTY_MISSION_LIST_MSG);
                        break;
                    }
                    displayContext.printMissions(persistenceContext.sortByAirKills());
                    break;
                case "4":
                    if (persistenceContext.sortByGroundKills().isEmpty()) {
                        displayContext.printMsg(EMPTY_MISSION_LIST_MSG);
                        break;
                    }
                    displayContext.printMissions(persistenceContext.sortByGroundKills());
                    break;
                case "5":
                    if (persistenceContext.sortByDeaths().isEmpty()) {
                        displayContext.printMsg(EMPTY_MISSION_LIST_MSG);
                        break;
                    }
                    displayContext.printMissions(persistenceContext.sortByDeaths());
                case "6":
                    if (persistenceContext.sortByLandings().isEmpty()) {
                        displayContext.printMsg(EMPTY_MISSION_LIST_MSG);
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

    private void displayPlayerLog() {
        displayContext.printMsg(ADD_PLAYER_NAME_MSG);
        String playerName = inputContext.getPlayerName();
        if (!isPlayerAvailable(playerName)) {
            return;
        }
        List<Mission> missions = formatterService.convertMissionInStringFormatToObject(fileReaderService.readFile(playerName + MISSIONS_FILE_SUFFIX));
        addAllMissions(missions);
        Player player = new Player(playerName);
        calculateTotalPlayerScoreService.setPlayerLog(player, persistenceContext.getMissionsByPlayerName(playerName));
        displayContext.printPlayerLog(player);
        persistenceContext.clearAll();

    }

    private void displayPlayerMissions() {
        displayContext.printMsg(ADD_PLAYER_NAME_MSG);
        String playerName = inputContext.getPlayerName();
        List<String> players = fileReaderService.readFile(PLAYER_FILE);
        if (players.contains(playerName)) {
            List<Mission> missions = formatterService.convertMissionInStringFormatToObject(fileReaderService.readFile(playerName + MISSIONS_FILE_SUFFIX));
            addAllMissions(missions);
            if (persistenceContext.getMissionsByPlayerName(playerName).isEmpty()) {
                displayContext.printMsg(EMPTY_MISSION_LIST_MSG);
                return;
            }
            displayContext.printMissions(persistenceContext.getMissionsByPlayerName(playerName));
            persistenceContext.clearAll();
            return;
        }
        displayContext.printMsg(NO_SUCH_PLAYER_MSG);
    }

    private void searchMission() {
        displayContext.printMsg(ADD_MISSION_NAME_MSG);
        List<Mission> foundMissions = new ArrayList<>();
        String missionName = inputContext.getMissionName();
        Mission mission = persistenceContext.searchMission(missionName);
        if(mission == null){
            displayContext.printMsg("Mission not found");
            return;
        }
        foundMissions.add(mission);
        displayContext.printMissions(foundMissions);
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

    private boolean isModuleAvailable(String moduleName) {
        List<String> modules =
                Arrays.stream(Modules.values()).map(Modules::getModuleName).collect(Collectors.toList());
        return modules.contains(moduleName);
    }

    private int getValidInteger() {
        final String wrongNumberFormatMsg = "This field needs to be an integer";
        while (true) {
            try {
                return Integer.parseInt(inputContext.getCommand());
            } catch (NumberFormatException e) {
                displayContext.printMsg(wrongNumberFormatMsg);
            }
        }
    }
    private void addAllMissions(List<Mission> missions){
        for (Mission mission : missions) {
            persistenceContext.addMission(mission);
        }
    }
}
