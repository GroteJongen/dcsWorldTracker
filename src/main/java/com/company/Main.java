package com.company;

import com.company.Input.ConsoleInputStrategy;
import com.company.Input.InputContext;
import com.company.Input.InputStrategy;
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
        InputStrategy inputStrategy = new ConsoleInputStrategy();
        InputContext inputContext = new InputContext(inputStrategy);
        NormalDisplay normalDisplay = new NormalDisplay();
        PersistenceStrategy persistenceStrategy = new ListPersistence(filterService);
        PersistenceContext persistenceContext = new PersistenceContext(persistenceStrategy);
        PlayerService playerService = new PlayerService(inputContext, fileWriterService, fileReaderService, normalDisplay);
        BasicValidationService basicValidationService = new BasicValidationService(inputContext, normalDisplay);
        UpdateMissionService updateMissionService = new UpdateMissionService(normalDisplay, inputContext, persistenceContext, fileReaderService, formatterService, basicValidationService, fileWriterService);
        MissionService missionService = new MissionService(normalDisplay, inputContext, basicValidationService, fileReaderService, fileWriterService);
        FilterMenuService filterMenuService = new FilterMenuService(normalDisplay,inputContext,formatterService,persistenceContext,fileReaderService,basicValidationService);


        GameController gameController = new GameController(inputContext, normalDisplay, persistenceContext, fileWriterService, fileReaderService
                , formatterService, calculateTotalPlayerScoreService, playerService, missionService, updateMissionService,filterMenuService,basicValidationService);
        gameController.startTheApp();


    }

}
