package no.ugland.utransprod.service;

import static org.junit.Assert.assertNotNull;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.test.FastTests;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.excel.ExcelReportEnum;
import no.ugland.utransprod.util.excel.ExcelReportSettingOwnProduction;

import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * @author atle.brekka
 *
 */
@Category(FastTests.class)
public class OwnProductionVManagerTest {

	@Test
	public void testFindByParams()throws Exception{
		OwnProductionVManager ownProductionVManager=(OwnProductionVManager)ModelUtil.getBean("ownProductionVManager");
		ExcelReportSettingOwnProduction params =new ExcelReportSettingOwnProduction(ExcelReportEnum.OWN_PRODUCTION);
		ProductAreaGroup productAreaGroup=new ProductAreaGroup();
		productAreaGroup.setProductAreaGroupName("Alle");
		params.setProductAreaGroup(productAreaGroup);
		assertNotNull(ownProductionVManager.findByParams(params));
	}
}
