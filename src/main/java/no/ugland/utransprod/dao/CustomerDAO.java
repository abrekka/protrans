package no.ugland.utransprod.dao;

import java.util.List;

import no.ugland.utransprod.model.Customer;

/**
 * Interface for DAO klasse mot tabell CUSTOMER
 * @author atle.brekka
 *
 */
public interface CustomerDAO extends DAO<Customer>{
    /**
     * Fjerner alle kunder
     */
    void removeAll();
    
    /**
     * Finner alle kunder
     * @return kunder
     */
    List<Customer> findAll();
    /**
     * Oppdaterer objekt
     * @param customer
     */
    void refreshObject(Customer customer);
    /**
     * Lazy laster kunde
     * @param customer
     * @param enums
     */
    //void lazyLoad(Customer customer, LazyLoadCustomerEnum[] enums);
    /**
     * Finner kunde med gitt kundenr
     * @param customerNr
     * @return kunde
     */
    Customer findByCustomerNr(Integer customerNr);
}
