package no.ugland.utransprod.gui.report.manuelt;

import static junit.framework.Assert.assertEquals;


import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.ExcelReportView;
import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.LFEnum;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.handlers.ExcelReportViewHandlerSales;
import no.ugland.utransprod.gui.util.JYearChooserFinder;
import no.ugland.utransprod.test.ManuellTest;
import no.ugland.utransprod.util.excel.ExcelReportEnum;
import no.ugland.utransprod.util.excel.ExcelUtil;

import org.fest.swing.core.ComponentFinder;
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
public class SalesReportViewTest  {
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

		ExcelReportViewHandlerSales excelReportViewHandler = new ExcelReportViewHandlerSales(
				ExcelReportEnum.SALES_REPORT);

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
	public void testShowSalesReport() throws Exception {
		ExcelUtil.setUseUniqueFileName(false);
		ComponentFinder finder = dialogFixture.robot.finder();
		JYearChooser yearChooser = finder.find(new JYearChooserFinder(
				"YearChooser"));
		yearChooser.setYear(2008);

		JComboBoxFixture comboBoxFrom = dialogFixture
				.comboBox("ComboBoxWeekFrom");
		comboBoxFrom.target.setSelectedIndex(49);

		JComboBoxFixture comboBoxTo = dialogFixture.comboBox("ComboBoxWeekTo");
		comboBoxTo.target.setSelectedIndex(49);

		JComboBoxFixture comboBoxProductArea = dialogFixture
				.comboBox("ComboBoxProductArea");
		comboBoxProductArea.target.setSelectedIndex(0);

		dialogFixture.button("ButtonShowExcel").click();

		JOptionPaneFinder.findOptionPane().withTimeout(40000).using(
				dialogFixture.robot);

		ExcelUtil excelUtil = new ExcelUtil();
		excelUtil.openExcelFileForRead("excel/Salgsrapport20085050.xls");
		boolean lineNotFound = true;
		int row = 0;

		do {
			String cellValue = excelUtil.readCell(row, 0, null);
			if (cellValue != null && cellValue.equalsIgnoreCase("Grunnlag")) {
				lineNotFound = false;
			}
			row++;
		} while (lineNotFound);

		String cellValue = excelUtil.readCell(row, 0, null);
		assertEquals("Type", cellValue);
		cellValue = excelUtil.readCell(row, 1, null);
		assertEquals("Fylke", cellValue);
		cellValue = excelUtil.readCell(row, 2, null);
		assertEquals("Selger", cellValue);
		cellValue = excelUtil.readCell(row, 3, null);
		assertEquals("Salgsdato", cellValue);
		cellValue = excelUtil.readCell(row, 4, null);
		assertEquals("Salgsuke", cellValue);
		cellValue = excelUtil.readCell(row, 5, null);
		assertEquals("Kundenr", cellValue);
		cellValue = excelUtil.readCell(row, 6, null);
		assertEquals("Navn", cellValue);
		cellValue = excelUtil.readCell(row, 7, null);
		assertEquals("Ordrenr", cellValue);
		cellValue = excelUtil.readCell(row, 8, null);
		assertEquals("Egenproduksjon", cellValue);
		cellValue = excelUtil.readCell(row, 9, null);
		assertEquals("Frakt", cellValue);
		cellValue = excelUtil.readCell(row, 10, null);
		assertEquals("Montering", cellValue);
		cellValue = excelUtil.readCell(row, 11, null);
		assertEquals("Jalinjer", cellValue);
		cellValue = excelUtil.readCell(row, 12, null);
		assertEquals("DB", cellValue);
		cellValue = excelUtil.readCell(row, 13, null);
		assertEquals("DG", cellValue);
	}

	@Test
	public void testShowSalesReportPrSalesman() throws ProTransException {
		ExcelUtil.setUseUniqueFileName(false);
		dialogFixture.show(new Dimension(320, 150));
		ComponentFinder finder = dialogFixture.robot.finder();
		JYearChooser yearChooser = finder.find(new JYearChooserFinder(
				"YearChooser"));
		yearChooser.setYear(2009);

		JComboBoxFixture comboBoxFrom = dialogFixture
				.comboBox("ComboBoxWeekFrom");
		comboBoxFrom.target.setSelectedIndex(49);

		JComboBoxFixture comboBoxTo = dialogFixture.comboBox("ComboBoxWeekTo");
		comboBoxTo.target.setSelectedIndex(49);

		JComboBoxFixture comboBoxProductArea = dialogFixture
				.comboBox("ComboBoxProductArea");
		comboBoxProductArea.target.setSelectedIndex(0);

		dialogFixture.checkBox("CheckBoxSalesman").click();

		dialogFixture.button("ButtonShowExcel").click();

		JOptionPaneFinder.findOptionPane().withTimeout(60000).using(
				dialogFixture.robot);

		ExcelUtil excelUtil = new ExcelUtil();
		excelUtil.openExcelFileForRead("excel/Salgsrapport20095050.xls");
		boolean lineNotFound = true;
		int row = 0;

		do {
			String cellValue = excelUtil.readCell(row, 0, null);
			if (cellValue != null && cellValue.equalsIgnoreCase("Tilbud")) {
				lineNotFound = false;
			}
			row++;
		} while (lineNotFound);

		String cellValue = excelUtil.readCell(row, 0, null);
		assertEquals("Selger", cellValue);
		cellValue = excelUtil.readCell(row, 1, null);
		assertEquals("Antall", cellValue);
		cellValue = excelUtil.readCell(row, 2, null);
		assertEquals("Egenproduksjon", cellValue);
		cellValue = excelUtil.readCell(row, 3, null);
		assertEquals("Frakt", cellValue);
		cellValue = excelUtil.readCell(row, 4, null);
		assertEquals("Montering", cellValue);
		cellValue = excelUtil.readCell(row, 5, null);
		assertEquals("Jalinjer", cellValue);
		cellValue = excelUtil.readCell(row, 6, null);
		assertEquals("DB", cellValue);
		cellValue = excelUtil.readCell(row, 7, null);
		assertEquals("DG", cellValue);
	}
}
