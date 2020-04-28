package com.company.display;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UpdateMenuOptions {
    MISSION_NAME("Update mission name"),
    MISSION_PLAYER_NAME("Update player name"),
    MISSION_SCORE("Update mission score"),
    AIRCRAFT_NAME("Update aircraft name"),
    GROUND_KILLS("Update ground kills"),
    AIR_KILLS("Update air kills"),
    DEATHS("Update deaths"),
    LANDINGS("Update landings"),
    SAVE_AND_EXIT("Save and exit"),
    ABORT("Abort");


    private final String option;

}
