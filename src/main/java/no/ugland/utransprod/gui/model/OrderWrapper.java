package no.ugland.utransprod.gui.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import no.ugland.utransprod.model.OrderLine;

import com.jgoodies.binding.list.ArrayListModel;

/**
 * Wrapperklasse for ordre i forbindelse ved bruk av tabelltre
 * @author atle.brekka
 * @param <T>
 * @param <E>
 */
public class OrderWrapper<T, E> {
    /**
     * 
     */
    private List<OrderLine> orderLines;

    /**
     * @param costableModel
     */
    public OrderWrapper(ICostableModel<T, E> costableModel) {
        init(costableModel);
    }

    /**
     * Initierer klasse
     * @param costableModel
     */
    @SuppressWarnings("unchecked")
    private void init(ICostableModel<T, E> costableModel) {
        ArrayListModel lines = costableModel.getOrderLineArrayListModel();
        if (lines != null) {
            orderLines = new ArrayList<OrderLine>(lines);
        } else {
            orderLines = new ArrayList<OrderLine>();
        }
    }

    /**
     * @return ordrelinjer
     */
    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    /**
     * Fjerner ordrelinje
     * @param orderLine
     */
    public void removeOrderLine(OrderLine orderLine) {
        Set<OrderLine> refOrders = orderLine.getOrderLines();
        for (OrderLine line : refOrders) {
            removeOrderLine(line);
        }
        orderLines.remove(orderLine);
    }

    /**
     * Setter ordrelinjer
     * @param orders
     */
    public void setOrderLines(List<OrderLine> orders) {
        this.orderLines = orders;
    }
}
