/*    */ package no.ugland.utransprod.service.impl;
/*    */ 
/*    */ import no.ugland.utransprod.dao.ProjectDAO;
/*    */ import no.ugland.utransprod.model.Project;
/*    */ import no.ugland.utransprod.service.ProjectManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ProjectManagerImpl implements ProjectManager {
/*    */    private ProjectDAO dao;
/*    */ 
/*    */    public final void setProjectDAO(ProjectDAO aDao) {
/* 15 */       this.dao = aDao;
/* 16 */    }
/*    */ 
/*    */ 
/*    */    public Project findByOrderNr(String orderNr) {
/* 20 */       return this.dao.findByOrderNr(orderNr);
/*    */    }
/*    */ }
