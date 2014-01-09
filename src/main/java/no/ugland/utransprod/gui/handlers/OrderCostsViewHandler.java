package no.ugland.utransprod.gui.handlers;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.OrderCostView;
import no.ugland.utransprod.gui.Updateable;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.buttons.DeleteButton;
import no.ugland.utransprod.gui.buttons.NewButton;
import no.ugland.utransprod.gui.model.DeviationModel;
import no.ugland.utransprod.gui.model.ICostableModel;
import no.ugland.utransprod.model.CostType;
import no.ugland.utransprod.model.CostUnit;
import no.ugland.utransprod.model.Deviation;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderCost;
import no.ugland.utransprod.service.CostTypeManager;
import no.ugland.utransprod.service.CostUnitManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.util.ApplicationParamUtil;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Util;
import no.ugland.utransprod.util.YesNoInteger;

import org.jdesktop.swingx.JXTable;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.binding.list.ArrayListModel;
import com.jgoodies.binding.list.SelectionInList;

/**
 * Hjelpeklasse for visning av kostnader for ordre
 * 
 * @author atle.brekka
 * 
 */
public class OrderCostsViewHandler implements CostChangeListener {
    PresentationModel presentationModel;

    final ArrayListModel costsList;

    final SelectionInList costSelectionList;

    private List<Component> components = new ArrayList<Component>();

    boolean enabled = true;

    private JButton buttonEditCost;

    private JButton buttonRemoveCost;

    private JButton buttonAddCost;

    private JXTable tableCost;

    private boolean addInternalCost;

    private boolean tableEditable;
    private boolean addDefaultCosts;
    private Login login;
    private ManagerRepository managerRepository;

    /**
     * @param aPresentationModel
     * @param aUserType
     * @param doAddInternalCost
     * @param isTableEditable
     * @param doAddDefaultCosts
     */
    public OrderCostsViewHandler(PresentationModel aPresentationModel, Login aLogin, boolean doAddInternalCost, boolean isTableEditable,
	    boolean doAddDefaultCosts, ManagerRepository aManagerRepository) {
	login = aLogin;
	managerRepository = aManagerRepository;
	addDefaultCosts = doAddDefaultCosts;
	tableEditable = isTableEditable;
	addInternalCost = doAddInternalCost;
	presentationModel = aPresentationModel;
	costsList = new ArrayListModel();
	costSelectionList = new SelectionInList((ListModel) costsList);
    }

    /**
     * Henter tabell for kostnader
     * 
     * @param window
     * 
     * @return tabell
     * @throws ProTransException
     */
    public JXTable getTableCost(WindowInterface window) throws ProTransException {
	tableCost = new JXTable();
	refreshCosts();
	tableCost.setModel(new CostTableModel(costSelectionList, tableEditable, presentationModel));
	tableCost.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	tableCost.setSelectionModel(new SingleListSelectionAdapter(costSelectionList.getSelectionIndexHolder()));
	tableCost.setColumnControlVisible(true);
	tableCost.setSearchable(null);
	components.add(tableCost);
	tableCost.setName("TableCost");
	return tableCost;
    }

    /**
     * Setter kostnadsliste
     * 
     * @throws ProTransException
     */
    @SuppressWarnings("unchecked")
    private void refreshCosts() throws ProTransException {
	costSelectionList.clearSelection();
	costsList.clear();
	costsList.addAll(Util.cloneCosts((Collection) presentationModel.getBufferedValue(ICostableModel.PROPERTY_COSTS)));

	if (costsList.size() == 0 && addInternalCost) {
	    CostTypeManager costTypeManager = (CostTypeManager) ModelUtil.getBean("costTypeManager");
	    CostUnitManager costUnitManager = (CostUnitManager) ModelUtil.getBean("costUnitManager");
	    CostType costTypeDev = costTypeManager.findByName("Avvik");
	    CostUnit costUnitInternal = costUnitManager.findByName("Intern");
	    OrderCost defaultOrderCost = new OrderCost();
	    defaultOrderCost.setCostAmount(BigDecimal.valueOf(500));
	    defaultOrderCost.setCostType(costTypeDev);
	    defaultOrderCost.setCostUnit(costUnitInternal);

	    addCost(defaultOrderCost);
	    presentationModel.setBufferedValue(ICostableModel.PROPERTY_COSTS, costsList);
	}

	if (addDefaultCosts) {
	    checkDefaultDeviationCosts();
	}
    }

