package no.ugland.utransprod.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.MenuBarBuilderInterface;
import no.ugland.utransprod.gui.ProductionOverviewView;
import no.ugland.utransprod.gui.handlers.OrderViewHandler;
import no.ugland.utransprod.gui.handlers.ProductionOverviewViewHandler;
import no.ugland.utransprod.service.DeviationManager;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.service.ProcentDoneManager;
import no.ugland.utransprod.service.VismaFileCreator;

import com.google.inject.Inject;

/**
 * Håndeterer menyvalg Produksjonsoversikt...
 * 
 * @author atle.brekka
 */
public class ProductionOverviewAction extends AbstractAction {
	/**
     * 
     */
	private final MenuBarBuilderInterface menuBarBuilder;
	private static final long serialVersionUID = 1L;
	
	private ProductionOverviewView productionOverviewView;

	@Inject
	public ProductionOverviewAction(
			final MenuBarBuilderInterface aMenuBarBuilderInterface,
			final ProductionOverviewView aProductionOverviewView) {
		super("Produksjonsoversikt...");
		productionOverviewView=aProductionOverviewView;
		this.menuBarBuilder = aMenuBarBuilderInterface;
		
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(final ActionEvent arg0) {
		//OrderViewHandler orderViewHandler = new OrderViewHandler(true, login,orderManager);
		menuBarBuilder.openFrame(productionOverviewView);

	}
}