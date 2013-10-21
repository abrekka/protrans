package no.ugland.utransprod.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import no.ugland.utransprod.gui.MenuBarBuilderInterface;
import no.ugland.utransprod.gui.PacklistView;
import no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler;
import no.ugland.utransprod.gui.handlers.DeviationViewHandlerFactory;
import no.ugland.utransprod.gui.handlers.OrderViewHandler;
import no.ugland.utransprod.gui.handlers.OrderViewHandlerFactory;
import no.ugland.utransprod.gui.handlers.PacklistViewHandler;
import no.ugland.utransprod.gui.model.PacklistApplyList;
import no.ugland.utransprod.model.PacklistV;
import no.ugland.utransprod.service.AccidentManager;
import no.ugland.utransprod.service.DeviationManager;
import no.ugland.utransprod.service.ExternalOrderManager;
import no.ugland.utransprod.service.ManagerRepositoryImpl;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.service.PacklistVManager;
import no.ugland.utransprod.util.ModelUtil;

import com.google.inject.Inject;

/**
 * Håndterer menyvalg Pakkliste...
 * 
 * @author atle.brekka
 */
public class PacklistAction extends AbstractAction {
	/**
	 * 
	 */
	private final MenuBarBuilderInterface menuBarBuilder;
	private static final long serialVersionUID = 1L;

	private PacklistViewHandler packlistViewHandler;

	@Inject
	public PacklistAction(MenuBarBuilderInterface aMenuBarBuilder,
			final PacklistViewHandler aPacklistViewHandler) {
		super("Pakkliste...");
		packlistViewHandler = aPacklistViewHandler;
		this.menuBarBuilder = aMenuBarBuilder;
	}

	/**
	 * Åpner kundevindu
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(final ActionEvent arg0) {
		menuBarBuilder.openFrame(new PacklistView(packlistViewHandler));

	}
}