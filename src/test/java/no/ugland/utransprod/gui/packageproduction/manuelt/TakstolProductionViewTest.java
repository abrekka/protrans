package no.ugland.utransprod.gui.packageproduction.manuelt;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static org.fest.swing.data.TableCell.row;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

import no.ugland.utransprod.gui.ArticlePackageViewFactory;
import no.ugland.utransprod.gui.ArticleProductionPackageView;
import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.LFEnum;
import no.ugland.utransprod.gui.TakstolApplyListView;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.action.ProduceableProvider;
import no.ugland.utransprod.gui.action.SetProductionUnitAction;
import no.ugland.utransprod.gui.action.SetProductionUnitActionFactory;
import no.ugland.utransprod.gui.handlers.ArticlePackageViewHandler;
import no.ugland.utransprod.gui.handlers.ArticlePackageViewHandlerFactory;
import no.ugland.utransprod.gui.handlers.ShowTakstolInfoActionFactory;
import no.ugland.utransprod.gui.handlers.TableEnum;
import no.ugland.utransprod.gui.handlers.TakstolProductionViewHandler;
import no.ugland.utransprod.gui.model.ApplyListInterface;
import no.ugland.utransprod.gui.model.ColorEnum;
import no.ugland.utransprod.gui.model.TakstolProductionApplyList;
import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.Produceable;
import no.ugland.utransprod.model.TakstolProductionV;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.test.ManuellTest;
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

import com.birosoft.liquid.LiquidLookAndFeel;

/**
 * @author atle.brekka
 * 
 */
