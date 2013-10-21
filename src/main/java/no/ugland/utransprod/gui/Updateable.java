package no.ugland.utransprod.gui;

/**
 * Interface som spesifiserer metoder som skal brukes for klasser som skal kunne
 * oppdatere objekter
 * 
 * @author atle.brekka
 * 
 */
public interface Updateable {
	/**
	 * Utfører lagring
	 * 
	 * @param window
	 */
	void doSave(WindowInterface window);

	/**
	 * Utfører sletting
	 * 
	 * @param window
	 * 
	 * @return true dersom slettet
	 */
	boolean doDelete(WindowInterface window);

	/**
	 * Utfører opprettelse av ny
	 * 
	 * @param window
	 */
	void doNew(WindowInterface window);

	/**
	 * Oppdaterer vindu
	 * @param window 
	 */
	void doRefresh(WindowInterface window);

}
