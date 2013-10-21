package no.ugland.utransprod.dao;

import no.ugland.utransprod.model.SupplierType;

/**
 * Interface for DAO mot tabell SUPPLIER_TYPE
 * @author atle.brekka
 *
 */
public interface SupplierTypeDAO extends DAO<SupplierType>{
	/**
	 * Oppdaterer
	 * @param supplierType
	 */
	void refreshObject(SupplierType supplierType);
}
