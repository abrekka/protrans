package no.ugland.utransprod.gui.manuelt;

import static org.fest.swing.data.TableCell.row;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.LFEnum;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.PacklistView;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler;
import no.ugland.utransprod.gui.handlers.DeviationViewHandlerFactory;
import no.ugland.utransprod.gui.handlers.OrderViewHandlerFactory;
import no.ugland.utransprod.gui.handlers.PacklistViewHandler;
import no.ugland.utransprod.gui.handlers.TableEnum;
import no.ugland.utransprod.gui.model.PacklistApplyList;
import no.ugland.utransprod.model.ApplicationUser;
import no.ugland.utransprod.model.PacklistV;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.model.UserType;
import no.ugland.utransprod.model.UserTypeAccess;
import no.ugland.utransprod.model.WindowAccess;
import no.ugland.utransprod.service.ApplicationUserManager;
import no.ugland.utransprod.service.ArticleTypeManager;
import no.ugland.utransprod.service.BudgetManager;
import no.ugland.utransprod.service.DeviationStatusManager;
import no.ugland.utransprod.service.JobFunctionManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.service.PacklistVManager;
import no.ugland.utransprod.service.ProductAreaGroupManager;
import no.ugland.utransprod.service.ProductAreaManager;
import no.ugland.utransprod.test.ManuellTest;
import no.ugland.utransprod.util.ModelUtil;

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

/**
 * @author atle.brekka
 * 
 */
@Category(ManuellTest.class)
public class PacklistViewTest {
    static {
	try {

	    UIManager.setLookAndFeel(LFEnum.LNF_LIQUID.getClassName());
	    JFrame.setDefaultLookAndFeelDecorated(true);
	    // LiquidLookAndFeel.setLiquidDecorations(true, "mac");

	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    private DialogFixture dialogFixture;
    @Mock
    private Login login;
    @Mock
    private ManagerRepository managerRepository;
    @Mock
    private DeviationViewHandlerFactory deviationViewHandlerFactory;
    @Mock
    private OrderViewHandlerFactory orderViewHandlerFactory;
    @Mock
    private JobFunctionManager jobFunctionManager;
    @Mock
    private ProductAreaManager productAreaManager;
    @Mock
    private DeviationStatusManager deviationStatusManager;
    @Mock
    private ApplicationUserManager applicationUserManager;
    @Mock
    private ProductAreaGroupManager productAreaGroupManager;
    @Mock
    private BudgetManager budgetManager;
    @Mock
    private PacklistVManager packlistVManager;

    @Before
    public void setUp() throws Exception {
	MockitoAnnotations.initMocks(this);

	when(managerRepository.getJobFunctionManager()).thenReturn(jobFunctionManager);
	when(managerRepository.getProductAreaManager()).thenReturn(productAreaManager);
	when(managerRepository.getDeviationStatusManager()).thenReturn(deviationStatusManager);
	when(managerRepository.getApplicationUserManager()).thenReturn(applicationUserManager);
	when(managerRepository.getProductAreaGroupManager()).thenReturn(productAreaGroupManager);
	when(managerRepository.getBudgetManager()).thenReturn(budgetManager);
	final OrderManager orderManager = (OrderManager) ModelUtil.getBean(OrderManager.MANAGER_NAME);
	when(managerRepository.getOrderManager()).thenReturn(orderManager);
	when(managerRepository.getPacklistVManager()).thenReturn(packlistVManager);
	ArticleTypeManager articleTypeManager = (ArticleTypeManager) ModelUtil.getBean(ArticleTypeManager.MANAGER_NAME);
	when(managerRepository.getArticleTypeManager()).thenReturn(articleTypeManager);
	ApplicationUser applicationUser = new ApplicationUser();
	applicationUser.setFirstName("firstName");
	applicationUser.setLastName("lastName");
	when(login.getApplicationUser()).thenReturn(applicationUser);
	UserType userType = new UserType();
	userType.setIsAdmin(1);
	when(login.getUserType()).thenReturn(userType);

	Set<UserTypeAccess> userTypeAccesses = new HashSet<UserTypeAccess>();
	UserTypeAccess userTypeAccess = new UserTypeAccess();
	userTypeAccess.setWindowAccess(new WindowAccess(null, "Attributter", null));
	userTypeAccesses.add(userTypeAccess);
	userType.setUserTypeAccesses(userTypeAccesses);
	ProductArea productArea = new ProductArea();
	ProductAreaGroup productAreaGroup = new ProductAreaGroup();
	productArea.setProductAreaGroup(productAreaGroup);
	applicationUser.setProductArea(productArea);
	final PacklistVManager packlistVManager = (PacklistVManager) ModelUtil.getBean(PacklistVManager.MANAGER_NAME);

	AbstractProductionPackageViewHandler<PacklistV> productionViewHandler = new PacklistViewHandler(login, managerRepository,
		deviationViewHandlerFactory, orderViewHandlerFactory, new PacklistApplyList(login, packlistVManager), null, null,null);

	final PacklistView packlistView = new PacklistView(productionViewHandler);

	JDialog dialog = GuiActionRunner.execute(new GuiQuery<JDialog>() {
	    protected JDialog executeInEDT() {
		JDialog dialog = new JDialog();
		WindowInterface window = new JDialogAdapter(dialog);
		dialog.add(packlistView.buildPanel(window));
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
    public void testSetApplied() {
	dialogFixture.checkBox("CheckBoxFilter").uncheck();

	JTableFixture tableFixture = dialogFixture.table(TableEnum.TABLEPACKLIST.getTableName());

	tableFixture.cell(row(0).column(1)).click();

	dialogFixture.button("ButtonApply").requireEnabled();
	dialogFixture.button("ButtonUnapply").requireDisabled();

	dialogFixture.button("ButtonApply").click();

	DialogFixture dateView = new DialogFixture(dialogFixture.robot, (JDialog) dialogFixture.robot.finder().findByName("DateView"));
	dateView.button("ButtonOk").click();

	tableFixture.cell(row(0).column(1)).click();

	// dialogFixture.button("ButtonApply").requireDisabled();
	// dialogFixture.button("ButtonUnapply").requireEnabled();
    }

}
