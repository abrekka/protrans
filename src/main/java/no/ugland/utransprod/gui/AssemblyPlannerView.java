package no.ugland.utransprod.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import no.ugland.utransprod.gui.handlers.AssemblyPlannerViewHandler;
import no.ugland.utransprod.gui.model.ColorEnum;
import no.ugland.utransprod.model.Supplier;
import no.ugland.utransprod.util.InternalFrameBuilder;
import no.ugland.utransprod.util.Util;
import no.ugland.utransprod.util.YearWeek;

import org.jdesktop.swingx.JXTable;

import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.beans.BeanAdapter;
import com.jgoodies.binding.beans.PropertyConnector;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import com.toedter.calendar.JYearChooser;

/**
 * Vindu for planlegging av montering
 * 
 * @author atle.brekka
 */
public class AssemblyPlannerView implements Viewer {
    private JYearChooser yearChooser;

    private JComboBox comboBoxWeeks;

    private AssemblyPlannerViewHandler viewHandler;

    private YearWeek yearWeek;

    private JButton buttonSearchOrder;

    private JLabel labelSearchResult;

    private JButton buttonRefresh;

    private JButton buttonPrint;
    private JButton buttonExcel;

    private JButton buttonCancel;

    private JPanel panelAssemblies;

    private JPanel panelTeams;

    private JPanel panelAssembliesMain;
    private JPanel panelRightMain;
    private JPanel panelRight;

    private JPanel panelTeamsMain;

    private JScrollBar teamVerticalScrollBar;

    private WindowInterface currentWindow;

    private JLabel labelWarning;

    private Map<Supplier, Integer> supplierRowSizes = new Hashtable<Supplier, Integer>();

    private JXTable tableDeviation;

    // private JComboBox comboBoxProductAreaGroup;

    // private ProductAreaGroup productAreaGroup;

    private Component orderPanelView;
    private JTextField textFieldGreen;
    private JTextField textFieldYellow;
    private JTextField textFieldRed;
    private JCheckBox checkBoxListView;

    private boolean useListView = true;

    private JXTable tableAssembly;

    private JButton buttonFilter;

    /**
     * @param handler
     */
    public AssemblyPlannerView(final AssemblyPlannerViewHandler handler) {
	viewHandler = handler;
    }

    /**
     * Initierer komponenter
     * 
     * @param window
     */
    private void initComponents(final WindowInterface window) {
	textFieldGreen = new JTextField("montert");
	textFieldGreen.setHorizontalAlignment(JTextField.CENTER);
	textFieldGreen.setBackground(ColorEnum.GREEN.getColor());

	textFieldYellow = new JTextField("sendt mangler");
	textFieldYellow.setHorizontalAlignment(JTextField.CENTER);
	textFieldYellow.setBackground(ColorEnum.YELLOW.getColor());

	textFieldRed = new JTextField("overtid");
	textFieldRed.setHorizontalAlignment(JTextField.CENTER);
	textFieldRed.setBackground(ColorEnum.RED.getColor());
	panelAssembliesMain = new JPanel(new BorderLayout());
	panelRightMain = new JPanel(new BorderLayout());
	panelRight = new JPanel(new BorderLayout());
	panelTeamsMain = new JPanel(new BorderLayout());
	yearWeek = new YearWeek();
	BeanAdapter yearWeekAdapter = new BeanAdapter(yearWeek, true);
	yearChooser = new JYearChooser();
	yearWeekAdapter.addPropertyChangeListener(viewHandler.getWeekChangeListener(yearWeek, window));

	orderPanelView = viewHandler.getOrderPanelView().buildPanel(window, "150", viewHandler.getRightClickListener(), "130", true);

	SelectionInList weekSelectionList = viewHandler.getWeekSelectionList(yearWeekAdapter);
	comboBoxWeeks = BasicComponentFactory.createComboBox(weekSelectionList);
	comboBoxWeeks.setSelectedItem(Util.getCurrentWeek());
	weekSelectionList.addValueChangeListener(viewHandler.getWeekChangeListener(yearWeek, window));

	buttonRefresh = viewHandler.getRefreshButton(window);
	buttonCancel = viewHandler.getCancelButton(window);
	buttonPrint = viewHandler.getPrintButton(window);
	buttonExcel = viewHandler.getExcelButton(window);
	buttonFilter = viewHandler.getFilterButton(window);

	buttonSearchOrder = viewHandler.getButtonSearchOrder(window, this);
	labelSearchResult = viewHandler.getLabelSearchResult();

	labelWarning = viewHandler.getLabelWarning();
	tableDeviation = viewHandler.getTableDeviation(window);
	// comboBoxProductAreaGroup = viewHandler.getComboBoxProductAreaGroup();
	// productAreaGroup = (ProductAreaGroup)
	// comboBoxProductAreaGroup.getSelectedItem();
	viewHandler.setAssemblyPlannerView(this);

	checkBoxListView = viewHandler.getCheckBoxListView(window);
	tableAssembly = viewHandler.getTableAssembly(window);

    }

