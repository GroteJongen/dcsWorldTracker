package com.company.menu;

import com.company.Input.ConsoleInput;
import com.company.display.NormalDisplay;
import com.company.persistence.Modules;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class BasicValidationService {
    private ConsoleInput consoleInput;
    private NormalDisplay normalDisplay;
    int getValidInteger() {
        final String wrongNumberFormatMsg = "This field needs to be an integer";
        while (true) {
            try {
                return Integer.parseInt(consoleInput.getCommand());
            } catch (NumberFormatException e) {
                normalDisplay.printMsg(wrongNumberFormatMsg);
            }
        }
    }
    public boolean isPlayerAvailable(String playerName, List<String> players) {
        if (!players.contains(playerName)) {
            normalDisplay.printMsg(PlayerMessages.NO_SUCH_PLAYER_MSG);
            return false;
        }
        return true;
    }
    public boolean isModuleAvailable(String moduleName) {
        List<String> modules =
                Arrays.stream(Modules.values()).map(Modules::getModuleName).collect(Collectors.toList());
        return modules.contains(moduleName);
    }
}