    /**
     * Sjekker om alle default kostander er med, dersom ikke blir disse lagt til
     * 
     * @throws ProTransException
     */
    private void checkDefaultDeviationCosts() throws ProTransException {
	CostTypeManager costTypeManager = (CostTypeManager) ModelUtil.getBean("costTypeManager");
	CostUnitManager costUnitManager = (CostUnitManager) ModelUtil.getBean("costUnitManager");
	List<String> defaultCosts = ApplicationParamUtil.getDefaultDeviationCosts();

	CostType costType;
	CostUnit costUnitInternal = costUnitManager.findByName("Intern");
	OrderCost orderCost;
	List<CostType> containsCostTypes = getCostTypes();
	for (String costName : defaultCosts) {
	    costType = costTypeManager.findByName(costName);

	    if (costType == null) {
		throw new ProTransException("Kan ikke finne kostnadstype " + costName);
	    }

	    if (!containsCostTypes.contains(costType)) {
		orderCost = new OrderCost();
		orderCost.setCostType(costType);
		orderCost.setCostUnit(costUnitInternal);
		orderCost.setOrder((Order) presentationModel.getBufferedValue(ICostableModel.PROPERTY_ORDER));
		orderCost.setDeviation((Deviation) presentationModel.getBufferedValue(ICostableModel.PROPERTY_DEVIATION));
		addCost(orderCost);
		presentationModel.setBufferedValue(ICostableModel.PROPERTY_COSTS, costsList);
	    }
	}

    }

    /**
     * Henter ut kostnadstyper som er definert
     * 
     * @return kostnadstyper
     */
    @SuppressWarnings("unchecked")
    private List<CostType> getCostTypes() {
	List<CostType> costTypes = new ArrayList<CostType>();
	Iterator<OrderCost> costIt = costsList.iterator();
	while (costIt.hasNext()) {
	    costTypes.add(costIt.next().getCostType());
	}
	return costTypes;
    }

    /**
     * Henter knapp for å legge til kostnad
     * 
     * @param window
     * @return knapp
     */
    public JButton getAddCostButton(WindowInterface window) {
	buttonAddCost = new NewButton("kostnad", new CostUpdate(), window);
	buttonAddCost.setName("ButtonAddCost");
	components.add(buttonAddCost);
	return buttonAddCost;
    }

    /**
     * Henter knapp for å fjerne kostnad
     * 
     * @param window
     * @return knapp
     */
    public JButton getRemoveCostButton(WindowInterface window) {
	buttonRemoveCost = new DeleteButton("kostnad", new CostUpdate(), window);
	buttonRemoveCost.setName("ButtonRemoveCost");
	buttonRemoveCost.setEnabled(false);
	components.add(buttonRemoveCost);
	return buttonRemoveCost;
    }

    /**
     * Henter knapp for å editere kostnad
     * 
     * @param window
     * @return knapp
     */
    public JButton getEditCostButton(WindowInterface window) {
	buttonEditCost = new JButton(new EditCostAction(window));
	buttonEditCost.setName("ButtonEditCost");
	buttonEditCost.setEnabled(false);
	components.add(buttonEditCost);
	return buttonEditCost;
    }

    /**
     * Enabler/disabler knapper
     * 
     * @param enable
     */
    public void setComponentEnablement(boolean enable) {
	enabled = enable;
	for (Component component : components) {
	    component.setEnabled(enable);
	}
	updateActionEnablement();
    }

