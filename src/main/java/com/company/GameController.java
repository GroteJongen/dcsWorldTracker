package com.company;

import com.company.Input.InputContext;
import com.company.persistence.*;
import com.company.display.DisplayContext;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class GameController {
    private static final String ADD_PLAYER_NAME_MSG = "Put the player name in";
    private static final String ADD_MISSION_NAME_MSG = "Put the mission name in";
    private static final String ADD_MISSION_SCORE_MSG = "Put the score in";
    private static final String ADD_MISSION_AIR_KILLS_MSG = "Put the air kills in";
    private static final String ADD_MISSION_GROUND_KILLS_MSG = "Put the ground kills in";
    private static final String ADD_MISSION_LANDINGS_MSG = "Put the amount of landings in";
    private static final String ADD_MISSION_DEATHS_MSG = "Put the amount of deaths in";
    private static final String ADD_MISSION_WIN_OR_LOOSE_MSG = "Put the win or loose in";
    private static final String ADD_AIRCRAFT_NAME_MSG = "Put the aircraft name in";
    private static final String AVAILABLE_PLAYER_MODULES_MSG = "Available player modules";
    private static final String AVAILABLE_PLAYERS_MSG = "Available players";
    private static final String NO_SUCH_MODULE_MSG = "There is no such'a module on your list";
    private static final String NO_SUCH_PLAYER_MSG = "Player does not exist";
    private static final String PLAYER_FILE = "players";
    private static final String MISSIONS_FILE_SUFFIX = "missions";
    private static final String EMPTY_MISSION_LIST_MSG = "You don't have any missions";
    private static final String MISSION_DOES_NOT_EXIST = "Mission does not exist";
    private static final String EMPTY_PLAYERS_LIST = "No players on list, you need to create new one";
    private InputContext inputContext;
    private DisplayContext displayContext;
    private PersistenceContext persistenceContext;
    private FileWriterService fileWriterService;
    private FileReaderService fileReaderService;
    private FormatterService formatterService;
    private CalculateTotalPlayerScoreService calculateTotalPlayerScoreService;


    GameController(InputContext inputContext, DisplayContext displayContext, PersistenceContext persistenceContext,
                   FileWriterService fileWriterService, FileReaderService fileReaderService, FormatterService formatterService,
                   CalculateTotalPlayerScoreService calculateTotalPlayerScoreService) {
        this.inputContext = inputContext;
        this.displayContext = displayContext;
        this.persistenceContext = persistenceContext;
        this.fileWriterService = fileWriterService;
        this.fileReaderService = fileReaderService;
        this.formatterService = formatterService;
        this.calculateTotalPlayerScoreService = calculateTotalPlayerScoreService;
    }

    void startTheApp() {
        boolean isRunning = true;
        List<String> listOfPlayers = fileReaderService.readFile(PLAYER_FILE);
        displayContext.printGreeting();
        while (isRunning) {
            displayContext.printMainMenu();
            String command = inputContext.getCommand();
            switch (command) {
                case "1":
                    addMission();
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

    private void addMission() {
        displayContext.printMsg(AVAILABLE_PLAYERS_MSG);
        displayContext.printOptions(fileReaderService.readFile(PLAYER_FILE));
        displayContext.printMsg(AVAILABLE_PLAYER_MODULES_MSG);
        printModules();
        displayContext.printMsg(ADD_PLAYER_NAME_MSG);
        String playerName = inputContext.getPlayerName();
        if (!isPlayerAvailable(playerName)) {
            return;
        }
        displayContext.printMsg(ADD_MISSION_NAME_MSG);
        String missionName = inputContext.getMissionName();
        displayContext.printMsg(ADD_AIRCRAFT_NAME_MSG);
        String aircraftName = inputContext.getAircraftName();
        if (!isModuleAvailable(aircraftName)) {
            displayContext.printMsg(NO_SUCH_MODULE_MSG);
            return;
        }
        displayContext.printMsg(ADD_MISSION_SCORE_MSG);
        int missionScore = getValidInteger();
        displayContext.printMsg(ADD_MISSION_AIR_KILLS_MSG);
        int airKills = getValidInteger();
        displayContext.printMsg(ADD_MISSION_GROUND_KILLS_MSG);
        int groundKills = getValidInteger();
        displayContext.printMsg(ADD_MISSION_DEATHS_MSG);
        int deaths = getValidInteger();
        displayContext.printMsg(ADD_MISSION_LANDINGS_MSG);
        int landings = getValidInteger();
        displayContext.printMsg(ADD_MISSION_WIN_OR_LOOSE_MSG);
        String missionResult = inputContext.getMissionResult();
        Mission mission = new Mission(playerName, missionName, aircraftName, groundKills, airKills, missionScore, landings, deaths, missionResult);
        fileWriterService.writeLine(mission, playerName + MISSIONS_FILE_SUFFIX);
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
    //TODO if there's no file, make a new one.
    //TODO if player's list is empty then ask user to add the first player
    private void addPlayer(List<String> players) {
        final String playerIsAlreadyOnList = "Players is already present on the list";
        List<String> newPlayers = new ArrayList<>();
        try{
            displayContext.printMsg(ADD_PLAYER_NAME_MSG);
            String playerName = inputContext.getPlayerName();
            if (players.contains(playerName)) {
                displayContext.printMsg(playerIsAlreadyOnList);
                return;
            }
            players.add(playerName);
            fileWriterService.savePlayer(players, PLAYER_FILE);
        }catch (UnsupportedOperationException error){
            displayContext.printMsg(EMPTY_PLAYERS_LIST);
            String playerName = inputContext.getPlayerName();
            newPlayers.add(playerName);
            fileWriterService.savePlayer(newPlayers,PLAYER_FILE);
        }

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
