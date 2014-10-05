package no.ugland.utransprod.gui.handlers.tester;

import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import no.ugland.utransprod.gui.DeviationOverviewViewFactory;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.handlers.AssemblyPlannerViewHandler;
import no.ugland.utransprod.gui.handlers.DeviationViewHandlerFactory;
import no.ugland.utransprod.gui.handlers.OrderViewHandler;
import no.ugland.utransprod.gui.handlers.SupplierOrderViewHandlerFactory;
import no.ugland.utransprod.service.AssemblyOverdueVManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.ProductAreaManager;
import no.ugland.utransprod.service.SupplierManager;
import no.ugland.utransprod.test.FastTests;
import no.ugland.utransprod.util.YearWeek;

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
public class AssemblyPlannerViewHandlerTest {
    private AssemblyPlannerViewHandler viewHandler;
    @Mock
    private Login login;
    @Mock
    private DeviationOverviewViewFactory deviationOverviewViewFactory;
    @Mock
    private ManagerRepository managerRepository;
    @Mock
    private DeviationViewHandlerFactory deviationViewHandlerFactory;
    @Mock
    private SupplierOrderViewHandlerFactory supplierOrderViewHandlerFactory;
    @Mock
    private ProductAreaManager productAreaManager;
    @Mock
    private SupplierManager supplierManager;
    @Mock
    private AssemblyOverdueVManager assemblyOverdueVManager;

    @Before
    public void setUp() throws Exception {
	MockitoAnnotations.initMocks(this);
	when(managerRepository.getProductAreaManager()).thenReturn(productAreaManager);
	when(managerRepository.getSupplierManager()).thenReturn(supplierManager);
	when(managerRepository.getAssemblyOverdueVManager()).thenReturn(assemblyOverdueVManager);

	OrderViewHandler orderViewHandler = new OrderViewHandler(login, managerRepository, deviationOverviewViewFactory, deviationViewHandlerFactory,
		true, null);
	viewHandler = new AssemblyPlannerViewHandler(orderViewHandler, login, supplierOrderViewHandlerFactory, managerRepository);
    }

    @Test
    public void testGetAssemblyTeams() {
	assertNotNull(viewHandler.getSuppliers(new YearWeek(), null));
    }

    @Test
    public void testGetButtonSearchOrder() {
	assertNotNull(viewHandler.getButtonSearchOrder(null, null));
    }

    @Test
    public void testGetLabelSearchResult() {
	assertNotNull(viewHandler.getLabelSearchResult());

    }

    @Test
    public void testGetLabelWarning() throws Exception {
	assertNotNull(viewHandler.getLabelWarning());
    }

    @Test
    public void testGetCancelButton() throws Exception {
	assertNotNull(viewHandler.getCancelButton(null));
    }

}
