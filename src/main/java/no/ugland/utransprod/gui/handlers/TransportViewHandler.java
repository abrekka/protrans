package no.ugland.utransprod.gui.handlers;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.ColorHighlighter;
import org.jdesktop.swingx.decorator.Filter;
import org.jdesktop.swingx.decorator.FilterPipeline;
import org.jdesktop.swingx.decorator.HighlighterFactory;
import org.jdesktop.swingx.decorator.PatternFilter;
import org.jdesktop.swingx.decorator.PatternPredicate;

import com.google.inject.Inject;
import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.adapter.ComboBoxAdapter;
import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.binding.beans.PropertyConnector;
import com.jgoodies.binding.list.ArrayListModel;
import com.jgoodies.binding.list.SelectionInList;
import com.toedter.calendar.JDateChooser;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.ProTransRuntimeException;
import no.ugland.utransprod.gui.IconEnum;
import no.ugland.utransprod.gui.LevertTransportView;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.ProductAreaGroupProvider;
import no.ugland.utransprod.gui.SentTransportView;
import no.ugland.utransprod.gui.SplitOrderView;
import no.ugland.utransprod.gui.TransportWeekView;
import no.ugland.utransprod.gui.UBColumnControlPopup;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.checker.StatusCheckerInterface;
import no.ugland.utransprod.gui.edit.AbstractEditView;
import no.ugland.utransprod.gui.model.ColorEnum;
import no.ugland.utransprod.gui.model.TextPaneRenderer;
import no.ugland.utransprod.gui.model.TextPaneRendererTransport;
import no.ugland.utransprod.gui.model.TransportListable;
import no.ugland.utransprod.gui.model.TransportModel;
import no.ugland.utransprod.gui.model.TransportSelectionListener;
import no.ugland.utransprod.gui.model.Transportable;
import no.ugland.utransprod.model.Colli;
import no.ugland.utransprod.model.Deviation;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.PostShipment;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.model.Supplier;
import no.ugland.utransprod.model.Transport;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.service.OverviewManager;
import no.ugland.utransprod.service.PostShipmentManager;
import no.ugland.utransprod.service.SupplierManager;
import no.ugland.utransprod.service.TransportManager;
import no.ugland.utransprod.service.VismaFileCreator;
import no.ugland.utransprod.service.enums.LazyLoadEnum;
import no.ugland.utransprod.service.enums.LazyLoadOrderEnum;
import no.ugland.utransprod.service.enums.LazyLoadPostShipmentEnum;
import no.ugland.utransprod.service.enums.LazyLoadTransportEnum;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.OrderLineWrapper;
import no.ugland.utransprod.util.PrefsUtil;
import no.ugland.utransprod.util.Threadable;
import no.ugland.utransprod.util.UserUtil;
import no.ugland.utransprod.util.Util;
import no.ugland.utransprod.util.YearWeek;
import no.ugland.utransprod.util.report.TransportLetter;
import no.ugland.utransprod.util.report.TransportLetterSelector;

/**
 * Hjelpeklasse for visning en transport.
 * 
 * @author atle.brekka
 */
