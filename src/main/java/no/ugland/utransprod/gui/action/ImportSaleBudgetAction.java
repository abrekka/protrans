package no.ugland.utransprod.gui.action;

import javax.swing.AbstractAction;

import no.ugland.utransprod.gui.model.BudgetType;

public class ImportSaleBudgetAction extends AbstractImportBudgetAction{
private static final long serialVersionUID = 1L;
	
	public ImportSaleBudgetAction() {
		super("Importer budsjettall salg...",BudgetType.SALE);
	}
}