    /**
     * Åpner vindu for å editere kostnad
     * 
     * @param window
     * @param selectedOrderCost
     */
    public void openCostView(WindowInterface window, OrderCost selectedOrderCost) {
	OrderCostViewHandler orderCostViewHandler = new OrderCostViewHandler(login, managerRepository);
	OrderCostView orderCostView = new OrderCostView(orderCostViewHandler, selectedOrderCost);

	WindowInterface dialog = new JDialogAdapter(Util.getDialog(window, "Kostnad", true));
	dialog.setName("OrderCostView");
	dialog.add(orderCostView.buildPanel(dialog));
	dialog.pack();
	Util.locateOnScreenCenter(dialog);
	dialog.setVisible(true);

	OrderCost orderCost = orderCostView.getOrderCost();
	if (orderCost != null) {
	    if (selectedOrderCost == null) {
		addCost(orderCost);

	    } else {
		costSelectionList.fireSelectedContentsChanged();
	    }
	    presentationModel.setBufferedValue(ICostableModel.PROPERTY_COSTS, costsList);
	}

    }

    /**
     * Legger til kostnad
     * 
     * @param orderCost
     */
    private void addCost(OrderCost orderCost) {
	if (orderCost != null) {
	    orderCost.setOrder((Order) presentationModel.getBufferedValue(ICostableModel.PROPERTY_ORDER));
	    orderCost.setDeviation((Deviation) presentationModel.getBufferedValue(ICostableModel.PROPERTY_DEVIATION));
	    costsList.add(orderCost);
	}
    }

    /**
     * Henter valgliste med kostnader
     * 
     * @return liste
     */
    public SelectionInList getCostSelectionList() {
	return costSelectionList;
    }

    /**
     * Editere kostnad
     * 
     * @param window
     */
    void doEditCost(WindowInterface window) {
	OrderCost selectedOrderCost = (OrderCost) costSelectionList.getSelection();
	openCostView(window, selectedOrderCost);
    }

    /**
     * Henter lytter for dobbeltklikk i tabell
     * 
     * @param window
     * @return lytter
     */
    public MouseListener getCostDoubleClickHandler(WindowInterface window) {
	return new CostDoubleClickHandler(window);
    }

    /**
     * Klasse som håndterer dobbeltklikk i tabell
     * 
     * @author atle.brekka
     * 
     */
    final class CostDoubleClickHandler extends MouseAdapter {
	WindowInterface window;

	/**
	 * @param aWindow
	 */
	public CostDoubleClickHandler(WindowInterface aWindow) {
	    window = aWindow;
	}

	/**
	 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
	    if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2 && enabled)
		if (costSelectionList.getSelection() != null) {
		    doEditCost(window);
		}
	}
    }

    /**
     * Klasse som håndterer editering av kostnad
     * 
     * @author atle.brekka
     * 
     */
    private class EditCostAction extends AbstractAction {
	private static final long serialVersionUID = 1L;

	private WindowInterface window;

	/**
	 * @param aWindow
	 */
	public EditCostAction(WindowInterface aWindow) {
	    super("Editer...");
	    window = aWindow;

	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent arg0) {
	    if (enabled) {
		doEditCost(window);
	    }

	}
    }

    /**
     * Taellmodell for kostnader
     * 
     * @author atle.brekka
     * 
     */
    private static final class CostTableModel extends AbstractTableAdapter {

	private static final long serialVersionUID = 1L;

	private static final String[] COLUMNS = { "Kostnad", "Betales av", "Beløp", "Mva", "Av." };

	private boolean editable;
	private PresentationModel presentationModel;

	/**
	 * @param listModel
	 * @param isEditable
	 * @param aPresentationModel
	 */
	CostTableModel(ListModel listModel, boolean isEditable, PresentationModel aPresentationModel) {
	    super(listModel, COLUMNS);
	    presentationModel = aPresentationModel;
	    editable = isEditable;
	}

	/**
	 * Henter verdi
	 * 
	 * @param rowIndex
	 * @param columnIndex
	 * @return verdi
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {
	    OrderCost orderCost = (OrderCost) getRow(rowIndex);
	    switch (columnIndex) {
	    case 0:
		return orderCost.getCostType();
	    case 1:
		return orderCost.getCostUnit();
	    case 2:
		return orderCost.getCostAmount();
	    case 3:
		return new YesNoInteger(orderCost.getInclVat());// orderCost.isInclVat();
	    case 4:
		if (orderCost.getDeviation() != null) {
		    return true;
		}
		return false;

	    default:
		throw new IllegalStateException("Unknown column");
	    }

	}

	/**
	 * Henter kolonneklasse
	 * 
	 * @param columnIndex
	 * @return kolonneklasse
	 */
	@Override
	public Class<?> getColumnClass(int columnIndex) {
	    switch (columnIndex) {
	    case 0:
		return CostType.class;
	    case 1:
		return CostUnit.class;
	    case 2:
		return BigDecimal.class;
	    case 3:
		return YesNoInteger.class;
	    case 4:
		return Boolean.class;
	    default:
		throw new IllegalStateException("Unknown column");
	    }
	}

