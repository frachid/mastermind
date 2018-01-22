package com.frachid.masterMind.business;

import java.util.List;

public class GameSituation {

    private List<String> possibleSolutions;
    private String secret;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public List<String> getPossibleSolutions() {
        return possibleSolutions;
    }

    public void setPossibleSolutions(List<String> possibleSolutions) {
        this.possibleSolutions = possibleSolutions;
    }

    public GameSituation(List<String> possibleSolutions, String secret) {
        this.possibleSolutions = possibleSolutions;
        this.secret = secret;
    }
    public GameSituation(String secret) {
        this.possibleSolutions = MasterMindUtils.buildTheoreticalSolutions();
        this.secret = secret;
    }

    public Double getNeededInformation(){
        return InformationCalculator.log(possibleSolutions.size(),2);
    }
}
