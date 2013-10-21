package no.ugland.utransprod.gui.overview2;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.LFEnum;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.OverviewView;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.handlers.ConstructionTypeViewHandler;
import no.ugland.utransprod.gui.model.ConstructionTypeModel;
import no.ugland.utransprod.model.ConstructionType;
import no.ugland.utransprod.model.UserType;
import no.ugland.utransprod.service.ArticleTypeManager;
import no.ugland.utransprod.service.ConstructionTypeManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.test.GUITests;
import no.ugland.utransprod.util.ModelUtil;

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
@Category(GUITests.class)
public class ConstructionTypeViewTest {
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
		ConstructionTypeManager constructionTypeManager = (ConstructionTypeManager) ModelUtil
				.getBean(ConstructionTypeManager.MANAGER_NAME);
		when(managerRepository.getConstructionTypeManager()).thenReturn(
				constructionTypeManager);
		ArticleTypeManager articleTypeManager = (ArticleTypeManager) ModelUtil
				.getBean(ArticleTypeManager.MANAGER_NAME);
		when(managerRepository.getArticleTypeManager()).thenReturn(
				articleTypeManager);

		ConstructionTypeViewHandler viewHandler = new ConstructionTypeViewHandler(
				login, managerRepository, false, false);

		final OverviewView<ConstructionType, ConstructionTypeModel> view = new OverviewView<ConstructionType, ConstructionTypeModel>(
				viewHandler);

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
		ConstructionTypeManager constructionTypeManager = (ConstructionTypeManager) ModelUtil
				.getBean("constructionTypeManager");
		ConstructionType constructionType = constructionTypeManager
				.findByName("test");
		if (constructionType != null) {
			constructionTypeManager.removeConstructionType(constructionType);
		}
		dialogFixture.cleanUp();
	}

	@Test
	public void testOpenEditView() throws Exception {

		JTableFixture table = dialogFixture.table("TableConstructionType");

		assertEquals(true, table.target.getModel().getRowCount() != 0);

		dialogFixture.button("RemoveConstructionType");

		dialogFixture.button("AddConstructionType").click();

		WindowFinder.findDialog("EditConstructionTypeView").using(
				dialogFixture.robot);

	}

	@Test
	public void testNewConstructionTypeWithArticle() {
		dialogFixture.button("AddConstructionType").click();

		DialogFixture editDialog = WindowFinder.findDialog(
				"EditConstructionTypeView").using(dialogFixture.robot);

		editDialog.textBox("TextFieldName").enterText("test");
		editDialog.comboBox("ComboBoxProductArea").selectItem("Takstol");

		editDialog.button("ButtonAddArticle").click();

		DialogFixture editArticleDialog = WindowFinder.findDialog(
				"ArticleTypeView").using(dialogFixture.robot);
		editArticleDialog.list("ListArticles").target.setSelectedIndex(127);
		assertEquals(true, editArticleDialog.list("ListArticles").target
				.getSelectedIndex() != -1);
		editArticleDialog.button("ButtonOkArticle").click();

		editDialog.button("SaveConstructionType").click();

	}

}
