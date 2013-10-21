package no.ugland.utransprod.gui.handlers;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.table.TableModel;

import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.ProTransMain;
import no.ugland.utransprod.gui.ProductAreaGroupProvider;
import no.ugland.utransprod.gui.TransportListView;
import no.ugland.utransprod.gui.TransportView;
import no.ugland.utransprod.gui.UBColumnControlPopup;
import no.ugland.utransprod.gui.Updateable;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.buttons.DeleteButton;
import no.ugland.utransprod.gui.buttons.NewButton;
import no.ugland.utransprod.gui.edit.EditTransportView;
import no.ugland.utransprod.gui.model.TransportModel;
import no.ugland.utransprod.gui.model.TransportSelectionListener;
import no.ugland.utransprod.gui.model.Transportable;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.model.Transport;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.VismaFileCreator;
import no.ugland.utransprod.service.enums.LazyLoadEnum;
import no.ugland.utransprod.util.PrefsUtil;
import no.ugland.utransprod.util.TransportComparator;
import no.ugland.utransprod.util.UserUtil;
import no.ugland.utransprod.util.Util;
import no.ugland.utransprod.util.YearWeek;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.HighlighterFactory;

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
public class TransportWeekViewHandler implements Updateable,
		TransportSelectionListener, ProductAreaGroupProvider {

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

	@Inject
	public TransportWeekViewHandler(final Login aLogin,
			final ManagerRepository aManagerRepository,
			TransportViewHandler aTransportViewHandler,
			OrderViewHandlerFactory aOrderViewHandlerFactory,
			final DeviationViewHandlerFactory aDeviationViewHandlerFactory,
			ShowTakstolInfoActionFactory aShowTakstolInfoActionFactory,
			@Assisted YearWeek aRouteDate,
			final VismaFileCreator vismaFileCreator) {
		this.vismaFileCreator = vismaFileCreator;
		showTakstolInfoActionFactory = aShowTakstolInfoActionFactory;
		deviationViewHandlerFactory = aDeviationViewHandlerFactory;
		orderViewHandlerFactory = aOrderViewHandlerFactory;
		login = aLogin;
		managerRepository = aManagerRepository;

		transportableList = new ArrayListModel();
		transportableSelectionList = new SelectionInList(
				(ListModel) transportableList);
		transportViewHandler = aTransportViewHandler;
		transportList = new ArrayListModel();
		routeDate = aRouteDate;

		transportSelectionList = new SelectionInList((ListModel) transportList);

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
			listener.propertyChange(new PropertyChangeEvent(this, null, null,
					buffering));
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
	public final SelectionInList getTransportSelectionList(
			final YearWeek routeDate1, final ProductAreaGroup productAreaGroup) {
		transportList.clear();

		List<Transport> transports = managerRepository.getTransportManager()
				.findByYearAndWeekAndProductAreaGroup(routeDate1.getYear(),
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
	public final List<Transport> getTransportList(final YearWeek routeDate1,
			final ProductAreaGroup productAreaGroup) {
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
		EditTransportView transportView = new EditTransportView(
				transportViewHandler, transport, false);
		WindowInterface dialog = new JDialogAdapter(new JDialog(
				ProTransMain.PRO_TRANS_MAIN, "Transport", true));
		dialog.setName("EditTransportView");
		dialog.add(transportView.buildPanel(dialog));
		dialog.pack();
		Util.locateOnScreenCenter(dialog);
		dialog.setVisible(true);

		if (transport.getTransportYear() != null
				&& transport.getTransportYear().equals(routeDate.getYear())
				&& transport.getTransportWeek() != null
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
		TransportViewHandler transportViewHandlerTmp = new TransportViewHandler(
				orderViewHandlerFactory, login, managerRepository,
				deviationViewHandlerFactory, showTakstolInfoActionFactory,
				vismaFileCreator);

		transportViewHandlerTmp.addBufferingListener(new BufferingListener());
		transportViewHandlerTmp.addSentListener(new SentListener());

		TransportViewHandler oldHandler = transportViewHandlers.get(transport);

		if (oldHandler != null) {
			oldHandler.removeTransportSelectionListener(this);
		}

		transportViewHandlers.put(transport, transportViewHandlerTmp);
		transportViewHandlerTmp.addTransportSelectionListener(this);
		TransportView transportView = new TransportView(
				transportViewHandlerTmp, transport);
		transportViews.add(transportView);
		return transportView;
	}

	public TransportListView getTransportListView() {
		transportListView = transportListView == null ? new TransportListView(
				this) : transportListView;
		return transportListView;
	}

	/**
	 * @see no.ugland.utransprod.gui.model.TransportSelectionListener#transportSelectionChange(boolean,
	 *      no.ugland.utransprod.gui.model.TransportModel)
	 */
	public void transportSelectionChange(boolean selection,
			TransportModel transportModel) {
		if (selection) {
			selectedTransportViewHandlers.put(transportModel.getObject(),
					transportViewHandlers.get(transportModel.getObject()));
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
				Util.showErrorDialog(window, "Feil",
						"Det kan bare velges en transport for editering");
			} else {
				Transport transport = selectedTransportViewHandlers.keySet()
						.iterator().next();
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
			aTransportViewHandler.saveTransportModel(window, transport
					.getSent());
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
			Transport changedTransport = ((TransportModel) event.getSource())
					.getObject();
			if (event.getNewValue() != null && (Boolean) event.getNewValue()) {
				changedTransportViewHandlers.put(changedTransport,
						transportViewHandlers.get(changedTransport));
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
	public void setSelectedTransportable(Transport transport,
			Transportable transportable) {
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
	public void setFilterSent(boolean sentFilter,
			ProductAreaGroup productAreaGroup) {
		PrefsUtil.setInvisibleColumns(productAreaGroup
				.getProductAreaGroupName(), TableEnum.TABLETRANSPORTORDERSLIST
				.getTableName(), tableOrdersList);
		Collection<TransportViewHandler> viewhandlers = transportViewHandlers
				.values();

		if (viewhandlers != null) {
			for (TransportViewHandler handler : viewhandlers) {
				handler.handleFilter(sentFilter, productAreaGroup);
			}
		}
	}

	public void saveUserInvisibleColumns(ProductAreaGroup productAreaGroup) {
		if (listView) {
			PrefsUtil
					.putUserInvisibleColumns(tableOrdersList, productAreaGroup);
		} else {
			if (transportList != null && transportList.size() != 0) {
				transportViewHandlers.get(transportList.get(0))
						.saveUserInvisibleColumns(productAreaGroup);
			}
		}
	}

	public void cleanUp() {
	}

	public JXTable getTableOrders(ProductAreaGroup productAreaGroup) {
		updateTransportableList();
		tableOrdersList = new JXTable();
		TableModel transportOrderTableModel = new TransportOrderTableModel(
				transportableSelectionList, transportableList, Util
						.getGavlChecker(), Util
						.getTakstolChecker(managerRepository), Util
						.getSteinChecker(), Util.getGulvsponChecker(),
				TransportOrderTableModel.TransportColumn.ForExcel.TABLE);
		tableOrdersList.setModel(transportOrderTableModel);
		tableOrdersList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tableOrdersList.setSelectionModel(new SingleListSelectionAdapter(
				transportableSelectionList.getSelectionIndexHolder()));
		tableOrdersList.setColumnControlVisible(true);
		tableOrdersList.setColumnControl(new UBColumnControlPopup(
				tableOrdersList, this));
		tableOrdersList.setRowHeight(40);
		tableOrdersList.setShowGrid(true);
		tableOrdersList.setShowVerticalLines(true);

		TransportOrderTableModel.TransportColumn[] columns = TransportOrderTableModel.TransportColumn
				.values();

		for (int i = 0; i < columns.length; i++) {
			if (columns[i].getForExcel().isEqual(
					TransportOrderTableModel.TransportColumn.ForExcel.TABLE)) {
				columns[i].setCellRenderer(tableOrdersList);
				columns[i].setPrefferedWidth(tableOrdersList);
			}
		}

		tableOrdersList.addHighlighter(HighlighterFactory
				.createAlternateStriping());
		tableOrdersList.addHighlighter(TransportViewHandler
				.getStartedPackingHighlighter(tableOrdersList, "Klar"));
		tableOrdersList.addHighlighter(TransportViewHandler
				.getReadyHighlighter(tableOrdersList, "Komplett"));
		tableOrdersList.addHighlighter(TransportViewHandler
				.getNotSentHighlighter(tableOrdersList, "Ikke sendt"));
		tableOrdersList.setShowGrid(true);
		tableOrdersList.setName(TableEnum.TABLETRANSPORTORDERSLIST
				.getTableName());
		PrefsUtil.setInvisibleColumns(productAreaGroup
				.getProductAreaGroupName(), tableOrdersList.getName(),
				tableOrdersList);

		return tableOrdersList;

	}

	@SuppressWarnings("unchecked")
	private void updateTransportableList() {
		transportableList.clear();
		Iterator<Transport> transportIt = transportList.iterator();

		while (transportIt.hasNext()) {
			Transport transport = transportIt.next();
			managerRepository.getTransportManager()
					.lazyLoad(
							transport,
							new LazyLoadEnum[][] {
									{ LazyLoadEnum.ORDERS, LazyLoadEnum.NONE },
									{ LazyLoadEnum.POST_SHIPMENTS,
											LazyLoadEnum.NONE }, });
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
}
