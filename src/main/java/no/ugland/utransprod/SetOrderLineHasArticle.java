package no.ugland.utransprod;

import java.util.Set;

import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.service.OrderLineManager;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.service.enums.LazyLoadOrderEnum;
import no.ugland.utransprod.util.ModelUtil;

/**
 * Hjelpeklasse som ble brukt til å sette om ordrelinje hadde artikkel
 * @author atle.brekka
 *
 */
public class SetOrderLineHasArticle {
	/**
	 * Setter om ordrelinje har artikkel
	 */
	public static void setOrderLinehasArticle(){
		OrderManager orderManager =(OrderManager)ModelUtil.getBean("orderManager");
		OrderLineManager orderLineManager =(OrderLineManager)ModelUtil.getBean("orderLineManager");
		Set<Order> orders=orderManager.findNotSent();
		Set<OrderLine> orderLines;
		for(Order order:orders){
			orderManager.lazyLoadOrder(order, new LazyLoadOrderEnum[]{LazyLoadOrderEnum.ORDER_LINES,LazyLoadOrderEnum.ORDER_LINE_ORDER_LINES});
			orderLines = order.getOrderLines();
			for(OrderLine orderLine: orderLines){
				//orderLineManager.lazyLoad(orderLine, new LazyLoadOrderLineEnum[]{LazyLoadOrderLineEnum.ORDER_LINE_ATTRIBUTE});
				orderLine.hasArticle();
				orderLineManager.saveOrderLine(orderLine);
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SetOrderLineHasArticle.setOrderLinehasArticle();
		System.exit(0);

	}

}
