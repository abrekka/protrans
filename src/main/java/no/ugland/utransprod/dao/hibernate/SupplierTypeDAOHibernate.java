package no.ugland.utransprod.dao.hibernate;

import no.ugland.utransprod.dao.SupplierTypeDAO;
import no.ugland.utransprod.model.SupplierType;

/**
 * Implementasjon av DAO mot tabell SUPPLIER_TYPE
 * 
 * @author atle.brekka
 * 
 */
public class SupplierTypeDAOHibernate extends BaseDAOHibernate<SupplierType>
		implements SupplierTypeDAO {
	/**
	 * 
	 */
	public SupplierTypeDAOHibernate() {
		super(SupplierType.class);
	}

	/**
	 * @see no.ugland.utransprod.dao.SupplierTypeDAO#refreshObject(no.ugland.utransprod.model.SupplierType)
	 */
	public void refreshObject(SupplierType supplierType) {
		getHibernateTemplate().load(supplierType,
				supplierType.getSupplierTypeId());

	}

}
