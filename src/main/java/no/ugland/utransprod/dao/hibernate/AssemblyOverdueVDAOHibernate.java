package no.ugland.utransprod.dao.hibernate;

import java.util.List;

import no.ugland.utransprod.dao.AssemblyOverdueVDAO;
import no.ugland.utransprod.model.AssemblyOverdueV;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * Implementasjon av DAO mot view ASSEMBLY_OVERDUE_V
 * 
 * @author atle.brekka
 * 
 */
public class AssemblyOverdueVDAOHibernate extends
		BaseDAOHibernate<AssemblyOverdueV> implements AssemblyOverdueVDAO {
	/**
	 * 
	 */
	public AssemblyOverdueVDAOHibernate() {
		super(AssemblyOverdueV.class);
	}

	/**
	 * @see no.ugland.utransprod.dao.AssemblyOverdueVDAO#getOldest()
	 */
	public AssemblyOverdueV getOldest() {
		return (AssemblyOverdueV) getHibernateTemplate().execute(
				new HibernateCallback() {

					@SuppressWarnings("unchecked")
					public Object doInHibernate(Session session)
							throws HibernateException {
						List<AssemblyOverdueV> orderedList = session
								.createCriteria(AssemblyOverdueV.class)
								.addOrder(Order.asc("assemblyYear")).addOrder(
										Order.asc("assemblyWeek")).list();
						if (orderedList != null && orderedList.size() > 0) {
							return orderedList.get(0);
						}
						return null;
					}

				});
	}

}
