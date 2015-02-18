package no.ugland.utransprod.gui.handlers;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.table.TableModel;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.OrderPanelTypeEnum;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.edit.AbstractEditView;
import no.ugland.utransprod.gui.edit.EditAssemblyView;
import no.ugland.utransprod.gui.edit.EditCommentView;
import no.ugland.utransprod.gui.model.AssemblyModel;
import no.ugland.utransprod.gui.model.ColorEnum;
import no.ugland.utransprod.gui.model.OrderCommentModel;
import no.ugland.utransprod.gui.model.ReportEnum;
import no.ugland.utransprod.gui.model.TextPaneRendererTransport;
import no.ugland.utransprod.model.Assembly;
import no.ugland.utransprod.model.Deviation;
import no.ugland.utransprod.model.FakturagrunnlagV;
import no.ugland.utransprod.model.IAssembly;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderComment;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.Ordln;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.model.Supplier;
import no.ugland.utransprod.service.AssemblyManager;
import no.ugland.utransprod.service.CraningCostManager;
import no.ugland.utransprod.service.DeviationManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.VismaFileCreator;
import no.ugland.utransprod.service.enums.LazyLoadDeviationEnum;
import no.ugland.utransprod.service.enums.LazyLoadOrderEnum;
import no.ugland.utransprod.util.CommentTypeUtil;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.OrderLineWrapper;
import no.ugland.utransprod.util.Threadable;
import no.ugland.utransprod.util.UserUtil;
import no.ugland.utransprod.util.Util;
import no.ugland.utransprod.util.YearWeek;
import no.ugland.utransprod.util.report.AssemblyReport;
import no.ugland.utransprod.util.report.AssemblyReportFactory;
import no.ugland.utransprod.util.report.AssemblyReportNy;
import no.ugland.utransprod.util.report.MailConfig;
import no.ugland.utransprod.util.report.ReportViewer;

import org.hibernate.Hibernate;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.ColorHighlighter;
import org.jdesktop.swingx.decorator.Filter;
import org.jdesktop.swingx.decorator.FilterPipeline;
import org.jdesktop.swingx.decorator.HighlighterFactory;
import org.jdesktop.swingx.decorator.PatternFilter;
import org.jdesktop.swingx.decorator.PatternPredicate;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.internal.Lists;
import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.adapter.ComboBoxAdapter;
import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.binding.beans.PropertyConnector;
import com.jgoodies.binding.list.ArrayListModel;
import com.jgoodies.binding.list.SelectionInList;
import com.toedter.calendar.JDateChooser;

/**
 * Hjelpeklasse for visning av monteringer
 * 
 * @author atle.brekka
 */
public class SupplierOrderViewHandler extends AbstractViewHandler<Assembly, AssemblyModel> {

    private static final long serialVersionUID = 1L;

    private final ArrayListModel assemblyList;

    final SelectionInList assemblySelectionList;

    private Supplier currentSupplier;

    JXTable tableOrders;

    private OrderViewHandler orderViewHandler;

    private YearWeek currentYearWeek;

    JPopupMenu popupMenuEdit;

    JMenuItem menuItemEdit;

    JMenuItem menuItemRemoveAssembly;

    JMenuItem menuItemShowMissing;

    JMenuItem menuItemShowContent;

    JMenuItem menuItemDeviation;

    JMenuItem menuItemAssemblyReport;

    private List<String> plannedList;

    private AssemblyTeamTableModel tableModel;

    private List<PropertyChangeListener> assemblyChangeListeners = new ArrayList<PropertyChangeListener>();

    private MenuItemListener menuItemListener;

    private MailConfig mailConfig;

    private AssemblyReportFactory assemblyReportFactory;

    private DeviationViewHandlerFactory deviationViewHandlerFactory;
    private Login login;
    private ManagerRepository managerRepository;

    private ProductAreaGroup currentProductAreaGroup;
    private VismaFileCreator vismaFileCreator;

