package no.ugland.utransprod.gui.handlers;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.Closeable;
import no.ugland.utransprod.gui.IconEnum;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.buttons.CancelButton;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Util;
import no.ugland.utransprod.util.excel.ExcelManager;
import no.ugland.utransprod.util.excel.ExcelReportEnum;
import no.ugland.utransprod.util.excel.ExcelReportSetting;
import no.ugland.utransprod.util.excel.ExcelUtil;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.ComboBoxAdapter;
import com.jgoodies.binding.beans.PropertyConnector;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.toedter.calendar.JYearChooser;

/**
 * Hjelpeklasse for generering av excelrapporter
 * 
 * @author atle.brekka
 */
public class ExcelReportViewHandler implements Closeable {
	/**
	 * 
	 */
	protected PresentationModel presentationModel;

	/**
	 * 
	 */
	protected JYearChooser yearChooser;

	/**
	 * 
	 */
	protected JComboBox comboBoxWeekFrom;

	/**
	 * 
	 */
	protected JComboBox comboBoxWeekTo;

	/**
	 * 
	 */
	protected JComboBox comboBoxReportType;

	protected JComboBox comboBoxProductArea;

	protected ExcelReportEnum excelReportEnum;
	private Dimension windowSize;

	public ExcelReportViewHandler(ExcelReportEnum excelReportType, Dimension aWindowSize) {
		windowSize = aWindowSize;
		presentationModel = new PresentationModel(new ExcelReportSetting(excelReportType));
		excelReportEnum = excelReportType;
	}

	/**
	 * Lager årsvelger
	 * 
	 * @return årsvelger
	 */
	public JYearChooser getYearChooser() {
		JYearChooser yearChooser1 = new JYearChooser();
		PropertyConnector conn = new PropertyConnector(yearChooser1, "year",
				presentationModel.getModel(ExcelReportSetting.PROPERTY_YEAR), "value");
		conn.updateProperty2();
		yearChooser1.setName("YearChooser");
		return yearChooser1;
	}

	/**
	 * Henter comboboks for uke fra
	 * 
	 * @return comboboks med uker
	 */
	public JComboBox getComboBoxWeekFrom() {
		JComboBox comboBox = new JComboBox(new ComboBoxAdapter(Util.getWeeks(),
				presentationModel.getModel(ExcelReportSetting.PROPERTY_WEEK_FROM)));
		comboBox.setName("ComboBoxWeekFrom");
		comboBox.setSelectedItem(Util.getCurrentWeek());
		return comboBox;
	}

	public JComboBox getComboBoxWeekTo() {
		JComboBox comboBox;
		comboBox = new JComboBox(
				new ComboBoxAdapter(Util.getWeeks(), presentationModel.getModel(ExcelReportSetting.PROPERTY_WEEK_TO)));
		if (!excelReportEnum.useTo()) {// dersom til ikke skal brukes kobles
			// til og fra
			PropertyConnector conn = new PropertyConnector(
					presentationModel.getModel(ExcelReportSetting.PROPERTY_WEEK_FROM), "value",
					presentationModel.getModel(ExcelReportSetting.PROPERTY_WEEK_TO), "value");
			conn.updateProperty2();

		}
		comboBox.setName("ComboBoxWeekTo");
		return comboBox;
	}

	/**
	 * Henter comboboks med alle rapporttyper dersom ikke rapporttype er
	 * allerede satt. Dersom rapporttype er satt blir det ikke lage noe
	 * comboboks
	 * 
	 * @return comboboks med rapporttyper
	 */
	public JComboBox getComboBoxReportType() {
		if (presentationModel
				.getValue(ExcelReportSetting.PROPERTY_EXCEL_REPORT_TYPE) == ExcelReportEnum.PRODUCTIVITY_PACK) {
			return new JComboBox(new ComboBoxAdapter(ExcelReportEnum.getProductivityReports(),
					presentationModel.getModel(ExcelReportSetting.PROPERTY_EXCEL_REPORT_TYPE)));
		}
		return null;
	}

	public JComboBox getComboBoxProductArea(boolean addEmpty) {
		JComboBox comboBox = Util.createComboBoxProductArea(
				presentationModel.getModel(ExcelReportSetting.PROPERTY_PRODUCT_AREA), addEmpty);
		comboBox.setName("ComboBoxProductArea");
		return comboBox;
	}

	/**
	 * Lager knapp for å vise excelrapport
	 * 
	 * @param window
	 * @return knapp
	 */
	public JButton getButtonShowExcel(WindowInterface window) {
		JButton button = new JButton(new ShowExcelAction(window));
		button.setIcon(IconEnum.ICON_EXCEL.getIcon());
		button.setName("ButtonShowExcel");
		return button;
	}

	/**
	 * @see no.ugland.utransprod.gui.Closeable#canClose(java.lang.String,
	 *      no.ugland.utransprod.gui.WindowInterface)
	 */
	public boolean canClose(String actionString, WindowInterface window) {
		return true;
	}

	/**
	 * Lager avbrytknapp
	 * 
	 * @param window
	 * @return knapp
	 */
	public JButton getButtonCancel(WindowInterface window) {
		return new CancelButton(window, this, true);
	}

	/**
	 * Henter vindusstørrels
	 * 
	 * @return vindusstørrelse
	 */
	public Dimension getWindowSize() {
		if (windowSize != null) {
			return windowSize;
		}
		if (excelReportEnum.useFrom()) {
			if (excelReportEnum.useTo()) {
				if (presentationModel.getValue(ExcelReportSetting.PROPERTY_EXCEL_REPORT_TYPE) == null) {
					return new Dimension(320, 150);
				} else if (excelReportEnum.useProductArea()) {
					return new Dimension(350, 130);
				}
				return new Dimension(320, 110);
			}
			return new Dimension(250, 130);
		}

		return new Dimension(250, 110);
	}

