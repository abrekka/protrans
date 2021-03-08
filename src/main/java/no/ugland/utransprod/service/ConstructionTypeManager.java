
// Warning: No line numbers available in class file
package no.ugland.utransprod.service;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import no.ugland.utransprod.model.ConstructionType;
import no.ugland.utransprod.model.ConstructionTypeArticle;
import no.ugland.utransprod.model.ConstructionTypeAttribute;
import no.ugland.utransprod.model.Deviation;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.service.enums.LazyLoadConstructionTypeArticleEnum;
import no.ugland.utransprod.service.enums.LazyLoadConstructionTypeAttributeEnum;
import no.ugland.utransprod.service.enums.LazyLoadConstructionTypeEnum;

public interface ConstructionTypeManager extends OverviewManager<ConstructionType> {
   String MANAGER_NAME = "constructionTypeManager";

   void saveConstructionType(ConstructionType var1);

   void removeAll();

   List<ConstructionType> findAll();

   ConstructionType findByName(String var1);

   void removeConstructionType(ConstructionType var1);

   ConstructionType findMaster(ProductArea var1);

   void lazyLoad(ConstructionType var1, LazyLoadConstructionTypeEnum[] var2);

   void lazyLoadAttribute(ConstructionTypeAttribute var1, LazyLoadConstructionTypeAttributeEnum[] var2);

   void lazyLoadArticle(ConstructionTypeArticle var1, LazyLoadConstructionTypeArticleEnum[] var2);

   void lazyLoadTree(ConstructionType var1);

   List<ConstructionType> findAllIncludeMaster();

   List<ConstructionType> findByProductArea(ProductArea var1);

   List<ConstructionType> findAllMasters();

   Set<OrderLine> getOrderLinesForNewConstructionType(Collection<OrderLine> var1, ConstructionType var2, Order var3, Deviation var4);

   Set<OrderLine> updateOrderLinesFromVisma(Set<OrderLine> var1, Collection<OrderLine> var2);
}
