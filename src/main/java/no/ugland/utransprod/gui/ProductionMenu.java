package no.ugland.utransprod.gui;

import java.awt.event.KeyEvent;

import javax.swing.JMenu;

import no.ugland.utransprod.gui.action.FrontProductionAction;
import no.ugland.utransprod.gui.action.GavlProductionAction;
import no.ugland.utransprod.gui.action.ProductionOverviewAction2;
import no.ugland.utransprod.gui.action.ProductionTimeAction;
import no.ugland.utransprod.gui.action.VeggProductionAction;

import com.google.inject.Inject;

public class ProductionMenu extends ProTransMenu {
	private GavlProductionAction gavlProductionAction;
	// private TakstolProductionAction takstolProductionAction;
	private VeggProductionAction veggProductionAction;
	private FrontProductionAction frontProductionAction;
	private ProductionBudgetAction productionBudgetAction;
	// private ProductionOverviewAction productionOverviewAction;

	private ProductionOverviewAction2 productionOverviewAction2;
	private ProductionTimeAction productionTimeAction;

	public ProductionMenu(Login aLogin) {
		super(aLogin);
	}

	@Inject
	public ProductionMenu(Login aLogin, GavlProductionAction aGavlProductionAction,
			// TakstolProductionAction aTakstolProductionAction,

			VeggProductionAction aVeggProductionAction, FrontProductionAction aFrontProductionAction,
			ProductionBudgetAction aProductionBudgetAction,
			// ProductionOverviewAction aProductionOverviewAction,
			ProductionOverviewAction2 aProductionOverviewAction2, ProductionTimeAction aProductionTimeAction) {
		super(aLogin);
		// productionOverviewAction = aProductionOverviewAction;
		productionOverviewAction2 = aProductionOverviewAction2;
		productionBudgetAction = aProductionBudgetAction;
		frontProductionAction = aFrontProductionAction;
		veggProductionAction = aVeggProductionAction;
		// takstolProductionAction = aTakstolProductionAction;
		gavlProductionAction = aGavlProductionAction;
		productionTimeAction = aProductionTimeAction;
	}

	@Override
	public JMenu buildMenu() {
		JMenu menuProduction = addMenu("Produksjon", KeyEvent.VK_P);
		addMenuItem(menuProduction, gavlProductionAction, KeyEvent.VK_G, null, null, null, "Gavl", false);
		// addMenuItem(menuProduction, takstolProductionAction, KeyEvent.VK_T,
		// null, null, null, "Takstol", false);
		addMenuItem(menuProduction, veggProductionAction, KeyEvent.VK_T, null, null, null, "Vegg", false);
		addMenuItem(menuProduction, frontProductionAction, KeyEvent.VK_F, null, null, null, "Front", false);
		addMenuItem(menuProduction, productionBudgetAction, KeyEvent.VK_B, null, null, null, "Budsjett", false);
		// addMenuItem(menuProduction, productionOverviewAction, KeyEvent.VK_O,
		// null, null, null, "Produksjonsoversikt", false);
		addMenuItem(menuProduction, productionOverviewAction2, KeyEvent.VK_O, null, null, null, "Produksjonsoversikt",
				false);
		addMenuItem(menuProduction, productionTimeAction, KeyEvent.VK_O, null, null, null, "Produksjonstid", false);
		return menuProduction;
	}

	public void setGavlProductionAction(GavlProductionAction aGavlProductionAction) {
		gavlProductionAction = aGavlProductionAction;

	}

}
