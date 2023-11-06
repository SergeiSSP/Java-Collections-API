package com.foxminded.senkiv;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class CharCounter {
    private char[] charArr;
    private Map<Character, Integer> charCountStorage;
    private static final Map<String, Map<Character, Integer>> cache = new HashMap<>();
    private boolean isRetrievedFromCache;



    private void addToCharCounter(Character letter){
        if(charCountStorage.containsKey(letter)){
            Integer entry = charCountStorage.get(letter);
            charCountStorage.replace(letter, ++entry);
        } else {
            charCountStorage.put(letter, 1);
        }
    }

    private void stringToCharArr(String input){
        this.charArr = input.toCharArray();
    }

    public void countChars(){
        for(Character letter : charArr){
            addToCharCounter(letter);
        }
    }

    private void saveToCash(String countedString,Map<Character, Integer> resultToSave){
        cache.put(countedString, resultToSave);
    }

    private Map<Character, Integer> retrieveFromCache(String toRetrieve){
        return cache.get(toRetrieve);
    }

    public String getCharCount(String input){
        isRetrievedFromCache = false;
        charCountStorage = new LinkedHashMap<>();
        String result;
        if(cache.containsKey(input)){
            isRetrievedFromCache = true;
            result = prettyOutput(retrieveFromCache(input));
        }else{
            stringToCharArr(input);
            countChars();
            saveToCash(input ,charCountStorage);
            result = prettyOutput(charCountStorage);
        }
        return result;
    }

    public String prettyOutput(Map<Character, Integer> counter){
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<Character, Integer> entry : counter.entrySet()){
            String line = String.format("\"%s\" : %d%n", entry.getKey(), entry.getValue());
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
}
