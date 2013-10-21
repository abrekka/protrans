package no.ugland.utransprod.dao.hibernate;

import java.util.List;

import no.ugland.utransprod.dao.OrderCostDAO;
import no.ugland.utransprod.model.OrderCost;
import no.ugland.utransprod.model.Supplier;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * Implementasjon av DAO mot tabell ORDER_COST for hibernate
 * @author atle.brekka
 */
public class OrderCostDAOHibernate extends BaseDAOHibernate<OrderCost>
        implements OrderCostDAO {
    /**
     * Konstruktør
     */
    public OrderCostDAOHibernate() {
        super(OrderCost.class);
    }

    @SuppressWarnings("unchecked")
    public final List<OrderCost> findBySupplier(final Supplier supplier) {
        return (List<OrderCost>) getHibernateTemplate().execute(
                new HibernateCallback() {

                    public Object doInHibernate(final Session session) {

                        return session.createCriteria(OrderCost.class).add(
                                Restrictions.eq("supplier", supplier)).list();
                    }

                });
    }

}
