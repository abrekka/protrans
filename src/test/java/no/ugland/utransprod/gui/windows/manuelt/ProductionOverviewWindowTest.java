package no.ugland.utransprod.gui.windows.manuelt;

import static org.mockito.Mockito.when;

import javax.swing.JFrame;
import javax.swing.UIManager;

import no.ugland.utransprod.gui.LFEnum;
import no.ugland.utransprod.gui.ProductionOverviewWindow;
import no.ugland.utransprod.gui.SystemReadyListener;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.action.ProduceableProvider;
import no.ugland.utransprod.gui.action.SetProductionUnitAction;
import no.ugland.utransprod.gui.action.SetProductionUnitActionFactory;
import no.ugland.utransprod.gui.handlers.OrderNrProvider;
import no.ugland.utransprod.gui.handlers.OrderViewHandler;
import no.ugland.utransprod.gui.handlers.OrderViewHandlerFactory;
import no.ugland.utransprod.gui.handlers.ProductionOverviewViewHandler;
import no.ugland.utransprod.gui.handlers.ShowTakstolInfoAction;
import no.ugland.utransprod.gui.handlers.ShowTakstolInfoActionFactory;
import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.service.BudgetManager;
import no.ugland.utransprod.service.FakturagrunnlagVManager;
import no.ugland.utransprod.service.OrdchgrHeadVManager;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.service.ProductionUnitManager;
import no.ugland.utransprod.service.VismaFileCreator;
import no.ugland.utransprod.service.impl.VismaFileCreatorImpl;
import no.ugland.utransprod.util.ModelUtil;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.FrameFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.birosoft.liquid.LiquidLookAndFeel;

/**
 * @author atle.brekka
 * 
 */
public class ProductionOverviewWindowTest extends WindowTest {
    static {
	try {

	    UIManager.setLookAndFeel(LFEnum.LNF_LIQUID.getClassName());
	    JFrame.setDefaultLookAndFeelDecorated(true);
	    LiquidLookAndFeel.setLiquidDecorations(true, "mac");

	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    private FrameFixture frameFixture;

    @Before
    protected void setUp() throws Exception {
	super.setUp();
	BudgetManager budgetManager = (BudgetManager) ModelUtil.getBean(BudgetManager.MANAGER_NAME);
	when(managerRepository.getBudgetManager()).thenReturn(budgetManager);
	ProductionUnitManager productionUnitManager = (ProductionUnitManager) ModelUtil.getBean(ProductionUnitManager.MANAGER_NAME);
	when(managerRepository.getProductionUnitManager()).thenReturn(productionUnitManager);
	OrdchgrHeadVManager ordchgrHeadVManager = (OrdchgrHeadVManager) ModelUtil.getBean(OrdchgrHeadVManager.MANAGER_NAME);
	FakturagrunnlagVManager fakturagrunnlagVManager = (FakturagrunnlagVManager) ModelUtil.getBean(FakturagrunnlagVManager.MANAGER_NAME);
	;
	VismaFileCreator vismaFileCreator = new VismaFileCreatorImpl(ordchgrHeadVManager, false, fakturagrunnlagVManager);
	final OrderViewHandler orderViewHandler = new OrderViewHandler(login, managerRepository, deviationOverviewViewFactory,
		deviationViewHandlerFactory, true, null);
	final OrderViewHandlerFactory orderViewHandlerFactory = new OrderViewHandlerFactory() {

	    public OrderViewHandler create(boolean notInitData) {
		return orderViewHandler;
	    }
	};

	final OrderManager orderManager = (OrderManager) ModelUtil.getBean(OrderManager.MANAGER_NAME);

	final ShowTakstolInfoActionFactory showTakstolInfoActionFactory = new ShowTakstolInfoActionFactory() {

	    public ShowTakstolInfoAction create(OrderNrProvider aProduceableProvider, WindowInterface window) {
		return null;
	    }
	};

	SetProductionUnitActionFactory setProductionUnitFactory = new SetProductionUnitActionFactory() {

	    public SetProductionUnitAction create(ArticleType aArticleType, ProduceableProvider aProduceableProvider, WindowInterface aWindow) {
		return new SetProductionUnitAction(managerRepository, aArticleType, aProduceableProvider, aWindow);
	    }
	};
	ProductionOverviewViewHandler productionOverviewViewHandler = new ProductionOverviewViewHandler(vismaFileCreator, orderViewHandlerFactory,
		login, managerRepository, deviationViewHandlerFactory, showTakstolInfoActionFactory, null, null, null, setProductionUnitFactory,
		null, null,null);
	final ProductionOverviewWindow productionOverviewWindow = new ProductionOverviewWindow(productionOverviewViewHandler);

	productionOverviewWindow.setLogin(login);

	JFrame frame = GuiActionRunner.execute(new GuiQuery<JFrame>() {
	    protected JFrame executeInEDT() {
		return (JFrame) productionOverviewWindow.buildMainWindow(new SystemReadyListener() {

		    public void systemReady() {

		    }

		}, managerRepository);

	    }
	});

	frameFixture = new FrameFixture(frame);
	frameFixture.show();

    }

    @After
    protected void tearDown() throws Exception {
	frameFixture.cleanUp();
	super.tearDown();
    }

    @Test
    public void testShowProductionWindow() throws Exception {
	frameFixture.requireVisible();
	assertEquals("Produksjonsoversikt", frameFixture.target.getTitle());

    }

}
