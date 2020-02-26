package no.ugland.utransprod.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multimap;
import com.google.inject.Inject;
import com.google.inject.name.Named;

import no.ugland.utransprod.model.Colli;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.OrderLineAttribute;
import no.ugland.utransprod.model.Ordln;
import no.ugland.utransprod.model.Prod;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.enums.LazyLoadOrderEnum;
import no.ugland.utransprod.service.enums.LazyLoadOrderLineEnum;

public class Collicreator {
	private Multimap<String, String> colliSetup;
	private ManagerRepository managerRepository;

	@Inject
	public Collicreator(@Named("colli_setup") Multimap aColliSetup, ManagerRepository aManagerRepository) {
		managerRepository = aManagerRepository;
		this.colliSetup = aColliSetup;
	}

	public void opprettKollier(Order ordre,String opprettetFra) {
		managerRepository.getOrderManager().lazyLoadOrder(ordre,
				new LazyLoadOrderEnum[] { LazyLoadOrderEnum.ORDER_LINES, LazyLoadOrderEnum.COLLIES });
		List<OrderLine> orderLines = ordre.getOrderLineList();
		Set<String> colliNames = colliSetup.keySet();
		List<Colli> collies = ordre.getColliList();

		if (colliNames != null) {
			for (String colliName : colliNames) {
				if (!inneholder(collies, colliName)) {
					OrderLine colliOrderLine = shouldHaveColli(orderLines, colliSetup.get(colliName));
					if (colliOrderLine != null) {
						Ordln ordln = colliOrderLine.getOrdln();
						if (ordln == null) {
							ordln = managerRepository.getOrdlnManager().findByOrdNoAndLnNo(colliOrderLine.getOrdNo(),
									colliOrderLine.getLnNo());
						}
						Colli newColli = new Colli(null, ordre, colliName, null, null, null, null, null, null,opprettetFra);
						if (ordln != null) {
							newColli.setProdTp(ordln.getProdTp());
							newColli.setPrCatNo(ordln.getProd().getPrCatNo());
						}
						ordre.addColli(newColli);

						if (colliName.equalsIgnoreCase("Takstein")) {
							checkTakstein(orderLines, newColli);
						}
						managerRepository.getColliManager().saveColli(newColli);
					}
				}
			}
		}

		ordre.setDefaultColliesGenerated(1);
		managerRepository.getOrderManager().saveObject(ordre);
	}

	public void checkTakstein(List<OrderLine> orderLines, Colli colli) {

		if (orderLines != null) {
			for (OrderLine orderLine : orderLines) {
				managerRepository.getOrderLineManager().lazyLoad(orderLine,
						new LazyLoadOrderLineEnum[] { LazyLoadOrderLineEnum.ORDER_LINE_ATTRIBUTE });
				if (orderLine.getArticleName().equalsIgnoreCase("Takstein")) {
					Set<OrderLineAttribute> attributes = orderLine.getOrderLineAttributes();
					if (attributes != null) {
						for (OrderLineAttribute attribute : attributes) {
							if (attribute.getAttributeName().equalsIgnoreCase("Sendes fra GG")
									&& (attribute.getAttributeValue() == null
											|| attribute.getAttributeValue().equalsIgnoreCase("Nei"))) {

								orderLine.setColli(colli);

								colli.addOrderLine(orderLine);

								colli.setPackageDate(Util.getCurrentDate());
								managerRepository.getColliManager().saveColli(colli);
								managerRepository.getOrderLineManager().saveOrderLine(orderLine);
							}
						}
					}
				}
			}
		}
	}

	private boolean inneholder(List<Colli> collies, final String kollinavn) {
		if (collies == null) {
			return false;
		}
		return Iterables.filter(collies, new Predicate<Colli>() {

			public boolean apply(Colli colli) {
				return kollinavn.equalsIgnoreCase(colli.getColliName());
			}
		}).iterator().hasNext();
	}

	private OrderLine shouldHaveColli(List<OrderLine> orderLines, Collection<String> articlenames) {
		OrderLine ordrelinjeforColli = null;
		if (orderLines != null) {
			for (OrderLine orderLine : orderLines) {
				if (orderLine.getHasArticle() == null) {
					managerRepository.getOrderLineManager().lazyLoad(orderLine,
							new LazyLoadOrderLineEnum[] { LazyLoadOrderLineEnum.ORDER_LINE_ATTRIBUTE });
				}
				if (articlenames.contains(orderLine.getArticleName()) && orderLine.hasArticle()) {
					if (orderLine.getOrdNo() != null && orderLine.getLnNo() != null) {
						return orderLine;
					} else {
						ordrelinjeforColli = orderLine;
					}
				}
			}
		}
		return ordrelinjeforColli;
	}
}
