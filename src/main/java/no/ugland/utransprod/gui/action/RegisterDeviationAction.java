package no.ugland.utransprod.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.google.inject.Inject;

import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.MenuBarBuilderInterface;
import no.ugland.utransprod.gui.edit.EditDeviationView;
import no.ugland.utransprod.gui.handlers.DeviationViewHandler;
import no.ugland.utransprod.gui.handlers.DeviationViewHandlerFactory;
import no.ugland.utransprod.gui.model.DeviationModel;
import no.ugland.utransprod.model.Deviation;
import no.ugland.utransprod.service.ManagerRepository;

/**
 * Håndterer menyvalg Registrere avvik...
 * 
 * @author atle.brekka
 */
public class RegisterDeviationAction extends AbstractAction {
	/**
	 * 
	 */
	private final MenuBarBuilderInterface menuBarBuilder;
	private static final long serialVersionUID = 1L;

	private DeviationViewHandler deviationViewHandler;

	@Inject
	public RegisterDeviationAction(MenuBarBuilderInterface aMenuBarBuilder,
			DeviationViewHandlerFactory deviationViewHandlerFactory, Login login, ManagerRepository managerRepository) {
		super("Registrere avvik...");
		// deviationViewHandler=deviationViewHandlerFactory.create(null, false,
		// false, true, null, true);
		deviationViewHandler = new DeviationViewHandler(login, managerRepository, null, null, false, false, true, null,
				true, true);

		this.menuBarBuilder = aMenuBarBuilder;
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(final ActionEvent arg0) {

		DeviationModel deviationModel = new DeviationModel(new Deviation(), false);
		EditDeviationView editDeviationView = new EditDeviationView(false, deviationModel, deviationViewHandler, false,
				true, true);

		/*
		 * WindowInterface window = InternalFrameBuilder.buildInternalFrame(
		 * "Registrere avvik", deviationViewHandler.getRegisterWindowSize(),
		 * false); window.add(editDeviationView.buildPanel(window),
		 * BorderLayout.CENTER);
		 */

		this.menuBarBuilder.openFrame(editDeviationView);

	}
}