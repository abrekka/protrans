
// Warning: No line numbers available in class file
package no.ugland.utransprod.service;

import no.ugland.utransprod.model.ConstructionTypeArticle;

public interface ConstructionTypeArticleManager extends Manager<ConstructionTypeArticle> {
   String MANAGER_NAME = "constructionTypeArticleManager";

   void saveConstructionTypeArticle(ConstructionTypeArticle var1);
}
