package no.ugland.utransprod.gui.handlers;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.ListModel;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.Closeable;
import no.ugland.utransprod.gui.IconEnum;
import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.buttons.CancelButton;
import no.ugland.utransprod.gui.model.ReportEnum;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.service.ProductAreaManager;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Threadable;
import no.ugland.utransprod.util.Util;
import no.ugland.utransprod.util.report.NokkelReport;
import no.ugland.utransprod.util.report.ReportViewer;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.adapter.ComboBoxAdapter;
import com.jgoodies.binding.beans.Model;
import com.jgoodies.binding.beans.PropertyConnector;
import com.toedter.calendar.JYearChooser;

/**
 * Håndterer nøkkelrapporter
 * 
 * @author atle.brekka
 */
public class KeyReportViewHandler implements Closeable {
	private PresentationModel presentationModel;

	KeyReportSetting keyReportSetting;

	private boolean disposeOnClose = true;

	private static List<String> productAreaList;

	/**
	 * @param aDatabaseManager
	 */
	public KeyReportViewHandler() {
		keyReportSetting = new KeyReportSetting();
		keyReportSetting.setWeek(Util.getCurrentWeek());
		presentationModel = new PresentationModel(keyReportSetting);
		productAreaList = new ArrayList<String>();
		initProductAreaList();
	}

	/**
	 * Initierer liste med produktområder
	 */
	private void initProductAreaList() {
		if (productAreaList.size() == 0) {
			ProductAreaManager productAreaManager = (ProductAreaManager) ModelUtil
					.getBean("productAreaManager");
			List<ProductArea> productAreas = productAreaManager.findAll();
			if (productAreas != null) {
				for (ProductArea productArea : productAreas) {
					productAreaList.add(productArea.getProductArea());
				}

			}
		}
	}

	/**
	 * Lager årvelger
	 * 
	 * @return årvelger
	 */
	public JYearChooser getYearChooser() {
		JYearChooser yearChooser = new JYearChooser();
		PropertyConnector conn = new PropertyConnector(yearChooser, "year",
				presentationModel.getModel(KeyReportSetting.PROPERTY_YEAR),
				"value");
		conn.updateProperty2();
		return yearChooser;
	}

	/**
	 * Lager komboboks for uke
	 * 
	 * @return komboboks
	 */
	public JComboBox getComboBoxWeek() {
		JComboBox comboBox = new JComboBox(new ComboBoxAdapter(Util.getWeeks(),
				presentationModel.getModel(KeyReportSetting.PROPERTY_WEEK)));
		comboBox.setName("ComboBoxWeek");
		return comboBox;
	}

	/**
	 * Lager komboboks for rapporttyper
	 * 
	 * @return komboboks
	 */
	public JComboBox getComboBoxReportType() {
		JComboBox comboBox = new JComboBox(new ComboBoxAdapter(ReportEnum
				.getKeyReports(), presentationModel
				.getModel(KeyReportSetting.PROPERTY_REPORT_TYPE)));
		comboBox.setName("ComboBoxReportType");
		return comboBox;
	}

	/**
	 * Lager komboboks for produktområde
	 * 
	 * @return komboboks
	 */
	public JComboBox getComboBoxProductArea() {
		JComboBox comboBox = new JComboBox(new ComboBoxAdapter(productAreaList,
				presentationModel
						.getModel(KeyReportSetting.PROPERTY_PRODUCT_AREA)));
		comboBox.setName("ComboBoxProductArea");
		return comboBox;
	}

	/**
	 * Lager knapp for å vise rapport
	 * 
	 * @param window
	 * @return knapp
	 */
	public JButton getButtonShowReport(WindowInterface window) {
		JButton button = new JButton(new ShowReportAction(window));
		button.setIcon(IconEnum.ICON_PRINT.getIcon());
		button.setName("ButtonShowReport");
		return button;
	}

	/**
	 * Lager avbrytknapp
	 * 
	 * @param window
	 * @return knapp
	 */
	public JButton getButtonCancel(WindowInterface window) {
		return new CancelButton(window, this, disposeOnClose);
	}

	/**
	 * Holder på rapportsettinger
	 * 
	 * @author atle.brekka
	 */
	public class KeyReportSetting extends Model {
		private static final long serialVersionUID = 1L;

		public static final String PROPERTY_YEAR = "year";

		public static final String PROPERTY_WEEK = "week";

		public static final String PROPERTY_REPORT_TYPE = "reportType";

		public static final String PROPERTY_PRODUCT_AREA = "productArea";

		private Integer year;

		private Integer week;

		private ReportEnum reportType;

		private String productArea;

		/**
		 * Henter rapporttype
		 * 
		 * @return rapporttype
		 */
		public ReportEnum getReportType() {
			return reportType;
		}

		/**
		 * Setter rapporttype
		 * 
		 * @param reportType
		 */
		public void setReportType(ReportEnum reportType) {
			ReportEnum oldType = getReportType();
			this.reportType = reportType;
			firePropertyChange(PROPERTY_REPORT_TYPE, oldType, reportType);
		}

