package no.ugland.utransprod.gui.model;

import no.ugland.utransprod.gui.WindowInterface;

/**
 * Interface for lyttere til kollier
 * 
 * @author atle.brekka
 * 
 */
public interface ColliListener {
	/**
	 * Valg av kolli har endret seg
	 * 
	 * @param selection
	 * @param colliModel
	 */
	void colliSelectionChange(boolean selection, ColliModel colliModel);

	/**
	 * Ordrelinje har blitt fjernet fra kolli
	 * 
	 * @param window
	 */
	void orderLineRemoved(WindowInterface window);
	void refreshCollies();
}
