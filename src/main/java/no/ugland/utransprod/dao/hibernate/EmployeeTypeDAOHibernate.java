package no.ugland.utransprod.dao.hibernate;

import no.ugland.utransprod.dao.EmployeeTypeDAO;
import no.ugland.utransprod.model.EmployeeType;

/**
 * Implementasjon av DAO mot tabell EMPLOYEE_TYPE
 * 
 * @author atle.brekka
 * 
 */
public class EmployeeTypeDAOHibernate extends BaseDAOHibernate<EmployeeType>
		implements EmployeeTypeDAO {
	/**
	 * 
	 */
	public EmployeeTypeDAOHibernate() {
		super(EmployeeType.class);
	}

	/**
	 * @see no.ugland.utransprod.dao.EmployeeTypeDAO#refreshObject(no.ugland.utransprod.model.EmployeeType)
	 */
	public void refreshObject(EmployeeType employeeType) {
		getHibernateTemplate().load(employeeType,
				employeeType.getEmployeeTypeId());

	}

}
