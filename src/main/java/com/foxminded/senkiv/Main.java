package com.foxminded.senkiv;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	private static final Logger logger = LogManager.getLogger();

	public static void main(String[] args) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		CharCounter charCounter = new CharCounter();

		while (true) {
			logger.info("Enter your String\n" +
				"If You want to stop -> write \"exit \" ");
			String toCount = bufferedReader.readLine();
			String result;
			if (toCount.equals("exit")) {
				break;
			}

			result = String.format("%n%s",charCounter.getCharCount(toCount));
			logger.info(result);
		}
		String cache = String.format("%n%s", charCounter.displayCache());
		logger.info(cache);
		bufferedReader.close();
	}
}
