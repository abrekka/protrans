package no.ugland.utransprod.gui.model;

/**
 * Interface for klasser som skal kunne rendres i TextPaneRenderer
 * 
 * @author atle.brekka
 * 
 */
public interface TextRenderable {
	/**
	 * @return ordrestreng
	 */
	String getOrderString();

	/**
	 * @return kommentar
	 */
	String getComment();
}
