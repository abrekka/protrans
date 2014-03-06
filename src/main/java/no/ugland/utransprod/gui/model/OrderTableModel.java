package no.ugland.utransprod.gui.model;

import java.util.Map;

import javax.swing.ListModel;

import no.ugland.utransprod.gui.OrderPanelTypeEnum;
import no.ugland.utransprod.gui.checker.StatusCheckerInterface;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.PostShipment;
import no.ugland.utransprod.util.Util;

import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.list.ArrayListModel;

/**
 * Tabellmodell for ordre
 * 
 * @author atle.brekka
 */
public final class OrderTableModel extends AbstractTableAdapter {
    /**
     * 
     */
    private final ArrayListModel list;

    /**
     * 
     */
    private OrderPanelTypeEnum orderPanelType;

    /**
     * 
     */
    private StatusCheckerInterface<Transportable> steinChecker;

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * @param listModel
     * @param orderPanelTypeEnum
     */
    public OrderTableModel(ListModel listModel, OrderPanelTypeEnum orderPanelTypeEnum) {
	this(listModel, new ArrayListModel(), orderPanelTypeEnum, null);
    }

    /**
     * @param listModel
     * @param aOrderList
     * @param orderPanelTypeEnum
     * @param aSteinChecker
     */
    public OrderTableModel(ListModel listModel, ArrayListModel aOrderList, OrderPanelTypeEnum orderPanelTypeEnum,
	    StatusCheckerInterface<Transportable> aSteinChecker) {
	super(listModel);
	list = aOrderList;
	orderPanelType = orderPanelTypeEnum;
	steinChecker = aSteinChecker;
    }

    /**
     * @see javax.swing.table.TableModel#getValueAt(int, int)
     */
    public Object getValueAt(int rowIndex, int columnIndex) {
	Transportable transportable = (Transportable) getRow(rowIndex);
	Map<String, String> statusMap = Util.createStatusMap(transportable.getStatus());
	return orderPanelType.getValueAt(transportable, columnIndex, steinChecker, statusMap);

    }

    private Object getValueAtConfirmReport(int rowIndex, int columnIndex) {
	Order order = (Order) getRow(rowIndex);

	switch (columnIndex) {
	case 0:
	    return order.getCustomer();
	case 1:
	    return order.getOrderNr();
	case 2:
	    return order.getDeliveryAddress();
	case 3:
	    return order.getPostalCode();
	case 4:
	    return order.getPostOffice();

	case 5:
	    return order.getConstructionType();
	case 6:
	    return order.getTransport();
	case 7:
	    return order.getCost("Egenproduksjon", "Kunde");
	case 8:
	    return order.getCost("Frakt", "Kunde");
	case 9:
	    return order.getCost("Montering", "Kunde");
	case 10:
	    return order.getCost("Egenproduksjon", "Intern");
	case 11:
	    return order.getContributionRate();
	default:
	    throw new IllegalStateException("Unknown column");
	}

    }

    /**
     * Henter verdi
     * 
     * @param rowIndex
     * @param columnIndex
     * @return verdi
     */
    public Object getValueAtOrder(int rowIndex, int columnIndex) {
	Transportable transportable = (Transportable) getRow(rowIndex);

	switch (columnIndex) {
	case 0:
	    return transportable.getCustomer();
	case 1:
	    String orderNr = transportable.getOrder().getOrderNr();
	    if (transportable instanceof PostShipment) {
		orderNr += " - etterlevering";
	    }
	    return orderNr;
	case 2:
	    return transportable.getOrder().getDeliveryAddress();
	case 3:
	    return transportable.getOrder().getPostalCode();
	case 4:
	    return transportable.getOrder().getPostOffice();

	case 5:
	    return transportable.getConstructionType();
	case 6:
	    return transportable.getTransport();
	    // case 7:return order.getComment();
	case 7:
	    if (transportable.getOrder().getProductArea() != null) {
		return transportable.getOrder().getProductArea().getProductAreaGroup();
	    }
	    return null;
	default:
	    throw new IllegalStateException("Unknown column");
	}

    }

