package no.ugland.utransprod.util.excel;

import static org.junit.Assert.assertEquals;

import no.ugland.utransprod.test.FastTests;

import org.junit.Test;
import org.junit.experimental.categories.Category;
@Category(FastTests.class)
public class ExcelReportSettingTest {

	@Test
	public void skalLageStrengAvPeriode(){
		ExcelReportSetting excelReportSetting=new ExcelReportSetting(null);
		excelReportSetting.setYear(2012);
		excelReportSetting.setWeekFrom(1);
		assertEquals("201201",excelReportSetting.getPeriodString());
		excelReportSetting.setWeekTo(2);
		assertEquals("20120102",excelReportSetting.getPeriodString());
	}
}
