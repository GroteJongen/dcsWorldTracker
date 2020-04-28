package com.company.persistence;


import com.company.Input.InputContext;
import com.company.display.DisplayContext;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
public class PlayerService {
    private static final String ADD_PLAYER_NAME_MSG = "put playerName in";
    private static final String PLAYER_FILE = "players";
    private static final String EMPTY_PLAYERS_LIST = "There are no players on your list, you need to add initial one, put the name in";
    private InputContext inputContext;
    private FileWriterService fileWriterService;
    private FileReaderService fileReaderService;
    private DisplayContext displayContext;



    public void addPlayer(List<String> players) {
        final String playerIsAlreadyOnList = "Players is already present on the list";
        List<String> newPlayers = new ArrayList<>();
        try{
            displayContext.printMsg(ADD_PLAYER_NAME_MSG);
            String playerName = inputContext.getPlayerName();
            if (players.contains(playerName)) {
                printMsg(playerIsAlreadyOnList);
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
    public List<String> loadPlayers(){
        return fileReaderService.readFile(PLAYER_FILE);

    }
    private void printMsg(String msg){
        System.out.println(msg);
    }
}
