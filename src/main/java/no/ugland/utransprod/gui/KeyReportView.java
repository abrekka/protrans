package no.ugland.utransprod.gui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import no.ugland.utransprod.gui.handlers.KeyReportViewHandler;
import no.ugland.utransprod.util.InternalFrameBuilder;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.toedter.calendar.JYearChooser;

/**
 * Brukes til å vise dialog for å sette parametre i forbindelse med
 * nøkkeltallsrapporter
 * 
 * @author atle.brekka
 * 
 */
public class KeyReportView implements Viewer {
	/**
	 * 
	 */
	private JYearChooser yearChooser;

	/**
	 * 
	 */
	private JComboBox comboBoxWeek;

	/**
	 * 
	 */
	private JButton buttonShowReport;

	/**
	 * 
	 */
	private JButton buttonCancel;

	/**
	 * 
	 */
	private KeyReportViewHandler viewHandler;

	/**
	 * 
	 */
	private JComboBox comboBoxReportType;

	/**
	 * 
	 */
	// private JRadioButton radioButtonVilla;
	private JComboBox comboBoxProductArea;

	/**
	 * 
	 */
	// private JRadioButton radioButtonRow;
	/**
	 * @param handler
	 */
	public KeyReportView(KeyReportViewHandler handler) {
		viewHandler = handler;
	}

	/**
	 * Initierer vinduskomponenter
	 * 
	 * @param window
	 */
	private void initComponents(WindowInterface window) {
		yearChooser = viewHandler.getYearChooser();
		comboBoxWeek = viewHandler.getComboBoxWeek();
		buttonShowReport = viewHandler.getButtonShowReport(window);
		buttonCancel = viewHandler.getButtonCancel(window);
		comboBoxReportType = viewHandler.getComboBoxReportType();
		// radioButtonVilla = viewHandler.getRadioButtonVilla();
		// radioButtonRow = viewHandler.getRadioButtonRow();
		comboBoxProductArea = viewHandler.getComboBoxProductArea();
	}

	/**
	 * @param window
	 * @return panel
	 */
	public JPanel buildPanel(WindowInterface window) {
		window.setName(viewHandler.getWindowTitle());
		initComponents(window);
		FormLayout layout = new FormLayout(
				"10dlu,p,3dlu,30dlu,3dlu,p,3dlu,40dlu,3dlu,60dlu,3dlu,p,10dlu",
				"10dlu,p,3dlu,p,3dlu,p,5dlu,p");
		PanelBuilder builder = new PanelBuilder(layout);
		// PanelBuilder builder = new PanelBuilder(new FormDebugPanel(),layout);
		CellConstraints cc = new CellConstraints();

		builder.addLabel("Rapporttype:", cc.xyw(2, 2, 3));
		builder.add(comboBoxReportType, cc.xyw(6, 2, 6));
		builder.addLabel("Produktområde:", cc.xyw(2, 4, 5));
		builder.add(comboBoxProductArea, cc.xyw(8, 4, 4));
		builder.addLabel("År:", cc.xy(2, 6));
		builder.add(yearChooser, cc.xyw(4, 6, 1));
		builder.addLabel("Uke:", cc.xy(6, 6));
		builder.add(comboBoxWeek, cc.xy(8, 6));

		// builder.add(radioButtonRow, cc.xy(11, 4));
		builder.add(ButtonBarFactory.buildCenteredBar(buttonShowReport,
				buttonCancel), cc.xyw(2, 8, 11));
		return builder.getPanel();
	}

	/**
	 * @see no.ugland.utransprod.gui.Viewer#buildWindow()
	 */
	public WindowInterface buildWindow() {
		WindowInterface window = InternalFrameBuilder.buildInternalFrame(
				viewHandler.getWindowTitle(), viewHandler.getWindowSize(),
				false);
		window.add(buildPanel(window), BorderLayout.CENTER);

		return window;
	}

	/**
	 * @see no.ugland.utransprod.gui.Viewer#getTitle()
	 */
	public String getTitle() {
		return "Nøkkeltallrapport :: parametre";
	}

	/**
	 * @see no.ugland.utransprod.gui.Viewer#initWindow()
	 */
	public void initWindow() {
	}

	/**
	 * @see no.ugland.utransprod.gui.Viewer#cleanUp()
	 */
	public void cleanUp() {
	}

	/**
	 * @see no.ugland.utransprod.gui.Viewer#useDispose()
	 */
	public boolean useDispose() {
		return viewHandler.getDisposeOnClose();
	}
}
