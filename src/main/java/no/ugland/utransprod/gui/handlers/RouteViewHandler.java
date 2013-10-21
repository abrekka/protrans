package no.ugland.utransprod.gui.handlers;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.ProTransRuntimeException;
import no.ugland.utransprod.gui.CloseListener;
import no.ugland.utransprod.gui.Closeable;
import no.ugland.utransprod.gui.IconEnum;
import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.OrderPanelTypeEnum;
import no.ugland.utransprod.gui.OrderPanelView;
import no.ugland.utransprod.gui.ProTransMain;
import no.ugland.utransprod.gui.ProductAreaGroupProvider;
import no.ugland.utransprod.gui.ReportConstraintView;
import no.ugland.utransprod.gui.TransportWeekView;
import no.ugland.utransprod.gui.UBColumnControlPopup;
import no.ugland.utransprod.gui.Updateable;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.buttons.CancelButton;
import no.ugland.utransprod.gui.buttons.RefreshButton;
import no.ugland.utransprod.gui.buttons.SaveButton;
import no.ugland.utransprod.gui.edit.EditDeviationView;
import no.ugland.utransprod.gui.model.BudgetType;
import no.ugland.utransprod.gui.model.ColorEnum;
import no.ugland.utransprod.gui.model.DeviationModel;
import no.ugland.utransprod.gui.model.ProductAreaGroupModel;
import no.ugland.utransprod.gui.model.ProductionBudgetModel;
import no.ugland.utransprod.gui.model.TextPaneRendererCustomer;
import no.ugland.utransprod.gui.model.TransportSumVModel;
import no.ugland.utransprod.gui.model.Transportable;
import no.ugland.utransprod.model.Budget;
import no.ugland.utransprod.model.Colli;
import no.ugland.utransprod.model.ConstructionType;
import no.ugland.utransprod.model.Deviation;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderComment;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.PostShipment;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.model.Transport;
import no.ugland.utransprod.model.TransportSumV;
import no.ugland.utransprod.service.ColliManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.OverviewManager;
import no.ugland.utransprod.service.PostShipmentManager;
import no.ugland.utransprod.service.ProductAreaGroupManager;
import no.ugland.utransprod.service.VismaFileCreator;
import no.ugland.utransprod.service.enums.LazyLoadEnum;
import no.ugland.utransprod.service.enums.LazyLoadOrderEnum;
import no.ugland.utransprod.service.enums.LazyLoadPostShipmentEnum;
import no.ugland.utransprod.util.ApplicationParamUtil;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.OrderLineWrapper;
import no.ugland.utransprod.util.PrefsUtil;
import no.ugland.utransprod.util.Threadable;
import no.ugland.utransprod.util.TransportComparator;
import no.ugland.utransprod.util.UserUtil;
import no.ugland.utransprod.util.Util;
import no.ugland.utransprod.util.YearWeek;
import no.ugland.utransprod.util.excel.ExcelUtil;

import org.hibernate.Hibernate;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.ColorHighlighter;
import org.jdesktop.swingx.decorator.Filter;
import org.jdesktop.swingx.decorator.FilterPipeline;
import org.jdesktop.swingx.decorator.PatternFilter;
import org.jdesktop.swingx.decorator.PatternPredicate;

import com.google.inject.Inject;
import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.binding.beans.BeanAdapter;
import com.jgoodies.binding.beans.PropertyConnector;
import com.jgoodies.binding.list.ArrayListModel;
import com.jgoodies.binding.list.SelectionInList;
import com.toedter.calendar.JYearChooser;

/**
 * Hjelpeklasse for visning av transportruter
 * 
 * @author atle.brekka
 */
