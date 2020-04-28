package com.company;

import com.company.Input.ConsoleInput;
import com.company.menu.*;
import com.company.persistence.*;
import com.company.display.NormalDisplay;


public class Main {

    public static void main(String[] args) {
        FileReaderService fileReaderService = new FileReaderService();
        FormatterService formatterService = new FormatterService();
        FilterService filterService = new FilterService();
        FileWriterService fileWriterService = new FileWriterService();
        CalculateTotalPlayerScoreService calculateTotalPlayerScoreService = new CalculateTotalPlayerScoreService();
        ConsoleInput consoleInput = new ConsoleInput();
        NormalDisplay normalDisplay = new NormalDisplay();
        ListPersistence listPersistence = new ListPersistence(filterService);
        PlayerService playerService = new PlayerService(consoleInput, fileWriterService, fileReaderService, normalDisplay);
        BasicValidationService basicValidationService = new BasicValidationService(consoleInput, normalDisplay);
        UpdateMissionService updateMissionService = new UpdateMissionService(normalDisplay, consoleInput, listPersistence, fileReaderService, formatterService, basicValidationService, fileWriterService);
        MissionService missionService = new MissionService(normalDisplay, consoleInput, basicValidationService, fileReaderService, fileWriterService);
        FilterMenuService filterMenuService = new FilterMenuService(normalDisplay, consoleInput,formatterService, listPersistence,fileReaderService,basicValidationService);


        GameController gameController = new GameController(consoleInput, normalDisplay, listPersistence, fileWriterService, fileReaderService
                , formatterService, calculateTotalPlayerScoreService, playerService, missionService, updateMissionService,filterMenuService,basicValidationService);
        gameController.startTheApp();


    }

}
