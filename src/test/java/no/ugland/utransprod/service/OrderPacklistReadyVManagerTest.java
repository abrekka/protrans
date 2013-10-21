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
public class OrderPacklistReadyVManagerTest {

	@Test
	public void testFindByParams()throws Exception{
		OrderPacklistReadyVManager orderPacklistReadyVManager=(OrderPacklistReadyVManager)ModelUtil.getBean("orderPacklistReadyVManager");
		ExcelReportSetting params =new ExcelReportSetting(ExcelReportEnum.PRODUCTIVITY_PACKLIST);
		assertNotNull(orderPacklistReadyVManager.findByParams(params));
	}
}
