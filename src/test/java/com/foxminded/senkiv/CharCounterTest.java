package com.foxminded.senkiv;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;


import static org.junit.jupiter.api.Assertions.*;


class CharCounterTest {
	private CharCounter charCounter;
	private Cache cache;
	private InformationExchanger exchanger;

	@BeforeEach
	void setUp() {
		charCounter = new CharCounter();
		this.cache = charCounter.cache;
		this.exchanger = charCounter.exchanger;
	}

	@Test
	void addToCharCounter_shouldAddLetterToStorageIfItOccurredFirstTime() {
		String result = exchanger.prettyOutput(charCounter.getCharCount("a"));

		assertEquals(String.format("%n\"a\" : 1"), result);
	}

	@Test
	void addToCharCounter_shouldIncrementLetterCounterIfItOccurredSecondTime() {
		String result = exchanger.prettyOutput(charCounter.getCharCount("aa"));

		assertEquals(String.format("%n\"a\" : 2"), result);
	}

	@Test
	void getCharCount_shouldReturnCorrectResultIfInputIncludesOnlySpaces() {
		String result = exchanger.prettyOutput(charCounter.getCharCount("        "));

		assertEquals(String.format("%n\" \" : 8"), result);
	}

	@Test
	void getCharCount_shouldRetrieveResultFromCacheIfItIsStoredThere() {
		charCounter.getCharCount("Hello World!");
		charCounter.getCharCount("Hello World!");

		assertTrue(charCounter.isRetrievedFromCache());
	}

	@Test
	void getCharCount_shouldCountAndAddToCounterAllCharsFromString() {
		String actualResult = exchanger.prettyOutput(charCounter.getCharCount("qwerty "));
		String expectedResult = String.format(
			"%n\"q\" : 1" +
				"%n\"w\" : 1" +
				"%n\"e\" : 1" +
				"%n\"r\" : 1" +
				"%n\"t\" : 1" +
				"%n\"y\" : 1" +
				"%n\" \" : 1"
		);

		assertEquals(expectedResult, actualResult);
	}

	@Test
	void getCharCount_shouldWorkWithEmptyInput() {
		String actualResult = exchanger.prettyOutput(charCounter.getCharCount(""));
		assertEquals("", actualResult);
	}

	@Test
	void getCharCount_shouldWorkWithSpecialCharacters() {
		String actualResult = exchanger.prettyOutput(charCounter.getCharCount("$$$$%%%%&&&&****"));
		String expectedResult = String.format(
			"%n\"%s\" : %d" +
				"%n\"%s\" : %d" +
				"%n\"%s\" : %d" +
				"%n\"%s\" : %d",
			"$", 4, "%", 4, "&", 4, "*", 4
		);
		assertEquals(expectedResult, actualResult);
	}

	@Test
	void getCharCount_shouldCountMixedCaseCharacters() {
		String actualResult = exchanger.prettyOutput(charCounter.getCharCount("HeLlo_WoRlD!"));
		String expectedResult = String.format(
			"%n\"%s\" : %d" +
				"%n\"%s\" : %d" +
				"%n\"%s\" : %d" +
				"%n\"%s\" : %d" +
				"%n\"%s\" : %d" +
				"%n\"%s\" : %d" +
				"%n\"%s\" : %d" +
				"%n\"%s\" : %d" +
				"%n\"%s\" : %d" +
				"%n\"%s\" : %d",
			"H", 1, "e", 1, "L", 1, "l", 2, "o", 2, "_", 1, "W", 1, "R", 1, "D", 1, "!", 1
		);
		assertEquals(expectedResult, actualResult);
	}

	@Test
	void displayCache_shouldReturnAllValuesStoredInCache() {
		charCounter.getCharCount("148091840980193840@&#%*&@#*%&@*#%&)@*#%");
		charCounter.getCharCount("QWERTY");
		charCounter.getCharCount("hELLO");
		charCounter.getCharCount("Bye");
		charCounter.getCharCount("asd asd asd");
		charCounter.getCharCount("23414");
		Set<String> actualResult = cache.getCachedStrings();
		Set<String> expectedResult = new HashSet<>();
		expectedResult.add("148091840980193840@&#%*&@#*%&@*#%&)@*#%");
		expectedResult.add("QWERTY");
		expectedResult.add("hELLO");
		expectedResult.add("Bye");
		expectedResult.add("asd asd asd");
		expectedResult.add("23414");
		assertTrue(actualResult.containsAll(expectedResult));
	}

	@Test
	void getCharCount_shouldThrowIllegalArgumentExceptionIfParameterIsNull() {
		assertThrows(IllegalArgumentException.class, () -> charCounter.exchanger.validate(null));
	}

	@Test
	void getCharCount_shouldReturnCorrectResultForLongStrings() {
		Map<Character, Integer> actualResult = charCounter.getCharCount("a".repeat(100) + "b".repeat(500) + "x".repeat(400));
		Map<Character, Integer> expectedResult = new LinkedHashMap<>();
		expectedResult.put('a', 100);
		expectedResult.put('b', 500);
		expectedResult.put('x', 400);

		assertEquals(expectedResult, actualResult);
	}


}

class ParametrizedCharCounterTest {
	static CharCounter cc;
	@BeforeAll
	static void setUp(){
		cc = new CharCounter();
	}


	@ParameterizedTest
	@CsvSource({"Hello , 1", "Bye, 2", "Hello, 2", "Bye, 2","Hello, 2","Hello, 2","Hello, 2"})
	void getCharCount_shouldNotStoreDuplicates(String inputString, int sizeOfCache){
		cc.getCharCount(inputString);
		int actualSize = cc.cache.getCachedStrings().size();
		assertEquals(sizeOfCache, actualSize);
	}
}



