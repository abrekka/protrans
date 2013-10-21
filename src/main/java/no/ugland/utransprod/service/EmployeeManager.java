package no.ugland.utransprod.service;

import java.util.List;

import no.ugland.utransprod.model.Employee;
import no.ugland.utransprod.model.Supplier;

/**
 * Interface for serviceklasse mot tabell EMPLOYEE
 * @author atle.brekka
 */
public interface EmployeeManager extends OverviewManager<Employee> {
    String MANAGER_NAME = "employeeManager";

	/**
     * @see no.ugland.utransprod.service.OverviewManager#findAll()
     */
    List<Employee> findAll();

    /**
     * Finner for gitt levernadør
     * @param supplier
     * @return ansatte
     */
    List<Employee> findBySupplier(Supplier supplier);
}
