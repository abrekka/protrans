package no.ugland.utransprod.service;

import java.util.List;

import no.ugland.utransprod.model.Supplier;

/**
 * Interface for serviceklasse mot tabell SUPPLIER
 * 
 * @author atle.brekka
 */
public interface SupplierManager extends OverviewManager<Supplier> {
    public static final String MANAGER_NAME = "supplierManager";

    List<Supplier> findAll();

    List<Supplier> findByTypeName(String typeString, String orderBy);

    Supplier findByName(String name);

    List<Supplier> findActiveByTypeName(String typeString, String orderBy);

    List<Supplier> findHavingAssembly(Integer year, Integer fromWeek, Integer toWeek);

    Boolean hasOrderCosts(Supplier supplier);
}
