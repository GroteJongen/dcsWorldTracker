package com.company.Persistence;


import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileReaderService {

    public List<String> readFile(String filename) {
        try (FileReader fileReader = new FileReader(filename);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            List<String> flashcards = new ArrayList<>();
            String line = bufferedReader.readLine().trim();

            while (line != null) {
                flashcards.add(line);
                line = bufferedReader.readLine();
            }
            return flashcards;

        } catch (IOException e) {
            String EXCEPTION_MSG = "No file detected";
            System.out.println(EXCEPTION_MSG);
            return Collections.emptyList();
        }
    }
  /*  public List<Player> readJsonFile(){
        List<Player> players = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Player player = objectMapper.readValue(new File("C:\\Users\\Marek\\dcsWorldTracker\\player.json"), Player.class);
            players.add(player);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return players;
    }*/
}
