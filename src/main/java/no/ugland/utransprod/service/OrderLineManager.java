
// Warning: No line numbers available in class file
package no.ugland.utransprod.service;

import java.util.List;
import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.dao.hibernate.QuerySettings;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.PackableListItem;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.service.enums.LazyLoadOrderEnum;
import no.ugland.utransprod.service.enums.LazyLoadOrderLineEnum;
import no.ugland.utransprod.util.excel.ExcelManager;

public interface OrderLineManager extends IApplyListManager<PackableListItem>, ExcelManager {
   String MANAGER_NAME = "orderLineManager";

   List<OrderLine> findUnproducedByArticle(String var1);

   void saveOrderLine(OrderLine var1);

   void lazyLoadOrder(Order var1, LazyLoadOrderEnum[] var2);

   void lazyLoad(OrderLine var1, LazyLoadOrderLineEnum[] var2);

   Integer countByDate(QuerySettings var1);

   List<Order> findByConstructionTypeArticleAttributes(List<OrderLine> var1, QuerySettings var2);

   List<OrderLine> findUnpackageByArticle(String var1);

   void saveOrder(Order var1) throws ProTransException;

   void refreshOrder(Order var1);

   void lazyLoadTree(OrderLine var1);

   List<OrderLine> findUnproducedByOrderNrAndArticleName(String var1, String var2);

   List<OrderLine> findUnpackedByOrderNrAndArticleName(String var1, String var2);

   OrderLine findByOrderLineId(Integer var1);

   List<OrderLine> findUnproducedByCustomerNrAndArticleName(Integer var1, String var2);

   List<OrderLine> findUnpackedByCustomerNrAndArticleName(Integer var1, String var2);

   List<OrderLine> findByArticleAndAttribute(String var1, String var2, String var3);

   List<OrderLine> findByOrderNrArticleNameAndAttribute(String var1, String var2, String var3, String var4);

   List<OrderLine> findByCustomerNrArticleNameAndAttribute(Integer var1, String var2, String var3, String var4);

   List<OrderLine> findAllConstructionTypeNotSent(ProductArea var1);

   void fjernColli(Integer var1);
}
