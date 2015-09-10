package no.ugland.utransprod.dao;

import java.util.List;

import no.ugland.utransprod.model.Supplier;

/**
 * Interface for DAO ot tabell SUPPLIER
 * 
 * @author atle.brekka
 * 
 */
public interface SupplierDAO extends DAO<Supplier> {
    void refreshObject(Supplier supplier);

    List<Supplier> findByTypeName(String typeString, String orderBy);

    Supplier findByName(String name);

    List<Supplier> findActiveByTypeName(String typeString, String orderBy);

    List<Supplier> findHavingAssembly(Integer year, Integer fromWeek, Integer toWeek);
}
