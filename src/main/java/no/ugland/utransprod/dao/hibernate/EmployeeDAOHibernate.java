package no.ugland.utransprod.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import no.ugland.utransprod.dao.EmployeeDAO;
import no.ugland.utransprod.model.Employee;
import no.ugland.utransprod.model.Supplier;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * Implementasjon av DAO mot tabell EMPLOYEE
 * 
 * @author atle.brekka
 * 
 */
public class EmployeeDAOHibernate extends BaseDAOHibernate<Employee> implements
		EmployeeDAO {
	/**
	 * 
	 */
	public EmployeeDAOHibernate() {
		super(Employee.class);
	}

	/**
	 * @see no.ugland.utransprod.dao.EmployeeDAO#refreshObject(no.ugland.utransprod.model.Employee)
	 */
	public void refreshObject(Employee employee) {
		getHibernateTemplate().load(employee, employee.getEmployeeId());

	}

	/**
	 * @see no.ugland.utransprod.dao.EmployeeDAO#findBySupplier(no.ugland.utransprod.model.Supplier)
	 */
	@SuppressWarnings("unchecked")
	public List<Employee> findBySupplier(final Supplier supplier) {
		if (supplier != null) {
			return (List<Employee>) getHibernateTemplate().execute(
					new HibernateCallback() {

						public Object doInHibernate(Session session)
								throws HibernateException {
							return session.createCriteria(Employee.class).add(
									Restrictions.eq("supplier", supplier))
									.list();
						}

					});
		}
		return new ArrayList<Employee>();
	}

}
