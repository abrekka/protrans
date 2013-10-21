package no.ugland.utransprod.util.report;

import static org.mockito.Mockito.when;
import no.ugland.utransprod.model.Colli;
import no.ugland.utransprod.model.ConstructionType;
import no.ugland.utransprod.model.Customer;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.service.ColliManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.service.OrdlnManager;
import no.ugland.utransprod.test.FastTests;
import no.ugland.utransprod.util.ModelUtil;

import org.fest.swing.core.BasicRobot;
import org.fest.swing.finder.WindowFinder;
import org.fest.swing.fixture.DialogFixture;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
@Category(FastTests.class)
public class TransportLetterTest {

	@Mock
	private ManagerRepository managerRepository;

	@Before
	public void settopp(){
		MockitoAnnotations.initMocks(this);
	}
	@Test
	public void testGenerateReportForGarage() {
OrderManager orderManager=(OrderManager)ModelUtil.getBean(OrderManager.MANAGER_NAME);
		//		final ManagerRepository managerRepository = context
//				.mock(ManagerRepository.class);
		when(managerRepository.getOrderManager()).thenReturn(orderManager);
		OrdlnManager ordlnManager=(OrdlnManager)ModelUtil.getBean(OrdlnManager.MANAGER_NAME);
		when(managerRepository.getOrdlnManager()).thenReturn(ordlnManager);
		ColliManager colliManager=(ColliManager)ModelUtil.getBean(ColliManager.MANAGER_NAME);
		when(managerRepository.getColliManager()).thenReturn(colliManager);
		TransportLetter transportLetter = new GarageTransportLetter(
				managerRepository);
		final Order transportable = new Order();
		ConstructionType constructionType = new ConstructionType();
		transportable.setConstructionType(constructionType);
		OrderLine orderLine = new OrderLine();
		transportable.addOrderLine(orderLine);
		final Colli colli = new Colli();
		colli.setColliName("test");
		colli.addOrderLine(orderLine);
		transportable.addColli(colli);
		Customer customer = new Customer();
		transportable.setCustomer(customer);
		ProductArea productArea = new ProductArea();
		ProductAreaGroup productAreaGroup = new ProductAreaGroup();
		productArea.setProductAreaGroup(productAreaGroup);
		transportable.setProductArea(productArea);

//		context.checking(new Expectations() {
//			{
//				allowing(managerRepository).getOrderManager();
//				allowing(managerRepository).getOrdlnManager();
//				allowing(managerRepository).getColliManager();
//				// allowing(colliManager).lazyLoad(colli, new LazyLoadEnum[][] {
//				// { LazyLoadEnum.ORDER_LINES,LazyLoadEnum.NONE } });
//				// allowing(orderManager).lazyLoadOrder(transportable, new
//				// LazyLoadOrderEnum[] { LazyLoadOrderEnum.COLLIES });
//			}
//		});

		transportLetter.generateTransportLetter(transportable, null);
		DialogFixture dialog = WindowFinder.findDialog("Fraktbrev")
				.withTimeout(10000).using(
						BasicRobot.robotWithCurrentAwtHierarchy());
		dialog.button("ButtonCancel").click();
	}

}
