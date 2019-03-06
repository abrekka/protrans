package no.ugland.utransprod.gui.packageproduction.manuelt;

import static junit.framework.Assert.assertEquals;
import static org.fest.swing.data.TableCell.row;

import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

import no.ugland.utransprod.gui.ApplyListView;
import no.ugland.utransprod.gui.FrontProductionWindow;
import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.LFEnum;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.handlers.TableEnum;
import no.ugland.utransprod.model.Produceable;
import no.ugland.utransprod.test.ManuellTest;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.DialogFixture;
import org.fest.swing.fixture.JTableFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.birosoft.liquid.LiquidLookAndFeel;

@Category(ManuellTest.class)
public class FrontProductionViewTest extends PackageProductionTest {
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
		FrontProductionWindow frontProductionWindow = new FrontProductionWindow(login, managerRepository,
				deviationViewHandlerFactory, null, null);
		frontProductionWindow.setLogin(login);

		final ApplyListView<Produceable> productionView = new ApplyListView<Produceable>(
				frontProductionWindow.getViewHandler(), false);

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
	public void testSetStartProduction() {
		dialogFixture.comboBox("ComboBoxProductAreaGroup").selectItem("Alle");
		dialogFixture.checkBox("CheckBoxFilter").uncheck();
		dialogFixture.button("ButtonStart").requireDisabled();
		dialogFixture.button("ButtonStart").requireText("Startet produksjon");

		dialogFixture.button("ButtonNotStart").requireDisabled();
		dialogFixture.button("ButtonNotStart").requireText("Ikke startet produksjon");

		JTableFixture tableFixture = dialogFixture.table(TableEnum.TABLEPRODUCTIONFRONT.getTableName());

		assertEquals("Startet", tableFixture.target.getColumnName(8));

		tableFixture.cell(row(0).column(1)).click();

		String content = tableFixture.cell(row(0).column(8)).value();

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
	public void testShowProductionDate() {
		dialogFixture.show(new Dimension(1000, 500));
		dialogFixture.comboBox("ComboBoxProductAreaGroup").selectItem("Alle");
		JTableFixture tableFixture = dialogFixture.table(TableEnum.TABLEPRODUCTIONFRONT.getTableName());

		assertEquals("Prod.dato", tableFixture.target.getColumnName(2));

	}

}
