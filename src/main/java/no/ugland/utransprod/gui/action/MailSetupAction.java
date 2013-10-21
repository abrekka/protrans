package no.ugland.utransprod.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import no.ugland.utransprod.gui.MenuBarBuilderInterface;
import no.ugland.utransprod.gui.edit.EditMailOptionsView;
import no.ugland.utransprod.gui.handlers.ApplicationParamViewHandler;
import no.ugland.utransprod.model.ApplicationParam;
import no.ugland.utransprod.service.ApplicationParamManager;

import com.google.inject.Inject;

/**
 * Håndterer menyvalg Mailoppsett
 * 
 * @author atle.brekka
 */
public class MailSetupAction extends AbstractAction {
	/**
	 * 
	 */
	private final MenuBarBuilderInterface menuBarBuilder;
	private ApplicationParamManager applicationParamManager;
	private static final long serialVersionUID = 1L;

	@Inject
	public MailSetupAction(MenuBarBuilderInterface aMenuBarBuilder,ApplicationParamManager aApplicationParamManager) {
		super("Mailoppsett...");
		applicationParamManager=aApplicationParamManager;
		this.menuBarBuilder = aMenuBarBuilder;
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(final ActionEvent arg0) {

		EditMailOptionsView view = new EditMailOptionsView(false,
				new ApplicationParam(), new ApplicationParamViewHandler("Mailoppsett",applicationParamManager,menuBarBuilder.getUserType()));

		menuBarBuilder.openFrame(view);

	}
}