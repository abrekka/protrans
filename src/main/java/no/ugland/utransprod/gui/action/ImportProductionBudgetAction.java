package no.ugland.utransprod.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import no.ugland.utransprod.gui.ProTransMain;
import no.ugland.utransprod.gui.handlers.ProductionBudgetImportHandler;
import no.ugland.utransprod.gui.model.BudgetType;
import no.ugland.utransprod.util.Util;

public class ImportProductionBudgetAction extends AbstractImportBudgetAction{

	private static final long serialVersionUID = 1L;
	
	public ImportProductionBudgetAction() {
		super("Importer budsjettall produksjon...",BudgetType.PRODUCTION);
	}

	

}
