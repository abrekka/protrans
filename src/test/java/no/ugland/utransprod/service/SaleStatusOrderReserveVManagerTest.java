package no.ugland.utransprod.service;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import java.math.BigDecimal;

import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.model.SaleStatusOrderReserveV;
import no.ugland.utransprod.test.FastTests;
import no.ugland.utransprod.util.ModelUtil;

import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(FastTests.class)
public class SaleStatusOrderReserveVManagerTest {

    @Test
    public void getReserveForGarasje() {
	SaleStatusOrderReserveVManager salesStatusOrderReserveVManager = (SaleStatusOrderReserveVManager) ModelUtil
		.getBean(SaleStatusOrderReserveVManager.MANAGER_NAME);
	ProductAreaManager productAreaManager = (ProductAreaManager) ModelUtil.getBean(ProductAreaManager.MANAGER_NAME);
	ProductArea productArea = productAreaManager.findByName("Villa Element");
	assertNotNull(productArea);
	SaleStatusOrderReserveV reserve = salesStatusOrderReserveVManager.findByProductArea(productArea);
	assertNotNull(reserve);
	assertEquals(BigDecimal.valueOf(200000), reserve.getOrderReserve());
    }
}
