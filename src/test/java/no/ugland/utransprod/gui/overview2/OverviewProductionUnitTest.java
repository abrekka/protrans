package no.ugland.utransprod.gui.overview2;

import static org.fest.swing.data.TableCell.row;
import static org.mockito.Mockito.when;

import java.awt.Dimension;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.LFEnum;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.OverviewView;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.handlers.ProductionUnitViewHandler;
import no.ugland.utransprod.gui.model.ProductionUnitModel;
import no.ugland.utransprod.model.ProductionUnit;
import no.ugland.utransprod.model.UserType;
import no.ugland.utransprod.service.ArticleTypeManager;
import no.ugland.utransprod.service.ProductionUnitManager;
import no.ugland.utransprod.test.GUITests;
import no.ugland.utransprod.util.Util;

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
import com.google.common.collect.Lists;
@Category(GUITests.class)
public class OverviewProductionUnitTest {
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
	private ProductionUnitManager productionUnitManager;
	@Mock
	private ArticleTypeManager articleTypeManager;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		Util.setArticleTypeManager(articleTypeManager);
		UserType userType = new UserType();
		userType.setIsAdmin(1);
		when(login.getUserType()).thenReturn(userType);
		List<ProductionUnit> productionUnitList = Lists.newArrayList();
		ProductionUnit productionUnit = new ProductionUnit();
		productionUnit.setProductionUnitName("productionUnitName");
		productionUnitList.add(productionUnit);
		when(productionUnitManager.findAll()).thenReturn(productionUnitList);

		final ProductionUnitViewHandler productionUnitViewHandler = new ProductionUnitViewHandler(
				login, productionUnitManager);
		final OverviewView<ProductionUnit, ProductionUnitModel> view = new OverviewView<ProductionUnit, ProductionUnitModel>(
				productionUnitViewHandler);

		JDialog dialog = GuiActionRunner.execute(new GuiQuery<JDialog>() {
			protected JDialog executeInEDT() {
				JDialog dialog = new JDialog();
				WindowInterface window = new JDialogAdapter(dialog);
				dialog.add(view.buildPanel(window));
				dialog.pack();
				dialog.setSize(productionUnitViewHandler.getWindowSize());
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
	}

	@Test
	public void testOpenNew() {
		dialogFixture.button("AddProductionUnit").click();
		WindowFinder.findDialog("EditProductionUnitView").withTimeout(60000)
				.using(dialogFixture.robot);
	}

	@Test
	public void testOpenProductionUnit() {
		dialogFixture.show(new Dimension(850, 500));
		JTableFixture tableFixture = dialogFixture.table("TableProductionUnit");
		tableFixture.click();

		tableFixture.cell(row(0).column(1)).doubleClick();

		WindowFinder.findDialog("EditProductionUnitView").withTimeout(60000)
				.using(dialogFixture.robot);

	}

}
