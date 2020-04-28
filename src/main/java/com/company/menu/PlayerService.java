package com.company.menu;


import com.company.Input.InputContext;
import com.company.display.DisplayContext;
import com.company.persistence.FileReaderService;
import com.company.persistence.FileWriterService;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class PlayerService {
    private InputContext inputContext;
    private FileWriterService fileWriterService;
    private FileReaderService fileReaderService;
    private DisplayContext displayContext;


    public void addPlayer(List<String> players) {
        final String playerIsAlreadyOnList = "Players is already present on the list";
        List<String> newPlayers = new ArrayList<>();
        try {
            displayContext.printMsg(PlayerMessages.ADD_PLAYER_NAME_MSG);
            String playerName = inputContext.getPlayerName();
            if (players.contains(playerName)) {
                displayContext.printMsg(playerIsAlreadyOnList);
                return;
            }
            players.add(playerName);
            fileWriterService.savePlayer(players, PlayerMessages.PLAYER_FILE);
        } catch (UnsupportedOperationException error) {
            displayContext.printMsg(PlayerMessages.EMPTY_PLAYERS_LIST);
            String playerName = inputContext.getPlayerName();
            newPlayers.add(playerName);
            fileWriterService.savePlayer(newPlayers, PlayerMessages.PLAYER_FILE);
        }

    }

    public List<String> loadPlayers() {
        return fileReaderService.readFile(PlayerMessages.PLAYER_FILE);

    }


}
