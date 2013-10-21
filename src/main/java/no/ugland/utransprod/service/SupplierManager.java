package no.ugland.utransprod.service;

import java.util.List;

import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.model.Supplier;

/**
 * Interface for serviceklasse mot tabell SUPPLIER
 * @author atle.brekka
 */
public interface SupplierManager extends OverviewManager<Supplier> {
    public static final String MANAGER_NAME="supplierManager";
    /**
     * @see no.ugland.utransprod.service.OverviewManager#findAll()
     */
    List<Supplier> findAll();

    /**
     * Finner basert på type
     * @param typeString
     * @param orderBy
     * @return leverandører
     */
    List<Supplier> findByTypeName(String typeString, String orderBy);

    /**
     * Lazy laster leverandør
     * @param supplier
     * @param enums
     */
    //void lazyLoad(Supplier supplier, LazyLoadSupplierEnum[] enums);

    Supplier findByName(String name);
    List<Supplier> findActiveByTypeName(String typeString, String orderBy,ProductAreaGroup productAreaGroup);
    List<Supplier> findHavingAssembly(Integer year,Integer fromWeek,Integer toWeek,ProductAreaGroup productAreaGroup);
    Boolean hasOrderCosts(Supplier supplier);
}
