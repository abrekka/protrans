package no.ugland.utransprod.gui.handlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.table.TableModel;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.ProTransMain;
import no.ugland.utransprod.gui.ProductAreaGroupProvider;
import no.ugland.utransprod.gui.SentTransportView;
import no.ugland.utransprod.gui.TransportListView;
import no.ugland.utransprod.gui.TransportView;
import no.ugland.utransprod.gui.UBColumnControlPopup;
import no.ugland.utransprod.gui.Updateable;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.buttons.DeleteButton;
import no.ugland.utransprod.gui.buttons.NewButton;
import no.ugland.utransprod.gui.edit.EditTransportView;
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
import no.ugland.utransprod.model.Transport;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.service.OverviewManager;
import no.ugland.utransprod.service.PostShipmentManager;
import no.ugland.utransprod.service.VismaFileCreator;
import no.ugland.utransprod.service.enums.LazyLoadOrderEnum;
import no.ugland.utransprod.service.enums.LazyLoadPostShipmentEnum;
import no.ugland.utransprod.service.enums.LazyLoadTransportEnum;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.OrderLineWrapper;
import no.ugland.utransprod.util.PrefsUtil;
import no.ugland.utransprod.util.Threadable;
import no.ugland.utransprod.util.TransportComparator;
import no.ugland.utransprod.util.UserUtil;
import no.ugland.utransprod.util.Util;
import no.ugland.utransprod.util.YearWeek;
import no.ugland.utransprod.util.report.TransportLetter;
import no.ugland.utransprod.util.report.TransportLetterSelector;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.Filter;
import org.jdesktop.swingx.decorator.FilterPipeline;
import org.jdesktop.swingx.decorator.HighlighterFactory;
import org.jdesktop.swingx.decorator.PatternFilter;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.binding.list.ArrayListModel;
import com.jgoodies.binding.list.SelectionInList;

/**
 * Hjelpeklasse forvisning av transporter for en gitt uke
 * 
 * @author atle.brekka
 */
public class TransportWeekViewHandler implements Updateable, TransportSelectionListener, ProductAreaGroupProvider {
    // private JPopupMenu popupMenuTransport;
    // private JMenuItem menuItemRemoveTransport;
    // private JMenuItem menuItemSetSent;
    // private JMenuItem menuItemMissing;
    // private JMenuItem menuItemReport;
    // private JMenuItem menuItemPacklist;
    // private JMenuItem menuItemDeviation;
    // private JMenuItem menuItemSplitOrder;
    // private JMenuItem menuItemShowDeviation;
    // private JMenuItem menuItemShowTakstolInfo;
    boolean handlingOrders = false;

    private final ArrayListModel transportList;

    private final SelectionInList transportSelectionList;

    private TransportViewHandler transportViewHandler;

    private JButton buttonRemoveTransport;

    private JButton buttonEditTransport;

    Map<Transport, TransportViewHandler> transportViewHandlers = new Hashtable<Transport, TransportViewHandler>();

    Map<Transport, TransportViewHandler> selectedTransportViewHandlers = new Hashtable<Transport, TransportViewHandler>();

    Map<Transport, TransportViewHandler> changedTransportViewHandlers = new Hashtable<Transport, TransportViewHandler>();

    private List<ListDataListener> transportListListeners = new ArrayList<ListDataListener>();

    private List<PropertyChangeListener> transportChangeListeners = new ArrayList<PropertyChangeListener>();

    private YearWeek routeDate;

    private List<TransportView> transportViews = new ArrayList<TransportView>();
    private final ArrayListModel transportableList;
    private final SelectionInList transportableSelectionList;
    private boolean listView = false;
    private TransportListView transportListView;
    private JXTable tableOrdersList;

    private Login login;
    private ManagerRepository managerRepository;

    private DeviationViewHandlerFactory deviationViewHandlerFactory;

    private OrderViewHandlerFactory orderViewHandlerFactory;
    private ShowTakstolInfoActionFactory showTakstolInfoActionFactory;

    private VismaFileCreator vismaFileCreator;
    private boolean initiert = false;

    @Inject
    public TransportWeekViewHandler(final Login aLogin, final ManagerRepository aManagerRepository, TransportViewHandler aTransportViewHandler,
	    OrderViewHandlerFactory aOrderViewHandlerFactory, final DeviationViewHandlerFactory aDeviationViewHandlerFactory,
	    ShowTakstolInfoActionFactory aShowTakstolInfoActionFactory, @Assisted YearWeek aRouteDate, final VismaFileCreator vismaFileCreator) {
	this.vismaFileCreator = vismaFileCreator;
	showTakstolInfoActionFactory = aShowTakstolInfoActionFactory;
	deviationViewHandlerFactory = aDeviationViewHandlerFactory;
	orderViewHandlerFactory = aOrderViewHandlerFactory;
	login = aLogin;
	managerRepository = aManagerRepository;

	transportableList = new ArrayListModel();
	transportableSelectionList = new SelectionInList((ListModel) transportableList);
	transportViewHandler = aTransportViewHandler;
	transportList = new ArrayListModel();
	routeDate = aRouteDate;

	transportSelectionList = new SelectionInList((ListModel) transportList);
	// setUpMenus();
    }

