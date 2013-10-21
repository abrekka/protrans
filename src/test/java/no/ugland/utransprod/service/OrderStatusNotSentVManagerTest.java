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
public class OrderStatusNotSentVManagerTest {

	@Test
	public void testFindByParams()throws Exception{
		OrderStatusNotSentVManager orderStatusNotSentVManager=(OrderStatusNotSentVManager)ModelUtil.getBean("orderStatusNotSentVManager");
		ExcelReportSetting params =new ExcelReportSetting(ExcelReportEnum.PRODUCTIVITY_ORDER_STATUS);
		assertNotNull(orderStatusNotSentVManager.findByParams(params));
	}
}
