package no.ugland.utransprod.dao.hibernate;

import no.ugland.utransprod.dao.IncomingOrderDAO;
import no.ugland.utransprod.model.Order;

/**
 * Implementasjon av DAO mot ordre til avrop
 * 
 * @author atle.brekka
 * 
 */
public class IncomingOrderDAOHibernate extends BaseDAOHibernate<Order>
		implements IncomingOrderDAO {

	/**
	 * Konstruktør
	 */
	public IncomingOrderDAOHibernate() {
		super(Order.class);
	}

}
