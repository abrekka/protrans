package no.ugland.utransprod.gui.manuelt;

import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.LFEnum;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.edit.EditTransportView;
import no.ugland.utransprod.gui.handlers.DeviationViewHandlerFactory;
import no.ugland.utransprod.gui.handlers.OrderViewHandlerFactory;
import no.ugland.utransprod.gui.handlers.ShowTakstolInfoActionFactory;
import no.ugland.utransprod.gui.handlers.TransportViewHandler;
import no.ugland.utransprod.model.Transport;
import no.ugland.utransprod.model.UserType;
import no.ugland.utransprod.model.UserTypeAccess;
import no.ugland.utransprod.model.WindowAccess;
import no.ugland.utransprod.service.ApplicationParamManager;
import no.ugland.utransprod.service.ArticleTypeManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.ProductAreaManager;
import no.ugland.utransprod.service.SupplierManager;
import no.ugland.utransprod.service.UserTypeManager;
import no.ugland.utransprod.service.enums.LazyLoadEnum;
import no.ugland.utransprod.service.impl.VismaFileCreatorImpl;
import no.ugland.utransprod.test.ManuellTest;
import no.ugland.utransprod.util.ApplicationParamUtil;
import no.ugland.utransprod.util.UserUtil;

import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
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
@Category(ManuellTest.class)
public class EditTransportViewTest {
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
	private ApplicationParamManager applicationParamManager;
	@Mock
	private Login login;
	@Mock
	private ManagerRepository managerRepository;
	@Mock
	private DeviationViewHandlerFactory deviationViewHandlerFactory;
	@Mock
	private OrderViewHandlerFactory orderViewHandlerFactory;
	@Mock
	private ShowTakstolInfoActionFactory showTakstolInfoActionFactory;
	@Mock
	private ProductAreaManager productAreaManager;
	@Mock
	private ArticleTypeManager articleTypeManager;
	@Mock
	private SupplierManager supplierManager;

	@Before
	public void setUp() throws Exception {
		FailOnThreadViolationRepaintManager.install();
		MockitoAnnotations.initMocks(this);
		when(managerRepository.getProductAreaManager()).thenReturn(
				productAreaManager);
		when(managerRepository.getArticleTypeManager()).thenReturn(
				articleTypeManager);
		when(managerRepository.getSupplierManager())
				.thenReturn(supplierManager);

		final UserType userType = new UserType();
		Set<UserTypeAccess> userTypeAccesses = new HashSet<UserTypeAccess>();
		UserTypeAccess userTypeAccess = new UserTypeAccess();
		WindowAccess windowAccess = new WindowAccess();
		windowAccess.setWindowName("test");
		userTypeAccess.setWindowAccess(windowAccess);
		userTypeAccesses.add(userTypeAccess);
		userType.setUserTypeAccesses(userTypeAccesses);
		when(login.getUserType()).thenReturn(userType);
		ApplicationParamUtil.setApplicationParamManger(applicationParamManager);

		
		final UserTypeManager userTypeManager = new UserTypeManagerTest();
		UserUtil.setUserTypeManagerForTest(userTypeManager);

		
		JDialog dialog = GuiActionRunner.execute(new GuiQuery<JDialog>() {
			protected JDialog executeInEDT() {
				Transport transport = new Transport();
				TransportViewHandler transportViewHandler = new TransportViewHandler(
						orderViewHandlerFactory, login, managerRepository,
						deviationViewHandlerFactory, showTakstolInfoActionFactory,
						new VismaFileCreatorImpl(null, true));

				final EditTransportView editTransportView = new EditTransportView(
						transportViewHandler, transport, false);

				JDialog dialog = new JDialog();
				WindowInterface window = new JDialogAdapter(dialog);
				dialog.add(editTransportView.buildPanel(window));
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
	public void testOpenDialog() {
		dialogFixture.requireVisible();

		dialogFixture.textBox("TextAreaComment").requireVisible();
	}

	private class UserTypeManagerTest implements UserTypeManager {

		public int getNumberOfUsers(UserType userType) {
			return 0;
		}

		public List<UserType> findAll() {
			return null;
		}

		public List<UserType> findByObject(UserType object) {
			return null;
		}

		public void refreshObject(UserType object) {
		}

		public void removeObject(UserType object) {
		}

		public void saveObject(UserType object) throws ProTransException {
		}

		public void lazyLoad(UserType object, LazyLoadEnum[][] enums) {
		}

	}
}
