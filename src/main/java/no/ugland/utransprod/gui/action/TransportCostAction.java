/**
 * 
 */
package no.ugland.utransprod.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.MenuBarBuilderInterface;
import no.ugland.utransprod.gui.TransportCostView;
import no.ugland.utransprod.gui.handlers.TransportCostViewHandler;
import no.ugland.utransprod.model.ApplicationUser;
import no.ugland.utransprod.service.TransportCostBasisManager;
import no.ugland.utransprod.util.Util;

import com.google.inject.Inject;

public class TransportCostAction extends AbstractAction {
	/**
	 * 
	 */
	private final MenuBarBuilderInterface menuBarBuilder;
	private TransportCostBasisManager transportCostBasisManager;
	private Login login;
	private static final long serialVersionUID = 1L;

	/**
	 * @param aApplicationUser
	 * @param menuBarBuilderImpl TODO
	 */
	@Inject
	public TransportCostAction(MenuBarBuilderInterface aMenuBarBuilder,final TransportCostBasisManager aTransportCostBasisManager,Login aLogin) {
		super("Transportkostnad...");
		login=aLogin;
		transportCostBasisManager=aTransportCostBasisManager;
		this.menuBarBuilder = aMenuBarBuilder;
	}

	/**
	 * Åpner taksteinvindu
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(final ActionEvent arg0) {

		TransportCostView transportCostView = new TransportCostView(
				new TransportCostViewHandler(login,transportCostBasisManager));

		menuBarBuilder.openFrame(transportCostView);

	}
}