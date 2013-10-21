package no.ugland.utransprod;

import java.util.List;
import java.util.Set;

import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.util.ModelUtil;

/**
 * @author atle.brekka
 *
 */
public class SetOrderLineAttributeInfo {
	/**
	 * 
	 */
	public static void setOrderLineAttributeInfo() {
		OrderManager orderManager = (OrderManager) ModelUtil
				.getBean("orderManager");
		List<Order> orders = orderManager.findAllNotSent();

		Set<OrderLine> orderLines;
		
		for (Order order : orders) {
			orderManager.lazyLoadTree(order);//LoadOrder(order,new LazyLoadOrderEnum[] { LazyLoadOrderEnum.ORDER_LINES });
			orderLines = order.getOrderLines();
			for (OrderLine orderLine : orderLines) {
				//if(orderLine.getAttributeInfo()==null){
				//orderLineManager.lazyLoad(orderLine,new LazyLoadOrderLineEnum[] { LazyLoadOrderLineEnum.ORDER_LINES,LazyLoadOrderLineEnum.ORDER_LINE_ATTRIBUTE });
					//orderLineManager.lazyLoadTree(orderLine);
				if(orderLine.getArticlePath().equalsIgnoreCase("Takstoler")){
				orderLine.setAttributeInfo(orderLine.getAttributesAsString());
				}
				//}
			}
			try {
				orderManager.saveOrder(order);
			} catch (ProTransException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SetOrderLineAttributeInfo.setOrderLineAttributeInfo();
		System.exit(0);

	}

}
