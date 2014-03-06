package no.ugland.utransprod.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.net.URL;
import java.util.List;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.model.BudgetType;
import no.ugland.utransprod.model.Budget;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.test.FastTests;
import no.ugland.utransprod.util.ModelUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(FastTests.class)
public class BudgetManagerTest {
    private BudgetManager budgetManager;
    private ProductAreaManager productAreaManager;
    private ProductArea productAreaVilla;
    private ProductArea productAreaTakstol;

    @Before
    public void setUp() throws Exception {
	budgetManager = (BudgetManager) ModelUtil.getBean(BudgetManager.MANAGER_NAME);
	productAreaManager = (ProductAreaManager) ModelUtil.getBean("productAreaManager");
	productAreaVilla = productAreaManager.findByName("Villa Element");
	productAreaTakstol = productAreaManager.findByName("Service");
    }

    @After
    public void tearDown() throws Exception {
	budgetManager.removeForYearProductArea(2009, productAreaVilla, BudgetType.PRODUCTION);
	budgetManager.removeForYearProductArea(2009, productAreaTakstol, BudgetType.PRODUCTION);
	budgetManager.removeForYearProductArea(2009, productAreaVilla, BudgetType.SALE);
    }

    @Test
    public void importProductionBudgetForVilla2009() throws Exception {
	URL url = getClass().getClassLoader().getResource("Budsjett_import_villa.xls");
	budgetManager.importBudget(url.getFile(), BudgetType.PRODUCTION);

	List<Budget> budgets = budgetManager.findByYear(2009, productAreaVilla, BudgetType.PRODUCTION);
	assertNotNull(budgets);
	assertEquals(52, budgets.size());
	int counter = 0;
	for (Budget budget : budgets) {
	    counter++;
	    assertEquals(BigDecimal.valueOf(counter * 1000), budget.getBudgetValue());
	    assertEquals(BigDecimal.valueOf(counter * 100), budget.getBudgetHours());
	}
    }

    @Test
    public void importProductionBudgetForVilla2009Twice() throws Exception {
	URL url = getClass().getClassLoader().getResource("Budsjett_import_villa.xls");
	budgetManager.importBudget(url.getFile(), BudgetType.PRODUCTION);

	List<Budget> budgets = budgetManager.findByYear(2009, productAreaVilla, BudgetType.PRODUCTION);
	assertNotNull(budgets);
	assertEquals(52, budgets.size());
	int counter = 0;
	for (Budget budget : budgets) {
	    counter++;
	    assertEquals(BigDecimal.valueOf(counter * 1000), budget.getBudgetValue());
	}

	try {
	    budgetManager.importBudget(url.getFile(), BudgetType.PRODUCTION);
	    assertEquals(true, false);
	} catch (ProTransException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    @Test
    public void importProductionBudgetForVillaAndTakstol2009() throws Exception {
	URL url = getClass().getClassLoader().getResource("Budsjett_import_villa.xls");
	budgetManager.importBudget(url.getFile(), BudgetType.PRODUCTION);

	List<Budget> budgets = budgetManager.findByYear(2009, productAreaVilla, BudgetType.PRODUCTION);
	assertNotNull(budgets);
	assertEquals(52, budgets.size());
	int counter = 0;
	for (Budget budget : budgets) {
	    counter++;
	    assertEquals(BigDecimal.valueOf(counter * 1000), budget.getBudgetValue());
	}

	url = getClass().getClassLoader().getResource("Budsjett_import_takstol.xls");
	budgetManager.importBudget(url.getFile(), BudgetType.PRODUCTION);

	budgets = budgetManager.findByYear(2009, productAreaTakstol, BudgetType.PRODUCTION);
	assertNotNull(budgets);
	assertEquals(52, budgets.size());
	counter = 0;
	for (Budget budget : budgets) {
	    counter++;
	    assertEquals(BigDecimal.valueOf(counter * 2000), budget.getBudgetValue());
	}
    }

    @Test
    public void importSalesBudgetForVilla2009() throws Exception {
	URL url = getClass().getClassLoader().getResource("Budsjett_sales_import_villa.xls");
	budgetManager.importBudget(url.getFile(), BudgetType.SALE);

	List<Budget> budgets = budgetManager.findByYear(2009, productAreaVilla, BudgetType.SALE);
	assertNotNull(budgets);
	assertEquals(52, budgets.size());
	int counter = 0;
	for (Budget budget : budgets) {
	    counter++;
	    assertEquals(BigDecimal.valueOf(counter * 1000), budget.getBudgetValue());
	}
    }
}
