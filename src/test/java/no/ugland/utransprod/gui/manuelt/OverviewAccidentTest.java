package no.ugland.utransprod.gui.manuelt;

import static org.fest.swing.data.TableCell.row;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;

import java.awt.Dimension;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.LFEnum;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.OverviewView;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.handlers.AccidentViewHandler;
import no.ugland.utransprod.gui.model.AccidentModel;
import no.ugland.utransprod.model.Accident;
import no.ugland.utransprod.model.ApplicationUser;
import no.ugland.utransprod.model.UserType;
import no.ugland.utransprod.model.UserTypeAccess;
import no.ugland.utransprod.model.WindowAccess;
import no.ugland.utransprod.service.AccidentManager;
import no.ugland.utransprod.service.ApplicationUserManager;
import no.ugland.utransprod.service.DeviationStatusManager;
import no.ugland.utransprod.service.JobFunctionManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.UserTypeManager;
import no.ugland.utransprod.test.ManuellTest;
import no.ugland.utransprod.util.ApplicationUserUtil;
import no.ugland.utransprod.util.UserUtil;
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
import com.google.common.collect.Lists;
@Category(ManuellTest.class)
public class OverviewAccidentTest {
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
	@Mock
	private DeviationStatusManager deviationStatusManager;
	@Mock
	private AccidentManager accidentManager;
	@Mock
	private UserTypeManager userTypeManager;
	@Mock
	private JobFunctionManager jobFunctionManager;
	@Mock
	private ApplicationUserManager applicationUserManager;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		UserUtil.setUserTypeManagerForTest(userTypeManager);
		Util.setJobFunctionManager(jobFunctionManager);
		ApplicationUserUtil.setApplicationUserManager(applicationUserManager);
		when(managerRepository.getDeviationStatusManager()).thenReturn(
				deviationStatusManager);
		UserType userType = new UserType();
		Set<UserTypeAccess> userTypeAccesses = new HashSet<UserTypeAccess>();
		UserTypeAccess userTypeAccess = new UserTypeAccess();
		userTypeAccess.setWriteAccess(1);
		WindowAccess windowAccess = new WindowAccess();
		windowAccess.setWindowName("Garasjetype");
		userTypeAccess.setWindowAccess(windowAccess);
		userTypeAccesses.add(userTypeAccess);
		userType.setUserTypeAccesses(userTypeAccesses);

		when(login.getUserType()).thenReturn(userType);
		ApplicationUser applicationUser = new ApplicationUser();
		applicationUser.setGroupUser("Nei");
		when(login.getApplicationUser()).thenReturn(applicationUser);
		when(managerRepository.getAccidentManager())
				.thenReturn(accidentManager);
		List<Accident> accidentList = Lists.newArrayList();
		Accident accident = new Accident();
		accidentList.add(accident);
		when(accidentManager.findAll()).thenReturn(accidentList);
		when(accidentManager.findByObject((Accident) anyObject())).thenReturn(
				accidentList);

		final AccidentViewHandler accidentViewHandler = new AccidentViewHandler(
				login, managerRepository);
		final OverviewView<Accident, AccidentModel> view = new OverviewView<Accident, AccidentModel>(
				accidentViewHandler);

		JDialog dialog = GuiActionRunner.execute(new GuiQuery<JDialog>() {
			protected JDialog executeInEDT() {
				JDialog dialog = new JDialog();
				WindowInterface window = new JDialogAdapter(dialog);
				dialog.add(view.buildPanel(window));
				dialog.pack();
				dialog.setSize(accidentViewHandler.getWindowSize());
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
		dialogFixture.table("TableAccident").click();
		dialogFixture.table("TableAccident").cell(row(0).column(1)).value();

	}

	@Test
	public void testSearchOpen() {
		dialogFixture.show(new Dimension(850, 500));
		dialogFixture.button("SearchAccident").click();

		DialogFixture editDialog = WindowFinder.findDialog("EditAccidentView")
				.withTimeout(40000).using(dialogFixture.robot);

		editDialog.textBox("TextFieldRegisteredBy").enterText("Arild%");
		editDialog.button("EditSearchAccident").click();

		// Pause.pause(10000);

		JTableFixture tableFixture = dialogFixture.table("TableAccident");
		tableFixture.selectCell(row(0).column(1));
		tableFixture.cell(row(0).column(1)).doubleClick();

		editDialog = WindowFinder.findDialog("EditAccidentView").withTimeout(
				20000).using(dialogFixture.robot);
	}

	@Test
	public void testOpenAccident() {
		dialogFixture.show(new Dimension(850, 500));
		JTableFixture tableFixture = dialogFixture.table("TableAccident");
		tableFixture.click();

		tableFixture.cell(row(0).column(1)).doubleClick();

		WindowFinder.findDialog("EditAccidentView").withTimeout(60000).using(
				dialogFixture.robot);

	}
}