    /**
     * @param aHeading
     * @param aAssemblyTeam
     * @param aOrderViewHandler
     * @param aYearWeek
     * @param aUserType
     * @param aApplicationUser
     */
    @Inject
    public SupplierOrderViewHandler(Login aLogin, ManagerRepository aManagerRepository, final AssemblyReportFactory aAssemblyReportFactory,
	    DeviationViewHandlerFactory aDeviationViewHandlerFactory, OrderViewHandlerFactory orderViewHandlerFactory,
	    @Assisted final Supplier aSupplier, @Assisted final YearWeek aYearWeek, VismaFileCreator aVismaFileCreator) {
	super("Montering", aManagerRepository.getAssemblyManager(), aLogin.getUserType(), true);
	this.vismaFileCreator = aVismaFileCreator;
	login = aLogin;
	managerRepository = aManagerRepository;
	deviationViewHandlerFactory = aDeviationViewHandlerFactory;
	assemblyReportFactory = aAssemblyReportFactory;
	currentSupplier = aSupplier;

	this.orderViewHandler = orderViewHandlerFactory.create(true);
	this.currentYearWeek = aYearWeek;
	assemblyList = new ArrayListModel();
	assemblySelectionList = new SelectionInList((ListModel) assemblyList);
	initLists();
	initMenus();
	initMailConfig();

    }

    private void initMailConfig() {
	mailConfig = new MailConfig("Fakturagrunnlag", "Fakturagrunnlag", "", "");
    }

    private void initMenus() {
	popupMenuEdit = new JPopupMenu("Editer...");
	menuItemEdit = new JMenuItem("Editer...");
	menuItemEdit.setEnabled(hasWriteAccess());
	menuItemRemoveAssembly = new JMenuItem("Fjern montering...");
	menuItemRemoveAssembly.setEnabled(hasWriteAccess());
	menuItemShowMissing = new JMenuItem("Se mangler...");
	menuItemShowContent = new JMenuItem("Se innhold...");
	menuItemDeviation = new JMenuItem("Registrere avvik...");
	menuItemAssemblyReport = new JMenuItem("Fakturagrunnlag...");

	popupMenuEdit.add(menuItemEdit);
	popupMenuEdit.add(menuItemRemoveAssembly);
	popupMenuEdit.add(menuItemShowMissing);
	popupMenuEdit.add(menuItemAssemblyReport);
    }

    /**
     * Legger til lytter til monteringsendringer
     * 
     * @param assemblyChangeListener
     */
    public void addAssemblyChangeListener(PropertyChangeListener assemblyChangeListener) {
	assemblyChangeListeners.add(assemblyChangeListener);
    }

    /**
     * Forteller at montering har endret seg
     */
    private void fireAsesemblyChanged() {
	for (PropertyChangeListener listener : assemblyChangeListeners) {
	    listener.propertyChange(new PropertyChangeEvent(this, "assembly", null, null));
	}
    }

    /**
     * Initierer lister
     */
    private void initLists() {
	assemblySelectionList.clearSelection();
	assemblyList.clear();
	if (currentYearWeek != null) {
	    List<Assembly> assemblies = ((AssemblyManager) overviewManager).findBySupplierYearWeek(currentSupplier, currentYearWeek.getYear(),
		    currentYearWeek.getWeek());
	    if (assemblies != null) {
		for (Assembly assembly : assemblies) {
		    orderViewHandler.lazyLoadOrder(assembly.getOrder(), new LazyLoadOrderEnum[] { LazyLoadOrderEnum.COMMENTS });
		}
		assemblyList.addAll(assemblies);
	    }
	}

    }