    // private void setUpMenus() {
    // popupMenuTransport = new JPopupMenu("Fjern transport");
    // popupMenuTransport.setName("PopupMenuTransport");
    // menuItemRemoveTransport = new JMenuItem("Fjern transport");
    // menuItemRemoveTransport.setName("MenuItemRemoveTransport");
    // menuItemRemoveTransport.setEnabled(hasWriteAccess());
    // menuItemSetSent = new JMenuItem("Sett sent");
    // menuItemSetSent.setEnabled(hasWriteAccess());
    // menuItemSetSent.setName("MenuItemSetSent");
    // menuItemMissing = new JMenuItem("Se mangler...");
    // menuItemMissing.setName("MenuItemMissing");
    // menuItemReport = new JMenuItem("Fraktbrev...");
    // menuItemReport.setName("MenuItemTransportLetter");
    // menuItemPacklist = new JMenuItem("Pakkliste...");
    // menuItemDeviation = new JMenuItem("Registrere avvik...");
    // menuItemSplitOrder = new JMenuItem("Splitt ordre...");
    // menuItemSplitOrder.setName("MenuItemSplitOrder");
    // menuItemShowDeviation = new JMenuItem("Se avviksskjema...");
    // menuItemShowDeviation.setName("MenuItemShowDeviation");
    // menuItemShowTakstolInfo = new JMenuItem("Takstolinfo...");
    // menuItemShowTakstolInfo.setName("MenuItemShowTakstolInfo");
    //
    // popupMenuTransport.add(menuItemRemoveTransport);
    // popupMenuTransport.add(menuItemSetSent);
    // popupMenuTransport.add(menuItemMissing);
    // popupMenuTransport.add(menuItemReport);
    // popupMenuTransport.add(menuItemSplitOrder);
    // popupMenuTransport.add(menuItemShowTakstolInfo);
    //
    // menuItemRemoveTransport.setEnabled(hasWriteAccess());
    //
    // }

    // private void addMenuListeners(WindowInterface window1) {
    // if (!initiert) {
    // initiert = true;
    // menuItemMissing.addActionListener(new MenuItemListenerMissing(window1));
    // menuItemReport.addActionListener(new MenuItemListenerReport(window1));
    // menuItemPacklist.addActionListener(new
    // MenuItemListenerPacklist(window1));
    //
    // menuItemRemoveTransport.addActionListener(new
    // MenuItemListenerRemoveTransport(window1));
    // }
    // menuItemDeviation.addActionListener(new
    // MenuItemListenerDeviation(window1));
    // menuItemSplitOrder.addActionListener(new
    // MenuItemListenerSplitOrder(window1));
    // menuItemShowDeviation.addActionListener(new
    // MenuItemListenerShowDeviation(window1));
    // menuItemShowTakstolInfo.addActionListener(showTakstolInfoActionFactory.create(this,
    // window1));
    // }

    // class MenuItemListenerRemoveTransport implements ActionListener {
    // /**
    // *
    // */
    // private WindowInterface window;
    //
    // /**
    // * @param aWindow
    // */
    // public MenuItemListenerRemoveTransport(WindowInterface aWindow) {
    // window = aWindow;
    //
    // }
    //
    // /**
    // * @see
    // java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
    // */
    // public void actionPerformed(ActionEvent actionEvent) {
    // // Util.setWaitCursor(window);
    // if
    // (actionEvent.getActionCommand().equalsIgnoreCase(menuItemRemoveTransport.getText()))
    // {
    //
    // removeTransport(window);
    //
    // }
    // // Util.setDefaultCursor(window);
    // }
    //
    // }

