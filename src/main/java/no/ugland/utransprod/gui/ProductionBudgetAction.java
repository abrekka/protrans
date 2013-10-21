package no.ugland.utransprod.gui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import no.ugland.utransprod.gui.handlers.ProductionBudgetViewHandler;
import no.ugland.utransprod.gui.model.ProductionBudgetModel;
import no.ugland.utransprod.model.Budget;
import no.ugland.utransprod.service.BudgetManager;

import com.google.inject.Inject;

/**
 * Håndterer menyvalg Budsjett...
 * 
 * @author atle.brekka
 */
class ProductionBudgetAction extends AbstractAction {
	/**
	 * 
	 */
	private final MenuBarBuilderInterface menuBarBuilder;
	private BudgetManager productionBudgetManager;
	private Login login;
	private static final long serialVersionUID = 1L;

	@Inject
	public ProductionBudgetAction(MenuBarBuilderInterface aMenuBarBuilder,BudgetManager aProductionBudgetManager,Login aLogin) {
		super("Budsjett...");
		login=aLogin;
		productionBudgetManager=aProductionBudgetManager;
		this.menuBarBuilder = aMenuBarBuilder;
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(final ActionEvent arg0) {
		menuBarBuilder.openFrame(new OverviewView<Budget, ProductionBudgetModel>(
				new ProductionBudgetViewHandler(login, true,productionBudgetManager)));

	}
}