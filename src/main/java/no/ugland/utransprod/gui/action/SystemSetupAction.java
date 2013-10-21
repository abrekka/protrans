/**
 * 
 */
package no.ugland.utransprod.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import no.ugland.utransprod.gui.SystemSetupView;
import no.ugland.utransprod.gui.handlers.SystemSetupViewHandler;
import no.ugland.utransprod.util.Util;

public class SystemSetupAction extends AbstractAction {
	private static final long serialVersionUID = 1L;

	public SystemSetupAction() {
		super("Oppsett...");
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(final ActionEvent arg0) {
		SystemSetupViewHandler systemSetupViewHandler = new SystemSetupViewHandler();
		SystemSetupView systemSetupView = new SystemSetupView(
				systemSetupViewHandler);
		Util.showEditViewable(systemSetupView, null);

	}
}