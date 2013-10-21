package no.ugland.utransprod.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import no.ugland.utransprod.gui.MenuBarBuilderInterface;
import no.ugland.utransprod.gui.OverviewView;
import no.ugland.utransprod.gui.handlers.DeviationStatusViewHandler;
import no.ugland.utransprod.gui.model.DeviationStatusModel;
import no.ugland.utransprod.model.DeviationStatus;
import no.ugland.utransprod.service.DeviationStatusManager;

import com.google.inject.Inject;

/**
 * Håndterer manyvalg Avikststus
 * 
 * @author atle.brekka
 */
public class DeviationStatusAction extends AbstractAction {
	/**
	 * 
	 */
	private final MenuBarBuilderInterface menuBarBuilder;
	private DeviationStatusManager deviationStatusManager;
	private static final long serialVersionUID = 1L;

	@Inject
	public DeviationStatusAction(MenuBarBuilderInterface aMenuBarBuilder,DeviationStatusManager aDeviationStatusManager) {
		super("Avikstatus...");
		deviationStatusManager=aDeviationStatusManager;
		this.menuBarBuilder = aMenuBarBuilder;
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(final ActionEvent arg0) {

		menuBarBuilder.openFrame(new OverviewView<DeviationStatus, DeviationStatusModel>(
				new DeviationStatusViewHandler(menuBarBuilder.getUserType(),deviationStatusManager)));

	}
}