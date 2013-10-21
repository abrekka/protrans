package no.ugland.utransprod.gui.buttons;

import javax.swing.JButton;

import no.ugland.utransprod.gui.Updateable;
import no.ugland.utransprod.gui.WindowInterface;

/**
 * Generell nyknapp
 * 
 * @author atle.brekka
 * 
 */
public class NewButton extends JButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param objectName
	 * @param updateable
	 * @param window
	 */
	public NewButton(String objectName, Updateable updateable,
			WindowInterface window) {
		this(objectName, updateable, window, "Ny");
	}

	/**
	 * @param objectName
	 * @param updateable
	 * @param window
	 * @param actionString
	 */
	public NewButton(String objectName, Updateable updateable,
			WindowInterface window, String actionString) {
		super(new NewAction(objectName, updateable, window, actionString));
	}

	/**
	 * Rydder opp
	 */
	public void dispose() {
		((NewAction) this.getAction()).dispose();
	}
}
