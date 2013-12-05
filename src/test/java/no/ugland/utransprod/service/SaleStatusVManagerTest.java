package no.ugland.utransprod.service;

import java.util.List;

import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.excel.ExcelReportSettingSaleStatus;

import org.fest.assertions.Assertions;
import org.junit.Before;
import org.junit.Test;

public class SaleStatusVManagerTest {
	private SaleStatusVManager saleStatusVManager;
	private ProductAreaManager productAreaManager;
	
	@Before
	public void settopp(){
		saleStatusVManager=(SaleStatusVManager)ModelUtil.getBean(SaleStatusVManager.MANAGER_NAME);;
		productAreaManager=(ProductAreaManager)ModelUtil.getBean(ProductAreaManager.MANAGER_NAME);;
	}

	@Test
	public void skalHente() {
		ProductArea productArea = productAreaManager.findByName("Garasje villa");
		ExcelReportSettingSaleStatus params=new ExcelReportSettingSaleStatus();
		params.setProductArea(productArea);
		params.setUseOrder(true);
		List<?> saleStatusList = saleStatusVManager.findByParams(params);
		Assertions.assertThat(saleStatusList).isNotEmpty();
	}

}
