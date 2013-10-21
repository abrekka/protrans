/**
 * 
 */
package no.ugland.utransprod.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.MenuBarBuilderInterface;
import no.ugland.utransprod.gui.OverviewView;
import no.ugland.utransprod.gui.handlers.ProductionUnitViewHandler;
import no.ugland.utransprod.gui.model.ProductionUnitModel;
import no.ugland.utransprod.model.ProductionUnit;
import no.ugland.utransprod.service.ProductionUnitManager;

import com.google.inject.Inject;

public class ProductionUnitAction extends AbstractAction {
	/**
	 * 
	 */
	private final MenuBarBuilderInterface menuBarBuilder;
	private ProductionUnitManager productionUnitManager;
	private Login login;
	private static final long serialVersionUID = 1L;

	@Inject
	public ProductionUnitAction(MenuBarBuilderInterface aMenuBarBuilder,ProductionUnitManager aProductionUnitManager,Login aLogin) {
		super("Produksjonsenheter...");
		login=aLogin;
		productionUnitManager=aProductionUnitManager;
		this.menuBarBuilder = aMenuBarBuilder;
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(final ActionEvent arg0) {

		menuBarBuilder.openFrame(new OverviewView<ProductionUnit, ProductionUnitModel>(
				new ProductionUnitViewHandler(login,productionUnitManager)));

	}
}