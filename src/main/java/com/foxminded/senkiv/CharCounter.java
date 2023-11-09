package com.foxminded.senkiv;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class CharCounter {
    private Map<Character, Integer> charCountStorage;
    private final Map<String, Map<Character, Integer>> cache = new HashMap<>();
    private boolean isRetrievedFromCache;
	private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private static final Logger logger = LogManager.getLogger();



    private void countChars(String input){
		for (Character letter : input.toCharArray()) {
			if (charCountStorage.containsKey(letter)) {
				Integer entry = charCountStorage.get(letter);
				charCountStorage.replace(letter, ++entry);
			} else {
				charCountStorage.put(letter, 1);
			}
		}
    }

    private void saveToCash(String countedString,Map<Character, Integer> resultToSave){
        cache.put(countedString, resultToSave);
    }

    private Map<Character, Integer> retrieveFromCache(String toRetrieve){
        return cache.get(toRetrieve);
    }

    public Map<Character, Integer> getCharCount(String inputString){
		isNull(inputString);
        isRetrievedFromCache = false;
        charCountStorage = new LinkedHashMap<>();
		if(cache.containsKey(inputString)){
            isRetrievedFromCache = true;
			return retrieveFromCache(inputString);
        }else{
            countChars(inputString);
            saveToCash(inputString ,charCountStorage);
        }

        return charCountStorage;
    }

    public String prettyOutput(Map<Character, Integer> counter){
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<Character, Integer> entry : counter.entrySet()){
            String line = String.format("%n\"%s\" : %d", entry.getKey(), entry.getValue());
            sb.append(line);
        }
        return sb.toString();
    }

    public Set<String> getCachedStrings(){
        return cache.keySet();
    }

    public String displayCache(){
        StringBuilder sb = new StringBuilder();
        for(String cachedStrings : getCachedStrings()){
            sb.append(cachedStrings).append("\n");
        }
        return sb.toString();
    }

    public boolean isRetrievedFromCache() {
        return isRetrievedFromCache;
    }

	private void isNull(String toCheck){
		if(toCheck == null){
			throw new NullPointerException("The value of parameter is null");
		}
	}

	public void inputRequest() throws IOException {
		while (true) {
			logger.info("Enter your String\n" +
				"If You want to stop -> write \"exit \" ");
			String toCount = br.readLine();
			String result;
			result = prettyOutput(getCharCount(toCount));
			logger.info(result);
			if (toCount.equals("exit")) {
				break;
			}
		}
		br.close();
	}
}
