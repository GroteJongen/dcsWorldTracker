package com.company;

import com.company.Input.ConsoleInputStrategy;
import com.company.Input.InputContext;
import com.company.Input.InputStrategy;
import com.company.persistence.*;
import com.company.display.DisplayContext;
import com.company.display.DisplayStrategy;
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
        DisplayStrategy displayStrategy = new NormalDisplay();
        DisplayContext displayContext = new DisplayContext(displayStrategy);
        PersistenceStrategy persistenceStrategy = new ListPersistence(filterService);
        PersistenceContext persistenceContext = new PersistenceContext(persistenceStrategy);
        PlayerService playerService = new PlayerService(inputContext, fileWriterService, fileReaderService, displayContext);
        BasicValidationService basicValidationService = new BasicValidationService(inputContext, displayContext);
        UpdateMissionService updateMissionService = new UpdateMissionService(displayContext, inputContext, persistenceContext, fileReaderService, formatterService, basicValidationService, fileWriterService);
        MissionService missionService = new MissionService(displayContext, inputContext, basicValidationService, fileReaderService, fileWriterService);
        FilterMenuService filterMenuService = new FilterMenuService(displayContext,inputContext,formatterService,persistenceContext,fileReaderService,basicValidationService);


        GameController gameController = new GameController(inputContext, displayContext, persistenceContext, fileWriterService, fileReaderService
                , formatterService, calculateTotalPlayerScoreService, playerService, missionService, updateMissionService,filterMenuService);
        gameController.startTheApp();


    }

}
