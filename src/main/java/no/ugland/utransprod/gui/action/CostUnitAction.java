package no.ugland.utransprod.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.MenuBarBuilderInterface;
import no.ugland.utransprod.gui.OverviewView;
import no.ugland.utransprod.gui.handlers.CostUnitViewHandler;
import no.ugland.utransprod.gui.model.CostUnitModel;
import no.ugland.utransprod.model.CostUnit;
import no.ugland.utransprod.service.CostUnitManager;

import com.google.inject.Inject;

/**
 * Klase som håndterer opprettelse av vindu for kostnadsenheter
 * 
 * @author atle.brekka
 */
public class CostUnitAction extends AbstractAction {
	/**
	 * 
	 */
	private final MenuBarBuilderInterface menuBarBuilder;
	private CostUnitManager costUnitManager;
	private Login login;
	private static final long serialVersionUID = 1L;

	@Inject
	public CostUnitAction(MenuBarBuilderInterface aMenuBarBuilder,CostUnitManager aCostUnitManager,Login aLogin) {
		super("Kostnadsenheter...");
		login=aLogin;
		costUnitManager=aCostUnitManager;
		this.menuBarBuilder = aMenuBarBuilder;
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(final ActionEvent arg0) {
		menuBarBuilder.openFrame(new OverviewView<CostUnit, CostUnitModel>(
				new CostUnitViewHandler(login,costUnitManager)));

	}
}