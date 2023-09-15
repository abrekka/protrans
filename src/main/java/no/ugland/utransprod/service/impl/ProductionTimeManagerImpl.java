package no.ugland.utransprod.service.impl;

import java.util.Date;
import java.util.List;

import no.ugland.utransprod.dao.ProductAreaDAO;
import no.ugland.utransprod.dao.ProductionTimeDAO;
import no.ugland.utransprod.model.ProductionTime;
import no.ugland.utransprod.service.ProductionTimeManager;

public class ProductionTimeManagerImpl implements ProductionTimeManager {
	private ProductionTimeDAO productionTimeDAO;

	public List<ProductionTime> findByOrderNrAndProductionname(String orderNr, String productionname) {
		return productionTimeDAO.findByOrderNrAndProductionname(orderNr, productionname);
	}

	public ProductionTimeDAO getProductionTimeDAO() {
		return productionTimeDAO;
	}

	public void setProductionTimeDAO(ProductionTimeDAO productionTimeDAO) {
		this.productionTimeDAO = productionTimeDAO;
	}

	public void saveProductionTime(ProductionTime productionTime) {
		productionTime.setUpdated(new Date());
		productionTimeDAO.saveObject(productionTime);
		
	}

	public void deleteAllForUser(String orderNr, String productionname, String userName) {
		productionTimeDAO.deleteAllForUser(orderNr, productionname, userName);
		
	}

	public List<ProductionTime> findAll() {
		return productionTimeDAO.findAll();
	}

}
