package no.ugland.utransprod;

import java.util.List;

import no.ugland.utransprod.gui.checker.StatusCheckerInterface;
import no.ugland.utransprod.gui.model.Transportable;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.service.ApplicationParamManager;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Util;

/**
 * @author atle.brekka
 *
 */
public class SetOrderStatus {
	/**
	 * 
	 */
	public static void setStatus(){

			OrderManager orderManager =(OrderManager)ModelUtil.getBean("orderManager");
			ApplicationParamManager applicationParamManager =(ApplicationParamManager)ModelUtil.getBean("applicationParamManager");
			List<Order> orders = orderManager.getAllNewOrders();
			String status;
			
			String steinArticleName = applicationParamManager.findByName("stein_artikkel");
			StatusCheckerInterface<Transportable> steinChecker = Util.getSteinChecker();
			for(Order order:orders){
				if(order.getStatus()==null){
					orderManager.lazyLoadTree(order);
					status = steinChecker.getArticleStatus(order);
					order.setStatus(steinArticleName+";"+status);
					try {
						orderManager.saveOrder(order);
					} catch (ProTransException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SetOrderStatus.setStatus();
		System.exit(0);

	}

}