@Category(ManuellTest.class)
public class TakstolProductionViewTest extends PackageProductionTest {
    static {
	try {

	    UIManager.setLookAndFeel(LFEnum.LNF_LIQUID.getClassName());
	    JFrame.setDefaultLookAndFeelDecorated(true);
	    LiquidLookAndFeel.setLiquidDecorations(true, "mac");

	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    private TakstolProductionViewHandler takstolProductionViewHandler;

    private DialogFixture dialogFixture;
    @Mock
    private ShowTakstolInfoActionFactory showTakstolInfoActionFactory;

    @Before
    public void setUp() throws Exception {

	super.setUp();

	final ArticlePackageViewHandler articlePackageViewHandler = new ArticlePackageViewHandler(new SetProductionUnitActionFactoryTest(), login,
		managerRepository, null, null, null, null);

	final ArticlePackageViewHandlerFactory articlePackageViewHandlerFactory = new ArticlePackageViewHandlerFactory() {

	    public ArticlePackageViewHandler create(ArticleType articleType, String colliName) {
		return articlePackageViewHandler;
	    }
	};

	ArticlePackageViewFactory articlePackageViewFactory = new ArticlePackageViewFactory() {

	    public ArticleProductionPackageView create(ArticleType articleType, ApplyListInterface applyListInterface, String colliName) {
		return new ArticleProductionPackageView(articlePackageViewHandlerFactory, articleType, null);
	    }
	};
	TakstolProductionApplyList takstolProductionApplyList = new TakstolProductionApplyList("Takstol", login, "Takstoler", managerRepository,
		articlePackageViewFactory);
	SetProductionUnitActionFactory setProductionUnitActionFactory = new SetProductionUnitActionFactory() {

	    public SetProductionUnitAction create(ArticleType aArticleType, ProduceableProvider aProduceableProvider, WindowInterface aWindow) {
		return new SetProductionUnitAction(managerRepository, aArticleType, aProduceableProvider, aWindow);
	    }
	};

	takstolProductionViewHandler = new TakstolProductionViewHandler(takstolProductionApplyList, login, managerRepository,
		deviationViewHandlerFactory, showTakstolInfoActionFactory, setProductionUnitActionFactory);

	final TakstolApplyListView productionView = new TakstolApplyListView(takstolProductionViewHandler);

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

    /**
     * @see junit.framework.TestCase#tearDown()
     */
    @After
    public void tearDown() throws Exception {
	dialogFixture.cleanUp();
    }

    @Test
    public void testSetApplied() {
	dialogFixture.comboBox("ComboBoxProductAreaGroup").selectItem("Takstol");
	dialogFixture.checkBox("CheckBoxFilter").uncheck();

	JTableFixture tableFixture = dialogFixture.table(TableEnum.TABLEPRODUCTIONTAKSTOL.getTableName());

	tableFixture.cell(row(0).column(1)).click();

	Produceable order = takstolProductionViewHandler.getSelectedObject();
	int index = 0;
	while (order.getProbability() != 100 || order.getProduced() != null) {
	    index++;
	    tableFixture.cell(row(index).column(1)).click();
	    order = takstolProductionViewHandler.getSelectedObject();
	}

	dialogFixture.button("ButtonApply").requireEnabled();
	dialogFixture.button("ButtonUnapply").requireDisabled();

	dialogFixture.button("ButtonApply").click();

	DialogFixture productionUnitDialog = WindowFinder.findDialog("Velg produksjonsenhet").using(dialogFixture.robot);
	productionUnitDialog.comboBox().selectItem("Jig 1");
	productionUnitDialog.button("ButtonOk").click();

	DialogFixture articleView = WindowFinder.findDialog("ArticlePackageView").withTimeout(30000).using(dialogFixture.robot);
	articleView.button("ButtonOk").click();

	tableFixture.cell(row(0).column(1)).click();

	dialogFixture.button("ButtonApply").requireDisabled();
	dialogFixture.button("ButtonUnapply").requireEnabled();
    }

    @Test
    public void testSetStartProduction() {
	dialogFixture.show(new Dimension(1000, 500));
	dialogFixture.checkBox("CheckBoxFilter").uncheck();
	dialogFixture.button("ButtonStart").requireDisabled();
	dialogFixture.button("ButtonStart").requireText("Startet produksjon");

	dialogFixture.button("ButtonNotStart").requireDisabled();
	dialogFixture.button("ButtonNotStart").requireText("Ikke startet produksjon");

	JTableFixture tableFixture = dialogFixture.table(TableEnum.TABLEPRODUCTIONTAKSTOL.getTableName());

	assertEquals("Startet", tableFixture.target.getColumnName(8));

	tableFixture.cell(row(0).column(1)).click();

	Produceable order = takstolProductionViewHandler.getSelectedObject();
	int index = 0;
	while (order.getProbability() != 100 || order.getProduced() != null) {
	    index++;
	    tableFixture.cell(row(index).column(1)).click();
	    order = takstolProductionViewHandler.getSelectedObject();
	}

	String content = tableFixture.cell(row(index).column(8)).value();

	if (content.equalsIgnoreCase("---")) {
	    dialogFixture.button("ButtonStart").requireEnabled();
	    dialogFixture.button("ButtonStart").click();
	    dialogFixture.button("ButtonStart").requireDisabled();

	    tableFixture.cell(row(index).column(1)).click();
	    dialogFixture.button("ButtonStart").requireDisabled();
	    dialogFixture.button("ButtonNotStart").requireEnabled();

	} else {
	    dialogFixture.button("ButtonNotStart").requireEnabled();
	    dialogFixture.button("ButtonNotStart").click();
	    dialogFixture.button("ButtonNotStart").requireDisabled();

	    tableFixture.cell(row(index).column(1)).click();
	    dialogFixture.button("ButtonNotStart").requireDisabled();
	    dialogFixture.button("ButtonStart").requireEnabled();
	}
    }

    @Test
    public void testShowProductionDate() {
	dialogFixture.show(new Dimension(1000, 500));
	JTableFixture tableFixture = dialogFixture.table(TableEnum.TABLEPRODUCTIONTAKSTOL.getTableName());

	assertEquals("Prod.dato", tableFixture.target.getColumnName(2));

    }

    @Test
    public void testShowTakstolInfo() {
	dialogFixture.show(new Dimension(1000, 500));
	dialogFixture.button("ButtonShowTakstolInfo").requireVisible();
	dialogFixture.button("ButtonShowTakstolInfo").requireDisabled();
    }

    @Test
    public void skalViseKnapperForKapping() {
	dialogFixture.show(new Dimension(1000, 500));
	dialogFixture.button("ButtonStartetKapping").requireVisible();
	dialogFixture.button("ButtonStartetKapping").requireText("Startet kapping");
	dialogFixture.button("ButtonIkkeStartetKapping").requireVisible();
	dialogFixture.button("ButtonIkkeStartetKapping").requireText("Ikke startet kapping");

	dialogFixture.button("ButtonFerdigKappet").requireVisible();
	dialogFixture.button("ButtonFerdigKappet").requireText("Ferdig kappet");

	dialogFixture.button("ButtonIkkeFerdigKappet").requireVisible();
	dialogFixture.button("ButtonIkkeFerdigKappet").requireText("Ikke ferdig kappet");
    }

    @Test
    public void skalSetteStartetKapping() {
	dialogFixture.comboBox("ComboBoxProductAreaGroup").selectItem("Takstol");
	dialogFixture.checkBox("CheckBoxFilter").uncheck();

	JTableFixture tableFixture = dialogFixture.table(TableEnum.TABLEPRODUCTIONTAKSTOL.getTableName());

	tableFixture.cell(row(0).column(1)).click();

	TakstolProductionV takstol = (TakstolProductionV) takstolProductionViewHandler.getSelectedObject();
	int index = 0;
	while (takstol.getProbability() != 100 || takstol.getProduced() != null || takstol.getActionStarted() != null
		|| takstol.getCuttingStarted() != null) {
	    index++;
	    tableFixture.cell(row(index).column(1)).click();
	    takstol = (TakstolProductionV) takstolProductionViewHandler.getSelectedObject();
	}

	dialogFixture.button("ButtonStartetKapping").requireEnabled();
	dialogFixture.button("ButtonStartetKapping").click();
	tableFixture.cell(row(index - 1).column(1)).click();
	assertEquals(ColorEnum.BLUE.getColor(), tableFixture.cell(row(index).column(1)).foreground().target());

	OrderManager orderManager = (OrderManager) ModelUtil.getBean(OrderManager.MANAGER_NAME);
	Order order = orderManager.findByOrderNr(takstol.getOrderNr());
	orderManager.lazyLoadTree(order);
	OrderLine orderLineTakstol = order.getOrderLine("Takstoler");
	assertNotNull(orderLineTakstol.getCuttingStarted());

    }

    @Test
    public void skalIkkeKunneSetteStartetKappingDersomStartetProduksjon() {
	dialogFixture.comboBox("ComboBoxProductAreaGroup").selectItem("Takstol");
	dialogFixture.checkBox("CheckBoxFilter").uncheck();

	JTableFixture tableFixture = dialogFixture.table(TableEnum.TABLEPRODUCTIONTAKSTOL.getTableName());

	tableFixture.cell(row(0).column(1)).click();

	TakstolProductionV takstol = (TakstolProductionV) takstolProductionViewHandler.getSelectedObject();
	int index = 0;
	boolean funnetStartetProduksjon = false;
	while (!funnetStartetProduksjon) {
	    index++;
	    tableFixture.cell(row(index).column(1)).click();
	    takstol = (TakstolProductionV) takstolProductionViewHandler.getSelectedObject();
	    if (takstol.getActionStarted() != null && takstol.getProduced() == null) {
		funnetStartetProduksjon = true;
	    }
	}

	dialogFixture.button("ButtonStartetKapping").requireDisabled();

    }

    @Test
    public void skalSettIkkeStartetKapping() {
	dialogFixture.comboBox("ComboBoxProductAreaGroup").selectItem("Takstol");
	dialogFixture.checkBox("CheckBoxFilter").uncheck();

	JTableFixture tableFixture = dialogFixture.table(TableEnum.TABLEPRODUCTIONTAKSTOL.getTableName());

	tableFixture.cell(row(0).column(1)).click();

	TakstolProductionV takstol = (TakstolProductionV) takstolProductionViewHandler.getSelectedObject();
	int index = 0;
	while (takstol.getProbability() != 100 || takstol.getProduced() != null || takstol.getActionStarted() != null
		|| takstol.getCuttingStarted() == null || takstol.getCuttingDone() != null) {
	    index++;
	    tableFixture.cell(row(index).column(1)).click();
	    takstol = (TakstolProductionV) takstolProductionViewHandler.getSelectedObject();
	}

	dialogFixture.button("ButtonStartetKapping").requireDisabled();
	dialogFixture.button("ButtonIkkeStartetKapping").requireEnabled();
	dialogFixture.button("ButtonIkkeStartetKapping").click();
	tableFixture.cell(row(index).column(1)).foreground().requireEqualTo(new Color(3, 3, 3));

	OrderManager orderManager = (OrderManager) ModelUtil.getBean(OrderManager.MANAGER_NAME);
	Order order = orderManager.findByOrderNr(takstol.getOrderNr());
	orderManager.lazyLoadTree(order);
	OrderLine orderLineTakstol = order.getOrderLine("Takstoler");
	assertNull(orderLineTakstol.getCuttingStarted());
    }

    @Test
    public void skalSetteFerdigKapping() {
	dialogFixture.comboBox("ComboBoxProductAreaGroup").selectItem("Takstol");
	dialogFixture.checkBox("CheckBoxFilter").uncheck();

	JTableFixture tableFixture = dialogFixture.table(TableEnum.TABLEPRODUCTIONTAKSTOL.getTableName());

	tableFixture.cell(row(0).column(1)).click();

	TakstolProductionV takstol = (TakstolProductionV) takstolProductionViewHandler.getSelectedObject();
	int index = 0;
	while (takstol.getProbability() != 100 || takstol.getProduced() != null || takstol.getActionStarted() != null
		|| takstol.getCuttingDone() != null) {
	    index++;
	    tableFixture.cell(row(index).column(1)).click();
	    takstol = (TakstolProductionV) takstolProductionViewHandler.getSelectedObject();
	}

	dialogFixture.button("ButtonFerdigKappet").requireEnabled();
	dialogFixture.button("ButtonFerdigKappet").click();
	tableFixture.cell(row(index - 1).column(1)).click();
	assertEquals(ColorEnum.GREEN.getColor(), tableFixture.cell(row(index).column(1)).foreground().target());

	OrderManager orderManager = (OrderManager) ModelUtil.getBean(OrderManager.MANAGER_NAME);
	Order order = orderManager.findByOrderNr(takstol.getOrderNr());
	orderManager.lazyLoadTree(order);
	OrderLine orderLineTakstol = order.getOrderLine("Takstoler");
	assertNotNull(orderLineTakstol.getCuttingDone());

    }

    @Test
    public void skalSettIkkeferdigKapping() {
	dialogFixture.comboBox("ComboBoxProductAreaGroup").selectItem("Takstol");
	dialogFixture.checkBox("CheckBoxFilter").uncheck();

	JTableFixture tableFixture = dialogFixture.table(TableEnum.TABLEPRODUCTIONTAKSTOL.getTableName());

	tableFixture.cell(row(0).column(1)).click();

	TakstolProductionV takstol = (TakstolProductionV) takstolProductionViewHandler.getSelectedObject();
	int index = 0;
	while (takstol.getProbability() != 100 || takstol.getProduced() != null || takstol.getActionStarted() != null
		|| takstol.getCuttingDone() == null) {
	    index++;
	    tableFixture.cell(row(index).column(1)).click();
	    takstol = (TakstolProductionV) takstolProductionViewHandler.getSelectedObject();
	}

	dialogFixture.button("ButtonFerdigKappet").requireDisabled();
	dialogFixture.button("ButtonIkkeFerdigKappet").requireEnabled();
	dialogFixture.button("ButtonIkkeFerdigKappet").click();
	tableFixture.cell(row(index).column(1)).foreground().requireEqualTo(new Color(3, 3, 3));

	OrderManager orderManager = (OrderManager) ModelUtil.getBean(OrderManager.MANAGER_NAME);
	Order order = orderManager.findByOrderNr(takstol.getOrderNr());
	orderManager.lazyLoadTree(order);
	OrderLine orderLineTakstol = order.getOrderLine("Takstoler");
	assertNull(orderLineTakstol.getCuttingDone());
    }

    private class SetProductionUnitActionFactoryTest implements SetProductionUnitActionFactory {

	public SetProductionUnitAction create(ArticleType aArticleType, ProduceableProvider aProduceableProvider, WindowInterface aWindow) {
	    // TODO Auto-generated method stub
	    return null;
	}

    }
}
