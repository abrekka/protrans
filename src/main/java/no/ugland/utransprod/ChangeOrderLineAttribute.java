package no.ugland.utransprod;

import java.util.List;
import java.util.Set;

import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.OrderLineAttribute;
import no.ugland.utransprod.service.OrderLineManager;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.service.enums.LazyLoadOrderEnum;
import no.ugland.utransprod.service.enums.LazyLoadOrderLineEnum;
import no.ugland.utransprod.util.ModelUtil;

/**
 * Hjelpeklasse for å oppdatere artikkelpath for ordrelinjer.
 * @author atle.brekka
 */
public final class ChangeOrderLineAttribute {
    private ChangeOrderLineAttribute() {

    }

    /**
     * Setter artikkeltpath.
     */
    public static void changeOrderLineAttributes() {
        OrderManager orderManager = (OrderManager) ModelUtil
                .getBean("orderManager");
        OrderLineManager orderLineManager = (OrderLineManager) ModelUtil
                .getBean("orderLineManager");
        List<Order> orders = orderManager.findAll();

        Set<OrderLine> orderLines;
        Set<OrderLineAttribute> orderLineAttributes;
        for (Order order : orders) {
            orderManager.lazyLoadOrder(order,
                    new LazyLoadOrderEnum[] {LazyLoadOrderEnum.ORDER_LINES});
            orderLines = order.getOrderLines();
            for (OrderLine orderLine : orderLines) {
                orderLineManager
                        .lazyLoad(
                                orderLine,
                                new LazyLoadOrderLineEnum[] {LazyLoadOrderLineEnum.ORDER_LINE_ATTRIBUTE});
                if (orderLine.getArticlePath() == null) {
                    String path = orderLine.getGeneratedArticlePath();
                    orderLine.setArticlePath(path);
                }

                orderLineAttributes = orderLine.getOrderLineAttributes();
                for (OrderLineAttribute attribute : orderLineAttributes) {
                    if (attribute.getOrderLineAttributeName() == null) {
                        attribute.setOrderLineAttributeName(attribute
                                .getAttributeName());
                    }
                }

            }
            try {
                orderManager.saveOrder(order);
            } catch (ProTransException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * @param args
     */
    public static void main(final String[] args) {
        ChangeOrderLineAttribute.changeOrderLineAttributes();
        System.exit(0);

    }

}
