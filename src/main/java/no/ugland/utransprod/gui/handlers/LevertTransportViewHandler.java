package no.ugland.utransprod.gui.handlers;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.ListModel;
import javax.swing.WindowConstants;

import org.jdesktop.swingx.JXTable;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.binding.list.ArrayListModel;
import com.jgoodies.binding.list.SelectionInList;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.Closeable;
import no.ugland.utransprod.gui.IconEnum;
import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.LevertTransportView;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.ProTransMain;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.buttons.CancelButton;
import no.ugland.utransprod.gui.edit.EditDeviationView;
import no.ugland.utransprod.gui.model.DeviationModel;
import no.ugland.utransprod.gui.model.TransportListable;
import no.ugland.utransprod.gui.model.Transportable;
import no.ugland.utransprod.model.Colli;
import no.ugland.utransprod.model.Deviation;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.PostShipment;
import no.ugland.utransprod.service.ColliManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.service.PostShipmentManager;
import no.ugland.utransprod.service.enums.LazyLoadOrderEnum;
import no.ugland.utransprod.service.enums.LazyLoadPostShipmentEnum;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Util;

/**
 * Håndterer sending av ordre
 * 
 * @author atle.brekka
 */
public class LevertTransportViewHandler implements Closeable {
	List<TransportListable> list;

	SelectionInList selectionList;

	private JButton buttonShowCollies;

	boolean isCollies;

	List<Transportable> orderUpdatedCollies = new ArrayList<Transportable>();

	boolean canceled = false;

	boolean levertTransport = true;

	Date levertDate;

	private ManagerRepository managerRepository;
	private Login login;
	private DeviationViewHandlerFactory deviationViewHandlerFactory;

	@Inject
	public LevertTransportViewHandler(final Login aLogin, final ManagerRepository aManagerRepository,
			final DeviationViewHandlerFactory aDeviationViewHandlerFactory, @Assisted List<TransportListable> objects,
			@Assisted boolean isCollies, @Assisted boolean transportLevert, @Assisted Date aLevertDate) {
		deviationViewHandlerFactory = aDeviationViewHandlerFactory;
		managerRepository = aManagerRepository;
		login = aLogin;
		levertDate = aLevertDate;
		levertTransport = transportLevert;
		list = objects;
		this.isCollies = isCollies;
		selectionList = new SelectionInList(list);
		initList();
	}

	/**
	 * Sjekker om det er kollier som behandles
	 * 
	 * @return true dersom kollier
	 */
	public boolean isCollies() {
		return isCollies;
	}

	/**
	 * Initierer lister
	 */
	@SuppressWarnings("unchecked")
	private void initList() {
		if (!isCollies) {
			OrderManager orderManager = (OrderManager) ModelUtil.getBean("orderManager");
			// List<Transportable> tmpList = new ArrayList<Transportable>(list);

			// for (Transportable transportable : tmpList) {
			for (TransportListable transportable : list) {
				if (transportable instanceof Order) {
					orderManager.lazyLoadOrder((Order) transportable,
							new LazyLoadOrderEnum[] { LazyLoadOrderEnum.COLLIES, LazyLoadOrderEnum.COMMENTS });
				}
				transportable.setLevert(levertDate);

				if (levertTransport) {
					transportable.getTransport().setLevert(levertDate);
				}
			}
		} else {
			// List<Transportable> tmpList = new ArrayList<Colli>(list);
			// for (Colli colli : tmpList) {
			for (TransportListable colli : list) {
				colli.setLevert(levertDate);
			}
		}
	}

	/**
	 * Lager tabell for ordre eller kollier
	 * 
	 * @return tabell
	 */
	public JXTable getTableOrders() {
		JXTable table = new JXTable();
		if (isCollies) {
			table.setModel(new TransportColliTableModel(selectionList));
		} else {
			table.setModel(new TransportOrderTableModel(selectionList));
		}
		table.setSelectionModel(new SingleListSelectionAdapter(selectionList.getSelectionIndexHolder()));
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setColumnControlVisible(true);

		table.packAll();
		table.setName("TableOrders");
		return table;
	}