	/**
	 * Henter vindustittel
	 * 
	 * @return vindustittel
	 */

	public String getWindowTitle() {
		return excelReportEnum.getReportName();
	}

	/**
	 * Viser excelrapport
	 * 
	 * @author atle.brekka
	 */
	private class ShowExcelAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * 
		 */
		private WindowInterface window;

		/**
		 * @param aWindow
		 */
		public ShowExcelAction(WindowInterface aWindow) {
			super("Excel");
			window = aWindow;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent arg0) {
			try {
				ExcelReportSetting excelReportSetting = (ExcelReportSetting) presentationModel.getBean();
				generateExcel(window, excelReportSetting);
			} catch (ProTransException e) {
				Util.showErrorDialog(window, "Feil", e.getMessage());
				e.printStackTrace();
			}
		}

	}

	public void generateExcel(WindowInterface window, ExcelReportSetting excelReportSetting) throws ProTransException {

		if (checkParameters(excelReportSetting, window)) {
			if (continueGenerateExcel(excelReportSetting, window)) {
				ExcelUtil excelUtil = new ExcelUtil();
				excelUtil.generateExcel(excelReportSetting, window);
			}
		}
	}

	private boolean continueGenerateExcel(ExcelReportSetting excelReportSetting, WindowInterface window) {
		ExcelManager excelManager = (ExcelManager) ModelUtil.getBean(excelReportEnum.getExcelManagerName());
		CheckObject checkObject = excelManager.checkExcel(excelReportSetting);

		if (checkObject != null && checkObject.getMsg().length() != 0) {
			return handleCheckObject(window, checkObject);
		}
		return true;

	}

	private boolean handleCheckObject(WindowInterface window, CheckObject checkObject) {
		boolean returnValue;
		if (checkObject.canContinue()) {
			returnValue = handleCanContinue(window, checkObject.getMsg());
		} else {
			returnValue = false;
			Util.showErrorDialog((Component) null, "Feil", checkObject.getMsg());
		}
		return returnValue;
	}

	private boolean handleCanContinue(WindowInterface window, String msg) {
		boolean doContinue = Util.showConfirmDialog(window.getComponent(), "Feil?", msg + " Vil du fortsette?");
		return doContinue;
	}

	/**
	 * Sjekker om alle parametre for rapport er satt
	 * 
	 * @param setting
	 * @param window
	 * @return true dersom alt er satt
	 */
	protected boolean checkParameters(ExcelReportSetting setting, WindowInterface window) {
		String errorString = null;
		if (excelReportEnum.useFrom() && setting.getWeekFrom() == null) {
			errorString = "Det må velges fra uke";
		} else if (excelReportEnum.useTo() && setting.getWeekTo() == null) {
			errorString = "Det må velges til uke";
		} else if (setting.getExcelReportType() == null) {
			errorString = "Det må velges rapport";
		}
		// else if (excelReportEnum.useProductArea() && setting.getProductArea()
		// == null) {
		// errorString = "Det må velges produktområde";
		// }

		if (errorString != null) {
			Util.showErrorDialog(window, "Rapportparametre", errorString);
			return false;
		}
		return true;

	}

	/**
	 * Initierer komponeter
	 * 
	 * @param window
	 */
	protected void initComponents(WindowInterface window, boolean addEmpty) {
		yearChooser = getYearChooser();
		comboBoxWeekFrom = getComboBoxWeekFrom();
		comboBoxWeekTo = getComboBoxWeekTo();
		comboBoxReportType = getComboBoxReportType();
		comboBoxProductArea = getComboBoxProductArea(addEmpty);
	}

	/**
	 * Lager vindu med utvalgskritrier
	 * 
	 * @param window
	 * @return panel
	 */
	public JPanel buildConstraintPanel(WindowInterface window, boolean addEmpty) {
		initComponents(window, addEmpty);
		FormLayout layout = new FormLayout("p,3dlu,30dlu,3dlu,p:grow,3dlu,30dlu,3dlu,p,3dlu,30dlu", "p,3dlu,p,3dlu,p");
		PanelBuilder builder = new PanelBuilder(layout);
		// PanelBuilder builder = new PanelBuilder(new FormDebugPanel(),layout);
		CellConstraints cc = new CellConstraints();

		if (excelReportEnum.useFrom()) {
			builder.addLabel("År:", cc.xy(1, 1));
			builder.add(yearChooser, cc.xy(3, 1));
			if (excelReportEnum.useTo()) {
				builder.addLabel("Fra uke:", cc.xy(5, 1));
				builder.addLabel("Til uke:", cc.xy(9, 1));
				builder.add(comboBoxWeekTo, cc.xy(11, 1));
			} else {
				builder.addLabel("Uke:", cc.xy(5, 1));
			}
			builder.add(comboBoxWeekFrom, cc.xy(7, 1));
		}

		if (comboBoxReportType != null) {
			builder.addLabel("Rapport:", cc.xyw(1, 3, 3));
			builder.add(comboBoxReportType, cc.xyw(5, 3, 7));
		}
		if (excelReportEnum.useProductArea()) {
			builder.addLabel("Produktområde:", cc.xyw(1, 5, 5));
			builder.add(comboBoxProductArea, cc.xyw(6, 5, 6));
		}

		return builder.getPanel();
	}
}