    /**
     * Lager tabell for ordre
     * 
     * @param aWindow
     * @return tabell
     */
    public JXTable getTableOrders(WindowInterface aWindow) {
	window = aWindow;
	initMenuItems(aWindow);

	ColorHighlighter assembliedHighlighter = new ColorHighlighter(new PatternPredicate("Ja", 7), ColorEnum.GREEN.getColor(), null);
	ColorHighlighter missingHighlighter = new ColorHighlighter(new PatternPredicate("1", 6), ColorEnum.YELLOW.getColor(), null);
	ColorHighlighter overdueHighlighter = new ColorHighlighter(new PatternPredicate("1", 5), ColorEnum.RED.getColor(), null);

	tableOrders = new JXTable();
	tableOrders.setModel(getTableModel(aWindow));
	tableOrders.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	tableOrders.setSelectionModel(new SingleListSelectionAdapter(assemblySelectionList.getSelectionIndexHolder()));
	tableOrders.setColumnControlVisible(true);
	tableOrders.setSearchable(null);
	tableOrders.addMouseListener(new MouseClickHandler(aWindow));

	tableOrders.setRowHeight(40);
	tableOrders.setShowGrid(true);
	tableOrders.setShowVerticalLines(true);
	tableOrders.getColumnModel().getColumn(2).setCellRenderer(new TextPaneRendererTransport());
	tableOrders.addHighlighter(HighlighterFactory.createAlternateStriping());
	tableOrders.setShowGrid(true);
	tableOrders.setRolloverEnabled(false);

	tableOrders.addHighlighter(assembliedHighlighter);
	tableOrders.addHighlighter(missingHighlighter);
	tableOrders.addHighlighter(overdueHighlighter);

	tableOrders.getColumnExt(5).setVisible(false);
	tableOrders.getColumnExt(5).setVisible(false);
	tableOrders.getColumnExt(5).setVisible(false);
	tableOrders.packAll();

	handleFilter();
	return tableOrders;

    }

    private void handleFilter() {
	if (currentProductAreaGroup != null && !currentProductAreaGroup.getProductAreaGroupName().equalsIgnoreCase("Alle")) {
	    Filter[] filters = new Filter[] { new PatternFilter(currentProductAreaGroup.getProductAreaGroupName(), Pattern.CASE_INSENSITIVE, 8) };
	    FilterPipeline filterPipeline = new FilterPipeline(filters);
	    tableOrders.setFilters(filterPipeline);
	} else {
	    tableOrders.setFilters(null);
	}
    }

    private void initMenuItems(WindowInterface aWindow) {
	if (menuItemListener == null) {
	    menuItemListener = new MenuItemListener(aWindow);
	    menuItemEdit.addActionListener(menuItemListener);
	    menuItemRemoveAssembly.addActionListener(menuItemListener);
	    menuItemShowMissing.addActionListener(menuItemListener);
	    menuItemShowContent.addActionListener(menuItemListener);
	    menuItemDeviation.addActionListener(menuItemListener);
	    menuItemAssemblyReport.addActionListener(new AssemblyReportListener());
	}
    }

    /**
     * Åpner ordrevindu
     * 
     * @param order
     * @param window
     */
    void openOrderView(Order order, WindowInterface window) {
	orderViewHandler.openOrderView(order, false, window);
    }

    /**
     * Håndterer museklikk
     * 
     * @author atle.brekka
     */
    final class MouseClickHandler extends MouseAdapter {
	/**
         *
         */
	private WindowInterface window;

	/**
	 * @param aWindow
	 */
	public MouseClickHandler(WindowInterface aWindow) {
	    window = aWindow;
	}

