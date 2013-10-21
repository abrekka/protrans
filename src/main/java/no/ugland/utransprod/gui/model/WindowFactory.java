package no.ugland.utransprod.gui.model;

import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.WindowInterface;

/**
 * Interface for factoryklasse for å hente dialog
 * 
 * @author atle.brekka
 * 
 */
public interface WindowFactory {

	/**
	 * Henter dialog eller kreerer det
	 * 
	 * @param windowEnum
	 * @param userType
	 * @param applicationUser
	 * @return dialog
	 */
	public abstract WindowInterface getWindow(WindowEnum windowEnum,
			Login login);

}