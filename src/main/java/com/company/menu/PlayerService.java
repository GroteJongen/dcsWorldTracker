package com.company.menu;


import com.company.Input.ConsoleInput;
import com.company.display.NormalDisplay;
import com.company.persistence.FileReaderService;
import com.company.persistence.FileWriterService;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class PlayerService {
    private ConsoleInput consoleInput;
    private FileWriterService fileWriterService;
    private FileReaderService fileReaderService;
    private NormalDisplay normalDisplay;


    public void addPlayer(List<String> players) {
        final String playerIsAlreadyOnList = "Players is already present on the list";
        List<String> newPlayers = new ArrayList<>();
        try {
            normalDisplay.printMsg(PlayerMessages.ADD_PLAYER_NAME_MSG);
            String playerName = consoleInput.getPlayerName();
            if (players.contains(playerName)) {
                normalDisplay.printMsg(playerIsAlreadyOnList);
                return;
            }
            players.add(playerName);
            fileWriterService.savePlayer(players, PlayerMessages.PLAYER_FILE);
        } catch (UnsupportedOperationException error) {
            normalDisplay.printMsg(PlayerMessages.EMPTY_PLAYERS_LIST);
            String playerName = consoleInput.getPlayerName();
            newPlayers.add(playerName);
            fileWriterService.savePlayer(newPlayers, PlayerMessages.PLAYER_FILE);
        }

    }

    public List<String> loadPlayers() {
        return fileReaderService.readFile(PlayerMessages.PLAYER_FILE);

    }


}
