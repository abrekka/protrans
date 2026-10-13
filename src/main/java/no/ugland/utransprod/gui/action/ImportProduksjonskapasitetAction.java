package no.ugland.utransprod.gui.action;

import java.awt.event.ActionEvent;

import no.ugland.utransprod.gui.ProTransMain;
import no.ugland.utransprod.gui.handlers.ProductionBudgetImportHandler;
import no.ugland.utransprod.gui.handlers.ProduksjonskapasitetImportHandler;
import no.ugland.utransprod.gui.model.BudgetType;
import no.ugland.utransprod.service.ProduksjonskapasitetManager;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Util;

public class ImportProduksjonskapasitetAction extends AbstractImportBudgetAction {

	private static final long serialVersionUID = 1L;
	private ProduksjonskapasitetManager produksjonskapasitetManager;

	public ImportProduksjonskapasitetAction() {
		super("Importer produksjonskapasitet...",BudgetType.PRODUCTION);
		this.produksjonskapasitetManager=(ProduksjonskapasitetManager)ModelUtil.getBean("produksjonskapasitetManager");
	}

	public void actionPerformed(ActionEvent e) {
		Util.createDialogAndRunInThread(
				new ProduksjonskapasitetImportHandler(ProTransMain.PRO_TRANS_MAIN, null, produksjonskapasitetManager));

	}

}