    /**
     * Initierer hendelsehåndtering
     * 
     * @param window
     */
    private void initEventHandling(final WindowInterface window) {
	PropertyConnector connYear = new PropertyConnector(yearChooser, "year", yearWeek, YearWeek.PROPERTY_YEAR);
	connYear.updateProperty2();
    }

    /**
     * Bygger vindu
     * 
     * @param window
     * @return panel
     */
    /*
     * public final JComponent buildPanelOld(final WindowInterface window) {
     * currentWindow = window; initComponents(window);
     * initEventHandling(window);
     * 
     * 
     * FormLayout layout = new FormLayout(
     * "10dlu,p,3dlu,30dlu,3dlu,20dlu,3dlu,30dlu,3dlu,30dlu,3dlu,p,3dlu,350dlu:grow"
     * ,
     * "10dlu,p,3dlu,p,3dlu,10dlu,3dlu,fill:200dlu:grow,3dlu,fill:80dlu:grow,10dlu,p,3dlu"
     * );
     * 
     * //PanelBuilder builder = new PanelBuilder(layout); PanelBuilder builder =
     * new PanelBuilder(new FormDebugPanel(),layout); CellConstraints cc = new
     * CellConstraints(); builder.addLabel("År:", cc.xy(2, 2));
     * builder.add(yearChooser, cc.xy(4, 2)); builder.addLabel("Uke:", cc.xy(6,
     * 2)); builder.add(comboBoxWeeks, cc.xy(8, 2));
     * builder.add(buttonSearchOrder, cc.xyw(2, 4, 5));
     * builder.add(labelWarning, cc.xyw(8, 4, 3));
     * builder.add(labelSearchResult, cc.xyw(2, 6, 7));
     * builder.add(buildOrderPanel(window), cc.xyw(2, 8, 9));
     * 
     * builder.add(buildDeviationPanel(window), cc.xyw(2, 10, 9));
     * 
     * builder.addLabel("Lag:", cc.xy(12, 2)); panelTeams =
     * buildAssemblyTeamPanel(true, yearWeek); panelTeamsMain.add(panelTeams,
     * BorderLayout.CENTER); JScrollPane scrollPaneTeam = new
     * JScrollPane(panelTeamsMain); scrollPaneTeam
     * .setVerticalScrollBarPolicy(ScrollPaneConstants
     * .VERTICAL_SCROLLBAR_NEVER); builder.add(scrollPaneTeam, cc.xywh(12, 4, 1,
     * 7)); teamVerticalScrollBar = scrollPaneTeam.getVerticalScrollBar();
     * panelAssemblies = buildAssembliesWeekPanel(window, true);
     * panelAssembliesMain.add(panelAssemblies, BorderLayout.CENTER);
     * builder.add(panelAssembliesMain, cc.xywh(14, 2, 1, 9));
     * 
     * builder.add(ButtonBarFactory.buildCenteredBar(buttonPrint, buttonRefresh,
     * buttonCancel), cc.xyw(2, 12, 13)); return builder.getPanel(); }
     */

    public final JComponent buildPanel(final WindowInterface window) {
	currentWindow = window;
	initComponents(window);
	initEventHandling(window);

	JPanel leftPane = buildLeftPane(window);
	JPanel rightPane = buildRightPane(window);
	JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPane, rightPane);
	splitPane.setOneTouchExpandable(true);
	// splitPane.setResizeWeight(0.1);

