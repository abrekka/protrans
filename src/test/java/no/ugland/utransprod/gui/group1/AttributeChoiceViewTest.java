package no.ugland.utransprod.gui.group1;

import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

import no.ugland.utransprod.gui.AttributeChoiceOverviewView;
import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.LFEnum;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.handlers.AttributeChoiceViewHandler;
import no.ugland.utransprod.gui.model.AttributeModel;
import no.ugland.utransprod.model.Attribute;
import no.ugland.utransprod.model.UserType;
import no.ugland.utransprod.model.UserTypeAccess;
import no.ugland.utransprod.model.WindowAccess;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.test.GUITests;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.finder.WindowFinder;
import org.fest.swing.fixture.DialogFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.birosoft.liquid.LiquidLookAndFeel;

@Category(GUITests.class)
public class AttributeChoiceViewTest {
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

		final UserType userType = new UserType();
		userType.setIsAdmin(1);
		Set<UserTypeAccess> userTypeAccesses = new HashSet<UserTypeAccess>();
		UserTypeAccess userTypeAccess = new UserTypeAccess();
		userTypeAccess.setWindowAccess(new WindowAccess(null, "Attributter",
				null));
		userTypeAccesses.add(userTypeAccess);
		userType.setUserTypeAccesses(userTypeAccesses);

		when(login.getUserType()).thenReturn(userType);

		final AttributeChoiceOverviewView view = new AttributeChoiceOverviewView(
				new AttributeChoiceViewHandler(login, managerRepository,
						new AttributeModel(new Attribute())));

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
	public void testNewChoice() throws Exception {
		dialogFixture.requireVisible();

		dialogFixture.button("AddAttributeChoice").click();

		DialogFixture editDialog = WindowFinder.findDialog(
				"EditAttributeChoiceView").using(dialogFixture.robot);

		editDialog.textBox("TextFieldChoiceValue").enterText("testattributt");
	}

}
