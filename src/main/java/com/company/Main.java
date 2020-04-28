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
        PlayerService playerService = new PlayerService(inputContext,fileWriterService,fileReaderService,displayContext);
        ValidateIntegerService validateIntegerService = new ValidateIntegerService(inputContext,displayContext);
        MissionService missionService = new MissionService(displayContext,inputContext,validateIntegerService,fileReaderService,fileWriterService);
        GameController gameController = new GameController(inputContext, displayContext, persistenceContext, fileWriterService, fileReaderService
                , formatterService, calculateTotalPlayerScoreService,playerService,missionService);
        gameController.startTheApp();



    }

}
