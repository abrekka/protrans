package no.ugland.utransprod.util.report.manuelt;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import java.net.URL;
import java.util.Collection;
import java.util.Map;

import no.ugland.utransprod.gui.model.BudgetType;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.model.SalesmanGoal;
import no.ugland.utransprod.service.BudgetManager;
import no.ugland.utransprod.service.ProductAreaManager;
import no.ugland.utransprod.service.SalesVManager;
import no.ugland.utransprod.test.FastTests;
import no.ugland.utransprod.test.ManuellTest;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Periode;
import no.ugland.utransprod.util.excel.ExcelReportEnum;
import no.ugland.utransprod.util.excel.ExcelReportSetting;
import no.ugland.utransprod.util.excel.ExcelUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.google.common.collect.Maps;
@Category(ManuellTest.class)
public class SalesgoalReportTest {
	private BudgetManager budgetManager;
	private SalesVManager salesVManager;
	private ProductAreaManager productAreaManager;
	
	@Before
	public void setUp() throws Exception {
		salesVManager = (SalesVManager) ModelUtil.getBean("salesVManager");
		budgetManager = (BudgetManager) ModelUtil
		.getBean(BudgetManager.MANAGER_NAME);
		productAreaManager = (ProductAreaManager) ModelUtil
		.getBean(ProductAreaManager.MANAGER_NAME);
	}
	
	@After
	public void tearDown(){
		ProductArea productAreaVilla = productAreaManager.findByName("Garasje villa");
		budgetManager.removeForYearProductArea(2010,productAreaVilla,BudgetType.SALESMAN);
	}

	@Test
	public void generateSalesgoalReport() throws Exception{
		URL url = getClass().getClassLoader().getResource("Budsjett_salesman_import_villa.xls");
        budgetManager.importBudget(url.getFile(),BudgetType.SALESMAN);
        Periode periode = new Periode(2010, 5, 5);
        Collection<SalesmanGoal> salesGoalList = salesVManager.getSalesGoalList(periode);
		ExcelUtil.setUseUniqueFileName(false);
		ExcelUtil excelUtil=new ExcelUtil();
		
		ExcelReportSetting reportSetting=new ExcelReportSetting(ExcelReportEnum.SALES_GOAL);
		reportSetting.setYear(2010);
		reportSetting.setWeekFrom(5);
		reportSetting.setWeekTo(5);
		Map<Object,Object> data=Maps.newHashMap();
		data.put("Reportdata", salesGoalList);
		excelUtil.generateSalesGoalReport(reportSetting, data);
		
		ExcelUtil excelUtilReader=new ExcelUtil();
		excelUtilReader.openExcelFileForRead("excel/Salgsmål.xls");
		
		String cellValue=excelUtilReader.readCell(2, 0, null);
		assertEquals("SALGSMÅL", cellValue);
		cellValue=excelUtilReader.readCell(3, 2, null);
		assertEquals("Salgsmål 2010", cellValue);
		cellValue=excelUtilReader.readCell(3, 5, null);
		assertEquals("Uke 5", cellValue);
		cellValue=excelUtilReader.readCell(3, 13, null);
		assertEquals("Akkumulert pr uke 5", cellValue);
		cellValue=excelUtilReader.readCell(4, 5, null);
		assertEquals("pr uke", cellValue);
		cellValue=excelUtilReader.readCell(4, 10, null);
		assertEquals("Endring fra i fjor(diff):", cellValue);
		cellValue=excelUtilReader.readCell(4, 13, null);
		assertEquals("pr uke", cellValue);
		cellValue=excelUtilReader.readCell(4, 18, null);
		assertEquals("Endring fra i fjor(diff):", cellValue);
		cellValue=excelUtilReader.readCell(5, 0, null);
		assertEquals("Produktområde", cellValue);
		cellValue=excelUtilReader.readCell(5, 1, null);
		assertEquals("Selger", cellValue);
		cellValue=excelUtilReader.readCell(5, 2, null);
		assertEquals("Ordre", cellValue);
		cellValue=excelUtilReader.readCell(5, 3, null);
		assertEquals("Tilbudsmengde", cellValue);
		cellValue=excelUtilReader.readCell(5, 4, null);
		assertEquals("Tilslagsprosent", cellValue);
		cellValue=excelUtilReader.readCell(5, 5, null);
		assertEquals("Tilslagsprosent", cellValue);
		cellValue=excelUtilReader.readCell(5, 6, null);
		assertEquals("DG", cellValue);
		cellValue=excelUtilReader.readCell(5, 7, null);
		assertEquals("Tilbudsmengde", cellValue);
		cellValue=excelUtilReader.readCell(5, 8, null);
		assertEquals("Ordremengde", cellValue);
		cellValue=excelUtilReader.readCell(5, 9, null);
		assertEquals("Avrop", cellValue);
		cellValue=excelUtilReader.readCell(5, 10, null);
		assertEquals("Tilslagsprosent", cellValue);
		cellValue=excelUtilReader.readCell(5, 11, null);
		assertEquals("Tilbudsmengde", cellValue);
		cellValue=excelUtilReader.readCell(5, 12, null);
		assertEquals("Ordremengde", cellValue);
		cellValue=excelUtilReader.readCell(5, 13, null);
		assertEquals("Tilslagsprosent", cellValue);
		cellValue=excelUtilReader.readCell(5, 14, null);
		assertEquals("DG", cellValue);
		cellValue=excelUtilReader.readCell(5, 15, null);
		assertEquals("Tilbudsmengde", cellValue);
		cellValue=excelUtilReader.readCell(5, 16, null);
		assertEquals("Ordremengde", cellValue);
		cellValue=excelUtilReader.readCell(5, 17, null);
		assertEquals("Avrop", cellValue);
		cellValue=excelUtilReader.readCell(5, 18, null);
		assertEquals("Tilslagsprosent", cellValue);
		cellValue=excelUtilReader.readCell(5, 19, null);
		assertEquals("Tilbudsmengde", cellValue);
		cellValue=excelUtilReader.readCell(5, 20, null);
		assertEquals("Ordremengde", cellValue);
		cellValue=excelUtilReader.readCell(6, 0, null);
		assertEquals("Garasje villa", cellValue);
		cellValue=excelUtilReader.readCell(6, 1, null);
		
		boolean salesmanFound=false;
		for(int i=6;i<=30;i++){
			cellValue=excelUtilReader.readCell(i, 1, null);
			if(cellValue.equalsIgnoreCase("Aleksander Federici")){
				salesmanFound=true;
			}
		}
		assertTrue(salesmanFound);
	}
}
