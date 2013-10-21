package no.ugland.utransprod;

import java.util.List;
import java.util.Set;

import no.ugland.utransprod.model.ConstructionType;
import no.ugland.utransprod.model.ConstructionTypeArticle;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.service.ConstructionTypeArticleManager;
import no.ugland.utransprod.service.ConstructionTypeManager;
import no.ugland.utransprod.service.OrderLineManager;
import no.ugland.utransprod.service.enums.LazyLoadConstructionTypeArticleEnum;
import no.ugland.utransprod.service.enums.LazyLoadConstructionTypeEnum;
import no.ugland.utransprod.service.enums.LazyLoadOrderEnum;
import no.ugland.utransprod.util.ModelUtil;

/**
 * @author atle.brekka
 *
 */
public class SetGavlKledning {

	/**
	 * 
	 */
	public static void setGavlKledning() {
		ConstructionTypeManager constructionTypeManager = (ConstructionTypeManager) ModelUtil
				.getBean("constructionTypeManager");
		ConstructionTypeArticleManager constructionTypeArticleManager = (ConstructionTypeArticleManager) ModelUtil
		.getBean("constructionTypeArticleManager");
		OrderLineManager orderLineManager = (OrderLineManager) ModelUtil
				.getBean("orderLineManager");
		

		List<ConstructionType> garasjer = constructionTypeManager.findAllIncludeMaster();

		Set<ConstructionTypeArticle> artikler;

		ConstructionTypeArticle gavl = null;
		ConstructionTypeArticle kledning = null;

		for (ConstructionType garasje : garasjer) {
			constructionTypeManager
					.lazyLoad(
							garasje,
							new LazyLoadConstructionTypeEnum[] { LazyLoadConstructionTypeEnum.CONSTRUCTION_TYPE_ARTICLE });
			artikler = garasje.getConstructionTypeArticles();


			for (ConstructionTypeArticle artikkel : artikler) {
				
				if (artikkel.getArticleName().equalsIgnoreCase("Gavl")) {
					gavl = artikkel;
					break;

				}
			}
			
			

			ConstructionTypeArticle gavlkledning = null;

			for (ConstructionTypeArticle artikkel : artikler) {
				Set<ConstructionTypeArticle> gavlkledningArtikler;
				if (artikkel.getArticleName().equalsIgnoreCase("Gavlkledning")) {
					gavlkledning = artikkel;
					constructionTypeManager
							.lazyLoadArticle(
									artikkel,
									new LazyLoadConstructionTypeArticleEnum[] { LazyLoadConstructionTypeArticleEnum.CONSTRUCTION_TYPE_ARTICLES });
					gavlkledningArtikler = artikkel
							.getConstructionTypeArticles();

					for (ConstructionTypeArticle gavlkledningArtikkel : gavlkledningArtikler) {
						if (gavlkledningArtikkel.getArticleName()
								.equalsIgnoreCase("Kledning")) {
							gavlkledningArtikkel
									.setConstructionTypeArticleRef(gavl);
							kledning=gavlkledningArtikkel;
						}

					}
					
					if(kledning!=null){
					constructionTypeArticleManager.saveConstructionTypeArticle(kledning);
					}

					constructionTypeManager
							.lazyLoadArticle(
									gavlkledning,
									new LazyLoadConstructionTypeArticleEnum[] { LazyLoadConstructionTypeArticleEnum.ORDER_LINE });
					Set<OrderLine> orderLines = gavlkledning.getOrderLines();
					Order order = null;
					Set<OrderLine> orderOrderLines;
					OrderLine gavlOrderLine = null;

					for (OrderLine orderLine : orderLines) {
						order = orderLine.getOrder();
						orderLineManager
								.lazyLoadOrder(
										order,
										new LazyLoadOrderEnum[] { LazyLoadOrderEnum.ORDER_LINES });

						orderOrderLines = order.getOrderLines();

						gavlOrderLine=null;
						for (OrderLine orderOrderLine : orderOrderLines) {
							if (orderOrderLine.getArticleName()
									.equalsIgnoreCase("Gavl")) {
								gavlOrderLine = orderOrderLine;
							}
						}

						//if(gavlOrderLine!=null){
						
						for (OrderLine orderOrderLine : orderOrderLines) {
							if (orderOrderLine.getArticlePath()
									.equalsIgnoreCase("Gavlkledning$Kledning")) {
								if(gavlOrderLine==null){
									gavlOrderLine=new OrderLine(null,orderOrderLine.getOrder(),gavl,null,null,null,null,null,null,null,"Gavl",null,1,null,null,null,null,null,null,null,null,null);
									orderLineManager.saveOrderLine(gavlOrderLine);
								}
								orderOrderLine.setOrderLineRef(gavlOrderLine);
								orderOrderLine.setArticlePath("Gavl$Kledning");
								orderLineManager.saveOrderLine(orderOrderLine);
								orderLineManager.lazyLoadTree(gavlOrderLine);
								gavlOrderLine.setAttributeInfo(gavlOrderLine.getAttributesAsString());
								orderLineManager.saveOrderLine(gavlOrderLine);
							}
						}
						//}

						// gavlkledning.removeOrderLine(orderLine);

					}

					// garasje.removeConstructionArticle(gavlkledning);
					constructionTypeManager.saveConstructionType(garasje);

				}
			}
			constructionTypeArticleManager.saveConstructionTypeArticle(gavl);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SetGavlKledning.setGavlKledning();
		System.exit(0);

	}

}
