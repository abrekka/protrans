package no.ugland.utransprod.gui.group1;

import static org.fest.swing.data.TableCell.row;
import static org.mockito.Mockito.when;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

import no.ugland.utransprod.gui.AssemblyPlannerView;
import no.ugland.utransprod.gui.DeviationOverviewView;
import no.ugland.utransprod.gui.DeviationOverviewViewFactory;
import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.LFEnum;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.handlers.AssemblyPlannerViewHandler;
import no.ugland.utransprod.gui.handlers.DeviationViewHandler;
import no.ugland.utransprod.gui.handlers.DeviationViewHandlerFactory;
import no.ugland.utransprod.gui.handlers.OrderViewHandler;
import no.ugland.utransprod.gui.handlers.OrderViewHandlerFactory;
import no.ugland.utransprod.gui.handlers.PreventiveActionViewHandler;
import no.ugland.utransprod.gui.handlers.SupplierOrderViewHandler;
import no.ugland.utransprod.gui.handlers.SupplierOrderViewHandlerFactory;
import no.ugland.utransprod.model.ApplicationUser;
import no.ugland.utransprod.model.Deviation;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.Ordln;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.model.Supplier;
import no.ugland.utransprod.model.UserType;
import no.ugland.utransprod.model.UserTypeAccess;
import no.ugland.utransprod.model.WindowAccess;
import no.ugland.utransprod.service.AssemblyManager;
import no.ugland.utransprod.service.AssemblyOverdueVManager;
import no.ugland.utransprod.service.CraningCostManager;
import no.ugland.utransprod.service.JobFunctionManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.service.ProductAreaManager;
import no.ugland.utransprod.service.SupplierManager;
import no.ugland.utransprod.test.FastTests;
import no.ugland.utransprod.test.GUITests;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.YearWeek;
import no.ugland.utransprod.util.report.AssemblyReport;
import no.ugland.utransprod.util.report.AssemblyReportFactory;
import no.ugland.utransprod.util.report.AssemblyReportImpl;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.DialogFixture;
import org.fest.swing.fixture.JTableFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.birosoft.liquid.LiquidLookAndFeel;

/**
 * @author atle.brekka
 * 
 */
