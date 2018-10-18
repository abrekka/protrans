package no.ugland.utransprod.gui.single;

import static org.fest.swing.data.TableCell.row;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.awt.Dimension;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

import no.ugland.utransprod.gui.DeviationOverviewView;
import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.LFEnum;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.handlers.DeviationViewHandler;
import no.ugland.utransprod.gui.handlers.PreventiveActionViewHandler;
import no.ugland.utransprod.model.ApplicationUser;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.model.UserType;
import no.ugland.utransprod.model.UserTypeAccess;
import no.ugland.utransprod.model.WindowAccess;
import no.ugland.utransprod.service.ApplicationUserManager;
import no.ugland.utransprod.service.DeviationManager;
import no.ugland.utransprod.service.DeviationStatusManager;
import no.ugland.utransprod.service.JobFunctionManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.service.PreventiveActionManager;
import no.ugland.utransprod.service.ProductAreaManager;
import no.ugland.utransprod.test.ManuellTest;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Util;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.finder.WindowFinder;
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
@Category(ManuellTest.class)
public class OverviewDeviationTest {
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

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		PreventiveActionManager preventiveActionManager = (PreventiveActionManager) ModelUtil
				.getBean(PreventiveActionManager.MANAGER_NAME);
		when(managerRepository.getPreventiveActionManager()).thenReturn(preventiveActionManager);
		JobFunctionManager jobFunctionManager = (JobFunctionManager) ModelUtil.getBean(JobFunctionManager.MANAGER_NAME);
		when(managerRepository.getJobFunctionManager()).thenReturn(jobFunctionManager);
		ProductAreaManager productAreaManager = (ProductAreaManager) ModelUtil.getBean(ProductAreaManager.MANAGER_NAME);
		when(managerRepository.getProductAreaManager()).thenReturn(productAreaManager);
		DeviationStatusManager deviationStatusManager = (DeviationStatusManager) ModelUtil
				.getBean(DeviationStatusManager.MANAGER_NAME);
		when(managerRepository.getDeviationStatusManager()).thenReturn(deviationStatusManager);
		ApplicationUserManager applicationUserManager = (ApplicationUserManager) ModelUtil
				.getBean(ApplicationUserManager.MANAGER_NAME);
		when(managerRepository.getApplicationUserManager()).thenReturn(applicationUserManager);
		DeviationManager deviationManager = (DeviationManager) ModelUtil.getBean(DeviationManager.MANAGER_NAME);
		when(managerRepository.getDeviationManager()).thenReturn(deviationManager);
		OrderManager orderManager = (OrderManager) ModelUtil.getBean(OrderManager.MANAGER_NAME);
		when(managerRepository.getOrderManager()).thenReturn(orderManager);
		final UserType userType = new UserType();
		Set<UserTypeAccess> userTypeAccesses = new HashSet<UserTypeAccess>();
		UserTypeAccess userTypeAccess = new UserTypeAccess();
		userTypeAccess.setWriteAccess(1);
		WindowAccess windowAccess = new WindowAccess();
		windowAccess.setWindowName("Produksjonsenhet");
		userTypeAccess.setWindowAccess(windowAccess);
		userTypeAccesses.add(userTypeAccess);
		userType.setUserTypeAccesses(userTypeAccesses);
		when(login.getUserType()).thenReturn(userType);
		final ApplicationUser applicationUser = new ApplicationUser();
		ProductArea productArea = new ProductArea();
		ProductAreaGroup productAreaGroup = new ProductAreaGroup();
		productArea.setProductAreaGroup(productAreaGroup);
		applicationUser.setProductArea(productArea);
		when(login.getApplicationUser()).thenReturn(applicationUser);

		final PreventiveActionViewHandler preventiveActionViewHandler = new PreventiveActionViewHandler(login,
				managerRepository);

		final DeviationViewHandler deviationViewHandler = new DeviationViewHandler(login, managerRepository,
				preventiveActionViewHandler, null, true, false, true, null, true, true);

		final DeviationOverviewView view = new DeviationOverviewView(preventiveActionViewHandler, deviationViewHandler,
				true, null, true, false, true, null, true);

