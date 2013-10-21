package no.ugland.utransprod.gui.overview2;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

import no.ugland.utransprod.gui.AttributeView;
import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.LFEnum;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.handlers.AttributeViewHandler;
import no.ugland.utransprod.model.UserType;
import no.ugland.utransprod.service.AttributeManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.test.GUITests;
import no.ugland.utransprod.util.ModelUtil;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.finder.WindowFinder;
import org.fest.swing.fixture.DialogFixture;
import org.jdesktop.swingx.JXList;
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
public class AttributeViewTest {
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
		AttributeManager attributeManager = (AttributeManager) ModelUtil
				.getBean(AttributeManager.MANAGER_NAME);
		when(managerRepository.getAttributeManager()).thenReturn(
				attributeManager);
		UserType userType = new UserType();
		userType.setIsAdmin(1);
		when(login.getUserType()).thenReturn(userType);

		AttributeViewHandler viewHandler = new AttributeViewHandler(login,
				managerRepository);
		final AttributeView view = new AttributeView(viewHandler, false);

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

		assertEquals(true,
				((JXList) dialogFixture.list("ListAttributes").target)
						.getElementCount() != 0);

		dialogFixture.button("CancelAttribute");
		dialogFixture.button("EditAttribute");
		dialogFixture.button("RemoveAttribute");
		dialogFixture.button("AddAttribute");
		dialogFixture.button("AddAttribute").requireText("Ny attributt...");
		dialogFixture.button("RemoveAttribute").requireText("Slett attributt");

	}

	@Test
	public void testNewAttribute() throws Exception {
		dialogFixture.show();

		dialogFixture.button("AddAttribute").click();

		DialogFixture editDialog = WindowFinder.findDialog("EditAttributeView")
				.using(dialogFixture.robot);

		editDialog.textBox("TextFieldName").enterText("Test");

	}

}
