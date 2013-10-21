package no.ugland.utransprod.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.MenuBarBuilderInterface;
import no.ugland.utransprod.gui.RouteView;
import no.ugland.utransprod.gui.handlers.OrderViewHandler;
import no.ugland.utransprod.gui.handlers.RouteViewHandler;
import no.ugland.utransprod.service.ColliManager;
import no.ugland.utransprod.service.DeviationManager;
import no.ugland.utransprod.service.OrderLineManager;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.service.TransportManager;

import com.google.inject.Inject;

/**
 * Klasse som håndterer opprettelse av vindu for transport
 * 
 * @author atle.brekka
 */
public class TransportAction extends AbstractAction {
	/**
	 * 
	 */
	private final MenuBarBuilderInterface menuBarBuilder;
	private static final long serialVersionUID = 1L;

	private RouteView routeView;

	@Inject
	public TransportAction(final MenuBarBuilderInterface aMenuBarBuilder,
			final RouteView aRouteView) {
		super("Transport...");
		routeView = aRouteView;
		menuBarBuilder = aMenuBarBuilder;
	}

	/**
	 * Åpner kundevindu
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(final ActionEvent arg0) {
		// OrderViewHandler orderViewHandler = new OrderViewHandler(true,login,
		// orderManager);

		/*
		 * menuBarBuilder.openFrame(new RouteView(new RouteViewHandler(
		 * orderViewHandler, menuBarBuilder.getApplicationUser(),
		 * menuBarBuilder.
		 * getUserType(),orderManager,deviationManager,transportManager)));
		 */
		menuBarBuilder.openFrame(routeView);

	}
}