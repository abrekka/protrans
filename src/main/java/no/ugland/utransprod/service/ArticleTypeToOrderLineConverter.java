package no.ugland.utransprod.service;

import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.Ord;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.Ordln;

public interface ArticleTypeToOrderLineConverter {
    OrderLine convert(ArticleType articleType,Ordln ordln,Order order,Ord ord);
    public static final ArticleTypeToOrderLineConverter UNKNOWN = new ArticleTypeToOrderLineConverter() {

        public OrderLine convert(ArticleType articleType,Ordln ordln,Order order,Ord ord) {
            return OrderLine.UNKNOWN;
        }};
}
