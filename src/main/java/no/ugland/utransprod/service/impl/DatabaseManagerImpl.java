/*    */ package no.ugland.utransprod.service.impl;
/*    */ 
/*    */ import java.sql.Connection;
/*    */ import no.ugland.utransprod.dao.pkg.DatabasePkgDAO;
/*    */ import no.ugland.utransprod.service.DatabaseManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DatabaseManagerImpl implements DatabaseManager {
/*    */    private DatabasePkgDAO dao;
/*    */ 
/*    */    public final void setDatabasePkgDAO(DatabasePkgDAO aDao) {
/* 16 */       this.dao = aDao;
/* 17 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final Connection getDbConnection() {
/* 23 */       return this.dao.getDbConnection();
/*    */    }
/*    */ }
