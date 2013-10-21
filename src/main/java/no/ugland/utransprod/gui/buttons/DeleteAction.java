package no.ugland.utransprod.gui.buttons;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.Updateable;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.util.Util;

/**
 * Generell klasse for håndtering av sletting
 * 
 * @author atle.brekka
 * 
 */
public class DeleteAction extends AbstractAction {
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
	 * 
	 */
	private String objectName;

	/**
	 * @param objectName
	 * @param updateable
	 * @param parent
	 */
	public DeleteAction(String objectName, Updateable updateable,
			WindowInterface parent) {
		super("Slett " + objectName);
		this.updateable = updateable;
		this.window = parent;
		this.objectName = objectName;
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {

		if (window instanceof JDialogAdapter) {
			if (Util.showConfirmDialog(window.getComponent(), "Slette?",
					"Vil du virkelig slette " + objectName)) {
				updateable.doDelete(window);
			}
		} else {
			if (Util.showConfirmDialog(window.getComponent(), "Slette?",
					"Vil du virkelig slette " + objectName)) {
				updateable.doDelete(window);
			}
		}

	}

	/**
	 * Rydder opp
	 */
	public void dispose() {
		updateable = null;
		window = null;
		objectName = null;
	}
}