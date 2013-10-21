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
public class PostShipmentCountVManagerTest {

	@Test
	public void testFindByParams()throws Exception{
		PostShipmentCountVManager postShipmentCountVManager=(PostShipmentCountVManager)ModelUtil.getBean("postShipmentCountVManager");
		ExcelReportSetting params =new ExcelReportSetting(ExcelReportEnum.POST_SHIPMENT_SENT_COUNT);
		assertNotNull(postShipmentCountVManager.findByParams(params));
	}
}
