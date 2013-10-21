package no.ugland.utransprod.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import no.ugland.utransprod.gui.ProTransMain;
import no.ugland.utransprod.gui.handlers.ProductionBudgetImportHandler;
import no.ugland.utransprod.gui.model.BudgetType;
import no.ugland.utransprod.util.Util;

public abstract class AbstractImportBudgetAction extends AbstractAction{
	private BudgetType budgetType;
	public AbstractImportBudgetAction(String actionString,BudgetType aBudgetType) {
		super(actionString);
		budgetType=aBudgetType;
	}
	public void actionPerformed(ActionEvent e) {
		Util.createDialogAndRunInThread(new ProductionBudgetImportHandler(
				ProTransMain.PRO_TRANS_MAIN, null,budgetType));
		
	}
}
