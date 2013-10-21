package no.ugland.utransprod.gui.action;

import no.ugland.utransprod.gui.model.BudgetType;

public class ImportSalesmanBudgetAction extends AbstractImportBudgetAction{
private static final long serialVersionUID = 1L;
	
	public ImportSalesmanBudgetAction() {
		super("Importer budsjettall selger...",BudgetType.SALESMAN);
	}

}
