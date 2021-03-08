
// Warning: No line numbers available in class file
package no.ugland.utransprod.service;

import java.util.List;
import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.ProductionUnit;

public interface ProductionUnitManager extends OverviewManager<ProductionUnit> {
   String MANAGER_NAME = "productionUnitManager";

   List<ProductionUnit> findByArticleTypeProductAreaGroup(ArticleType var1, String var2);

   ProductionUnit findByName(String var1);
}
