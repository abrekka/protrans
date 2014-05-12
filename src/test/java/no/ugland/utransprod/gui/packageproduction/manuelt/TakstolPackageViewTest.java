package no.ugland.utransprod.gui.packageproduction.manuelt;

import static junit.framework.Assert.assertEquals;
import static org.fest.swing.data.TableCell.row;
import static org.mockito.Mockito.when;

import java.awt.Dimension;
import java.io.File;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPopupMenu;
import javax.swing.UIManager;

import no.ugland.utransprod.gui.ApplyListView;
import no.ugland.utransprod.gui.ArticlePackageViewFactory;
import no.ugland.utransprod.gui.ArticleProductionPackageView;
import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.LFEnum;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.action.ProduceableProvider;
import no.ugland.utransprod.gui.action.SetProductionUnitAction;
import no.ugland.utransprod.gui.action.SetProductionUnitActionFactory;
import no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler;
import no.ugland.utransprod.gui.handlers.ArticlePackageViewHandler;
import no.ugland.utransprod.gui.handlers.ArticlePackageViewHandlerFactory;
import no.ugland.utransprod.gui.handlers.TableEnum;
import no.ugland.utransprod.gui.handlers.TakstolPackageViewHandler;
import no.ugland.utransprod.gui.model.ApplyListInterface;
import no.ugland.utransprod.gui.model.TakstolPackageApplyList;
import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.PackableListItem;
import no.ugland.utransprod.service.AccidentManager;
import no.ugland.utransprod.service.FakturagrunnlagVManager;
import no.ugland.utransprod.service.OrdchgrHeadVManager;
import no.ugland.utransprod.service.TakstolPackageVManager;
import no.ugland.utransprod.service.VismaFileCreator;
import no.ugland.utransprod.service.impl.VismaFileCreatorImpl;
import no.ugland.utransprod.test.ManuellTest;
import no.ugland.utransprod.util.ApplicationParamUtil;
import no.ugland.utransprod.util.ModelUtil;

import org.apache.commons.io.FileUtils;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.finder.WindowFinder;
import org.fest.swing.fixture.DialogFixture;
import org.fest.swing.fixture.JPopupMenuFixture;
import org.fest.swing.fixture.JTableFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.birosoft.liquid.LiquidLookAndFeel;

/**
 * @author atle.brekka
 */
@Category(ManuellTest.class)
public class TakstolPackageViewTest extends PackageProductionTest {
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
     *
     */
    private DialogFixture dialogFixture;

