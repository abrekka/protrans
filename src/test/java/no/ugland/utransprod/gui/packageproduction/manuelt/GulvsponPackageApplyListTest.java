package no.ugland.utransprod.gui.packageproduction.manuelt;

import static junit.framework.Assert.assertEquals;
import static org.fest.swing.data.TableCell.row;

import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

import no.ugland.utransprod.gui.ApplyListView;
import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.LFEnum;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler;
import no.ugland.utransprod.gui.handlers.PackageViewHandler;
import no.ugland.utransprod.gui.handlers.TableEnum;
import no.ugland.utransprod.gui.model.GulvsponPackageApplyList;
import no.ugland.utransprod.model.PackableListItem;
import no.ugland.utransprod.service.GulvsponPackageVManager;
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
public class GulvsponPackageApplyListTest extends PackageProductionTest {
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

	@Before
	public void setUp() throws Exception {
		super.setUp();

		GulvsponPackageVManager gulvsponPackageVManager = (GulvsponPackageVManager) ModelUtil
				.getBean("gulvsponPackageVManager");
		AbstractProductionPackageViewHandler<PackableListItem> packageViewHandler = new PackageViewHandler(
				login, managerRepository, deviationViewHandlerFactory,
				new GulvsponPackageApplyList(login, gulvsponPackageVManager,
						managerRepository), "Pakking av gulvspon",
				TableEnum.TABLEPACKAGEGULVSPON, "Gulvspon");

		final ApplyListView<PackableListItem> applyListView = new ApplyListView<PackableListItem>(
				packageViewHandler, true);

		JDialog dialog = GuiActionRunner.execute(new GuiQuery<JDialog>() {
			protected JDialog executeInEDT() {
				JDialog dialog = new JDialog();
				WindowInterface window = new JDialogAdapter(dialog);
				dialog.add(applyListView.buildPanel(window));
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

		JTableFixture tableFixture = dialogFixture
				.table(TableEnum.TABLEPACKAGEGULVSPON.getTableName());

		tableFixture.cell(row(0).column(1)).click();

		dialogFixture.button("ButtonApply").requireEnabled();
		dialogFixture.button("ButtonUnapply").requireDisabled();

		dialogFixture.button("ButtonApply").click();

		tableFixture.cell(row(0).column(1)).click();

		dialogFixture.button("ButtonApply").requireDisabled();
		dialogFixture.button("ButtonUnapply").requireEnabled();
	}

	@Test
	public void testSetStartPackage() {
		dialogFixture.show(new Dimension(1000, 500));
		dialogFixture.checkBox("CheckBoxFilter").uncheck();
		dialogFixture.button("ButtonStart").requireDisabled();
		dialogFixture.button("ButtonStart").requireText("Startet pakking");

		dialogFixture.button("ButtonNotStart").requireDisabled();
		dialogFixture.button("ButtonNotStart").requireText(
				"Ikke startet pakking");

		JTableFixture tableFixture = dialogFixture
				.table(TableEnum.TABLEPACKAGEGULVSPON.getTableName());

		assertEquals("Startet", tableFixture.target.getColumnName(6));

		tableFixture.cell(row(0).column(1)).click();

		String content = tableFixture.cell(row(0).column(5)).value();

		if (content.equalsIgnoreCase("---")) {
			dialogFixture.button("ButtonStart").requireEnabled();
			dialogFixture.button("ButtonStart").click();

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
}
