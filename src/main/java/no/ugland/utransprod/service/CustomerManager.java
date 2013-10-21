package no.ugland.utransprod.service;

import java.util.List;

import no.ugland.utransprod.model.Customer;

/**
 * Interface for manager for kunder
 * @author atle.brekka
 */
public interface CustomerManager extends OverviewManager<Customer> {
    String MANAGER_NAME = "customerManager";

    /**
     * Lagre kunde
     * @param customer
     */
    void saveCustomer(Customer customer);

    /**
     * Finn kundemed kundenr
     * @param nr
     * @return kunde eller null dersom det ikke finnes noen
     */
    Customer findByCustomerNr(Integer nr);

    /**
     * Fjern alle kunder
     */
    void removeAll();

    /**
     * Fjern kunde
     * @param customer
     */
    void removeCustomer(Customer customer);

    /**
     * Finn alle kunder
     * @return kunder
     */
    List<Customer> findAll();

    /**
     * Finn alle kunder basert på eksempel
     * @param customer
     * @return kunder
     */
    List<Customer> findByCustomer(Customer customer);

    /**
     * Lazy laster kunde
     * @param customer
     * @param enums
     */
    //void lazyLoad(Customer customer, LazyLoadCustomerEnum[] enums);
}
