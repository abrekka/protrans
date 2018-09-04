package no.ugland.utransprod.gui.manuelt;

import static junit.framework.Assert.assertNotNull;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Mockito.when;

import java.awt.Component;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.UIManager;

import no.ugland.utransprod.gui.DeviationOverviewViewFactory;
import no.ugland.utransprod.gui.GarageMenu;
import no.ugland.utransprod.gui.GavlProductionWindow;
import no.ugland.utransprod.gui.LFEnum;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.MenuBarBuilderImpl;
import no.ugland.utransprod.gui.MenuBarBuilderInterface;
import no.ugland.utransprod.gui.ProTransMain;
import no.ugland.utransprod.gui.ProductionMenu;
import no.ugland.utransprod.gui.action.GavlProductionAction;
import no.ugland.utransprod.gui.action.OrderAction;
import no.ugland.utransprod.gui.handlers.DeviationViewHandlerFactory;
import no.ugland.utransprod.gui.handlers.OrderViewHandler;
import no.ugland.utransprod.gui.handlers.OrderViewHandlerFactory;
import no.ugland.utransprod.model.ApplicationUser;
import no.ugland.utransprod.model.UserType;
import no.ugland.utransprod.model.UserTypeAccess;
import no.ugland.utransprod.model.WindowAccess;
import no.ugland.utransprod.service.ApplicationUserManager;
import no.ugland.utransprod.service.DeviationStatusManager;
import no.ugland.utransprod.service.JobFunctionManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.service.ProductAreaManager;
import no.ugland.utransprod.test.ManuellTest;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.FrameFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@Category(ManuellTest.class)
public class MenubarOrderMenuTest {
    static {
	try {

	    UIManager.setLookAndFeel(LFEnum.LNF_LIQUID.getClassName());
	    JFrame.setDefaultLookAndFeelDecorated(true);
	    // LiquidLookAndFeel.setLiquidDecorations(true, "mac");

	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    private FrameFixture frameFixture;
    @Mock
    private Login login;
    @Mock
    private ManagerRepository managerRepository;

    @Mock
    private DeviationOverviewViewFactory deviationOverviewViewFactory;
    @Mock
    private DeviationViewHandlerFactory deviationViewHandlerFactory;
    @Mock
    private OrderViewHandlerFactory orderViewHandlerFactory;
    @Mock
    private ProductAreaManager productAreaManager;
    @Mock
    private JobFunctionManager jobFunctionManager;
    @Mock
    private DeviationStatusManager deviationStatusManager;
    @Mock
    private ApplicationUserManager applicationUserManager;
    @Mock
    private OrderManager orderManager;

    @Before
    public void setUp() throws Exception {
	MockitoAnnotations.initMocks(this);

	when(managerRepository.getProductAreaManager()).thenReturn(productAreaManager);
	when(managerRepository.getJobFunctionManager()).thenReturn(jobFunctionManager);
	when(managerRepository.getDeviationStatusManager()).thenReturn(deviationStatusManager);
	when(managerRepository.getApplicationUserManager()).thenReturn(applicationUserManager);
	when(managerRepository.getOrderManager()).thenReturn(orderManager);
	ApplicationUser applicationUser = new ApplicationUser();
	when(login.getApplicationUser()).thenReturn(applicationUser);
	UserType userType = new UserType();
	UserTypeAccess userTypeAccess = new UserTypeAccess();
	userTypeAccess.setWindowAccess(new WindowAccess(null, "Attributter", null));
	userType.setIsAdmin(1);
	Set<UserTypeAccess> userTypeAccesses = new HashSet<UserTypeAccess>();
	userTypeAccesses.add(userTypeAccess);
	userType.setUserTypeAccesses(userTypeAccesses);
	when(login.getUserType()).thenReturn(userType);

	OrderViewHandler orderViewHandler = new OrderViewHandler(login, managerRepository, deviationOverviewViewFactory, deviationViewHandlerFactory,
		true, null);
	when(orderViewHandlerFactory.create(anyBoolean())).thenReturn(orderViewHandler);

	final MenuBarBuilderInterface menuBarBuilder = new MenuBarBuilderImpl(login);
	final GarageMenu garageMenu = new GarageMenu(login);

	OrderAction orderAction = new OrderAction(menuBarBuilder, orderViewHandlerFactory);
	garageMenu.setOrderAction(orderAction);
	menuBarBuilder.setGarageMenu(garageMenu);

	ProductionMenu productionMenu = new ProductionMenu(login);
	GavlProductionWindow gavlProductionWindow = new GavlProductionWindow(login, managerRepository, deviationViewHandlerFactory, null,null);
	GavlProductionAction gavlProductionAction = new GavlProductionAction(menuBarBuilder, gavlProductionWindow);
	productionMenu.setGavlProductionAction(gavlProductionAction);
	menuBarBuilder.setProductionMenu(productionMenu);

	ProTransMain proTransMain = GuiActionRunner.execute(new GuiQuery<ProTransMain>() {
	    protected ProTransMain executeInEDT() {
		ProTransMain proTransMain = new ProTransMain(menuBarBuilder, login);
		proTransMain.buildFrame();
		return proTransMain;
	    }
	});

	frameFixture = new FrameFixture(proTransMain);

    }

    @After
    public void tearDown() throws Exception {
	frameFixture.cleanUp();

    }

    @Test
    public void testOrderMenu() {
	frameFixture.show();
	frameFixture.menuItemWithPath(new String[] { "Ordre", "Ordre..." }).click();

	Component comp = frameFixture.robot.finder().findByName("OverviewOrder");
	assertNotNull(comp);
    }

}
