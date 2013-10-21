package no.ugland.utransprod.gui;

/**
 * Interface som brukes for klasser som h�ndterer visning av vindu
 * 
 * @author atle.brekka
 * 
 */
public interface Viewer {
	/**
	 * Bygger vindu som skal vises
	 * 
	 * @return vindu (JInternalFrame eller JDialog)
	 */
	WindowInterface buildWindow();

	/**
	 * @return tittel
	 */
	String getTitle();

	/**
	 * Initierer vindu
	 */
	void initWindow();

	/**
	 * Rydder opp
	 */
	void cleanUp();

	/**
	 * @return true dersom vindu skal disposes ved avslutning
	 */
	boolean useDispose();
}