	FormLayout layout = new FormLayout("10dlu,fill:p:grow", "10dlu,fill:p:grow,3dlu,p");

	PanelBuilder builder = new PanelBuilder(layout);
	// PanelBuilder builder = new PanelBuilder(new FormDebugPanel(),layout);
	CellConstraints cc = new CellConstraints();

	builder.add(splitPane, cc.xy(2, 2));
	builder.add(ButtonBarFactory.buildCenteredBar(buttonExcel, buttonPrint, buttonRefresh, buttonCancel), cc.xy(2, 4));
	return builder.getPanel();
    }

    private JPanel buildRightPane(WindowInterface window) {
	FormLayout layout = new FormLayout("p:grow", "15dlu,fill:140dlu:grow");
	PanelBuilder builder = new PanelBuilder(layout);
	// PanelBuilder builder = new PanelBuilder(new FormDebugPanel(),
	// layout);
	CellConstraints cc = new CellConstraints();

	builder.add(buildColorInfoPanel(), cc.xy(1, 1));
	if (checkBoxListView.isSelected()) {
	    panelRight = buildAssemblyTablePanel();
	    panelRightMain.add(panelRight, BorderLayout.CENTER);
	    builder.add(panelRightMain, cc.xywh(1, 2, 1, 1));
	} else {
	    panelRight = buildAssemblyPlanPanel(window);
	    panelRightMain.add(panelRight, BorderLayout.CENTER);
	    // builder.addLabel("Lag:", cc.xy(1, 3));
	    // panelTeams = buildAssemblyTeamPanel(true, yearWeek);
	    // panelTeamsMain.add(panelTeams, BorderLayout.CENTER);
	    // JScrollPane scrollPaneTeam = new JScrollPane(panelTeamsMain);
	    // scrollPaneTeam.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
	    // builder.add(scrollPaneTeam, cc.xy(1, 5));
	    // teamVerticalScrollBar = scrollPaneTeam.getVerticalScrollBar();
	    // panelAssemblies = buildAssembliesWeekPanel(window, true);
	    // panelAssembliesMain.add(panelAssemblies, BorderLayout.CENTER);
	    // builder.add(panelAssembliesMain, cc.xywh(3, 2, 1, 4));
	    builder.add(panelRightMain, cc.xywh(1, 2, 1, 1));
	}

	return builder.getPanel();
    }

    private JPanel buildAssemblyTablePanel() {
	FormLayout layout = new FormLayout("fill:140dlu:grow", "p,fill:140dlu:grow");
	PanelBuilder builder = new PanelBuilder(layout);
	// PanelBuilder builder = new PanelBuilder(new FormDebugPanel(),
	// layout);
	CellConstraints cc = new CellConstraints();
	builder.add(ButtonBarFactory.buildCenteredBar(buttonFilter), cc.xy(1, 1));
	JScrollPane scrollPane = new JScrollPane(tableAssembly);
	builder.add(scrollPane, cc.xy(1, 2));
	return builder.getPanel();
    }

    private JPanel buildAssemblyPlanPanel(WindowInterface window) {
	FormLayout layout = new FormLayout("p,3dlu,p:grow", "3dlu,p,3dlu,fill:140dlu:grow");
	PanelBuilder builder = new PanelBuilder(layout);
	// PanelBuilder builder = new PanelBuilder(new FormDebugPanel(),
	// layout);
	CellConstraints cc = new CellConstraints();
	builder.addLabel("Lag:", cc.xy(1, 2));
	panelTeams = buildAssemblyTeamPanel(false, yearWeek);
	panelTeamsMain.add(panelTeams, BorderLayout.CENTER);
	JScrollPane scrollPaneTeam = new JScrollPane(panelTeamsMain);
	scrollPaneTeam.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
	builder.add(scrollPaneTeam, cc.xy(1, 4));
	teamVerticalScrollBar = scrollPaneTeam.getVerticalScrollBar();
	panelAssemblies = buildAssembliesWeekPanel(window, true);
	panelAssembliesMain.add(panelAssemblies, BorderLayout.CENTER);
	builder.add(panelAssembliesMain, cc.xywh(3, 1, 1, 4));
	return builder.getPanel();
    }

    private JPanel buildLeftPane(WindowInterface window) {
	FormLayout layout = new FormLayout("p:grow", "p,3dlu,fill:120dlu:grow,3dlu,fill:100dlu:grow");
	PanelBuilder builder = new PanelBuilder(layout);
	// PanelBuilder builder = new PanelBuilder(new FormDebugPanel(),
	// layout);
	CellConstraints cc = new CellConstraints();

	/*
	 * builder.addLabel("År:", cc.xy(1, 1)); builder.add(yearChooser,
	 * cc.xy(3, 1)); builder.addLabel("Uke:", cc.xy(5, 1));
	 * builder.add(comboBoxWeeks, cc.xy(7, 1));
	 * builder.addLabel("Produktområde:", cc.xyw(11, 1, 1));
	 * 
	 * builder.add(ButtonBarFactory.buildLeftAlignedBar(buttonSearchOrder,
	 * buttonReport), cc.xyw(1, 3, 10));
	 * builder.add(comboBoxProductAreaGroup, cc.xyw(11, 3, 1));
	 * 
	 * builder.add(labelSearchResult, cc.xyw(1, 5, 9));
	 */
	builder.add(buildFilterPanel(), cc.xy(1, 1));

	builder.add(orderPanelView, cc.xy(1, 3));

	builder.add(buildDeviationPanel(window), cc.xy(1, 5));

	return builder.getPanel();
    }

    private Component buildFilterPanel() {
	FormLayout layout = new FormLayout("p,3dlu,30dlu,3dlu,p,3dlu,p,3dlu,10dlu,p", "p,3dlu,p,3dlu,10dlu");
	PanelBuilder builder = new PanelBuilder(layout);
	// PanelBuilder builder = new PanelBuilder(new FormDebugPanel(),
	// layout);
	CellConstraints cc = new CellConstraints();
	builder.addLabel("År:", cc.xy(1, 1));
	builder.add(yearChooser, cc.xy(3, 1));
	builder.addLabel("Uke:", cc.xy(5, 1));
	builder.add(comboBoxWeeks, cc.xy(7, 1));
	// builder.addLabel("Produktområde:", cc.xy(10, 1));
	// builder.add(comboBoxProductAreaGroup, cc.xy(10, 3));
	builder.add(buttonSearchOrder, cc.xyw(1, 3, 5));
	builder.add(labelWarning, cc.xyw(7, 3, 3));
	builder.add(labelSearchResult, cc.xyw(1, 5, 10));
	return builder.getPanel();
    }

    /**
     * Lager panel med avvik
     * 
     * @param window
     * @return panel
     */
    private Component buildDeviationPanel(final WindowInterface window) {
	FormLayout layout = new FormLayout("130dlu:grow", "p,3dlu,50dlu:grow");
	PanelBuilder builder = new PanelBuilder(layout);
	// PanelBuilder builder = new PanelBuilder(new FormDebugPanel(),layout);
	CellConstraints cc = new CellConstraints();

	builder.addLabel("Avvik:", cc.xy(1, 1));
	builder.add(new JScrollPane(tableDeviation), cc.xy(1, 3));

	return builder.getPanel();
    }

    /**
     * Bygger panel med monteringer for alle monteringsteam
     * 
     * @param window
     * @param starting
     * @return panel
     */
    private JPanel buildAssembliesWeekPanel(final WindowInterface window, final boolean starting) {
	FormLayout layout = new FormLayout("348dlu:grow", "p,3dlu,p,2dlu,fill:300dlu:grow");
	PanelBuilder builder = new PanelBuilder(layout);
	// PanelBuilder builder = new PanelBuilder(new FormDebugPanel(),layout);
	CellConstraints cc = new CellConstraints();
	// builder.add(buildColorInfoPanel(), cc.xy(1, 1));
	JScrollPane scrollPaneWeek = new JScrollPane(buildWeekPanel());
	scrollPaneWeek.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

	builder.add(scrollPaneWeek, cc.xy(1, 3));

	JScrollPane scrollPaneAssembly = new JScrollPane(buildAssemblyWeekPanel(window, starting));
	scrollPaneAssembly.getHorizontalScrollBar().addAdjustmentListener(
		viewHandler.getHorizontalAdjustmentListener(scrollPaneWeek.getHorizontalScrollBar()));
	scrollPaneAssembly.getVerticalScrollBar().addAdjustmentListener(viewHandler.getVerticalAdjustmentListener(teamVerticalScrollBar));

	builder.add(scrollPaneAssembly, cc.xy(1, 5));
	return builder.getPanel();
    }

    private Component buildColorInfoPanel() {
	FormLayout layout = new FormLayout("52dlu,3dlu,52dlu,3dlu,52dlu,3dlu,p", "p");
	PanelBuilder builder = new PanelBuilder(layout);
	// PanelBuilder builder = new PanelBuilder(new FormDebugPanel(),layout);
	CellConstraints cc = new CellConstraints();
	builder.add(textFieldGreen, cc.xy(1, 1));
	builder.add(textFieldYellow, cc.xy(3, 1));
	builder.add(textFieldRed, cc.xy(5, 1));
	builder.add(checkBoxListView, cc.xy(7, 1));
	return builder.getPanel();
    }

    /**
     * Bygger panel med monteringer for alle uker som skal vise
     * 
     * @param window
     * @param starting
     * @return panel
     */
    private JPanel buildAssemblyWeekPanel(final WindowInterface window, final boolean starting) {
	int weekStart = yearWeek.getWeek() - 1;
	int weekStop = yearWeek.getWeek() + 1;
	String columnLayout = "p,p,p,p,p";
	// if (weekStart == 0) {
	// weekStart = 1;
	// columnLayout = "p,p,p,";
	// }
	if (weekStop == 54) {
	    weekStop = 53;
	    columnLayout = "p,p,p,";
	}

	FormLayout layout = new FormLayout(columnLayout, "");
	DefaultFormBuilder builder = new DefaultFormBuilder(layout);
	YearWeek currentYearWeek = new YearWeek();
	currentYearWeek.setYear(yearWeek.getYear());

	builder.appendRow(new RowSpec("center:3dlu"));
	builder.nextRow();

	int weekCounter = 0;
	List<Supplier> suppliers = viewHandler.getSuppliers(yearWeek);
	for (Supplier supplier : suppliers) {
	    weekCounter = 0;
	    for (int i = weekStart; i <= weekStop; i++) {

		weekCounter++;
		currentYearWeek.setWeek(i);

		if (yearWeek.getWeek() == 1 && i == 0) {
		    builder.appendRow(new RowSpec("fill:p"));
		    builder.append("                                                                                       ");
		} else {

		    builder.appendRow(new RowSpec("fill:p"));
		    builder.append(viewHandler.getAssemblyTeamOrderView(supplier, currentYearWeek, weekCounter, starting).buildPanel(window,
			    supplierRowSizes.get(supplier)));
		}
	    }
	}

	return builder.getPanel();
    }

    /**
     * Bygger panel som viser uketallene til uker som skal vises
     * 
     * @return panel
     */
    private JComponent buildWeekPanel() {
	StringBuffer layoutBuffer = new StringBuffer();
	for (int i = 0; i <= 2; i++) {
	    layoutBuffer.append("150dlu,p,");
	}
	layoutBuffer.append("150dlu");
	FormLayout layout = new FormLayout(layoutBuffer.toString(), "");
	DefaultFormBuilder builder = new DefaultFormBuilder(layout);
	int weekStart = yearWeek.getWeek() - 1;
	int weekStop = yearWeek.getWeek() + 1;
	// if (weekStart == 0) {
	// weekStart = 1;
	// }
	if (weekStop == 54) {
	    weekStop = 53;
	}
	for (int j = weekStart; j <= weekStop; j++) {
	    if (yearWeek.getWeek() == 1 && j == 0) {
		builder.append(" ");
	    } else {
		builder.append("Uke " + j);
	    }
	}
	return builder.getPanel();
    }

    private JPanel buildAssemblyTeamPanel(final boolean starting, final YearWeek yearWeek1) {
	FormLayout layout = new FormLayout("80dlu", "");
	DefaultFormBuilder builder = new DefaultFormBuilder(layout);
	int counter = 0;

	List<Supplier> suppliers = viewHandler.getSuppliers(yearWeek1);
	for (Supplier supplier : suppliers) {
	    counter++;
	    int maxOrderSize = viewHandler.getMaxNumbersOfOrders(supplier, yearWeek1, starting);
	    if (maxOrderSize == 0) {
		maxOrderSize = 1;
	    }

	    int rowSize = (25 * maxOrderSize) + 30;

	    supplierRowSizes.put(supplier, rowSize);

	    String rowSizeSpec = "center:" + rowSize + "dlu";
	    builder.appendRow(new RowSpec(rowSizeSpec));
	    builder.append(new JLabel(supplier.getSupplierName()));
	}
	builder.appendRow(new RowSpec("top:15dlu"));
	return builder.getPanel();
    }

    /**
     * Bygger panel for ordre
     * 
     * @param window
     * @return panel
     */
    /*
     * private Component buildOrderPanel(final WindowInterface window) { return
     * viewHandler.getOrderPanelView(this).buildPanel(window, "200",
     * viewHandler.getRightClickListener(), "130", true); }
     */

    /**
     * @see no.ugland.utransprod.gui.Viewer#buildWindow()
     */
    public final WindowInterface buildWindow() {
	WindowInterface window = InternalFrameBuilder.buildInternalFrame(viewHandler.getWindowTitle(), viewHandler.getWindowSize(), true);
	window.add(buildPanel(window), BorderLayout.CENTER);

	return window;
    }

    /**
     * Ved forandring av uke må hele vinduet genereres på nytt
     * 
     * @param newYearWeek
     */
    public final void changeWeek(final YearWeek newYearWeek) {
	supplierRowSizes.clear();
	if (newYearWeek != null) {
	    yearWeek.setWeek(newYearWeek.getWeek());
	    yearWeek.setYear(newYearWeek.getYear());
	}

	panelRightMain.remove(panelRight);
	if (checkBoxListView.isSelected()) {
	    viewHandler.initAssemblyList();
	    panelRight = buildAssemblyTablePanel();
	    panelRightMain.add(panelRight, BorderLayout.CENTER);
	} else {
	    if (panelTeams != null) {
		panelTeamsMain.remove(panelTeams);
	    }
	    // panelTeams = buildAssemblyTeamPanel(false, yearWeek);
	    // panelTeamsMain.add(panelTeams);
	    //
	    panelTeamsMain.repaint();
	    panelTeamsMain.validate();
	    if (panelTeamsMain.getParent() != null) {
		panelTeamsMain.getParent().repaint();
		panelTeamsMain.getParent().validate();
	    }

	    if (panelAssemblies != null) {
		panelAssembliesMain.remove(panelAssemblies);
	    }
	    // panelAssemblies = buildAssembliesWeekPanel(currentWindow, false);
	    // panelAssembliesMain.add(panelAssemblies);
	    //
	    // panelAssembliesMain.repaint();
	    // panelAssembliesMain.validate();
	    //
	    // if (panelAssembliesMain.getParent() != null) {
	    // panelAssembliesMain.getParent().repaint();
	    // panelAssembliesMain.getParent().validate();
	    // }
	    panelRight = buildAssemblyPlanPanel(currentWindow);
	    panelRightMain.add(panelRight, BorderLayout.CENTER);
	}

	panelRightMain.getParent().repaint();
	panelRightMain.getParent().validate();

    }

    /**
     * @see no.ugland.utransprod.gui.Viewer#getTitle()
     */
    public final String getTitle() {
	return viewHandler.getWindowTitle();
    }

    /**
     * @see no.ugland.utransprod.gui.Viewer#initWindow()
     */
    public final void initWindow() {
	viewHandler.init();

    }

    /**
     * @see no.ugland.utransprod.gui.Viewer#cleanUp()
     */
    public void cleanUp() {

    }

    /**
     * @see no.ugland.utransprod.gui.Viewer#useDispose()
     */
    public final boolean useDispose() {
	return viewHandler.getDisposeOnClose();
    }

    // public void setProductAreaGroup(ProductAreaGroup group) {
    // productAreaGroup = group;
    //
    // }
}
