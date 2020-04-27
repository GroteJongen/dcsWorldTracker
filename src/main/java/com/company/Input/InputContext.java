package com.company.Input;

public class InputContext {
    private InputStrategy inputStrategy;

    public InputContext(InputStrategy inputStrategy) {
        this.inputStrategy = inputStrategy;
    }

    public String getCommand(){
        return inputStrategy.getCommand();
    }
    public String getMissionName(){
        return inputStrategy.getMissionName();
    }
    public String getAircraftName(){
        return inputStrategy.getAircraftName();
    }
    public String getPlayerName(){
        return inputStrategy.getPlayerName();
    }
    public int getScore(){
        return inputStrategy.getScore();
    }
    public int getAirKills(){
        return inputStrategy.getAirKills();
    }
    public int getGroundKills(){
        return inputStrategy.getGroundKills();
    }
    public int getDeaths(){
        return inputStrategy.getDeaths();
    }
    public int getLandings(){
        return inputStrategy.getLandings();
    }
    public String getMissionResult(){
        return inputStrategy.getMissionResult();
    }

}
