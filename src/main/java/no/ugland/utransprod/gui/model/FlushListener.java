package no.ugland.utransprod.gui.model;

/**
 * Interface for klassesom vil lytte p[ flushevent
 * @author atle.brekka
 *
 */
public interface FlushListener {
	/**
	 * @param flushing
	 */
	void flushChanged(boolean flushing);
}
