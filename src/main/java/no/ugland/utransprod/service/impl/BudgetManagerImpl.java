package no.ugland.utransprod.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.swing.JLabel;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.dao.ApplicationUserDAO;
import no.ugland.utransprod.dao.BudgetDAO;
import no.ugland.utransprod.gui.model.BudgetType;
import no.ugland.utransprod.model.Budget;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.service.BudgetManager;
import no.ugland.utransprod.service.ProductAreaManager;
import no.ugland.utransprod.util.Periode;
import no.ugland.utransprod.util.excel.ExcelUtil;

/**
 * Implementasjon av serviceklasse for tabell PRODUCTION_BUDGET.
 * 
 * @author atle.brekka
 */
public class BudgetManagerImpl extends ManagerImpl<Budget> implements BudgetManager {

    private ProductAreaManager productAreaManager;

    private JLabel infoLabel;

    private String infoText = "Importerer budsjetttall...";

    private ApplicationUserDAO applicationUserDAO;

    public final void setProductAreaManager(final ProductAreaManager aManager) {
	this.productAreaManager = aManager;
    }

    /**
     * @see no.ugland.utransprod.service.OverviewManager#findAll()
     */
    public final List<Budget> findAll() {
	return ((BudgetDAO) dao).findAll();
    }

    /**
     * @param object
     * @return budsjett
     * @see no.ugland.utransprod.service.OverviewManager#findByObject(java.lang.Object)
     */
    public final List<Budget> findByObject(final Budget object) {
	return dao.findByExampleLike(object);
    }

    /**
     * @param object
     * @see no.ugland.utransprod.service.OverviewManager#refreshObject(java.lang.Object)
     */
    public final void refreshObject(final Budget object) {
	((BudgetDAO) dao).refreshProductionBudget(object);

    }

    /**
     * Fjerner budsjett.
     * 
     * @param productionBudget
     */
    public final void removeProductionBudget(final Budget productionBudget) {
	dao.removeObject(productionBudget.getBudgetId());

    }

    /**
     * @param object
     * @see no.ugland.utransprod.service.OverviewManager#removeObject(java.lang.Object)
     */
    public final void removeObject(final Budget object) {
	removeProductionBudget(object);

    }

    /**
     * @param object
     * @see no.ugland.utransprod.service.OverviewManager#saveObject(java.lang.Object)
     */
    public final void saveObject(final Budget object) {
	dao.saveObject(object);

    }

    /**
     * @see no.ugland.utransprod.service.BudgetManager#findByYearAndWeek(java.lang.Integer,
     *      java.lang.Integer, java.lang.Integer,
     *      no.ugland.utransprod.model.ProductArea)
     */
    public final List<Budget> findByYearAndWeek(final Integer year, final Integer week, final Integer notId, final ProductArea productArea,
	    final BudgetType budgetType) {
	return ((BudgetDAO) dao).findByYearAndWeek(year, week, notId, productArea, budgetType);
    }

    /**
     * @see no.ugland.utransprod.service.BudgetManager#findByYearAndWeek(java.lang.Integer,
     *      java.lang.Integer, no.ugland.utransprod.model.ProductArea)
     */
    public final Budget findByYearAndWeek(final Integer year, final Integer week, final ProductArea productArea, BudgetType budgetType) {
	return ((BudgetDAO) dao).findByYearAndWeek(year, week, productArea, budgetType);
    }

    /**
     * @see no.ugland.utransprod.service.BudgetManager#
     *      findByYearAndWeekPrProductAreaGroup(java.lang.Integer,
     *      java.lang.Integer, no.ugland.utransprod.model.ProductAreaGroup)
     */
    public final Budget findByYearAndWeekPrProductAreaGroup(final Integer year, final Integer week, BudgetType budgetType) {
	return ((BudgetDAO) dao).findByYearAndWeek(year, week, budgetType);
    }

    public void importBudget(String excelFileName, BudgetType budgetType) throws ProTransException {
	ExcelUtil excelUtil = new ExcelUtil();
	excelUtil.openExcelFileForRead(excelFileName);
	ExcelUtil.checkFileFormat(excelUtil, budgetType.getHeaderFormat());
	importBudgets(excelUtil, budgetType);
    }

    public final void setLabelInfo(final JLabel aLabel) {
	infoLabel = aLabel;

    }

    private void importBudgets(final ExcelUtil excelUtil, BudgetType budgetType) throws ProTransException {
	int row = 1;
	Integer year = excelUtil.readCellAsInteger(row, budgetType.getYearColumn());
	String week = excelUtil.readCell(row, COLUMN_WEEK, "%1$04.0f");
	String productAreaName = excelUtil.readCell(row, budgetType.getProductAreaColumn(), null);
	ProductArea productArea = productAreaManager.findByName(productAreaName);

	if (productArea == null) {
	    throw new ProTransException("Produktområde " + productAreaName + " ble ikke funnet");
	}

	while (week != null) {
	    if (infoLabel != null) {
		infoLabel.setText(infoText + row);
	    }
	    importBudget(row, excelUtil, year, productArea, budgetType);
	    row++;
	    week = excelUtil.readCell(row, COLUMN_WEEK, "%1$04.0f");
	}
    }

    private void importBudget(final int row, final ExcelUtil excelUtil, final Integer year, final ProductArea productArea, BudgetType budgetType)
	    throws ProTransException {
	Budget budget = budgetType.validateAndGetBudget(excelUtil, row, year, productArea, (BudgetDAO) dao, applicationUserDAO);
	saveObject(budget);
    }

    public List<Budget> findByYear(Integer year, ProductArea productArea, BudgetType budgetType) {
	return ((BudgetDAO) dao).findByYear(year, productArea, budgetType);
    }

    public void removeForYearProductArea(Integer year, ProductArea productArea, BudgetType budgetType) {
	((BudgetDAO) dao).removeForYearProductArea(year, productArea, budgetType);

    }

    public void lazyLoad(Budget object, Enum[] enums) {
	// TODO Auto-generated method stub

    }

    @Override
    protected Serializable getObjectId(Budget object) {
	return object.getBudgetId();
    }

    public Budget findSumPrProductAreaAndPeriode(Periode periode, ProductArea productArea, BudgetType budgetType) {
	return ((BudgetDAO) dao).findSumPrProductAreaAndPeriode(periode, productArea, budgetType);
    }

    public void setApplicationUserDAO(ApplicationUserDAO applicationUserDao) {
	this.applicationUserDAO = applicationUserDao;
    }

    public Budget findByYearAndSalesman(Integer year, String salesman, ProductArea productArea, BudgetType budgetType) {
	return ((BudgetDAO) dao).findByYearAndSalesman(year, salesman, productArea, budgetType);
    }

}
