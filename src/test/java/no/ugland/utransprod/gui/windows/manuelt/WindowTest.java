package no.ugland.utransprod.gui.windows.manuelt;

import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import junit.extensions.abbot.ComponentTestFixture;
import no.ugland.utransprod.gui.DeviationOverviewViewFactory;
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
import no.ugland.utransprod.service.ApplicationUserManager;
import no.ugland.utransprod.service.ArticleTypeManager;
import no.ugland.utransprod.service.DeviationManager;
import no.ugland.utransprod.service.DeviationStatusManager;
import no.ugland.utransprod.service.JobFunctionManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.service.PreventiveActionManager;
import no.ugland.utransprod.service.ProductAreaGroupManager;
import no.ugland.utransprod.service.ProductAreaManager;
import no.ugland.utransprod.service.TakstolPackageVManager;
import no.ugland.utransprod.service.TakstolProductionVManager;
import no.ugland.utransprod.util.ModelUtil;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class WindowTest extends ComponentTestFixture {
	@Mock
	protected Login login;
	@Mock
	protected ManagerRepository managerRepository;
	@Mock
	protected DeviationViewHandlerFactory deviationViewHandlerFactory;
	@Mock
	protected DeviationOverviewViewFactory deviationOverviewViewFactory;

	@Override
	protected void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		final JobFunctionManager jobFunctionManager = (JobFunctionManager) ModelUtil
				.getBean(JobFunctionManager.MANAGER_NAME);
		when(managerRepository.getJobFunctionManager()).thenReturn(
				jobFunctionManager);
		TakstolPackageVManager takstolPackageVManager = (TakstolPackageVManager) ModelUtil
				.getBean(TakstolPackageVManager.MANAGER_NAME);
		when(managerRepository.getTakstolPackageVManager()).thenReturn(
				takstolPackageVManager);
		TakstolProductionVManager takstolProsuctionVManager = (TakstolProductionVManager) ModelUtil
				.getBean(TakstolProductionVManager.MANAGER_NAME);
		when(managerRepository.getTakstolProductionVManager()).thenReturn(
				takstolProsuctionVManager);
		final ApplicationUser applicationUser = new ApplicationUser();
		when(login.getApplicationUser()).thenReturn(applicationUser);
		final UserType userType = new UserType();
		Set<UserTypeAccess> userTypeAccesses = new HashSet<UserTypeAccess>();
		UserTypeAccess userTypeAccess = new UserTypeAccess();
		userTypeAccess.setWriteAccess(1);
		WindowAccess windowAccess = new WindowAccess();
		windowAccess.setWindowName("Produksjonsenhet");
		userTypeAccess.setWindowAccess(windowAccess);
		userTypeAccesses.add(userTypeAccess);
		userType.setUserTypeAccesses(userTypeAccesses);
		userType.setIsAdmin(1);
		when(login.getUserType()).thenReturn(userType);

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
		ArticleTypeManager articleTypeManager = (ArticleTypeManager) ModelUtil
				.getBean(ArticleTypeManager.MANAGER_NAME);
		when(managerRepository.getArticleTypeManager()).thenReturn(
				articleTypeManager);
		ProductAreaGroupManager productAreaGroupManager = (ProductAreaGroupManager) ModelUtil
				.getBean(ProductAreaGroupManager.MANAGER_NAME);
		when(managerRepository.getProductAreaGroupManager()).thenReturn(
				productAreaGroupManager);

		OrderManager orderManager = (OrderManager) ModelUtil
				.getBean(OrderManager.MANAGER_NAME);
		when(managerRepository.getOrderManager()).thenReturn(orderManager);

		ProductArea productArea = new ProductArea();

		ProductAreaGroup productAreaGroup = productAreaGroupManager
				.findByName("Garasje");
		productArea.setProductAreaGroup(productAreaGroup);
		applicationUser.setProductArea(productArea);

		final DeviationManager deviationManager = (DeviationManager) ModelUtil
				.getBean(DeviationManager.MANAGER_NAME);
		final PreventiveActionManager preventiveActionManager = (PreventiveActionManager) ModelUtil
				.getBean(PreventiveActionManager.MANAGER_NAME);

		final PreventiveActionViewHandler preventiveActionViewHandler = new PreventiveActionViewHandler(
				login, managerRepository);
		final DeviationViewHandler deviationViewHandler = new DeviationViewHandler(
				login, managerRepository, preventiveActionViewHandler, null,
				true, false, true, null, true);

	}
}