    /**
     * Henter verdi for modell brukt ved montering
     * 
     * @param rowIndex
     * @param columnIndex
     * @return verdi
     */
    public Object getValueAtAssembly(int rowIndex, int columnIndex) {
	Order order = (Order) getRow(rowIndex);
	switch (columnIndex) {
	case 0:
	    return order.getConstructionType();
	case 1:
	    return order.getPostalCode();
	case 2:
	    return order.getPostOffice();
	case 3:
	    return order;
	case 4:
	    return order.getOrderNr();
	case 5:
	    return order.getDeliveryAddress();
	case 6:
	    return order.getTransport();
	    // case 7:return order.getComment();
	default:
	    throw new IllegalStateException("Unknown column");
	}

    }

    /**
     * Henter verdi dersom modell er brukt ved transport
     * 
     * @param rowIndex
     * @param columnIndex
     * @return verdi
     */
    public Object getValueAtTransport(int rowIndex, int columnIndex) {
	Order order = (Order) getRow(rowIndex);

	Map<String, String> statusMap = Util.createStatusMap(order.getStatus());
	switch (columnIndex) {
	case 0:
	    return order.getDeliveryWeek();
	case 1:
	    return order.getConstructionType();
	case 2:
	    if (order.doAssembly()) {
		if (order.getAssembly() != null) {
		    return "M" + order.getAssembly().getAssemblyWeek();
		}
		return "M";
	    }
	    return null;
	case 3:
	    return order.getPostalCode();
	case 4:
	    return order.getPostOffice();
	case 5:
	    return statusMap.get(steinChecker.getArticleName());
	case 6:
	    return order;
	case 7:
	    return order.getOrderNr();
	case 8:
	    return order.getDeliveryAddress();
	case 9:
	    return order.getProductAreaGroup().getProductAreaGroupName();

	    // case 8:return order.getComment();
	default:
	    throw new IllegalStateException("Unknown column");
	}

    }

    /**
     * Henter ordre
     * 
     * @param rowIndex
     * @return ordre
     */
    public Order getOrder(int rowIndex) {
	return (Order) getRow(rowIndex);
    }

    /**
     * Setter inn ordre
     * 
     * @param index
     * @param order
     */
    public void insertRow(int index, Order order) {
	list.add(index, order);
	fireTableDataChanged();
    }

    /**
     * fjerner ordre
     * 
     * @param index
     */
    public void removeRow(int index) {
	if (index < list.size()) {
	    list.remove(index);
	    fireTableDataChanged();
	}
    }

    /**
     * @see com.jgoodies.binding.adapter.AbstractTableAdapter#getColumnName(int)
     */
    @Override
    public String getColumnName(int column) {
	return orderPanelType.getColumnName(column);
	/*
	 * switch (orderPanelType) { case NEW_ORDERS: return
	 * COLUMNS_NEW_ORDERS[column]; case ASSEMBLY_ORDERS: return
	 * COLUMNS_ASSEMBLY_ORDERS[column]; case CONFIRM_REPORT: return
	 * COLUMNS_CONFIRM_REPORT[column]; default: return COLUMNS[column]; }
	 */
    }

    /**
     * @see com.jgoodies.binding.adapter.AbstractTableAdapter#getColumnCount()
     */
    @Override
    public int getColumnCount() {
	return orderPanelType.getColumCount();
	/*
	 * switch (orderPanelType) { case NEW_ORDERS: return
	 * COLUMNS_NEW_ORDERS.length; case ASSEMBLY_ORDERS: return
	 * COLUMNS_ASSEMBLY_ORDERS.length; case CONFIRM_REPORT: return
	 * COLUMNS_CONFIRM_REPORT.length; default: return COLUMNS.length; }
	 */
    }

}