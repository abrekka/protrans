package no.ugland.utransprod;

import java.lang.Thread.UncaughtExceptionHandler;

import org.apache.log4j.Logger;

/**
 * Klasse som håndterer exceptionsom ikke har blitt håndtert
 * @author atle.brekka
 *
 */
public class ProtransUncaughtHandler implements UncaughtExceptionHandler{
	/**
	 * 
	 */
	private static final Logger logger = Logger.getLogger(ProtransUncaughtHandler.class);
	/**
	 * Håndterer exception ved å logge denne
	 * @param t 
	 * @param e 
	 */
	public void uncaughtException(Thread t, Throwable e) {
		logger.error("Feil som ikke er håndtert", e);
		
	}

}
