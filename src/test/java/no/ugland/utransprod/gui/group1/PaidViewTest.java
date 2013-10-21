package no.ugland.utransprod.gui.group1;

import static org.mockito.Mockito.when;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.LFEnum;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.PaidView;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.handlers.DeviationViewHandlerFactory;
import no.ugland.utransprod.gui.handlers.PaidViewHandler;
import no.ugland.utransprod.gui.model.PaidApplyList;
import no.ugland.utransprod.model.ApplicationUser;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.model.UserType;
import no.ugland.utransprod.service.ApplicationUserManager;
import no.ugland.utransprod.service.DeviationStatusManager;
import no.ugland.utransprod.service.JobFunctionManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.PaidVManager;
import no.ugland.utransprod.service.ProductAreaGroupManager;
import no.ugland.utransprod.service.ProductAreaManager;
import no.ugland.utransprod.test.GUITests;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Util;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.DialogFixture;
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
public class PaidViewTest {
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
	private DeviationViewHandlerFactory deviationViewHandlerFactory;
	@Mock
	private JobFunctionManager jobFunctionManager;
	@Mock
	private ProductAreaManager productAreaManager;
	@Mock
	private DeviationStatusManager deviationStatusManager;
	@Mock
	private ApplicationUserManager applicationUserManager;
	@Mock
	private PaidVManager paidVManager;
	private ProductAreaGroupManager productAreaGroupManager;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		productAreaGroupManager=(ProductAreaGroupManager)ModelUtil.getBean(ProductAreaGroupManager.MANAGER_NAME);
		Util.setProductAreaGroupManager(productAreaGroupManager);
		ProductAreaGroup productAreaGroup = new ProductAreaGroup();
		when(managerRepository.getJobFunctionManager()).thenReturn(
				jobFunctionManager);
		when(managerRepository.getProductAreaManager()).thenReturn(
				productAreaManager);
		when(managerRepository.getDeviationStatusManager()).thenReturn(
				deviationStatusManager);
		when(managerRepository.getApplicationUserManager()).thenReturn(
				applicationUserManager);
		when(managerRepository.getProductAreaGroupManager()).thenReturn(
				productAreaGroupManager);
		ApplicationUser applicationUser = new ApplicationUser();

		UserType userType = new UserType();
		userType.setIsAdmin(1);
		ProductArea productArea = new ProductArea();
		productArea.setProductAreaGroup(productAreaGroup);
		applicationUser.setProductArea(productArea);
		when(login.getUserType()).thenReturn(userType);
		when(login.getApplicationUser()).thenReturn(applicationUser);

		PaidViewHandler paidViewHandler = new PaidViewHandler(
				new PaidApplyList(login, paidVManager), login,
				managerRepository, deviationViewHandlerFactory);

		final PaidView viewer = new PaidView(paidViewHandler, false);

		JDialog dialog = GuiActionRunner.execute(new GuiQuery<JDialog>() {
			protected JDialog executeInEDT() {
				JDialog dialog = new JDialog();
				WindowInterface window = new JDialogAdapter(dialog);
				dialog.add(viewer.buildPanel(window));
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
	public void testShow() {
		dialogFixture.requireVisible();
	}

	@Test
	public void testFilterButtons() {
		dialogFixture.radioButton("RadioButtonAll").requireSelected();
		dialogFixture.radioButton("RadioButtonAssembly").requireVisible();
		dialogFixture.radioButton("RadioButtonNotAssembly").requireVisible();
	}

}
