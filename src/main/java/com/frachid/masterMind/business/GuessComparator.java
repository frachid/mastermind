package com.frachid.masterMind.business;

public class GuessComparator {

    public String compareStringCombinAsString(String guess, String secret){
        Response reponse = compareStringCombinAsResponse(guess,secret);
        return reponse.toString();
    }
    public Response compareStringCombinAsResponse(String guess, String secret){
        String result = null;
        String copySecret = secret;
        int exact = 0;
        int inexact = 0;
        int minLength = Math.min(guess.length(), secret.length());
        for(int i=0;i<minLength;i++){
            char c=guess.charAt(i);
            int indexOfFirstC = copySecret.indexOf(c);
            if(indexOfFirstC>=0){
                copySecret = deleteCharAt(copySecret,indexOfFirstC);
                if(secret.charAt(i)==c){
                    exact++;
                }else{
                    inexact++;
                }
            }
        }
        return new Response(exact,inexact);
    }
    public String comparer(final String guess, final String secret) {
        int v = 0;
        int x = 0;
        final char[] G = guess.toCharArray();
        final char[] S = secret.toCharArray();

        for (int i = 0; i < secret.length(); i++) {
            for (int j = 0; j < guess.length(); j++) {
                if (G[j] == S[j]) {
                    v++;
                    G[j] = "*".charAt(0);
                    S[j] = "!".charAt(0);

                } else if (G[j] == S[i] && i != j) {
                    x++;
                    G[j] = "*".charAt(0);
                    S[i] = "!".charAt(0);
                }

            }

        }

        final String resultat = v + "V" + x + "X";
        System.out.println(resultat);
        return resultat;
    }
    private static String deleteCharAt(String strValue, int index) {
        return strValue.substring(0, index) + strValue.substring(index + 1);

    }

}