	/**
	 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
	    if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2) {
		Util.setWaitCursor(window.getComponent());
		if (assemblySelectionList.getSelection() != null) {
		    int index = tableOrders.convertRowIndexToModel(assemblySelectionList.getSelectionIndex());
		    Assembly assembly = (Assembly) assemblySelectionList.getElementAt(index);
		    Order order = assembly.getOrder() == null ? assembly.getDeviation().getOrder() : assembly.getOrder();
		    openOrderView(order, window);
		}
		Util.setDefaultCursor(window.getComponent());
	    } else if (SwingUtilities.isRightMouseButton(e) && assemblySelectionList.getSelectionIndex() != -1) {
		Assembly assembly = (Assembly) assemblySelectionList.getElementAt(tableOrders.convertRowIndexToModel(assemblySelectionList
			.getSelectionIndex()));
		if (assembly.getDeviation() != null) {
		    popupMenuEdit.add(menuItemShowContent);
		    popupMenuEdit.remove(menuItemDeviation);
		} else {
		    popupMenuEdit.remove(menuItemShowContent);
		    popupMenuEdit.add(menuItemDeviation);
		}
		popupMenuEdit.show((JXTable) e.getSource(), e.getX(), e.getY());
	    }
	}
    }

    /**
     * Editerer montering
     * 
     * @param window
     */
    void editAssembly(WindowInterface window) {
	Assembly assembly = (Assembly) assemblySelectionList.getSelection();
	if (assembly != null) {
	    openEditView(assembly, false, window);
	}
	fireAsesemblyChanged();
    }

    /**
     * Viser innhold av en montering
     * 
     * @param iAssembly
     * @param window
     */
    void showContent(IAssembly iAssembly, WindowInterface window) {
	Collection<OrderLine> orderLines = iAssembly.getAssemblyOrderLines();
	if (orderLines != null) {
	    List<OrderLineWrapper> missingList = Util.getOrderLineWrapperList(orderLines);
	    Util.showOptionsDialog(window, missingList, "Innhold", false, false);
	}
    }

    /**
     * Menylytter
     * 
     * @author atle.brekka
     */
    private class MenuItemListener implements ActionListener {
	/**
         *
         */
	private WindowInterface window;

	/**
	 * @param aWindow
	 */
	public MenuItemListener(WindowInterface aWindow) {
	    window = aWindow;

	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent actionEvent) {
	    if (actionEvent.getActionCommand().equalsIgnoreCase(menuItemEdit.getText())) {

		editAssembly(window);

	    } else if (actionEvent.getActionCommand().equalsIgnoreCase(menuItemRemoveAssembly.getText())) {
		if (Util.showConfirmDialog(window.getComponent(), "Fjerne?", "Vil du virkelig fjerne montering?")) {
		    removeAssembly(window);
		}
	    } else if (actionEvent.getActionCommand().equalsIgnoreCase(menuItemShowMissing.getText())) {

		showMissingCollies(window);
	    } else if (actionEvent.getActionCommand().equalsIgnoreCase(menuItemShowContent.getText())) {

		Assembly assembly = (Assembly) assemblySelectionList.getElementAt(tableOrders.convertRowIndexToModel(assemblySelectionList
			.getSelectionIndex()));
		Deviation deviation = assembly.getDeviation();
		if (deviation != null) {
		    managerRepository.getDeviationManager().lazyLoad(deviation,
			    new LazyLoadDeviationEnum[] { LazyLoadDeviationEnum.ORDER_LINES, LazyLoadDeviationEnum.COMMENTS });

		    showContent(deviation, window);
		}
	    } else if (actionEvent.getActionCommand().equalsIgnoreCase(menuItemDeviation.getText())) {

		Assembly assembly = (Assembly) assemblySelectionList.getElementAt(tableOrders.convertRowIndexToModel(assemblySelectionList
			.getSelectionIndex()));
		Order order = assembly.getOrder();
		if (order != null) {

		    DeviationViewHandler deviationViewHandler = deviationViewHandlerFactory.create(order, true, false, true, null, true);
		    deviationViewHandler.registerDeviation(order, window);
		}
	    }

	}

    }

