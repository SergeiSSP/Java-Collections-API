package com.foxminded.senkiv;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class CharCounter {
    private Map<Character, Integer> charCountStorage;
	protected final Cache cache = new Cache();
    private boolean isRetrievedFromCache;
	protected final InformationExchanger exchanger = new InformationExchanger();

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


    public Map<Character, Integer> getCharCount(String inputString){
        isRetrievedFromCache = false;
        charCountStorage = new LinkedHashMap<>();
		if(cache.isContaining(inputString)){
            isRetrievedFromCache = true;
			return cache.retrieveFromCache(inputString);
        }else{
            countChars(inputString);
            cache.saveToCache(inputString ,charCountStorage);
        }
        return charCountStorage;
    }

    public boolean isRetrievedFromCache() {
        return isRetrievedFromCache;
    }


	public void runCounting() throws IOException {
		try {
			while (true) {
				String input = exchanger.preCalculationAction();
				if (input.equals("exit")) {
					break;
				}
				Map<Character, Integer> result = getCharCount(input);
				exchanger.postCalculationAction(result);
			}
		} finally {
			exchanger.br.close();
		}
	}
}
