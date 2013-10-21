package no.ugland.utransprod.service;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;

import java.util.List;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.PacklistV;
import no.ugland.utransprod.test.SlowTests;
import no.ugland.utransprod.util.ModelUtil;

import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * @author atle.brekka
 * 
 */
@Category(SlowTests.class)
public class PacklistVManagerTest {

	@Test
	public void testFindAll() throws ProTransException {
		PacklistVManager packlistVManager = (PacklistVManager) ModelUtil
				.getBean("packlistVManager");
		OrderManager orderManager = (OrderManager) ModelUtil
				.getBean(OrderManager.MANAGER_NAME);

		List<PacklistV> liste = packlistVManager.findAllApplyable();
		PacklistV packlistV = liste.get(0);
		assertFalse(liste.isEmpty());
		assertNull(packlistV.getProductionBasis());

		Order order = orderManager.findByOrderNr(packlistV.getOrderNr());
		order.setProductionBasis(Integer.valueOf(70));
		orderManager.saveOrder(order);

		liste = packlistVManager.findApplyableByOrderNr(packlistV.getOrderNr());
		assertEquals(1, liste.size());
		assertEquals(Integer.valueOf(70), liste.get(0).getProductionBasis());

		order = orderManager.findByOrderNr(packlistV.getOrderNr());
		order.setProductionBasis(null);
		orderManager.saveOrder(order);

	}
}