@Category(GUITests.class)
public class AssemblyPlannerViewTest {
	static {
		try {

			UIManager.setLookAndFeel(LFEnum.LNF_LIQUID.getClassName());
			JFrame.setDefaultLookAndFeelDecorated(true);
			LiquidLookAndFeel.setLiquidDecorations(true, "mac");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private DialogFixture dialogFixture;

	@Mock
	private Login login;
	@Mock
	private ManagerRepository managerRepository;

	// @Mock
	// private DeviationOverviewViewFactory deviationOverviewViewFactory;
	// @Mock
	// private DeviationViewHandlerFactory deviationViewHandlerFactory;
	// @Mock
	// private AssemblyReportFactory assemblyReportFactory;
	// @Mock
	// private OrderViewHandlerFactory orderViewHandlerFactory;
	// @Mock
	// private SupplierOrderViewHandlerFactory supplierOrderViewHandlerFactory;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		ProductAreaManager productAreaManager = (ProductAreaManager) ModelUtil
				.getBean(ProductAreaManager.MANAGER_NAME);
		AssemblyManager assemblyManager = (AssemblyManager) ModelUtil
				.getBean(AssemblyManager.MANAGER_NAME);
		SupplierManager supplierManager = (SupplierManager) ModelUtil
				.getBean(SupplierManager.MANAGER_NAME);
		OrderManager orderManager = (OrderManager) ModelUtil
				.getBean(OrderManager.MANAGER_NAME);
		when(managerRepository.getProductAreaManager()).thenReturn(
				productAreaManager);
		when(managerRepository.getAssemblyManager())
				.thenReturn(assemblyManager);
		when(managerRepository.getSupplierManager())
				.thenReturn(supplierManager);
		when(managerRepository.getOrderManager()).thenReturn(orderManager);
		AssemblyOverdueVManager assemblyOverdueVmnager = (AssemblyOverdueVManager) ModelUtil
				.getBean(AssemblyOverdueVManager.MANAGER_NAME);
		when(managerRepository.getAssemblyOverdueVManager()).thenReturn(
				assemblyOverdueVmnager);
		JobFunctionManager jobFunctionManager=(JobFunctionManager)ModelUtil.getBean(JobFunctionManager.MANAGER_NAME);
		when(managerRepository.getJobFunctionManager()).thenReturn(jobFunctionManager);
		UserType userType = new UserType();
		userType.setIsAdmin(1);
		when(login.getUserType()).thenReturn(userType);
		ApplicationUser applicationUser = new ApplicationUser();
		ProductArea productArea = productAreaManager
				.findByName("Garasje villa");
		applicationUser.setProductArea(productArea);
		when(login.getApplicationUser()).thenReturn(applicationUser);
		final Supplier supplier = new Supplier();
		supplier.setSupplierId(47);
		supplier.setSupplierName("Sørensen Garagebygg ANS");

		final YearWeek yearWeek = new YearWeek(2009, 34);
		final CraningCostManager craningCostManager = (CraningCostManager) ModelUtil
				.getBean(CraningCostManager.MANAGER_NAME);
		final AssemblyReportFactory assemblyReportFactory = new AssemblyReportFactory() {

			public AssemblyReport create(Order order,
					List<Ordln> vismaOrderLines) {

				return new AssemblyReportImpl(craningCostManager, order,
						vismaOrderLines);
			}
		};
		final PreventiveActionViewHandler preventiveActionViewHandler = new PreventiveActionViewHandler(
				login, managerRepository);
		final DeviationViewHandlerFactory deviationViewHandlerFactory = new DeviationViewHandlerFactory() {

			public DeviationViewHandler create(Order aOrder, boolean doSeAll,
					boolean forOrderInfo, boolean isForRegisterNew,
					Deviation notDisplayDeviation,
					boolean isDeviationTableEditable) {

				return new DeviationViewHandler(login, managerRepository,
						preventiveActionViewHandler, aOrder, doSeAll,
						forOrderInfo, isForRegisterNew, notDisplayDeviation,
						isDeviationTableEditable);
			}
		};
		final DeviationOverviewViewFactory deviationOverviewViewFactory = new DeviationOverviewViewFactory() {

			public DeviationOverviewView create(
					DeviationViewHandler deviationViewHandler,
					boolean useSearchButton, Order aOrder, boolean doSeeAll,
					boolean forOrderInfo, boolean isForRegisterNew,
					Deviation notDisplayDeviation,
					boolean isDeviationTableEditable) {
				return new DeviationOverviewView(preventiveActionViewHandler,
						deviationViewHandler, useSearchButton, aOrder,
						doSeeAll, forOrderInfo, isForRegisterNew,
						notDisplayDeviation, isDeviationTableEditable);
			}
		};
		final OrderViewHandlerFactory orderViewHandlerFactory = new OrderViewHandlerFactory() {

			public OrderViewHandler create(boolean notInitData) {

				return new OrderViewHandler(login, managerRepository,
						deviationOverviewViewFactory,
						deviationViewHandlerFactory, notInitData);
			}
		};
		final SupplierOrderViewHandler supplierOrderViewHandler = new SupplierOrderViewHandler(
				login, managerRepository, assemblyReportFactory,
				deviationViewHandlerFactory, orderViewHandlerFactory, supplier,
				yearWeek);

		Set<UserTypeAccess> userTypeAccesses = new HashSet<UserTypeAccess>();
		UserTypeAccess userTypeAccess = new UserTypeAccess();
		userTypeAccess.setWindowAccess(new WindowAccess(null, "Attributter",
				null));
		userTypeAccesses.add(userTypeAccess);
		userType.setUserTypeAccesses(userTypeAccesses);

		final List<Supplier> supplierList = new ArrayList<Supplier>();
		supplierList.add(supplier);

		final OrderViewHandler orderViewHandler = new OrderViewHandler(login,
				managerRepository, deviationOverviewViewFactory,
				deviationViewHandlerFactory, true);

		SupplierOrderViewHandlerFactory supplierOrderViewHandlerFactory = new SupplierOrderViewHandlerFactory() {

			public SupplierOrderViewHandler create(Supplier aSupplier,
					YearWeek aYearWeek) {
				return new SupplierOrderViewHandler(login, managerRepository,
						assemblyReportFactory, deviationViewHandlerFactory,
						orderViewHandlerFactory, supplier, yearWeek);
			}
		};
		final AssemblyPlannerView view = new AssemblyPlannerView(
				new AssemblyPlannerViewHandler(orderViewHandler, login,
						supplierOrderViewHandlerFactory, managerRepository));

		JDialog dialog = GuiActionRunner.execute(new GuiQuery<JDialog>() {
			protected JDialog executeInEDT() {
				JDialog dialog = new JDialog();
				WindowInterface window = new JDialogAdapter(dialog);
				dialog.add(view.buildPanel(window));
				dialog.pack();
				return dialog;
			}
		});
		dialogFixture = new DialogFixture(dialog);
		dialogFixture.show();

	}

	@After
	public void tearDown() throws Exception {
		dialogFixture.cleanUp();
	}

	@Test
	public void testOpenWindow() throws Exception {

		dialogFixture.requireVisible();
	}

	@Test
	public void testSetAssemblyForDeviation() {
		JTableFixture tableFixture = dialogFixture.table("TableDeviation");
		tableFixture.cell(row(0).column(0)).select();
	}

	@Test
	public void testAddComment() {
		dialogFixture.show(new Dimension(1010, 600));
	}

}
