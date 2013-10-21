package no.ugland.utransprod.gui.report.manuelt;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

import no.ugland.utransprod.gui.ExcelReportView;
import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.LFEnum;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.handlers.ExcelReportViewHandlerDeviation;
import no.ugland.utransprod.test.ManuellTest;
import no.ugland.utransprod.util.excel.ExcelReportEnum;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.finder.JOptionPaneFinder;
import org.fest.swing.fixture.DialogFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.birosoft.liquid.LiquidLookAndFeel;
@Category(ManuellTest.class)
public class SummaryReportDeviationTest  {
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

		ExcelReportViewHandlerDeviation excelReportViewHandler = new ExcelReportViewHandlerDeviation(
				"Avvik - Skjema gjennomgåelse", false,
				ExcelReportEnum.DEVIATION_SUMMARY);

		final ExcelReportView excelReportView = new ExcelReportView(
				excelReportViewHandler, false);

		JDialog dialog = GuiActionRunner.execute(new GuiQuery<JDialog>() {
			protected JDialog executeInEDT() {
				JDialog dialog = new JDialog();
				WindowInterface window = new JDialogAdapter(dialog);
				dialog.add(excelReportView.buildPanel(window));
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
	public void testShowReport() {
		dialogFixture.comboBox("ComboBoxMonth").selectItem("Januar");
		dialogFixture.comboBox("ComboBoxProductArea").selectItem(
				"Garasje villa");
		dialogFixture.button("ButtonShowExcel").click();

		JOptionPaneFinder.findOptionPane().withTimeout(60000).using(
				dialogFixture.robot);
	}
}
