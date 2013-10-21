package no.ugland.utransprod.dao;

import no.ugland.utransprod.model.EmployeeType;

/**
 * Interface for DAO mot tabell EMPLOYEE_TYPE
 * @author atle.brekka
 *
 */
public interface EmployeeTypeDAO extends DAO<EmployeeType>{
	/**
	 * Oppdaterer ansattype
	 * @param employeeType
	 */
	void refreshObject(EmployeeType employeeType);
}
