package no.ugland.utransprod.dao;

import java.util.List;

import no.ugland.utransprod.model.Employee;
import no.ugland.utransprod.model.Supplier;

/**
 * Interface for DAO mot tabell EMPLOYEE
 * @author atle.brekka
 *
 */
public interface EmployeeDAO extends DAO<Employee>{
	/**
	 * Oppdaterer ansatt
	 * @param employee
	 */
	void refreshObject(Employee employee);
	/**
	 * Finner ansatte for gitt leverandør
	 * @param supplier
	 * @return ansatte
	 */
	List<Employee> findBySupplier(Supplier supplier);
}
