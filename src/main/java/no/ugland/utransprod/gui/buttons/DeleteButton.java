package no.ugland.utransprod.gui.buttons;

import javax.swing.JButton;

import no.ugland.utransprod.gui.Updateable;
import no.ugland.utransprod.gui.WindowInterface;

/**
 * Generell knapp for sletting
 * 
 * @author atle.brekka
 * 
 */
public class DeleteButton extends JButton 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param objectName
	 * @param updateable
	 * @param parent
	 */
	public DeleteButton(String objectName, Updateable updateable,
			WindowInterface parent) {
		super(new DeleteAction(objectName, updateable, parent));
	}

	
}
