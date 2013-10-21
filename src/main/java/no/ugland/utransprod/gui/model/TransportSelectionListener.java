package no.ugland.utransprod.gui.model;

/**
 * Interface for klasser som skal lytte på transportselektering
 * 
 * @author atle.brekka
 * 
 */
public interface TransportSelectionListener {
	/**
	 * Forteller at selektering har endret seg
	 * 
	 * @param selection
	 * @param transportModel
	 */
	void transportSelectionChange(boolean selection,
			TransportModel transportModel);
}
