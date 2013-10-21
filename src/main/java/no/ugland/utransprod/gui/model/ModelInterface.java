package no.ugland.utransprod.gui.model;

import java.beans.PropertyChangeListener;

import com.jgoodies.binding.PresentationModel;

/**
 * Interface for GUI-modeller
 * 
 * @author atle.brekka
 * 
 * @param <E>
 */
public interface ModelInterface<E> {
	/**
	 * Henter bufferobjekt
	 * 
	 * @param presentationModel
	 * @return bufferobjekt
	 */
	E getBufferedObjectModel(PresentationModel presentationModel);

	/**
	 * Legger til lytter på buffering
	 * 
	 * @param listener
	 * @param presentationModel
	 */
	void addBufferChangeListener(PropertyChangeListener listener,
			PresentationModel presentationModel);
}
