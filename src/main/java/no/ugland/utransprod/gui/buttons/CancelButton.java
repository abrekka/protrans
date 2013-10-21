package no.ugland.utransprod.gui.buttons;

import javax.swing.JButton;

import no.ugland.utransprod.gui.Closeable;
import no.ugland.utransprod.gui.IconEnum;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.util.Util;

/**
 * Generell avbrytknapp
 * 
 * @author atle.brekka
 * 
 */
public class CancelButton extends JButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param window
	 * @param closeable
	 * @param disposeOnClose
	 */
	public CancelButton(WindowInterface window, Closeable closeable,
			boolean disposeOnClose) {
		this(window, closeable, true, Util.getGuiProperty("button.cancel.text"), IconEnum.ICON_CANCEL, null,
				disposeOnClose);

	}

	/**
	 * @param window
	 * @param closeable
	 * @param cancelListener
	 * @param disposeOnClose
	 */
	public CancelButton(WindowInterface window, Closeable closeable,
			CancelListener cancelListener, boolean disposeOnClose) {
		this(window, closeable, true, Util.getGuiProperty("button.cancel.text"), IconEnum.ICON_CANCEL,
				cancelListener, disposeOnClose);

	}

	/**
	 * @param window
	 * @param closeable
	 * @param checkUpdate
	 * @param buttonString
	 * @param buttonIcon
	 * @param cancelListener
	 * @param disposeOnClose
	 */
	public CancelButton(WindowInterface window, Closeable closeable,
			boolean checkUpdate, String buttonString, IconEnum buttonIcon,
			CancelListener cancelListener, boolean disposeOnClose) {
		super(new CancelAction(window, closeable, buttonString, checkUpdate,
				cancelListener, disposeOnClose));
		if (buttonIcon != null) {
			setIcon(buttonIcon.getIcon());
		}
	}

	/**
	 * Rydder opp
	 */
	public void dispose() {
		((CancelAction) this.getAction()).dispose();
	}

}
