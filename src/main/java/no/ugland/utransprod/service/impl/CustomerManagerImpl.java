package no.ugland.utransprod.service.impl;

import java.io.Serializable;
import java.util.List;

import no.ugland.utransprod.dao.CustomerDAO;
import no.ugland.utransprod.model.Customer;
import no.ugland.utransprod.service.CustomerManager;

/**
 * Implementasjon av manager for kunder.
 * @author atle.brekka
 */
public class CustomerManagerImpl extends ManagerImpl<Customer>implements CustomerManager {
    /**
     * @see no.ugland.utransprod.service.CustomerManager#saveCustomer(no.ugland.utransprod.model.Customer)
     */
    public final void saveCustomer(final Customer customer) {
        dao.saveObject(customer);

    }

    /**
     * @see no.ugland.utransprod.service.CustomerManager#findByCustomerNr(java.lang.Integer)
     */
    public final Customer findByCustomerNr(final Integer nr) {
        Customer customer = new Customer();
        customer.setCustomerNr(nr);
        List<Customer> customers = dao.findByExample(customer);
        if (customers == null || customers.size() != 1) {
            return null;
        }
        return customers.get(0);
    }

    /**
     * @see no.ugland.utransprod.service.CustomerManager#removeAll()
     */
    public final void removeAll() {
        dao.removeAll();

    }

    /**
     * @see no.ugland.utransprod.service.CustomerManager#removeCustomer(no.ugland.utransprod.model.Customer)
     */
    public final void removeCustomer(final Customer customer) {
        dao.removeObject(customer.getCustomerId());

    }

    /**
     * @see no.ugland.utransprod.service.CustomerManager#findAll()
     */
    public final List<Customer> findAll() {
        return ((CustomerDAO)dao).findAll();
    }

    /**
     * @see no.ugland.utransprod.service.CustomerManager#findByCustomer(no.ugland.utransprod.model.Customer)
     */
    public final List<Customer> findByCustomer(final Customer customer) {
        return dao.findByExampleLike(customer);
    }

    /**
     * @param object
     * @return kunder
     * @see no.ugland.utransprod.service.OverviewManager#findByObject(java.lang.Object)
     */
    public final List<Customer> findByObject(final Customer object) {
        return findByCustomer(object);
    }

    /**
     * @param object
     * @see no.ugland.utransprod.service.OverviewManager#removeObject(java.lang.Object)
     */
    public final void removeObject(final Customer object) {
        removeCustomer(object);

    }

    /**
     * @param object
     * @see no.ugland.utransprod.service.OverviewManager#saveObject(java.lang.Object)
     */
    public final void saveObject(final Customer object) {
        saveCustomer(object);

    }

    /**
     * @param customer
     * @see no.ugland.utransprod.service.OverviewManager#refreshObject(java.lang.Object)
     */
    public final void refreshObject(final Customer customer) {
        ((CustomerDAO)dao).refreshObject(customer);

    }

    /**
     * @see no.ugland.utransprod.service.CustomerManager#lazyLoad(no.ugland.utransprod.model.Customer,
     *      no.ugland.utransprod.service.enums.LazyLoadCustomerEnum[])
     */
    /*public final void lazyLoad(final Customer customer, final LazyLoadCustomerEnum[] enums) {
        dao.lazyLoad(customer, enums);

    }*/

    /*public void lazyLoad(Customer object, Enum[] enums) {
        lazyLoad(object,(LazyLoadCustomerEnum[]) enums);
        
    }*/

    @Override
    protected Serializable getObjectId(Customer object) {
        return object.getCustomerId();
    }
}
