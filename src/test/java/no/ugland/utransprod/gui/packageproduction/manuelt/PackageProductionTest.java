package no.ugland.utransprod.gui.packageproduction.manuelt;

import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.handlers.DeviationViewHandler;
import no.ugland.utransprod.gui.handlers.DeviationViewHandlerFactory;
import no.ugland.utransprod.gui.handlers.PreventiveActionViewHandler;
import no.ugland.utransprod.model.ApplicationUser;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.model.UserType;
import no.ugland.utransprod.model.UserTypeAccess;
import no.ugland.utransprod.model.WindowAccess;
import no.ugland.utransprod.service.ApplicationParamManager;
import no.ugland.utransprod.service.ApplicationUserManager;
import no.ugland.utransprod.service.ArticleTypeManager;
import no.ugland.utransprod.service.ColliManager;
import no.ugland.utransprod.service.DeviationStatusManager;
import no.ugland.utransprod.service.FrontProductionVManager;
import no.ugland.utransprod.service.JobFunctionManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.OrderLineManager;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.service.ProductAreaGroupManager;
import no.ugland.utransprod.service.ProductAreaManager;
import no.ugland.utransprod.service.ProductionUnitManager;
import no.ugland.utransprod.service.TakstolPackageVManager;
import no.ugland.utransprod.service.TakstolProductionVManager;
import no.ugland.utransprod.util.ModelUtil;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class PackageProductionTest {
	@Mock
	protected ManagerRepository managerRepository;
	@Mock
	protected DeviationViewHandlerFactory deviationViewHandlerFactory;
	@Mock
	protected Login login;
	@Mock
	protected ApplicationParamManager applicationParamManager;

	@Before
	protected void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		// robot = BasicRobot.robotWithNewAwtHierarchy();
		JobFunctionManager jobFunctionManager = (JobFunctionManager) ModelUtil
				.getBean(JobFunctionManager.MANAGER_NAME);
		when(managerRepository.getJobFunctionManager()).thenReturn(
				jobFunctionManager);
		ApplicationUser applicationUser = new ApplicationUser();
		ProductArea productArea = new ProductArea();
		ProductAreaGroup productAreaGroup = new ProductAreaGroup();
		productArea.setProductAreaGroup(productAreaGroup);
		applicationUser.setProductArea(productArea);
		applicationUser.setGroupUser("Nei");
		when(login.getApplicationUser()).thenReturn(applicationUser);
		ProductAreaManager productAreaManager = (ProductAreaManager) ModelUtil
				.getBean(ProductAreaManager.MANAGER_NAME);
		when(managerRepository.getProductAreaManager()).thenReturn(
				productAreaManager);
		DeviationStatusManager deviationStatusManager = (DeviationStatusManager) ModelUtil
				.getBean(DeviationStatusManager.MANAGER_NAME);
		when(managerRepository.getDeviationStatusManager()).thenReturn(
				deviationStatusManager);
		ApplicationUserManager applicationUserManager = (ApplicationUserManager) ModelUtil
				.getBean(ApplicationUserManager.MANAGER_NAME);
		when(managerRepository.getApplicationUserManager()).thenReturn(
				applicationUserManager);
		ProductAreaGroupManager productAreaGroupManager = (ProductAreaGroupManager) ModelUtil
				.getBean(ProductAreaGroupManager.MANAGER_NAME);
		when(managerRepository.getProductAreaGroupManager()).thenReturn(
				productAreaGroupManager);
		OrderManager orderManager = (OrderManager) ModelUtil
				.getBean(OrderManager.MANAGER_NAME);
		when(managerRepository.getOrderManager()).thenReturn(orderManager);
		UserType userType = new UserType();
		userType.setIsAdmin(1);
		when(login.getUserType()).thenReturn(userType);
		OrderLineManager orderLineManager = (OrderLineManager) ModelUtil
				.getBean(OrderLineManager.MANAGER_NAME);
		when(managerRepository.getOrderLineManager()).thenReturn(
				orderLineManager);
		ArticleTypeManager articleTypeManager = (ArticleTypeManager) ModelUtil
				.getBean(ArticleTypeManager.MANAGER_NAME);
		when(managerRepository.getArticleTypeManager()).thenReturn(
				articleTypeManager);
		FrontProductionVManager frontProductionVManager = (FrontProductionVManager) ModelUtil
				.getBean(FrontProductionVManager.MANAGER_NAME);
		when(managerRepository.getFrontProductionVManager()).thenReturn(
				frontProductionVManager);
		ProductionUnitManager productionUnitManager = (ProductionUnitManager) ModelUtil
				.getBean(ProductionUnitManager.MANAGER_NAME);
		when(managerRepository.getProductionUnitManager()).thenReturn(
				productionUnitManager);
		ColliManager colliManager = (ColliManager) ModelUtil
				.getBean(ColliManager.MANAGER_NAME);
		when(managerRepository.getColliManager()).thenReturn(colliManager);
		TakstolProductionVManager takstolProductionVManager = (TakstolProductionVManager) ModelUtil
				.getBean(TakstolProductionVManager.MANAGER_NAME);
		when(managerRepository.getTakstolProductionVManager()).thenReturn(
				takstolProductionVManager);
		TakstolPackageVManager takstolPackageVManager=(TakstolPackageVManager)ModelUtil.getBean(TakstolPackageVManager.MANAGER_NAME);
		when(managerRepository.getTakstolPackageVManager()).thenReturn(
				takstolPackageVManager);
		// vismaFileCreator =context.mock(VismaFileCreator.class);
		// managerRepository = context.mock(ManagerRepository.class);
		// deviationViewHandlerFactory = context
		// .mock(DeviationViewHandlerFactory.class);
		// login = context.mock(Login.class);
		// final PreventiveActionManager preventiveActionManager =
		// (PreventiveActionManager) ModelUtil
		// .getBean(PreventiveActionManager.MANAGER_NAME);

		Set<UserTypeAccess> userTypeAccesses = new HashSet<UserTypeAccess>();
		UserTypeAccess userTypeAccess = new UserTypeAccess();
		userTypeAccess.setWriteAccess(1);
		WindowAccess windowAccess = new WindowAccess();
		windowAccess.setWindowName("Produksjonsenhet");
		userTypeAccess.setWindowAccess(windowAccess);
		userTypeAccesses.add(userTypeAccess);
		userType.setUserTypeAccesses(userTypeAccesses);

		// final JobFunctionManager jobFunctionManager = (JobFunctionManager)
		// ModelUtil
		// .getBean(JobFunctionManager.MANAGER_NAME);

		// final DeviationManager deviationManager = (DeviationManager)
		// ModelUtil
		// .getBean(DeviationManager.MANAGER_NAME);
		// final AccidentManager accidentManager = (AccidentManager) ModelUtil
		// .getBean(AccidentManager.MANAGER_NAME);
		// context.checking(new Expectations() {
		// {
		// allowing(managerRepository).getPreventiveActionManager();
		// will(returnValue(preventiveActionManager));
		// allowing(login).getUserType();
		// will(returnValue(userType));
		// allowing(managerRepository).getJobFunctionManager();
		// will(returnValue(jobFunctionManager));
		// allowing(login).getApplicationUser();
		// will(returnValue(applicationUser));
		// allowing(managerRepository).getDeviationManager();
		// will(returnValue(deviationManager));
		// allowing(managerRepository).getAccidentManager();
		// will(returnValue(accidentManager));
		//
		// }
		// });
		final PreventiveActionViewHandler preventiveActionViewHandler = new PreventiveActionViewHandler(
				login, managerRepository);
		// final DeviationOverviewViewFactory deviationOverviewViewFactory =
		// context
		// .mock(DeviationOverviewViewFactory.class);
		final DeviationViewHandler deviationViewHandler = new DeviationViewHandler(
				login, managerRepository, preventiveActionViewHandler, null,
				true, false, true, null, true);
		// final OrderManager orderManager = (OrderManager) ModelUtil
		// .getBean(OrderManager.MANAGER_NAME);
		// final OrderLineManager orderLineManager = (OrderLineManager)
		// ModelUtil
		// .getBean(OrderLineManager.MANAGER_NAME);
		// context.checking(new Expectations() {
		// {
		// allowing(deviationViewHandlerFactory).create(null, true, false,
		// true, null, true);
		// will(returnValue(deviationViewHandler));
		// allowing(managerRepository).getOrderManager();
		// will(returnValue(orderManager));
		// allowing(managerRepository).getOrderLineManager();
		// will(returnValue(orderLineManager));
		//
		// }
		// });

	}
}
