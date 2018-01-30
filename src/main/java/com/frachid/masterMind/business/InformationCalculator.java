package com.frachid.masterMind.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.frachid.masterMind.business.MasterMindUtils.EPSILON;

public class InformationCalculator {

    private static List<Response> possibleReponses = MasterMindUtils.buildResponses();



    public static List<String>  calculateInfoGeneratedByResponse(Response response, String guess, List<String> possibleSolutions) {

        List<String> newPossibleSolutions = new ArrayList<String>();
        GuessComparator comparator = new GuessComparator();
        int numberOfStillPossibleCombinAfterAsking = 0;
        int numberOfStillPossibleCombinBeforAsking = possibleSolutions.size();
        for(String possibleSolution:possibleSolutions){
            if(response.equals(comparator.compareStringCombinAsResponse(guess,possibleSolution))){
                numberOfStillPossibleCombinAfterAsking++;
                newPossibleSolutions.add(possibleSolution);
            }
        }
        boolean log = false;
        double probability = (double)((double)numberOfStillPossibleCombinAfterAsking/(double)numberOfStillPossibleCombinBeforAsking);
        double informationReceived = -log(probability,2);
        if(log){
            System.out.println("avant de poser votre question vous aviez "+numberOfStillPossibleCombinBeforAsking+" possibilites");
            System.out.println("maintenant vous avez "+numberOfStillPossibleCombinAfterAsking+" possibilites");
            System.out.println("la probabilite d avoir cette reponse etait de  "+probability);
            System.out.println("vous avez alors recu une information de  "+informationReceived);
        }

        System.out.println("informationReceived = " + String.format( "%.2f", informationReceived ));
        possibleSolutions=newPossibleSolutions;

        return newPossibleSolutions;
    }

    public static double calculateEntropy(String guess, List<String> possibleSolutions) {

        int numberOfStillPossibleCombinBeforAsking = possibleSolutions.size();
        Map<Response,Integer> possibilityMap = buildPossibilityMap();
        for(String possibleSolution : possibleSolutions){
            GuessComparator comparator = new GuessComparator();
            Response response = comparator.compareStringCombinAsResponse(guess,possibleSolution);
            final Integer oldValue = possibilityMap.get(response);
            possibilityMap.put(response,oldValue+1);
        }
        Map<Response,Double> probabilityMap = buildProbabilityMap(possibilityMap, numberOfStillPossibleCombinBeforAsking);
        Double entropy = calculateEntropy(probabilityMap);
        return entropy;

    }

    private static Double calculateEntropy(Map<Response, Double> probabilityMap) {
        Double entropy = 0.0;
        for(Response possibleReponse : possibleReponses){
            double probability = probabilityMap.get(possibleReponse);
            if(probability!=0.0){
                double information = -log(probability,2);
                double entropyToAdd = probability*information;
                entropy += entropyToAdd;
            }
        }
        return entropy;
    }

    private static Map<Response,Double> buildProbabilityMap(Map<Response, Integer> possibilityMap, int numberOfStillPossibleCombinBeforAsking) {

        Map<Response,Double> probabilityMap = new HashMap<Response,Double>();
        for(Response possibleReponse : possibleReponses){
            int possibilityNumber = possibilityMap.get(possibleReponse);
            double probability = (double)((double)possibilityNumber/(double)numberOfStillPossibleCombinBeforAsking);
            probabilityMap.put(possibleReponse,probability);
        }
        return probabilityMap;
    }

    private static Map<Response,Integer> buildPossibilityMap() {
        Map<Response,Integer> possibilityMap = new HashMap<Response,Integer>();
        for(Response possibleReponse : possibleReponses){
            possibilityMap.put(possibleReponse,0);
        }
        return possibilityMap;
    }
    static double log(double nombre, int base){
        return (Math.log(nombre) / Math.log(base));
    }

    public static void processResponse(Essaie essaie) {
        List<String> newPossibleSolutions = new ArrayList<String>();
        GameSituation gameSituation = essaie.getGameSituation();
        List<String> possibleSolutions = gameSituation.getPossibleSolutions();
        String guess = essaie.getGuess();
        Response response = essaie.getResponse();
        GuessComparator comparator = new GuessComparator();
        int numberOfStillPossibleCombinAfterAsking = 0;
        int numberOfStillPossibleCombinBeforAsking = possibleSolutions.size();
        for(String possibleSolution:possibleSolutions){
            if(response.equals(comparator.compareStringCombinAsResponse(guess,possibleSolution))){
                numberOfStillPossibleCombinAfterAsking++;
                newPossibleSolutions.add(possibleSolution);
            }
        }
        boolean log = false;
        double probability = (double)((double)numberOfStillPossibleCombinAfterAsking/(double)numberOfStillPossibleCombinBeforAsking);
        double generatedInfo = -log(probability,2);
        if(log){
            System.out.println("avant de poser votre question vous aviez "+numberOfStillPossibleCombinBeforAsking+" possibilites");
            System.out.println("maintenant vous avez "+numberOfStillPossibleCombinAfterAsking+" possibilites");
            System.out.println("la probabilite d avoir cette reponse etait de  "+probability);
            System.out.println("vous avez alors recu une information de  "+generatedInfo);
            System.out.println("informationReceived = " + String.format( "%.2f", generatedInfo ));
        }



        gameSituation.setPossibleSolutions(newPossibleSolutions);
        essaie.setGeneratedInfo(generatedInfo);
    }

    public static String theGuessThatGivesTheBiggestEntropy(List<String> possibleSolutions) {
        String theGuessThatGivesTheBiggestEntropy = "";
        Double bestEntropy = 0.0;
        List<String> possibleGuess = MasterMindUtils.buildTheoreticalSolutions();
        for (String guess : possibleGuess) {
            Double entropy = calculateEntropy(guess, possibleSolutions);
            System.out.println("(guess;entropy)  : " + guess + " ; " + String.format("%.2f", entropy));

            if (entropy > bestEntropy || (bestEntropy - entropy < EPSILON && possibleSolutions.contains(guess) && !possibleSolutions.contains(
                    theGuessThatGivesTheBiggestEntropy))) {
                theGuessThatGivesTheBiggestEntropy = guess;
                bestEntropy = entropy;
            }
        }

        System.out.println("_____________________________________________________________________");
        System.out.println("________________________The Best Guess is____________________________");
        System.out.println("_____________________________________________________________________");
        System.out.println(theGuessThatGivesTheBiggestEntropy + " ; " + String.format("%.2f", bestEntropy));
        System.out.println("_____________________________________________________________________");
        if (possibleSolutions.contains(theGuessThatGivesTheBiggestEntropy)) {
            System.out.println("________________and it is a possible solution____________________");
        } else {
            System.out.println("________________but it isn't a possible solution_________________");
        }
        return theGuessThatGivesTheBiggestEntropy;

    }
}
