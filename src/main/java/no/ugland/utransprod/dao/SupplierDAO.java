package no.ugland.utransprod.dao;

import java.util.List;

import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.model.Supplier;

/**
 * Interface for DAO ot tabell SUPPLIER
 * 
 * @author atle.brekka
 * 
 */
public interface SupplierDAO extends DAO<Supplier> {
	/**
	 * Oppdaterer
	 * 
	 * @param supplier
	 */
	void refreshObject(Supplier supplier);

	/**
	 * Finner basert på typestreng
	 * 
	 * @param typeString
	 * @param orderBy
	 * @return leverandører
	 */
	List<Supplier> findByTypeName(String typeString, String orderBy);

	/**
	 * Lazy laster
	 * 
	 * @param supplier
	 * @param enums
	 */
	//void lazyLoad(Supplier supplier, LazyLoadSupplierEnum[] enums);
    Supplier findByName(String name);
    List<Supplier> findActiveByTypeName(String typeString, String orderBy,ProductAreaGroup productAreaGroup);
    List<Supplier> findHavingAssembly(Integer year, Integer fromWeek, Integer toWeek,ProductAreaGroup productAreaGroup);
}
