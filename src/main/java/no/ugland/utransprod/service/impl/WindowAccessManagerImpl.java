/*    */ package no.ugland.utransprod.service.impl;
/*    */ 
/*    */ import java.util.List;
/*    */ import no.ugland.utransprod.dao.WindowAccessDAO;
/*    */ import no.ugland.utransprod.model.WindowAccess;
/*    */ import no.ugland.utransprod.service.WindowAccessManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WindowAccessManagerImpl implements WindowAccessManager {
/*    */    private WindowAccessDAO dao;
/*    */ 
/*    */    public final void setWindowAccessDAO(WindowAccessDAO aDao) {
/* 20 */       this.dao = aDao;
/* 21 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final List<WindowAccess> findAll() {
/* 27 */       return this.dao.getObjects("windowName");
/*    */    }
/*    */ 
/*    */    public List<WindowAccess> findAllWithTableNames() {
/* 31 */       return this.dao.findAllWithTableNames();
/*    */    }
/*    */ }