    /**
     * Fjern montering
     * 
     * @param window
     */
    void removeAssembly(WindowInterface window) {
	int index = tableOrders.convertRowIndexToModel(assemblySelectionList.getSelectionIndex());
	Assembly assembly = (Assembly) assemblySelectionList.getElementAt(index);

	Order order = assembly.getOrder();
	Deviation deviation = assembly.getDeviation();

	orderViewHandler.setAssemblyInactive(assembly);
	if (order != null) {

	    try {
		orderViewHandler.getOrderManager().saveOrder(order);
		orderViewHandler.initAndGetOrderPanelSelectionList(OrderPanelTypeEnum.ASSEMBLY_ORDERS);
	    } catch (ProTransException e) {
		Util.showErrorDialog(window, "Feil", e.getMessage());
		e.printStackTrace();
	    }
	} else {
	    DeviationManager deviationManager = (DeviationManager) ModelUtil.getBean("deviationManager");
	    deviation.setAssembly(null);
	    deviationManager.saveDeviation(deviation);
	    managerRepository.getAssemblyManager().removeObject(assembly);
	    fireAsesemblyChanged();
	}

	assemblyList.remove(index);
    }

    /**
     * @param object
     * @return feilmelding
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#checkDeleteObject(java.lang.Object)
     */
    @Override
    public CheckObject checkDeleteObject(Assembly object) {
	return null;
    }

