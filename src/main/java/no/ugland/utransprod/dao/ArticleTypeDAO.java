
// Warning: No line numbers available in class file
package no.ugland.utransprod.dao;

import java.util.List;
import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.Attribute;
import no.ugland.utransprod.service.enums.LazyLoadArticleTypeEnum;

public interface ArticleTypeDAO extends DAO<ArticleType> {
   String DAO_NAME = "articleTypeDAO";

   void refreshObject(ArticleType var1);

   ArticleType findByName(String var1);

   void lazyLoad(ArticleType var1, LazyLoadArticleTypeEnum[] var2);

   List<Attribute> findAllConstructionTypeAttributes();

   ArticleType findByProdCatNoAndProdCatNo2(Integer var1, Integer var2);
}
