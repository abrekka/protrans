package no.ugland.utransprod.service;

import static org.junit.Assert.assertNotNull;
import no.ugland.utransprod.test.FastTests;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.excel.ExcelReportEnum;
import no.ugland.utransprod.util.excel.ExcelReportSetting;

import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * @author atle.brekka
 *
 */
@Category(FastTests.class)
public class NotInvoicedVManagerTest {

	@Test
	public void testFindByParams()throws Exception{
		NotInvoicedVManager notInvoicedVManager=(NotInvoicedVManager)ModelUtil.getBean("notInvoicedVManager");
		ExcelReportSetting params =new ExcelReportSetting(ExcelReportEnum.PRODUCTIVITY_LIST_NOT_INVOICED);
		assertNotNull(notInvoicedVManager.findByParams(params));
	}
}