    /**
     * @param object
     * @param presentationModel
     * @param window
     * @return feilmelding
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#checkSaveObject(java.lang.Object,
     *      com.jgoodies.binding.PresentationModel,
     *      no.ugland.utransprod.gui.WindowInterface)
     */
    @Override
    public CheckObject checkSaveObject(AssemblyModel object, PresentationModel presentationModel, WindowInterface window) {
	return null;
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getAddRemoveString()
     */
    @Override
    public String getAddRemoveString() {
	return "montering";
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getClassName()
     */
    @Override
    public String getClassName() {
	return null;
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getNewObject()
     */
    @Override
    public Assembly getNewObject() {
	return new Assembly();
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getTableModel(no.ugland.utransprod.gui.WindowInterface)
     */
    @Override
    public TableModel getTableModel(WindowInterface window) {
	if (tableModel == null) {
	    tableModel = new AssemblyTeamTableModel(assemblySelectionList, assemblyList, window);
	}
	return tableModel;
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getTableWidth()
     */
    @Override
    public String getTableWidth() {
	return null;
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getTitle()
     */
    @Override
    public String getTitle() {
	return null;
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getWindowSize()
     */
    @Override
    public Dimension getWindowSize() {
	return null;
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#setColumnWidth(org.jdesktop.swingx.JXTable)
     */
    @Override
    public void setColumnWidth(JXTable table) {
    }

    /**
     * Lager comboboks for planlagt
     * 
     * @param presentationModel
     * @return comboboks
     */
    public JComboBox getComboBoxPlanned(PresentationModel presentationModel) {
	return new JComboBox(new ComboBoxAdapter(getPlannedList(), presentationModel.getBufferedModel(AssemblyModel.PROPERTY_PLANNED)));
    }

    /**
     * Lager sjekkboks for montert
     * 
     * @param presentationModel
     * @return sjekkboks
     */
    public JCheckBox getCheckBoxAssemblied(PresentationModel presentationModel) {
	return BasicComponentFactory.createCheckBox(presentationModel.getBufferedModel(AssemblyModel.PROPERTY_ASSEMBLIED_BOOL), "Montert");
    }

    /**
     * Lager tekstfelt for kommentar
     * 
     * @param presentationModel
     * @return tekstfelt
     */
    public JList getListAreaComment(PresentationModel presentationModel) {
	JList list = BasicComponentFactory.createList(new SelectionInList(presentationModel.getBufferedModel(AssemblyModel.PROPERTY_COMMENT_LIST)));
	list.setName("ListComment");
	return list;
    }

    /**
     * Lager datovelger for montering
     * 
     * @param presentationModel
     * @return datovelger
     */
    public JDateChooser getDateChooser(PresentationModel presentationModel) {
	JDateChooser dateChooser = new JDateChooser();
	PropertyConnector conn1 = new PropertyConnector(dateChooser, "date",
		presentationModel.getBufferedModel(AssemblyModel.PROPERTY_ASSEMBLIED_DATE), "value");
	conn1.updateProperty1();
	PropertyConnector conn2 = new PropertyConnector(dateChooser, "enabled",
		presentationModel.getBufferedModel(AssemblyModel.PROPERTY_ASSEMBLIED_BOOL), "value");
	conn2.updateProperty1();
	return dateChooser;
    }

    public JButton getButtonAddComment(PresentationModel presentationModel) {
	JButton button = new JButton(new AddCommentAction(presentationModel));
	button.setName("ButtonAddComment");
	return button;
    }

    /**
     * Lager planlagtliste
     * 
     * @return liste
     */
    public List<String> getPlannedList() {
	if (plannedList == null) {
	    String[] strings = new String[] { "Mandag", "Tirsdag", "Onsdag", "Torsdag", "Fredag", "Neste uke", "Feil mur" };
	    plannedList = new ArrayList<String>(Arrays.asList(strings));
	}
	return plannedList;
    }

    /**
     * Henter liste med monteringslag
     * 
     * @return monteringslag
     */
    public List<Supplier> getSupplierList(ProductAreaGroup productAreaGroup) {
	return managerRepository.getSupplierManager().findActiveByTypeName("Montering", "postalCode", productAreaGroup);
    }

    /**
     * Setter valgt montering
     * 
     * @param assembly
     */
    public void setSelectedAssembly(Assembly assembly) {
	assemblySelectionList.setSelection(assembly);
    }

    /**
     * Hneter antall ordre
     * 
     * @return antall ordre
     */
    public int getNumberOfOrders() {
	return assemblyList.getSize();
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#cleanUp()
     */
    @Override
    public void cleanUp() {
	super.cleanUp();
	assemblySelectionList.clearSelection();
	assemblyList.clear();

    }

    /**
     * Oppdaterer data
     * 
     * @param yearWeek
     */
    public void refresh(YearWeek yearWeek) {
	currentYearWeek = yearWeek;
	initLists();
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#hasWriteAccess()
     */
    @Override
    public Boolean hasWriteAccess() {
	return UserUtil.hasWriteAccess(userType, "Montering");
    }

    /**
     * Viser manglende kollier
     * 
     * @param window
     */
    void showMissingCollies(WindowInterface window) {
	Assembly assembly = (Assembly) assemblySelectionList.getSelection();
	if (assembly != null) {
	    Order order = assembly.getOrder();
	    Deviation deviation = assembly.getDeviation();
	    List<OrderLine> missing = null;
	    if (order != null) {
		orderViewHandler.lazyLoadOrder(order, new LazyLoadOrderEnum[] { LazyLoadOrderEnum.ORDER_LINES, LazyLoadOrderEnum.COMMENTS,
			LazyLoadOrderEnum.ORDER_LINE_ORDER_LINES });
		missing = order.getMissingCollies();
	    } else {
		DeviationManager deviationManager = (DeviationManager) ModelUtil.getBean("deviationManager");
		deviationManager.lazyLoad(deviation, new LazyLoadDeviationEnum[] { LazyLoadDeviationEnum.ORDER_LINES, LazyLoadDeviationEnum.COMMENTS,
			LazyLoadDeviationEnum.ORDER_LINE_ORDER_LINES });
		missing = deviation.getMissingCollies();
	    }

	    if (missing != null) {
		Util.showOptionsDialog(window, missing, "Mangler", false, false);
	    }
	}
    }

    /**
     * @param handler
     * @param object
     * @param searching
     * @return view
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getEditView(no.ugland.utransprod.gui.handlers.AbstractViewHandler,
     *      java.lang.Object, boolean)
     */
    @Override
    protected AbstractEditView<AssemblyModel, Assembly> getEditView(AbstractViewHandler<Assembly, AssemblyModel> handler, Assembly object,
	    boolean searching) {
	return new EditAssemblyView(false, new AssemblyModel(object, login.getApplicationUser().getUserName()), this);
    }

    private class AssemblyReportListener implements ActionListener {

	public void actionPerformed(ActionEvent event) {

	    Util.runInThreadWheel(window.getRootPane(), new Threadable() {

		public void enableComponents(boolean enable) {
		}

		public Object doWork(Object[] params, JLabel labelInfo) {
		    labelInfo.setText("Genererer fakturagrunnlag...");
		    String errorMsg = null;
		    try {
			// generateAssemblyReport();
			generateAssemblyReportNy();
		    } catch (ProTransException e) {
			errorMsg = e.getMessage();
			e.printStackTrace();
		    }
		    return errorMsg;
		}

		public void doWhenFinished(Object object) {
		    if (object != null) {
			Util.showErrorDialog(window, "Feil", object.toString());
		    }
		}

	    }, null);

	}

    }

    void generateAssemblyReport() throws ProTransException {
	Assembly assembly = (Assembly) assemblySelectionList.getElementAt(tableOrders.convertRowIndexToModel(assemblySelectionList
		.getSelectionIndex()));
	if (assembly != null) {
	    managerRepository.getOrderManager().lazyLoadOrder(assembly.getOrder(),
		    new LazyLoadOrderEnum[] { LazyLoadOrderEnum.ORDER_COSTS, LazyLoadOrderEnum.COMMENTS });

	    List<Ordln> vismaOrderLines = managerRepository.getOrdlnManager().findForFakturagrunnlag(assembly.getOrder().getOrderNr());
	    AssemblyReport assemblyReport = assemblyReportFactory.create(assembly.getOrder(), vismaOrderLines);

	    mailConfig.addToHeading(" for ordrenummer " + assembly.getOrder().getOrderNr());

	    ReportViewer reportViewer = new ReportViewer("Montering", mailConfig);
	    List<AssemblyReport> assemblyReportList = new ArrayList<AssemblyReport>();
	    assemblyReportList.add(assemblyReport);
	    reportViewer.generateProtransReportFromBeanAndShow(assemblyReportList, "Montering", ReportEnum.ASSEMBLY, null, null, window, true);

	}
    }

    void generateAssemblyReportNy() throws ProTransException {
	Assembly assembly = (Assembly) assemblySelectionList.getElementAt(tableOrders.convertRowIndexToModel(assemblySelectionList
		.getSelectionIndex()));
	if (assembly != null) {
	    Order order = assembly.getOrder() == null ? assembly.getDeviation().getOrder() : assembly.getOrder();
	    managerRepository.getOrderManager().lazyLoadOrder(order,
		    new LazyLoadOrderEnum[] { LazyLoadOrderEnum.ORDER_COSTS, LazyLoadOrderEnum.COMMENTS });

	    // List<Ordln> vismaOrderLines =
	    // managerRepository.getOrdlnManager().findForFakturagrunnlag(assembly.getOrder().getOrderNr());

	    Integer orderId = order == null ? assembly.getDeviation().getOrder().getOrderId() : order.getOrderId();
	    List<FakturagrunnlagV> fakturagrunnlag = managerRepository.getFakturagrunnlagVManager().findFakturagrunnlag(orderId);
	    List<FakturagrunnlagV> filtrertFakturagrunnlag = Lists.newArrayList(Iterables.filter(fakturagrunnlag, ikkeFraktMed001()));
	    // AssemblyReport assemblyReport =
	    // assemblyReportFactory.create(assembly.getOrder(),
	    // vismaOrderLines);
	    final CraningCostManager craningCostManager = (CraningCostManager) ModelUtil.getBean(CraningCostManager.MANAGER_NAME);
	    AssemblyReportNy assemblyReport = new AssemblyReportNy(craningCostManager, order, filtrertFakturagrunnlag);

	    String orderNr = order == null ? assembly.getDeviation().getOrderNr() : order.getOrderNr();
	    mailConfig.addToHeading(" for ordrenummer " + orderNr);

	    ReportViewer reportViewer = new ReportViewer("Montering", mailConfig);
	    List<AssemblyReportNy> assemblyReportList = Lists.newArrayList();
	    assemblyReportList.add(assemblyReport);
	    reportViewer.generateProtransReportFromBeanAndShow(assemblyReportList, "Montering", ReportEnum.ASSEMBLY_NY, null, null, window, true);

	}
    }

    private Predicate<FakturagrunnlagV> ikkeFraktMed001() {
	return new Predicate<FakturagrunnlagV>() {

	    public boolean apply(FakturagrunnlagV fakturagrunnlagV) {
		return !(fakturagrunnlagV.getOrgPriceMont() != null && "FRAKT".equalsIgnoreCase(fakturagrunnlagV.getProdno()) && fakturagrunnlagV
			.getOrgPriceMont().doubleValue() == 0.01);
	    }
	};
    }

    private class AddCommentAction extends AbstractAction {
	private static final long serialVersionUID = 1L;
	private PresentationModel presentationModel;

	public AddCommentAction(PresentationModel aPresentationModel) {
	    super("Legg til kommentar...");
	    presentationModel = aPresentationModel;

	}

	@SuppressWarnings("unchecked")
	public void actionPerformed(ActionEvent e) {
	    OrderComment orderComment = new OrderComment();
	    orderComment.setUserName(login.getApplicationUser().getUserName());
	    orderComment.setCommentDate(Util.getCurrentDate());
	    orderComment.addCommentType(CommentTypeUtil.getCommentType("Montering"));

	    CommentViewHandler orderCommentViewHandler = new CommentViewHandler(login, managerRepository.getOrderManager());
	    EditCommentView editDeviationCommentView = new EditCommentView(new OrderCommentModel(orderComment), orderCommentViewHandler);

	    JDialog dialog = Util.getDialog(window, "Legg til kommentar", true);
	    dialog.setName("EditDeviationCommentView");
	    WindowInterface dialogWindow = new JDialogAdapter(dialog);
	    dialogWindow.add(editDeviationCommentView.buildPanel(dialogWindow));
	    dialog.pack();
	    Util.locateOnScreenCenter(dialog);
	    dialogWindow.setVisible(true);

	    if (!orderCommentViewHandler.isCanceled()) {
		List<OrderComment> comments = new ArrayList<OrderComment>(
			(List<OrderComment>) presentationModel.getBufferedValue(AssemblyModel.PROPERTY_COMMENT_LIST));
		Order order = ((AssemblyModel) presentationModel.getBean()).getObject().getOrder();
		order.setCachedComment(null);
		orderComment.setOrder(order);
		comments.add(0, orderComment);
		presentationModel.setBufferedValue(AssemblyModel.PROPERTY_COMMENT_LIST, comments);

	    }

	}
    }

    @Override
    void afterSaveObject(Assembly assembly, WindowInterface window) {
	try {
	    Order order = assembly.getOrder();
	    if (order == null && assembly.getDeviation() != null) {
		order = assembly.getDeviation().getOrder();
	    }
	    if (order != null) {
		if (!Hibernate.isInitialized(order.getOrderComments())) {
		    managerRepository.getOrderManager().lazyLoadOrder(order, new LazyLoadOrderEnum[] { LazyLoadOrderEnum.COMMENTS });
		}
		order.cacheComments();
		orderViewHandler.getOrderManager().saveOrder(order);
	    }
	    vismaFileCreator.createVismaFileForAssembly(order, assembly.getAssembliedBool());
	} catch (ProTransException e) {
	    Util.showErrorDialog(window, "Feil", e.getMessage());
	    e.printStackTrace();
	}

    }

    @Override
    String getAddString() {
	return null;
    }

    public void setProductAreaGroup(ProductAreaGroup productAreaGroup) {
	currentProductAreaGroup = productAreaGroup;

    }

}
