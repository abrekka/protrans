package no.ugland.utransprod.gui.overview;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.when;

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
import no.ugland.utransprod.gui.handlers.PreventiveActionViewHandler;
import no.ugland.utransprod.gui.model.PreventiveActionModel;
import no.ugland.utransprod.model.ApplicationUser;
import no.ugland.utransprod.model.PreventiveAction;
import no.ugland.utransprod.model.PreventiveActionComment;
import no.ugland.utransprod.model.PreventiveActionCommentCommentType;
import no.ugland.utransprod.model.UserType;
import no.ugland.utransprod.service.JobFunctionManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.PreventiveActionManager;
import no.ugland.utransprod.service.enums.LazyLoadEnum;
import no.ugland.utransprod.test.GUITests;
import no.ugland.utransprod.util.ModelUtil;

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

/**
 * @author atle.brekka
 */
@Category(GUITests.class)
public class OverviewPreventiveActionViewTest {
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
	private PreventiveActionManager preventiveActionManager;
	@Mock
	private Login login;
	@Mock
	private ManagerRepository managerRepository;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		JobFunctionManager jobFunctionManager = (JobFunctionManager) ModelUtil
				.getBean(JobFunctionManager.MANAGER_NAME);
		when(managerRepository.getJobFunctionManager()).thenReturn(
				jobFunctionManager);
		ApplicationUser applicationUser = new ApplicationUser();
		applicationUser.setUserName("username");
		when(login.getApplicationUser()).thenReturn(applicationUser);
		UserType userType = new UserType();
		userType.setIsAdmin(1);
		when(login.getUserType()).thenReturn(userType);
		preventiveActionManager = (PreventiveActionManager) ModelUtil
				.getBean("preventiveActionManager");
		when(managerRepository.getPreventiveActionManager()).thenReturn(
				preventiveActionManager);

		PreventiveActionViewHandler preventiveActionViewHandler = new PreventiveActionViewHandler(
				login, managerRepository);

		final OverviewView<PreventiveAction, PreventiveActionModel> view = new OverviewView<PreventiveAction, PreventiveActionModel>(
				preventiveActionViewHandler);

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
		PreventiveAction preventiveAction = new PreventiveAction();
		preventiveAction.setPreventiveActionName("test");
		List<PreventiveAction> list = preventiveActionManager
				.findByObject(preventiveAction);
		if (list != null) {
			for (PreventiveAction action : list) {
				preventiveActionManager.removeObject(action);
			}
		}
	}

	@Test
	public void testOpenWindow() throws Exception {

		dialogFixture.requireVisible();
	}

	@Test
	public void testCreationComment() {
		dialogFixture.show();
		dialogFixture.button("AddPreventiveAction").click();
		DialogFixture editDialog = WindowFinder.findDialog(
				"EditPreventiveActionView").using(dialogFixture.robot);
		editDialog.list("ListPreventiveActionComments").selectItem(0);
	}

	@Test
	public void testCloseComment() {
		dialogFixture.show();
		dialogFixture.button("AddPreventiveAction").click();
		DialogFixture editDialog = WindowFinder.findDialog(
				"EditPreventiveActionView").using(dialogFixture.robot);
		editDialog.textBox("TextFieldName").enterText("test");
		editDialog.list("ListPreventiveActionComments").selectItem(0);

		editDialog.checkBox("CheckBoxClosed").check();

		DialogFixture inputDialog = WindowFinder.findDialog("Lukking").using(
				dialogFixture.robot);

		inputDialog.textBox().enterText("Test");
		inputDialog.button("ButtonOk").click();

		editDialog.list("ListPreventiveActionComments").selectItem(1);
		editDialog.button("SavePreventiveAction").click();

		PreventiveAction preventiveAction = new PreventiveAction();
		preventiveAction.setPreventiveActionName("test");
		List<PreventiveAction> list = preventiveActionManager
				.findByObject(preventiveAction);
		assertNotNull(list);
		assertEquals(1, list.size());

		preventiveAction = list.get(0);
		preventiveActionManager
				.lazyLoad(
						preventiveAction,
						new LazyLoadEnum[][] { {
								LazyLoadEnum.PREVENTIVE_ACTION_COMMENTS,
								LazyLoadEnum.PREVENTIVE_ACTION_COMMENT_COMMENT_TYPES } });
		Set<PreventiveActionComment> comments = preventiveAction
				.getPreventiveActionComments();
		assertNotNull(comments);
		assertEquals(2, comments.size());

		int count = 0;
		for (PreventiveActionComment comment : comments) {
			count++;
			if (count == 1) {
				assertEquals("Test", comment.getComment());
			} else {
				assertEquals("Opprettet", comment.getComment());
			}
			Set<PreventiveActionCommentCommentType> commentList = comment
					.getPreventiveActionCommentCommentTypes();
			assertNotNull(commentList);
			assertEquals(1, commentList.size());

			PreventiveActionCommentCommentType commentType = commentList
					.iterator().next();
			if (count == 1) {
				assertEquals("Lukking", commentType.getCommentType()
						.getCommentTypeName());
			} else {
				assertEquals("Opprettet", commentType.getCommentType()
						.getCommentTypeName());
			}

		}
	}
}
