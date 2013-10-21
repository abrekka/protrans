package no.ugland.utransprod;

import java.util.List;
import java.util.Map;

import no.ugland.utransprod.gui.checker.StatusCheckerInterface;
import no.ugland.utransprod.gui.model.Transportable;
import no.ugland.utransprod.model.FrontProductionV;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.Produceable;
import no.ugland.utransprod.model.VeggProductionV;
import no.ugland.utransprod.service.FrontProductionVManager;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.service.VeggProductionVManager;
import no.ugland.utransprod.service.enums.LazyLoadOrderEnum;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Util;

/**
 * Setter caching for kommentarer og status veggproduksjon og frontproduksjon
 * 
 * @author atle.brekka
 * 
 */
public class OrderCommentCache {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		OrderManager orderManager = (OrderManager) ModelUtil
				.getBean("orderManager");
		FrontProductionVManager frontProductionVManager = (FrontProductionVManager) ModelUtil
				.getBean("frontProductionVManager");
		VeggProductionVManager veggProductionVManager = (VeggProductionVManager) ModelUtil
				.getBean("veggProductionVManager");
		List<Order> orders = orderManager.findAllNotSent();

		for (Order order : orders) {
			orderManager.lazyLoadOrder(order, new LazyLoadOrderEnum[] {
					LazyLoadOrderEnum.COMMENTS, LazyLoadOrderEnum.COLLIES });
			order.cacheComments();
			order.cacheGarageColliHeight();
			try {
				orderManager.saveOrder(order);
			} catch (ProTransException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		List<Produceable> fronter = frontProductionVManager.findAllApplyable();
		StatusCheckerInterface<Transportable> veggChecker = Util
				.getVeggChecker();

		for (Produceable front : fronter) {

			Map<String, String> statusMap = Util
					.createStatusMap(((FrontProductionV) front)
							.getOrderStatus());

			String status = statusMap.get(veggChecker.getArticleName());
			if (status == null) {

				Order order = orderManager
						.findByOrderNr(((FrontProductionV) front).getOrderNr());
				if (order != null) {
					orderManager.lazyLoadTree(order);
					status = veggChecker.getArticleStatus(order);
					statusMap.put(veggChecker.getArticleName(), status);
					order.setStatus(Util.statusMapToString(statusMap));
					try {
						orderManager.saveOrder(order);
					} catch (ProTransException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		}

		List<Produceable> vegger = veggProductionVManager.findAllApplyable();
		StatusCheckerInterface<Transportable> frontChecker = Util
				.getFrontChecker();

		for (Produceable vegg : vegger) {

			Map<String, String> statusMap = Util
					.createStatusMap(((VeggProductionV) vegg).getOrderStatus());

			String status = statusMap.get(frontChecker.getArticleName());
			if (status == null) {

				Order order = orderManager
						.findByOrderNr(((VeggProductionV) vegg).getOrderNr());
				if (order != null) {
					orderManager.lazyLoadTree(order);
					status = frontChecker.getArticleStatus(order);
					statusMap.put(frontChecker.getArticleName(), status);
					order.setStatus(Util.statusMapToString(statusMap));
					try {
						orderManager.saveOrder(order);
					} catch (ProTransException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		}

	}
}
