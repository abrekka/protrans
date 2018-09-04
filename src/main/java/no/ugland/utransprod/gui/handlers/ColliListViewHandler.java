package no.ugland.utransprod.gui.handlers;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.ColliView;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.Updateable;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.buttons.DeleteButton;
import no.ugland.utransprod.gui.buttons.NewButton;
import no.ugland.utransprod.gui.model.ColliListener;
import no.ugland.utransprod.gui.model.ColliModel;
import no.ugland.utransprod.gui.model.Packable;
import no.ugland.utransprod.gui.model.Transportable;
import no.ugland.utransprod.model.Colli;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.OrderLineAttribute;
import no.ugland.utransprod.service.Manager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.OrderLineManager;
import no.ugland.utransprod.service.OverviewManager;
import no.ugland.utransprod.service.VismaFileCreator;
import no.ugland.utransprod.service.enums.LazyLoadEnum;
import no.ugland.utransprod.service.enums.LazyLoadOrderLineEnum;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.UserUtil;
import no.ugland.utransprod.util.Util;

import org.hibernate.Hibernate;

import com.google.common.collect.Multimap;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class ColliListViewHandler implements ColliListener, Updateable, ColliViewHandlerProvider {
	Map<Colli, ColliViewHandler> colliViewHandlers = new Hashtable<Colli, ColliViewHandler>();
	private ManagerRepository managerRepository;
	private Login login;
	Map<Colli, ColliViewHandler> selectedColliViewHandlers = new Hashtable<Colli, ColliViewHandler>();
	private JButton buttonRemoveColli;
	private JButton buttonEditColli;
	private JButton buttonAddColli;
	boolean refreshing = false;
	private Multimap<String, String> colliSetup;
	private List<ListDataListener> colliListListeners = new ArrayList<ListDataListener>();
	List<ColliListener> colliListeners = new ArrayList<ColliListener>();

	private List<Colli> colliList;
	private Packable packable;
	private VismaFileCreator vismaFileCreator;

	@Inject
	public ColliListViewHandler(Login aLogin, ManagerRepository aManagerRepository,
			@Assisted Multimap<String, String> aColliSetup, VismaFileCreator vismaFileCreator) {
		this.vismaFileCreator = vismaFileCreator;
		login = aLogin;
		managerRepository = aManagerRepository;
		this.colliSetup = aColliSetup;
		colliList = new ArrayList<Colli>();

	}

	public void addListDataListener(ListDataListener listener) {
		colliListListeners.add(listener);
	}

	public void addColliListener(ColliListener listener) {
		colliListeners.add(listener);
	}

	@SuppressWarnings("unchecked")
	public List<Colli> getColliList() {
		// return (List<Colli>)
		// presentationModelPackable.getValue(AbstractOrderModel.PROPERTY_COLLI_LIST);
		return colliList;
	}

	// public Comparator<? super Colli> getColliComparator() {
	// return new ColliComparator();
	// }

	/*
	 * public class ColliComparator implements Comparator<Colli> {
	 * 
	 * public int compare(Colli colli1, Colli colli2) { return new
	 * CompareToBuilder().append(colli1.getColliName(),
	 * colli2.getColliName()).toComparison(); }
	 * 
	 * }
	 */

	public ColliView getColliView(Colli colli, WindowInterface window) {
		ColliViewHandler colliViewHandler = colliViewHandlers.get(colli);

		if (colliViewHandler == null) {
			colliViewHandler = new ColliViewHandler("Kolli", colli,
					// (Packable) presentationModelPackable.getBean(),
					packable, login, managerRepository, window, vismaFileCreator);
			colliViewHandlers.put(colli, colliViewHandler);
			colliViewHandler.addColliSelectionListener(this);
			addColliListener(colliViewHandler);
		} else {
			colliViewHandler.initColli(colli);
		}
		ColliView colliView = new ColliView(colliViewHandler);
		return colliView;
	}

	public void colliSelectionChange(boolean selection, ColliModel colliModel) {
		if (hasWriteAccess()) {
			if (selection) {
				selectedColliViewHandlers.put(colliModel.getObject(), colliViewHandlers.get(colliModel.getObject()));
			} else {
				selectedColliViewHandlers.remove(colliModel.getObject());
			}

			if (selectedColliViewHandlers.size() != 0) {
				buttonRemoveColli.setEnabled(true);
				buttonEditColli.setEnabled(true);
			} else {
				buttonRemoveColli.setEnabled(false);
				buttonEditColli.setEnabled(false);
			}
		}

	}

	public void orderLineRemoved(WindowInterface window) {
		fireOrderLineRemoved(window);

	}

	private void fireOrderLineRemoved(WindowInterface window) {
		for (ColliListener listener : colliListeners) {
			listener.orderLineRemoved(window);
		}
	}

	public boolean hasWriteAccess() {
		return UserUtil.hasWriteAccess(login.getUserType(), "Garasjepakke");
	}

	public JButton getButtonAddColli(WindowInterface window) {
		buttonAddColli = new NewButton("kolli", this, window);
		return buttonAddColli;
	}

	public boolean doDelete(WindowInterface window) {
		boolean returnValue = true;
		Set<Colli> collies = selectedColliViewHandlers.keySet();
		ColliViewHandler handler;

		for (Colli colli : collies) {
			handler = selectedColliViewHandlers.get(colli);
			if (handler != null) {
				returnValue = handler.doDelete(window);
				if (returnValue) {
					handler = colliViewHandlers.remove(colli);
					colliList.remove(colli);
					packable.removeColli(colli);
				}
			}
		}
		selectedColliViewHandlers.clear();

		fireColliRemoved();
		fireListChanged();
		return returnValue;
	}

	@SuppressWarnings("unchecked")
	public void doNew(WindowInterface window) {
		// AbstractOrderModel abstractOrderModel = (AbstractOrderModel)
		// presentationModelPackable.getBean();

		Colli newColli = new Colli(null, packable.getOrder(), // abstractOrderModel.getOrderModelOrder(),
				null, null, null, null,
				// abstractOrderModel.getOrderModelPostShipment(),
				packable.getPostShipment(), null, null);
		ColliViewHandler colliViewHandler = new ColliViewHandler("Kolli", newColli,
				// abstractOrderModel,
				packable, login, managerRepository, window, vismaFileCreator);
		boolean isOk = colliViewHandler.openEditView(null, false, window, false);
		// abstractOrderModel.addColli(newColli);
		if (newColli.getColliId() != null) {
			packable.addColli(newColli);
			colliList.add(newColli);
			fireListChanged();

		}

	}

	public void doRefresh(WindowInterface window) {
		try {
			refreshing = true;

			// presentationModelPackable.setBean(new OrderModel(new
			// Order(),false, false, false, null, null));

			selectedColliViewHandlers.clear();
			buttonAddColli.setEnabled(false);
			buttonRemoveColli.setEnabled(false);
			buttonEditColli.setEnabled(false);

			checkCollies(window);
		} catch (ProTransException e) {
			e.printStackTrace();
			Util.showErrorDialog(window, "Feil", e.getMessage());
		}

	}

	public void doSave(WindowInterface window) {
	}

	private void fireColliRemoved() {
		for (ListDataListener listener : colliListListeners) {
			listener.intervalRemoved(new ListDataEvent(this, -1, -1, -1));
		}
		buttonEditColli.setEnabled(false);
		buttonRemoveColli.setEnabled(false);
	}

	public void fireListChanged() {
		for (ListDataListener listener : colliListListeners) {
			listener.contentsChanged(new ListDataEvent(this, -1, -1, -1));
		}
	}

	@SuppressWarnings("unchecked")
	public void checkCollies(WindowInterface window) throws ProTransException {
		if (!refreshing) {
			OverviewManager overviewManager = (OverviewManager) ModelUtil.getBean(packable.getManagerName());
			if (!defaultColliesGenerated()) {
				overviewManager.refreshObject(packable);
//				packable.setDefaultColliesGenerated(1);
//				overviewManager.saveObject(packable);
				// packable = (Packable) overviewManager.merge(packable);
				if (!Hibernate.isInitialized(packable.getCollies())) {
					initializePackable(packable);
				}
				List<Colli> collies = packable.getColliList();
				List<OrderLine> orderLines = packable.getOrderLineList();
				Colli tmpColli;

				tmpColli = new Colli(null, packable.getOrder(), null, null, null, null, packable.getPostShipment(),
						null, null);
				if (collies == null) {
					collies = new ArrayList<Colli>();

				}
				// sjekk om kollier Takstol,Gavl,Gulvspon,Garasjepakke er med
				// for
				// ordre,
				// sjekk mot artikler

				Set<String> colliNames = colliSetup.keySet();
				if (colliNames != null) {
					for (String colliName : colliNames) {
						tmpColli.setColliName(colliName);
						if (!collies.contains(tmpColli)) {
							if (!Hibernate.isInitialized(packable.getCollies())) {
								initializePackable(packable);
							}
							if (shouldHaveColli(orderLines, colliSetup.get(colliName), packable.getTransportable())) {
								Colli newColli = new Colli(null, tmpColli.getOrder(), tmpColli.getColliName(), null,
										null, null, tmpColli.getPostShipment(), null, null);
								packable.addColli(newColli);

								if (colliName.equalsIgnoreCase("Takstein")) {
									checkTakstein(orderLines, newColli, window);
								}
								managerRepository.getColliManager().saveColli(newColli);
							}
						}
					}
				}

				packable.setDefaultColliesGenerated(1);
				overviewManager.saveObject(packable);
				setPackable(packable, null);
			}
		}
	}

	private void initializePackable(Packable packable) {
		Manager manager = (Manager) ModelUtil.getBean(packable.getManagerName());
		manager.lazyLoad(packable,
				new LazyLoadEnum[][] { { LazyLoadEnum.COLLIES, LazyLoadEnum.NONE },
						{ LazyLoadEnum.ORDER_LINES, LazyLoadEnum.NONE },
						{ LazyLoadEnum.ORDER_COMMENTS, LazyLoadEnum.NONE } });

	}

	@SuppressWarnings("unchecked")
	private boolean defaultColliesGenerated() {
		return Util
				// .convertNumberToBoolean(((AbstractOrderModel)
				// presentationModelPackable
				.convertNumberToBoolean(packable.getDefaultColliesGenerated());
	}

	private boolean shouldHaveColli(List<OrderLine> orderLines, Collection<String> articlenames,
			Transportable transportable) {
		OrderLineManager orderLineManager = (OrderLineManager) ModelUtil.getBean("orderLineManager");
		if (orderLines != null) {
			for (OrderLine orderLine : orderLines) {
				if (orderLine.getHasArticle() == null) {
					orderLineManager.lazyLoad(orderLine,
							new LazyLoadOrderLineEnum[] { LazyLoadOrderLineEnum.ORDER_LINE_ATTRIBUTE });
				}
				if (articlenames.contains(orderLine.getArticleName()) && orderLine.hasArticle()
						&& orderLine.belongTo(transportable)) {
					return true;
				}
			}
		}
		return false;
	}

	public void checkTakstein(List<OrderLine> orderLines, Colli colli, WindowInterface window) {

		if (orderLines != null) {
			for (OrderLine orderLine : orderLines) {
				managerRepository.getOrderLineManager().lazyLoad(orderLine,
						new LazyLoadOrderLineEnum[] { LazyLoadOrderLineEnum.ORDER_LINE_ATTRIBUTE });
				if (orderLine.getArticleName().equalsIgnoreCase("Takstein")) {
					Set<OrderLineAttribute> attributes = orderLine.getOrderLineAttributes();
					if (attributes != null) {
						for (OrderLineAttribute attribute : attributes) {
							if (attribute.getAttributeName().equalsIgnoreCase("Sendes fra GG")
									&& (attribute.getAttributeValue() == null
											|| attribute.getAttributeValue().equalsIgnoreCase("Nei"))) {
								ColliViewHandler colliViewHandler = colliViewHandlers.get(colli);
								try {
									if (colliViewHandler != null) {
										colliViewHandler.addOrderLine(orderLine, 0);
									} else {
										colliViewHandler = new ColliViewHandler("Kolli", colli,
												// (Packable)
												// presentationModelPackable.getBean(),
												packable, login, managerRepository, window, vismaFileCreator);
										colliViewHandler.addOrderLine(orderLine, 0);
										colliViewHandlers.put(colli, colliViewHandler);
										colliViewHandler.addColliSelectionListener(this);
										addColliListener(colliViewHandler);
									}
									fireOrderLineRemoved(null);
									fireListChanged();
								} catch (ProTransException e) {
									Util.showErrorDialog(window, "Feil", e.getMessage());
									e.printStackTrace();
								}
							}
						}
					}
				}
			}
		}
	}

	public JButton getButtonEditColli(WindowInterface window) {
		buttonEditColli = new JButton(new EditColliAction(window));
		buttonEditColli.setEnabled(false);
		return buttonEditColli;
	}

	private final class EditColliAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		private WindowInterface window;

		public EditColliAction(WindowInterface aWindow) {
			super("Editer kolli...");
			window = aWindow;
		}

		public void actionPerformed(ActionEvent arg0) {

			if (selectedColliViewHandlers.size() != 1) {
				Util.showErrorDialog(window, "Feil", "Det kan bare velges en kolli for editering");
			} else {
				Colli colli = selectedColliViewHandlers.keySet().iterator().next();
				ColliViewHandler handler = selectedColliViewHandlers.remove(colli);
				handler.openEditView(colli, false, window, false);
				handler.setColliSelected(false);
				colliViewHandlers.put(colli, handler);
				selectedColliViewHandlers.clear();
				handler.refreshCollies();// updateColliModel();
			}

		}
	}

	public JButton getButtonRemoveColli(WindowInterface window) {
		buttonRemoveColli = new DeleteButton("kolli", this, window);
		buttonRemoveColli.setEnabled(false);
		return buttonRemoveColli;
	}

	/*
	 * public void setPresentationModel( PresentationModel presentationModel) {
	 * presentationModelPackable=presentationModel;
	 * 
	 * }
	 */

	public ColliViewHandler getColliViewHandler(Colli colli) {
		return colliViewHandlers.get(colli);
	}

	public void setPackable(Packable aPackable, List<Colli> collies) {
		packable = aPackable;
		colliList.clear();
		if (collies != null) {
			colliList.addAll(collies);
		} else {
			colliList.addAll(packable.getColliList());
		}
		// Collections.sort(colliList);
	}

	public void refreshCollies() {
		fireRefreshCollies();

	}

	private void fireRefreshCollies() {
		for (ColliListener listener : colliListeners) {
			listener.refreshCollies();
		}

	}

	public Packable getPackable() {
		return packable;
	}

	public void putColliViewHandler(Colli colli, ColliViewHandler colliViewHandler) {
		colliViewHandlers.put(colli, colliViewHandler);
	}
}
