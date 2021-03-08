
// Warning: No line numbers available in class file
package no.ugland.utransprod.dao;

import java.util.List;
import no.ugland.utransprod.model.ConstructionType;
import no.ugland.utransprod.model.ConstructionTypeArticle;
import no.ugland.utransprod.model.ConstructionTypeAttribute;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.service.enums.LazyLoadConstructionTypeArticleEnum;
import no.ugland.utransprod.service.enums.LazyLoadConstructionTypeAttributeEnum;
import no.ugland.utransprod.service.enums.LazyLoadConstructionTypeEnum;

public interface ConstructionTypeDAO extends DAO<ConstructionType> {
   void removeAll();

   void refreshObject(ConstructionType var1);

   List<ConstructionType> findAllExceptMaster();

   void lazyLoad(ConstructionType var1, LazyLoadConstructionTypeEnum[] var2);

   void lazyLoadAttribute(ConstructionTypeAttribute var1, LazyLoadConstructionTypeAttributeEnum[] var2);

   void lazyLoadArticle(ConstructionTypeArticle var1, LazyLoadConstructionTypeArticleEnum[] var2);

   void lazyLoadTree(ConstructionType var1);

   List<ConstructionType> findByProductArea(ProductArea var1);

   List<ConstructionType> findAllMasters();

   List<ConstructionType> findMasterByProductArea(ProductArea var1);
}
