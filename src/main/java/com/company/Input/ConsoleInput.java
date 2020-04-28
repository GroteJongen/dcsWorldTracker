package com.company.Input;

import java.util.Scanner;

public class ConsoleInput {
    private Scanner scanner = new Scanner(System.in);


    public String getCommand() {
        return scanner.nextLine();
    }


    public String getPlayerName() {
        return scanner.nextLine();
    }


    public String getMissionName() {
        return scanner.nextLine();
    }


    public String getAircraftName() {
        return scanner.nextLine();
    }


    public String getMissionResult() {
        while (true) {
            System.out.println("Put WIN or LOOSE in");
            String winOrLoose = scanner.nextLine();
            if (winOrLoose.equals("WIN")) {
                return "WIN";
            }
            if (winOrLoose.equals("LOOSE")) {
                return "LOOSE";
            }
        }
    }
}
