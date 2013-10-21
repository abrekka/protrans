package no.ugland.utransprod.service;

import java.util.List;

import no.ugland.utransprod.model.EmployeeType;

/**
 * Interface for serviceklasse mmot tabell EMPLOYEE_TYPE
 * @author atle.brekka
 */
public interface EmployeeTypeManager extends OverviewManager<EmployeeType> {
    String MANAGER_NAME = "employeeTypeManager";

	/**
     * @see no.ugland.utransprod.service.OverviewManager#findAll()
     */
    List<EmployeeType> findAll();
}
