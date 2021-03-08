/*    */ package no.ugland.utransprod.service.impl;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.List;
/*    */ import no.ugland.utransprod.dao.JobFunctionDAO;
/*    */ import no.ugland.utransprod.model.ApplicationUser;
/*    */ import no.ugland.utransprod.model.JobFunction;
/*    */ import no.ugland.utransprod.service.JobFunctionManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class JobFunctionManagerImpl extends ManagerImpl<JobFunction> implements JobFunctionManager {
/*    */    public final List<JobFunction> findAll() {
/* 20 */       return this.dao.getObjects();
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final List<JobFunction> findByObject(JobFunction object) {
/* 29 */       return this.dao.findByExampleLike(object);
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final void refreshObject(JobFunction object) {
/* 37 */       ((JobFunctionDAO)this.dao).refreshObject(object);
/*    */ 
/* 39 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final void removeObject(JobFunction object) {
/* 46 */       this.dao.removeObject(object.getJobFunctionId());
/*    */ 
/* 48 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final void saveObject(JobFunction object) {
/* 55 */       this.dao.saveObject(object);
/*    */ 
/* 57 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final Boolean isFunctionManager(ApplicationUser user) {
/* 74 */       return ((JobFunctionDAO)this.dao).isFunctionManager(user);
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    protected Serializable getObjectId(JobFunction object) {
/* 84 */       return object.getJobFunctionId();
/*    */    }
/*    */ }
