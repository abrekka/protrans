package no.ugland.utransprod.gui.report.manuelt;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

import no.ugland.utransprod.gui.ExcelReportView;
import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.LFEnum;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.handlers.ExcelReportViewHandlerSaleStatus;
import no.ugland.utransprod.test.ManuellTest;
import no.ugland.utransprod.util.Util;
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
@Category(ManuellTest.class)
public class SaleStatusReportViewTest  {
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

		ExcelReportViewHandlerSaleStatus excelReportViewHandler = new ExcelReportViewHandlerSaleStatus();

		final ExcelReportView excelReportView = new ExcelReportView(
				excelReportViewHandler, true);

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
	public void testShow() throws Exception {

		dialogFixture.checkBox("CheckBoxOrder").requireVisible();
		dialogFixture.checkBox("CheckBoxOffer").requireVisible();
		dialogFixture.comboBox("ComboBoxProductArea").requireVisible();

		JComboBoxFixture comboBoxProductArea = dialogFixture
				.comboBox("ComboBoxProductArea");
		comboBoxProductArea.target.setSelectedIndex(0);

		dialogFixture.button("ButtonShowExcel").requireVisible();
	}

	@Test
	public void testGenerateReportBothTypes() throws Exception {
		ExcelUtil.setUseUniqueFileName(false);
		dialogFixture.show();

		dialogFixture.checkBox("CheckBoxOrder").click();
		dialogFixture.checkBox("CheckBoxOffer").click();

		dialogFixture.comboBox("ComboBoxProductArea").target
				.setSelectedIndex(1);

		dialogFixture.button("ButtonShowExcel").click();

		JOptionPaneFinder.findOptionPane().withTimeout(40000).using(
				dialogFixture.robot);

		ExcelUtil excelUtil = new ExcelUtil();
		excelUtil.openExcelFileForRead("excel/Salgsstatus"
				+ Util.getCurrentYearWeekAsString() + ".xls");
	}

	@Test
	public void testGenerateReportWithoutType() throws Exception {

		dialogFixture.button("ButtonShowExcel").click();

		JOptionPaneFinder.findOptionPane().using(dialogFixture.robot)
				.requireMessage("Det må velges tilbud,ordre eller begge");
	}

	@Test
	public void testGenerateReportOrder() throws Exception {
		ExcelUtil.setUseUniqueFileName(true);

		dialogFixture.checkBox("CheckBoxOrder").click();

		dialogFixture.comboBox("ComboBoxProductArea").target
				.setSelectedIndex(0);

		dialogFixture.button("ButtonShowExcel").click();

		JOptionPaneFinder.findOptionPane().withTimeout(40000).using(
				dialogFixture.robot);
	}

	@Test
	public void testGenerateReportOffer() throws Exception {
		ExcelUtil.setUseUniqueFileName(true);

		dialogFixture.checkBox("CheckBoxOffer").click();

		dialogFixture.comboBox("ComboBoxProductArea").target
				.setSelectedIndex(0);

		dialogFixture.button("ButtonShowExcel").click();

		JOptionPaneFinder.findOptionPane().withTimeout(40000).using(
				dialogFixture.robot);
	}

	@Test
	public void testGenerateReportAll() throws Exception {
		ExcelUtil.setUseUniqueFileName(true);

		dialogFixture.checkBox("CheckBoxOrder").click();
		dialogFixture.checkBox("CheckBoxOffer").click();

		dialogFixture.button("ButtonShowExcel").click();

		JOptionPaneFinder.findOptionPane().withTimeout(40000).using(
				dialogFixture.robot);
	}
}
