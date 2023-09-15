package no.ugland.utransprod.service;

import java.util.List;

import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.model.ProductionTime;

public interface ProductionTimeManager {
	String MANAGER_NAME = "productionTimeManager";
	
	List<ProductionTime> findByOrderNrAndProductionname(String orderNr,String productionname);
	
	void saveProductionTime(ProductionTime productionTime);

	void deleteAllForUser(String orderNr, String string, String userName);

	List<ProductionTime> findAll();
}
