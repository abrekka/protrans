package no.ugland.utransprod.gui.windows.manuelt;

import static org.mockito.Mockito.when;

import javax.swing.JFrame;

import no.ugland.utransprod.gui.ArticlePackageViewFactory;
import no.ugland.utransprod.gui.ArticleProductionPackageView;
import no.ugland.utransprod.gui.SystemReadyListener;
import no.ugland.utransprod.gui.TakstolProductionWindow;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.handlers.ArticlePackageViewHandler;
import no.ugland.utransprod.gui.handlers.ArticlePackageViewHandlerFactory;
import no.ugland.utransprod.gui.handlers.OrderNrProvider;
import no.ugland.utransprod.gui.handlers.ShowTakstolInfoAction;
import no.ugland.utransprod.gui.handlers.ShowTakstolInfoActionFactory;
import no.ugland.utransprod.gui.handlers.TableEnum;
import no.ugland.utransprod.gui.handlers.TakstolProductionViewHandler;
import no.ugland.utransprod.gui.model.ApplyListInterface;
import no.ugland.utransprod.gui.model.TakstolProductionApplyList;
import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.service.ProductionUnitManager;
import no.ugland.utransprod.util.ModelUtil;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.FrameFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author atle.brekka
 * 
 */
public class TakstolProductionWindowTest extends WindowTest {

	/**
     *
     */
	private FrameFixture frameFixture;

	@Before
	protected void setUp() throws Exception {
		super.setUp();
		ProductionUnitManager productionUnitManager = (ProductionUnitManager) ModelUtil
				.getBean(ProductionUnitManager.MANAGER_NAME);
		when(managerRepository.getProductionUnitManager()).thenReturn(
				productionUnitManager);
		ArticlePackageViewFactory articlePackageViewFactory = new ArticlePackageViewFactory() {

			public ArticleProductionPackageView create(ArticleType articleType,
					ApplyListInterface applyListInterface,
					String defaultColliName) {
				ArticlePackageViewHandlerFactory articlePackageViewHandlerFactory = new ArticlePackageViewHandlerFactory() {

					public ArticlePackageViewHandler create(
							ArticleType articleType, String defaultColliName) {
						return null;
					}
				};
				return new ArticleProductionPackageView(
						articlePackageViewHandlerFactory, articleType,
						defaultColliName);
			}
		};
		TakstolProductionApplyList takstolProductionApplyList = new TakstolProductionApplyList(
				"Takstol", login, "Takstoler", managerRepository,
				articlePackageViewFactory);

		final ShowTakstolInfoActionFactory showTakstolInfoActionFactory = new ShowTakstolInfoActionFactory() {

			public ShowTakstolInfoAction create(
					OrderNrProvider aProduceableProvider, WindowInterface window) {
				return new ShowTakstolInfoAction(managerRepository, window,
						aProduceableProvider);
			}
		};

		TakstolProductionViewHandler takstolProductionViewhandler = new TakstolProductionViewHandler(
				takstolProductionApplyList, login, managerRepository,
				deviationViewHandlerFactory, showTakstolInfoActionFactory, null);
		final TakstolProductionWindow takstolProductionWindow = new TakstolProductionWindow(
				login, takstolProductionViewhandler);
		takstolProductionWindow.setLogin(login);

		JFrame frame = GuiActionRunner.execute(new GuiQuery<JFrame>() {
			protected JFrame executeInEDT() {
				return (JFrame) takstolProductionWindow.buildMainWindow(
						new SystemReadyListener() {

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
	public void testShow() throws Exception {
		frameFixture.requireVisible();

		frameFixture.table(TableEnum.TABLEPRODUCTIONTAKSTOL.getTableName());
		assertEquals("Produksjon av takstol", frameFixture.target.getTitle());

	}
}
