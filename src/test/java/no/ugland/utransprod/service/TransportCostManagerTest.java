package no.ugland.utransprod.service;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import no.ugland.utransprod.dao.TransportCostDAO;
import no.ugland.utransprod.model.CostType;
import no.ugland.utransprod.model.CostUnit;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderCost;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.model.Supplier;
import no.ugland.utransprod.model.Transport;
import no.ugland.utransprod.model.TransportCost;
import no.ugland.utransprod.model.TransportCostBasis;
import no.ugland.utransprod.service.impl.TransportCostManagerImpl;
import no.ugland.utransprod.test.FastTests;
import no.ugland.utransprod.util.Periode;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.common.collect.Lists;
@Category(FastTests.class)
public class TransportCostManagerTest {

	private static final Integer YEAR = 2010;
	private static final Integer WEEK_50 = 50;
	private TransportCostManager transportCostManager;
	@Mock
	private TransportManager transportManager;
	@Mock
	private TransportCostBasisManager transportCostBasisManager;
	@Mock
	private TransportCostDAO transportCostDAO;
	@Mock
	private CostTypeManager costTypeManager;
	@Mock
	private CostUnitManager costUnitManager;
	@Mock
	private TransportCostAdditionManager transportCostAdditionManager;

	@Before
	public void settopp() {
		MockitoAnnotations.initMocks(this);
		transportCostManager = TransportCostManagerImpl.med() //
				.transportManager(transportManager) //
				.transportCostBasisManager(transportCostBasisManager) //
				.transportCostDAO(transportCostDAO) //
				.costTypeManager(costTypeManager) //
				.costUnitManager(costUnitManager) //
				.transportCostAdditionManager(transportCostAdditionManager)
				.build();
		CostType costType = new CostType();
		when(costTypeManager.findByName("Fraktkost")).thenReturn(costType);
		CostUnit costUnit = new CostUnit();
		when(costUnitManager.findByName("Intern")).thenReturn(costUnit);
	}

	@Test
	public void skalBeregneTransportkostnad() throws Exception {
		Periode periode = new Periode(YEAR, WEEK_50, WEEK_50);
		String productAreaGroupName = "Garasje";
		Transport transport = new Transport();
		transport.setSent(new Date());
		Supplier supplier = new Supplier();
		transport.setSupplier(supplier);
		Order order = new Order();
		order.setSent(new Date());
		order.setPostalCode("4841");
		ProductArea productArea = new ProductArea();
		ProductAreaGroup productAreaGroup = new ProductAreaGroup();
		productAreaGroup.setProductAreaGroupName(productAreaGroupName);
		productArea.setProductAreaGroup(productAreaGroup);
		order.setProductArea(productArea);
		transport.addOrder(order);
		List<Transport> transportList = Lists.newArrayList(transport);
		when(transportManager.findInPeriode(periode, productAreaGroupName))
				.thenReturn(transportList);
		TransportCost transportCost=new TransportCost();
		transportCost.setCost(BigDecimal.valueOf(1000));
		when(transportCostDAO.findByPostalCode("4841")).thenReturn(transportCost);

		List<TransportCostBasis> list = transportCostManager
				.generateTransportCostList(periode);
		assertNotNull(list);
		assertEquals(1, list.size());

		TransportCostBasis transportCostBasis = list.get(0);
		assertEquals(1, transportCostBasis.getOrderCosts().size());
		OrderCost orderCost = transportCostBasis.getOrderCosts().iterator()
				.next();
		assertNotNull(orderCost.getCostAmount());
		assertTrue(orderCost.getCostAmount().intValue() > 0);
	}
}