		/**
		 * @return produktområde
		 */
		public String getProductArea() {
			return productArea;
		}

		/**
		 * @param productArea
		 */
		public void setProductArea(String productArea) {
			String oldArea = getProductArea();
			this.productArea = productArea;
			firePropertyChange(PROPERTY_PRODUCT_AREA, oldArea, productArea);
		}

		/**
		 * Henter uke
		 * 
		 * @return uke
		 */
		public Integer getWeek() {
			return week;
		}

		/**
		 * Setter uke
		 * 
		 * @param week
		 */
		public void setWeek(Integer week) {
			Integer oldWeek = getWeek();
			this.week = week;
			firePropertyChange(PROPERTY_WEEK, oldWeek, week);
		}

		/**
		 * Henter år
		 * 
		 * @return år
		 */
		public Integer getYear() {
			return year;
		}

		/**
		 * Setter år
		 * 
		 * @param year
		 */
		public void setYear(Integer year) {
			Integer oldYear = getYear();
			this.year = year;
			firePropertyChange(PROPERTY_YEAR, oldYear, year);
		}
	}

	/**
	 * Viser rapport
	 * 
	 * @author atle.brekka
	 */
	private class ShowReportAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		private WindowInterface window;

		/**
		 * @param aWindow
		 */
		public ShowReportAction(WindowInterface aWindow) {
			super("Rapport");
			window = aWindow;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent arg0) {
			if (keyReportSetting.getReportType() == null) {
				Util.showErrorDialog(window, "Ikke valgt rapport",
						"Det må velges en rapporttype!");
				return;
			}

			if (keyReportSetting.getProductArea() == null) {
				Util.showErrorDialog(window, "Ikke valgt produktområde",
						"Det må velges et produktområde!");
				return;
			}
			window
					.setSize(new Dimension((int) window.getSize().getWidth(),
							250));
			Util.runInThreadWheel(window.getRootPane(), new Printer(window),
					null);

		}
	}

	/**
	 * Henter vindustittel
	 * 
	 * @return tittel
	 */
	public String getWindowTitle() {
		return "Nøkkeltallrapport";
	}

	/**
	 * Genererer rapport
	 * 
	 * @author atle.brekka
	 */
	private class Printer implements Threadable {
		private WindowInterface owner;

		/**
		 * @param aOwner
		 */
		public Printer(WindowInterface aOwner) {
			owner = aOwner;
		}

		/**
		 * @see no.ugland.utransprod.util.Threadable#doWhenFinished(java.lang.Object)
		 */
		public void doWhenFinished(Object object) {
			owner.setSize(getWindowSize());
		}

		public Object doWork(Object[] params, JLabel labelInfo) {
			labelInfo.setText("Genererer rapport...");
			ReportViewer reportViewer = new ReportViewer(keyReportSetting
					.getReportType().getReportName());
			WindowInterface window;
			window = new JDialogAdapter(Util.getDialog(owner, keyReportSetting
					.getReportType().getReportName(), false));

			window.add(reportViewer.buildPanel(window));

			try {

				NokkelReport nokkelReport = new NokkelReport(keyReportSetting
						.getYear(), keyReportSetting.getWeek(),
						keyReportSetting.getProductArea());
				List<NokkelReport> reportList = new ArrayList<NokkelReport>();
				reportList.add(nokkelReport);
				reportViewer.generateProtransReportFromBean(reportList,
						getWindowTitle(), keyReportSetting.getReportType(),
						null, keyReportSetting.getReportType()
								.getReportFileName());
				window.pack();
				Util.locateOnScreenCenter(window);
				window.setVisible(true);
			} catch (ProTransException e) {
				e.printStackTrace();
			}

			return null;
		}

		/**
		 * @see no.ugland.utransprod.util.Threadable#enableComponents(boolean)
		 */
		public void enableComponents(boolean enable) {
		}

	}

	/**
	 * Henter vindusstørrelse
	 * 
	 * @return vindusstørrelse
	 */
	public Dimension getWindowSize() {
		return new Dimension(330, 150);
	}

	/**
	 * @see no.ugland.utransprod.gui.Closeable#canClose(java.lang.String,
	 *      no.ugland.utransprod.gui.WindowInterface)
	 */
	public boolean canClose(String actionString, WindowInterface window) {
		return true;
	}

	/**
	 * Tabellmodell for nøkkelrapport. Dette er bare en tom modell for å vise
	 * rapport
	 * 
	 * @author atle.brekka
	 */
	public final class KeyReportTableModel extends AbstractTableAdapter {
		private static final long serialVersionUID = 1L;

		/**
		 * @param listModel
		 */
		public KeyReportTableModel(ListModel listModel) {
			super(listModel, new String[] { "TMP" });
		}

		/**
		 * @see javax.swing.table.TableModel#getValueAt(int, int)
		 */
		public Object getValueAt(int row, int arg1) {
			return getRow(row);
		}

	}

	/**
	 * Sjekker om dialog skal kjøre dispose
	 * 
	 * @return true dersom dispose
	 */
	public boolean getDisposeOnClose() {
		return disposeOnClose;
	}
}
