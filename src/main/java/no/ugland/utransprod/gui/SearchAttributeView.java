package no.ugland.utransprod.gui;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

import no.ugland.utransprod.dao.hibernate.QuerySettings;
import no.ugland.utransprod.gui.handlers.SearchAttributeViewHandler;

import org.jdesktop.swingx.JXTreeTable;

import com.jgoodies.binding.beans.PropertyConnector;
import com.jgoodies.forms.builder.ButtonStackBuilder;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JYearChooser;

/**
 * Vindu som brukes til å søke opp ordre med gitte kriterier for attributter
 * 
 * @author atle.brekka
 * 
 */
public class SearchAttributeView {
	/**
	 * 
	 */
	private JDateChooser dateChooserFrom;

	/**
	 * 
	 */
	private JDateChooser dateChooserTo;

	/**
	 * 
	 */
	private JXTreeTable treeTableArticles;

	/**
	 * 
	 */
	private JXTreeTable treeTableChosen;

	/**
	 * 
	 */
	private JButton buttonSelect;

	/**
	 * 
	 */
	private JButton buttonDeselect;

	/**
	 * 
	 */
	private JButton buttonSearch;

	/**
	 * 
	 */
	private JButton buttonCancel;

	/**
	 * 
	 */
	private SearchAttributeViewHandler viewHandler;

	/**
	 * 
	 */
	private JRadioButton radioButtonInvoiceDate;

	/**
	 * 
	 */
	private JRadioButton radioButtonOrderDate;

	/**
	 * 
	 */
	private JRadioButton radioButtonTransportWeek;

	/**
	 * 
	 */
	private JRadioButton radioButtonAnd;

	/**
	 * 
	 */
	private JRadioButton radioButtonOr;

	/**
	 * 
	 */
	private JPanel panelDates;

	/**
	 * 
	 */
	private JPanel panelWeeks;

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
	private JComboBox comboBoxWeeksFrom;

	/**
	 * 
	 */
	private JComboBox comboBoxWeeksTo;

	/**
	 * 
	 */
	private JButton buttonClear;

	/**
	 * @param handler
	 */
	public SearchAttributeView(SearchAttributeViewHandler handler) {
		viewHandler = handler;
	}

	/**
	 * Initierer vinduskomponenter
	 * 
	 * @param window
	 */
	private void initComponents(WindowInterface window) {
		window.setName("SearchAttributeView");
		dateChooserFrom = viewHandler.getDateChooserFrom();
		dateChooserTo = viewHandler.getDateChooserTo();

		treeTableArticles = viewHandler.getTreeTableArticles();
		treeTableChosen = viewHandler.getTreeTableChosen();
		buttonSelect = viewHandler.getButtonSelect();
		buttonDeselect = viewHandler.getButtonDeselect();
		buttonSearch = viewHandler.getButtonSearch(window);
		buttonCancel = viewHandler.getButtonCancel(window);
		buttonClear = viewHandler.getButtonClear();
		viewHandler.initEventHandling();

		radioButtonInvoiceDate = viewHandler.getRadioButtonInvoiceDate();
		radioButtonOrderDate = viewHandler.getRadioButtonOrderDate();
		radioButtonTransportWeek = viewHandler.getRadioButtonTransportWeek();
		radioButtonOr = viewHandler.getRadioButtonOr();
		radioButtonAnd = viewHandler.getRadioButtonAnd();

		yearChooserFrom = viewHandler.getYearChooserFrom();
		yearChooserTo = viewHandler.getYearChooserTo();
		comboBoxWeeksFrom = viewHandler.getComboBoxWeeksFrom();
		comboBoxWeeksTo = viewHandler.getComboBoxWeeksTo();

		panelDates = buildPanelDates();
		panelDates.setVisible(false);
		panelWeeks = buildPanelWeeks();
		panelWeeks.setVisible(false);
	}

