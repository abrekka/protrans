package no.ugland.utransprod;

import java.util.List;

import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.service.enums.LazyLoadOrderEnum;
import no.ugland.utransprod.util.ModelUtil;

/**
 * Hjelpeklasse som setter info på ordre
 * @author atle.brekka
 *
 */
public class SetOrderInfo {
	/**
	 * Setter ordreinfo
	 */
	public static void setInfo() {
		OrderManager orderManager = (OrderManager) ModelUtil
				.getBean("orderManager");
		
		List<Order> orders = orderManager.findAll();
		
		for(Order order:orders){
			orderManager.lazyLoadOrder(order, new LazyLoadOrderEnum[]{LazyLoadOrderEnum.ORDER_LINES,LazyLoadOrderEnum.ORDER_LINE_ORDER_LINES,LazyLoadOrderEnum.ORDER_COSTS});
			order.setInfo(order.orderLinesToString());
			
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
		SetOrderInfo.setInfo();
		System.exit(0);

	}

}
