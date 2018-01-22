package com.frachid.masterMind.business;

import java.util.ArrayList;
import java.util.List;

public class MasterMindUtils {

    public static final int SECRET_LENGHT = 5;
    public static final int MAX_ESSAIE = 9;
    public static final String TOUT_BON = SECRET_LENGHT+"V0X";
    public static final String GAGNE = "gagne";
    public static final String PERDU = "perdu";


    public static List<Response> buildResponses() {
        List<Response> responses = new ArrayList<Response>();
        for(int exact=0;exact<=SECRET_LENGHT;exact++){
            for(int inexact=0;inexact<=SECRET_LENGHT-exact;inexact++){
                // TODO: enlever le cas 4V1X pour 5 par ex
                Response response = new Response(exact,inexact);
                responses.add(response);
            }
        }
        return responses;
    }
    public static List<String> buildTheoreticalSolutions() {
        List<String> solutions = new ArrayList<String>();
        for(int i=0;i<Math.pow(10,SECRET_LENGHT);i++){
            String solution = buildStringFromInt(i);
            solutions.add(solution);
        }
        return solutions;
    }
    public static String buildStringFromInt (int solutionInt){
        String solution = ""+solutionInt;
        for(int lenght = solution.length(); lenght<SECRET_LENGHT;lenght++){
            solution = "0"+solution;
        }
        return solution;
    }
    public static String buildSecret() {
        Integer random = (int) (Math.random()*Math.pow(10,SECRET_LENGHT));
        return MasterMindUtils.buildStringFromInt(random);
    }
}
