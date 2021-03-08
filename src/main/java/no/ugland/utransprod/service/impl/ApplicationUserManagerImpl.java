/*     */ package no.ugland.utransprod.service.impl;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import no.ugland.utransprod.dao.ApplicationUserDAO;
/*     */ import no.ugland.utransprod.model.ApplicationUser;
/*     */ import no.ugland.utransprod.service.ApplicationUserManager;
/*     */ import no.ugland.utransprod.service.JobFunctionManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ApplicationUserManagerImpl extends ManagerImpl<ApplicationUser> implements ApplicationUserManager {
/*     */    private JobFunctionManager jobFunctionManager;
/*     */ 
/*     */    public final void setJobFunctionManager(JobFunctionManager aJobFunctionManager) {
/*  24 */       this.jobFunctionManager = aJobFunctionManager;
/*  25 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final ApplicationUser login(String userName, String password) {
/*  32 */       List<ApplicationUser> users = ((ApplicationUserDAO)this.dao).findByUserNameAndPassword(userName, password);
/*  33 */       return users != null && users.size() == 1 ? (ApplicationUser)users.get(0) : null;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final List<ApplicationUser> findAll() {
/*  43 */       return this.dao.getObjects("userName");
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final List<ApplicationUser> findAllNotGroup() {
/*  50 */       return ((ApplicationUserDAO)this.dao).findAllNotGroup();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final List<ApplicationUser> findByObject(ApplicationUser object) {
/*  59 */       return this.dao.findByExampleLike(object);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void refreshObject(ApplicationUser object) {
/*  67 */       ((ApplicationUserDAO)this.dao).refreshObject(object);
/*     */ 
/*  69 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void removeObject(ApplicationUser object) {
/*  76 */       this.dao.removeObject(object.getUserId());
/*     */ 
/*  78 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void saveObject(ApplicationUser object) {
/*  85 */       this.dao.saveObject(object);
/*     */ 
/*  87 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final List<String> findAllPackers() {
/*  94 */       return ((ApplicationUserDAO)this.dao).findAllPackers();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final Boolean isUserFunctionManager(ApplicationUser user) {
/* 102 */       return this.jobFunctionManager.isFunctionManager(user);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final List<String> findAllNamesNotGroup() {
/* 109 */       List<ApplicationUser> users = this.findAllNotGroup();
/* 110 */       List<String> userNames = new ArrayList();
/* 111 */       if (users != null) {         Iterator var3 = users.iterator();         while(var3.hasNext()) {
/* 112 */             ApplicationUser user = (ApplicationUser)var3.next();
/* 113 */             userNames.add(user.getFullName());
/*     */          }      }
/*     */ 
/* 116 */       return userNames;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void saveApplicationUser(ApplicationUser user) {
/* 124 */       this.dao.saveObject(user);
/*     */ 
/* 126 */    }
/*     */ 
/*     */ 
/*     */    protected Serializable getObjectId(ApplicationUser object) {
/* 130 */       return object.getUserId();
/*     */    }
/*     */ 
/*     */    public ApplicationUser merge(ApplicationUser object) {
/* 134 */       return (ApplicationUser)this.dao.merge(object);
/*     */    }
/*     */ }
