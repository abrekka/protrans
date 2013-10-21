package no.ugland.utransprod.gui.overview;

import static junit.framework.Assert.assertEquals;
import static org.fest.swing.data.TableCell.row;
import static org.mockito.Mockito.when;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.LFEnum;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.OverviewView;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.handlers.AttributeViewHandler;
import no.ugland.utransprod.gui.model.AttributeModel;
import no.ugland.utransprod.model.Attribute;
import no.ugland.utransprod.model.UserType;
import no.ugland.utransprod.service.AttributeChoiceManager;
import no.ugland.utransprod.service.AttributeManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.test.GUITests;
import no.ugland.utransprod.util.ModelUtil;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.finder.JOptionPaneFinder;
import org.fest.swing.finder.WindowFinder;
import org.fest.swing.fixture.DialogFixture;
import org.fest.swing.fixture.JTableFixture;
import org.jdesktop.swingx.JXTable;
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
public class AttributeOverviewTest {
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
		when(login.getUserType()).thenReturn(userType);
		AttributeManager attributeManager = (AttributeManager) ModelUtil
				.getBean(AttributeManager.MANAGER_NAME);
		when(managerRepository.getAttributeManager()).thenReturn(
				attributeManager);
		AttributeChoiceManager attributeChoiceManager = (AttributeChoiceManager) ModelUtil
				.getBean(AttributeChoiceManager.MANAGER_NAME);
		when(managerRepository.getAttributeChoiceManager()).thenReturn(
				attributeChoiceManager);

		final OverviewView<Attribute, AttributeModel> view = new OverviewView<Attribute, AttributeModel>(
				new AttributeViewHandler(login, managerRepository));

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

		AttributeManager attributeManager = (AttributeManager) ModelUtil
				.getBean("attributeManager");
		Attribute attribute = attributeManager.findByName("testing");

		if (attribute != null) {
			attributeManager.removeAttribute(attribute);
		}
	}

	@Test
	public void testOpenWindow() throws Exception {
		dialogFixture.requireVisible();

		dialogFixture.button("EditAttribute");
		dialogFixture.button("RemoveAttribute");
		dialogFixture.button("AddAttribute");
		dialogFixture.button("AddAttribute").requireText("Ny attributt...");
		dialogFixture.button("RemoveAttribute").requireText("Slett attributt");

	}

	@Test
	public void testAddChoice() {
		dialogFixture.button("AddAttribute").click();

		DialogFixture editDialog = WindowFinder.findDialog("EditAttributeView")
				.using(dialogFixture.robot);

		editDialog.textBox("TextFieldName").enterText("testing");
		editDialog.textBox("TextFieldDescription").click();
		editDialog.button("SaveAttribute").click();
		JOptionPaneFinder.findOptionPane().using(dialogFixture.robot)
				.buttonWithText("Nei").click();
		editDialog.button("AddAttributeChoice").click();

		DialogFixture choiceDialog = WindowFinder.findDialog(
				"EditAttributeChoiceView").using(dialogFixture.robot);

		choiceDialog.textBox("TextFieldChoiceValue").enterText("test");
		choiceDialog.button("ButtonOk").click();

		editDialog.button("EditCancelAttribute").click();

		JTableFixture tableFixture = dialogFixture.table("TableAttribute");
		int rowCount = tableFixture.target.getRowCount();
		((JXTable) tableFixture.target).scrollRowToVisible(rowCount - 1);
		tableFixture.selectCell(row(rowCount - 1).column(0));

		tableFixture.cell(row(rowCount - 1).column(0)).doubleClick();

		editDialog = WindowFinder.findDialog("EditAttributeView").using(
				dialogFixture.robot);
		assertEquals("test", editDialog.table("TableAttributeChoice").target
				.getValueAt(0, 0));
	}

}
