package com.frachid.masterMind.business;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataInfoMatch {
    private List<Response> possibleReponses;
    private List<String> theoreticalSolutions;
    private List<String> possibleSolutions;
    private Map<String,Response> previousGuesses;

    //Constructor
    public DataInfoMatch() {
        possibleReponses = MasterMindUtils.buildResponses();
        theoreticalSolutions = MasterMindUtils.buildTheoreticalSolutions();
        possibleSolutions = MasterMindUtils.buildTheoreticalSolutions();
        previousGuesses = new HashMap<String,Response>();

    }


    //Get Set

    public List<String> getTheoreticalSolutions() {
        return theoreticalSolutions;
    }

    public void setTheoreticalSolutions(List<String> theoreticalSolutions) {
        this.theoreticalSolutions = theoreticalSolutions;
    }

    public List<String> getPossibleSolutions() {
        return possibleSolutions;
    }

    public void setPossibleSolutions(List<String> possibleSolutions) {
        this.possibleSolutions = possibleSolutions;
    }

    public List<Response> getPossibleReponses() {
        return possibleReponses;
    }

    public void setPossibleReponses(List<Response> possibleReponses) {
        this.possibleReponses = possibleReponses;
    }

}
