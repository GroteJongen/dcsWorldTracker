package com.company.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.List;

public class JsonFileWriter {
    public void writeToFile(List<Player> players){
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            objectMapper.writeValue(new File("player.json"),players);
        }catch (Exception e){
            System.out.println("Something went wrong");
        }
    }
}
