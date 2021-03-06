package com.frachid.masterMind.business;

import java.util.List;

public class Essaie {

    private GameSituation gameSituation;
    private String guess;
    private Double entropy;
    private Double generatedInfo;

    public void setEntropy(Double entropy) {
        this.entropy = entropy;
    }

    public Double getGeneratedInfo() {
        return generatedInfo;
    }

    public void setGeneratedInfo(Double generatedInfo) {
        this.generatedInfo = generatedInfo;
    }

    public GameSituation getGameSituation() {
        return gameSituation;
    }

    public void setGameSituation(GameSituation gameSituation) {
        this.gameSituation = gameSituation;
    }

    public String getGuess() {
        return guess;
    }

    public void setGuess(String guess) {
        this.guess = guess;
    }

    public Essaie(GameSituation gameSituation, String guess) {
        this.gameSituation = gameSituation;
        this.guess = guess;
    }
    public Response getResponse(){

        String secret = gameSituation.getSecret();
        GuessComparator comparator = new GuessComparator();
        return comparator.compareStringCombinAsResponse(secret,guess);
    }
    public void  calculateInfoGeneratedByResponse() {
        List<String> newPossibleSolutions = InformationCalculator.calculateInfoGeneratedByResponse(getResponse(),guess,gameSituation.getPossibleSolutions());
        gameSituation.setPossibleSolutions(newPossibleSolutions);
    }

    /**
     * Calculate the information generated by the Essaie
     * update the possibleSolutions In gameSituation
     */
    public void processResponse(){
        InformationCalculator.processResponse(this);
    }
    public Double getEntropy(){
        return InformationCalculator.calculateEntropy(guess,gameSituation.getPossibleSolutions());
    }
}
