package no.ugland.utransprod.gui.manuelt;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Set;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.LFEnum;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.edit.EditAttributeView;
import no.ugland.utransprod.gui.handlers.AttributeViewHandler;
import no.ugland.utransprod.model.Attribute;
import no.ugland.utransprod.model.UserType;
import no.ugland.utransprod.model.UserTypeAccess;
import no.ugland.utransprod.model.WindowAccess;
import no.ugland.utransprod.service.AttributeChoiceManager;
import no.ugland.utransprod.service.AttributeManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.UserTypeManager;
import no.ugland.utransprod.test.ManuellTest;
import no.ugland.utransprod.util.UserUtil;

import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.finder.JOptionPaneFinder;
import org.fest.swing.finder.WindowFinder;
import org.fest.swing.fixture.DialogFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.birosoft.liquid.LiquidLookAndFeel;
import com.google.inject.internal.Sets;
@Category(ManuellTest.class)
public class EditAttributeTest {
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
	private ManagerRepository managerRepository;

	@Mock
	private AttributeChoiceManager attributeChoiceManager;

	@Mock
	private AttributeManager attributeManager;

	@Mock
	private Login login;

	@Mock
	private UserTypeManager userTypeManager;

	@Before
	public void setUp() throws Exception {
		FailOnThreadViolationRepaintManager.install();
		MockitoAnnotations.initMocks(this);

		when(managerRepository.getAttributeChoiceManager()).thenReturn(
				attributeChoiceManager);
		when(managerRepository.getAttributeManager()).thenReturn(
				attributeManager);
		UserType userType = new UserType();
		Set<UserTypeAccess> userTypeAccesses = Sets.newHashSet();
		userTypeAccesses.add(new UserTypeAccess(null, 1, userType,
				new WindowAccess(null, "Attributter", null)));
		userType.setUserTypeAccesses(userTypeAccesses);
		when(login.getUserType()).thenReturn(userType);
		UserUtil.setUserTypeManagerForTest(userTypeManager);

		AttributeViewHandler attributeViewHandler = new AttributeViewHandler(
				login, managerRepository);

		final EditAttributeView editAttributeView = new EditAttributeView(
				attributeViewHandler, new Attribute(), false);

		JDialog dialog = GuiActionRunner.execute(new GuiQuery<JDialog>() {
			protected JDialog executeInEDT() {
				JDialog dialog = new JDialog();
				WindowInterface window = new JDialogAdapter(dialog);
				dialog.add(editAttributeView.buildPanel(window));
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
		dialogFixture.show();
		dialogFixture.requireVisible();
	}

	@Test
	public void testAddChoice() {

		dialogFixture.show();

		dialogFixture.textBox("TextFieldName").enterText("test");
		dialogFixture.textBox("TextFieldDescription").enterText("test");
		dialogFixture.button("SaveAttribute").click();
		JOptionPaneFinder.findOptionPane().using(dialogFixture.robot)
				.buttonWithText("Nei").click();
		dialogFixture.button("AddAttributeChoice").click();

		DialogFixture choiceDialog = WindowFinder.findDialog(
				"EditAttributeChoiceView").using(dialogFixture.robot);
		choiceDialog.textBox("TextFieldChoiceValue").enterText("testvalg");
		choiceDialog.textBox("TextFieldProdCatNo").enterText("1");
		choiceDialog.textBox("TextFieldProdCatNo2").enterText("2");
		choiceDialog.textBox("TextAreaComment").enterText("kommentar");
		choiceDialog.button("ButtonOk").click();

		assertEquals("testvalg",
				dialogFixture.table("TableAttributeChoice").target.getValueAt(
						0, 0));

	}

	@Test
	public void testSetProductCatNo() {
		dialogFixture.show();
		dialogFixture.textBox("TextFieldName").enterText("test");
		dialogFixture.textBox("TextFieldProdCatNo").enterText("1");
		dialogFixture.textBox("TextFieldProdCatNo2").enterText("2");
	}

	@Test
	public void testSetDataType() {
		dialogFixture.show();
		dialogFixture.comboBox("ComboBoxDataType").selectItem("Tall");
	}

}
