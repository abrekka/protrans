package no.ugland.utransprod.gui.report.manuelt;

import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

import no.ugland.utransprod.gui.ExcelReportView;
import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.LFEnum;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.handlers.ExcelReportViewHandler;
import no.ugland.utransprod.gui.util.JYearChooserFinder;
import no.ugland.utransprod.test.ManuellTest;
import no.ugland.utransprod.util.excel.ExcelReportEnum;
import no.ugland.utransprod.util.excel.ExcelUtil;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.finder.JOptionPaneFinder;
import org.fest.swing.fixture.DialogFixture;
import org.fest.swing.fixture.JComboBoxFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.birosoft.liquid.LiquidLookAndFeel;
import com.toedter.calendar.JYearChooser;
@Category(ManuellTest.class)
public class TakstolOwnOrderReportViewTest  {
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

		ExcelReportViewHandler excelReportViewHandler = new ExcelReportViewHandler(
				ExcelReportEnum.TAKSTOL_OWN_ORDER_REPORT, new Dimension(320,
						130));

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
	public void testShowReport() throws Exception {

		JYearChooser yearChooser = dialogFixture.robot.finder().find(
				new JYearChooserFinder("YearChooser"));
		yearChooser.setYear(2009);

		JComboBoxFixture comboBoxFrom = dialogFixture
				.comboBox("ComboBoxWeekFrom");
		comboBoxFrom.target.setSelectedIndex(10);

		JComboBoxFixture comboBoxTo = dialogFixture.comboBox("ComboBoxWeekTo");
		comboBoxTo.target.setSelectedIndex(10);

		dialogFixture.button("ButtonShowExcel").click();

		JOptionPaneFinder.findOptionPane().withTimeout(140000).using(
				dialogFixture.robot);

		ExcelUtil excelUtil = new ExcelUtil();
	}

}
