package no.ugland.utransprod.gui.overview;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.LFEnum;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.OverviewView;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.handlers.ArticleTypeViewHandler;
import no.ugland.utransprod.gui.model.ArticleTypeModel;
import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.UserType;
import no.ugland.utransprod.service.ArticleTypeManager;
import no.ugland.utransprod.service.AttributeManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.test.GUITests;
import no.ugland.utransprod.util.ModelUtil;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.finder.JOptionPaneFinder;
import org.fest.swing.finder.WindowFinder;
import org.fest.swing.fixture.DialogFixture;
import org.fest.swing.fixture.JOptionPaneFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.birosoft.liquid.LiquidLookAndFeel;

/**
 * Tester vinduer for artikler
 * 
 * @author atle.brekka
 */
@Category(GUITests.class)
public class ArticleTypeViewTest {
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

	private ArticleTypeViewHandler viewHandler;

	private ArticleTypeManager articleTypeManager;
	@Mock
	private Login login;
	@Mock
	private ManagerRepository managerRepository;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		UserType userType = new UserType();
		userType.setIsAdmin(1);
		when(login.getUserType()).thenReturn(userType);

		articleTypeManager = (ArticleTypeManager) ModelUtil
				.getBean("articleTypeManager");
		when(managerRepository.getArticleTypeManager()).thenReturn(
				articleTypeManager);
		AttributeManager attributeManager = (AttributeManager) ModelUtil
				.getBean(AttributeManager.MANAGER_NAME);
		when(managerRepository.getAttributeManager()).thenReturn(
				attributeManager);

		viewHandler = new ArticleTypeViewHandler(login, managerRepository, null);

		final OverviewView<ArticleType, ArticleTypeModel> view = new OverviewView<ArticleType, ArticleTypeModel>(
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
		ArticleType artikkel = articleTypeManager.findByName("Tak");
		if (artikkel != null) {
			articleTypeManager.removeArticleType(artikkel);
		}
		dialogFixture.cleanUp();
	}

	@Test
	public void testOpenWindow() throws Exception {

		assertEquals(true, dialogFixture.table("TableArticleType").target
				.getRowCount() != 0);

		// sjekker at knapper finnes
		dialogFixture.button("AddArticleType");
		dialogFixture.button("EditArticleType");
		dialogFixture.button("RemoveArticleType");
		dialogFixture.button("SearchArticleType");

	}

	@Test
	public void testNew() throws Exception {
		dialogFixture.show();

		dialogFixture.button("AddArticleType").click();

		DialogFixture editDialog = WindowFinder.findDialog(
				"EditArticleTypeView").using(dialogFixture.robot);
		editDialog.table("TableAttributes");
		editDialog.button("ButtonAddAttribute");
		editDialog.button("ButtonRemoveAttribute");

		editDialog.textBox("TextFieldName").enterText("Tak");
		editDialog.textBox("TextFieldDescription").click();

		editDialog.textBox("TextFieldProdCatNo").enterText("1");
		editDialog.textBox("TextFieldProdCatNo2").enterText("2");

		int sizeBefore = viewHandler.getObjectSelectionList(null).getSize();

		editDialog.button("SaveArticleType").click();
		editDialog.button("EditCancelArticleType").click();

		assertEquals(sizeBefore + 1, viewHandler.getObjectSelectionList(null)
				.getSize());

		ArticleType artikkel = articleTypeManager.findByName("Tak");
		assertEquals(Integer.valueOf(1), artikkel.getProdCatNo());
		assertEquals(Integer.valueOf(2), artikkel.getProdCatNo2());
	}

	@Test
	public void testNewSaveEditSave() throws Exception {
		dialogFixture.show();
		int sizeBefore = viewHandler.getObjectSelectionList(null).getSize();

		dialogFixture.button("AddArticleType").click();

		DialogFixture editDialog = WindowFinder.findDialog(
				"EditArticleTypeView").using(dialogFixture.robot);

		editDialog.textBox("TextFieldName").enterText("Tak");
		editDialog.textBox("TextFieldDescription").click();
		editDialog.button("SaveArticleType").requireEnabled();
		editDialog.button("SaveArticleType").click();

		JOptionPaneFinder.findOptionPane().using(dialogFixture.robot)
				.buttonWithText("Nei").click();

		editDialog.textBox("TextFieldDescription").enterText("Test");
		editDialog.textBox("TextFieldName").click();

		editDialog.button("SaveArticleType").requireEnabled();
		editDialog.button("SaveArticleType").click();
		JOptionPaneFinder.findOptionPane().using(dialogFixture.robot)
				.buttonWithText("Nei").click();
		editDialog.button("EditCancelArticleType").click();

		assertEquals(sizeBefore + 1, viewHandler.getObjectSelectionList(null)
				.getSize());
	}

	@Test
	public void testOpenAttributes() throws Exception {
		dialogFixture.button("AddArticleType").click();

		DialogFixture editDialog = WindowFinder.findDialog(
				"EditArticleTypeView").using(dialogFixture.robot);
		editDialog.button("ButtonAddAttribute").click();

		WindowFinder.findDialog("AttributeView").using(dialogFixture.robot);
	}

	@Test
	public void testAddArticleTypeToArticleType() {
		dialogFixture.show();
		dialogFixture.button("AddArticleType").click();

		DialogFixture editDialog = WindowFinder.findDialog(
				"EditArticleTypeView").using(dialogFixture.robot);

		editDialog.textBox("TextFieldName").enterText("Tak");
		editDialog.textBox("TextFieldDescription").click();

		editDialog.button("SaveArticleType").click();
		JOptionPaneFinder.findOptionPane().using(dialogFixture.robot)
				.buttonWithText("Nei").click();

		ArticleType articleType = articleTypeManager.findByName("Tak");
		assertNotNull(articleType);

		editDialog.button("ButtonAddArticle").click();

		DialogFixture articleDialog = WindowFinder
				.findDialog("ArticleTypeView").using(dialogFixture.robot);
		articleDialog.list("ListArticles").target.setSelectedValue(articleType,
				true);
		articleDialog.button("ButtonOkArticle").click();

		editDialog.button("SaveArticleType").click();

		JOptionPaneFixture optionPane = JOptionPaneFinder.findOptionPane()
				.using(dialogFixture.robot);
		assertNotNull(optionPane);
		optionPane
				.requireMessage("Artikkel kan ikke ha referanse til seg selv!");

	}
}
