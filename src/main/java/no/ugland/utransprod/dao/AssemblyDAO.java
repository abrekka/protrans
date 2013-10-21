package no.ugland.utransprod.dao;

import java.util.List;
import java.util.Set;

import no.ugland.utransprod.model.Assembly;
import no.ugland.utransprod.model.Supplier;

/**
 * Interface for DAO mot tabell ASSEMBLY
 * @author atle.brekka
 */
public interface AssemblyDAO extends DAO<Assembly> {
    /**
     * Sletter
     * @param assemblies
     */
    void deleteAssemblies(Set<Assembly> assemblies);

    /**
     * Oppdaterer objekt
     * @param assembly
     */
    void refreshObject(Assembly assembly);

    /**
     * Finner montering basert på team,år og uke
     * @param supplier
     * @param year
     * @param week
     * @return monteringer
     */
    List<Assembly> findBySupplierYearWeek(Supplier supplier, Integer year,
            Integer week);
}
