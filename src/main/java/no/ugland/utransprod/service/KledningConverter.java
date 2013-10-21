package no.ugland.utransprod.service;

import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.Ord;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.Ordln;

import com.google.common.collect.Lists;
import com.google.inject.Inject;

public class KledningConverter extends DefaultConverter {

	@Inject
	public KledningConverter(ManagerRepository managerRepository) {
		super(managerRepository);
	}

	public OrderLine convert(ArticleType articleType, Ordln ordln, Order order,
			Ord ord) {
		OrderLine kledning = articleType == ArticleType.UNKNOWN ? OrderLine.UNKNOWN
				: getOrderLine(articleType, ordln, order);
		return ordln != null ? setBelongsTo(kledning, order, ordln) : kledning;
	}

	private OrderLine setBelongsTo(OrderLine kledning, Order order, Ordln ordln) {
		BelongTo belongTo = BelongTo
				.valueOf("BELONG_TO_"
						+ (ordln.getFree4() != null ? ordln.getFree4()
								.intValue() : "0"));
		return belongTo.setBelongTo(kledning, order, managerRepository, ordln);
	}

	private enum BelongTo {
		BELONG_TO_1("Vegg") {
			@Override
			public OrderLine setBelongTo(OrderLine kledning, Order order,
					ManagerRepository managerRepository, Ordln ordln) {
				// Hører til vegg
				return setOrderLineRef(kledning, order, getMainArticleName(),
						managerRepository, ordln);
			}
		},
		BELONG_TO_2("Gavl") {
			@Override
			public OrderLine setBelongTo(OrderLine kledning, Order order,
					ManagerRepository managerRepository, Ordln ordln) {
				// Hører til gavl
				return setOrderLineRef(kledning, order, getMainArticleName(),
						managerRepository, ordln);
			}
		},
		BELONG_TO_0("") {
			@Override
			public OrderLine setBelongTo(OrderLine kledning, Order order,
					ManagerRepository managerRepository, Ordln ordnl) {
				// Toppartikkel
				return kledning;
			}
		};

		private String mainArticleName;

		private BelongTo(String aMainArticleName) {
			mainArticleName = aMainArticleName;
		}

		public String getMainArticleName() {
			return mainArticleName;
		}

		public abstract OrderLine setBelongTo(OrderLine kledning, Order order,
				ManagerRepository managerRepository, Ordln ordln);

		private static OrderLine setOrderLineRef(OrderLine kledning,
				Order order, String topArticleName,
				ManagerRepository managerRepository, Ordln ordln) {
			OrderLine topArticle = order.getOrderLine(topArticleName);
			if (topArticle != OrderLine.UNKNOWN) {
				topArticle.setOrdNo(ordln.getOrdno());
				managerRepository.getConstructionTypeManager()
						.updateOrderLinesFromVisma(topArticle.getOrderLines(),
								Lists.newArrayList(kledning));

			}
			return OrderLine.UNKNOWN;
		}

	}

}
