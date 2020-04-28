package com.company.persistence;

import com.company.Input.InputContext;
import com.company.display.DisplayContext;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ValidateIntegerService {
    private InputContext inputContext;
    private DisplayContext displayContext;
    public int getValidInteger() {
        final String wrongNumberFormatMsg = "This field needs to be an integer";
        while (true) {
            try {
                return Integer.parseInt(inputContext.getCommand());
            } catch (NumberFormatException e) {
                displayContext.printMsg(wrongNumberFormatMsg);
            }
        }
    }
}
