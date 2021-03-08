/*     */ package no.ugland.utransprod.service.impl;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.List;
/*     */ import no.ugland.utransprod.dao.CustomerDAO;
/*     */ import no.ugland.utransprod.model.Customer;
/*     */ import no.ugland.utransprod.service.CustomerManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CustomerManagerImpl extends ManagerImpl<Customer> implements CustomerManager {
/*     */    public final void saveCustomer(Customer customer) {
/*  19 */       this.dao.saveObject(customer);
/*     */ 
/*  21 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final Customer findByCustomerNr(Integer nr) {
/*  27 */       Customer customer = new Customer();
/*  28 */       customer.setCustomerNr(nr);
/*  29 */       List<Customer> customers = this.dao.findByExample(customer);
/*  30 */       return customers != null && customers.size() == 1 ? (Customer)customers.get(0) : null;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void removeAll() {
/*  40 */       this.dao.removeAll();
/*     */ 
/*  42 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void removeCustomer(Customer customer) {
/*  48 */       this.dao.removeObject(customer.getCustomerId());
/*     */ 
/*  50 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final List<Customer> findAll() {
/*  56 */       return ((CustomerDAO)this.dao).findAll();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final List<Customer> findByCustomer(Customer customer) {
/*  63 */       return this.dao.findByExampleLike(customer);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final List<Customer> findByObject(Customer object) {
/*  72 */       return this.findByCustomer(object);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void removeObject(Customer object) {
/*  80 */       this.removeCustomer(object);
/*     */ 
/*  82 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void saveObject(Customer object) {
/*  89 */       this.saveCustomer(object);
/*     */ 
/*  91 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void refreshObject(Customer customer) {
/*  98 */       ((CustomerDAO)this.dao).refreshObject(customer);
/*     */ 
/* 100 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    protected Serializable getObjectId(Customer object) {
/* 118 */       return object.getCustomerId();
/*     */    }
/*     */ }
