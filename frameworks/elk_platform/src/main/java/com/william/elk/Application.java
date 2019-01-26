package com.william.elk;


import org.apache.log4j.Logger;

import java.util.Random;

/**
 * 
 * @author zdpwilliam
 *
 */
public class Application {
	private static final Logger LOGGER = Logger.getLogger(Application.class);

	public void start() {
		while (true) {
			LOGGER.info("William will be the owner of the World! For Freedom!");
			try {
                if(random(19, getRandomInt(10000))) {
                    try {
                        createRuntimeException();
                    }
                    catch (RuntimeException e) {
                        LOGGER.error("Application createRuntimeException: {}", e);
                    }
                }
				Thread.currentThread().sleep(5000);
			} catch (Exception e) {
				LOGGER.error("Application start() occurs exception: {}", e);
			}
		}
	}

    private int getRandomInt(int max) {
        Random random = new Random(System.nanoTime());
        return random.nextInt(max);
    }

    private boolean random(int factor, int random) {
        return (random % factor) == 0;
    }

	private int createRuntimeException() {
		return 2/0;
	}
}
