package no.ugland.utransprod.dao;

import java.util.List;

import no.ugland.utransprod.model.ProductionTime;
import no.ugland.utransprod.model.ProductionUnit;

public interface ProductionTimeDAO extends DAO<ProductionTime> {
	List<ProductionTime> findByOrderNrAndProductionname(String orderNr, String productionname);

	void deleteAllForUser(String orderNr, String productionname, String userName);

	List<ProductionTime> findAll();
}
