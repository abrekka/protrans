package no.ugland.utransprod.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import no.ugland.utransprod.gui.IncomingOrderOverviewView;
import no.ugland.utransprod.gui.MenuBarBuilderInterface;
import no.ugland.utransprod.gui.handlers.IncomingOrderViewHandler;
import no.ugland.utransprod.gui.handlers.OrderViewHandler;
import no.ugland.utransprod.gui.handlers.OrderViewHandlerFactory;
import no.ugland.utransprod.service.IncomingOrderManager;
import no.ugland.utransprod.service.OrderManager;

import com.google.inject.Inject;

/**
 * Håndterer menyvalg Ordre til avrop...
 * 
 * @author atle.brekka
 */
public class IncomingOrderAction extends AbstractAction {
	/**
	 * 
	 */
	private final MenuBarBuilderInterface menuBarBuilder;
	private static final long serialVersionUID = 1L;
	private IncomingOrderOverviewView incomingOrderOverviewView;

	@Inject
	public IncomingOrderAction(MenuBarBuilderInterface aMenuBarBuilder,IncomingOrderOverviewView aIncomingOrderOverviewView) {
		super("Ordre til avrop...");
		incomingOrderOverviewView=aIncomingOrderOverviewView;
		this.menuBarBuilder = aMenuBarBuilder;
	}

	/**
	 * Åpner kundevindu
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(final ActionEvent arg0) {
	
		
		menuBarBuilder.openFrame(incomingOrderOverviewView);

	}
}