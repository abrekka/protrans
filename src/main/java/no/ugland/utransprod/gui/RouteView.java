package no.ugland.utransprod.gui;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

import no.ugland.utransprod.gui.handlers.RouteViewHandler;
import no.ugland.utransprod.gui.model.ColorEnum;
import no.ugland.utransprod.util.InternalFrameBuilder;

import org.jdesktop.swingx.JXTable;

import com.google.inject.Inject;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.debug.FormDebugPanel;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.toedter.calendar.JYearChooser;

/**
 * Klasse som håndterer visning av transportruter for gitt år og uke
 * 
 * @author atle.brekka
 */
public class RouteView implements Viewer {
	private JYearChooser yearChooser;

	private JComboBox comboBoxWeeks;

	private JButton buttonCancel;

	private JButton buttonSave;

	private JButton buttonRefresh;

	private RouteViewHandler viewHandler;

	private JButton buttonSearchOrder;

	private JLabel labelSearchResult;

	private JButton buttonReport;

	private JXTable tablePostShipment;

	private JButton buttonAddPostShipment;

	private JButton buttonDeletePostShipment;

	private JButton buttonExcel;

	private JLabel labelOrderCount;

	private JLabel labelValue;

	private JCheckBox checkBoxFilterSent;

	private JLabel labelBudget;

	private JComboBox comboBoxProductAreaGroup;
	private JCheckBox checkBoxListView;
	private JTextField textFieldGreen;
	private JTextField textFieldYellow;
	private JTextField textFieldRed;
	private JTextField textFieldGrey;

	/**
	 * @param handler
	 */
	@Inject
	public RouteView(RouteViewHandler handler) {
		viewHandler = handler;
	}

	/**
	 * Initierer komponenter
	 * 
	 * @param window
	 */
	private void initComponents(WindowInterface window) {
		textFieldGreen=new JTextField("klar");
		textFieldGreen.setHorizontalAlignment(JTextField.CENTER);
		textFieldGreen.setBackground(ColorEnum.GREEN.getColor());
		
		textFieldYellow=new JTextField("pakking startet");
		textFieldYellow.setHorizontalAlignment(JTextField.CENTER);
		textFieldYellow.setBackground(ColorEnum.YELLOW.getColor());
		
		textFieldRed=new JTextField("ikke sendt");
		textFieldRed.setHorizontalAlignment(JTextField.CENTER);
		textFieldRed.setBackground(ColorEnum.RED.getColor());
		
		textFieldGrey=new JTextField("sanns. 90%");
		textFieldGrey.setHorizontalAlignment(JTextField.CENTER);
		textFieldGrey.setBackground(ColorEnum.GREY.getColor());
		
		yearChooser = viewHandler.getYearChooser(window);
		comboBoxWeeks = viewHandler.getComboBoxWeeks(window);

		buttonCancel = viewHandler.getCancelButton(window);
		buttonSave = viewHandler.getSaveButton(window);
		buttonSave.setEnabled(false);
		buttonRefresh = viewHandler.getRefreshButton(window);

		buttonSearchOrder = viewHandler.getButtonSearchOrder(window);
		labelSearchResult = viewHandler.getLabelSearchResult();

		buttonReport = viewHandler.getButtonReport();
		tablePostShipment = viewHandler.getTablePostShipment(window);
		buttonAddPostShipment = viewHandler.getButtonAddPostShipment(window);
		buttonDeletePostShipment = viewHandler
				.getButtonDeletePostShipment(window);
		buttonExcel = viewHandler.getButtonExcel(window);
		labelOrderCount = viewHandler.getLabelOrderCount();
		labelValue = viewHandler.getLabelGarageCost();
		checkBoxFilterSent = viewHandler.getCheckBoxFilterSent();
		labelBudget = viewHandler.getLabelBudget();

		comboBoxProductAreaGroup = viewHandler.getComboBoxProductAreaGroup();
		checkBoxListView = viewHandler.getCheckBoxListView();
	}

	/**
	 * Initierer hendelsehåndtering
	 * 
	 * @param window
	 */
	private void initEventHandling(WindowInterface window) {
		tablePostShipment.addMouseListener(viewHandler
				.getRightClickListener(true));
	}

	private JPanel buildFilterPanel() {
		FormLayout layout = new FormLayout(
				"p,3dlu,30dlu,3dlu,p,3dlu,p,3dlu,25dlu,3dlu,p",
				"p,3dlu,p,3dlu,10dlu");
		PanelBuilder builder = new PanelBuilder(layout);
		// PanelBuilder builder = new PanelBuilder(new FormDebugPanel(),layout);
		CellConstraints cc = new CellConstraints();

		builder.addLabel("År:", cc.xy(1, 1));
		builder.add(yearChooser, cc.xy(3, 1));
		builder.addLabel("Uke:", cc.xy(5, 1));
		builder.add(comboBoxWeeks, cc.xy(7, 1));
		builder.addLabel("Produktområde:", cc.xyw(11, 1, 1));

		builder.add(ButtonBarFactory.buildLeftAlignedBar(buttonSearchOrder,
				buttonReport), cc.xyw(1, 3, 10));
		builder.add(comboBoxProductAreaGroup, cc.xyw(11, 3, 1));

		builder.add(labelSearchResult, cc.xyw(1, 5, 9));
		return builder.getPanel();
	}

	private JPanel buildLeftPane(WindowInterface window) {
		FormLayout layout = new FormLayout("p:grow",
				"p,3dlu,fill:p:grow,3dlu,fill:p:grow");
		PanelBuilder builder = new PanelBuilder(layout);
		// PanelBuilder builder = new PanelBuilder(new FormDebugPanel(),layout);
		CellConstraints cc = new CellConstraints();

		builder.add(buildFilterPanel(), cc.xy(1, 1));

		builder.add(buildOrderPanel(window), cc.xyw(1, 3, 1));

		builder.add(buildPostShipmentPanel(window), cc.xyw(1, 5, 1));

		return builder.getPanel();
	}