		JDialog dialog = GuiActionRunner.execute(new GuiQuery<JDialog>() {
			protected JDialog executeInEDT() {
				JDialog dialog = new JDialog();
				WindowInterface window = new JDialogAdapter(dialog);
				dialog.add(view.buildPanel(window));
				dialog.pack();
				dialog.setSize(deviationViewHandler.getWindowSize());
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
	public void testClickTable() {
		dialogFixture.show(new Dimension(850, 500));
		dialogFixture.checkBox("CheckBoxFilterOwn").uncheck();
		dialogFixture.table("TableDeviation").click();
		dialogFixture.table("TableDeviation").cell(row(1).column(12)).value();

	}

	@Test
	public void testNoManagerCanNotDelete() {
		dialogFixture.show(new Dimension(850, 500));

		dialogFixture.checkBox("CheckBoxFilterOwn").uncheck();
		dialogFixture.table("TableDeviation").cell(row(0).column(0)).click();

		dialogFixture.button("RemoveDeviation").requireDisabled();
	}

	@Test
	public void testPrint() {
		dialogFixture.show(new Dimension(850, 500));
		dialogFixture.checkBox("CheckBoxFilterOwn").uncheck();
		JTableFixture tableFixture = dialogFixture.table("TableDeviation");
		tableFixture.selectCell(row(0).column(1));
		tableFixture.cell(row(0).column(1)).doubleClick();

		DialogFixture edit = WindowFinder.findDialog("EditDeviationView").withTimeout(20000).using(dialogFixture.robot);

		edit.button("ButtonPrint").click();

		DialogFixture printer = WindowFinder.findDialog("Avvik").withTimeout(20000).using(dialogFixture.robot);
		printer.button("ButtonCancel").click();
		assertNotNull(printer);
	}

	@Test
	public void testSearchOpen() {
		dialogFixture.show(new Dimension(850, 500));
		dialogFixture.checkBox("CheckBoxFilterOwn").uncheck();
		dialogFixture.button("SearchDeviation").click();

		DialogFixture editDialog = WindowFinder.findDialog("EditDeviationView").withTimeout(20000)
				.using(dialogFixture.robot);

		editDialog.textBox("TextFieldOrderNr").enterText("34325");
		editDialog.button("EditSearchDeviation").click();

		org.fest.swing.timing.Pause.pause(5000);

		dialogFixture.checkBox("CheckBoxFilterDone").check();
		JTableFixture tableFixture = dialogFixture.table("TableDeviation");
		tableFixture.selectCell(row(0).column(1));
		tableFixture.cell(row(0).column(1)).doubleClick();

		editDialog = WindowFinder.findDialog("EditDeviationView").withTimeout(20000).using(dialogFixture.robot);
	}

	@Test
	public void testOpenDeviation() {
		dialogFixture.show(new Dimension(850, 500));
		dialogFixture.checkBox("CheckBoxFilterOwn").uncheck();
		JTableFixture tableFixture = dialogFixture.table("TableDeviation");
		tableFixture.click();
		int row = -1;
		boolean functionNotFound = true;
		String content;
		while (functionNotFound) {
			row++;
			content = dialogFixture.table("TableDeviation").cell(row(row).column(8)).value();
			if (content.equalsIgnoreCase("Transport")) {
				functionNotFound = false;
			}
		}

		tableFixture.cell(row(row).column(1)).doubleClick();

		WindowFinder.findDialog("EditDeviationView").withTimeout(60000).using(dialogFixture.robot);

	}

	@Test
	public void testSetDateClosed() {
		dialogFixture.show(new Dimension(850, 500));
		dialogFixture.checkBox("CheckBoxFilterOwn").uncheck();

		JTableFixture tableFixture = dialogFixture.table("TableDeviation");
		tableFixture.click();

		tableFixture.cell(row(1).column(1)).doubleClick();

		DialogFixture editDialog = WindowFinder.findDialog("EditDeviationView").withTimeout(60000)
				.using(dialogFixture.robot);

		editDialog.comboBox("ComboBoxStatus").selectItem("Ferdigbehandlet");
		editDialog.textBox("TextFieldDateClosed").requireText(Util.getCurrentDateAsDateString());
	}
}
