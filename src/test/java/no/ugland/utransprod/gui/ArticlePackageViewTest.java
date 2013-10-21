package no.ugland.utransprod.gui;

import static junit.framework.Assert.assertEquals;
import static org.fest.swing.data.TableCell.row;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JDialog;

import no.ugland.utransprod.gui.action.ProduceableProvider;
import no.ugland.utransprod.gui.action.SetProductionUnitAction;
import no.ugland.utransprod.gui.action.SetProductionUnitActionFactory;
import no.ugland.utransprod.gui.handlers.ArticlePackageViewHandler;
import no.ugland.utransprod.gui.handlers.ArticlePackageViewHandler.PackProduction;
import no.ugland.utransprod.gui.handlers.ArticlePackageViewHandlerFactory;
import no.ugland.utransprod.gui.model.Applyable;
import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.Colli;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.UserType;
import no.ugland.utransprod.model.UserTypeAccess;
import no.ugland.utransprod.model.WindowAccess;
import no.ugland.utransprod.service.ApplicationParamManager;
import no.ugland.utransprod.service.ColliManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.OrderLineManager;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.service.UserTypeManager;
import no.ugland.utransprod.test.FastTests;
import no.ugland.utransprod.test.GUITests;
import no.ugland.utransprod.util.ApplicationParamUtil;
import no.ugland.utransprod.util.UserUtil;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.DialogFixture;
import org.fest.swing.fixture.JTableFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@Category(GUITests.class)
public class ArticlePackageViewTest {

	private DialogFixture dialogFixture;

	@Mock
	private UserTypeManager userTypeManager;
	@Mock
	private Login login;
	@Mock
	private ManagerRepository managerRepository;
	@Mock
	private OrderManager orderManager;
	@Mock
	private ColliManager colliManager;
	@Mock
	private OrderLineManager orderLineManager;
	@Mock
	private ApplicationParamManager applicationParamManager;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		when(managerRepository.getOrderManager()).thenReturn(orderManager);
		when(managerRepository.getColliManager()).thenReturn(colliManager);
		when(managerRepository.getOrderLineManager()).thenReturn(
				orderLineManager);
		ApplicationParamUtil.setApplicationParamManger(applicationParamManager);
		OrderLine orderLine = new OrderLine();
		ArticleType articleType = new ArticleType();
		articleType.setArticleTypeName("test");
		orderLine.setArticleType(articleType);
		final Order order = new Order();
		order.setOrderNr("1");
		orderLine.setOrder(order);
		final Colli colli = new Colli();
		colli.setColliName("test");
		orderLine.setColli(colli);
		when(orderLineManager.findByOrderLineId(anyInt()))
				.thenReturn(orderLine);
		final List<Applyable> packlist = new ArrayList<Applyable>();

		when(colliManager.findByNameAndOrder(anyString(), (Order) anyObject()))
				.thenReturn(colli);

		packlist.add(orderLine);
		final SetProductionUnitActionFactory setProductionUnitActionFactory = new SetProductionUnitActionFactoryTest();
		final UserType userType = new UserType();
		Set<UserTypeAccess> userTypeAccesses = new HashSet<UserTypeAccess>();
		UserTypeAccess userTypeAccess = new UserTypeAccess();
		WindowAccess windowAccess = new WindowAccess();
		windowAccess.setWindowName("windowName");
		userTypeAccess.setWindowAccess(windowAccess);
		userTypeAccesses.add(userTypeAccess);
		userType.setUserTypeAccesses(userTypeAccesses);
		UserUtil.setUserTypeManagerForTest(userTypeManager);
		when(login.getUserType()).thenReturn(userType);
		ArticlePackageViewHandlerFactory articlePackageViewHandlerFactory = new ArticlePackageViewHandlerFactory() {

			public ArticlePackageViewHandler create(ArticleType articleType,
					String colliName) {
				return new ArticlePackageViewHandler(
						setProductionUnitActionFactory, login,
						managerRepository, null, null, null);
			}
		};
		final ArticleProductionPackageView articlePackageView = new ArticleProductionPackageView(
				articlePackageViewHandlerFactory, null, null);
		articlePackageView.setArticles(packlist, PackProduction.PACK);

		JDialog dialog = GuiActionRunner.execute(new GuiQuery<JDialog>() {
			protected JDialog executeInEDT() {
				JDialog dialog = new JDialog();
				WindowInterface window = new JDialogAdapter(dialog);
				dialog.add(articlePackageView.buildPanel(window));
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
	public void testShowPackageArticles() {
		dialogFixture.requireVisible();

		JTableFixture tableFixture = dialogFixture.table("TableArticles");
		tableFixture.requireVisible();
		assertEquals("Artikkel", tableFixture.target.getColumnName(0));
		assertEquals("Pakket", tableFixture.target.getColumnName(1));
		
		assertEquals("test ", tableFixture.cell(row(0).column(0)).value());
		tableFixture.cell(row(0).column(1)).click();
		dialogFixture.button("ButtonOk").click();
	}

	private class SetProductionUnitActionFactoryTest implements
			SetProductionUnitActionFactory {

		public SetProductionUnitAction create(ArticleType aArticleType,
				ProduceableProvider aProduceableProvider,
				WindowInterface aWindow) {
			return new SetProductionUnitAction(null, null, null, null);
		}

	}
}