public class TransportViewHandler extends AbstractViewHandler<Transport, TransportModel>
		implements ProductAreaGroupProvider, OrderNrProvider {
	private static final Logger LOGGER = Logger.getLogger(TransportViewHandler.class);

	private static final long serialVersionUID = 1L;

	private final ArrayListModel transportableList;

	final SelectionInList transportableSelectionList;

	private List<TransportSelectionListener> selectionListeners = new ArrayList<TransportSelectionListener>();

	private JCheckBox checkBoxSelection;

	PresentationModel transportPresentationModel;

	private OrderViewHandler orderViewHandler;

	private JXTable tableOrders;

	private Map<String, StatusCheckerInterface<Transportable>> statusCheckers;

	private TransportOrderTableModel transportOrderTableModel;

	private List<PropertyChangeListener> bufferingListeners = new ArrayList<PropertyChangeListener>();

	private List<PropertyChangeListener> sentListeners = new ArrayList<PropertyChangeListener>();

	private JPopupMenu popupMenuTransport;

	private JMenuItem menuItemRemoveTransport;

	private JMenuItem menuItemSetSent;
	private JMenuItem menuItemSetLevert;
	private JMenuItem menuItemSetTransportOrder;
	private JMenuItem menuItemUpdateStatus;

	private JMenuItem menuItemMissing;

	private JMenuItem menuItemDeviation;

	private JMenuItem menuItemReport;

	private JMenuItem menuItemPacklist;

	private JMenuItem menuItemSplitOrder;
	private JMenuItem menuItemShowDeviation;
	private JMenuItem menuItemShowTakstolInfo;

	boolean handlingOrders = false;

	private static ArrayListModel transportSupplierList;

	private ArrayListModel transportEmployeeList;

	// private ProductAreaGroup currentProductAreaGroup;

	private static boolean isTest = false;

	private ManagerRepository managerRepository;
	private Login login;
	private DeviationViewHandlerFactory deviationViewHandlerFactory;
	private ShowTakstolInfoActionFactory showTakstolInfoActionFactory;

	private VismaFileCreator vismaFileCreator;

	/**
	 * @param aOrderViewHandler
	 * @param aApplicationUser
	 * @param userType
	 */
	@Inject
	public TransportViewHandler(final OrderViewHandlerFactory aOrderViewHandlerFactory, final Login aLogin,
			final ManagerRepository aManagerRepository, final DeviationViewHandlerFactory aDeviationViewHandlerFactory,
			ShowTakstolInfoActionFactory aShowTakstolInfoActionFactory, final VismaFileCreator vismaFileCreator) {
		super("Transport", aManagerRepository.getTransportManager(), true, aLogin.getUserType(), true);
		this.vismaFileCreator = vismaFileCreator;
		showTakstolInfoActionFactory = aShowTakstolInfoActionFactory;
		deviationViewHandlerFactory = aDeviationViewHandlerFactory;
		managerRepository = aManagerRepository;
		login = aLogin;
		statusCheckers = Util.getStatusCheckersTransport(managerRepository);
		transportableList = new ArrayListModel();
		transportableSelectionList = new SelectionInList((ListModel) transportableList);
		orderViewHandler = aOrderViewHandlerFactory.create(true);

		setUpMenus();

		setupTransportSupplierList();
		transportEmployeeList = new ArrayListModel();
	}

	private void setupTransportSupplierList() {
		if (transportSupplierList == null) {

			transportSupplierList = new ArrayListModel(
					managerRepository.getSupplierManager().findActiveByTypeName("Transport", "supplierName"));
		}
	}

	private void setUpMenus() {
		popupMenuTransport = new JPopupMenu("Fjern transport");
		popupMenuTransport.setName("PopupMenuTransport");
		menuItemRemoveTransport = new JMenuItem("Fjern transport");
		menuItemRemoveTransport.setName("MenuItemRemoveTransport");
		menuItemRemoveTransport.setEnabled(hasWriteAccess());
		menuItemSetSent = new JMenuItem("Sett sent");
		menuItemSetLevert = new JMenuItem("Sett levert");
		menuItemSetLevert.setEnabled(hasWriteAccess());
		menuItemSetLevert.setName("MenuItemSetLevert");
		menuItemUpdateStatus = new JMenuItem("Oppdater status");
		menuItemSetSent.setEnabled(hasWriteAccess());
		menuItemSetSent.setName("MenuItemSetSent");
		menuItemMissing = new JMenuItem("Se mangler...");
		menuItemMissing.setName("MenuItemMissing");
		menuItemReport = new JMenuItem("Fraktbrev...");
		menuItemReport.setName("MenuItemTransportLetter");
		menuItemPacklist = new JMenuItem("Pakkliste...");
		menuItemDeviation = new JMenuItem("Registrere avvik...");
		menuItemSplitOrder = new JMenuItem("Splitt ordre...");
		menuItemSplitOrder.setName("MenuItemSplitOrder");
		menuItemShowDeviation = new JMenuItem("Se avviksskjema...");
		menuItemShowDeviation.setName("MenuItemShowDeviation");
		menuItemShowTakstolInfo = new JMenuItem("Takstolinfo...");
		menuItemShowTakstolInfo.setName("MenuItemShowTakstolInfo");

		menuItemSetTransportOrder = new JMenuItem("Endre transport...");

		popupMenuTransport.add(menuItemRemoveTransport);
		popupMenuTransport.add(menuItemSetSent);
		popupMenuTransport.add(menuItemSetLevert);
		popupMenuTransport.add(menuItemMissing);
		popupMenuTransport.add(menuItemReport);
		popupMenuTransport.add(menuItemSplitOrder);
		popupMenuTransport.add(menuItemShowTakstolInfo);
		popupMenuTransport.add(menuItemUpdateStatus);
		popupMenuTransport.add(menuItemSetTransportOrder);

		menuItemRemoveTransport.setEnabled(hasWriteAccess());
	}

	/**
	 * Lagern komboboks for leverandør.
	 * 
	 * @return komboboks
	 */
	public final JComboBox getComboBoxSupplier(final int numberOf) {
		JComboBox tmp = new JComboBox(new ComboBoxAdapter((ListModel) transportSupplierList,
				transportPresentationModel.getBufferedModel(TransportModel.PROPERTY_SUPPLIER)));

		tmp.addItemListener(new SupplierSelectionListener());
		tmp.setEnabled(hasWriteAccess());
		tmp.setName("ComboBoxSupplier" + numberOf);
		return tmp;
	}

	/**
	 * Lager komboboks for ansatt.
	 * 
	 * @return komboboks
	 */
	public final JComboBox getComboBoxEmployee() {
		initEmployeeCombo();
		JComboBox comboBox = new JComboBox(new ComboBoxAdapter((ListModel) transportEmployeeList,
				transportPresentationModel.getBufferedModel(TransportModel.PROPERTY_EMPLOYEE)));
		comboBox.setEnabled(hasWriteAccess());
		return comboBox;
	}

	public final void addBufferingListener(final PropertyChangeListener listener) {
		bufferingListeners.add(listener);
	}

	/**
	 * Legger til lytter for sending.
	 * 
	 * @param listener
	 */
	public final void addSentListener(final PropertyChangeListener listener) {
		sentListeners.add(listener);
	}

	/**
	 * Forteller at det buffer har forandret seg.
	 */
	final void fireBufferChange() {
		for (PropertyChangeListener listener : bufferingListeners) {
			listener.propertyChange(new PropertyChangeEvent(transportPresentationModel.getBean(), "buffering",
					transportPresentationModel.getBean(), transportPresentationModel.isBuffering()));
		}
	}

	/**
	 * Forteller at sentstatus har endret seg.
	 */
	final void fireSentChange() {
		for (PropertyChangeListener listener : sentListeners) {
			listener.propertyChange(new PropertyChangeEvent(transportPresentationModel.getBean(), "sent", null,
					transportPresentationModel.isBuffering()));
		}
	}

	public final JLabel getLabelName() {
		JLabel label = BasicComponentFactory
				.createLabel(transportPresentationModel.getModel(TransportModel.PROPERTY_TRANSPORT_NAME));
		String comment = (String) transportPresentationModel.getValue(TransportModel.PROPERTY_TRANSPORT_COMMENT);
		if (comment != null) {
			label.setIcon(IconEnum.ICON_COMMENT.getIcon());
			label.setToolTipText(comment);
		}
		return label;
	}

	/**
	 * Lager datovelger.
	 * 
	 * @return datovelger
	 */
	public final JDateChooser getDatePicker() {
		JDateChooser datePicker = new JDateChooser();
		datePicker.setName("DatePicker");
		datePicker.setEnabled(hasWriteAccess());

		PropertyConnector connOrderDate = new PropertyConnector(datePicker, "date",
				transportPresentationModel.getBufferedModel(TransportModel.PROPERTY_LOADING_DATE), "value");

		connOrderDate.updateProperty1();

		return datePicker;
	}

	/**
	 * Setter presentasjonsmodell.
	 * 
	 * @param aPresentationModel
	 */
	public final void setPresentationModel(final PresentationModel aPresentationModel) {
		transportPresentationModel = aPresentationModel;
	}

	/**
	 * Legger til lytter for selektering av transport.
	 * 
	 * @param listener
	 */
	public final void addTransportSelectionListener(final TransportSelectionListener listener) {
		selectionListeners.add(listener);
	}

	public final void removeTransportSelectionListener(final TransportSelectionListener listener) {
		selectionListeners.remove(listener);
	}

	/**
	 * Henter statussjekker for gavl.
	 * 
	 * @return statussjekker
	 */
	private StatusCheckerInterface<Transportable> getGavlChecker() {
		StatusCheckerInterface<Transportable> gavlChecker = statusCheckers.get("Gavl");
		if (gavlChecker == null) {
			LOGGER.error("Det er ikke definert statussjekker for gavl");
			throw new ProTransRuntimeException("Det er ikke definert statussjekker for gavl");
		}
		return gavlChecker;
	}

	/**
	 * Henter statussjekker for takstol.
	 * 
	 * @return statussjekker
	 */
	private StatusCheckerInterface<Transportable> getTakstolChecker() {
		StatusCheckerInterface<Transportable> takstolChecker = statusCheckers.get("Takstol");
		if (takstolChecker == null) {
			LOGGER.error("Det er ikke definert statussjekker for takstol");
			throw new ProTransRuntimeException("Det er ikke definert statussjekker for takstol");
		}
		return takstolChecker;
	}

	/**
	 * Henter statussjekker for takstein.
	 * 
	 * @return statussjekker
	 */
	private StatusCheckerInterface<Transportable> getSteinChecker() {
		StatusCheckerInterface<Transportable> steinChecker = statusCheckers.get("Stein");
		if (steinChecker == null) {
			LOGGER.error("Det er ikke definert statussjekker for stein");
			throw new ProTransRuntimeException("Det er ikke definert statussjekker for stein");
		}
		return steinChecker;
	}

	/**
	 * Henter statussjekker for gulvspon.
	 * 
	 * @return statussjekker
	 */
	private StatusCheckerInterface<Transportable> getGulvsponChecker() {
		StatusCheckerInterface<Transportable> gulvsponChecker = statusCheckers.get("Gulvspon");
		if (gulvsponChecker == null) {
			LOGGER.error("Det er ikke definert statussjekker for gulvspon");
			throw new ProTransRuntimeException("Det er ikke definert statussjekker for gulvspon");
		}
		return gulvsponChecker;
	}

	private void refresh(final Transport transport) {
		if (transport != null) {
			overviewManager.refreshObject(transport);
		}

	}

	/**
	 * Initierer ordre.
	 * 
	 * @param transportables
	 * @param window
	 */
	// private void initOrders(List<Transportable> transportables,
	// WindowInterface window) {
	// if (transportables != null) {
	// PostShipmentManager postShipmentManager = (PostShipmentManager)
	// ModelUtil.getBean("postShipmentManager");
	// Set<String> checkers = statusCheckers.keySet();
	// Map<String, String> statusMap;
	//
	// String status;
	// StatusCheckerInterface<Transportable> checker;
	// boolean orderLoaded = false;
	// boolean needToSave = false;
	//
	// for (Transportable transportable : transportables) {
	// orderLoaded = false;
	// needToSave = false;
	// statusMap = Util.createStatusMap(transportable.getStatus());
	// for (String checkerName : checkers) {
	// checker = statusCheckers.get(checkerName);
	// status = statusMap.get(checker.getArticleName());
	//
	// if (status == null) {
	// needToSave = true;
	// if (!orderLoaded && transportable instanceof Order) {
	// orderViewHandler.lazyLoadOrder((Order) transportable, new
	// LazyLoadOrderEnum[] {
	// LazyLoadOrderEnum.ORDER_LINE_ATTRIBUTES, LazyLoadOrderEnum.ORDER_LINES
	// // LazyLoadOrderEnum.COLLIES,
	// // LazyLoadOrderEnum.ORDER_LINES,
	// // LazyLoadOrderEnum.ORDER_LINE_ORDER_LINES,
	// // LazyLoadOrderEnum.COMMENTS
	// });
	// orderLoaded = true;
	// } else if (!orderLoaded && transportable instanceof PostShipment) {
	// postShipmentManager.lazyLoad((PostShipment) transportable,
	// new LazyLoadPostShipmentEnum[] { LazyLoadPostShipmentEnum.ORDER_LINES,
	// LazyLoadPostShipmentEnum.ORDER_LINE_ORDER_LINES,
	// // LazyLoadPostShipmentEnum.COLLIES,
	// // LazyLoadPostShipmentEnum.ORDER_LINES,
	// // LazyLoadPostShipmentEnum.ORDER_LINE_ORDER_LINES,
	// // LazyLoadPostShipmentEnum.ORDER_COMMENTS
	// });
	// orderLoaded = true;
	// }
	// status = checker.getArticleStatus(transportable);
	//
	// }
	// statusMap.put(checker.getArticleName(), status);
	//
	// }
	// transportable.setStatus(Util.statusMapToString(statusMap));
	//
	// if (transportable.getComment() == null) {
	// needToSave = true;
	// cacheComment(transportable, window, !orderLoaded);
	// orderLoaded = true;
	// }
	// // if (transportable.getGarageColliHeight() == null) {
	// // needToSave = true;
	// // cacheGarageColliHeight(transportable, window, !orderLoaded);
	// // orderLoaded = true;
	// // }
	// // if (transportable.getTakstolHeight() == null) {
	// // needToSave = true;
	// // cacheTakstolHeight(transportable, window, !orderLoaded);
	// // orderLoaded = true;
	// // }
	//
	// if (needToSave) {
	// if (transportable instanceof Order) {
	// try {
	// orderViewHandler.getOrderManager().saveOrder((Order) transportable);
	// } catch (ProTransException e) {
	// Util.showErrorDialog(window, "Feil", e.getMessage());
	// e.printStackTrace();
	// }
	// } else {
	// postShipmentManager.savePostShipment((PostShipment) transportable);
	// }
	// }
	// }
	// }
	// }

	@SuppressWarnings("unchecked")
	private void updateTransportableList(boolean lazyload) {
		if (lazyload) {
			((TransportManager) overviewManager).lazyLoadTransport(
					((TransportModel) transportPresentationModel.getBean()).getObject(),
					new LazyLoadTransportEnum[] { LazyLoadTransportEnum.ORDER, LazyLoadTransportEnum.POST_SHIPMENTS,
							LazyLoadTransportEnum.ORDER_LINES });
		}

		Set<Transportable> transportables = (Set<Transportable>) transportPresentationModel
				.getBufferedValue(TransportModel.PROPERTY_TRANSPORTABLES);
		transportableList.clear();

		if (transportables != null) {
			transportableList.addAll(transportables);

		}
	}

	public static ColorHighlighter getProbabilityHighlighter(final JXTable table, final String columnName) {
		return new ColorHighlighter(new PatternPredicate("90", table.getColumnExt(columnName).getModelIndex()),
				ColorEnum.GREY.getColor(), null);
	}

	public static ColorHighlighter getNotSentHighlighter(final JXTable table, final String columnName) {
		return new ColorHighlighter(new PatternPredicate("!", table.getColumnExt(columnName).getModelIndex()),
				ColorEnum.RED.getColor(), null);
	}

	public static ColorHighlighter getReadyHighlighter(final JXTable table, final String columnName) {
		return new ColorHighlighter(new PatternPredicate("Ja", table.getColumnExt(columnName).getModelIndex()),
				ColorEnum.GREEN.getColor(), null);
	}

	public static ColorHighlighter getStartedPackingHighlighter(final JXTable table, final String columnName) {
		return new ColorHighlighter(new PatternPredicate("Ja", table.getColumnExt(columnName).getModelIndex()),
				ColorEnum.YELLOW.getColor(), null);
	}

	/**
	 * Lager tabell for ordre.
	 * 
	 * @param window
	 * @param numberOf
	 * @return tabell
	 */
	@SuppressWarnings("unchecked")
	public JXTable getTableOrders(WindowInterface window, int numberOf,
			TransportWeekViewHandler transportWeekViewHandler, TransportWeekView transportWeekView) {// ,
		// final
		// ProductAreaGroup
		// productAreaGroup)
		// {
		// currentProductAreaGroup = productAreaGroup;
		updateTransportableList(false);

		/**************************
		 * prøver å fjerne denne
		 ***********************/
		// initOrders(transportableList, window);

		tableOrders = new JXTable();
		transportOrderTableModel = new TransportOrderTableModel(transportableSelectionList, transportableList,
				getGavlChecker(), getTakstolChecker(), getSteinChecker(), getGulvsponChecker(),
				TransportOrderTableModel.TransportColumn.ForExcel.TABLE);
		tableOrders.setModel(transportOrderTableModel);
		tableOrders.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tableOrders.setSelectionModel(
				new SingleListSelectionAdapter(transportableSelectionList.getSelectionIndexHolder()));
		tableOrders.setColumnControlVisible(true);
		tableOrders.setColumnControl(new UBColumnControlPopup(tableOrders, this));
		tableOrders.setSearchable(null);
		tableOrders.addMouseListener(new TableClickHandler(window));

		tableOrders.setRowHeight(40);
		tableOrders.setShowGrid(true);
		tableOrders.setShowVerticalLines(true);

		List<TransportOrderTableModel.TransportColumn> columns = TransportOrderTableModel.TransportColumn
				.getTableColumns();

		for (TransportOrderTableModel.TransportColumn column : columns) {
			column.setCellRenderer(tableOrders);
			column.setPrefferedWidth(tableOrders);
		}

		tableOrders.getColumnModel().getColumn(tableOrders.getColumnExt("Ikke levert").getModelIndex())
				.setCellRenderer(new TextPaneRendererLevert());

		tableOrders.addHighlighter(HighlighterFactory.createAlternateStriping());

		tableOrders.addHighlighter(TransportViewHandler.getStartedPackingHighlighter(tableOrders, "Klar"));
		tableOrders.addHighlighter(TransportViewHandler.getReadyHighlighter(tableOrders, "Komplett"));
		tableOrders.addHighlighter(TransportViewHandler.getNotSentHighlighter(tableOrders, "Ikke opplastet"));
		tableOrders.addHighlighter(TransportViewHandler.getProbabilityHighlighter(tableOrders, "Sannsynlighet"));

		tableOrders.setShowGrid(true);
		tableOrders.setRolloverEnabled(false);

		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setHorizontalAlignment(SwingConstants.CENTER);
		Font font = renderer.getFont().deriveFont(Font.BOLD);

		renderer.setFont(font);
		tableOrders.getColumnModel().getColumn(2).setCellRenderer(renderer);
		tableOrders.setDefaultRenderer(String.class, renderer);

		addMenuListeners(window, transportWeekViewHandler, transportWeekView);

		if (isTest) {
			tableOrders.setName(TableEnum.TABLETRANSPORTORDERS.getTableName() + numberOf);
		} else {
			tableOrders.setName(TableEnum.TABLETRANSPORTORDERS.getTableName());
		}
		PrefsUtil.setInvisibleColumns(ProductAreaGroup.UNKNOWN.getProductAreaGroupName(),
				TableEnum.TABLETRANSPORTORDERS.getTableName(), tableOrders);
		// tableOrders.getColumnExt(13).setVisible(false);

		return tableOrders;

	}

	private void addMenuListeners(WindowInterface window1, TransportWeekViewHandler transportWeekViewHandler,
			TransportWeekView transportWeekView) {
		menuItemMissing.addActionListener(new MenuItemListenerMissing(window1));
		menuItemReport.addActionListener(new MenuItemListenerReport(window1));
		menuItemPacklist.addActionListener(new MenuItemListenerPacklist(window1));

		menuItemRemoveTransport.addActionListener(new MenuItemListenerRemoveTransport(window1));
		menuItemDeviation.addActionListener(new MenuItemListenerDeviation(window1));
		menuItemSplitOrder.addActionListener(new MenuItemListenerSplitOrder(window1));
		menuItemShowDeviation.addActionListener(new MenuItemListenerShowDeviation(window1));
		menuItemShowTakstolInfo.addActionListener(showTakstolInfoActionFactory.create(this, window1));

		menuItemUpdateStatus.addActionListener(new MenuItemListenerUpdateStatus(window1));
		menuItemSetTransportOrder
				.addActionListener(new MenuItemListenerOrder(window, transportWeekViewHandler, transportWeekView));
	}

	private class MenuItemListenerOrder implements ActionListener {
		/**
		    *
		    */
		private WindowInterface window;
		private TransportWeekViewHandler transportWeekViewHandler;
		private TransportWeekView transportWeekView;

		/**
		 * @param aWindow
		 */
		public MenuItemListenerOrder(WindowInterface aWindow, TransportWeekViewHandler transportWeekViewHandler,
				TransportWeekView transportWeekView) {
			window = aWindow;
			this.transportWeekViewHandler = transportWeekViewHandler;
			this.transportWeekView = transportWeekView;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent actionEvent) {
			if (actionEvent.getActionCommand().equalsIgnoreCase(menuItemSetTransportOrder.getText())) {
				Util.setWaitCursor(window);
				setTransport(false, window, transportWeekViewHandler);
				transportWeekView.changeWeek(null, false, null);
				Util.setDefaultCursor(window);
			}

		}

	}

	void setTransport(final boolean setPostShipment, final WindowInterface window,
			TransportWeekViewHandler transportWeekViewHandler) {
		int index = tableOrders.convertRowIndexToModel(transportableSelectionList.getSelectionIndex());
		if (index != -1) {
			Transport transport = getTransport(window, transportWeekViewHandler);
			Transportable transportable = (Transportable) transportableSelectionList.getElementAt(index);

			if (transport != Transport.UNKNOWN && !transportIsSent(transport, window) && isWeekValid(transport, window)
					&& isAssemblyValid(transportable, transport, window)) {
				setTransportForTransportable(transportable, transport, window,
						// getTransportableList(setPostShipment),
						// getSelectionIndex(setPostShipment),
						transportWeekViewHandler);
			}
		}

	}

	private void setTransportForTransportable(final Transportable transportable, final Transport transport,
			final WindowInterface window,
			// final List<Transportable> transportableList,
			// final int index,
			TransportWeekViewHandler transportWeekViewHandler) {
		try {
			OverviewManager<Object> manager = (OverviewManager<Object>) ModelUtil
					.getBean(transportable.getManagerName());
			manager.refreshObject(transportable);
			transportable.setTransport(transport);
			manager.saveObject(transportable);
			manager.lazyLoad(transportable, new LazyLoadEnum[][] { { LazyLoadEnum.ORDER_COMMENTS, LazyLoadEnum.NONE },
					{ LazyLoadEnum.COLLIES, LazyLoadEnum.NONE } });

			TransportViewHandler transportViewHandler = transportWeekViewHandler.getTransportViewHandler(transport);
			((TransportOrderTableModel) transportViewHandler.getTableModel(window)).insertRow(0, transportable);
			// transportableList.remove(index);
			Order order = transportable.getOrder();
			managerRepository.getOrderManager().lazyLoad(order,
					new LazyLoadEnum[][] { { LazyLoadEnum.ORDER_LINES, LazyLoadEnum.NONE } });
			vismaFileCreator.createVismaFileForTransport(order);
		} catch (ProTransException e) {
			throw new ProTransRuntimeException(e.getMessage());
		}
	}

	private boolean isAssemblyValid(final Transportable transportable, final Transport transport,
			final WindowInterface window) {
		boolean assemblyValid = transportable.getAssembly() == null ? true
				: !transport.isAfter(new YearWeek(transportable.getAssembly().getAssemblyYear(),
						transportable.getAssembly().getAssemblyWeek()));
		if (!assemblyValid) {
			Util.showErrorDialog(window, "Feil", "Kan ikke sette transport etter montering");
		}
		return assemblyValid;
	}

	private boolean isWeekValid(final Transport transport, final WindowInterface window) {
		boolean weekValid = !Util.isAfter(new YearWeek(Util.getCurrentYear(), Util.getCurrentWeek()),
				new YearWeek(transport.getTransportYear(), transport.getTransportWeek()));
		weekValid = !weekValid ? Util.showConfirmDialog(window.getComponent(), "Gammel uke",
				"Du prøver å sette transport til en gammel uke, ønsker du å gjøre dette?") : true;
		return weekValid;
	}

	private boolean transportIsSent(final Transport transport, final WindowInterface window) {
		boolean isSent = transport.getSent() != null;
		if (isSent) {
			Util.showErrorDialog(window, "Feil", "Kan ikke tilordre en transport som allerede er sendt!");
		}
		return isSent;
	}

	private Transport getTransport(final WindowInterface window, TransportWeekViewHandler transportWeekViewHandler) {
		List<Transport> transportList = transportWeekViewHandler.getTransportList();
		Transport transport = (Transport) JOptionPane.showInputDialog(window.getComponent(), "Velg transport",
				"Transport", JOptionPane.OK_CANCEL_OPTION, null, transportList.toArray(), null);
		return transport == null ? Transport.UNKNOWN : transport;
	}

	/**
	 * Finner status med gitt statussjekker
	 * 
	 * @param checker
	 * @param statusMap
	 * @param transportable
	 * @param isLoaded
	 * @param ignoreHandlingOrders
	 * @param window
	 * @return status
	 */
	String getStatus(StatusCheckerInterface<Transportable> checker, Map<String, String> statusMap,
			Transportable transportable, boolean isLoaded, boolean ignoreHandlingOrders, WindowInterface window) {

		String status = statusMap.get(checker.getArticleName());
		if (status != null || (handlingOrders && !ignoreHandlingOrders)) {
			return status;
		}
		// if (!isLoaded) {
		// loadTransportable(transportable);
		// }

		status = checker.getArticleStatus(transportable);
		statusMap.put(checker.getArticleName(), status);
		transportable.setStatus(Util.statusMapToString(statusMap));
		if (transportable instanceof Order) {
			try {
				orderViewHandler.getOrderManager().saveOrder((Order) transportable);
			} catch (ProTransException e) {
				Util.showErrorDialog(window, "Feil", e.getMessage());
				e.printStackTrace();
			}
		} else {
			PostShipmentManager postShipmentManager = (PostShipmentManager) ModelUtil.getBean("postShipmentManager");
			postShipmentManager.savePostShipment((PostShipment) transportable);
		}
		return status;
	}

	/**
	 * Lazy laster transportable
	 * 
	 * @param transportable
	 */
	// private void loadTransportable(Transportable transportable) {
	//
	// if (transportable instanceof Order) {
	// orderViewHandler.lazyLoadOrder((Order) transportable, new
	// LazyLoadOrderEnum[] { LazyLoadOrderEnum.COLLIES,
	// LazyLoadOrderEnum.ORDER_COSTS,
	// LazyLoadOrderEnum.ORDER_LINES, LazyLoadOrderEnum.ORDER_LINE_ORDER_LINES
	// });
	// } else {
	// PostShipmentManager postShipmentManager = (PostShipmentManager)
	// ModelUtil.getBean("postShipmentManager");
	// postShipmentManager.lazyLoad((PostShipment) transportable, new
	// LazyLoadPostShipmentEnum[] { LazyLoadPostShipmentEnum.COLLIES,
	// LazyLoadPostShipmentEnum.ORDER_LINES,
	// LazyLoadPostShipmentEnum.ORDER_LINE_ORDER_LINES });
	// }
	// }

	/**
	 * Cacher kommentarer
	 * 
	 * @param transportable
	 * @param window
	 * @param load
	 */
	@SuppressWarnings("unchecked")
	void cacheComment(Transportable transportable, WindowInterface window, boolean load) {
		if (load) {
			OverviewManager<Object> manager = (OverviewManager<Object>) ModelUtil
					.getBean(transportable.getManagerName());
			manager.lazyLoad(transportable, new LazyLoadEnum[][] { { LazyLoadEnum.ORDER_COMMENTS, LazyLoadEnum.NONE },
					{ LazyLoadEnum.COLLIES, LazyLoadEnum.NONE } });
			transportable.cacheComments();
		}
	}

	@SuppressWarnings("unchecked")
	void cacheTakstolHeight(Transportable transportable, WindowInterface window, boolean load) {
		if (load) {
			OverviewManager<Object> manager = (OverviewManager<Object>) ModelUtil
					.getBean(transportable.getManagerName());
			manager.lazyLoad(transportable,
					new LazyLoadEnum[][] { { LazyLoadEnum.ORDER_LINES, LazyLoadEnum.ORDER_LINE_ATTRIBUTES } });
			transportable.cacheTakstolHeight();
		}
	}

	/**
	 * Cacher høyde på garasjekolli
	 * 
	 * @param transportable
	 * @param window
	 * @param load
	 */
	@SuppressWarnings("unchecked")
	void cacheGarageColliHeight(Transportable transportable, WindowInterface window, boolean load) {
		if (load) {
			OverviewManager<Object> manager = (OverviewManager<Object>) ModelUtil
					.getBean(transportable.getManagerName());
			manager.lazyLoad(transportable, new LazyLoadEnum[][] { { LazyLoadEnum.ORDER_LINES, LazyLoadEnum.NONE },
					{ LazyLoadEnum.COLLIES, LazyLoadEnum.NONE } });
			transportable.cacheGarageColliHeight();
		}
	}

	/**
	 * @param object
	 * @return feil
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#checkDeleteObject(java.lang.Object)
	 */
	@Override
	public CheckObject checkDeleteObject(Transport object) {
		if (object.getOrders() != null && object.getOrders().size() != 0) {
			return new CheckObject("Kan ikke slette transport som har ordre", false);
		}
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
	public CheckObject checkSaveObject(TransportModel object, PresentationModel presentationModel,
			WindowInterface window) {

		return null;
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getAddRemoveString()
	 */
	@Override
	public String getAddRemoveString() {

		return "transport";
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getClassName()
	 */
	@Override
	public String getClassName() {
		return "Transport";
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getNewObject()
	 */
	@Override
	public Transport getNewObject() {
		return new Transport();
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getTableModel(no.ugland.utransprod.gui.WindowInterface)
	 */
	@Override
	public TableModel getTableModel(WindowInterface window) {
		return transportOrderTableModel;
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
		return "Transport";
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getWindowSize()
	 */
	@Override
	public Dimension getWindowSize() {
		return new Dimension(700, 800);
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#setColumnWidth(org.jdesktop.swingx.JXTable)
	 */
	@Override
	public void setColumnWidth(JXTable table) {

	}

	/**
	 * Henter lytter for selektering
	 * 
	 * @param transportModel
	 * @return lytter
	 */
	public ActionListener getTransportSelectionListener(TransportModel transportModel) {
		objectList.add(transportModel.getObject());
		objectSelectionList.setSelection(transportModel.getObject());
		return new TransportActionListener(transportModel);
	}

	/**
	 * Håndterer selektering av transport
	 * 
	 * @author atle.brekka
	 */
	private final class TransportActionListener implements ActionListener {
		/**
		 *
		 */
		private TransportModel currentTransportModel;

		/**
		 * @param transportModel
		 */
		public TransportActionListener(TransportModel transportModel) {
			currentTransportModel = transportModel;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent arg0) {
			for (TransportSelectionListener listener : selectionListeners) {
				listener.transportSelectionChange(checkBoxSelection.isSelected(), currentTransportModel);
			}

		}

	}

	/**
	 * Lager sjekkboks for selektering av transport
	 * 
	 * @param number
	 * @return sjekkboks
	 */
	public JCheckBox getSelectionCheckBox(int number) {
		checkBoxSelection = new JCheckBox();
		checkBoxSelection.setEnabled(hasWriteAccess());
		checkBoxSelection.setName("CheckBoxSelection" + number);
		return checkBoxSelection;
	}

	/**
	 * Lager sjekkboks for setting av sent
	 * 
	 * @param window
	 * @param number
	 * @return sjekkboks
	 */
	public JCheckBox getCheckBoxSent(WindowInterface window, int number) {
		transportPresentationModel.addBeanPropertyChangeListener(TransportModel.PROPERTY_SENT_BOOL,
				new SentPropertyListener());
		JCheckBox checkBox = BasicComponentFactory
				.createCheckBox(transportPresentationModel.getModel(TransportModel.PROPERTY_SENT_BOOL), "Opplastet");
		checkBox.setEnabled(hasWriteAccess());
		checkBox.setName("CheckBoxSent" + number);

		PropertyConnector conn = new PropertyConnector(
				transportPresentationModel.getModel(TransportModel.PROPERTY_SENT_STRING), "value", checkBox,
				"toolTipText");
		conn.updateProperty2();
		return checkBox;
	}

	public JCheckBox getCheckBoxLevert(WindowInterface window, int number) {
		transportPresentationModel.addBeanPropertyChangeListener(TransportModel.PROPERTY_LEVERT_BOOL,
				new LevertPropertyListener());
		JCheckBox checkBox = BasicComponentFactory
				.createCheckBox(transportPresentationModel.getModel(TransportModel.PROPERTY_LEVERT_BOOL), "Levert");
		checkBox.setEnabled(hasWriteAccess());
		checkBox.setName("CheckBoxLevert" + number);

		PropertyConnector conn = new PropertyConnector(
				transportPresentationModel.getModel(TransportModel.PROPERTY_LEVERT_STRING), "value", checkBox,
				"toolTipText");
		conn.updateProperty2();
		return checkBox;
	}

	/**
	 * Lager tekstfelt for opplastingstid
	 * 
	 * @return tekstfelt
	 */
	public JTextField getTextFieldLoadTime() {
		JTextField textField = BasicComponentFactory
				.createTextField(transportPresentationModel.getBufferedModel(TransportModel.PROPERTY_LOAD_TIME));
		textField.setEnabled(hasWriteAccess());
		return textField;
	}

	public JTextField getTextFieldTrolley() {
		JTextField textField = BasicComponentFactory
				.createTextField(transportPresentationModel.getBufferedModel(TransportModel.PROPERTY_TROLLEY));
		textField.setEnabled(hasWriteAccess());
		return textField;
	}

	/**
	 * Lagrer transport
	 * 
	 * @param window
	 * @param sentDate
	 */
	public void saveTransportModel(WindowInterface window, Date sentDate) {
		if (transportPresentationModel != null) {
			TransportModel transportModel = ((TransportModel) transportPresentationModel.getBean())
					.getBufferedObjectModel(transportPresentationModel);

			CheckObject checkObject = checkSaveObject(transportModel, null, window);
			String errorString = null;
			if (checkObject != null) {
				errorString = checkObject.getMsg();
			}
			if (errorString == null) {
				transportPresentationModel.triggerCommit();
				((TransportModel) transportPresentationModel.getBean()).getObject().setSent(sentDate);
				saveObject((TransportModel) transportPresentationModel.getBean(), window);

				updateTransportableList(true);
			} else {

				Util.showErrorDialog((Component) null, "Feil", errorString);

			}
		}
	}

	public void saveTransportModelLevert(WindowInterface window, Date levertDate) {
		if (transportPresentationModel != null) {
			TransportModel transportModel = ((TransportModel) transportPresentationModel.getBean())
					.getBufferedObjectModel(transportPresentationModel);

			CheckObject checkObject = checkSaveObject(transportModel, null, window);
			String errorString = null;
			if (checkObject != null) {
				errorString = checkObject.getMsg();
			}
			if (errorString == null) {
				transportPresentationModel.triggerCommit();
				((TransportModel) transportPresentationModel.getBean()).getObject().setLevert(levertDate);
				saveObject((TransportModel) transportPresentationModel.getBean(), window);

				updateTransportableList(true);
			} else {

				Util.showErrorDialog((Component) null, "Feil", errorString);

			}
		}
	}

	/**
	 * Initierer hendelsehåndtering
	 */
	public void initEventHandling() {
		checkBoxSelection.addActionListener(
				getTransportSelectionListener((TransportModel) transportPresentationModel.getBean()));
		((TransportModel) transportPresentationModel.getBean())
				.addBufferChangeListener(new TransportBufferingListener(), transportPresentationModel);
	}

	/**
	 * Håndterer buffering
	 * 
	 * @author atle.brekka
	 */
	final class TransportBufferingListener implements PropertyChangeListener {
		/**
		 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
		 */
		public void propertyChange(PropertyChangeEvent arg0) {
			fireBufferChange();

		}

	}

	/**
	 * Åpner dialog for ordre
	 * 
	 * @param order
	 * @param window
	 */
	void openOrderView(Order order, WindowInterface window) {
		orderViewHandler.openOrderView(order, false, window, true);

	}

	/**
	 * Håndterer klikk i tabell
	 * 
	 * @author atle.brekka
	 */
	final class TableClickHandler extends MouseAdapter {
		/**
		 *
		 */
		private WindowInterface window;
		private ActionTransportSending actionTransportSending;
		private ActionTransportSending actionTransportNotSending;
		private ActionTransportLevert actionTransportLevert;
		private ActionTransportLevert actionTransportNotLevert;

		/**
		 * @param aWindow
		 */
		public TableClickHandler(WindowInterface aWindow) {
			window = aWindow;
			actionTransportSending = new ActionTransportSending(true, ActionTransportSending.OPPLASTET_STRING, window);
			actionTransportNotSending = new ActionTransportSending(false, ActionTransportSending.IKKE_OPPLASTET_STRING,
					window);
			actionTransportLevert = new ActionTransportLevert(true, ActionTransportLevert.LEVERT_STRING, window);
			actionTransportNotLevert = new ActionTransportLevert(false, ActionTransportLevert.IKKE_LEVERT_STRING,
					window);
		}

		/**
		 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseClicked(MouseEvent e) {

			if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2) {
				Util.setWaitCursor(window.getComponent());
				if (transportableSelectionList.getSelection() != null) {
					int index = tableOrders.convertRowIndexToModel(transportableSelectionList.getSelectionIndex());
					Transportable transportable = (Transportable) transportableSelectionList.getElementAt(index);
					Order order;
					if (transportable instanceof Order) {
						order = (Order) transportable;
					} else {
						order = ((PostShipment) transportable).getOrder();
					}
					openOrderView(order, window);
				}
				Util.setDefaultCursor(window.getComponent());
			} else if (SwingUtilities.isRightMouseButton(e)) {

				Transportable tmp = (Transportable) transportableSelectionList.getSelection();

				if (tmp instanceof PostShipment) {
					popupMenuTransport.add(menuItemPacklist);
					popupMenuTransport.add(menuItemShowDeviation);
					popupMenuTransport.remove(menuItemDeviation);
				} else {
					popupMenuTransport.remove(menuItemPacklist);
					popupMenuTransport.remove(menuItemShowDeviation);
					popupMenuTransport.add(menuItemDeviation);
				}

				if (tmp != null) {
					if (tmp.getSent() == null) {
						menuItemSetSent.setAction(actionTransportSending);
					} else {
						menuItemSetSent.setAction(actionTransportNotSending);
					}

					if (tmp.getLevertBool()) {
						menuItemSetLevert.setAction(actionTransportNotLevert);
					} else {
						menuItemSetLevert.setAction(actionTransportLevert);
					}
				}

				popupMenuTransport.show((JXTable) e.getSource(), e.getX(), e.getY());

			}

		}
	}

	/**
	 * Lazy laster transport
	 * 
	 * @param transport
	 * @param enums
	 */
	// public void lazyLoadTransport(Transport transport,
	// LazyLoadTransportEnum[] enums) {
	// ((TransportManager) overviewManager).lazyLoadTransport(transport, enums);
	// }

	/**
	 * Setter selektert order
	 * 
	 * @param order
	 */
	public void setSelectedTransportable(Transportable transportable) {
		transportableSelectionList.setSelection(transportable);
		tableOrders
				.scrollRowToVisible(tableOrders.convertRowIndexToModel(transportableSelectionList.getSelectionIndex()));
	}

	/**
	 * Henter alle statussjekkere
	 * 
	 * @return statussjekkere
	 */
	public Map<String, StatusCheckerInterface<Transportable>> getStatusCheckers() {
		return statusCheckers;
	}

	/**
	 * Viser order som skal sendes
	 * 
	 * @param aWindow
	 * @param currentTransportable
	 * @param sentTransport
	 * @return dato for opplasting
	 * @throws ProTransException
	 */
	private Date showSentOrders(final WindowInterface aWindow, final List<TransportListable> listTransportable,
			final boolean sentTransport) throws ProTransException {
		Date sentDate = Util.getCurrentDate();
		boolean isCanceled = false;
		handlingOrders = true;

		sentDate = checkLoadingDate(aWindow, sentDate,
				((TransportModel) transportPresentationModel.getBean()).getObject());
		if (listTransportable.size() != 0) {
			Transport transport = ((Transportable) listTransportable.get(0)).getTransport();
			checkIfTransportableHasSupplier(transport);

			isCanceled = showOrders(aWindow, sentTransport, sentDate, listTransportable);
			if (isCanceled) {
				sentDate = null;
			}
		}

		if (sentTransport && !isCanceled) {
			saveTransportModel(aWindow, sentDate);
			transportOrderTableModel.fireTableDataChanged();
		}

		return sentDate;
	}

	private Date showLevertOrders(final WindowInterface aWindow, final List<TransportListable> listTransportable,
			final boolean levertTransport) throws ProTransException {
		Date levertDate = Util.getCurrentDate();
		boolean isCanceled = false;
		handlingOrders = true;

		// levertDate = checkLoadingDate(aWindow, levertDate,
		// ((TransportModel) transportPresentationModel.getBean()).getObject());
		if (listTransportable.size() != 0) {
			Transport transport = ((Transportable) listTransportable.get(0)).getTransport();
			checkIfTransportableHasSupplier(transport);

			isCanceled = showLevertOrders(aWindow, levertTransport, levertDate, listTransportable);
			if (isCanceled) {
				levertDate = null;
			}
		}

		if (levertTransport && !isCanceled) {
			saveTransportModelLevert(aWindow, levertDate);
			transportOrderTableModel.fireTableDataChanged();
		}

		return levertDate;
	}

	private boolean showOrders(final WindowInterface window1, final boolean sentTransport, final Date sentDate,
			final List<TransportListable> tmpTransportableList) {

		SentTransportViewHandler sentTransportViewHandler = new SentTransportViewHandler(login, managerRepository,
				deviationViewHandlerFactory, tmpTransportableList, false, sentTransport, sentDate);

		// Util.showEditViewable(new
		// SentTransportView(sentTransportViewHandler), window1);
		sentTransportViewHandler.saveObjects(window1, sentDate);

		handlingOrders = false;
		return sentTransportViewHandler.isCanceled();
	}

	private boolean showLevertOrders(final WindowInterface window1, final boolean levertTransport,
			final Date levertDate, final List<TransportListable> tmpTransportableList) {

		LevertTransportViewHandler levertTransportViewHandler = new LevertTransportViewHandler(login, managerRepository,
				deviationViewHandlerFactory, tmpTransportableList, false, levertTransport, levertDate);

		Util.showEditViewable(new LevertTransportView(levertTransportViewHandler), window1);

		handlingOrders = false;
		return levertTransportViewHandler.isCanceled();
	}

	private Date checkLoadingDate(WindowInterface window, Date sentDate, Transport transport) {
		if (transport.getLoadingDate() == null || !Util.SHORT_DATE_FORMAT.format(transport.getLoadingDate())
				.equals(Util.getCurrentDateAsDateString())) {
			if (Util.showConfirmDialog(window.getComponent(), "Sent dato",
					"Sent dato er forskjellig fra opplastingsdato, ønsker du å endre sent dato?")) {
				sentDate = Util.getDate(window);
			}
		}
		return sentDate;
	}

	/**
	 * Sjekker om det finnes ordre som ikke er betalt.
	 * 
	 * @return true dersom ikke betalt
	 */
	@SuppressWarnings("unchecked")
	private boolean haveOrdersNotPaid(final List<TransportListable> listTransportListable) {
		Iterator<TransportListable> it = transportableList.iterator();
		while (it.hasNext()) {
			TransportListable transportListable = it.next();
			if (!transportListable.isPaid() && transportListable.getProductAreaGroup().usePrepayment()) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Setter sendt/ikke sendt.
	 * 
	 * @param sent
	 * @param window
	 * @param currentTransportable
	 * @param sentTransport
	 * @return true dersom sendt dato ikke er satt
	 */
	final boolean setSent(final Boolean sent, final WindowInterface window,
			final List<TransportListable> listTransportable, final boolean sentTransport) {
		try {
			checkProbability(listTransportable);
			Date sentDate = null;
			if (sent) {
				sentDate = handleTransportSending(window, listTransportable, sentTransport);

			} else {
				sentDate = handleTransportNotSent(listTransportable, sentTransport);
			}

			transportPresentationModel.triggerCommit();

			// if (sentDate != null) {
			// fireSentChange();
			// }

			for (TransportListable transportListable : listTransportable) {
				vismaFileCreator.createVismaFileForDelivery(transportListable.getOrder(), false, 1);
				vismaFileCreator.createVismaFileForDelivery(transportListable.getOrder(), true, 2);
			}

			Util.setDefaultCursor(window.getComponent());
			if (sentDate != null) {
				return false;
			}
		} catch (ProTransException e) {
			Util.showErrorDialog(window, "Feil", e.getMessage());
			e.printStackTrace();
		}
		return true;
	}

	final boolean setLevert(final Boolean levert, final WindowInterface window,
			final List<TransportListable> listTransportable, final boolean levertTransport) {
		try {
			Date levertDate = null;
			if (levert) {
				levertDate = handleTransportLevert(window, listTransportable, levertTransport);

			} else {
				levertDate = handleTransportNotLevert(listTransportable, levertTransport);
			}

			transportPresentationModel.triggerCommit();

			if (levertDate != null) {
				fireSentChange();
			}

			Util.setDefaultCursor(window.getComponent());
			if (levertDate != null) {
				return false;
			}
		} catch (ProTransException e) {
			Util.showErrorDialog(window, "Feil", e.getMessage());
			e.printStackTrace();
		}
		return true;
	}

	private void checkProbability(List<TransportListable> listTransportable) throws ProTransException {
		if (listTransportable != null) {
			for (TransportListable listable : listTransportable) {
				if (listable.getProbability() != null && listable.getProbability() == 90) {
					throw new ProTransException("Kan ikke sende transport som inneholder 90% ordre");
				}
			}
		}

	}

	private Date handleTransportNotSent(final List<TransportListable> listTransportListable,
			final boolean saveTransport) throws ProTransException {
		Date sentDate;
		PostShipmentManager postShipmentManager = (PostShipmentManager) ModelUtil.getBean("postShipmentManager");
		sentDate = Util.getCurrentDate();

		Set<Colli> collies;

		for (TransportListable transportable : listTransportListable) {
			// lazyLoadTransportable(postShipmentManager, transportable);
			if (!Hibernate.isInitialized(transportable.getCollies())) {
				if (transportable instanceof Order) {
					orderViewHandler.lazyLoadOrder((Order) transportable,
							new LazyLoadOrderEnum[] { LazyLoadOrderEnum.COLLIES });
				} else {
					postShipmentManager.lazyLoad((PostShipment) transportable,
							new LazyLoadPostShipmentEnum[] { LazyLoadPostShipmentEnum.COLLIES });
				}
			}
			collies = transportable.getCollies();
			removeSentFromCollies(collies);

			// removeSentFromTransportable(transportable);

			saveTransportable(postShipmentManager, transportable);

		}

		if (saveTransport) {
			saveTransportModel(window, null);
		}
		return sentDate;
	}

	private Date handleTransportNotLevert(final List<TransportListable> listTransportListable,
			final boolean saveTransport) throws ProTransException {
		Date levertDate;
		PostShipmentManager postShipmentManager = (PostShipmentManager) ModelUtil.getBean("postShipmentManager");
		levertDate = Util.getCurrentDate();

		Set<Colli> collies;

		for (TransportListable transportable : listTransportListable) {
			// lazyLoadTransportable(postShipmentManager, transportable);
			if (!Hibernate.isInitialized(transportable.getCollies())) {
				if (transportable instanceof Order) {
					orderViewHandler.lazyLoadOrder((Order) transportable,
							new LazyLoadOrderEnum[] { LazyLoadOrderEnum.COLLIES });
				} else {
					postShipmentManager.lazyLoad((PostShipment) transportable,
							new LazyLoadPostShipmentEnum[] { LazyLoadPostShipmentEnum.COLLIES });
				}
			}
			collies = transportable.getCollies();
			// removeSentFromCollies(collies);

			// removeSentFromTransportable(transportable);

			saveTransportableIkkeLevert(postShipmentManager, transportable);

		}

		if (saveTransport) {
			saveTransportModelLevert(window, null);
		}
		return levertDate;
	}

	private void saveTransportable(PostShipmentManager postShipmentManager, TransportListable transportable)
			throws ProTransException {
		// if (transportable instanceof Order) {
		// orderViewHandler.refreshOrder((Order) transportable);
		// }

		// Transport transport = transportable.getTransport();
		// transport.setSent(null);
		// managerRepository.getTransportManager().saveTransport(transport);

		if (transportable instanceof Order) {
			// ((Order) transportable).setHasMissingCollies(null);
			//
			// orderViewHandler.getOrderManager().saveOrder((Order)
			// transportable);
			orderViewHandler.getOrderManager().taBortSentOgManglerKolli((Order) transportable);

		} else {
			transportable.setSentBool(false);
			postShipmentManager.savePostShipment((PostShipment) transportable);
		}
	}

	private void saveTransportableIkkeLevert(PostShipmentManager postShipmentManager, TransportListable transportable)
			throws ProTransException {
		// if (transportable instanceof Order) {
		// orderViewHandler.refreshOrder((Order) transportable);
		// }

		// Transport transport = transportable.getTransport();
		// transport.setLevert(null);
		// managerRepository.getTransportManager().saveTransport(transport);

		if (transportable instanceof Order) {
			// ((Order) transportable).setHasMissingCollies(null);
			//
			// orderViewHandler.getOrderManager().saveOrder((Order)
			// transportable);
			orderViewHandler.getOrderManager().settLevert((Order) transportable, null);

		} else {
			transportable.setLevert(null);
			postShipmentManager.savePostShipment((PostShipment) transportable);
		}
	}

	private void removeSentFromTransportable(TransportListable transportable) {
		if (transportable instanceof Order) {
			orderViewHandler.refreshOrder((Order) transportable);
		}
		transportable.setSentBool(false);
		transportable.getTransport().setSent(null);
	}

	private void removeSentFromCollies(Set<Colli> collies) {
		if (collies != null) {
			for (Colli colli : collies) {
				colli.setSentBool(false);
				managerRepository.getColliManager().saveColli(colli);
			}
		}
	}

	// private void lazyLoadTransportable(PostShipmentManager
	// postShipmentManager, TransportListable transportable) {
	// if (transportable instanceof Order) {
	// orderViewHandler.lazyLoadOrder((Order) transportable, new
	// LazyLoadOrderEnum[] { LazyLoadOrderEnum.COLLIES,
	// LazyLoadOrderEnum.ORDER_LINES,
	// LazyLoadOrderEnum.ORDER_LINE_ORDER_LINES, LazyLoadOrderEnum.ORDER_COSTS,
	// LazyLoadOrderEnum.POST_SHIPMENTS,
	// LazyLoadOrderEnum.COMMENTS });
	// } else {
	// postShipmentManager.lazyLoad((PostShipment) transportable, new
	// LazyLoadPostShipmentEnum[] { LazyLoadPostShipmentEnum.COLLIES,
	// LazyLoadPostShipmentEnum.ORDER_LINES,
	// LazyLoadPostShipmentEnum.ORDER_LINE_ORDER_LINES,
	// LazyLoadPostShipmentEnum.POST_SHIPMENT_REFS,
	// LazyLoadPostShipmentEnum.ORDER_COMMENTS });
	// }
	// }

	private Date handleTransportSending(final WindowInterface window1, final List<TransportListable> listTransportable,
			final boolean sentTransport) throws ProTransException {
		boolean continueSending = true;
		Date sentDate = null;

		if (haveOrdersNotPaid(listTransportable)) {
			continueSending = continueSending(window1);
		}
		if (continueSending) {
			sentDate = showSentOrders(window1, listTransportable, sentTransport);
		}
		return sentDate;
	}

	private Date handleTransportLevert(final WindowInterface window1, final List<TransportListable> listTransportable,
			final boolean levertTransport) throws ProTransException {
		Date levertDate = null;

		levertDate = showLevertOrders(window1, listTransportable, levertTransport);
		return levertDate;
	}

	private void checkIfTransportableHasSupplier(final Transport transport) throws ProTransException {
		if (transport.getSupplier() == null) {
			throw new ProTransException("Transport har ikke firma satt");
		}
	}

	private boolean continueSending(final WindowInterface window) {
		boolean continueSending = true;
		// forskuddsbetaling er fjernet
		// if (!Util
		// .showConfirmDialog(window.getComponent(), "Ordre ikke betalt",
		// "Det finnes ordre som ikke er betalt, skal de sendes allikevel?")) {
		// continueSending = false;
		// }
		return continueSending;
	}

	/**
	 * Klasse som håndtere at sent settes på transport. Dersom det settes til
	 * sent skal det komme opp en dialog med alle ordre som er med på denne
	 * transporten og det skal være mulig å utelate ordre
	 * 
	 * @author atle.brekka
	 */
	private class SentPropertyListener implements PropertyChangeListener {

		/**
		 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
		 */
		public final void propertyChange(final PropertyChangeEvent event) {
			if (!handlingOrders) {
				handleSentTransport(event);
			}
		}

	}

	private class LevertPropertyListener implements PropertyChangeListener {

		/**
		 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
		 */
		public final void propertyChange(final PropertyChangeEvent event) {
			if (!handlingOrders) {
				handleLevertTransport(event);
			}
		}

	}

	@SuppressWarnings("unchecked")
	void handleSentTransport(final PropertyChangeEvent event) {
		Util.setWaitCursor(window.getComponent());
		Boolean sent = (Boolean) event.getNewValue();
		boolean isCanceled = setSent(sent, window, transportableList, true);
		handlingOrders = true;
		if (isCanceled) {
			transportPresentationModel.setValue(TransportModel.PROPERTY_SENT_BOOL, !sent);
		}
		handlingOrders = false;
		Util.setDefaultCursor(window.getComponent());
	}

	void handleLevertTransport(final PropertyChangeEvent event) {
		Util.setWaitCursor(window.getComponent());
		Boolean levert = (Boolean) event.getNewValue();
		boolean isCanceled = setLevert(levert, window, transportableList, true);
		handlingOrders = true;
		if (isCanceled) {
			transportPresentationModel.setValue(TransportModel.PROPERTY_LEVERT_BOOL, !levert);
		}
		handlingOrders = false;
		Util.setDefaultCursor(window.getComponent());
	}

	/**
	 * Fjerner transport fra ordre eller ettersending.
	 * 
	 * @param window1
	 */
	final void removeTransport(final WindowInterface window1) {
		PostShipmentManager postShipmentManager = (PostShipmentManager) ModelUtil.getBean("postShipmentManager");
		int index = tableOrders.convertRowIndexToModel(transportableSelectionList.getSelectionIndex());
		if (index != -1) {
			Transportable transportable = (Transportable) transportableSelectionList.getElementAt(index);
			if (transportable instanceof Order) {
				Order order = (Order) transportable;

				orderViewHandler.lazyLoadOrder(order, new LazyLoadOrderEnum[] { LazyLoadOrderEnum.POST_SHIPMENTS,
						LazyLoadOrderEnum.COMMENTS, LazyLoadOrderEnum.COLLIES, LazyLoadOrderEnum.ORDER_LINES });
			} else {
				PostShipment postShipment = (PostShipment) transportable;

				postShipmentManager.lazyLoad(postShipment,
						new LazyLoadPostShipmentEnum[] { LazyLoadPostShipmentEnum.POST_SHIPMENT_REFS,
								LazyLoadPostShipmentEnum.ORDER_COMMENTS, LazyLoadPostShipmentEnum.COLLIES,
								LazyLoadPostShipmentEnum.ORDER_LINES });
			}
			if (transportable.getPostShipments() != null && transportable.getPostShipments().size() != 0) {
				Util.showErrorDialog(window1, "Ordre har etterleveringer",
						"Kan ikke fjerne ordre som har etterleveringer.");
				return;
			}
			transportable.setSentBool(false);
			transportable.setTransport(null);

			if (transportable instanceof Order) {
				Order order = (Order) transportable;

				// orderViewHandler.lazyLoadOrder(order, new LazyLoadOrderEnum[]
				// { LazyLoadOrderEnum.COLLIES, LazyLoadOrderEnum.ORDER_LINES,
				// LazyLoadOrderEnum.ORDER_COSTS });
				Set<Colli> collies = order.getCollies();
				removeSentFromCollies(collies);

				Set<OrderLine> orderLines = order.getOrderLines();
				if (orderLines != null) {
					PostShipment postShipment;
					for (OrderLine orderLine : orderLines) {
						postShipment = orderLine.getPostShipment();
						Deviation deviation;
						if (postShipment != null) {
							orderLine.setPostShipment(null);
							deviation = postShipment.getDeviation();
							postShipment.setOrder(null);

							postShipmentManager.removePostShipment(postShipment);

							if (deviation != null) {
								managerRepository.getDeviationManager().removeObject(deviation);
							}
						}
					}
				}

				orderViewHandler.refreshOrder(order);
				order.setSentBool(false);
				order.setTransport(null);
				try {
					vismaFileCreator.createVismaFileForTransport(order);

					orderViewHandler.getOrderManager().saveOrder(order);
				} catch (ProTransException e) {
					Util.showErrorDialog(window1, "Feil", e.getMessage());
					e.printStackTrace();
					return;
				}

				overviewManager.refreshObject(((TransportModel) transportPresentationModel.getBean()).getObject());
			} else {
				postShipmentManager.savePostShipment((PostShipment) transportable);
			}
			transportableList.remove(index);
			fireSentChange();
			transportPresentationModel.triggerCommit();
		}
	}

	/**
	 * Håndterer popupmenyvalg fjern transport
	 * 
	 * @author atle.brekka
	 */
	class MenuItemListenerRemoveTransport implements ActionListener {
		/**
		 *
		 */
		private WindowInterface window;

		/**
		 * @param aWindow
		 */
		public MenuItemListenerRemoveTransport(WindowInterface aWindow) {
			window = aWindow;

		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent actionEvent) {
			Util.setWaitCursor(window);
			if (actionEvent.getActionCommand().equalsIgnoreCase(menuItemRemoveTransport.getText())) {

				removeTransport(window);

			}
			Util.setDefaultCursor(window);
		}

	}

	/**
	 * Håndterer popupmeny for registrering av avvik
	 * 
	 * @author atle.brekka
	 */
	class MenuItemListenerDeviation implements ActionListener {
		private WindowInterface window;

		/**
		 * @param aWindow
		 */
		public MenuItemListenerDeviation(WindowInterface aWindow) {
			window = aWindow;

		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent actionEvent) {
			Util.setWaitCursor(window);
			if (actionEvent.getActionCommand().equalsIgnoreCase(menuItemDeviation.getText())) {
				Order order = (Order) transportableSelectionList.getElementAt(
						tableOrders.convertRowIndexToModel(transportableSelectionList.getSelectionIndex()));
				DeviationViewHandler2 deviationViewHandler = deviationViewHandlerFactory.create(order, true, false,
						true, null, true, false);
				deviationViewHandler.registerDeviation(order, window);

			}
			Util.setDefaultCursor(window);
		}

	}

	class MenuItemListenerSplitOrder implements ActionListener {
		private WindowInterface window1;

		public MenuItemListenerSplitOrder(WindowInterface aWindow) {
			window1 = aWindow;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent actionEvent) {
			Util.setWaitCursor(window);
			splitOrder(window1);
			Util.setDefaultCursor(window);
		}

	}

	class MenuItemListenerShowDeviation implements ActionListener {
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

	class MenuItemListenerUpdateStatus implements ActionListener {
		private WindowInterface window1;

		/**
		 * @param aWindow
		 */
		public MenuItemListenerUpdateStatus(WindowInterface aWindow) {
			window1 = aWindow;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent actionEvent) {
			Util.setWaitCursor(window1);
			Transportable transportable = (Transportable) transportableSelectionList
					.getElementAt(tableOrders.convertRowIndexToModel(transportableSelectionList.getSelectionIndex()));
			Order order = transportable.getOrder();
			updateOrderStatus(order);
			Util.setDefaultCursor(window1);
		}

	}

	private void updateOrderStatus(Order order) {
		if (order.getInfo() == null) {
			if (!Hibernate.isInitialized(order.getOrderLines())) {
				managerRepository.getOrderManager().lazyLoadOrder(order,
						new LazyLoadOrderEnum[] { LazyLoadOrderEnum.ORDER_LINES,
								LazyLoadOrderEnum.ORDER_LINE_ATTRIBUTES, LazyLoadOrderEnum.COMMENTS,
								LazyLoadOrderEnum.ORDER_LINE_ORDER_LINES, LazyLoadOrderEnum.ORDER_COSTS,
								LazyLoadOrderEnum.COLLIES });
			}
			order.setInfo(order.orderLinesToString());
			managerRepository.getOrderManager().saveOrder(order);
		}
		Map<String, String> statusMap = Util.createStatusMap(order.getStatus());
		StatusCheckerInterface<Transportable> steinChecker = Util.getSteinChecker();
		StatusCheckerInterface<Transportable> gulvsponChecker = Util.getGulvsponChecker();

		String status = statusMap.get(steinChecker.getArticleName());
		boolean needToSave = false;
		if (status == null) {
			needToSave = true;
			// managerRepository.getOrderManager().lazyLoadTree(order);
			managerRepository.getOrderManager().lazyLoadOrder(order,
					new LazyLoadOrderEnum[] { LazyLoadOrderEnum.ORDER_LINES, LazyLoadOrderEnum.ORDER_LINE_ATTRIBUTES });
			status = steinChecker.getArticleStatus(order);
			statusMap.put(steinChecker.getArticleName(), status);
		}

		status = statusMap.get(gulvsponChecker.getArticleName());

		if (status == null) {
			if (!needToSave) {
				needToSave = true;
				// managerRepository.getOrderManager().lazyLoadTree(order);
				managerRepository.getOrderManager().lazyLoadOrder(order, new LazyLoadOrderEnum[] {
						LazyLoadOrderEnum.ORDER_LINES, LazyLoadOrderEnum.ORDER_LINE_ATTRIBUTES });
			}
			status = gulvsponChecker.getArticleStatus(order);
			statusMap.put(gulvsponChecker.getArticleName(), status);
		}

		if (needToSave) {
			order.setStatus(Util.statusMapToString(statusMap));
			try {
				managerRepository.getOrderManager().saveOrder(order);
			} catch (ProTransException e) {
				Util.showErrorDialog(window, "Feil", e.getMessage());
				e.printStackTrace();
			}
		}
	}

	private void splitOrder(WindowInterface aWindow) {
		Transportable transportable = (Transportable) transportableSelectionList
				.getElementAt(tableOrders.convertRowIndexToModel(transportableSelectionList.getSelectionIndex()));

		SplitOrderViewHandler splitOrderViewHandler = new SplitOrderViewHandler(transportable);

		Util.showEditViewable(new SplitOrderView(splitOrderViewHandler), aWindow);

		if (!splitOrderViewHandler.isCanceled()) {
			handleSplittedOrder(splitOrderViewHandler, transportable);
		}
	}

	private void showDeviation(WindowInterface aWindow) {
		Transportable transportable = (Transportable) transportableSelectionList
				.getElementAt(tableOrders.convertRowIndexToModel(transportableSelectionList.getSelectionIndex()));

		DeviationViewHandler2 deviationViewHandler = deviationViewHandlerFactory.create(null, true, false, true, null,
				true, false);
		deviationViewHandler.showDeviation(transportable.getDeviation(), aWindow);
	}

	private void handleSplittedOrder(SplitOrderViewHandler splitOrderViewHandler, Transportable transportable) {
		List<Object> splitted = splitOrderViewHandler.getSplitted();

		if (splitted != null && splitted.size() != 0) {
			PostShipmentManager postShipmentManager = (PostShipmentManager) ModelUtil.getBean("postShipmentManager");
			PostShipment postShipment = new PostShipment();
			postShipment.setOrder(transportable.getOrder());
			postShipment.setPostShipmentRef(transportable.getPostShipment());
			for (Object object : splitted) {
				if (object instanceof Colli) {
					splitColli((Colli) object, postShipment);
				} else {
					splitOrderLine((OrderLine) object, postShipment);
				}
			}
			// postShipmentManager.lazyLoad(postShipment, new
			// LazyLoadPostShipmentEnum[] {
			// LazyLoadPostShipmentEnum.ORDER_LINES,
			// LazyLoadPostShipmentEnum.ORDER_LINE_ORDER_LINES });
			if (postShipment.isDonePackage()) {
				postShipment.setPostShipmentReady(Util.getCurrentDate());
			}

			postShipmentManager.savePostShipment(postShipment);
			transportable.setStatus(null);
			saveTransportable(transportable);

			fireSentChange();
		}
	}

	private void saveTransportable(Transportable transportable) {
		if (transportable.getPostShipment() != null) {
			PostShipmentManager postShipmentManager = (PostShipmentManager) ModelUtil.getBean("postShipmentManager");
			postShipmentManager.savePostShipment((PostShipment) transportable);
		} else {
			try {
				orderViewHandler.getOrderManager().saveOrder((Order) transportable);
			} catch (ProTransException e) {
				e.printStackTrace();
				throw new ProTransRuntimeException(e.getMessage());
			}
		}
	}

	private void splitColli(Colli colli, PostShipment postShipment) {
		postShipment.addColli(colli);
		colli.setTransport(null);
		colli.setOrder(null);

		Set<OrderLine> orderLines = colli.getOrderLines();
		if (orderLines != null) {
			for (OrderLine orderLine : orderLines) {
				splitOrderLine(orderLine, postShipment);
			}
		}
	}

	private void splitOrderLine(OrderLine orderLine, PostShipment postShipment) {
		postShipment.addOrderLine(orderLine);

	}

	/**
	 * Håndterer popupmenyvalg vis mangler
	 * 
	 * @author atle.brekka
	 */
	private class MenuItemListenerMissing implements ActionListener {
		private WindowInterface window1;

		/**
		 * @param aWindow
		 */
		public MenuItemListenerMissing(final WindowInterface aWindow) {
			window1 = aWindow;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent actionEvent) {
			Util.setWaitCursor(window);
			showMissingCollies(window1);
			Util.setDefaultCursor(window);
		}

	}

	/**
	 * Håndterer popupmenyvalg fraktbrev
	 * 
	 * @author atle.brekka
	 */
	private class MenuItemListenerReport implements ActionListener {
		private WindowInterface window;

		/**
		 * @param aWindow
		 */
		public MenuItemListenerReport(WindowInterface aWindow) {
			window = aWindow;

		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent actionEvent) {
			handlingOrders = true;
			Util.runInThreadWheel(window.getRootPane(), new LetterPrinter(window), null);
		}

	}

	/**
	 * Håndterer popupmenyvalg pakkliste
	 * 
	 * @author atle.brekka
	 */
	private class MenuItemListenerPacklist implements ActionListener {
		private WindowInterface window;

		public MenuItemListenerPacklist(WindowInterface aWindow) {
			window = aWindow;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent actionEvent) {
			Util.setWaitCursor(window);
			Transportable transportable = (Transportable) transportableSelectionList
					.getElementAt(tableOrders.convertRowIndexToModel(transportableSelectionList.getSelectionIndex()));
			if (transportable instanceof PostShipment) {
				Util.runInThreadWheel(window.getRootPane(), new PacklistPrinter(window, (PostShipment) transportable),
						null);
			}
			Util.setDefaultCursor(window);
		}

	}

	/**
	 * Viser mangler
	 * 
	 * @param window
	 */
	void showMissingCollies(WindowInterface window) {
		Transportable transportable = (Transportable) transportableSelectionList.getSelection();
		showMissingColliesForTransportable(transportable, window);
	}

	/**
	 * Viser mangler.
	 * 
	 * @param transportable
	 * @param window
	 */
	public static void showMissingColliesForTransportable(Transportable transportable, final WindowInterface window) {
		if (transportable != null) {
			if (transportable instanceof PostShipment) {
				PostShipmentManager postShipmentManager = (PostShipmentManager) ModelUtil
						.getBean("postShipmentManager");
				postShipmentManager.lazyLoad((PostShipment) transportable,
						new LazyLoadPostShipmentEnum[] { LazyLoadPostShipmentEnum.ORDER_LINES,
								LazyLoadPostShipmentEnum.ORDER_LINE_ORDER_LINES,
								LazyLoadPostShipmentEnum.ORDER_COMMENTS, LazyLoadPostShipmentEnum.COLLIES });
			} else {
				if (!Hibernate.isInitialized(transportable.getOrderLines())
						|| !Hibernate.isInitialized(transportable.getCollies())) {
					OrderManager orderManager = (OrderManager) ModelUtil.getBean("orderManager");
					transportable = orderManager.getOrderWithOrderLinesAndCollies(transportable.getOrderNr());

					// orderManager.lazyLoadOrder((Order) transportable,
					// new LazyLoadOrderEnum[] { LazyLoadOrderEnum.ORDER_LINES,
					// // LazyLoadOrderEnum.ORDER_LINE_ORDER_LINES,
					// // LazyLoadOrderEnum.COMMENTS,
					// LazyLoadOrderEnum.COLLIES// ,
					// // LazyLoadOrderEnum.PROCENT_DONE
					// });
				}

			}
			List<OrderLine> missing = transportable.getMissingCollies();
			if (missing != null) {
				List<OrderLineWrapper> missingList = Util.getOrderLineWrapperList(missing);
				Util.showOptionsDialog(window, missingList, "Mangler", false, false);
			}

		}
	}

	/**
	 * Henter tabellmodell for transport
	 * 
	 * @return tabellmodell
	 */
	public TransportOrderTableModel getTransportOrderTableModel() {
		return transportOrderTableModel;
	}

	public TransportOrderTableModel getTransportOrderTableModelForExcel() {
		return transportOrderTableModel.clone(TransportOrderTableModel.TransportColumn.ForExcel.EXCEL);
	}

	/**
	 * Initierere komboboks for ansatte
	 */
	void initEmployeeCombo() {
		Supplier supplier = (Supplier) transportPresentationModel.getBufferedValue(TransportModel.PROPERTY_SUPPLIER);

		if (supplier != null) {
			SupplierManager supplierManager = (SupplierManager) ModelUtil.getBean("supplierManager");
			supplierManager.lazyLoad(supplier, new LazyLoadEnum[][] { { LazyLoadEnum.EMPLOYEES, LazyLoadEnum.NONE } });
			transportEmployeeList.clear();
			if (supplier.getActiveEmployees() != null) {
				transportEmployeeList.addAll(supplier.getActiveEmployees());
			}
		}
	}

	/**
	 * Håndterer endring av valgt leverandør
	 * 
	 * @author atle.brekka
	 */
	class SupplierSelectionListener implements ItemListener {
		/**
		 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
		 */
		public void itemStateChanged(ItemEvent event) {
			initEmployeeCombo();

		}

	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#hasWriteAccess()
	 */
	@Override
	public Boolean hasWriteAccess() {
		return UserUtil.hasWriteAccess(userType, "Transport");
	}

	private class LetterPrinter implements Threadable {
		private WindowInterface owner;

		public LetterPrinter(WindowInterface aWindow) {
			owner = aWindow;

		}

		public void doWhenFinished(Object object) {
			handlingOrders = false;
		}

		public Object doWork(Object[] params, JLabel labelInfo) {
			labelInfo.setText("Genererer fraktbrev...");
			generateAndPrintTransportLetter(owner);
			return null;

		}

		public void enableComponents(boolean enable) {
		}

	}

	private void generateAndPrintTransportLetter(final WindowInterface owner) {
		int selectedIndex = transportableSelectionList.getSelectionIndex();
		if (selectedIndex >= 0) {
			Transportable transportable = getSelectedTransport(selectedIndex);

			if (transportable != null) {
				// if (!Hibernate.isInitialized(transportable.getOrderLines()))
				// {
				// if (Order.class.isInstance(transportable)) {
				// managerRepository.getOrderManager().lazyLoadOrder((Order)
				// transportable,
				// new LazyLoadOrderEnum[] { LazyLoadOrderEnum.ORDER_LINES });
				// } else {
				// managerRepository.getPostShipmentManager().lazyLoad((PostShipment)
				// transportable,
				// new LazyLoadPostShipmentEnum[] {
				// LazyLoadPostShipmentEnum.ORDER_LINES });
				// }

				if (Order.class.isInstance(transportable) && (!Hibernate.isInitialized(transportable.getOrderLines())
						|| !Hibernate.isInitialized(transportable.getCollies())
				// || !Hibernate.isInitialized(transportable.getOrderComments())
				)) {
					transportable = managerRepository.getOrderManager()
							.getOrderWithOrderLinesAndCollies(transportable.getOrderNr());
					// managerRepository.getOrderManager().lazyLoadOrder((Order)
					// transportable, new LazyLoadOrderEnum[] {
					// LazyLoadOrderEnum.ORDER_LINES, LazyLoadOrderEnum.COLLIES
					// , LazyLoadOrderEnum.COMMENTS
					// });
				} else if (PostShipment.class.isInstance(transportable)
						&& (!Hibernate.isInitialized(transportable.getOrderLines())
								|| !Hibernate.isInitialized(transportable.getCollies())
				// || !Hibernate.isInitialized(transportable.getOrderComments())
				)) {
					managerRepository.getPostShipmentManager().lazyLoad((PostShipment) transportable,
							new LazyLoadPostShipmentEnum[] { LazyLoadPostShipmentEnum.ORDER_LINES,
									LazyLoadPostShipmentEnum.COLLIES
							// ,LazyLoadPostShipmentEnum.ORDER_COMMENTS
					});
				}
				// }
				TransportLetter transportLetter = TransportLetterSelector
						.valueOf(StringUtils.upperCase(transportable.getProductAreaGroup().getProductAreaGroupName()))
						.getTransportLetter(managerRepository);
				transportLetter.generateTransportLetter(transportable, owner);
			}
		}
	}

	private Transportable getSelectedTransport(int selectedIndex) {
		Transportable transportable = (Transportable) transportableSelectionList
				.getElementAt(tableOrders.convertRowIndexToModel(selectedIndex));
		return transportable;
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#doDelete(no.ugland.utransprod.gui.WindowInterface)
	 */
	@Override
	public boolean doDelete(WindowInterface window) {
		boolean returnValue = true;

		Transport transport = ((TransportModel) transportPresentationModel.getBean()).getObject();

		if (transport != null) {
			((TransportManager) overviewManager).lazyLoadTransport(transport,
					new LazyLoadTransportEnum[] { LazyLoadTransportEnum.ORDER, LazyLoadTransportEnum.ORDER_LINES });
			if (transport.getOrders() != null && transport.getOrders().size() != 0) {
				Util.showErrorDialog(window, "Kan ikke slette", "Kan ikke slette transport som inneholder ordre");
				returnValue = false;
			} else {
				overviewManager.removeObject(transport);
			}
		}

		return returnValue;
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
	protected AbstractEditView<TransportModel, Transport> getEditView(
			AbstractViewHandler<Transport, TransportModel> handler, Transport object, boolean searching) {
		return null;
	}

	/**
	 * Setter eller fjerner filter for sendte transporter
	 * 
	 * @param sentFilter
	 * @param productAreaGroup
	 */
	public void handleFilter(boolean sentFilter) {// ), ProductAreaGroup
		// productAreaGroup) {
		// currentProductAreaGroup = productAreaGroup;
		if (tableOrders != null) {
			List<Filter> filters = new ArrayList<Filter>();
			if (sentFilter) {
				Filter filter = new PatternFilter("Nei", Pattern.CASE_INSENSITIVE, 13);
				filters.add(filter);
			}
			// if (productAreaGroup != null) {
			PrefsUtil.setInvisibleColumns(ProductAreaGroup.UNKNOWN.getProductAreaGroupName(),
					TableEnum.TABLETRANSPORTORDERS.getTableName(), tableOrders);
			// }
			if (filters.size() != 0) {
				Filter[] filterArray = new Filter[filters.size()];
				FilterPipeline filterPipeline = new FilterPipeline(filters.toArray(filterArray));
				tableOrders.setFilters(filterPipeline);

			} else {
				tableOrders.setFilters(null);
			}
		}
	}

	public void saveUserInvisibleColumns(ProductAreaGroup productAreaGroup) {
		PrefsUtil.putUserInvisibleColumns(tableOrders, productAreaGroup);
	}

	public String getProductAreaGroupName() {
		return ProductAreaGroup.UNKNOWN.getProductAreaGroupName();
	}

	public static void setTesting(boolean testing) {
		isTest = testing;
	}

	@Override
	void afterSaveObject(Transport transport, WindowInterface window) {
		refresh(transport);
		if (transportOrderTableModel != null) {
			transportOrderTableModel.fireTableDataChanged();
			tableOrders.repaint();
			tableOrders.revalidate();
			tableOrders.getParent().validate();
		}

	}

	@Override
	String getAddString() {
		return null;
	}

	@SuppressWarnings("serial")
	private class ActionTransportSending extends AbstractAction {
		public static final String OPPLASTET_STRING = "Sett opplastet...";
		public static final String IKKE_OPPLASTET_STRING = "Sett ikke opplastet...";
		private boolean sending = true;
		private WindowInterface window;

		private ActionTransportSending(boolean isSending, String actionCommandString, WindowInterface aWindow) {
			super(actionCommandString);
			sending = isSending;
			window = aWindow;
		}

		public void actionPerformed(ActionEvent e) {
			setSent(sending, window,
					Arrays.asList(
							new TransportListable[] { (TransportListable) transportableSelectionList.getSelection() }),
					false);
			updateTransportableList(true);

		}

	}

	@SuppressWarnings("serial")
	private class ActionTransportLevert extends AbstractAction {
		public static final String LEVERT_STRING = "Sett levert...";
		public static final String IKKE_LEVERT_STRING = "Sett ikke levert...";
		private boolean levert = true;
		private WindowInterface window;

		private ActionTransportLevert(boolean isLevert, String actionCommandString, WindowInterface aWindow) {
			super(actionCommandString);
			levert = isLevert;
			window = aWindow;
		}

		public void actionPerformed(ActionEvent e) {
			setLevert(levert, window,
					Arrays.asList(
							new TransportListable[] { (TransportListable) transportableSelectionList.getSelection() }),
					false);
			updateTransportableList(true);
		}

	}

	public String getSelectedOrderNr() {
		int selectedIndex = transportableSelectionList.getSelectionIndex();
		Transportable transportable = getSelectedTransport(selectedIndex);
		return transportable.getOrderNr();
	}

	public class TextPaneRendererLevert extends JTextPane implements TableCellRenderer {

		public Component getTableCellRendererComponent(JTable arg0, Object arg1, boolean arg2, boolean arg3, int arg4,
				int arg5) {
			setText((String) arg1);
			if (getText().contains("!")) {
				insertIcon(IconEnum.ICON_WARNING.getIcon());
			}
			return this;
		}

	}

}
