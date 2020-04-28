package com.company.display;


import com.company.Mission;
import com.company.persistence.Player;

import java.util.List;

public interface DisplayStrategy {

    void printList(List<String> list);

    void printMessage(String msg);

    void printMissions(List<Mission> missions);

    void printPlayerLog(Player player);


}