	/**
	 * Lager ok-knapp
	 * 
	 * @param window
	 * @return knapp
	 */
	public JButton getButtonOk(WindowInterface window) {
		JButton button = new CancelButton(window, this, true, "Ok", IconEnum.ICON_OK, null, true);
		button.setName("ButtonOk");
		return button;
	}

	/**
	 * Tabellmodell for ordre
	 * 
	 * @author atle.brekka
	 */
	private class TransportOrderTableModel extends AbstractTableAdapter {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * @param listModel
		 */
		public TransportOrderTableModel(ListModel listModel) {
			super(listModel, new String[] { "Ordre", "Levert" });

		}

		/**
		 * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
		 */
		@Override
		public Class<?> getColumnClass(int columnIndex) {
			switch (columnIndex) {
			case 0:
				return Order.class;
			case 1:
				return Boolean.class;

			default:
				throw new IllegalStateException("Unknown column");
			}
		}

		/**
		 * @see javax.swing.table.TableModel#getValueAt(int, int)
		 */
		public Object getValueAt(int rowIndex, int columnIndex) {
			Transportable transportable = (Transportable) getRow(rowIndex);
			switch (columnIndex) {
			case 0:
				return transportable;
			case 1:
				return transportable.getLevertBool();

			default:
				throw new IllegalStateException("Unknown column");
			}
		}

		/**
		 * @see javax.swing.table.AbstractTableModel#setValueAt(java.lang.Object,
		 *      int, int)
		 */
		@Override
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			Transportable transportable = (Transportable) getRow(rowIndex);

			switch (columnIndex) {
			case 1:
				if ((Boolean) aValue) {
					transportable.setLevert(levertDate);
				} else {
					transportable.setLevert(null);
				}

				break;
			}
		}