	private JPanel buildInfoPanel() {
		FormLayout layout = new FormLayout(
				"p,3dlu,p,3dlu,15dlu,3dlu,p,3dlu,35dlu,3dlu,28dlu,3dlu,35dlu,p,p,p",
				"p,3dlu,p");
		PanelBuilder builder = new PanelBuilder(layout);
//		 PanelBuilder builder = new PanelBuilder(new FormDebugPanel(),layout);
		CellConstraints cc = new CellConstraints();

		builder.addLabel("Transport", cc.xy(1, 1));
		builder.addLabel("#Ordre:", cc.xy(3, 1));
		builder.add(labelOrderCount, cc.xy(5, 1));
		builder.addLabel("Verdi:", cc.xy(7, 1));
		builder.add(labelValue, cc.xy(9, 1));
		builder.addLabel("Budsjett:", cc.xy(11, 1));
		builder.add(labelBudget, cc.xy(13, 1));
		builder.add(checkBoxFilterSent, cc.xy(15, 1));
		builder.add(checkBoxListView, cc.xy(16, 1));
		builder.add(buildColorInfoPanel(),cc.xyw(1, 3,16));

		return builder.getPanel();
	}
	
	private JPanel buildColorInfoPanel(){
		FormLayout layout = new FormLayout(
				"55dlu,3dlu,55dlu,3dlu,55dlu,3dlu,55dlu",
				"p");
		
		PanelBuilder builder = new PanelBuilder(layout);
		CellConstraints cc = new CellConstraints();

		builder.add(textFieldGreen,cc.xy(1, 1));
		builder.add(textFieldYellow,cc.xy(3, 1));
		builder.add(textFieldRed,cc.xy(5, 1));
		builder.add(textFieldGrey,cc.xy(7, 1));

		return builder.getPanel();
	}

	private JPanel buildRightPane(WindowInterface window) {
		FormLayout layout = new FormLayout("p:grow", "p,3dlu,fill:p:grow");
		PanelBuilder builder = new PanelBuilder(layout);
		// PanelBuilder builder = new PanelBuilder(new FormDebugPanel(),layout);
		CellConstraints cc = new CellConstraints();

		builder.add(buildInfoPanel(), cc.xy(1, 1));

		builder.add(buildTransportWeekPanel(window), cc.xy(1, 3));

		return builder.getPanel();
	}

	/**
	 * Bygger panel
	 * 
	 * @param window
	 * @return panel
	 */
	public Component buildPanel(WindowInterface window) {
		initComponents(window);
		initEventHandling(window);
		JPanel leftPane = buildLeftPane(window);
		JPanel rightPane = buildRightPane(window);
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				leftPane, rightPane);
		splitPane.setOneTouchExpandable(true);
		splitPane.setResizeWeight(0.5);

		FormLayout layout = new FormLayout("10dlu,fill:p:grow",
				"10dlu,fill:p:grow,3dlu,p,3dlu");
		PanelBuilder builder = new PanelBuilder(layout);
//		 PanelBuilder builder = new PanelBuilder(new FormDebugPanel(),layout);
		CellConstraints cc = new CellConstraints();
		builder.add(splitPane, cc.xy(2, 2));
		builder.add(ButtonBarFactory.buildCenteredBar(buttonExcel, buttonSave,
				buttonRefresh, buttonCancel), cc.xy(2, 4));
		viewHandler.handleFilter();
		return builder.getPanel();
	}

	/**
	 * Bygger panel som viser transportruter for gitt år og uke
	 * 
	 * @param window
	 * @return panel
	 */
	private Component buildTransportWeekPanel(WindowInterface window) {
		return viewHandler.getTransportWeekView().buildPanel(window);
	}

	/**
	 * Bygger panel for visning av nye ordre
	 * 
	 * @param window
	 * @return panel
	 */
	private Component buildOrderPanel(WindowInterface window) {
		return viewHandler.getOrderPanelView().buildPanel(window, "200",
				viewHandler.getRightClickListener(false), "240", true);
	}

	/**
	 * Lager panel for etterleveringer
	 * 
	 * @param window
	 * @return panel
	 */
	private Component buildPostShipmentPanel(WindowInterface window) {
		FormLayout layout = new FormLayout("240dlu:grow",
				"p,3dlu,fill:120dlu:grow,3dlu,p");
		PanelBuilder builder = new PanelBuilder(layout);
		CellConstraints cc = new CellConstraints();

		builder.addLabel("Ettersendinger:", cc.xy(1, 1));
		builder.add(new JScrollPane(tablePostShipment), cc.xy(1, 3));
		builder.add(ButtonBarFactory.buildCenteredBar(buttonAddPostShipment,
				buttonDeletePostShipment), cc.xy(1, 5));

		return builder.getPanel();
	}

	/**
	 * @see no.ugland.utransprod.gui.Viewer#buildWindow()
	 */
	public WindowInterface buildWindow() {
		WindowInterface window = InternalFrameBuilder
				.buildInternalFrame(viewHandler.getWindowTitle(), viewHandler
						.getWindowSize(), true);
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
		viewHandler.doRefresh(null);

	}

	/**
	 * @see no.ugland.utransprod.gui.Viewer#cleanUp()
	 */
	public void cleanUp() {
		viewHandler.cleanUp();

	}

	/**
	 * @see no.ugland.utransprod.gui.Viewer#useDispose()
	 */
	public boolean useDispose() {
		return viewHandler.getDisposeOnClose();
	}

}
