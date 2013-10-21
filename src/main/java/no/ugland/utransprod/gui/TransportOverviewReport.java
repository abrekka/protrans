package no.ugland.utransprod.gui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import no.ugland.utransprod.gui.handlers.TransportOverviewReportHandler;
import no.ugland.utransprod.util.InternalFrameBuilder;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.toedter.calendar.JYearChooser;

/**
 * Viser oversiktsrapport for transport
 * 
 * @author atle.brekka
 * 
 */
public class TransportOverviewReport implements Viewer {
	/**
	 * 
	 */
	private JYearChooser yearChooser;

	/**
	 * 
	 */
	private JComboBox comboBoxFromWeek;

	/**
	 * 
	 */
	private JComboBox comboBoxToWeek;

	/**
	 * 
	 */
	private TransportOverviewReportHandler viewHandler;

	/**
	 * 
	 */
	private JButton buttonPrint;

	/**
	 * 
	 */
	private JButton buttonCancel;
	private JComboBox comboBoxProductAreaGroup;

	/**
	 * @param handler
	 */
	public TransportOverviewReport(TransportOverviewReportHandler handler) {
		viewHandler = handler;
	}

	/**
	 * Initierer vinduskomponenter
	 * 
	 * @param window
	 */
	private void initComponents(WindowInterface window) {
		yearChooser = viewHandler.getYearChooser();
		comboBoxFromWeek = viewHandler.getComboBoxFromWeek();
		comboBoxToWeek = viewHandler.getComboBoxToWeek();
		buttonPrint = viewHandler.getButtonPrint(window);
		buttonCancel = viewHandler.getButtonCancel(window);
		comboBoxProductAreaGroup=viewHandler.getComboBoxProductAreaGroup();
	}

	/**
	 * Bygger panel
	 * 
	 * @param window
	 * @return panel
	 */
	public JPanel buildPanel(WindowInterface window) {
		initComponents(window);
		FormLayout layout = new FormLayout(
				"10dlu,p,3dlu,30dlu,3dlu,p,3dlu,p,3dlu,p,3dlu,p,10dlu",
				"10dlu,p,3dlu,p,3dlu,p,5dlu");
		PanelBuilder builder = new PanelBuilder(layout);
		//PanelBuilder builder = new PanelBuilder(layout,new FormDebugPanel());
		CellConstraints cc = new CellConstraints();

		builder.addLabel("År:", cc.xy(2, 2));
		builder.add(yearChooser, cc.xy(4, 2));
		builder.addLabel("Fra uke:", cc.xy(6, 2));
		builder.add(comboBoxFromWeek, cc.xy(8, 2));
		builder.addLabel("Til uke:", cc.xy(10, 2));
		builder.add(comboBoxToWeek, cc.xy(12, 2));
		builder.addLabel("Produktområde:",cc.xyw(2, 4,5));
		builder.add(comboBoxProductAreaGroup,cc.xyw(8, 4,5));
		builder.add(ButtonBarFactory
				.buildCenteredBar(buttonPrint, buttonCancel), cc.xyw(2, 6, 11));

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
	 * @see no.ugland.utransprod.gui.Viewer#cleanUp()
	 */
	public void cleanUp() {
	}

	/**
	 * @see no.ugland.utransprod.gui.Viewer#getTitle()
	 */
	public String getTitle() {
		return "Transportoversikt";
	}

	/**
	 * @see no.ugland.utransprod.gui.Viewer#initWindow()
	 */
	public void initWindow() {
	}

	/**
	 * @see no.ugland.utransprod.gui.Viewer#useDispose()
	 */
	public boolean useDispose() {
		return viewHandler.getDisposeOnClose();
	}
}
