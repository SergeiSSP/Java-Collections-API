package com.foxminded.senkiv;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Cache {
	private final Map<String, Map<Character, Integer>> storage = new HashMap<>();

	void saveToCache(String countedString,Map<Character, Integer> resultToSave){
		storage.put(countedString, resultToSave);
	}

	public String displayCache(){
		StringBuilder sb = new StringBuilder();
		for(String cachedStrings : getCachedStrings()){
			sb.append(cachedStrings).append("\n");
		}
		return sb.toString();
	}

	public Set<String> getCachedStrings(){
		return storage.keySet();
	}


	public Map<Character, Integer> retrieveFromCache(String requiredString){
		return storage.get(requiredString);
	}

	public boolean isContaining(String s){
		return storage.containsKey(s);
	}

}
