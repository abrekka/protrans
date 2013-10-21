/**
 * 
 */
package no.ugland.utransprod.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import no.ugland.utransprod.gui.MenuBarBuilderInterface;
import no.ugland.utransprod.gui.edit.EditTakstolSetupView;
import no.ugland.utransprod.gui.handlers.ApplicationParamViewHandler;
import no.ugland.utransprod.service.ApplicationParamManager;

import com.google.inject.Inject;

public class TakstolSetupAction extends AbstractAction {
	/**
	 * 
	 */
	private final MenuBarBuilderInterface menuBarBuilder;
	private ApplicationParamManager applicationParamManager;
	private static final long serialVersionUID = 1L;

	@Inject
	public TakstolSetupAction(MenuBarBuilderInterface aMenuBarBuilder,ApplicationParamManager aApplicationParamManager) {
		super("Takstoloppsett...");
		applicationParamManager=aApplicationParamManager;
		this.menuBarBuilder = aMenuBarBuilder;
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(final ActionEvent arg0) {
		ApplicationParamViewHandler applicationParamViewHandler = new ApplicationParamViewHandler(
				"Takstoloppsett", applicationParamManager,
				menuBarBuilder.getUserType());
		menuBarBuilder.openFrame(new EditTakstolSetupView(applicationParamViewHandler));

	}
}