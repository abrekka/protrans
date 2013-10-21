package no.ugland.utransprod;

import java.util.List;
import java.util.Set;

import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.service.OrderLineManager;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.service.enums.LazyLoadOrderEnum;
import no.ugland.utransprod.service.enums.LazyLoadOrderLineEnum;
import no.ugland.utransprod.util.ModelUtil;

/**
 * @author atle.brekka
 *
 */
public class SetOrderLineDefault {

	/**
	 * 
	 */
	public static void setOrderLineDefault() {
		OrderManager orderManager = (OrderManager) ModelUtil
				.getBean("orderManager");
		OrderLineManager orderLineManager = (OrderLineManager) ModelUtil
				.getBean("orderLineManager");
		List<Order> orders = orderManager.findAll();

		Set<OrderLine> orderLines;
		
		for (Order order : orders) {
			orderManager.lazyLoadOrder(order,
					new LazyLoadOrderEnum[] { LazyLoadOrderEnum.ORDER_LINES });
			orderLines = order.getOrderLines();
			for (OrderLine orderLine : orderLines) {
				orderLineManager
						.lazyLoad(
								orderLine,
								new LazyLoadOrderLineEnum[] { LazyLoadOrderLineEnum.ORDER_LINE_ATTRIBUTE });
				orderLine.isDefault();
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
	public static void main(String[] args) {
		SetOrderLineDefault.setOrderLineDefault();
		System.exit(0);

	}

}