	/**
	 * @see javax.swing.table.AbstractTableModel#isCellEditable(int, int)
	 */
	@Override
	public boolean isCellEditable(int row, int column) {
	    if (editable && column == 2) {
		return true;
	    }
	    return false;
	}

	/**
	 * @see javax.swing.table.AbstractTableModel#setValueAt(java.lang.Object,
	 *      int, int)
	 */
	@Override
	public void setValueAt(Object object, int rowIndex, int columnIndex) {
	    OrderCost orderCost = (OrderCost) getRow(rowIndex);
	    if (columnIndex == 2) {
		orderCost.setCostAmount((BigDecimal) object);
		presentationModel.setBufferedValue(DeviationModel.PROPERTY_MOD_COUNT, 1);
	    }
	}

    }

    /**
     * Klasse som håndterer oppdatering av kostnader
     * 
     * @author atle.brekka
     * 
     */
    class CostUpdate implements Updateable {
	/**
	 * @see no.ugland.utransprod.gui.Updateable#doDelete(no.ugland.utransprod.gui.WindowInterface)
	 */
	public boolean doDelete(WindowInterface window) {
	    boolean deleted = true;
	    if (enabled) {
		OrderCost selectedCost = (OrderCost) costSelectionList.getSelection();

		if (selectedCost != null) {
		    costsList.remove(selectedCost);
		    presentationModel.setBufferedValue(ICostableModel.PROPERTY_COSTS, costsList);
		}
	    } else {
		deleted = false;
	    }
	    return deleted;
	}

	/**
	 * @see no.ugland.utransprod.gui.Updateable#doNew(no.ugland.utransprod.gui.WindowInterface)
	 */
	public void doNew(WindowInterface window) {
	    if (enabled) {
		openCostView(window, null);
	    }

	}

	/**
	 * @see no.ugland.utransprod.gui.Updateable#doSave(no.ugland.utransprod.gui.WindowInterface)
	 */
	public void doSave(WindowInterface window1) {
	}

	/**
	 * @see no.ugland.utransprod.gui.Updateable#doRefresh(no.ugland.utransprod.gui.WindowInterface)
	 */
	public void doRefresh(WindowInterface window) {

	}

    }

    /**
     * Enabler/disabler knapper
     */
    protected void updateActionEnablement() {
	if (enabled) {
	    boolean hasSelection = getCostSelectionList().hasSelection();
	    buttonEditCost.setEnabled(hasSelection);
	    buttonRemoveCost.setEnabled(hasSelection);
	}
    }

    /**
     * Klasse som håndterer valg i liste
     * 
     * @author atle.brekka
     * 
     */
    protected class SelectionEmptyHandler implements PropertyChangeListener {

	/**
	 * @param evt
	 */
	public void propertyChange(PropertyChangeEvent evt) {
	    updateActionEnablement();
	}
    }

    /**
     * Initierer hendelsehåndtering
     * 
     * @param window
     */
    public void initEventHandling(WindowInterface window) {
	getCostSelectionList().addPropertyChangeListener(SelectionInList.PROPERTYNAME_SELECTION_EMPTY, new SelectionEmptyHandler());
	tableCost.addMouseListener(getCostDoubleClickHandler(window));
    }

    public void costChanged(Order order) {
	Set<OrderCost> costSet = order.getOrderCosts();
	costSelectionList.clearSelection();
	costsList.clear();
	if (costSet != null) {
	    costsList.addAll(costSet);
	}

	presentationModel.setBufferedValue(ICostableModel.PROPERTY_COSTS, costsList);

    }

}
