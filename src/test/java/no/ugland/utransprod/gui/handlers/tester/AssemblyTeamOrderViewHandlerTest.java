package no.ugland.utransprod.gui.handlers.tester;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import no.ugland.utransprod.gui.DeviationOverviewViewFactory;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.handlers.DeviationViewHandlerFactory;
import no.ugland.utransprod.gui.handlers.OrderViewHandler;
import no.ugland.utransprod.gui.handlers.OrderViewHandlerFactory;
import no.ugland.utransprod.gui.handlers.SupplierOrderViewHandler;
import no.ugland.utransprod.model.Assembly;
import no.ugland.utransprod.model.UserType;
import no.ugland.utransprod.service.AssemblyManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.service.ProductAreaManager;
import no.ugland.utransprod.test.FastTests;
import no.ugland.utransprod.util.YearWeek;
import no.ugland.utransprod.util.report.AssemblyReportFactory;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * @author atle.brekka
 * 
 */
@Category(FastTests.class)
public class AssemblyTeamOrderViewHandlerTest {
    private SupplierOrderViewHandler viewHandler;

    @Mock
    private OrderManager orderManager;

    @Mock
    private AssemblyManager assemblyManager;

    @Mock
    private Login login;
    @Mock
    private DeviationOverviewViewFactory deviationOverviewViewFactory;
    @Mock
    private ManagerRepository managerRepository;
    @Mock
    private DeviationViewHandlerFactory deviationViewHandlerFactory;
    @Mock
    private AssemblyReportFactory assemblyReportFactory;
    @Mock
    private OrderViewHandlerFactory orderViewHandlerFactory;
    @Mock
    private ProductAreaManager productAreaManager;

    @Before
    public void setUp() throws Exception {
	MockitoAnnotations.initMocks(this);
	when(managerRepository.getProductAreaManager()).thenReturn(productAreaManager);
	when(managerRepository.getAssemblyManager()).thenReturn(assemblyManager);
	when(managerRepository.getOrderManager()).thenReturn(orderManager);
	final OrderViewHandler orderViewHandler = new OrderViewHandler(login, managerRepository, deviationOverviewViewFactory,
		deviationViewHandlerFactory, true);
	when(orderViewHandlerFactory.create(true)).thenReturn(orderViewHandler);
	UserType userType = new UserType();
	userType.setIsAdmin(1);
	when(login.getUserType()).thenReturn(userType);
	YearWeek yearWeek = new YearWeek();

	viewHandler = new SupplierOrderViewHandler(login, managerRepository, assemblyReportFactory, deviationViewHandlerFactory,
		orderViewHandlerFactory, null, yearWeek, null);

    }

    @Test
    public void testGetOrderListAtStart() {
	assertEquals(0, viewHandler.getObjectSelectionListSize());
    }

    @Test
    public void testGetAddRemoveString() {
	assertNotNull(viewHandler.getAddRemoveString());
	assertEquals("montering", viewHandler.getAddRemoveString());
    }

    @Test
    public void testGetNewObject() {
	assertNotNull(viewHandler.getNewObject());
	assertEquals(Assembly.class, viewHandler.getNewObject().getClass());
    }

    @Test
    public void testGetTableModel() {
	assertNotNull(viewHandler.getTableModel(null));

    }

}
