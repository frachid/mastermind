package com.frachid;

import com.frachid.masterMind.business.GuessComparator;
import org.junit.*;

import static org.junit.Assert.*;

public class ComparateurResponseTest {

    private static final String TOUT_FAUX = "0V0X";
    private static final String UN_EXACT = "1V0X";
    private static final String UN_INEXACT = "0V1X";
    private static final String DEUX_EXACT_UN_INEXACT = "2V1X";
    private static final String UN_EXACT_TROIS_INEXACT = "1V3X";
    private GuessComparator comparateur = new GuessComparator();

    @Test
    public void totallyDifferentResponses(){
        assertTrue(TOUT_FAUX.equalsIgnoreCase(comparateur.compareStringCombinAsString("12345","67890")) );
    }
    @Test
    public void test1(){
        assertTrue(UN_EXACT.equalsIgnoreCase(comparateur.compareStringCombinAsString("12345","17890")) );
    }
    @Test
    public void test2(){
        assertTrue(UN_INEXACT.equalsIgnoreCase(comparateur.compareStringCombinAsString("12345","67490")) );
    }
    @Test
    public void test3(){
        assertTrue(DEUX_EXACT_UN_INEXACT.equalsIgnoreCase(comparateur.compareStringCombinAsString("12345","56340")) );
    }
    @Test
    public void test4(){
        assertTrue(UN_EXACT_TROIS_INEXACT.equalsIgnoreCase(comparateur.compareStringCombinAsString("12345","53248")) );
    }
    @Test
    public void test5(){
        assertTrue(DEUX_EXACT_UN_INEXACT.equalsIgnoreCase(comparateur.compareStringCombinAsString("02245","12325")) );
    }
    @Test
    public void testJeu()
    {

        assertEquals(comparateur.comparer("12345", "67890"), TOUT_FAUX);
        assertEquals(comparateur.comparer("12345", "17890"), UN_EXACT);
        assertEquals(comparateur.comparer("12345", "67490"), UN_INEXACT);
        assertEquals(comparateur.comparer("12345", "56340"), DEUX_EXACT_UN_INEXACT);
        assertEquals(comparateur.comparer("12345", "53248"), UN_EXACT_TROIS_INEXACT);
        assertEquals(comparateur.comparer("02245", "12325"), DEUX_EXACT_UN_INEXACT);

    }
}
