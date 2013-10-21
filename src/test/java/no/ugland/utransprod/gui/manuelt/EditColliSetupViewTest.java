package no.ugland.utransprod.gui.manuelt;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.LFEnum;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.edit.EditColliSetupView;
import no.ugland.utransprod.gui.handlers.ApplicationParamViewHandler;
import no.ugland.utransprod.model.ApplicationParam;
import no.ugland.utransprod.model.ApplicationUser;
import no.ugland.utransprod.model.UserType;
import no.ugland.utransprod.service.ApplicationParamManager;
import no.ugland.utransprod.service.ApplicationUserManager;
import no.ugland.utransprod.service.enums.LazyLoadEnum;
import no.ugland.utransprod.test.ManuellTest;
import no.ugland.utransprod.util.ApplicationParamUtil;
import no.ugland.utransprod.util.ModelUtil;

import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.finder.JOptionPaneFinder;
import org.fest.swing.finder.WindowFinder;
import org.fest.swing.fixture.DialogFixture;
import org.fest.swing.fixture.JListFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.birosoft.liquid.LiquidLookAndFeel;
@Category(ManuellTest.class)
public class EditColliSetupViewTest {
	static {
		try {

			UIManager.setLookAndFeel(LFEnum.LNF_LIQUID.getClassName());
			JFrame.setDefaultLookAndFeelDecorated(true);
			LiquidLookAndFeel.setLiquidDecorations(true, "mac");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * test
	 */
	private DialogFixture dialogFixture;

	/**
     *
     */
	private ApplicationParamManager applicationParamManager;
	private String newColliName = "kolli_6";

	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	@Before
	public void setUp() throws Exception {
		FailOnThreadViolationRepaintManager.install();
		setUpDialog();

	}

	private void setUpDialog() {
		applicationParamManager = (ApplicationParamManager) ModelUtil
				.getBean("applicationParamManager");
		ApplicationUserManager applicationUserManager = (ApplicationUserManager) ModelUtil
				.getBean("applicationUserManager");
		ApplicationUser user;
		user = applicationUserManager.login("admin", "admin");
		applicationUserManager.lazyLoad(user, new LazyLoadEnum[][] { {
				LazyLoadEnum.USER_ROLES, LazyLoadEnum.NONE } });
		UserType userType = user.getUserRoles().iterator().next().getUserType();

		ApplicationParamViewHandler applicationParamViewHandler = new ApplicationParamViewHandler(
				"Kollier", applicationParamManager, userType);

		final EditColliSetupView editColliSetupView = new EditColliSetupView(
				applicationParamViewHandler);

		JDialog dialog = GuiActionRunner.execute(new GuiQuery<JDialog>() {
			protected JDialog executeInEDT() {
				JDialog dialog = new JDialog();
				WindowInterface window = new JDialogAdapter(dialog);
				dialog.add(editColliSetupView.buildPanel(window));
				dialog.pack();
				return dialog;
			}
		});
		dialogFixture = new DialogFixture(dialog);
		dialogFixture.show();

	}

	/**
	 * @see junit.framework.TestCase#tearDown()
	 */
	@After
	public void tearDown() throws Exception {
		dialogFixture.cleanUp();
		ApplicationParam applicationParam = ApplicationParamUtil
				.findParam(newColliName);

		if (applicationParam != null) {
			applicationParamManager.removeObject(applicationParam);
		}
	}

	@Test
	public void testShow() {
		dialogFixture.requireVisible();
		dialogFixture.list("ListCollies").requireVisible();
	}

	@Test
	public void testAddColli() {
		dialogFixture.requireVisible();
		String[] content = dialogFixture.list("ListCollies").contents();
		int size = content.length;
		newColliName = "kolli_" + (size + 1);
		dialogFixture.button("ButtonAddColli").click();

		DialogFixture optionDialog = WindowFinder.findDialog("Kollioppsett")
				.using(dialogFixture.robot);
		optionDialog.textBox("TextFieldInput").enterText("test1;test1");
		optionDialog.button("ButtonOk").click();

		content = dialogFixture.list("ListCollies").contents();
		assertEquals(size + 1, content.length);

		dialogFixture.button("SaveApplicationParam").click();

		ApplicationParam applicationParam = ApplicationParamUtil
				.findParam(newColliName);
		assertNotNull(applicationParam);
	}

	@Test
	public void testRemoveColli() {
		dialogFixture.requireVisible();
		dialogFixture.button("ButtonRemoveColli").requireDisabled();
		JListFixture listFixture = dialogFixture.list("ListCollies");
		String[] content = listFixture.contents();
		int size = content.length;
		newColliName = "kolli_" + (size + 1);
		dialogFixture.button("ButtonAddColli").click();

		DialogFixture optionDialog = WindowFinder.findDialog("Kollioppsett")
				.using(dialogFixture.robot);
		optionDialog.textBox("TextFieldInput").enterText("test;test");
		optionDialog.button("ButtonOk").click();

		content = dialogFixture.list("ListCollies").contents();
		assertEquals(size + 1, content.length);
		dialogFixture.button("SaveApplicationParam").click();
		ApplicationParam applicationParam = ApplicationParamUtil
				.findParam(newColliName);
		assertNotNull(applicationParam);

		listFixture.selectItem("test;test");
		dialogFixture.button("ButtonRemoveColli").click();
		JOptionPaneFinder.findOptionPane().using(dialogFixture.robot)
				.buttonWithText("Ja").click();

		content = dialogFixture.list("ListCollies").contents();
		assertEquals(size, content.length);

		dialogFixture.button("SaveApplicationParam").click();

		applicationParam = ApplicationParamUtil.findParam(newColliName);
		assertNull(applicationParam);
	}

}
