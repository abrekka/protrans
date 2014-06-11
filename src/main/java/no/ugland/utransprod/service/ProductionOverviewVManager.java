package no.ugland.utransprod.service;

import java.util.List;

import no.ugland.utransprod.dao.ProductionOverviewVDAO;
import no.ugland.utransprod.model.ProductionOverviewV;

public class ProductionOverviewVManager {
    public static final String MANAGER_NAME = "productionOverviewVManager";
    private ProductionOverviewVDAO productionOverviewVDAO;

    public List<ProductionOverviewV> findAll() {
	return productionOverviewVDAO.findAll();
    }

    public void setProductionOverviewVDAO(ProductionOverviewVDAO productionOverviewVDao) {
	this.productionOverviewVDAO = productionOverviewVDao;
    }
    //
    // public ProductionOverviewVDAO getProductionOverviewVDAOHibernate() {
    // return productionOverviewVDAO;
    // }

}
