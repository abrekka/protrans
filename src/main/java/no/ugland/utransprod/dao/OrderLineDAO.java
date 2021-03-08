
// Warning: No line numbers available in class file
package no.ugland.utransprod.dao;

import java.util.List;
import no.ugland.utransprod.dao.hibernate.QuerySettings;
import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.Ordln;
import no.ugland.utransprod.model.PackableListItem;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.service.enums.LazyLoadOrderLineEnum;
import no.ugland.utransprod.util.Periode;

public interface OrderLineDAO extends DAO<OrderLine> {
   List<OrderLine> findUnproducedByArticle(ArticleType var1);

   void lazyLoad(OrderLine var1, LazyLoadOrderLineEnum[] var2);

   Integer countByDate(QuerySettings var1);

   List<Order> findByConstructionTypeArticleAttributes(List<OrderLine> var1, QuerySettings var2);

   List<OrderLine> findUnpackageByArticle(ArticleType var1);

   void lazyLoadTree(OrderLine var1);

   List<OrderLine> findUnproducedByOrderNrAndArticleName(String var1, ArticleType var2);

   List<OrderLine> findUnpackedByOrderNrAndArticleName(String var1, ArticleType var2);

   List<OrderLine> findUnproducedByCustomerNrAndArticleName(Integer var1, ArticleType var2);

   List<OrderLine> findUnpackedByCustomerNrAndArticleName(Integer var1, ArticleType var2);

   List<OrderLine> findByArticleAndAttribute(ArticleType var1, String var2, String var3);

   List<OrderLine> findByOrderNrArticleNameAndAttribute(String var1, ArticleType var2, String var3, String var4);

   List<OrderLine> findByCustomerNrArticleNameAndAttribute(Integer var1, ArticleType var2, String var3, String var4);

   List<PackableListItem> findAllApplyable();

   List<PackableListItem> findApplyableByCustomerNr(Integer var1);

   List<PackableListItem> findApplyableByOrderNr(String var1);

   void refresh(PackableListItem var1);

   List<OrderLine> findAllConstructionTypeNotSent(ProductArea var1);

   Ordln findOrdlnByOrderLine(Integer var1);

   List<Order> findTakstolOwnOrderByPeriode(Periode var1);

   void fjernColli(Integer var1);
}
