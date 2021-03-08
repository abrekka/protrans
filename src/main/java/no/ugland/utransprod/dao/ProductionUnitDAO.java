
// Warning: No line numbers available in class file
package no.ugland.utransprod.dao;

import java.util.List;
import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.ProductionUnit;

public interface ProductionUnitDAO extends DAO<ProductionUnit> {
   List<ProductionUnit> findByArticleTypeProductAreaGroup(ArticleType var1, String var2);

   ProductionUnit findByName(String var1);
}
