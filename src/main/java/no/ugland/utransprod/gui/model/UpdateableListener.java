package no.ugland.utransprod.gui.model;

/**
 * Interface for lytter på artikkelendringer
 * 
 * @author atle.brekka
 * 
 */
public interface UpdateableListener {
	/**
	 * Før artikkel blir lagt til
	 * 
	 * @return true dersom artikkel kan legges til
	 */
	boolean beforeAdded();

	/**
	 * Etter at artikkel har blitt lagt til
	 */
	void afterAdded();
}
