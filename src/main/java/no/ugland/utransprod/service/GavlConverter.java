package no.ugland.utransprod.service;

import com.google.inject.Inject;

import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.Ord;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.Ordln;

public class GavlConverter extends DefaultConverter{
	private static final String TAKSTOLER = "Takstoler";
	private static final String ATTRIBUTE_VINKEL="Vinkel";
    private static final String ATTRIBUTE_BREDDE="Bredde";

	@Inject
    public GavlConverter(ManagerRepository managerRepository) {
		super(managerRepository);
	}

	public OrderLine convert(ArticleType articleType, Ordln ordln, Order order,
			Ord ord) {
		OrderLine gavl=articleType == ArticleType.UNKNOWN ? OrderLine.UNKNOWN : getOrderLine(articleType, ordln,
                order);
		return setAngleAndWidth(gavl,order);
	}

	private OrderLine setAngleAndWidth(OrderLine gavl,Order order) {
		if(gavl!=OrderLine.UNKNOWN){
			OrderLine takstol = order.getOrderLine(TAKSTOLER);
			if(takstol!=OrderLine.UNKNOWN){
				gavl.setAttributeValue(ATTRIBUTE_VINKEL, takstol.getAttributeValue(ATTRIBUTE_VINKEL));
				gavl.setAttributeValue(ATTRIBUTE_BREDDE, takstol.getAttributeValue(ATTRIBUTE_BREDDE));
			}
		}
		return gavl;
	}

}
