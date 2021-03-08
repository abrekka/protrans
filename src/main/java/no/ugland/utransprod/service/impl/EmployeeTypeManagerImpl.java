/*    */ package no.ugland.utransprod.service.impl;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.List;
/*    */ import no.ugland.utransprod.dao.EmployeeTypeDAO;
/*    */ import no.ugland.utransprod.model.EmployeeType;
/*    */ import no.ugland.utransprod.service.EmployeeTypeManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EmployeeTypeManagerImpl extends ManagerImpl<EmployeeType> implements EmployeeTypeManager {
/*    */    public final List<EmployeeType> findAll() {
/* 19 */       return this.dao.getObjects("employeeTypeName");
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final List<EmployeeType> findByEmployeeType(EmployeeType employeeType) {
/* 27 */       return this.dao.findByExampleLike(employeeType);
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final List<EmployeeType> findByObject(EmployeeType object) {
/* 36 */       return this.findByEmployeeType(object);
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final void refreshObject(EmployeeType object) {
/* 44 */       ((EmployeeTypeDAO)this.dao).refreshObject(object);
/*    */ 
/* 46 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final void removeEmployeeType(EmployeeType employeeType) {
/* 52 */       this.dao.removeObject(employeeType.getEmployeeTypeId());
/*    */ 
/* 54 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final void removeObject(EmployeeType object) {
/* 61 */       this.removeEmployeeType(object);
/*    */ 
/* 63 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final void saveEmployeeType(EmployeeType employeeType) {
/* 69 */       this.dao.saveObject(employeeType);
/*    */ 
/* 71 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final void saveObject(EmployeeType object) {
/* 78 */       this.saveEmployeeType(object);
/*    */ 
/* 80 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */    protected Serializable getObjectId(EmployeeType object) {
/* 85 */       return object.getEmployeeTypeId();
/*    */    }
/*    */ }