    @Before
    public void setUp() throws Exception {
	super.setUp();
	ApplicationParamUtil.setApplicationParamManger(applicationParamManager);
	when(applicationParamManager.findByName("visma_out_dir")).thenReturn("visma");

	TakstolPackageVManager takstolPackageVManager = (TakstolPackageVManager) ModelUtil.getBean("takstolPackageVManager");
	OrdchgrHeadVManager ordchgrManager = (OrdchgrHeadVManager) ModelUtil.getBean(OrdchgrHeadVManager.MANAGER_NAME);
	FakturagrunnlagVManager fakturagrunnlagVManager = (FakturagrunnlagVManager) ModelUtil.getBean(FakturagrunnlagVManager.MANAGER_NAME);
	VismaFileCreator vismaFileCreator = new VismaFileCreatorImpl(ordchgrManager, false, fakturagrunnlagVManager);

	AccidentManager accidentManager = (AccidentManager) ModelUtil.getBean(AccidentManager.MANAGER_NAME);
	when(managerRepository.getAccidentManager()).thenReturn(accidentManager);

	ArticlePackageViewFactory articlePackageViewFactory = new ArticlePackageViewFactoryTest();

	AbstractProductionPackageViewHandler<PackableListItem> productionViewHandler = new TakstolPackageViewHandler(new TakstolPackageApplyList(
		takstolPackageVManager, vismaFileCreator, login, "Takstoler", null, articlePackageViewFactory, managerRepository), login,
		TableEnum.TABLEPACKAGETAKSTOL, "Takstoler", managerRepository, deviationViewHandlerFactory);

	final ApplyListView<PackableListItem> productionView = new ApplyListView<PackableListItem>(productionViewHandler, true);

	JDialog dialog = GuiActionRunner.execute(new GuiQuery<JDialog>() {
	    protected JDialog executeInEDT() {
		JDialog dialog = new JDialog();
		WindowInterface window = new JDialogAdapter(dialog);
		dialog.add(productionView.buildPanel(window));
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
    public void testSetApplied() {
	dialogFixture.checkBox("CheckBoxFilter").uncheck();

	JTableFixture tableFixture = dialogFixture.table(TableEnum.TABLEPACKAGETAKSTOL.getTableName());

	tableFixture.cell(row(0).column(1)).click();

	dialogFixture.button("ButtonApply").requireEnabled();
	dialogFixture.button("ButtonUnapply").requireDisabled();

	dialogFixture.button("ButtonApply").click();

	DialogFixture articleView = WindowFinder.findDialog("ArticlePackageView").using(dialogFixture.robot);
	JTableFixture tableFixtureArticles = articleView.table("TableArticles");
	int rows = tableFixtureArticles.rowCount();
	for (int i = 0; i < rows; i++) {
	    String test = tableFixtureArticles.cell(row(i).column(1)).value();
	    if ("false".equalsIgnoreCase(test)) {
		tableFixtureArticles.cell(row(i).column(1)).click();
	    }
	}

	articleView.button("ButtonOk").click();

	// DialogFixture dateView = new
	// DialogFixture(robot,(JDialog)robot.finder().findByName("DateView"));
	// dateView.button("ButtonOk").click();

	tableFixture.cell(row(0).column(1)).click();

	dialogFixture.button("ButtonApply").requireDisabled();
	dialogFixture.button("ButtonUnapply").requireEnabled();
    }

    @Test
    public void testSetAppliedWithFileToVisma() throws Exception {
	dialogFixture.show(new Dimension(1000, 500));
	dialogFixture.comboBox("ComboBoxProductAreaGroup").selectItem("Alle");
	dialogFixture.button("ButtonSearch").click();

	DialogFixture searchDialog = WindowFinder.findDialog("SearchOrderView").using(dialogFixture.robot);
	searchDialog.textBox("TextFieldSearch").enterText("70722");
	searchDialog.button("ButtonOk").click();

	// dialogFixture.table(TableEnum.TABLEPACKAGETAKSTOL.getTableName()).cell(row(0).column(1)).select();
	if (!dialogFixture.button("ButtonApply").target.isEnabled()) {
	    dialogFixture.button("ButtonUnapply").click();

	    dialogFixture.button("ButtonSearch").click();

	    searchDialog = WindowFinder.findDialog("SearchOrderView").using(dialogFixture.robot);
	    searchDialog.textBox("TextFieldSearch").enterText("70722");
	    searchDialog.button("ButtonOk").click();
	}
	dialogFixture.button("ButtonApply").requireEnabled();
	dialogFixture.button("ButtonUnapply").requireDisabled();

	dialogFixture.button("ButtonApply").click();
	DialogFixture articleView = WindowFinder.findDialog("ArticlePackageView").using(dialogFixture.robot);
	JTableFixture tableFixtureArticles = articleView.table("TableArticles");
	int rows = tableFixtureArticles.rowCount();
	for (int i = 0; i < rows; i++) {
	    String test = tableFixtureArticles.cell(row(i).column(1)).value();
	    if ("false".equalsIgnoreCase(test)) {
		tableFixtureArticles.cell(row(i).column(1)).click();
	    }
	}

	articleView.button("ButtonOk").click();

	File file = new File("visma/70722_.edi");
	assertEquals(true, file.exists());
	FileUtils.forceDelete(file);

    }

    @Test
    public void testSetStartPackage() {
	dialogFixture.comboBox("ComboBoxProductAreaGroup").selectItem("Alle");
	dialogFixture.checkBox("CheckBoxFilter").uncheck();
	dialogFixture.button("ButtonStart").requireDisabled();
	dialogFixture.button("ButtonStart").requireText("Startet pakking");

	dialogFixture.button("ButtonNotStart").requireDisabled();
	dialogFixture.button("ButtonNotStart").requireText("Ikke startet pakking");

	JTableFixture tableFixture = dialogFixture.table(TableEnum.TABLEPACKAGETAKSTOL.getTableName());

	assertEquals("Startet", tableFixture.target.getColumnName(6));

	tableFixture.cell(row(0).column(1)).click();

	String content = tableFixture.cell(row(0).column(6)).value();

	if (content.equalsIgnoreCase("---")) {
	    dialogFixture.button("ButtonStart").requireEnabled();
	    dialogFixture.button("ButtonStart").click();
	    dialogFixture.button("ButtonStart").requireDisabled();

	    tableFixture.cell(row(0).column(1)).click();
	    dialogFixture.button("ButtonStart").requireDisabled();
	    dialogFixture.button("ButtonNotStart").requireEnabled();

	} else {
	    dialogFixture.button("ButtonNotStart").requireEnabled();
	    dialogFixture.button("ButtonNotStart").click();
	    dialogFixture.button("ButtonNotStart").requireDisabled();

	    tableFixture.cell(row(0).column(1)).click();
	    dialogFixture.button("ButtonNotStart").requireDisabled();
	    dialogFixture.button("ButtonStart").requireEnabled();
	}
    }

    @Test
    public void testRegisterAccident() {
	dialogFixture.show(new Dimension(1000, 500));
	dialogFixture.button("ButtonAddAccident").requireVisible();
	dialogFixture.button("ButtonAddAccident").click();

	DialogFixture accidentDialog = WindowFinder.findDialog("EditAccidentView").withTimeout(10000).using(dialogFixture.robot);
	accidentDialog.button("EditCancelAccident").click();

    }

    @Test
    public void testRightClickRegisterAccident() {
	dialogFixture.show(new Dimension(1000, 500));
	JTableFixture tableFixture = dialogFixture.table(TableEnum.TABLEPACKAGETAKSTOL.getTableName());
	tableFixture.cell(row(0).column(1)).click();
	tableFixture.cell(row(0).column(1)).rightClick();

	JPopupMenuFixture popupMenuFixture = new JPopupMenuFixture(dialogFixture.robot, (JPopupMenu) dialogFixture.robot.finder().findByName(
		"PopupMenuProductionPackage"));
	popupMenuFixture.menuItem("MenuItemAccident").click();

	DialogFixture accidentDialog = WindowFinder.findDialog("EditAccidentView").withTimeout(10000).using(dialogFixture.robot);
	accidentDialog.button("EditCancelAccident").click();
    }

    private class ArticlePackageViewFactoryTest implements ArticlePackageViewFactory {

	public ArticleProductionPackageView create(ArticleType articleType, ApplyListInterface applyListInterface, String colliName) {
	    return new ArticleProductionPackageView(new ArticlePackageViewHandlerFactoryTest(), null, null);
	}

    }

    private class ArticlePackageViewHandlerFactoryTest implements ArticlePackageViewHandlerFactory {

	public ArticlePackageViewHandler create(ArticleType articleType, String colliName) {
	    return new ArticlePackageViewHandler(new SetProductionUnitActionFactoryTest(), login, managerRepository, null, null, null);
	}

    }

    private class SetProductionUnitActionFactoryTest implements SetProductionUnitActionFactory {

	public SetProductionUnitAction create(ArticleType aArticleType, ProduceableProvider aProduceableProvider, WindowInterface aWindow) {
	    return null;
	}

    }
}