public class RouteViewHandler implements Closeable, Updateable,
		ListDataListener, ProductAreaGroupProvider, OrderNrProvider {
	TransportWeekView transportWeekView;

	JButton buttonSave;

	TransportWeekViewHandler transportWeekViewHandler;

	final SelectionInList weekSelectionList;

	boolean internalWeekChange = false;

	OrderViewHandler orderViewHandler;

	private JLabel labelSearchResult;

	private final ArrayListModel postShipmentList;

	final SelectionInList postShipmentSelectionList;

	JPopupMenu popupMenuSetTransportOrder;

	JMenuItem menuItemSetTransportOrder;
	JMenuItem menuItemShowTakstolinfo;

	JPopupMenu popupMenuPostShipment;

	JMenuItem menuItemSetTransportPostShimpment;

	JMenuItem menuItemShowDeviation;

	private JMenuItem menuItemShowContent;

	private JMenuItem menuItemPacklist;

	private JMenuItem menuItemAddComment;

	JXTable tablePostShipment;

	JButton buttonDeletePostShipment;

	private List<CloseListener> closeListeners = new ArrayList<CloseListener>();

	private PresentationModel presentationModelTransportSum;

	private PresentationModel presentationModelBudget;

	private YearWeek yearWeek;

	private TransportChangeListener transportChangeListener;

	private boolean disposeOnClose = true;

	JCheckBox checkBoxFilter;

	private List<ProductAreaGroup> productAreaGroupList;

	PresentationModel productAreaGroupModel;

	SelectionEmpyHandler selectionEmpyHandler;

	private ManagerRepository managerRepository;
	private Login login;

	private DeviationViewHandlerFactory deviationViewHandlerFactory;

	private OrderViewHandlerFactory orderViewHandlerFactory;
	private ShowTakstolInfoActionFactory showTakstolInfoActionFactory;

	private VismaFileCreator vismaFileCreator;

	/**
	 * @param aOrderViewHandler
	 * @param aApplicationUser
	 * @param aUserType
	 */
	@Inject
	public RouteViewHandler(
			final OrderViewHandlerFactory aOrderViewHandlerFactory,
			final Login aLogin, final ManagerRepository aManagerRepository,
			final DeviationViewHandlerFactory aDeviationViewHandlerFactory,
			ShowTakstolInfoActionFactory aShowTakstolInfoActionFactory,
			final VismaFileCreator vismaFileCreator) {
		this.vismaFileCreator = vismaFileCreator;
		showTakstolInfoActionFactory = aShowTakstolInfoActionFactory;
		deviationViewHandlerFactory = aDeviationViewHandlerFactory;
		managerRepository = aManagerRepository;
		login = aLogin;
		transportChangeListener = new TransportChangeListener();
		yearWeek = new YearWeek();
		weekSelectionList = new SelectionInList(Util.getWeeks());
		orderViewHandlerFactory = aOrderViewHandlerFactory;
		orderViewHandler = orderViewHandlerFactory.create(true);
		orderViewHandler.addListDataListener(this);

		postShipmentList = new ArrayListModel();
		postShipmentSelectionList = new SelectionInList(
				(ListModel) postShipmentList);

		initMenus();

		presentationModelTransportSum = new PresentationModel(
				new TransportSumVModel(new TransportSumV(Integer.valueOf(0),
						BigDecimal.valueOf(0), null)));

		presentationModelBudget = new PresentationModel(
				new ProductionBudgetModel(new Budget(null, null, null,
						BigDecimal.valueOf(0), null, null)));

		initProductAreaGroup();
		setTransportSum();

	}

	private void initMenus() {
		popupMenuSetTransportOrder = new JPopupMenu("Sett transport...");
		menuItemSetTransportOrder = new JMenuItem("Sett transport...");
		menuItemSetTransportOrder.setName("MenuItemSetTransportOrder");
		menuItemSetTransportOrder.setEnabled(hasWriteAccess());

		menuItemShowTakstolinfo = new JMenuItem("Takstolinfo...");
		menuItemShowTakstolinfo.setName("MenuItemShowTakstolinfo");

		popupMenuSetTransportOrder.add(menuItemSetTransportOrder);
		popupMenuSetTransportOrder.add(menuItemShowTakstolinfo);

		popupMenuPostShipment = new JPopupMenu("Sett transport...");
		popupMenuPostShipment.setName("PopupMenuPostShipment");
		menuItemSetTransportPostShimpment = new JMenuItem("Sett transport...");
		menuItemSetTransportPostShimpment
				.setName("MenuItemSetTransportPostShipment");
		menuItemSetTransportPostShimpment.setEnabled(hasWriteAccess());
		menuItemShowContent = new JMenuItem("Vis innhold...");
		menuItemShowContent.setName("MenuItemShowContent");
		menuItemPacklist = new JMenuItem("Pakkliste...");
		menuItemAddComment = new JMenuItem("Legg til kommentar...");
		menuItemAddComment.setName("MenuItemAddComment");
		menuItemShowDeviation = new JMenuItem("Se avviksskjema...");
		menuItemShowDeviation.setName("MenuItemShowDeviation");

		popupMenuPostShipment.add(menuItemSetTransportPostShimpment);
		popupMenuPostShipment.add(menuItemShowContent);
		popupMenuPostShipment.add(menuItemPacklist);
		popupMenuPostShipment.add(menuItemAddComment);
		popupMenuPostShipment.add(menuItemShowDeviation);
	}

	/**
	 * Initierer liste med produktområdegrupper
	 */
	private void initProductAreaGroup() {
		productAreaGroupModel = new PresentationModel(
				new ProductAreaGroupModel(ProductAreaGroup.UNKNOWN));
		productAreaGroupModel
				.addBeanPropertyChangeListener(new ProductAreaChangeListener());
		ProductAreaGroupManager productAreaGroupManager = (ProductAreaGroupManager) ModelUtil
				.getBean("productAreaGroupManager");
		productAreaGroupList = new ArrayList<ProductAreaGroup>();
		List<ProductAreaGroup> groups = productAreaGroupManager.findAll();
		if (groups != null) {
			productAreaGroupList.addAll(groups);
		}
	}

	/**
	 * Setter antall garsjer og garasjeverdi for gjeldende uke
	 */
	void setTransportSum() {
		ProductAreaGroup group = (ProductAreaGroup) productAreaGroupModel
				.getValue(ProductAreaGroupModel.PROPERTY_PRODUCT_AREA_GROUP);

		group = group.getProductAreaGroupName().equalsIgnoreCase("Alle") ? ProductAreaGroup.UNKNOWN
				: group;

		TransportSumV sum = managerRepository.getTransportSumVManager()
				.findYearAndWeekByProductAreaGroup(yearWeek.getYear(),
						yearWeek.getWeek(), group);

		presentationModelTransportSum.setBean(new TransportSumVModel(sum));

		YearWeek yearWeekMinusOne = Util.addWeek(yearWeek, -1);
		Budget productionBudget = managerRepository.getBudgetManager()
				.findByYearAndWeekPrProductAreaGroup(
						yearWeekMinusOne.getYear(), yearWeekMinusOne.getWeek(),
						group, BudgetType.PRODUCTION);

		presentationModelBudget.setBean(new ProductionBudgetModel(
				productionBudget));
	}

	/**
	 * Legger til lukkelytter
	 * 
	 * @param listener
	 */
	public void addCloseListener(CloseListener listener) {
		closeListeners.add(listener);
	}

	/**
	 * Gir beskjed om at vindu lukkes
	 */
	private void fireClose() {
		for (CloseListener listener : closeListeners) {
			listener.windowClosed();
		}
	}

	/**
	 * Lager årvelger
	 * 
	 * @param window
	 * @return årvelger
	 */
	public JYearChooser getYearChooser(WindowInterface window) {
		JYearChooser yearChooser = new JYearChooser();
		PropertyConnector.connect(yearChooser, "year", yearWeek, "year");
		yearChooser.addPropertyChangeListener(new YearChangeListener(window));
		yearChooser.setName("YearChooserTransport");
		return yearChooser;
	}

	/**
	 * Lager comboboks for valg av uke
	 * 
	 * @param window
	 * @return comboboks
	 */
	public JComboBox getComboBoxWeeks(WindowInterface window) {
		JComboBox comboBoxWeeks = BasicComponentFactory
				.createComboBox(weekSelectionList);
		comboBoxWeeks.setSelectedItem(Util.getCurrentWeek());
		BeanAdapter routeDateAdapter = new BeanAdapter(yearWeek, true);
		weekSelectionList.setSelectionHolder(routeDateAdapter
				.getValueModel(YearWeek.PROPERTY_WEEK));
		weekSelectionList
				.addValueChangeListener(new WeekChangeListener(window));
		comboBoxWeeks.setName("ComboBoxWeeks");
		return comboBoxWeeks;
	}

	/**
	 * Lager knapp for å legge til etterleveringer
	 * 
	 * @param window
	 * @return knapp
	 */
	public JButton getButtonAddPostShipment(WindowInterface window) {
		JButton button = new JButton(new AddPostShipmentAction(window));
		button.setEnabled(hasWriteAccess());
		return button;
	}

	/**
	 * Lager knapp for å slette etterlvering
	 * 
	 * @param window
	 * @return knapp
	 */
	public JButton getButtonDeletePostShipment(WindowInterface window) {
		buttonDeletePostShipment = new JButton(new DeletePostShipmentAction(
				window));
		buttonDeletePostShipment.setName("ButtonDeletePostShipment");
		buttonDeletePostShipment.setEnabled(false);
		selectionEmpyHandler = new SelectionEmpyHandler();
		postShipmentSelectionList.addPropertyChangeListener(
				SelectionInList.PROPERTYNAME_SELECTION_EMPTY,
				selectionEmpyHandler);
		return buttonDeletePostShipment;
	}

	/**
	 * Lager knapp for å generere excel
	 * 
	 * @param window
	 * @return knapp
	 */
	public JButton getButtonExcel(WindowInterface window) {
		JButton button = new JButton(new ExcelAction(window));
		button.setIcon(IconEnum.ICON_EXCEL.getIcon());
		return button;
	}

	/**
	 * Lager label for å vise antall order
	 * 
	 * @return label
	 */
	public JLabel getLabelOrderCount() {
		return BasicComponentFactory.createLabel(presentationModelTransportSum
				.getModel(TransportSumVModel.PROPERTY_ORDER_COUNT_STRING));
	}

	/**
	 * Lager label for å vise garasjeverdi
	 * 
	 * @return label
	 */
	public JLabel getLabelGarageCost() {
		return BasicComponentFactory.createLabel(presentationModelTransportSum
				.getModel(TransportSumVModel.PROPERTY_GARAGE_COST_STRING));
	}

	/**
	 * Lager label for budsjett
	 * 
	 * @return label
	 */
	public JLabel getLabelBudget() {
		return BasicComponentFactory.createLabel(presentationModelBudget
				.getModel(ProductionBudgetModel.PROPERTY_BUDGET_VALUE));
	}

	/**
	 * Lager komboboks for produktområdegruppe
	 * 
	 * @return komboboks
	 */
	public JComboBox getComboBoxProductAreaGroup() {
		return Util.getComboBoxProductAreaGroup(login.getApplicationUser(),
				login.getUserType(), productAreaGroupModel);
	}

	/**
	 * Lager sjekkboks for filtrering av sendte transporter
	 * 
	 * @return sjekkboks
	 */
	public JCheckBox getCheckBoxFilterSent() {
		checkBoxFilter = new JCheckBox("Vis sendt");
		checkBoxFilter.setSelected(true);
		checkBoxFilter.setName("CheckBoxFilter");
		checkBoxFilter.addActionListener(new FilterActionListener());
		return checkBoxFilter;
	}

	public JCheckBox getCheckBoxListView() {
		JCheckBox checkBoxListView = new JCheckBox(new ListAction());
		checkBoxListView
				.setSelected(transportWeekViewHandler != null ? transportWeekViewHandler
						.getUseListView()
						: false);
		checkBoxListView.setName("CheckBoxListView");
		return checkBoxListView;
	}

	/**
	 * Lager label for å vise status om søk
	 * 
	 * @return label
	 */
	public JLabel getLabelSearchResult() {
		labelSearchResult = new JLabel();
		return labelSearchResult;
	}

	/**
	 * Lager knapp for å søke etter ordre
	 * 
	 * @param window
	 * @return knapp
	 */
	public JButton getButtonSearchOrder(WindowInterface window) {
		setActionListenersForPostShipmentMenus(window);
		return new JButton(new SearchOrderAction(window));
	}

	/**
	 * Lager knapp for å hente ut statistikk
	 * 
	 * @return knapp
	 */
	public JButton getButtonReport() {
		return new JButton(new ReportAction());
	}

	/**
	 * Henter avbrytknapp
	 * 
	 * @param window
	 * @return knapp
	 */
	public JButton getCancelButton(WindowInterface window) {
		return new CancelButton(window, this, disposeOnClose);
	}

	/**
	 * Lager lagreknapp
	 * 
	 * @param window
	 * @return knapp
	 */
	public JButton getSaveButton(WindowInterface window) {
		buttonSave = new SaveButton(this, window);
		buttonSave.setEnabled(false);
		buttonSave.setName("SaveTransport");
		return buttonSave;
	}

	/**
	 * Lager napp for å oppdatere
	 * 
	 * @param window
	 * @return knapp
	 */
	public JButton getRefreshButton(WindowInterface window) {
		JButton button = new RefreshButton(this, window);
		button.setName("ButtonRefresh");
		return button;
	}

	/**
	 * @see no.ugland.utransprod.gui.Closeable#canClose(java.lang.String,
	 *      no.ugland.utransprod.gui.WindowInterface)
	 */
	public boolean canClose(String actionString, WindowInterface window) {
		boolean canClose = true;
		if (transportWeekViewHandler != null) {
			if (transportWeekViewHandler.hasChanges()) {
				if (Util.showConfirmDialog(window.getComponent(), "Lagre?",
						"Det er gjort endringer, ønsker du å lagre?")) {
					canClose = false;
				} else {
					transportWeekViewHandler.resetChanges();
				}
			}

		}
		if (canClose) {
			fireClose();
		}
		updateInvisibleColumns();
		return canClose;
	}

	private void updateInvisibleColumns() {
		PrefsUtil
				.putUserInvisibleColumns(
						tablePostShipment,
						(ProductAreaGroup) productAreaGroupModel
								.getValue(ProductAreaGroupModel.PROPERTY_PRODUCT_AREA_GROUP));

		PrefsUtil
				.putUserInvisibleColumns(
						orderViewHandler.getPanelTableOrders(
								OrderPanelTypeEnum.NEW_ORDERS, null),
						(ProductAreaGroup) productAreaGroupModel
								.getValue(ProductAreaGroupModel.PROPERTY_PRODUCT_AREA_GROUP));

		transportWeekViewHandler
				.saveUserInvisibleColumns((ProductAreaGroup) productAreaGroupModel
						.getValue(ProductAreaGroupModel.PROPERTY_PRODUCT_AREA_GROUP));
	}

	/**
	 * Henter vindusstørrelse
	 * 
	 * @return vindusstørrelse
	 */
	public Dimension getWindowSize() {
		return new Dimension(1000, 660);
	}

	/**
	 * Henter view for transport
	 * 
	 * @return transportview
	 */
	public TransportWeekView getTransportWeekView() {
		if (transportWeekView == null) {
			if (transportWeekViewHandler == null) {
				TransportViewHandler transportViewHandler = new TransportViewHandler(
						orderViewHandlerFactory, login, managerRepository,
						deviationViewHandlerFactory,
						showTakstolInfoActionFactory, vismaFileCreator);
				/* applicationUser, userType,transportManager); */
				transportWeekViewHandler = new TransportWeekViewHandler(login,
						managerRepository, transportViewHandler,
						orderViewHandlerFactory, deviationViewHandlerFactory,
						showTakstolInfoActionFactory, yearWeek,
						vismaFileCreator);
				transportWeekViewHandler
						.addTransportChangeListener(transportChangeListener);
			}
			transportWeekView = new TransportWeekView(
					yearWeek,
					transportWeekViewHandler,
					(ProductAreaGroup) productAreaGroupModel
							.getValue(ProductAreaGroupModel.PROPERTY_PRODUCT_AREA_GROUP));
		}

		return transportWeekView;
	}

	/**
	 * Håndterer endring av uke.
	 * 
	 * @author atle.brekka
	 */
	private final class WeekChangeListener implements PropertyChangeListener {
		private WindowInterface window;

		/**
		 * @param aWindow
		 */
		public WeekChangeListener(final WindowInterface aWindow) {
			window = aWindow;
		}

		/**
		 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
		 */
		public void propertyChange(final PropertyChangeEvent event) {
			Util.setWaitCursor(window.getComponent());
			if (!internalWeekChange) {
				if (canClose(null, window)) {

					transportWeekView.changeWeek(null);
					buttonSave.setEnabled(false);
					setTransportSum();
					checkBoxFilter.setSelected(true);
				} else {
					internalWeekChange = true;
					weekSelectionList.setSelection(event.getOldValue());
				}
			} else {
				internalWeekChange = false;
			}
			Util.setDefaultCursor(window.getComponent());
		}

	}

	/**
	 * Håndterer endring av år
	 * 
	 * @author atle.brekka
	 */
	private final class YearChangeListener implements PropertyChangeListener {
		private WindowInterface window;

		/**
		 * @param aWindow
		 */
		public YearChangeListener(WindowInterface aWindow) {
			window = aWindow;
		}

		/**
		 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
		 */
		public void propertyChange(PropertyChangeEvent event) {
			if (event.getPropertyName().equalsIgnoreCase("year")) {
				Util.setWaitCursor(window.getComponent());
				if (event.getPropertyName().equalsIgnoreCase("year")) {
					if (!internalWeekChange) {
						if (canClose(null, window)) {
							internalWeekChange = true;
							transportWeekView.changeWeek((Integer) event
									.getNewValue());
							buttonSave.setEnabled(false);
							internalWeekChange = false;
							setTransportSum();
							checkBoxFilter.setSelected(true);
						}
					} else {
						internalWeekChange = false;
					}
				}
				Util.setDefaultCursor(window.getComponent());
			}
		}

	}

	/**
	 * @see no.ugland.utransprod.gui.Updateable#doDelete(no.ugland.utransprod.gui.WindowInterface)
	 */
	public boolean doDelete(WindowInterface window) {
		return true;

	}

	/**
	 * @see no.ugland.utransprod.gui.Updateable#doNew(no.ugland.utransprod.gui.WindowInterface)
	 */
	public void doNew(WindowInterface window) {
	}

	/**
	 * @see no.ugland.utransprod.gui.Updateable#doRefresh(no.ugland.utransprod.gui.WindowInterface)
	 */
	public void doRefresh(WindowInterface window) {
		orderViewHandler
				.initAndGetOrderPanelSelectionList(OrderPanelTypeEnum.NEW_ORDERS);
		transportWeekView.changeWeek(null);

		PostShipmentManager postShipmentManager = (PostShipmentManager) ModelUtil
				.getBean("postShipmentManager");
		List<PostShipment> postShipmentLines = postShipmentManager
				.findAllWithoutTransport();
		postShipmentList.clear();
		if (postShipmentLines != null) {
			postShipmentList.addAll(postShipmentLines);
		}
	}

	/**
	 * @see no.ugland.utransprod.gui.Updateable#doSave(no.ugland.utransprod.gui.WindowInterface)
	 */
	public void doSave(WindowInterface window) {
		transportWeekViewHandler.saveChanges(window);
		buttonSave.setEnabled(false);

	}

	/**
	 * Håndterer endringer på transport i gjeldende uke
	 * 
	 * @author atle.brekka
	 */
	final class TransportChangeListener implements PropertyChangeListener {

		/**
		 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
		 */
		public void propertyChange(PropertyChangeEvent event) {
			if (event.getNewValue() != null && (Boolean) event.getNewValue()) {
				buttonSave.setEnabled(true);
			}

			refreshPostShipment();
			orderViewHandler
					.initAndGetOrderPanelSelectionList(OrderPanelTypeEnum.NEW_ORDERS);

		}

	}

	/**
	 * Oppdaterer etterleveringer
	 */
	void refreshPostShipment() {
		PostShipmentManager postShipmentManager = (PostShipmentManager) ModelUtil
				.getBean("postShipmentManager");
		postShipmentList.clear();
		List<PostShipment> postShipmentLines = postShipmentManager
				.findAllWithoutTransport();
		if (postShipmentLines != null) {
			initPostShipmentList(postShipmentManager, postShipmentLines);
			postShipmentList.addAll(postShipmentLines);
		}
	}

	/**
	 * Henter view for ordre
	 * 
	 * @return orderview
	 */
	public OrderPanelView getOrderPanelView() {
		return new OrderPanelView(orderViewHandler,
				OrderPanelTypeEnum.NEW_ORDERS, "Ordre:");
	}

	/**
	 * Lager tabell for etterleveringer
	 * 
	 * @param window
	 * @return tabell
	 */
	public JXTable getTablePostShipment(WindowInterface window) {
		PostShipmentManager postShipmentManager = (PostShipmentManager) ModelUtil
				.getBean("postShipmentManager");

		ColorHighlighter pattern = new ColorHighlighter(new PatternPredicate(
				"Ja", 6), ColorEnum.GREEN.getColor(), null);

		tablePostShipment = new JXTable();
		tablePostShipment.addHighlighter(pattern);
		postShipmentList.clear();
		List<PostShipment> postShipmentLines = postShipmentManager
				.findAllWithoutTransport();
		if (postShipmentLines != null) {
			initPostShipmentList(postShipmentManager, postShipmentLines);
			postShipmentList.addAll(postShipmentLines);

		}
		tablePostShipment.setModel(new PostShipmentTableModel(
				postShipmentSelectionList));
		tablePostShipment.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tablePostShipment.setSelectionModel(new SingleListSelectionAdapter(
				postShipmentSelectionList.getSelectionIndexHolder()));
		tablePostShipment.setColumnControlVisible(true);
		tablePostShipment.setColumnControl(new UBColumnControlPopup(
				tablePostShipment, this));
		tablePostShipment.setSearchable(null);

		tablePostShipment.setRowHeight(25);
		tablePostShipment.getColumnModel().getColumn(0).setCellRenderer(

		new TextPaneRendererCustomer());

		tablePostShipment.packAll();

		// setActionListenersForPostShipmentMenus(window);

		tablePostShipment.setName(TableEnum.TABLEPOSTSHIPMENTS.getTableName());
		return tablePostShipment;
	}

	private void setActionListenersForPostShipmentMenus(WindowInterface window) {
		menuItemAddComment.addActionListener(new AddCommentAction(window));
		menuItemShowContent.addActionListener(new ShowContentAction(window));
		menuItemPacklist.addActionListener(new GeneratePacklistAction(window));
		menuItemSetTransportOrder.addActionListener(new MenuItemListenerOrder(
				window));
		menuItemShowTakstolinfo.addActionListener(showTakstolInfoActionFactory
				.create(this, window));
		menuItemSetTransportPostShimpment
				.addActionListener(new MenuItemListenerPostShipment(window));
		menuItemShowDeviation
				.addActionListener(new MenuItemListenerShowDeviation(window));
	}

	private void initPostShipmentList(PostShipmentManager postShipmentManager,
			List<PostShipment> postShipmentLines) {
		for (PostShipment postShipment : postShipmentLines) {
			if (postShipment.getCachedComment() == null) {
				orderViewHandler.lazyLoadOrder(postShipment.getOrder(),
						new LazyLoadOrderEnum[] { LazyLoadOrderEnum.COMMENTS });
				postShipment.cacheComments();
				postShipmentManager.savePostShipment(postShipment);
			}
		}
	}

	/**
	 * @see javax.swing.event.ListDataListener#contentsChanged(javax.swing.event.ListDataEvent)
	 */
	public void contentsChanged(ListDataEvent arg0) {
	}

	/**
	 * @see javax.swing.event.ListDataListener#intervalAdded(javax.swing.event.ListDataEvent)
	 */
	public void intervalAdded(ListDataEvent arg0) {
	}

	/**
	 * @see javax.swing.event.ListDataListener#intervalRemoved(javax.swing.event.ListDataEvent)
	 */
	public void intervalRemoved(ListDataEvent arg0) {
	}

	/**
	 * Søker etter ordre
	 * 
	 * @param window
	 */
	void doSearch(WindowInterface window) {
		labelSearchResult.setText("");
		Transportable transportable = orderViewHandler
				.searchOrder(window, true);
		if (transportable == null) {
			labelSearchResult.setText("Det ble ikke funnet noen ordre");

		} else {

			if (transportable.getTransport() != null) {
				yearWeek.setYear(transportable.getTransport()
						.getTransportYear());
				yearWeek.setWeek(transportable.getTransport()
						.getTransportWeek());
				transportWeekView.changeWeek(yearWeek.getYear());
				transportWeekViewHandler.setSelectedTransportable(transportable
						.getTransport(), transportable);
			} else {
				labelSearchResult.setText("Orderen har ikke transport satt");
				orderViewHandler.setSelectedNewTransportable(transportable);
			}
		}

	}

	/**
	 * Håndterer søk
	 * 
	 * @author atle.brekka
	 */
	private class SearchOrderAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		private WindowInterface window;

		/**
		 * @param aWindow
		 */
		public SearchOrderAction(WindowInterface aWindow) {
			super("Søk ordre...");
			window = aWindow;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(final ActionEvent event) {
			doSearch(window);

		}
	}

	/**
	 * Håndterer statistikk
	 * 
	 * @author atle.brekka
	 */
	private class ReportAction extends AbstractAction {
		/**
         *
         */
		private static final long serialVersionUID = 1L;

		/**
         *
         */
		public ReportAction() {
			super("Statistikk...");
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent arg0) {
			ReportConstraintViewHandler reportConstraintViewHandler = new ReportConstraintViewHandler();
			ReportConstraintView reportConstraintView = new ReportConstraintView(
					reportConstraintViewHandler);

			WindowInterface window = new JDialogAdapter(new JDialog(
					ProTransMain.PRO_TRANS_MAIN, "Transport statistikk"));

			window.add(reportConstraintView.buildPanel(window));
			window.pack();
			Util.locateOnScreenCenter(window);
			window.setVisible(true);

		}
	}

	/**
	 * Tabellmodell for etterleveringer
	 * 
	 * @author atle.brekka
	 */
	public class PostShipmentTableModel extends AbstractTableAdapter {

		/**
         *
         */
		private static final long serialVersionUID = 1L;

		/**
		 * @param listModel
		 */
		public PostShipmentTableModel(ListModel listModel) {
			super(listModel, new String[] { "Kunde", "Ordrenr", "Adresse",
					"Postnummer", "Poststed", "Type",
					// "Kommentar",
					"Klar", "Produktområde" });
		}

		/**
		 * @see javax.swing.table.TableModel#getValueAt(int, int)
		 */
		public Object getValueAt(int row, int column) {
			PostShipment postShipment = (PostShipment) getRow(row);
			switch (column) {
			case 0:
				return postShipment;
			case 1:
				return postShipment.getOrder().getOrderNr();
			case 2:
				return postShipment.getOrder().getDeliveryAddress();
			case 3:
				return postShipment.getOrder().getPostalCode();
			case 4:
				return postShipment.getOrder().getPostOffice();

			case 5:
				return postShipment.getOrder().getConstructionType();
				// case 6: return postShipment.getOrder().getComment();
			case 6:
				if (postShipment.getOrderReady() != null) {
					return "Ja";
				}
				return "Nei";
			case 7:
				if (postShipment.getProductAreaGroup() != null) {
					return postShipment.getProductAreaGroup()
							.getProductAreaGroupName();
				}
				return "";
			default:
				throw new IllegalStateException("Unknown column");
			}
		}

		/**
		 * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
		 */
		@Override
		public Class<?> getColumnClass(int column) {
			switch (column) {
			case 0:
				return PostShipment.class;
			case 1:
			case 2:
			case 3:
			case 4:
			case 6:
			case 7:
				return String.class;
			case 5:
				return ConstructionType.class;
			default:
				throw new IllegalStateException("Unknown column");
			}
		}

		public PostShipment getPostShipment(int rowIndex) {
			return (PostShipment) getRow(rowIndex);
		}

	}

	/**
	 * Lager lytter til høyreklikk
	 * 
	 * @param setPostShipment
	 * @return muslytter
	 */
	public MouseListener getRightClickListener(boolean setPostShipment) {
		return setPostShipment ? new RightClickListener(popupMenuPostShipment)
				: new RightClickListener(popupMenuSetTransportOrder);
	}

	/**
	 * Lytter til høyreklikk
	 * 
	 * @author atle.brekka
	 */
	final class RightClickListener extends MouseAdapter {
		private JPopupMenu popupMenu;

		/**
		 * @param setPostShipment
		 */
		public RightClickListener(final JPopupMenu aPopupMenu) {
			popupMenu = aPopupMenu;
		}

		/**
		 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
		 */
		@SuppressWarnings("unchecked")
		@Override
		public void mouseClicked(MouseEvent e) {

			if (SwingUtilities.isRightMouseButton(e)
					&& !transportWeekViewHandler.useListView()) {
				popupMenu.show((JXTable) e.getSource(), e.getX(), e.getY());
			}
		}
	}

	/**
	 * Setter transport på ordre
	 * 
	 * @param setPostShipment
	 * @param window
	 */
	void setTransport(final boolean setPostShipment,
			final WindowInterface window) {
		Transport transport = getTransport(window);
		Transportable transportable = getTransportable(setPostShipment);
		if (transport != Transport.UNKNOWN
				&& !transportIsSent(transport, window)
				&& isWeekValid(transport, window)
				&& isAssemblyValid(transportable, transport, window)) {
			setTransportForTransportable(transportable, transport, window,
					getTransportableList(setPostShipment),
					getSelectionIndex(setPostShipment));
		}

	}

	private boolean isAssemblyValid(final Transportable transportable,
			final Transport transport, final WindowInterface window) {
		boolean assemblyValid = transportable.getAssembly() == null ? true
				: !transport.isAfter(new YearWeek(transportable.getAssembly()
						.getAssemblyYear(), transportable.getAssembly()
						.getAssemblyWeek()));
		if (!assemblyValid) {
			Util.showErrorDialog(window, "Feil",
					"Kan ikke sette transport etter montering");
		}
		return assemblyValid;
	}

	@SuppressWarnings("unchecked")
	private List<Transportable> getTransportableList(
			final boolean setPostShipment) {
		return setPostShipment ? postShipmentList : orderViewHandler
				.getOrderPanelList();
	}

	private int getSelectionIndex(final boolean setPostShipment) {
		return setPostShipment ? tablePostShipment
				.convertRowIndexToModel(postShipmentSelectionList
						.getSelectionIndex()) : orderViewHandler
				.getOrderPanelSelectedOrderIndex();
	}

	private Transportable getTransportable(final boolean setPostShipment) {
		int index = getSelectionIndex(setPostShipment);
		SelectionInList selectionInList = setPostShipment ? postShipmentSelectionList
				: orderViewHandler.getOrderPanelSelectionList();
		return (Transportable) selectionInList.getElementAt(index);
	}

	private boolean isWeekValid(final Transport transport,
			final WindowInterface window) {
		boolean weekValid = !Util.isAfter(new YearWeek(Util.getCurrentYear(),
				Util.getCurrentWeek()), new YearWeek(transport
				.getTransportYear(), transport.getTransportWeek()));
		weekValid = !weekValid ? Util
				.showConfirmDialog(window.getComponent(), "Gammel uke",
						"Du prøver å sette transport til en gammel uke, ønsker du å gjøre dette?")
				: true;
		return weekValid;
	}

	private boolean transportIsSent(final Transport transport,
			final WindowInterface window) {
		boolean isSent = transport.getSent() != null;
		if (isSent) {
			Util.showErrorDialog(window, "Feil",
					"Kan ikke tilordre en transport som allerede er sendt!");
		}
		return isSent;
	}

	@SuppressWarnings("unchecked")
	private Transport getTransport(final WindowInterface window) {
		List<Transport> transportList = transportWeekViewHandler
				.getTransportList();
		Transport transport = (Transport) JOptionPane.showInputDialog(window
				.getComponent(), "Velg transport", "Transport",
				JOptionPane.OK_CANCEL_OPTION, null, transportList.toArray(),
				null);
		return transport == null ? Transport.UNKNOWN : transport;
	}

	@SuppressWarnings("unchecked")
	private void setTransportForTransportable(
			final Transportable transportable, final Transport transport,
			final WindowInterface window,
			final List<Transportable> transportableList, final int index) {
		try {
			OverviewManager<Object> manager = (OverviewManager<Object>) ModelUtil
					.getBean(transportable.getManagerName());
			manager.refreshObject(transportable);
			transportable.setTransport(transport);
			manager.saveObject(transportable);
			manager.lazyLoad(transportable, new LazyLoadEnum[][] {
					{ LazyLoadEnum.ORDER_COMMENTS, LazyLoadEnum.NONE },
					{ LazyLoadEnum.COLLIES, LazyLoadEnum.NONE } });

			TransportViewHandler transportViewHandler = transportWeekViewHandler
					.getTransportViewHandler(transport);
			((TransportOrderTableModel) transportViewHandler
					.getTableModel(window)).insertRow(0, transportable);
			transportableList.remove(index);
			Order order = transportable.getOrder();
			managerRepository.getOrderManager().lazyLoad(
					order,
					new LazyLoadEnum[][] { { LazyLoadEnum.ORDER_LINES,
							LazyLoadEnum.NONE } });
			vismaFileCreator.createVismaFileForTransport(order);
		} catch (ProTransException e) {
			throw new ProTransRuntimeException(e.getMessage());
		}
	}

	/**
	 * Håndterer menyvalg for ordre
	 * 
	 * @author atle.brekka
	 */
	private class MenuItemListenerOrder implements ActionListener {
		/**
         *
         */
		private WindowInterface window;

		/**
		 * @param aWindow
		 */
		public MenuItemListenerOrder(WindowInterface aWindow) {
			window = aWindow;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent actionEvent) {
			if (actionEvent.getActionCommand().equalsIgnoreCase(
					menuItemSetTransportOrder.getText())) {

				setTransport(false, window);

			}
		}

	}

	/**
	 * Håndterer menyvalg for etterleveringer
	 * 
	 * @author atle.brekka
	 */
	private class MenuItemListenerPostShipment implements ActionListener {
		/**
         *
         */
		private WindowInterface window;

		/**
		 * @param aWindow
		 */
		public MenuItemListenerPostShipment(WindowInterface aWindow) {
			window = aWindow;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent actionEvent) {
			if (actionEvent.getActionCommand().equalsIgnoreCase(
					menuItemSetTransportPostShimpment.getText())) {

				setTransport(true, window);

			}
		}

	}

	/**
	 * Henter vindustittel
	 * 
	 * @return tittel
	 */
	public String getWindowTitle() {
		return "Transport";
	}

	/**
	 * Håndterer å legge til etterleveringer
	 * 
	 * @author atle.brekka
	 */
	private class AddPostShipmentAction extends AbstractAction {
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
		public AddPostShipmentAction(WindowInterface aWindow) {
			super("Ny...");
			window = aWindow;

		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent arg0) {
			Util.setWaitCursor(window.getComponent());
			registerDeviation(window);
			Util.setDefaultCursor(window.getComponent());
		}
	}

	/**
	 * Håndterer sletting av etterlevering
	 * 
	 * @author atle.brekka
	 */
	private class DeletePostShipmentAction extends AbstractAction {
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
		public DeletePostShipmentAction(WindowInterface aWindow) {
			super("Slett");
			window = aWindow;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent arg0) {
			try {
				PostShipment postShipment = (PostShipment) postShipmentSelectionList
						.getElementAt(tablePostShipment
								.convertRowIndexToModel(postShipmentSelectionList
										.getSelectionIndex()));
				if (postShipment != null) {
					if (Util.showConfirmDialog(window.getComponent(),
							"Slette?", "Vil du virkelig slette ettersending?")) {
						managerRepository.getPostShipmentManager().lazyLoad(
								postShipment,
								new LazyLoadPostShipmentEnum[] {
										LazyLoadPostShipmentEnum.ORDER_LINES,
										LazyLoadPostShipmentEnum.COLLIES });
						Set<OrderLine> orderLines = postShipment
								.getOrderLines();

						if (orderLines != null) {
							for (OrderLine orderLine : orderLines) {
								if (orderLine.getOrder().getSent() != null) {
									if (!Util
											.showConfirmDialog(
													window.getComponent(),
													"Slette?",
													"Ordre som ordrelinjer hører til har blitt sendt, vil du allikevel slette ettersending?")) {
										return;
									}
								}
								orderLine.setPostShipment(null);
								managerRepository.getOrderLineManager()
										.saveOrderLine(orderLine);
							}
						}
						Set<Colli> collies = postShipment.getCollies();
						if (collies != null) {
							ColliManager colliManager = (ColliManager) ModelUtil
									.getBean("colliManager");
							for (Colli colli : collies) {
								colli.setPostShipment(null);
								colli.setOrder(postShipment.getOrder());
								colliManager.saveColli(colli);
								/*
								 * colliManager .lazyLoadColli( colli, new
								 * LazyLoadColliEnum[]
								 * {LazyLoadColliEnum.ORDER_LINES});
								 */
								// orderLines = colli.getOrderLines();
								/*
								 * for (OrderLine orderLine : orderLines) {
								 * orderLine.setColli(null);
								 * orderLineManager.saveOrderLine(orderLine); }
								 * colliManager.removeObject(colli);
								 */
							}
						}
						Deviation deviation = postShipment.getDeviation();
						Order order = postShipment.getOrder();

						if (deviation != null) {
							if (Util
									.showConfirmDialog(
											window.getComponent(),
											"Slette?",
											"Etterlevering er koblet til avvik som også vil bli slettet, vil du allikevel slette?")) {
								managerRepository.getDeviationManager()
										.removeObject(deviation);
							}
						} else {
							managerRepository.getPostShipmentManager()
									.removeObject(postShipment);
						}

						cacheOrderComments(order);

						refreshPostShipment();
					}
				}
			} catch (ProTransException e) {
				Util.showErrorDialog(window, "Feil", e.getMessage());
				e.printStackTrace();
			}
		}

		private void cacheOrderComments(Order order) throws ProTransException {
			// OrderManager orderManager = (OrderManager)
			// ModelUtil.getBean("orderManager");
			managerRepository.getOrderManager().lazyLoadOrder(order,
					new LazyLoadOrderEnum[] { LazyLoadOrderEnum.COMMENTS });
			order.setStatus(null);
			order.cacheComments();
			managerRepository.getOrderManager().saveOrder(order);
		}
	}

	/**
	 * Registerer avvik
	 * 
	 * @param parentWindow
	 */
	@SuppressWarnings("unchecked")
	void registerDeviation(WindowInterface parentWindow) {
		// henter ordrenr det skal lages ettersending for
		String orderNr = JOptionPane.showInputDialog(parentWindow
				.getComponent(), "Ordrenummer:");
		Order order = null;
		// List<OrderLine> orderLinesWithoutColli = null;
		ArrayListModel orderLinesWithoutColli = null;
		if (orderNr != null) {
			// OrderManager orderManager = (OrderManager)
			// ModelUtil.getBean("orderManager");
			// finner ordre basert på ordrenr
			order = managerRepository.getOrderManager().findByOrderNr(orderNr);

			if (order == null) {
				if (!Util.showConfirmFrame(parentWindow, "Ordre ikke funnet",
						"Ordre ble ikke funnet, vil du fortsette?")) {
					return;
				}
			} else {
				managerRepository.getOrderManager().lazyLoadOrder(
						order,
						new LazyLoadOrderEnum[] {
								LazyLoadOrderEnum.ORDER_LINES,
								LazyLoadOrderEnum.ORDER_LINE_ORDER_LINES });
				// henter ordrelinjer som ikke er pakket
				List<OrderLine> missing = order.getMissingCollies();
				if (missing != null && missing.size() != 0) {
					orderLinesWithoutColli = new ArrayListModel(missing);
				}
			}
		} else {
			if (!Util.showConfirmFrame(parentWindow, "Ordrenr ikke satt",
					"Ordrenr er ikke satt, vil du fortsette?")) {

				return;
			}
		}

		// dersom ordre ikke er funnet må det lages en ordre
		if (order == null) {
			order = createOrder(orderNr, parentWindow);

			if (order.getOrderId() == null) {
				return;
			}
		}

		PostShipment postShipment = new PostShipment();
		postShipment.setOrder(order);
		Deviation deviation = new Deviation();
		deviation.setPostShipment(postShipment);
		deviation.setOrder(order);
		deviation.setCustomerName(order.getCustomer().getFullName());
		deviation.setCustomerNr(order.getCustomer().getCustomerNr());
		deviation.setOrderNr(order.getOrderNr());

		DeviationViewHandler deviationViewHandler = deviationViewHandlerFactory
				.create(null, false, false, false, null, true);

		DeviationModel deviationModel = new DeviationModel(deviation, true);

		if (orderLinesWithoutColli != null) {
			deviationModel.setOrderLines(orderLinesWithoutColli);
		}

		EditDeviationView editDeviationView = new EditDeviationView(false,
				deviationModel, deviationViewHandler, true, true);

		JDialog dialog = new JDialog(ProTransMain.PRO_TRANS_MAIN, "Avvik", true);
		WindowInterface window = new JDialogAdapter(dialog);

		window.add(editDeviationView.buildPanel(window), BorderLayout.CENTER);

		window.pack();
		Util.locateOnScreenCenter(window);
		window.setVisible(true);

		refreshPostShipment();
	}

	/**
	 * Lager ordre
	 * 
	 * @param orderNr
	 * @return ordre
	 */
	private Order createOrder(String orderNr, WindowInterface window) {
		Transport transport = new Transport();
		transport.setTransportName("Historisk");
		// TransportManager transportManager = (TransportManager)
		// ModelUtil.getBean("transportManager");
		List<Transport> transportList = managerRepository.getTransportManager()
				.findByObject(transport);
		if (transportList != null && transportList.size() == 1) {
			transport = transportList.get(0);
		}
		Order order = new Order();
		order.setOrderNr(orderNr);
		order.setTransport(transport);
		orderViewHandler.setEditEnabled(true);
		orderViewHandler.openOrderView(order, false, window);
		/*
		 * EditOrderView editOrderView = new EditOrderView(orderViewHandler,
		 * order, false); WindowInterface dialog = new JDialogAdapter(new
		 * JDialog( ProTransMain.PRO_TRANS_MAIN, "Ordre", true));
		 * dialog.setName("EditOrderView");
		 * dialog.add(editOrderView.buildPanel(dialog)); dialog.pack();
		 * Util.locateOnScreenCenter(dialog); dialog.setVisible(true);
		 */

		return order;
	}

	/**
	 * Håndterer valg i etterlevringstabell
	 * 
	 * @author atle.brekka
	 */
	class SelectionEmpyHandler implements PropertyChangeListener {

		/**
		 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
		 */
		public void propertyChange(PropertyChangeEvent arg0) {
			if (hasWriteAccess()) {
				if (buttonDeletePostShipment != null
						&& postShipmentSelectionList != null) {
					buttonDeletePostShipment
							.setEnabled(postShipmentSelectionList
									.hasSelection());
				}
			}

		}

	}

	/**
	 * Håndterer utskrift av transport til excel
	 * 
	 * @author atle.brekka
	 */
	private class ExcelAction extends AbstractAction {
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
		public ExcelAction(WindowInterface aWindow) {
			super("Excel");
			window = aWindow;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@SuppressWarnings("unchecked")
		public void actionPerformed(ActionEvent arg0) {
			Map<Transport, TransportViewHandler> transportHandlers = transportWeekViewHandler
					.getTransportViewHandlers();
			// Map<Transport, TransportOrderTableModelExcel> models = new
			// HashMap<Transport, TransportOrderTableModelExcel>();
			Map<Transport, TransportOrderTableModel> models = new HashMap<Transport, TransportOrderTableModel>();
			Set<Transport> transports = transportHandlers.keySet();

			List<Transport> sortedTransport = new ArrayList<Transport>(
					transports);
			Collections.sort(sortedTransport, new TransportComparator());
			List<ListTransport> listTransports = getListTransports(sortedTransport);

			Collection<ListTransport> selectedTransports = (Collection<ListTransport>) Util
					.showOptionsDialog(window, listTransports, "Velg", true,
							true);
			if (selectedTransports != null && selectedTransports.size() != 0) {
				for (ListTransport listTransport : selectedTransports) {
					/*
					 * models.put(listTransport.getTransport(), new
					 * TransportOrderTableModelExcel(transportHandlers.get(
					 * listTransport
					 * .getTransport()).getTransportOrderTableModel()));
					 */
					models.put(listTransport.getTransport(), transportHandlers
							.get(listTransport.getTransport())
							.getTransportOrderTableModelForExcel());
				}

				Util.runInThreadWheel(window.getRootPane(), new ExcelGenerator(
						models, window), null);
			}

		}
	}

	/**
	 * Lager en liste med transport som er wrappet av en klasse som har en
	 * tilpasset toString-metode
	 * 
	 * @param transports
	 * @return liste av transport
	 */
	List<ListTransport> getListTransports(Collection<Transport> transports) {
		ArrayList<ListTransport> listTransports = new ArrayList<ListTransport>();
		for (Transport transport : transports) {
			listTransports.add(new ListTransport(transport));
		}
		return listTransports;
	}

	/**
	 * Genererer excel
	 * 
	 * @author atle.brekka
	 */
	private class ExcelGenerator implements Threadable {
		/**
         *
         */
		private Map<Transport, TransportOrderTableModel> models;

		/**
         *
         */
		private WindowInterface window;

		/**
		 * @param someModels
		 * @param aWindow
		 */
		public ExcelGenerator(
				Map<Transport, TransportOrderTableModel> someModels,
				WindowInterface aWindow) {
			models = someModels;
			window = aWindow;
		}

		/**
		 * @see no.ugland.utransprod.util.Threadable#doWhenFinished(java.lang.Object)
		 */
		public void doWhenFinished(Object object) {
			if (object != null) {
				Util.showErrorDialog(window, "Feil", object.toString());
			} else {
				Util
						.showMsgDialog(window.getComponent(), "Excel",
								"Dersom excel ikke starter opp automatisk, ligger fil under katalog excel");
			}

		}

		/**
		 * @see no.ugland.utransprod.util.Threadable#doWork(java.lang.Object[],
		 *      javax.swing.JLabel)
		 */
		public Object doWork(Object[] params, JLabel labelInfo) {
			labelInfo.setText("Generer excel for transport...");
			String errorString = null;
			try {
				String fileName = "transport_"
						+ Util.getCurrentDateAsDateTimeString() + ".xls";
				String excelDirectory = ApplicationParamUtil
						.findParamByName("excel_path");
				ExcelUtil excelUtil = new ExcelUtil();
				excelUtil.showDataInExcelTransport(excelDirectory, fileName,
						null, models);
			} catch (ProTransException e) {
				errorString = e.getMessage();
				e.printStackTrace();
			}

			return errorString;
		}

		/**
		 * @see no.ugland.utransprod.util.Threadable#enableComponents(boolean)
		 */
		public void enableComponents(boolean enable) {
		}

	}

	/**
	 * Håndterer visning av innhold i etterlevering
	 * 
	 * @author atle.brekka
	 */
	private class ShowContentAction extends AbstractAction {
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
		public ShowContentAction(WindowInterface aWindow) {
			window = aWindow;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent arg0) {

			PostShipment postShipment = (PostShipment) postShipmentSelectionList
					.getElementAt(tablePostShipment
							.convertRowIndexToModel(postShipmentSelectionList
									.getSelectionIndex()));

			showContentForPostShipment(postShipment, window);

		}

	}

	private class AddCommentAction extends AbstractAction {
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
		public AddCommentAction(WindowInterface aWindow) {
			window = aWindow;

		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent arg0) {
			PostShipment postShipment = (PostShipment) postShipmentSelectionList
					.getElementAt(tablePostShipment
							.convertRowIndexToModel(postShipmentSelectionList
									.getSelectionIndex()));
			CommentViewHandler commentViewHandler = new CommentViewHandler(
					login, managerRepository.getOrderManager());
			OrderComment newOrderComment = commentViewHandler
					.showAndEditOrderComment(window, null, "orderManager");

			if (newOrderComment != null) {
				Order order = postShipment.getOrder();

				if (!Hibernate.isInitialized(order.getOrderComments())) {
					orderViewHandler
							.lazyLoadOrder(
									order,
									new LazyLoadOrderEnum[] { LazyLoadOrderEnum.COMMENTS });
				}
				order.addOrderComment(newOrderComment);
				order.cacheComments();
				newOrderComment.setDeviation(postShipment.getDeviation());
				try {
					orderViewHandler.getOrderManager().saveOrder(order);

				} catch (ProTransException e) {
					Util.showErrorDialog(window, "Feil", e.getMessage());
					e.printStackTrace();
				}
				postShipment.cacheComments();
				PostShipmentManager postShipmentManager = (PostShipmentManager) ModelUtil
						.getBean("postShipmentManager");
				postShipmentManager.savePostShipment(postShipment);
			}

		}

	}

	/**
	 * Viser innhold i etterlevering
	 * 
	 * @param postShipment
	 * @param window
	 */
	public static void showContentForPostShipment(PostShipment postShipment,
			WindowInterface window) {
		PostShipmentManager postShipmentManager = (PostShipmentManager) ModelUtil
				.getBean("postShipmentManager");
		if (postShipment != null) {
			postShipmentManager
					.lazyLoad(
							postShipment,
							new LazyLoadPostShipmentEnum[] { LazyLoadPostShipmentEnum.ORDER_LINES });
			Set<OrderLine> content = postShipment.getOrderLines();
			if (content != null) {
				List<OrderLineWrapper> orderLineList = Util
						.getOrderLineWrapperList(content);
				Util.showOptionsDialog(window, orderLineList, "Innhold", false,
						false);
			}
		}
	}

	/**
	 * Genererer pakkliste for etterlevering
	 * 
	 * @author atle.brekka
	 */
	private class GeneratePacklistAction extends AbstractAction {
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
		public GeneratePacklistAction(WindowInterface aWindow) {
			window = aWindow;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent arg0) {
			if (postShipmentSelectionList.getSelectionIndex() >= 0) {
				PostShipment postShipment = (PostShipment) postShipmentSelectionList
						.getElementAt(tablePostShipment
								.convertRowIndexToModel(postShipmentSelectionList
										.getSelectionIndex()));

				Util.runInThreadWheel(window.getRootPane(),
						new PacklistPrinter(window, postShipment), null);

			}
		}
	}

	/**
	 * Sjekker om bruker har skriveakksess
	 * 
	 * @return true dersom bruker har skriveakksess
	 */
	public boolean hasWriteAccess() {
		return UserUtil.hasWriteAccess(login.getUserType(), "Transport");
	}

	/**
	 * Wrapper klasse rundt transport for å lage en passende toString-metode
	 * 
	 * @author atle.brekka
	 */
	private class ListTransport {
		/**
         *
         */
		private Transport transport;

		/**
		 * @param aTransport
		 */
		public ListTransport(Transport aTransport) {
			transport = aTransport;
		}

		/**
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			if (transport.getLoadingDate() != null) {
				return transport.toString()
						+ " "
						+ Util.SHORT_DATE_FORMAT.format(transport
								.getLoadingDate());
			}
			return transport.toString();
		}

		/**
		 * Henter transport
		 * 
		 * @return transport
		 */
		public Transport getTransport() {
			return transport;
		}
	}

	/**
	 * @return true dersom dispose skal kjøres
	 */
	public boolean getDisposeOnClose() {
		return disposeOnClose;
	}

	/**
	 * Henter antall transporter
	 * 
	 * @return antall
	 */
	public int getNumberOfTransports() {
		return transportWeekView.getNumberOfTransport();
	}

	/**
	 * Setter eller fjerner filter
	 */
	public void handleFilter() {
		// gi beskjed til alle transportlister at de skal filtrere
		if (transportWeekViewHandler != null) {
			ProductAreaGroup group = (ProductAreaGroup) productAreaGroupModel
					.getValue(ProductAreaGroupModel.PROPERTY_PRODUCT_AREA_GROUP);
			PrefsUtil.setInvisibleColumns(group.getProductAreaGroupName(),
					tablePostShipment.getName(), tablePostShipment);
			/*
			 * if (group != null) { group = group.getProductAreaGroup(); }
			 */
			transportWeekViewHandler.setFilterSent(
					!checkBoxFilter.isSelected(), group);
			orderViewHandler.handleFilter(group, OrderPanelTypeEnum.NEW_ORDERS);

			if (group != null
					&& !group.getProductAreaGroupName()
							.equalsIgnoreCase("Alle")) {
				Filter[] filters = new Filter[] { new PatternFilter(group
						.getProductAreaGroupName(), Pattern.CASE_INSENSITIVE, 7) };
				FilterPipeline filterPipeline = new FilterPipeline(filters);
				tablePostShipment.setFilters(filterPipeline);
			} else {
				tablePostShipment.setFilters(null);
			}
			tablePostShipment.repaint();
			setTransportSum();
		}

	}

	/**
	 * Håndterer setting av filter
	 * 
	 * @author atle.brekka
	 */
	class FilterActionListener implements ActionListener {

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent event) {
			handleFilter();

		}

	}

	/**
	 * Håndterer endring av filter
	 * 
	 * @author atle.brekka
	 */
	class FilterPropertyChangeListener implements PropertyChangeListener {

		/**
		 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
		 */
		public void propertyChange(PropertyChangeEvent evt) {
			handleFilter();

		}

	}

	public void cleanUp() {

	}

	private class ProductAreaChangeListener implements PropertyChangeListener {

		public void propertyChange(PropertyChangeEvent evt) {
			changeProductAreaGroup();
			handleFilter();

		}

	}

	private final void changeProductAreaGroup() {
		if (transportWeekView != null) {
			transportWeekView
					.setProductAreaGroup((ProductAreaGroup) productAreaGroupModel
							.getValue(ProductAreaGroupModel.PROPERTY_PRODUCT_AREA_GROUP));
			transportWeekView.changeWeek(null);
		}
	}

	public String getProductAreaGroupName() {
		return ((ProductAreaGroup) productAreaGroupModel
				.getValue(ProductAreaGroupModel.PROPERTY_PRODUCT_AREA_GROUP))
				.getProductAreaGroupName();
	}

	class MenuItemListenerShowDeviation implements ActionListener {
		/**
         *
         */
		private WindowInterface window1;

		/**
		 * @param aWindow
		 */
		public MenuItemListenerShowDeviation(WindowInterface aWindow) {
			window1 = aWindow;

		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent actionEvent) {
			Util.setWaitCursor(window1);
			showDeviation(window1);
			Util.setDefaultCursor(window1);
		}

	}

	private void showDeviation(WindowInterface aWindow) {
		PostShipment postShipment = (PostShipment) postShipmentSelectionList
				.getElementAt(tablePostShipment
						.convertRowIndexToModel(postShipmentSelectionList
								.getSelectionIndex()));
		// PostShipment postShipment = (PostShipment)
		// postShipmentSelectionList.getSelection();

		DeviationViewHandler deviationViewHandler = deviationViewHandlerFactory
				.create(null, true, false, true, null, true);
		deviationViewHandler
				.showDeviation(postShipment.getDeviation(), aWindow);
	}

	private class ListAction extends AbstractAction {
		public ListAction() {
			super("Liste");
		}

		public void actionPerformed(ActionEvent e) {
			transportWeekViewHandler.setUseListView(((JCheckBox) e.getSource())
					.isSelected());
			transportWeekView.changeWeek(null);
		}
	}

	public String getSelectedOrderNr() {
		Transportable transportable = getTransportable(false);

		return transportable != null ? transportable.getOrderNr() : "";
	}
}
