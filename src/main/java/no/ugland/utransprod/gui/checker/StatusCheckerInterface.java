package no.ugland.utransprod.gui.checker;

/**
 * Interface for klasser som sjekker status
 * 
 * @author atle.brekka
 * 
 * @param <E>
 */
public interface StatusCheckerInterface<E> {
	/**
	 * Genererer status
	 * 
	 * @param object
	 * @return status
	 */
	String getArticleStatus(E object);

	/**
	 * Henter artikkelnavn
	 * 
	 * @return artikkelnavn
	 */
	String getArticleName();
}
