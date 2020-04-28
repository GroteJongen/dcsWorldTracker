package com.company.persistence;

import com.company.Mission;

import java.util.ArrayList;
import java.util.List;

public class FormatterService {

    public List<Mission> convertMissionInStringFormatToObject(List<String> lines) {
        String semicolon = ":";
        List<Mission> formatterMissions = new ArrayList<>();
        for (String line : lines) {
            String playerName = line.split(semicolon)[0];
            String missionName = line.split(semicolon)[1];
            String aircraft = line.split(semicolon)[2];
            int score = Integer.parseInt(line.split(semicolon)[3]);
            int groundKills = Integer.parseInt(line.split(semicolon)[4]);
            int airKills = Integer.parseInt(line.split(semicolon)[5]);
            int landings = Integer.parseInt(line.split(semicolon)[6]);
            int deaths = Integer.parseInt(line.split(semicolon)[7]);
            String isVictory = line.split(semicolon)[8];
            formatterMissions.add(new Mission(playerName, missionName, aircraft, groundKills, airKills, score, landings, deaths, isVictory));
        }
        return formatterMissions;
    }

}
