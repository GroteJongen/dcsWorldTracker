package com.company;

import com.company.Input.InputContext;
import com.company.menu.*;
import com.company.persistence.*;
import com.company.display.DisplayContext;
import lombok.AllArgsConstructor;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
class GameController {


    private InputContext inputContext;
    private DisplayContext displayContext;
    private PersistenceContext persistenceContext;
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
        displayContext.printGreeting();
        while (isRunning) {
            displayContext.printMainMenu();
            String command = inputContext.getCommand();
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
                    displayContext.printGoodbye();
                    break;
            }

        }
    }


    private void displayPlayersAndModules() {
        displayContext.printOptions(fileReaderService.readFile(PlayerMessages.PLAYER_FILE));
        printModules();

    }

    private void removeMission(List<String> players) {
        displayContext.printMsg(PlayerMessages.ADD_PLAYER_NAME_MSG);
        String playerName = inputContext.getPlayerName();
        if (!basicValidationService.isPlayerAvailable(playerName, players)) {
            return;
        }
        List<Mission> missions = formatterService.convertMissionInStringFormatToObject(fileReaderService.readFile(playerName + MissionMessages.MISSIONS_FILE_SUFFIX));
        for (Mission mission : missions) {
            persistenceContext.addMission(mission);
        }
        displayContext.printMsg(MissionMessages.ADD_MISSION_NAME_MSG);
        String missionName = inputContext.getMissionName();
        if (persistenceContext.searchMission(missionName) == null) {
            displayContext.printMsg(MissionMessages.MISSION_DOES_NOT_EXIST);
            return;
        }
        fileWriterService.writeLines(persistenceContext.getMissionsByPlayerName(playerName), playerName + MissionMessages.MISSIONS_FILE_SUFFIX);
        persistenceContext.remove(missionName);
        persistenceContext.clearAll();
    }


    private void addPlayer(List<String> players) {
        playerService.addPlayer(players);
    }

    private void displayPlayerLog(List<String> players) {
        displayContext.printMsg(PlayerMessages.ADD_PLAYER_NAME_MSG);
        String playerName = inputContext.getPlayerName();
        if (!basicValidationService.isPlayerAvailable(playerName, players)) {
            return;
        }
        List<Mission> missions = formatterService.convertMissionInStringFormatToObject(fileReaderService.readFile(playerName + MissionMessages.MISSIONS_FILE_SUFFIX));
        addAllMissions(missions);
        Player player = new Player(playerName);
        calculateTotalPlayerScoreService.setPlayerLog(player, persistenceContext.getMissionsByPlayerName(playerName));
        displayContext.printPlayerLog(player);
        persistenceContext.clearAll();

    }

    private void displayPlayerMissions() {
        displayContext.printMsg(PlayerMessages.ADD_PLAYER_NAME_MSG);
        String playerName = inputContext.getPlayerName();
        List<String> players = fileReaderService.readFile(PlayerMessages.PLAYER_FILE);
        if (players.contains(playerName)) {
            List<Mission> missions = formatterService.convertMissionInStringFormatToObject(fileReaderService.readFile(playerName + MissionMessages.MISSIONS_FILE_SUFFIX));
            addAllMissions(missions);
            if (persistenceContext.getMissionsByPlayerName(playerName).isEmpty()) {
                displayContext.printMsg(MissionMessages.EMPTY_MISSION_LIST_MSG);
                return;
            }
            displayContext.printMissions(persistenceContext.getMissionsByPlayerName(playerName));
            persistenceContext.clearAll();
            return;
        }
        displayContext.printMsg(PlayerMessages.NO_SUCH_PLAYER_MSG);
    }

    private void searchMission(List<String> players) {
        final String addPlayerNameMsg = "put player name in";
        displayContext.printMsg(addPlayerNameMsg);
        String playerName = inputContext.getPlayerName();
        if (basicValidationService.isPlayerAvailable(playerName, players)) {
            displayContext.printMsg(PlayerMessages.NO_SUCH_PLAYER_MSG);
            return;
        }
        List<Mission> missions = formatterService.convertMissionInStringFormatToObject(fileReaderService.readFile(playerName + MissionMessages.MISSIONS_FILE_SUFFIX));
        addAllMissions(missions);
        displayContext.printMsg(MissionMessages.ADD_MISSION_NAME_MSG);
        List<Mission> foundMissions = new ArrayList<>();
        String missionName = inputContext.getMissionName();
        Mission mission = persistenceContext.searchMission(missionName);
        if (mission == null) {
            displayContext.printMsg("Mission not found");
            return;
        }
        foundMissions.add(mission);
        displayContext.printMissions(foundMissions);
        persistenceContext.clearAll();
    }

    private void printModules() {
        List<String> modules =
                Arrays.stream(Modules.values()).map(Modules::getModuleName).collect(Collectors.toList());
        displayContext.printOptions(modules);
    }

    private void addAllMissions(List<Mission> missions) {
        for (Mission mission : missions) {
            persistenceContext.addMission(mission);
        }
    }
}
