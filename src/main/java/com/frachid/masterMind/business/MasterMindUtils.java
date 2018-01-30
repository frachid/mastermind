package com.frachid.masterMind.business;

import java.util.ArrayList;
import java.util.List;

public class MasterMindUtils {

    public static final int MAX_ESSAIE = 9;
    public static final int MAX_ENTROPY = 5;
    public static final String GAGNE = "gagne";
    public static final String PERDU = "perdu";
    public static final Double EPSILON = 0.01;

    public static List<Response> buildResponses(int secretLength) {
        List<Response> responses = new ArrayList<Response>();
        for (int exact = 0; exact <= secretLength; exact++) {
            for (int inexact = 0; inexact <= secretLength - exact; inexact++) {
                // TODO: enlever le cas 4V1X pour 5 par ex
                Response response = new Response(exact,inexact);
                responses.add(response);
            }
        }
        return responses;
    }

    public static List<String> buildTheoreticalSolutions(int secretLength) {
        List<String> solutions = new ArrayList<String>();
        for (int i = 0; i < Math.pow(10, secretLength); i++) {
            String solution = buildStringFromInt(i, secretLength);
            solutions.add(solution);
        }
        return solutions;
    }

    public static String buildStringFromInt(int solutionInt, int secretLength) {
        String solution = ""+solutionInt;
        for (int lenght = solution.length(); lenght < secretLength; lenght++) {
            solution = "0"+solution;
        }
        return solution;
    }

    public static String buildSecret(int secretLength) {
        Integer random = (int) (Math.random() * Math.pow(10, secretLength));
        return MasterMindUtils.buildStringFromInt(random, secretLength);
    }
}
