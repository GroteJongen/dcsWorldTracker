package com.company.display;

import com.company.Mission;
import com.company.persistence.Player;

import java.util.List;

public class NormalDisplay {
    private final String playerName = "Player name: ";
    private final String airKills = "Air kills: ";
    private final String groundKills = "Ground kills: ";
    private final String deaths = "Deaths: ";
    private final String landings = "Landings: ";
    private final String score = "Score: ";
    private final String BOARDER = "***********************************************";
    private final String NEW_LINE = "\n";
    private final String arrow = " --> ";


    public void printList(List<String> options) {

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(BOARDER + NEW_LINE);
        for (int i = 0; i < options.size(); i++) {

            stringBuilder.append(i + 1).append(arrow).append(options.get(i)).append(NEW_LINE);
        }
        stringBuilder.append(BOARDER + NEW_LINE);
        System.out.println(stringBuilder);
    }


    public void printMsg(String message) {
        String messageToPrint = (BOARDER + NEW_LINE) + message + NEW_LINE + BOARDER + NEW_LINE;
        System.out.print(messageToPrint);
    }


    public void printMissions(List<Mission> missions) {
        final String missionName = "Mission name: ";
        final String aircraftName = "Aircraft: ";
        final String winOrLose = "WIN/LOSE: ";
        StringBuilder stringBuilder = new StringBuilder();
        String divider = " | ";
        stringBuilder.append(BOARDER + NEW_LINE);
        for (int i = 0; i < missions.size(); i++) {

            stringBuilder.append(i + 1).append(arrow).append(playerName)
                    .append(missions.get(i).getPlayerName()).append(divider).append(missionName)
                    .append(missions.get(i).getMissionName()).append(divider)
                    .append(aircraftName).append(missions.get(i).getAircraftName()).append(divider)
                    .append(airKills).append(missions.get(i).getAirKills()).append(divider)
                    .append(groundKills).append(missions.get(i).getGroundKills()).append(divider)
                    .append(score).append(missions.get(i).getScore()).append(divider).append(divider)
                    .append(landings).append(missions.get(i).getLandings()).append(divider)
                    .append(deaths).append(missions.get(i).getDeaths()).append(divider)
                    .append(winOrLose).append(missions.get(i).getIsVictory()).append(divider).append(NEW_LINE);

        }

        stringBuilder.append(BOARDER + NEW_LINE);
        System.out.println(stringBuilder);
    }

    public void printPlayerLog(Player player) {
        final String victories = "Victories: ";
        final String missionsFlown = "Missions flown: ";
        final String loses = "loses: ";
        System.out.println(playerName + player.getPlayerName() + " | " + score + player.getTotalScore() + " | " + airKills + player.getTotalAirKills()
                + " | " + groundKills + player.getTotalGroundKills() + " | " + deaths + player.getTotalDeaths() + " | " + landings + player.getTotalLandings() + " | " + missionsFlown
                + player.getTotalMissions() + " | " + victories + player.getTotalVictories() + " | " + loses + player.getTotalLooses());
    }
}
