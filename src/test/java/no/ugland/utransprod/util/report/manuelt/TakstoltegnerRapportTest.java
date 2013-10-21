package no.ugland.utransprod.util.report.manuelt;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

import java.util.Collection;
import java.util.Map;

import no.ugland.utransprod.model.TakstoltegnerV;
import no.ugland.utransprod.model.TakstoltegnerVSum;
import no.ugland.utransprod.service.TakstoltegnerVManager;
import no.ugland.utransprod.test.FastTests;
import no.ugland.utransprod.test.ManuellTest;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Periode;
import no.ugland.utransprod.util.excel.ExcelReportEnum;
import no.ugland.utransprod.util.excel.ExcelReportSetting;
import no.ugland.utransprod.util.excel.ExcelUtil;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.google.common.collect.Maps;
@Category(ManuellTest.class)
public class TakstoltegnerRapportTest {

	@Test
	public void generateTakstoltegnerRapport() throws Exception{
		TakstoltegnerVManager takstoltegnerVManager=(TakstoltegnerVManager)ModelUtil.getBean(TakstoltegnerVManager.MANAGER_NAME);
		Periode periode=new Periode(2010, 4, 5);
		Map<TakstoltegnerVSum,Collection<TakstoltegnerV>> tegnere=takstoltegnerVManager.findByPeriode(periode);
		
		assertNotNull(tegnere);
		
		ExcelUtil.setUseUniqueFileName(false);
		ExcelUtil excelUtil=new ExcelUtil();
		
		ExcelReportSetting reportSetting=new ExcelReportSetting(ExcelReportEnum.TAKSTOLTEGNER);
		reportSetting.setYear(2010);
		reportSetting.setWeekFrom(4);
		reportSetting.setWeekTo(5);
		
		Map<Object,Object> data=Maps.newHashMap();
		data.put("Reportdata", tegnere);
		
		excelUtil.generateTakstoltegnerReport(reportSetting, data);
		
		ExcelUtil excelUtilReader=new ExcelUtil();
		excelUtilReader.openExcelFileForRead("excel/Takstoltegner.xls");
		
		String cellValue=excelUtilReader.readCell(0, 0, null);
		assertEquals("Takstoltegning uke 4 - 5 2010", cellValue);
		
		cellValue=excelUtilReader.readCell(3, 1, null);
		assertEquals("Takstol", cellValue);
		
		cellValue=excelUtilReader.readCell(3, 3, null);
		assertEquals("Garasje", cellValue);
		
		cellValue=excelUtilReader.readCell(3, 5, null);
		assertEquals("Byggelement", cellValue);
		
		cellValue=excelUtilReader.readCell(4, 0, null);
		assertEquals("Tegner", cellValue);
		
		cellValue=excelUtilReader.readCell(4, 1, null);
		assertEquals("Antall", cellValue);
		
		cellValue=excelUtilReader.readCell(4, 2, null);
		assertEquals("Sum", cellValue);
		
		cellValue=excelUtilReader.readCell(4, 3, null);
		assertEquals("Antall", cellValue);
		
		cellValue=excelUtilReader.readCell(4, 4, null);
		assertEquals("Sum", cellValue);
		
		cellValue=excelUtilReader.readCell(4, 5, null);
		assertEquals("Antall", cellValue);
		
		cellValue=excelUtilReader.readCell(4, 6, null);
		assertEquals("Sum", cellValue);
		
		
		boolean drawerFound=false;
		for(int i=5;i<=8;i++){
			cellValue=excelUtilReader.readCell(i, 0, null);
			
			if(cellValue!=null&&cellValue.equalsIgnoreCase("Tom Andersen")){
				drawerFound=true;
				cellValue=excelUtilReader.readCell(i, 1, null);
				assertEquals("1.0", cellValue);
				
				cellValue=excelUtilReader.readCell(i, 2, null);
				assertEquals("8344.0", cellValue);
			}
		}
		assertTrue(drawerFound);
		
		cellValue=excelUtilReader.readCell(10, 0, null);
		assertEquals("Grunnlag", cellValue);
		
		cellValue=excelUtilReader.readCell(11, 0, null);
		assertEquals("Tegner", cellValue);
		
		
		
		
		
	}
}
