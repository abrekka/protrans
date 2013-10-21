package no.ugland.utransprod.dao.hibernate;

import java.util.List;

import no.ugland.utransprod.dao.CustomerDAO;
import no.ugland.utransprod.model.Customer;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * Implementasjon av DAO for CUSTOMER for hobernate.
 * @author atle.brekka
 */
public class CustomerDAOHibernate extends BaseDAOHibernate<Customer> implements
        CustomerDAO {
    public CustomerDAOHibernate() {
        super(Customer.class);
    }

    /**
     * @see no.ugland.utransprod.dao.CustomerDAO#removeAll()
     */
    @Override
    public final void removeAll() {
        getHibernateTemplate().bulkUpdate("delete from Customer");

    }

    /**
     * @see no.ugland.utransprod.dao.CustomerDAO#findAll()
     */
    @SuppressWarnings("unchecked")
    public final List<Customer> findAll() {
        return getHibernateTemplate().find("from Customer");
    }

    /**
     * @see no.ugland.utransprod.dao.CustomerDAO#refreshObject(no.ugland.utransprod.model.Customer)
     */
    public final void refreshObject(final Customer customer) {
        getHibernateTemplate().load(customer, customer.getCustomerId());

    }

    /**
     * @see no.ugland.utransprod.dao.CustomerDAO#lazyLoad(no.ugland.utransprod.model.Customer,
     *      no.ugland.utransprod.service.enums.LazyLoadCustomerEnum[])
     */
    /*public final void lazyLoad(final Customer customer,
            final LazyLoadCustomerEnum[] enums) {
        if (customer != null && customer.getCustomerId() != null) {
            getHibernateTemplate().execute(new HibernateCallback() {

                public Object doInHibernate(final Session session) {
                    session.load(customer, customer.getCustomerId());
                    Set<?> set;

                    for (LazyLoadCustomerEnum lazyEnum : enums) {
                        switch (lazyEnum) {
                        case ORDER:
                            set = customer.getOrders();
                            set.iterator();
                            break;
                        default:
                        }
                    }
                    return null;
                }

            });
        }

    }*/

    /**
     * @see no.ugland.utransprod.dao.CustomerDAO#findByCustomerNr(java.lang.Integer)
     */
    public final Customer findByCustomerNr(final Integer customerNr) {
        return (Customer) getHibernateTemplate().execute(
                new HibernateCallback() {

                    @SuppressWarnings("unchecked")
                    public Object doInHibernate(final Session session) {
                        List<Customer> list = session.createCriteria(
                                Customer.class).add(
                                Restrictions.eq("customerNr", customerNr))
                                .list();
                        if (list != null && list.size() == 1) {
                            return list.get(0);
                        }
                        return null;
                    }

                });
    }

}
