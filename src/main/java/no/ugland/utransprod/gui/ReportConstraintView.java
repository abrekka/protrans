package no.ugland.utransprod.gui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import no.ugland.utransprod.gui.handlers.ReportConstraintViewHandler;
import no.ugland.utransprod.util.InternalFrameBuilder;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.toedter.calendar.JYearChooser;

/**
 * Brukes til å sette rapportegenskaper som fra og til periode
 * 
 * @author atle.brekka
 * 
 */
public class ReportConstraintView implements Viewer {
	/**
	 * 
	 */
	private ReportConstraintViewHandler viewHandler;

	/**
	 * 
	 */
	private JYearChooser yearChooserFrom;

	/**
	 * 
	 */
	private JYearChooser yearChooserTo;

	/**
	 * 
	 */
	private JComboBox comboBoxWeekFrom;

	/**
	 * 
	 */
	private JComboBox comboBoxWeekTo;

	/**
	 * 
	 */
	private JRadioButton radioButtonTransported;

	/**
	 * 
	 */
	private JRadioButton radioButtonTransportPlanned;

	/**
	 * 
	 */
	private JRadioButton radioButtonAll;

	/**
	 * 
	 */
	private JButton buttonExcel;

	/**
	 * 
	 */
	private JButton buttonCancel;

	/**
	 * 
	 */
	private JButton buttonShowReport;
	private JComboBox comboBoxProductArea;


	/**
	 * @param handler
	 */
	public ReportConstraintView(ReportConstraintViewHandler handler) {
		this.viewHandler = handler;
	}

	/**
	 * Initierer vinduskomponenter
	 * 
	 * @param window
	 */
	private void initComponents(WindowInterface window) {
		yearChooserFrom = viewHandler.getYearChooserFrom();
		yearChooserTo = viewHandler.getYearChooserTo();

		comboBoxWeekFrom = viewHandler.getComboBoxWeekFrom();
		comboBoxWeekTo = viewHandler.getComboBoxWeekTo();

		radioButtonTransported = viewHandler.getRadioButtonTransported();
		radioButtonTransportPlanned = viewHandler
				.getRadioButtonTransportPlanned();
		radioButtonAll = viewHandler.getRadioButtonAll();

		buttonCancel = viewHandler.getButtonCancel(window);
		buttonExcel = viewHandler.getButtonExcel(window);
		buttonShowReport = viewHandler.getButtonShowReport(window);
		comboBoxProductArea = viewHandler.getComboBoxProductArea();
	}

	/**
	 * Lager panel
	 * 
	 * @param window
	 * @return panel
	 */
	public JPanel buildPanel(WindowInterface window) {
		window.setName(viewHandler.getWindowTitle());
		initComponents(window);
		FormLayout layout = new FormLayout("10dlu,center:310dlu:grow,10dlu",
				"10dlu,p,3dlu,120dlu:grow,3dlu,p,5dlu");
		PanelBuilder builder = new PanelBuilder(layout);
		//PanelBuilder builder = new PanelBuilder(new FormDebugPanel(),layout);
		CellConstraints cc = new CellConstraints();

		builder.add(buildParamPanel(), cc.xy(2, 2));

		builder.add(viewHandler.buildTabbedPane(window), cc.xy(2, 4));

		builder.add(ButtonBarFactory
				.buildCenteredBar(buttonExcel, buttonCancel), cc.xyw(2, 6, 2));

		return builder.getPanel();
	}
	
	
	
	

	/**
	 * Bygger parameterpanel
	 * 
	 * @return panel
	 */
	private JPanel buildParamPanel() {
		FormLayout layout = new FormLayout("p,3dlu,30dlu,3dlu,p,3dlu,p,3dlu,p",
				"p,3dlu,p,3dlu,p,3dlu,p");
		PanelBuilder builder = new PanelBuilder(layout);
		CellConstraints cc = new CellConstraints();

		builder.addLabel("Fra år:", cc.xy(1, 1));
		builder.add(yearChooserFrom, cc.xy(3, 1));
		builder.addLabel("Fra uke:", cc.xy(5, 1));
		builder.add(comboBoxWeekFrom, cc.xy(7, 1));
		builder.addLabel("Til år:", cc.xy(1, 3));
		builder.add(yearChooserTo, cc.xy(3, 3));
		builder.addLabel("Til uke:", cc.xy(5, 3));
		builder.add(comboBoxWeekTo, cc.xy(7, 3));
		builder.addLabel("Produktområde:",cc.xyw(1, 5,3));
		builder.add(comboBoxProductArea,cc.xyw(5,5,3));

		builder.add(radioButtonTransported, cc.xy(9, 1));
		builder.add(radioButtonTransportPlanned, cc.xy(9, 3));
		builder.add(radioButtonAll, cc.xy(9, 5));
		builder.add(ButtonBarFactory.buildCenteredBar(buttonShowReport), cc
				.xyw(1, 7, 9));

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
		return viewHandler.getWindowTitle();
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
