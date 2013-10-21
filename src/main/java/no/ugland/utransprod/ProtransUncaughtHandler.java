package no.ugland.utransprod;

import java.lang.Thread.UncaughtExceptionHandler;

import org.apache.log4j.Logger;

/**
 * Klasse som h�ndterer exceptionsom ikke har blitt h�ndtert
 * @author atle.brekka
 *
 */
public class ProtransUncaughtHandler implements UncaughtExceptionHandler{
	/**
	 * 
	 */
	private static final Logger logger = Logger.getLogger(ProtransUncaughtHandler.class);
	/**
	 * H�ndterer exception ved � logge denne
	 * @param t 
	 * @param e 
	 */
	public void uncaughtException(Thread t, Throwable e) {
		logger.error("Feil som ikke er h�ndtert", e);
		
	}

}