		/**
		 * @see javax.swing.table.AbstractTableModel#isCellEditable(int, int)
		 */
		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			if (columnIndex == 1) {
				return true;
			}
			return false;
		}

	}

	/**
	 * Tabellmodell for kollier
	 * 
	 * @author atle.brekka
	 */
	private class TransportColliTableModel extends AbstractTableAdapter {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * @param listModel
		 */
		public TransportColliTableModel(ListModel listModel) {
			super(listModel, new String[] { "Kolli", "Levert" });

		}

		/**
		 * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
		 */
		@Override
		public Class<?> getColumnClass(int columnIndex) {
			switch (columnIndex) {
			case 0:
				return Colli.class;
			case 1:
				return Boolean.class;

			default:
				throw new IllegalStateException("Unknown column");
			}
		}

		/**
		 * @see javax.swing.table.TableModel#getValueAt(int, int)
		 */
		public Object getValueAt(int rowIndex, int columnIndex) {
			Colli colli = (Colli) getRow(rowIndex);
			switch (columnIndex) {
			case 0:
				return colli;
			case 1:
				return colli.getLevertBool();

			default:
				throw new IllegalStateException("Unknown column");
			}
		}

		/**
		 * @see javax.swing.table.AbstractTableModel#setValueAt(java.lang.Object,
		 *      int, int)
		 */
		@Override
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			Colli colli = (Colli) getRow(rowIndex);

			switch (columnIndex) {
			case 1:
				if ((Boolean) aValue) {
					colli.setLevert(levertDate);
				} else {
					colli.setLevert(null);
				}
				break;
			}
		}

		/**
		 * @see javax.swing.table.AbstractTableModel#isCellEditable(int, int)
		 */
		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			if (columnIndex == 1) {
				return true;
			}
			return false;
		}

	}

	/**
	 * @see no.ugland.utransprod.gui.Closeable#canClose(java.lang.String,
	 *      no.ugland.utransprod.gui.WindowInterface)
	 */
	public boolean canClose(String actionString, WindowInterface window) {
		saveObjects(window);

		return true;
	}

	/**
	 * Lagrer objekter
	 * 
	 * @param window
	 */
	@SuppressWarnings("unchecked")
	private void saveObjects(final WindowInterface window) {
		ColliManager colliManager = (ColliManager) ModelUtil.getBean("colliManager");
		if (isCollies) {
			saveCollies(colliManager);
		} else {
			handleLevertTransportables(window);
		}
	}

	private void handleLevertTransportables(final WindowInterface window) {
		OrderManager orderManager = (OrderManager) ModelUtil.getBean("orderManager");
		PostShipmentManager postShipmentManager = (PostShipmentManager) ModelUtil.getBean("postShipmentManager");
		List<TransportListable> updatedTransportables = new ArrayList<TransportListable>(list);

		for (TransportListable transportlistable : updatedTransportables) {

			// lazyLoadTransportlistable(orderManager, postShipmentManager,
			// transportlistable);
//			lazyLoadTree(orderManager, postShipmentManager, transportlistable);
			if (transportlistable instanceof Order) {
				orderManager.lazyLoadOrder((Order) transportlistable,
						new LazyLoadOrderEnum[] { LazyLoadOrderEnum.ORDER_LINES, LazyLoadOrderEnum.COLLIES});
			} else {
				postShipmentManager.lazyLoad((PostShipment) transportlistable, new LazyLoadPostShipmentEnum[] {
						LazyLoadPostShipmentEnum.ORDER_LINES, LazyLoadPostShipmentEnum.COLLIES});
			}
			
			transportlistable.setLevert(levertDate);
			handleCollies(transportlistable);
			if (checkMissingOrderLines(transportlistable, window)) {
				settLevert(window, orderManager, postShipmentManager, transportlistable,levertDate);

				// lazyLoadTree(orderManager, postShipmentManager,
				// transportlistable);
			}

		}
	}

	private boolean checkMissingOrderLines(final TransportListable transportlistable, final WindowInterface window) {
		if (transportlistable.getLevertBool()) {
			ArrayListModel orderLinesNotLevert = null;
			List<OrderLine> notLevert = transportlistable.getOrderLinesNotLevert();
			if (notLevert != null && notLevert.size() != 0) {
				orderLinesNotLevert = new ArrayListModel(notLevert);
			}
			if (orderLinesNotLevert != null && orderLinesNotLevert.size() != 0) {
				if (Util.showConfirmDialog(window.getComponent(), "Mangler!", "Det finnes ordrelinjer for ordre "
						+ transportlistable
						+ "\n som ikke er levert, dersom den sendes må det registreres et avvik.\nVil du allikevel sende denne?")) {
					registerDeviation(orderLinesNotLevert, transportlistable);
					transportlistable.setLevert(levertDate);
				} else {
					return false;
				}

				/*
				 * MsgDialog( null, "Ikke sent",
				 * "Det finnes ordrelinjer for ordre " + transportlistable +
				 * " som ikke er sent, dersom den sendes må registreres et avvik.\nVil du allikevel sende denne?"
				 * ); registerDeviation(orderLinesNotSent, transportlistable);
				 */
			}
		}
		return true;
	}

	private void lazyLoadTree(OrderManager orderManager, PostShipmentManager postShipmentManager,
			TransportListable transportlistable) {
		if (transportlistable instanceof Order) {
			orderManager.lazyLoadTree((Order) transportlistable);
		} else {
			postShipmentManager.lazyLoadTree((PostShipment) transportlistable);
		}
	}

	private void settLevert(final WindowInterface window, OrderManager orderManager,
			PostShipmentManager postShipmentManager, TransportListable transportlistable, Date levertDate) {
		if (transportlistable instanceof Order) {
			orderManager.settLevert((Order)transportlistable,levertDate);
//			try {
//				orderManager.saveOrder((Order) transportlistable);
//			} catch (ProTransException e) {
//				Util.showErrorDialog(window, "Feil", e.getMessage());
//				e.printStackTrace();
//			}
		} else {
			postShipmentManager.settLevert((PostShipment) transportlistable,levertDate);
//			postShipmentManager.savePostShipment((PostShipment) transportlistable);
		}
	}

	private void handleCollies(TransportListable transportlistable) {
		ColliManager colliManager = (ColliManager) ModelUtil.getBean("colliManager");
		Set<Colli> collies;
		if (!orderUpdatedCollies.contains(transportlistable)) {
			collies = transportlistable.getCollies();
			if (collies != null) {
				for (Colli colli : collies) {
					colli.setLevert(levertDate);
					colliManager.saveColli(colli);
				}
			}
		}
	}

	private void lazyLoadTransportlistable(OrderManager orderManager, PostShipmentManager postShipmentManager,
			TransportListable transportlistable) {
		if (transportlistable instanceof Order) {
			orderManager.lazyLoadOrder((Order) transportlistable, new LazyLoadOrderEnum[] {
					LazyLoadOrderEnum.ORDER_LINES, LazyLoadOrderEnum.COLLIES, LazyLoadOrderEnum.COMMENTS });
		} else {
			postShipmentManager.lazyLoad((PostShipment) transportlistable,
					new LazyLoadPostShipmentEnum[] { LazyLoadPostShipmentEnum.ORDER_LINES,
							LazyLoadPostShipmentEnum.COLLIES, LazyLoadPostShipmentEnum.ORDER_COMMENTS });
		}
	}

	private void saveCollies(ColliManager colliManager) {
		for (TransportListable colli : list) {
			colliManager.saveColli((Colli) colli);
		}
	}

	/**
	 * Registrerer avvik
	 * 
	 * @param orderLines
	 * @param transportlistable
	 */
	@SuppressWarnings("unchecked")
	private void registerDeviation(ArrayListModel orderLines, TransportListable transportlistable) {
		PostShipment postShipment = new PostShipment();
		PostShipmentManager postShipmentManager = (PostShipmentManager) ModelUtil.getBean("postShipmentManager");

		// DeviationViewHandler2 deviationViewHandler =
		// deviationViewHandlerFactory.create(transportlistable.getOrder(),
		// false,
		// false, false, null, true);
		DeviationViewHandler deviationViewHandler = new DeviationViewHandler(login, managerRepository, null,
				transportlistable.getOrder(), true, false, true, null, true, false);

		Deviation deviation = new Deviation();

		DeviationModel deviationModel = new DeviationModel(deviation, true);

		deviationModel.setOrderLines(orderLines);
		deviationModel.setPostShipment(postShipment);
		deviationModel.setOrder(transportlistable.getOrder());

		EditDeviationView editDeviationView = new EditDeviationView(false, deviationModel, deviationViewHandler, true,
				true, false);

		JDialog dialog = new JDialog(ProTransMain.PRO_TRANS_MAIN, "Avvik", true);
		dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		WindowInterface window = new JDialogAdapter(dialog);

		window.add(editDeviationView.buildPanel(window), BorderLayout.CENTER);

		window.pack();
		Util.locateOnScreenCenter(window);
		window.setVisible(true);

		postShipment = deviation.getPostShipment();

		// postShipment.setDeviation(deviation);
		postShipment.setOrder(transportlistable.getOrder());
		postShipment.cacheComments();

		if (transportlistable instanceof PostShipment) {
			postShipment.setPostShipmentRef((PostShipment) transportlistable);
		}

		postShipmentManager.savePostShipment(postShipment);
		Colli colli;
		Iterator<OrderLine> it = orderLines.iterator();
		while (it.hasNext()) {
			OrderLine orderLine = it.next();
			orderLine.setPostShipment(postShipment);
			colli = orderLine.getColli();
			if (colli != null) {
				ColliManager colliManager = (ColliManager) ModelUtil.getBean("colliManager");
				colli.setPostShipment(postShipment);
				colli.setOrder(null);
				colliManager.saveColli(colli);
			}
		}

		/*
		 * if (transportlistable instanceof Order) { OrderManager orderManager =
		 * (OrderManager) ModelUtil .getBean("orderManager"); try {
		 * orderManager.saveOrder((Order) transportlistable); } catch
		 * (ProTransException e) { Util.showErrorDialog(window, "Feil",
		 * e.getMessage()); e.printStackTrace(); } } else { postShipmentManager
		 * .savePostShipment((PostShipment) transportlistable); }
		 */
	}

	/**
	 * Lager knapp for å vise kollier
	 * 
	 * @return knapp
	 */
	public JButton getButtonShowCollies() {
		buttonShowCollies = new JButton(new ShowColliesAction());
		buttonShowCollies.setEnabled(false);
		selectionList.addPropertyChangeListener(SelectionInList.PROPERTYNAME_SELECTION_INDEX,
				new SelectionPropertyListener());
		buttonShowCollies.setName("ButtonShowCollies");
		return buttonShowCollies;
	}

	/**
	 * Lager avbrytknapp
	 * 
	 * @param window
	 * @return knapp
	 */
	public JButton getButtonCancel(WindowInterface window) {
		return new CancelButton(window, new CancelWindow(), true);
	}

	/**
	 * Enabler/disabler knapper
	 */
	void enableButtons() {
		if (!isCollies) {
			Transportable transportable = (Transportable) selectionList.getSelection();

			if (transportable != null && transportable.getCollies() != null && transportable.getCollies().size() != 0) {
				buttonShowCollies.setEnabled(true);
			} else {
				buttonShowCollies.setEnabled(false);
			}
		}
	}

	/**
	 * Viser kollier
	 * 
	 * @author atle.brekka
	 */
	private class ShowColliesAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * 
		 */
		public ShowColliesAction() {
			super("Kollier...");
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@SuppressWarnings("unchecked")
		public void actionPerformed(ActionEvent arg0) {
			Transportable transportable = (Transportable) selectionList.getSelection();

			if (transportable.getCollies() != null) {
				orderUpdatedCollies.add(transportable);
				LevertTransportViewHandler levertTransportViewHandler = new LevertTransportViewHandler(login,
						managerRepository, deviationViewHandlerFactory, new ArrayListModel(transportable.getCollies()),
						true, levertTransport, levertDate);
				LevertTransportView levertTransportView = new LevertTransportView(levertTransportViewHandler);
				WindowInterface dialog = new JDialogAdapter(new JDialog(ProTransMain.PRO_TRANS_MAIN, "Kollier", true));
				dialog.setName("levertTransportView");
				dialog.add(levertTransportView.buildPanel(dialog));
				dialog.pack();
				Util.locateOnScreenCenter(dialog);
				dialog.setVisible(true);

			}
		}
	}

	/**
	 * Lytter på selektering i tabell
	 * 
	 * @author atle.brekka
	 */
	class SelectionPropertyListener implements PropertyChangeListener {

		/**
		 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
		 */
		public void propertyChange(PropertyChangeEvent evt) {
			enableButtons();

		}

	}

	/**
	 * Sjekker om dialog er kansellert
	 * 
	 * @return true dersom kansellert
	 */
	public boolean isCanceled() {
		return canceled;
	}

	/**
	 * Lukker vindu
	 * 
	 * @author atle.brekka
	 */
	class CancelWindow implements Closeable {

		/**
		 * @see no.ugland.utransprod.gui.Closeable#canClose(java.lang.String,
		 *      no.ugland.utransprod.gui.WindowInterface)
		 */
		@SuppressWarnings("unchecked")
		public boolean canClose(String actionString, WindowInterface window) {
			canceled = true;
			List<TransportListable> tmpList = new ArrayList<TransportListable>(list);

			if (!isCollies) {
				OrderManager orderManager = (OrderManager) ModelUtil.getBean("orderManager");
				for (TransportListable transportListable : tmpList) {
					if (transportListable instanceof Order) {
						orderManager.lazyLoadOrder((Order) transportListable,
								new LazyLoadOrderEnum[] { LazyLoadOrderEnum.COLLIES });
					}
					transportListable.setSentBool(false);

					if (levertTransport) {
						transportListable.getTransport().setLevert(null);
					}
				}
			}
			return true;
		}

	}
}
