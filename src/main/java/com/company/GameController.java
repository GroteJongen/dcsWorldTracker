package com.company;

import com.company.Input.ConsoleInput;
import com.company.display.NormalDisplay;
import com.company.display.MainMenuOptions;
import com.company.menu.*;
import com.company.persistence.*;
import lombok.AllArgsConstructor;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
class GameController {


    private ConsoleInput consoleInput;
    private NormalDisplay normalDisplay;
    private ListPersistence listPersistence;
    private FileWriterService fileWriterService;
    private FileReaderService fileReaderService;
    private FormatterService formatterService;
    private CalculateTotalPlayerScoreService calculateTotalPlayerScoreService;
    private PlayerService playerService;
    private MissionService missionService;
    private UpdateMissionService updateMissionService;
    private FilterMenuService filterMenuService;
    private BasicValidationService basicValidationService;


    void startTheApp() {
        boolean isRunning = true;
        List<String> listOfPlayers = playerService.loadPlayers();
        normalDisplay.printMsg(GenericMessages.GREETING);
        while (isRunning) {
            printMenuOptions();
            String command = consoleInput.getCommand();
            switch (command) {
                case "1":
                    missionService.addMission(listOfPlayers);
                    break;
                case "2":
                    removeMission(listOfPlayers);
                    break;
                case "3":
                    updateMissionService.updateMission(listOfPlayers);
                    break;
                case "4":
                    searchMission(listOfPlayers);
                    break;
                case "5":
                    displayPlayerLog(listOfPlayers);
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
                    filterMenuService.filterMissions(listOfPlayers);
                    break;
                case "10":
                    isRunning = false;
                    normalDisplay.printMsg(GenericMessages.GOODBYE);
                    break;
            }

        }
    }


    private void displayPlayersAndModules() {
        normalDisplay.printList(fileReaderService.readFile(PlayerMessages.PLAYER_FILE));
        printModules();

    }

    private void removeMission(List<String> players) {
        normalDisplay.printMsg(PlayerMessages.ADD_PLAYER_NAME_MSG);
        String playerName = consoleInput.getPlayerName();
        if (!basicValidationService.isPlayerAvailable(playerName, players)) {
            return;
        }
        List<Mission> missions = formatterService.convertMissionInStringFormatToObject(fileReaderService.readFile(playerName + MissionMessages.MISSIONS_FILE_SUFFIX));
        for (Mission mission : missions) {
            listPersistence.addMission(mission);
        }
        normalDisplay.printMsg(MissionMessages.ADD_MISSION_NAME_MSG);
        String missionName = consoleInput.getMissionName();
        if (listPersistence.searchMission(missionName) == null) {
            normalDisplay.printMsg(MissionMessages.MISSION_DOES_NOT_EXIST);
            return;
        }
        fileWriterService.writeLines(listPersistence.getMissionsByPlayerName(playerName), playerName + MissionMessages.MISSIONS_FILE_SUFFIX);
        listPersistence.remove(missionName);
        listPersistence.clearAll();
    }


    private void addPlayer(List<String> players) {
        playerService.addPlayer(players);
    }

    private void displayPlayerLog(List<String> players) {
        normalDisplay.printMsg(PlayerMessages.ADD_PLAYER_NAME_MSG);
        String playerName = consoleInput.getPlayerName();
        if (!basicValidationService.isPlayerAvailable(playerName, players)) {
            return;
        }
        List<Mission> missions = formatterService.convertMissionInStringFormatToObject(fileReaderService.readFile(playerName + MissionMessages.MISSIONS_FILE_SUFFIX));
        addAllMissions(missions);
        Player player = new Player(playerName);
        calculateTotalPlayerScoreService.setPlayerLog(player, listPersistence.getMissionsByPlayerName(playerName));
        normalDisplay.printPlayerLog(player);
        listPersistence.clearAll();

    }

    private void displayPlayerMissions() {
        normalDisplay.printMsg(PlayerMessages.ADD_PLAYER_NAME_MSG);
        String playerName = consoleInput.getPlayerName();
        List<String> players = fileReaderService.readFile(PlayerMessages.PLAYER_FILE);
        if (players.contains(playerName)) {
            List<Mission> missions = formatterService.convertMissionInStringFormatToObject(fileReaderService.readFile(playerName + MissionMessages.MISSIONS_FILE_SUFFIX));
            addAllMissions(missions);
            if (listPersistence.getMissionsByPlayerName(playerName).isEmpty()) {
                normalDisplay.printMsg(MissionMessages.EMPTY_MISSION_LIST_MSG);
                return;
            }
            normalDisplay.printMissions(listPersistence.getMissionsByPlayerName(playerName));
            listPersistence.clearAll();
            return;
        }
        normalDisplay.printMsg(PlayerMessages.NO_SUCH_PLAYER_MSG);
    }

    private void searchMission(List<String> players) {
        final String addPlayerNameMsg = "put player name in";
        normalDisplay.printMsg(addPlayerNameMsg);
        String playerName = consoleInput.getPlayerName();
        if (basicValidationService.isPlayerAvailable(playerName, players)) {
            normalDisplay.printMsg(PlayerMessages.NO_SUCH_PLAYER_MSG);
            return;
        }
        List<Mission> missions = formatterService.convertMissionInStringFormatToObject(fileReaderService.readFile(playerName + MissionMessages.MISSIONS_FILE_SUFFIX));
        addAllMissions(missions);
        normalDisplay.printMsg(MissionMessages.ADD_MISSION_NAME_MSG);
        List<Mission> foundMissions = new ArrayList<>();
        String missionName = consoleInput.getMissionName();
        Mission mission = listPersistence.searchMission(missionName);
        if (mission == null) {
            normalDisplay.printMsg("Mission not found");
            return;
        }
        foundMissions.add(mission);
        normalDisplay.printMissions(foundMissions);
        listPersistence.clearAll();
    }

    private void printModules() {
        List<String> modules =
                Arrays.stream(Modules.values()).map(Modules::getModuleName).collect(Collectors.toList());
        normalDisplay.printList(modules);
    }

    private void addAllMissions(List<Mission> missions) {
        for (Mission mission : missions) {
            listPersistence.addMission(mission);
        }
    }
    private void printMenuOptions(){
        List<String> mainMenuOptions = Arrays.stream(MainMenuOptions.values())
                .map(MainMenuOptions::getMenuOptions).collect(Collectors.toList());
        normalDisplay.printList(mainMenuOptions);
    }
}
