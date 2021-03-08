/*    */ package no.ugland.utransprod.service.impl;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.List;
/*    */ import no.ugland.utransprod.dao.UserTypeDAO;
/*    */ import no.ugland.utransprod.model.UserType;
/*    */ import no.ugland.utransprod.service.UserTypeManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class UserTypeManagerImpl extends ManagerImpl<UserType> implements UserTypeManager {
/*    */    public final List<UserType> findAll() {
/* 19 */       return this.dao.getObjects("description");
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final int getNumberOfUsers(UserType userType) {
/* 27 */       return ((UserTypeDAO)this.dao).getNumberOfUsers(userType);
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final List<UserType> findByUserType(UserType userType) {
/* 36 */       return this.dao.findByExample(userType);
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final List<UserType> findByObject(UserType object) {
/* 45 */       return this.findByUserType(object);
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final void refreshObject(UserType userType) {
/* 53 */       ((UserTypeDAO)this.dao).refresh(userType);
/*    */ 
/* 55 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final void removeUserType(UserType userType) {
/* 62 */       this.dao.removeObject(userType.getUserTypeId());
/*    */ 
/* 64 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final void removeObject(UserType object) {
/* 71 */       this.removeUserType(object);
/*    */ 
/* 73 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public final void saveObject(UserType object) {
/* 80 */       this.dao.saveObject(object);
/*    */ 
/* 82 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    protected Serializable getObjectId(UserType object) {
/* 91 */       return object.getUserTypeId();
/*    */    }
/*    */ }
