package no.ugland.utransprod.gui.handlers;

import java.awt.Component;
import java.math.BigDecimal;
import java.sql.SQLException;

import javax.swing.JLabel;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.dao.BudgetDAO;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.model.BudgetType;
import no.ugland.utransprod.model.Budget;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.model.Produksjonskapasitet;
import no.ugland.utransprod.service.BudgetManager;
import no.ugland.utransprod.service.ProduksjonskapasitetManager;
import no.ugland.utransprod.util.FileExtensionFilter;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Threadable;
import no.ugland.utransprod.util.Util;
import no.ugland.utransprod.util.excel.ExcelUtil;

public class ProduksjonskapasitetImportHandler implements Threadable {
	private Component parentComponent;
	private WindowInterface waitWindow;
	private String openDir;
	private ProduksjonskapasitetManager produksjonskapasitetManager;

	public ProduksjonskapasitetImportHandler(Component parent, String aOpenDir,
			ProduksjonskapasitetManager produksjonskapasitetManager) {
		this.parentComponent = parent;
		this.openDir = aOpenDir;
		this.produksjonskapasitetManager = produksjonskapasitetManager;

	}

	public void doWhenFinished(Object object) {
		if (object != null) {
			Util.showErrorDialog(this.parentComponent, "Feil", object.toString());
		} else {
			Util.showMsgDialog(this.parentComponent, "Importert", "Produksjonskapasitet er importert");

		}

		if (this.waitWindow != null) {
			this.waitWindow.dispose();
		}

	}

	public Object doWork(Object[] params, JLabel labelInfo) {
		String errorMsg = null;
		try {
			if (params != null) {
				this.waitWindow = (WindowInterface) params[0];
			}
			labelInfo.setText("Importerer produksjonskapasitet...");
			this.importBudget(labelInfo, this.waitWindow);

		} catch (ProTransException var5) {
			var5.printStackTrace();
			errorMsg = var5.getMessage();
		}
		return errorMsg;
	}

	public void enableComponents(boolean enable) {
	}

	public final void importBudget(JLabel label, WindowInterface window) throws ProTransException {
		BudgetManager productionBudgetManager = (BudgetManager) ModelUtil.getBean("budgetManager");

		productionBudgetManager.setLabelInfo(label);
		String excelFileName = Util.getFileName(this.parentComponent,
				new FileExtensionFilter(new String[] { "xlsx", "xls" }), this.openDir);

		if (excelFileName != null) {
			ExcelUtil excelUtil = new ExcelUtil();
			excelUtil.openExcelFileForRead(excelFileName);
			ExcelUtil.checkFileFormat(excelUtil,
					"Aar;Uke;Avdeling;Navn;Timer;Service;Innarbeiding;Avspasering;Lager;Annet;Hms;Estimert;Prodinternt;Prodestimert;Kommentar;");

			importerProduksjonskapasitet(excelUtil);

//			productionBudgetManager.importBudget(excelFileName, this.budgetType);
		}

	}

	private void importerProduksjonskapasitet(final ExcelUtil excelUtil) throws ProTransException {
		int row = 1;
		Integer aar = excelUtil.readCellAsInteger(row, 0);
//		String week = excelUtil.readCell(row, COLUMN_WEEK, "%1$04.0f");
//		String productAreaName = excelUtil.readCell(row, budgetType.getProductAreaColumn(), null);
//		ProductArea productArea = productAreaManager.findByName(productAreaName);

//		if (productArea == null) {
//			throw new ProTransException("Produktområde " + productAreaName + " ble ikke funnet");
//		}

		while (aar != null) {
//			if (infoLabel != null) {
//				infoLabel.setText(infoText + row);
//			}
			importerLinje(row, excelUtil);
			row++;
			aar = excelUtil.readCellAsInteger(row, 0);
//			week = excelUtil.readCell(row, COLUMN_WEEK, "%1$04.0f");
		}
	}

	private void importerLinje(final int row, final ExcelUtil excelUtil) throws ProTransException {
		Produksjonskapasitet produksjonskapasitet = new Produksjonskapasitet(excelUtil.readCellAsInteger(row, 0),excelUtil.readCellAsInteger(row, 1),
				excelUtil.readCell(row, 2, null), excelUtil.readCell(row, 3, null),
				excelUtil.readCellAsBigDecimal(row, 4), excelUtil.readCellAsBigDecimal(row, 5),
				excelUtil.readCellAsBigDecimal(row, 6), excelUtil.readCellAsBigDecimal(row, 7),
				excelUtil.readCellAsBigDecimal(row, 8), excelUtil.readCellAsBigDecimal(row, 9),
				excelUtil.readCellAsBigDecimal(row, 10), excelUtil.readCellAsBigDecimal(row, 11),
				excelUtil.readCellAsBigDecimal(row, 12), excelUtil.readCellAsBigDecimal(row, 13),
				excelUtil.readCell(row, 14, null));
		try {
			produksjonskapasitetManager.saveObject(produksjonskapasitet);
		} catch (ProTransException e) {
			Util.showErrorDialog((Component) null, "Lagring feiler", e.getMessage());
			throw e;
		}
//		Budget budget = budgetType.validateAndGetBudget(excelUtil, row, year, productArea, (BudgetDAO) dao,
//				applicationUserDAO);
//		saveObject(budget);
	}
}