    final void removeTransport(final WindowInterface window1) {
	PostShipmentManager postShipmentManager = (PostShipmentManager) ModelUtil.getBean("postShipmentManager");
	int index = tableOrdersList.convertRowIndexToModel(transportableSelectionList.getSelectionIndex());
	if (index != -1) {
	    Transportable transportable = (Transportable) transportableSelectionList.getElementAt(index);
	    if (transportable instanceof Order) {
		Order order = (Order) transportable;

		managerRepository.getOrderManager().lazyLoadOrder(order,
			new LazyLoadOrderEnum[] { LazyLoadOrderEnum.POST_SHIPMENTS, LazyLoadOrderEnum.COMMENTS, LazyLoadOrderEnum.COLLIES });
	    } else {
		PostShipment postShipment = (PostShipment) transportable;

		postShipmentManager.lazyLoad(postShipment, new LazyLoadPostShipmentEnum[] { LazyLoadPostShipmentEnum.POST_SHIPMENT_REFS,
			LazyLoadPostShipmentEnum.ORDER_COMMENTS, LazyLoadPostShipmentEnum.COLLIES });
	    }
	    if (transportable.getPostShipments() != null && transportable.getPostShipments().size() != 0) {
		Util.showErrorDialog(window1, "Ordre har etterleveringer", "Kan ikke fjerne ordre som har etterleveringer.");
		return;
	    }
	    transportable.setSentBool(false);
	    transportable.setTransport(null);

	    if (transportable instanceof Order) {
		Order order = (Order) transportable;

		managerRepository.getOrderManager().lazyLoadOrder(order,
			new LazyLoadOrderEnum[] { LazyLoadOrderEnum.COLLIES, LazyLoadOrderEnum.ORDER_LINES, LazyLoadOrderEnum.ORDER_COSTS });
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

		managerRepository.getOrderManager().refreshObject(order);
		order.setSentBool(false);
		order.setTransport(null);
		try {
		    vismaFileCreator.createVismaFileForTransport(order);

		    managerRepository.getOrderManager().saveOrder(order);
		} catch (ProTransException e) {
		    Util.showErrorDialog(window1, "Feil", e.getMessage());
		    e.printStackTrace();
		    return;
		}

		// overviewManager.refreshObject(((TransportModel)
		// transportPresentationModel.getBean()).getObject());
	    } else {
		postShipmentManager.savePostShipment((PostShipment) transportable);
	    }
	    transportableList.remove(index);
	    // fireSentChange();
	    // transportPresentationModel.triggerCommit();
	}
    }

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
	    Transportable transportable = getSelectedTransport();
	    if (transportable instanceof PostShipment) {
		Util.runInThreadWheel(window.getRootPane(), new PacklistPrinter(window, (PostShipment) transportable), null);
	    }
	    Util.setDefaultCursor(window);
	}

    }

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

    private void generateAndPrintTransportLetter(final WindowInterface owner) {
	int selectedIndex = transportableSelectionList.getSelectionIndex();
	if (selectedIndex >= 0) {
	    Transportable transportable = getSelectedTransport(selectedIndex);
	    if (!Hibernate.isInitialized(transportable.getCollies())) {
		if (Order.class.isInstance(transportable)) {
		    managerRepository.getOrderManager().lazyLoadOrder((Order) transportable, new LazyLoadOrderEnum[] { LazyLoadOrderEnum.COLLIES });
		} else {
		    managerRepository.getPostShipmentManager().lazyLoad((PostShipment) transportable,
			    new LazyLoadPostShipmentEnum[] { LazyLoadPostShipmentEnum.COLLIES });
		}
	    }

	    if (transportable != null) {
		TransportLetter transportLetter = TransportLetterSelector.valueOf(
			StringUtils.upperCase(transportable.getProductAreaGroup().getProductAreaGroupName())).getTransportLetter(managerRepository);
		transportLetter.generateTransportLetter(transportable, owner);
	    }
	}
    }

    private Transportable getSelectedTransport(int selectedIndex) {
	Transportable transportable = (Transportable) transportableSelectionList.getElementAt(tableOrdersList.convertRowIndexToModel(selectedIndex));
	return transportable;
    }

    private Transportable getSelectedTransport() {
	int selectedIndex = transportableSelectionList.getSelectionIndex();
	Transportable transportable = (Transportable) transportableSelectionList.getElementAt(tableOrdersList.convertRowIndexToModel(selectedIndex));
	return transportable;
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
	    // Util.setWaitCursor(window1);
	    showMissingCollies(window1);
	    // Util.setDefaultCursor(window1);
	}

    }

    void showMissingCollies(WindowInterface window) {
	Transportable transportable = getSelectedTransport();
	showMissingColliesForTransportable(transportable, window);
    }

    public static void showMissingColliesForTransportable(final Transportable transportable, final WindowInterface window) {
	if (transportable != null) {
	    if (transportable instanceof PostShipment) {
		PostShipmentManager postShipmentManager = (PostShipmentManager) ModelUtil.getBean("postShipmentManager");
		postShipmentManager.lazyLoad((PostShipment) transportable, new LazyLoadPostShipmentEnum[] { LazyLoadPostShipmentEnum.ORDER_LINES,
			LazyLoadPostShipmentEnum.ORDER_LINE_ORDER_LINES, LazyLoadPostShipmentEnum.ORDER_COMMENTS, LazyLoadPostShipmentEnum.COLLIES });
	    } else {
		OrderManager orderManager = (OrderManager) ModelUtil.getBean("orderManager");
		orderManager.lazyLoadOrder((Order) transportable, new LazyLoadOrderEnum[] { LazyLoadOrderEnum.ORDER_LINES,
			LazyLoadOrderEnum.ORDER_LINE_ORDER_LINES, LazyLoadOrderEnum.COMMENTS, LazyLoadOrderEnum.COLLIES,
			LazyLoadOrderEnum.PROCENT_DONE });

	    }
	    List<OrderLine> missing = transportable.getMissingCollies();
	    if (missing != null) {
		List<OrderLineWrapper> missingList = Util.getOrderLineWrapperList(missing);
		Util.showOptionsDialog(window, missingList, "Mangler", false, false);
	    }

	}
    }

    /**
     * Legger til lytter
     * 
     * @param listener
     */
    public void addListDataListener(ListDataListener listener) {
	transportListListeners.add(listener);
    }

    /**
     * Fyrer hendelse om at transport er fjernet
     */
    private void fireTransportRemoved() {
	for (ListDataListener listener : transportListListeners) {
	    listener.intervalRemoved(new ListDataEvent(this, -1, -1, -1));
	}
	buttonEditTransport.setEnabled(false);
	buttonRemoveTransport.setEnabled(false);
    }

    /**
     * Legger til lytter for transportendringer
     * 
     * @param listener
     */
    public void addTransportChangeListener(PropertyChangeListener listener) {
	transportChangeListeners.add(listener);
    }

    /**
     * Fyrer hendelse om at transport er endret
     * 
     * @param buffering
     */
    void fireTransportChange(boolean buffering) {
	for (PropertyChangeListener listener : transportChangeListeners) {
	    listener.propertyChange(new PropertyChangeEvent(this, null, null, buffering));
	}
    }

    /**
     * Fyrer hendelse om at transport er lagt til
     */
    private void fireTransportAdded() {
	for (ListDataListener listener : transportListListeners) {
	    listener.intervalAdded(new ListDataEvent(this, -1, -1, -1));
	}
	buttonEditTransport.setEnabled(false);
	buttonRemoveTransport.setEnabled(false);
    }

    /**
     * Henter liste med transportruter.
     * 
     * @param routeDate1
     * @param productAreaGroup
     * @return transportruter
     */
    public final SelectionInList getTransportSelectionList(final YearWeek routeDate1, final ProductAreaGroup productAreaGroup) {
	transportList.clear();

	List<Transport> transports = managerRepository.getTransportManager().findByYearAndWeekAndProductAreaGroup(routeDate1.getYear(),
		routeDate1.getWeek(), productAreaGroup);
	Collections.sort(transports, new TransportComparator());
	transportList.addAll(transports);
	return transportSelectionList;
    }

    /**
     * Henter liste med transportruter.
     * 
     * @param routeDate1
     * @param productAreaGroup
     * @return transportruter
     */
    @SuppressWarnings("unchecked")
    public final List<Transport> getTransportList(final YearWeek routeDate1, final ProductAreaGroup productAreaGroup) {
	getTransportSelectionList(routeDate1, productAreaGroup);
	return transportList;
    }

    /**
     * Henter transportliste
     * 
     * @return transportliste
     */
    public ArrayListModel getTransportList() {
	return transportList;
    }

    /**
     * Henter liste med transporter som ikke er sendt
     * 
     * @return transportliste
     */
    @SuppressWarnings("unchecked")
    public List<Transport> getTransportNotSentList() {
	List<Transport> transportNotSentList = new ArrayList<Transport>();
	List<Transport> transports = transportList;
	for (Transport transport : transports) {
	    if (transport.getSent() == null) {
		transportNotSentList.add(transport);
	    }
	}
	return transportNotSentList;
    }

    /**
     * Henter knapp for å legge til transport
     * 
     * @param window
     * @return knapp
     */
    public JButton getButtonAddTransport(WindowInterface window) {
	JButton button = new NewButton("transport", this, window);
	button.setEnabled(hasWriteAccess());
	return button;
    }

    /**
     * Lager knapp for å fjerne transport
     * 
     * @param window
     * @return knapp
     */
    public JButton getButtonRemoveTransport(WindowInterface window) {
	buttonRemoveTransport = new DeleteButton("transport", this, window);
	buttonRemoveTransport.setEnabled(false);
	buttonRemoveTransport.setName("ButtonRemoveTransport");
	return buttonRemoveTransport;
    }

    /**
     * Lager knapp for å editere transport
     * 
     * @param window
     * @return knapp
     */
    public JButton getButtonEditTransport(WindowInterface window) {
	buttonEditTransport = new JButton(new EditTransportAction(window));
	buttonEditTransport.setEnabled(false);
	return buttonEditTransport;
    }

    /**
     * @see no.ugland.utransprod.gui.Updateable#doDelete(no.ugland.utransprod.gui.WindowInterface)
     */
    public boolean doDelete(WindowInterface window) {
	boolean returnValue = true;
	Set<Transport> transports = selectedTransportViewHandlers.keySet();
	TransportViewHandler handler;

	for (Transport transport : transports) {
	    handler = selectedTransportViewHandlers.get(transport);
	    returnValue = handler.doDelete(window);
	    if (returnValue) {
		transportViewHandlers.remove(transport);
		transportList.remove(transport);
	    }
	}
	fireTransportRemoved();
	return returnValue;
    }

    /**
     * Åpner vindu for editering av transport
     * 
     * @param transport
     */
    void openEditView(Transport transport) {
	EditTransportView transportView = new EditTransportView(transportViewHandler, transport, false);
	WindowInterface dialog = new JDialogAdapter(new JDialog(ProTransMain.PRO_TRANS_MAIN, "Transport", true));
	dialog.setName("EditTransportView");
	dialog.add(transportView.buildPanel(dialog));
	dialog.pack();
	Util.locateOnScreenCenter(dialog);
	dialog.setVisible(true);

	if (transport.getTransportYear() != null && transport.getTransportYear().equals(routeDate.getYear()) && transport.getTransportWeek() != null
		&& transport.getTransportWeek().equals(routeDate.getWeek())) {
	    fireTransportAdded();
	}

    }

    /**
     * @see no.ugland.utransprod.gui.Updateable#doNew(no.ugland.utransprod.gui.WindowInterface)
     */
    public void doNew(WindowInterface window) {
	openEditView(new Transport());
    }

    /**
     * @see no.ugland.utransprod.gui.Updateable#doRefresh(no.ugland.utransprod.gui.WindowInterface)
     */
    public void doRefresh(WindowInterface window) {

    }

    /**
     * @see no.ugland.utransprod.gui.Updateable#doSave(no.ugland.utransprod.gui.WindowInterface)
     */
    public void doSave(WindowInterface window) {

    }

    /**
     * Lager transportvindu
     * 
     * @param transport
     * @return transportvindu
     */
    public TransportView getTransportView(Transport transport) {
	TransportViewHandler transportViewHandlerTmp = new TransportViewHandler(orderViewHandlerFactory, login, managerRepository,
		deviationViewHandlerFactory, showTakstolInfoActionFactory, vismaFileCreator);

	transportViewHandlerTmp.addBufferingListener(new BufferingListener());
	transportViewHandlerTmp.addSentListener(new SentListener());

	TransportViewHandler oldHandler = transportViewHandlers.get(transport);

	if (oldHandler != null) {
	    oldHandler.removeTransportSelectionListener(this);
	}
	managerRepository.getTransportManager().lazyLoadTransport(transport,
		new LazyLoadTransportEnum[] { LazyLoadTransportEnum.ORDER, LazyLoadTransportEnum.POST_SHIPMENTS });
	transportViewHandlers.put(transport, transportViewHandlerTmp);
	transportViewHandlerTmp.addTransportSelectionListener(this);
	TransportView transportView = new TransportView(transportViewHandlerTmp, transport);
	transportViews.add(transportView);
	return transportView;
    }

    public TransportListView getTransportListView(WindowInterface window) {
	transportListView = transportListView == null ? new TransportListView(this, window) : transportListView;
	return transportListView;
    }

    /**
     * @see no.ugland.utransprod.gui.model.TransportSelectionListener#transportSelectionChange(boolean,
     *      no.ugland.utransprod.gui.model.TransportModel)
     */
    public void transportSelectionChange(boolean selection, TransportModel transportModel) {
	if (selection) {
	    selectedTransportViewHandlers.put(transportModel.getObject(), transportViewHandlers.get(transportModel.getObject()));
	} else {
	    selectedTransportViewHandlers.remove(transportModel.getObject());
	}

	if (selectedTransportViewHandlers.size() != 0) {
	    buttonRemoveTransport.setEnabled(true);
	    buttonEditTransport.setEnabled(true);
	} else {
	    buttonRemoveTransport.setEnabled(false);
	    buttonEditTransport.setEnabled(false);
	}

    }

    /**
     * Editere transport
     * 
     * @author atle.brekka
     */
    private final class EditTransportAction extends AbstractAction {
	private static final long serialVersionUID = 1L;

	private WindowInterface window;

	public EditTransportAction(WindowInterface aWindow) {
	    super("Editer transport...");
	    window = aWindow;
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent arg0) {
	    if (selectedTransportViewHandlers.size() != 1) {
		Util.showErrorDialog(window, "Feil", "Det kan bare velges en transport for editering");
	    } else {
		Transport transport = selectedTransportViewHandlers.keySet().iterator().next();
		openEditView(transport);
		selectedTransportViewHandlers.clear();
	    }

	}
    }

    /**
     * Sjekker om det finnes endringer
     * 
     * @return true dersom endringer
     */
    public boolean hasChanges() {
	if (changedTransportViewHandlers.size() != 0) {
	    return true;
	}
	return false;
    }

    /**
     * Tar bort endringer
     */
    public void resetChanges() {
	changedTransportViewHandlers.clear();
    }

    /**
     * Lagrer endringer
     * 
     * @param window
     */
    public void saveChanges(WindowInterface window) {
	Set<Transport> transports = changedTransportViewHandlers.keySet();
	TransportViewHandler aTransportViewHandler;
	for (Transport transport : transports) {
	    aTransportViewHandler = transportViewHandlers.get(transport);
	    aTransportViewHandler.saveTransportModel(window, transport.getSent());
	}
	changedTransportViewHandlers.clear();
    }

    /**
     * Lytter på bufferendringer
     * 
     * @author atle.brekka
     */
    class BufferingListener implements PropertyChangeListener {

	/**
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent event) {
	    Transport changedTransport = ((TransportModel) event.getSource()).getObject();
	    if (event.getNewValue() != null && (Boolean) event.getNewValue()) {
		changedTransportViewHandlers.put(changedTransport, transportViewHandlers.get(changedTransport));
		fireTransportChange(true);
	    } else {
		changedTransportViewHandlers.remove(changedTransport);
		fireTransportChange(false);
	    }

	}

    }

    /**
     * Håndterer sending
     * 
     * @author atle.brekka
     */
    class SentListener implements PropertyChangeListener {

	/**
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent event) {
	    fireTransportChange((Boolean) event.getNewValue());

	}

    }

    /**
     * Setter selektert ordre
     * 
     * @param transport
     * @param order
     */
    public void setSelectedTransportable(Transport transport, Transportable transportable) {
	TransportViewHandler handler = transportViewHandlers.get(transport);

	if (handler != null) {
	    handler.setSelectedTransportable(transportable);
	}
    }

    /**
     * Henter hjelpeklasse for gitt transport
     * 
     * @param transport
     * @return hjelpeklasse for transport
     */
    public TransportViewHandler getTransportViewHandler(Transport transport) {
	return transportViewHandlers.get(transport);
    }

    /**
     * Henter alle hjelpeklasser for transporter i liste
     * 
     * @return hjelpeklasser for transport
     */
    public Map<Transport, TransportViewHandler> getTransportViewHandlers() {
	return transportViewHandlers;
    }

    /**
     * Rensker
     */
    public void clear() {
	transportViewHandlers.clear();

	selectedTransportViewHandlers.clear();

	changedTransportViewHandlers.clear();
    }

    /**
     * Sjekker om bruker har skriverettigheter
     * 
     * @return true derom bruker har skriverettigheter
     */
    public boolean hasWriteAccess() {
	return UserUtil.hasWriteAccess(login.getUserType(), "Transport");
    }

    /**
     * Setter eller fjerner filter for alle transporter
     * 
     * @param sentFilter
     * @param productAreaGroup
     */
    public void setFilterSent(boolean sentFilter, ProductAreaGroup productAreaGroup) {
	PrefsUtil.setInvisibleColumns(productAreaGroup.getProductAreaGroupName(), TableEnum.TABLETRANSPORTORDERSLIST.getTableName(), tableOrdersList);
	Collection<TransportViewHandler> viewhandlers = transportViewHandlers.values();

	if (listView) {
	    if (tableOrdersList != null) {
		List<Filter> filters = new ArrayList<Filter>();
		if (sentFilter) {
		    Filter filter = new PatternFilter("Nei", Pattern.CASE_INSENSITIVE, 21);
		    filters.add(filter);
		}
		if (filters.size() != 0) {
		    Filter[] filterArray = new Filter[filters.size()];
		    FilterPipeline filterPipeline = new FilterPipeline(filters.toArray(filterArray));
		    tableOrdersList.setFilters(filterPipeline);

		} else {
		    tableOrdersList.setFilters(null);
		}
	    }
	} else {
	    if (viewhandlers != null) {
		for (TransportViewHandler handler : viewhandlers) {
		    handler.handleFilter(sentFilter, productAreaGroup);
		}
	    }
	}
    }

    public void saveUserInvisibleColumns(ProductAreaGroup productAreaGroup) {
	if (listView) {
	    PrefsUtil.putUserInvisibleColumns(tableOrdersList, productAreaGroup);
	} else {
	    if (transportList != null && transportList.size() != 0) {
		transportViewHandlers.get(transportList.get(0)).saveUserInvisibleColumns(productAreaGroup);
	    }
	}
    }

    public void cleanUp() {
    }

    public JXTable getTableOrders(ProductAreaGroup productAreaGroup, WindowInterface aWindow) {
	updateTransportableList();
	tableOrdersList = new JXTable();
	TableModel transportOrderTableModel = new TransportOrderTableModelList(transportableSelectionList, transportableList, Util.getGavlChecker(),
		Util.getTakstolChecker(managerRepository), Util.getSteinChecker(), Util.getGulvsponChecker(),
		TransportOrderTableModelList.TransportColumn.ForExcel.TABLE);
	tableOrdersList.setModel(transportOrderTableModel);
	tableOrdersList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	tableOrdersList.setSelectionModel(new SingleListSelectionAdapter(transportableSelectionList.getSelectionIndexHolder()));
	tableOrdersList.setColumnControlVisible(true);
	tableOrdersList.setColumnControl(new UBColumnControlPopup(tableOrdersList, this));
	tableOrdersList.setRowHeight(40);
	tableOrdersList.setShowGrid(true);
	tableOrdersList.setShowVerticalLines(true);
	// tableOrdersList.addMouseListener(new TableClickHandler(aWindow));

	List<TransportOrderTableModelList.TransportColumn> columns = TransportOrderTableModelList.TransportColumn.getTableColumns();

	for (TransportOrderTableModelList.TransportColumn column : columns) {
	    column.setCellRenderer(tableOrdersList);
	    column.setPrefferedWidth(tableOrdersList);
	}

	tableOrdersList.addHighlighter(HighlighterFactory.createAlternateStriping());
	tableOrdersList.addHighlighter(TransportViewHandler.getStartedPackingHighlighter(tableOrdersList, "Klar"));
	tableOrdersList.addHighlighter(TransportViewHandler.getReadyHighlighter(tableOrdersList, "Komplett"));
	tableOrdersList.addHighlighter(TransportViewHandler.getNotSentHighlighter(tableOrdersList, "Ikke sendt"));
	tableOrdersList.setShowGrid(true);
	tableOrdersList.setName(TableEnum.TABLETRANSPORTORDERSLIST.getTableName());
	PrefsUtil.setInvisibleColumns(productAreaGroup.getProductAreaGroupName(), tableOrdersList.getName(), tableOrdersList);

	// addMenuListeners(aWindow);
	return tableOrdersList;

    }

    private class ActionTransportSending extends AbstractAction {
	public static final String SEND_STRING = "Sett sent...";
	public static final String NOT_SEND_STRING = "Sett ikke sent...";
	private boolean sending = true;
	private WindowInterface window;

	private ActionTransportSending(boolean isSending, String actionCommandString, WindowInterface aWindow) {
	    super(actionCommandString);
	    sending = isSending;
	    window = aWindow;
	}

	public void actionPerformed(ActionEvent e) {
	    Transportable transportable = getSelectedTransport();
	    setSent(sending, window, transportable, false);

	}

    }

    private void checkProbability(Transportable transportable) throws ProTransException {
	if (transportable != null) {
	    // for (TransportListable listable : listTransportable) {
	    if (transportable.getProbability() != null && transportable.getProbability() == 90) {
		throw new ProTransException("Kan ikke sende transport som inneholder 90% ordre");
	    }
	    // }
	}

    }

    private boolean haveOrdersNotPaid(final Transportable transportable) {
	// Iterator<TransportListable> it = transportableList.iterator();
	// while (it.hasNext()) {
	// TransportListable transportListable = it.next();
	if (!transportable.isPaid() && transportable.getProductAreaGroup().usePrepayment()) {
	    return true;
	}
	// }

	return false;
    }

    private Date checkLoadingDate(WindowInterface window, Date sentDate, Transport transport) {
	if (transport.getLoadingDate() == null
		|| !Util.SHORT_DATE_FORMAT.format(transport.getLoadingDate()).equals(Util.getCurrentDateAsDateString())) {
	    if (Util.showConfirmDialog(window.getComponent(), "Sent dato",
		    "Sent dato er forskjellig fra opplastingsdato, ønsker du å endre sent dato?")) {
		sentDate = Util.getDate(window);
	    }
	}
	return sentDate;
    }

    private void checkIfTransportableHasSupplier(final Transport transport) throws ProTransException {
	if (transport.getSupplier() == null) {
	    throw new ProTransException("Transport har ikke firma satt");
	}
    }

    private boolean showOrders(final WindowInterface window1, final boolean sentTransport, final Date sentDate,
	    final List<TransportListable> tmpTransportableList) {

	SentTransportViewHandler sentTransportViewHandler = new SentTransportViewHandler(login, managerRepository, deviationViewHandlerFactory,
		tmpTransportableList, false, sentTransport, sentDate);

	Util.showEditViewable(new SentTransportView(sentTransportViewHandler), window1);

	handlingOrders = false;
	return sentTransportViewHandler.isCanceled();
    }

    public void saveTransportModel(Date sentDate, Transportable transportable) {
	if (transportable != null) {
	    // TransportModel transportModel = ((TransportModel)
	    // transportPresentationModel.getBean())
	    // .getBufferedObjectModel(transportPresentationModel);

	    // CheckObject checkObject = checkSaveObject(transport, null,
	    // window);
	    // String errorString = null;
	    // if (checkObject != null) {
	    // errorString = checkObject.getMsg();
	    // }
	    // if (errorString == null) {
	    // transportPresentationModel.triggerCommit();
	    // ((TransportModel)
	    // transportPresentationModel.getBean()).getObject().setSent(sentDate);
	    transportable.setSent(sentDate);
	    OverviewManager<Object> manager = (OverviewManager<Object>) ModelUtil.getBean(transportable.getManagerName());
	    manager.saveObject(transportable);
	    // saveObject(transport, window);

	    updateTransportableList();
	    // } else {
	    //
	    // Util.showErrorDialog((Component) null, "Feil", errorString);
	    //
	    // }
	}
    }

    private Date showSentOrders(final WindowInterface aWindow, final Transportable transportable, final boolean sentTransport)
	    throws ProTransException {
	Date sentDate = Util.getCurrentDate();
	boolean isCanceled = false;
	handlingOrders = true;

	sentDate = checkLoadingDate(aWindow, sentDate, transportable.getTransport());
	// if (listTransportable.size() != 0) {
	// Transport transport = ((Transportable)
	// listTransportable.get(0)).getTransport();
	checkIfTransportableHasSupplier(transportable.getTransport());

	// isCanceled = showOrders(aWindow, sentTransport, sentDate,
	// transportable.getTransport().getOrders());
	if (isCanceled) {
	    sentDate = null;
	}
	// }

	if (!isCanceled) {
	    saveTransportModel(sentDate, transportable);
	    // transportOrderTableModel.fireTableDataChanged();
	}

	return sentDate;
    }

    private Date handleTransportSending(final WindowInterface window1, final Transportable transportable, final boolean sentTransport)
	    throws ProTransException {
	boolean continueSending = true;
	Date sentDate = null;

	if (haveOrdersNotPaid(transportable)) {
	    continueSending = true;
	}
	if (continueSending) {
	    sentDate = showSentOrders(window1, transportable, sentTransport);
	}
	return sentDate;
    }

    private void lazyLoadTransportable(PostShipmentManager postShipmentManager, Transportable transportable) {
	if (transportable instanceof Order) {
	    managerRepository.getOrderManager().lazyLoadOrder(
		    (Order) transportable,
		    new LazyLoadOrderEnum[] { LazyLoadOrderEnum.COLLIES, LazyLoadOrderEnum.ORDER_LINES, LazyLoadOrderEnum.ORDER_LINE_ORDER_LINES,
			    LazyLoadOrderEnum.ORDER_COSTS, LazyLoadOrderEnum.POST_SHIPMENTS, LazyLoadOrderEnum.COMMENTS });
	} else {
	    postShipmentManager.lazyLoad((PostShipment) transportable, new LazyLoadPostShipmentEnum[] { LazyLoadPostShipmentEnum.COLLIES,
		    LazyLoadPostShipmentEnum.ORDER_LINES, LazyLoadPostShipmentEnum.ORDER_LINE_ORDER_LINES,
		    LazyLoadPostShipmentEnum.POST_SHIPMENT_REFS, LazyLoadPostShipmentEnum.ORDER_COMMENTS });
	}
    }

    private void removeSentFromCollies(Set<Colli> collies) {
	if (collies != null) {
	    for (Colli colli : collies) {
		colli.setSentBool(false);
		managerRepository.getColliManager().saveColli(colli);
	    }
	}
    }

    private void removeSentFromTransportable(Transportable transportable) {
	if (transportable instanceof Order) {
	    managerRepository.getOrderManager().refreshObject((Order) transportable);
	}
	transportable.setSentBool(false);
	transportable.getTransport().setSent(null);
    }

    private Date handleTransportNotSent(final Transportable transportable, final boolean saveTransport) throws ProTransException {
	Date sentDate;
	PostShipmentManager postShipmentManager = (PostShipmentManager) ModelUtil.getBean("postShipmentManager");
	sentDate = Util.getCurrentDate();

	Set<Colli> collies;

	// for (TransportListable transportable : listTransportListable) {
	lazyLoadTransportable(postShipmentManager, transportable);
	collies = transportable.getCollies();
	removeSentFromCollies(collies);

	removeSentFromTransportable(transportable);

	saveTransportable(postShipmentManager, transportable);

	// }

	if (saveTransport) {
	    saveTransportModel(null, transportable);
	}
	return sentDate;
    }

    private void saveTransportable(PostShipmentManager postShipmentManager, Transportable transportable) throws ProTransException {
	if (transportable instanceof Order) {
	    ((Order) transportable).setHasMissingCollies(null);

	    managerRepository.getOrderManager().saveOrder((Order) transportable);

	} else {
	    postShipmentManager.savePostShipment((PostShipment) transportable);
	}
    }

    final boolean setSent(final Boolean sent, final WindowInterface window, Transportable transportable, final boolean sentTransport) {
	try {
	    checkProbability(transportable);
	    Date sentDate = null;
	    if (sent) {
		sentDate = handleTransportSending(window, transportable, sentTransport);

	    } else {
		sentDate = handleTransportNotSent(transportable, sentTransport);
	    }

	    // transportPresentationModel.triggerCommit();

	    // if (sentDate != null) {
	    // fireSentChange();
	    // }

	    // for (TransportListable transportListable : listTransportable) {
	    vismaFileCreator.createVismaFileForDelivery(transportable.getOrder());
	    // }

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

    // final class TableClickHandler extends MouseAdapter {
    // /**
    // *
    // */
    // private WindowInterface window;
    // private ActionTransportSending actionTransportSending;
    // private ActionTransportSending actionTransportNotSending;
    //
    // /**
    // * @param aWindow
    // */
    // public TableClickHandler(WindowInterface aWindow) {
    // window = aWindow;
    // actionTransportSending = new ActionTransportSending(true,
    // ActionTransportSending.SEND_STRING, window);
    // actionTransportNotSending = new ActionTransportSending(false,
    // ActionTransportSending.NOT_SEND_STRING, window);
    // }
    //
    // /**
    // * @see
    // java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
    // */
    // @Override
    // public void mouseClicked(MouseEvent e) {
    //
    // if (SwingUtilities.isRightMouseButton(e)) {
    //
    // Transportable tmp = (Transportable)
    // transportableSelectionList.getSelection();
    //
    // if (tmp instanceof PostShipment) {
    // popupMenuTransport.add(menuItemPacklist);
    // popupMenuTransport.add(menuItemShowDeviation);
    // popupMenuTransport.remove(menuItemDeviation);
    // } else {
    // popupMenuTransport.remove(menuItemPacklist);
    // popupMenuTransport.remove(menuItemShowDeviation);
    // popupMenuTransport.add(menuItemDeviation);
    // }
    //
    // if (tmp != null) {
    // if (tmp.getSent() == null) {
    // menuItemSetSent.setAction(actionTransportSending);
    // } else {
    // menuItemSetSent.setAction(actionTransportNotSending);
    // }
    // }
    //
    // popupMenuTransport.show((JXTable) e.getSource(), e.getX(), e.getY());
    //
    // }
    //
    // }
    // }

    @SuppressWarnings("unchecked")
    private void updateTransportableList() {
	transportableList.clear();
	Iterator<Transport> transportIt = transportList.iterator();

	while (transportIt.hasNext()) {
	    Transport transport = transportIt.next();
	    // managerRepository.getTransportManager().lazyLoad(transport,
	    // new LazyLoadEnum[][] { { LazyLoadEnum.ORDERS, LazyLoadEnum.NONE
	    // }, { LazyLoadEnum.POST_SHIPMENTS, LazyLoadEnum.NONE }, });
	    transportableList.addAll(transport.getTransportables());
	}

    }

    public String getProductAreaGroupName() {
	return null;
    }

    public void setUseListView(boolean usingListView) {
	listView = usingListView;
    }

    public boolean useListView() {
	return listView;
    }

    public int getNumberOfOrders() {
	return transportList.size();
    }

    public boolean getUseListView() {
	return listView;
    }

    public JXTable getTableOrdersList() {
	return tableOrdersList;
    }

}
