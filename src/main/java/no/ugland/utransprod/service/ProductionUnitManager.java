package no.ugland.utransprod.service;

import java.util.List;

import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.ProductionUnit;


public interface ProductionUnitManager extends OverviewManager<ProductionUnit> {
    public final static String MANAGER_NAME="productionUnitManager";
    //void lazyLoad(ProductionUnit productionUnit,LazyLoadProductionUnitEnum[] enums);
    List<ProductionUnit> findByArticleTypeProductAreaGroup(ArticleType articleType,String productAreaGroupName);
    ProductionUnit findByName(String name);
}
