package no.ugland.utransprod.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import no.ugland.utransprod.gui.MenuBarBuilderInterface;
import no.ugland.utransprod.gui.ProductionOverviewView2;

import com.google.inject.Inject;

/**
 * Håndeterer menyvalg Produksjonsoversikt...
 * 
 * @author atle.brekka
 */
public class ProductionOverviewAction2 extends AbstractAction {
    /**
     * 
     */
    private final MenuBarBuilderInterface menuBarBuilder;
    private static final long serialVersionUID = 1L;

    private ProductionOverviewView2 productionOverviewView;

    @Inject
    public ProductionOverviewAction2(final MenuBarBuilderInterface aMenuBarBuilderInterface, final ProductionOverviewView2 aProductionOverviewView) {
	super("Produksjonsoversikt2...");
	productionOverviewView = aProductionOverviewView;
	this.menuBarBuilder = aMenuBarBuilderInterface;

    }

    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(final ActionEvent arg0) {
	// OrderViewHandler orderViewHandler = new OrderViewHandler(true,
	// login,orderManager);
	menuBarBuilder.openFrame(productionOverviewView);

    }
}