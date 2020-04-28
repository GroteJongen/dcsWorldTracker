package com.company.persistence;

import com.company.Input.InputContext;
import com.company.display.DisplayContext;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class BasicValidationService {
    private static final String NO_SUCH_PLAYER_MSG = "Player does not exist";
    private InputContext inputContext;
    private DisplayContext displayContext;
    int getValidInteger() {
        final String wrongNumberFormatMsg = "This field needs to be an integer";
        while (true) {
            try {
                return Integer.parseInt(inputContext.getCommand());
            } catch (NumberFormatException e) {
                displayContext.printMsg(wrongNumberFormatMsg);
            }
        }
    }
    boolean isPlayerAvailable(String playerName, List<String> players) {
        if (!players.contains(playerName)) {
            displayContext.printMsg(NO_SUCH_PLAYER_MSG);
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
