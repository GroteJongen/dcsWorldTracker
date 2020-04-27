package com.display;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MainMenuOptions {
    ADD_MISSION("Add mission"),
    REMOVE_MISSION("Remove mission"),
    UPDATE_MISSION("Update mission"),
    SEARCH_MISSION("Search mission"),
    DISPLAY_PLAYER_LOG("Display playerLog"),
    ADD_PLAYER("Add player"),
    SHOW_MISSION("Show player'S missions"),
    SHOW_PLAYERS_AND_MODULES("Show all players and modules"),
    FILTER_MISSIONS("Filter all Missions"),
    EXIT("Exit");



    private final String menuOptions;

}
