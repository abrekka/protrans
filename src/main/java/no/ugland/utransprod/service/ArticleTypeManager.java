
// Warning: No line numbers available in class file
package no.ugland.utransprod.service;

import java.util.List;
import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.Attribute;
import no.ugland.utransprod.service.enums.LazyLoadArticleTypeEnum;

public interface ArticleTypeManager extends OverviewManager<ArticleType> {
   String MANAGER_NAME = "articleTypeManager";

   void saveArticleType(ArticleType var1);

   ArticleType findByName(String var1);

   void removeArticleType(ArticleType var1);

   void lazyLoad(ArticleType var1, LazyLoadArticleTypeEnum[] var2);

   List<Attribute> findAllConstructionTypeAttributes();

   List<ArticleType> findAllTopLevel();
}
