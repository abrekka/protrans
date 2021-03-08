/*    */ package no.ugland.utransprod.service.impl;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.List;
/*    */ import no.ugland.utransprod.dao.EmployeeDAO;
/*    */ import no.ugland.utransprod.model.Employee;
/*    */ import no.ugland.utransprod.model.Supplier;
/*    */ import no.ugland.utransprod.service.EmployeeManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EmployeeManagerImpl extends ManagerImpl<Employee> implements EmployeeManager {
/*    */    public final List<Employee> findAll() {
/* 20 */       return this.dao.getObjects("firstName");
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final List<Employee> findByEmployee(Employee employee) {
/* 28 */       return this.dao.findByExampleLike(employee);
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final List<Employee> findByObject(Employee object) {
/* 37 */       return this.findByEmployee(object);
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final void refreshObject(Employee object) {
/* 45 */       ((EmployeeDAO)this.dao).refreshObject(object);
/*    */ 
/* 47 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final void removeEmployee(Employee employee) {
/* 53 */       this.dao.removeObject(employee.getEmployeeId());
/*    */ 
/* 55 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final void removeObject(Employee object) {
/* 62 */       this.removeEmployee(object);
/*    */ 
/* 64 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final void saveEmployee(Employee employee) {
/* 70 */       this.dao.saveObject(employee);
/*    */ 
/* 72 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final void saveObject(Employee object) {
/* 79 */       this.saveEmployee(object);
/*    */ 
/* 81 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final List<Employee> findBySupplier(Supplier supplier) {
/* 87 */       return ((EmployeeDAO)this.dao).findBySupplier(supplier);
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */    protected Serializable getObjectId(Employee object) {
/* 93 */       return object.getEmployeeId();
/*    */    }
/*    */ }
