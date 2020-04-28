package com.company.persistence;

import com.company.Mission;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileWriterService {
    private final String EXCEPTION_MSG = "No file detected";

    public void writeLine(Mission mission, String fileName) {
        final String semicolon = ":";
        final String newLine = "\n";
        String groundKills = Integer.toString(mission.getGroundKills());
        String airKills = Integer.toString(mission.getAirKills());
        String landings = Integer.toString(mission.getLandings());
        String deaths = Integer.toString(mission.getDeaths());
        String score = Integer.toString(mission.getScore());
        String formattedLine = mission.getPlayerName() + semicolon + mission.getMissionName() + semicolon + mission.getAircraftName()
                + semicolon + score
                + semicolon + groundKills + semicolon + airKills + semicolon + landings
                + semicolon + deaths + semicolon + mission.getIsVictory() + newLine;

        try (FileWriter fileWriter = new FileWriter(fileName, true)) {
            fileWriter.write(formattedLine);

        } catch (IOException e) {
            System.out.println(EXCEPTION_MSG);
        }
    }

    public void writeLines(List<Mission> missions, String fileName) {
        for (Mission mission : missions) {
            overWriteLines(mission, fileName);
        }
    }

    public void savePlayer(List<String> playerLog, String fileName) {
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            for (String playerInformation : playerLog) {
                fileWriter.write(playerInformation + "\n");
            }
        } catch (IOException e) {
            System.out.println(EXCEPTION_MSG);
        }
    }

    private void overWriteLines(Mission mission, String fileName) {
        final String semicolon = ":";
        final String newLine = "\n";
        String groundKills = Integer.toString(mission.getGroundKills());
        String airKills = Integer.toString(mission.getAirKills());
        String landings = Integer.toString(mission.getLandings());
        String deaths = Integer.toString(mission.getDeaths());
        String score = Integer.toString(mission.getScore());
        String formattedLine = mission.getPlayerName() + semicolon + mission.getMissionName() + semicolon + mission.getAircraftName()
                + semicolon + score
                + semicolon + groundKills + semicolon + airKills + semicolon + landings
                + semicolon + deaths + semicolon + mission.getIsVictory() + newLine;

        try (FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write(formattedLine);

        } catch (IOException e) {
            System.out.println(EXCEPTION_MSG);
        }
    }

}
