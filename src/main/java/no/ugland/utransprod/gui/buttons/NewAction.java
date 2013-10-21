package no.ugland.utransprod.gui.buttons;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import no.ugland.utransprod.gui.Updateable;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.util.Util;

/**
 * Generell klasse for handlingen ny
 * 
 * @author atle.brekka
 * 
 */
public class NewAction extends AbstractAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private Updateable updateable;

	/**
	 * 
	 */
	private WindowInterface window;

	/**
	 * @param objectName
	 * @param updateable
	 * @param window
	 * 
	 */
	public NewAction(String objectName, Updateable updateable,
			WindowInterface window) {
		this(objectName, updateable, window, "Ny");
	}

	/**
	 * @param objectName
	 * @param updateable
	 * @param window
	 * @param actionString
	 */
	public NewAction(String objectName, Updateable updateable,
			WindowInterface window, String actionString) {
		super(actionString + " " + objectName + "...");
		this.updateable = updateable;
		this.window = window;
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		Util.setWaitCursor(window.getComponent());
		updateable.doNew(window);
		Util.setDefaultCursor(window.getComponent());

	}

	/**
	 * Rydder opp
	 */
	public void dispose() {
		updateable = null;
		window = null;
	}
}