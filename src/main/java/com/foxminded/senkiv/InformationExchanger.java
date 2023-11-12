package com.foxminded.senkiv;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public class InformationExchanger {
	protected final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private static final Logger logger = LogManager.getLogger();


	public String requestInput() throws IOException {
		return br.readLine();
	}
	public String preCalculationAction() throws IOException {
			logger.info("Enter your String\n" +
				"If You want to stop -> write \"exit \" ");
			String input = requestInput();
			validate(input);
			return input;
	}

	public void postCalculationAction(Map<Character, Integer> result){
		String toDisplay = prettyOutput(result);
		logger.info(toDisplay);
	}

	public String prettyOutput(Map<Character, Integer> counter){
		StringBuilder sb = new StringBuilder();
		for(Map.Entry<Character, Integer> entry : counter.entrySet()){
			String line = String.format("%n\"%s\" : %d", entry.getKey(), entry.getValue());
			sb.append(line);
		}
		return sb.toString();
	}

	public void validate(String toCheck){
		switch (toCheck) {
			case null:
					throw new IllegalArgumentException("Cannot operate with null value.");

			case "":
				logger.info("The String is empty.");
				break;

			default:
				break;
		}
	}
}