	/**
	 * Bygger panel for datoutvalg
	 * 
	 * @return panel
	 */
	public JPanel buildPanelDates() {
		FormLayout layout = new FormLayout("p,3dlu,p", "p,3dlu,p");
		PanelBuilder builder = new PanelBuilder(layout);
		CellConstraints cc = new CellConstraints();

		builder.addLabel("Fra dato:", cc.xy(1, 1));
		builder.add(dateChooserFrom, cc.xy(3, 1));
		builder.addLabel("Til dato:", cc.xy(1, 3));
		builder.add(dateChooserTo, cc.xy(3, 3));

		return builder.getPanel();
	}

	/**
	 * Bygger panel for ukeutvalg
	 * 
	 * @return panel
	 */
	public JPanel buildPanelWeeks() {
		FormLayout layout = new FormLayout("p,3dlu,30dlu,3dlu,p,3dlu,p",
				"p,3dlu,p");
		PanelBuilder builder = new PanelBuilder(layout);
		CellConstraints cc = new CellConstraints();

		builder.addLabel("Fra år:", cc.xy(1, 1));
		builder.add(yearChooserFrom, cc.xy(3, 1));
		builder.addLabel("Fra uke:", cc.xy(5, 1));
		builder.add(comboBoxWeeksFrom, cc.xy(7, 1));

		builder.addLabel("Til år:", cc.xy(1, 3));
		builder.add(yearChooserTo, cc.xy(3, 3));
		builder.addLabel("Til uke:", cc.xy(5, 3));
		builder.add(comboBoxWeeksTo, cc.xy(7, 3));

		return builder.getPanel();
	}

	/**
	 * Bygger panel
	 * 
	 * @param window
	 * @return panel
	 */
	public JComponent buildPanel(WindowInterface window) {
		initComponents(window);
		initEventHandling();

		FormLayout layout = new FormLayout(
				"10dlu,p,3dlu,60dlu,3dlu,100dlu,3dlu,p,3dlu,130dlu,10dlu",
				"10dlu,p,3dlu,p,3dlu,50dlu,3dlu,p,3dlu,100dlu:grow,5dlu,p:grow");
		// PanelBuilder builder = new PanelBuilder(new FormDebugPanel(),layout);
		PanelBuilder builder = new PanelBuilder(layout);
		CellConstraints cc = new CellConstraints();
		builder.add(radioButtonInvoiceDate, cc.xy(2, 2));
		builder.add(radioButtonOrderDate, cc.xy(4, 2));
		builder.add(radioButtonTransportWeek, cc.xy(2, 4));
		builder.add(panelDates, cc.xyw(2, 6, 3));
		builder.add(panelWeeks, cc.xyw(2, 6, 3));

		builder.add(radioButtonAnd, cc.xy(2, 8));
		builder.add(radioButtonOr, cc.xy(4, 8));

		builder.addLabel("Artikler:", cc.xy(6, 2));
		builder.add(new JScrollPane(treeTableArticles), cc.xywh(6, 4, 1, 7));
		builder.add(buildButtonPanel(), cc.xywh(8, 4, 1, 4));
		builder.addLabel("Utvalg:", cc.xy(10, 2));
		builder.add(new JScrollPane(treeTableChosen), cc.xywh(10, 4, 1, 7));
		builder.add(ButtonBarFactory.buildCenteredBar(buttonSearch,
				buttonClear, buttonCancel), cc.xyw(2, 12, 9));
		return builder.getPanel();
	}

	/**
	 * Bygger knappepanel
	 * 
	 * @return panel
	 */
	private JPanel buildButtonPanel() {
		ButtonStackBuilder builder = new ButtonStackBuilder();
		builder.addGridded(buttonSelect);
		builder.addRelatedGap();
		builder.addGridded(buttonDeselect);
		return builder.getPanel();
	}

	/**
	 * Initierer hendelsehåndtering
	 */
	private void initEventHandling() {
		PropertyConnector connDates = new PropertyConnector(panelDates,
				"visible", viewHandler.getPresentationModel().getModel(
						QuerySettings.PROPERTY_USE_DATES), "value");
		connDates.updateProperty1();

		PropertyConnector connWeeks = new PropertyConnector(panelWeeks,
				"visible", viewHandler.getPresentationModel().getModel(
						QuerySettings.PROPERTY_USE_WEEKS), "value");
		connWeeks.updateProperty1();
	}

}
