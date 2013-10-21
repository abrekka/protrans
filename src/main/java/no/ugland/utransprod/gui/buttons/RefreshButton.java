package no.ugland.utransprod.gui.buttons;

import javax.swing.JButton;

import no.ugland.utransprod.gui.IconEnum;
import no.ugland.utransprod.gui.Updateable;
import no.ugland.utransprod.gui.WindowInterface;

/**
 * Knapp som brukes til å oppdaterer vindu
 * 
 * @author atle.brekka
 * 
 */
public class RefreshButton extends JButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param updateable
	 * @param window
	 */
	public RefreshButton(Updateable updateable, WindowInterface window) {
		super(new RefreshAction(updateable, window));
		setIcon(IconEnum.ICON_REFRESH.getIcon());
	}
}