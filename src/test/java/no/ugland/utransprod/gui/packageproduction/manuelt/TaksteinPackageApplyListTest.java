package no.ugland.utransprod.gui.packageproduction.manuelt;

import static junit.framework.Assert.assertEquals;
import static org.fest.swing.data.TableCell.row;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

import no.ugland.utransprod.gui.ApplyListView;
import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.LFEnum;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.action.ProduceableProvider;
import no.ugland.utransprod.gui.action.SetProductionUnitAction;
import no.ugland.utransprod.gui.action.SetProductionUnitActionFactory;
import no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler;
import no.ugland.utransprod.gui.handlers.ProductionViewHandler;
import no.ugland.utransprod.gui.handlers.TableEnum;
import no.ugland.utransprod.gui.model.ProductionApplyList;
import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.Produceable;
import no.ugland.utransprod.service.ProductionUnitManager;
import no.ugland.utransprod.service.TaksteinSkarpnesVManager;
import no.ugland.utransprod.test.ManuellTest;
import no.ugland.utransprod.util.ModelUtil;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.DialogFixture;
import org.fest.swing.fixture.JTableFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.birosoft.liquid.LiquidLookAndFeel;

/**
 * @author atle.brekka
 * 
 */
@Category(ManuellTest.class)
public class TaksteinPackageApplyListTest extends PackageProductionTest {
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

		TaksteinSkarpnesVManager taksteinSkarpnesVManager = (TaksteinSkarpnesVManager) ModelUtil
				.getBean("taksteinSkarpnesVManager");

		SetProductionUnitActionFactory setProductionUnitActionFactory = new SetProductionUnitActionFactory() {

			public SetProductionUnitAction create(ArticleType aArticleType,
					ProduceableProvider aProduceableProvider,
					WindowInterface aWindow) {
				return new SetProductionUnitAction(managerRepository,
						aArticleType, aProduceableProvider, aWindow);
			}
		};

		final ProductionUnitManager productionUnitManager = (ProductionUnitManager) ModelUtil
				.getBean(ProductionUnitManager.MANAGER_NAME);

		AbstractProductionPackageViewHandler<Produceable> productionViewHandler = new ProductionViewHandler(
				new ProductionApplyList(login, taksteinSkarpnesVManager,
						"Takstein", "Takstein", null, managerRepository,null), "Takstein", login,
				"bestilt", null, TableEnum.TABLETAKSTEIN, null,
				managerRepository, deviationViewHandlerFactory,
				setProductionUnitActionFactory);

		final ApplyListView<Produceable> productionView = new ApplyListView<Produceable>(
				productionViewHandler, false);

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
	public void testShow() {
		dialogFixture.requireVisible();
		dialogFixture.comboBox("ComboBoxProductAreaGroup").selectItem("Alle");

		dialogFixture.button("ButtonApply").requireText("Sett bestilt");
		dialogFixture.button("ButtonUnapply").requireText("Sett ikke bestilt");

		JTableFixture tableFixture = dialogFixture
				.table(TableEnum.TABLETAKSTEIN.getTableName());

		assertEquals("Bestilt", tableFixture.target.getColumnName(6));
	}

	@Test
	public void testSetApplied() {
		dialogFixture.checkBox("CheckBoxFilter").uncheck();

		JTableFixture tableFixture = dialogFixture
				.table(TableEnum.TABLETAKSTEIN.getTableName());

		tableFixture.cell(row(0).column(1)).click();

		dialogFixture.button("ButtonApply").requireEnabled();
		dialogFixture.button("ButtonUnapply").requireDisabled();

		dialogFixture.button("ButtonApply").click();

		tableFixture.cell(row(0).column(1)).click();

		dialogFixture.button("ButtonApply").requireDisabled();
		dialogFixture.button("ButtonUnapply").requireEnabled();
	}
}
