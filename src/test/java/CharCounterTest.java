import com.foxminded.senkiv.CharCounter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class CharCounterTest {
    private  CharCounter charCounter;

    @BeforeEach
    void setUp(){
        charCounter = new CharCounter();
    }

    @Test
     void addToCharCounter_shouldAddLetterToStorageIfItOccurredFirstTime(){
        String result = charCounter.getCharCount("a");

        assertEquals(String.format("\"a\" : 1%n"), result);
    }

    @Test
    void addToCharCounter_shouldIncrementLetterCounterIfItOccurredSecondTime(){
        String result = charCounter.getCharCount("aa");

        assertEquals(String.format("\"a\" : 2%n"), result);
    }

    @Test
    void getCharCount_shouldReturnCorrectResultIfInputIncludesOnlySpaces(){
        String result = charCounter.getCharCount("        ");

        assertEquals(String.format("\" \" : 8%n"), result);
    }

    @Test
    void getCharCount_shouldRetrieveResultFromCacheIfItIsStoredThere(){
        charCounter.getCharCount("Hello World!");
        charCounter.getCharCount("Hello World!");

        assertTrue(charCounter.isRetrievedFromCache());
    }

    @Test
    void getCharCount_shouldCountAndAddToCounterAllCharsFromString(){
        String actualResult = charCounter.getCharCount("qwerty ");
        String expectedResult = String.format(
                        "\"q\" : 1%n" +
                        "\"w\" : 1%n" +
                        "\"e\" : 1%n" +
                        "\"r\" : 1%n" +
                        "\"t\" : 1%n" +
                        "\"y\" : 1%n" +
                        "\" \" : 1%n"
        );

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getCharCount_shouldWorkWithEmptyInput(){
        String actualResult = charCounter.getCharCount("");
        assertEquals("", actualResult);
    }

    @Test
    void getCharCount_shouldWorkWithSpecialCharacters(){
        String actualResult = charCounter.getCharCount("$$$$%%%%&&&&****");
        String expectedResult = String.format(
                        "\"%s\" : %d%n" +
                        "\"%s\" : %d%n" +
                        "\"%s\" : %d%n" +
                        "\"%s\" : %d%n",
                "$", 4, "%", 4, "&", 4, "*", 4
        );
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getCharCount_shouldCountMixedCaseCharacters(){
        String actualResult = charCounter.getCharCount("HeLlo_WoRlD!");
        String expectedResult = String.format(
                        "\"%s\" : %d%n" +
                        "\"%s\" : %d%n" +
                        "\"%s\" : %d%n" +
                        "\"%s\" : %d%n" +
                        "\"%s\" : %d%n" +
                        "\"%s\" : %d%n" +
                        "\"%s\" : %d%n" +
                        "\"%s\" : %d%n" +
                        "\"%s\" : %d%n" +
                        "\"%s\" : %d%n" ,
                "H", 1, "e", 1, "L", 1, "l", 2,"o", 2 ,"_", 1,  "W", 1,"R", 1, "D", 1, "!", 1
        );
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void displayCache_shouldReturnAllValuesStoredInCache(){
        charCounter.getCharCount("148091840980193840@&#%*&@#*%&@*#%&)@*#%");
        charCounter.getCharCount("QWERTY");
        charCounter.getCharCount("hELLO");
        charCounter.getCharCount("Bye");
        charCounter.getCharCount("asd asd asd");
        charCounter.getCharCount("23414");
        Set<String> actualResult = charCounter.getCachedStrings();
        Set<String> expectedResult = new HashSet<>();
        expectedResult.add("148091840980193840@&#%*&@#*%&@*#%&)@*#%");
        expectedResult.add("QWERTY");
        expectedResult.add("hELLO");
        expectedResult.add("Bye");
        expectedResult.add("asd asd asd");
        expectedResult.add("23414");
        assertTrue(actualResult.containsAll(expectedResult));
    }
}
