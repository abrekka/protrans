package no.ugland.utransprod.gui.model;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.fest.assertions.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import no.ugland.utransprod.model.FrontProductionV;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.Ordln;
import no.ugland.utransprod.model.OrdlnPK;
import no.ugland.utransprod.model.Produceable;
import no.ugland.utransprod.service.ColliManager;
import no.ugland.utransprod.service.FakturagrunnlagVManager;
import no.ugland.utransprod.service.IApplyListManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.OrdchgrHeadVManager;
import no.ugland.utransprod.service.OrderLineManager;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.service.OrdlnManager;
import no.ugland.utransprod.service.VismaFileCreator;
import no.ugland.utransprod.service.impl.VismaFileCreatorImpl;

public class FrontProductionApplyListTest {
	private FrontProductionApplyList frontProductionApplyList;

	@Mock
	OrdchgrHeadVManager ordchgrManager;

	@Mock
	private FakturagrunnlagVManager fakturagrunnlagVManager;

	@Mock
	private ManagerRepository managerRepository;
	@Mock
	private OrdlnManager ordlnManager;

	@Mock
	private VismaFileCreator vismaFileCreator;

	@Mock
	private OrderManager orderManager;

	@Mock
	private OrderLineManager orderLineManager;

	@Mock
	private ColliManager colliManager;

	@Mock
	private IApplyListManager<Produceable> applyListManager;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		Mockito.when(managerRepository.getOrdlnManager()).thenReturn(ordlnManager);
		Mockito.when(managerRepository.getOrderManager()).thenReturn(orderManager);
		Mockito.when(managerRepository.getOrderLineManager()).thenReturn(orderLineManager);
		Mockito.when(managerRepository.getColliManager()).thenReturn(colliManager);

		frontProductionApplyList = new FrontProductionApplyList(null, applyListManager, null, null, null,
				managerRepository, vismaFileCreator);

	}

	@Test
	public void skalLageLinjerForAlleProduksjonsordre() throws IOException {
		OrderLine orderLine = new OrderLine();
		orderLine.setOrdNo(111);
		orderLine.setDoneBy("produsert av");
		orderLine.setRealProductionHours(BigDecimal.valueOf(10.5));
		Order order = new Order();
		orderLine.setOrder(order);
		order.setProductionWeek(6);
		Mockito.when(orderLineManager.findByOrderLineId(Mockito.anyInt())).thenReturn(orderLine);
		Mockito.when(orderManager.findByOrderNr(Mockito.anyString())).thenReturn(order);

		List<Ordln> ordrelinjer = new ArrayList<Ordln>();
		Ordln ordln = new Ordln();
		ordln.setNoinvoab(BigDecimal.ONE);
		ordln.setOrdlnPK(new OrdlnPK(1, 1));
		ordln.setPurcno(123);
		ordrelinjer.add(ordln);

		ordln = new Ordln();
		ordln.setNoinvoab(BigDecimal.ONE);
		ordln.setOrdlnPK(new OrdlnPK(1, 2));
		ordln.setPurcno(124);
		ordrelinjer.add(ordln);
		Mockito.when(ordlnManager.findOrdLnByOrdNo(Mockito.anyInt())).thenReturn(ordrelinjer);

		Produceable producable = new FrontProductionV();
		frontProductionApplyList.handleApply(producable, true, null, "Front");

		ArgumentCaptor<List> argumentCaptor = ArgumentCaptor.forClass(List.class);
		Mockito.verify(vismaFileCreator, Mockito.times(2)).writeFile(Mockito.anyString(), Mockito.anyString(),
				argumentCaptor.capture(), Mockito.anyInt());
		List<List> linjer = argumentCaptor.getAllValues();
		Assertions.assertThat(linjer.size()).isEqualTo(2);
		Assertions.assertThat(linjer.get(0).get(0)).isEqualTo(
				"H;;123;;;;;produsert av;;;;;;;;;;;;;;;;;;;;;;;Front;;;;;;;;;;10.5;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;4");
		Assertions.assertThat(linjer.get(1).get(0)).isEqualTo(
				"H;;124;;;;;produsert av;;;;;;;;;;;;;;;;;;;;;;;Front;;;;;;;;;;10.5;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;4");
	}

	
}
