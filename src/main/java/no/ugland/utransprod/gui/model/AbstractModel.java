package no.ugland.utransprod.gui.model;

import java.beans.PropertyChangeListener;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.beans.Model;

/**
 * Abstrakt klasse for modellklasser
 * 
 * @author atle.brekka
 * 
 * @param <T>
 * @param <E>
 */
public abstract class AbstractModel<T, E> extends Model implements
		ModelInterface<E> {

	private static final long serialVersionUID = 1L;

	/**
	 * Property for objekt som blir wrappet
	 */
	public static final String PROPERTY_OBJECT = "object";

	/**
	 * Objekt som blir wrappet av modellklassen
	 */
	protected T object;

	/**
	 * @param object
	 */
	public AbstractModel(T object) {
		this.object = object;
	}

	/**
	 * Henter buffermodell
	 * 
	 * @param presentationModel
	 * @return buffermodell
	 */
	public abstract E getBufferedObjectModel(PresentationModel presentationModel);

	/**
	 * Legger til lytter på bufferverdier
	 * 
	 * @param listener
	 * @param presentationModel
	 */
	public abstract void addBufferChangeListener(
			PropertyChangeListener listener, PresentationModel presentationModel);

	/**
	 * Kopierer view til modell
	 */
	public void viewToModel() {

	}

	/**
	 * Kopierer modell til view
	 */
	public void modelToView() {

	}

	/**
	 * Henter objekt som er wrappet
	 * 
	 * @return objekt
	 */
	public T getObject() {

		return object;
	}

	public void firePropertiesChanged() {
		this.fireMultiplePropertiesChanged();
	}

}
