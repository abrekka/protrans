package no.ugland.utransprod.gui.buttons;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;

import no.ugland.utransprod.gui.Closeable;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.util.Util;

/**
 * Handling som lukker gjeldende vindu
 * 
 * @author abr99
 */
public class CancelAction extends AbstractAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -580679088028788222L;

	/**
	 * 
	 */
	private WindowInterface window;

	/**
	 * 
	 */
	private Closeable closeable;

	/**
	 * 
	 */
	private boolean checkUpdate;

	/**
	 * 
	 */
	private String actionString;

	/**
	 * 
	 */
	private List<CancelListener> cancelListeners = new ArrayList<CancelListener>();

	/**
	 * 
	 */
	private boolean useDispose;

	/**
	 * Konstruktør
	 * 
	 * @param window
	 * @param aCloseable
	 * @param disposeOnClose
	 */
	public CancelAction(WindowInterface window, Closeable aCloseable, boolean disposeOnClose) {
		this(window, aCloseable, null, disposeOnClose);
	}

	/**
	 * @param window
	 * @param aCloseable
	 * @param cancelListener
	 * @param disposeOnClose
	 */
	public CancelAction(WindowInterface window, Closeable aCloseable, CancelListener cancelListener,
			boolean disposeOnClose) {
		super("Avslutt");
		useDispose = disposeOnClose;
		this.window = window;
		closeable = aCloseable;
		actionString = "Avslutt";
		if (cancelListener != null) {
			cancelListeners.add(cancelListener);
		}
	}

	/**
	 * @param window
	 * @param aCloseable
	 * @param actionString
	 * @param checkUpdate
	 * @param cancelListener
	 * @param disposeOnClose
	 */
	public CancelAction(WindowInterface window, Closeable aCloseable, String actionString, boolean checkUpdate,
			CancelListener cancelListener, boolean disposeOnClose) {
		super(actionString);
		useDispose = disposeOnClose;
		this.window = window;
		closeable = aCloseable;
		this.checkUpdate = checkUpdate;
		this.actionString = actionString;
		if (cancelListener != null) {
			cancelListeners.add(cancelListener);
		}
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		Util.setWaitCursor(window);
		if (checkUpdate) {
			if (closeable.canClose(actionString, window)) {
				if (useDispose) {
					window.dispose();
					window = null;
				} else {
					window.setVisible(false);
				}
			}
		} else {
			if (useDispose) {
				window.dispose();
			} else {
				window.setVisible(false);
			}
		}
		fireCancel();
	}

	/**
	 * Gir beskjed om at knapp er trykket
	 */
	private void fireCancel() {
		for (CancelListener listener : cancelListeners) {
			listener.canceled();
		}
	}

	/**
	 * Fjerner referanse til klasse som bruker denne
	 */
	public void dispose() {
		closeable = null;
	}

}
