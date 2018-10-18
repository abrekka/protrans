package no.ugland.utransprod.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import no.ugland.utransprod.gui.DeviationOverviewView;
import no.ugland.utransprod.gui.DeviationOverviewView2;
import no.ugland.utransprod.gui.DeviationOverviewViewFactory;
import no.ugland.utransprod.gui.MenuBarBuilderInterface;
import no.ugland.utransprod.gui.handlers.DeviationViewHandler;
import no.ugland.utransprod.gui.handlers.DeviationViewHandler2;
import no.ugland.utransprod.gui.handlers.DeviationViewHandlerFactory;
import no.ugland.utransprod.service.ConstructionTypeManager;
import no.ugland.utransprod.service.DeviationManager;
import no.ugland.utransprod.service.PreventiveActionManager;
import no.ugland.utransprod.util.Util;

import com.google.inject.Inject;

/**
 * Håndterer menyvalg Administrer avvik...
 * 
 * @author atle.brekka
 */
public class AdminDeviationAction extends AbstractAction {
	/**
	 * 
	 */
	private final MenuBarBuilderInterface menuBarBuilder;
	private static final long serialVersionUID = 1L;
	private DeviationOverviewView2 deviationOverviewView;

	@Inject
	public AdminDeviationAction(final MenuBarBuilderInterface aMenuBarBuilder,
			DeviationOverviewViewFactory deviationOverviewViewFactory,
			DeviationViewHandlerFactory deviationViewHandlerFactory) {
		super("Administrer avvik...");
		DeviationViewHandler2 deviationViewHandler = deviationViewHandlerFactory.create(null, true, false, true, null,
				true, true);
		deviationOverviewView = deviationOverviewViewFactory.create(deviationViewHandler, true, null, true, false, true,
				null, true);

		this.menuBarBuilder = aMenuBarBuilder;

	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(final ActionEvent arg0) {
		this.menuBarBuilder.openFrame(deviationOverviewView);
	}
}