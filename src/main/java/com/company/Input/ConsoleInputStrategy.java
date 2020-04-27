package com.company.Input;

import java.util.Scanner;

public class ConsoleInputStrategy implements InputStrategy {
    private Scanner scanner = new Scanner(System.in);

    @Override
    public String getCommand() {
        return scanner.nextLine();
    }

    @Override
    public String getPlayerName() {
        return scanner.nextLine();
    }

    @Override
    public String getMissionName() {
        return scanner.nextLine();
    }

    @Override
    public String getAircraftName() {
        return scanner.nextLine();
    }

    @Override
    public int getScore() {
        return scanner.nextInt();
    }

    @Override
    public int getGroundKills() {
        return scanner.nextInt();
    }

    @Override
    public int getAirKills() {
        return scanner.nextInt();
    }

    @Override
    public int getLandings() {
        return scanner.nextInt();
    }

    @Override
    public int getDeaths() {
        return scanner.nextInt();
    }

    @Override
    public String getMissionResult() {
        while(true){
            String winOrLoose = scanner.nextLine();
            switch (winOrLoose){
                case "WIN":
                    return "WIN";
                case "LOOSE":
                    return "LOOSE";
                default:
                    System.out.println("put WIN or LOOSE");
            }
        }
    }
}
