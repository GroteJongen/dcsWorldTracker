package com.company.display;

import com.company.Mission;
import com.company.persistence.Player;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
//TODO Delete display context and simplyfy all the stuff.
public class DisplayContext {
    private static final String GREETING = "Hallo dear user";
    private static final String GOODBYE = "Goodbye dear user";
    private List<String> filterMenuOptions =
            Arrays.stream(FilterMenuOptions.values())
                    .map(FilterMenuOptions::getFilterMenuOptions)
                    .collect(Collectors.toList());
    private List<String> updateMenuOptions = Arrays.stream(UpdateMenuOptions.values())
            .map(UpdateMenuOptions::getOption).collect(Collectors.toList());
    private List<String> mainMenuOptions = Arrays.stream(MainMenuOptions.values())
            .map(MainMenuOptions::getMenuOptions).collect(Collectors.toList());
    private DisplayStrategy displayStrategy;

    public DisplayContext(DisplayStrategy displayStrategy) {
        this.displayStrategy = displayStrategy;
    }

    public void printMainMenu() {
        displayStrategy.printList(mainMenuOptions);
    }

    public void printGreeting() {
        displayStrategy.printMessage(GREETING);
    }

    public void printGoodbye() {
        displayStrategy.printMessage(GOODBYE);
    }

    public void printMissions(List<Mission> missions) {
        displayStrategy.printMissions(missions);
    }

    public void printOptions(List<String> options) {
        displayStrategy.printList(options);
    }

    public void printMsg(String msg) {
        displayStrategy.printMessage(msg);
    }

    public void printFilterMenuOptions() {
        displayStrategy.printList(filterMenuOptions);
    }

    public void printPlayerLog(Player player) {
        displayStrategy.printPlayerLog(player);
    }

    public void printUpdateOptions() {
        displayStrategy.printList(updateMenuOptions);
    }
}
