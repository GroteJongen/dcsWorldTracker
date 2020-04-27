package com.display;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum FilterMenuOptions {
    FILTER_MISSIONS_BY_AIRCRAFT("Filter missions by aircraft"),
    FILTER_MISSIONS_BY_SCORE("Sort missions by score"),
    FILTER_MISSIONS_BY__AIR_KILLS("Sort missions by air kills"),
    FILTER_MISSIONS_BY_GROUND_KILLS("Sort missions by ground kills"),
    FILTER_MISSIONS_BY_DEATHS("Sort missions by deaths"),
    FILTER_MISSIONS_BY_LANDINGS("Sort missions by landings"),
    EXIT_SUB_MENU("Exit");

    private final String filterMenuOptions;

}
