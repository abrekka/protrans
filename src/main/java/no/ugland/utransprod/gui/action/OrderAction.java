package no.ugland.utransprod.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import no.ugland.utransprod.gui.MenuBarBuilderInterface;
import no.ugland.utransprod.gui.OrderOverviewView;
import no.ugland.utransprod.gui.handlers.OrderViewHandler;
import no.ugland.utransprod.gui.handlers.OrderViewHandlerFactory;
import no.ugland.utransprod.service.OrderManager;

import com.google.inject.Inject;

/**
 * Klasse som håndterer visning av ordre
 * 
 * @author atle.brekka
 */
public class OrderAction extends AbstractAction {
	/**
	 * 
	 */
	private final MenuBarBuilderInterface menuBarBuilder;
	private OrderViewHandler orderViewHandler;
	private static final long serialVersionUID = 1L;

	@Inject
	public OrderAction(MenuBarBuilderInterface aMenuBarBuilder,OrderViewHandlerFactory aOrderViewHandlerFactory) {
		super("Ordre...");
		orderViewHandler=aOrderViewHandlerFactory.create(true);
		this.menuBarBuilder = aMenuBarBuilder;
	}

	/**
	 * Åpner ordrevindu
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(final ActionEvent arg0) {
		menuBarBuilder.openFrame(new OrderOverviewView(orderViewHandler));
	}
}