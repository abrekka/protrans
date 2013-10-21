/**
 * 
 */
package no.ugland.utransprod.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import no.ugland.utransprod.gui.MenuBarBuilderInterface;
import no.ugland.utransprod.gui.edit.EditColliSetupView;
import no.ugland.utransprod.gui.handlers.ApplicationParamViewHandler;
import no.ugland.utransprod.service.ApplicationParamManager;

import com.google.inject.Inject;

public class ColliSetupAction extends AbstractAction {
	/**
	 * 
	 */
	private final MenuBarBuilderInterface menuBarBuilder;
	private ApplicationParamManager applicationParamManager;
	private static final long serialVersionUID = 1L;

	@Inject
	public ColliSetupAction(MenuBarBuilderInterface aMenuBarBuilder,ApplicationParamManager aApplicationParamManager) {
		super("Kollioppsett...");
		applicationParamManager=aApplicationParamManager;
		this.menuBarBuilder = aMenuBarBuilder;
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(final ActionEvent arg0) {
		ApplicationParamViewHandler applicationParamViewHandler = new ApplicationParamViewHandler(
				"Kollioppsett", applicationParamManager,
				menuBarBuilder.getUserType());
		menuBarBuilder.openFrame(new EditColliSetupView(applicationParamViewHandler));
	}
}