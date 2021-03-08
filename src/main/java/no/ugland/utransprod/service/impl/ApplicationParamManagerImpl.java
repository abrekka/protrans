/*     */ package no.ugland.utransprod.service.impl;
/*     */ 
/*     */ import com.google.common.collect.ArrayListMultimap;
/*     */ import com.google.common.collect.Multimap;
/*     */ import java.io.Serializable;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import no.ugland.utransprod.ProTransRuntimeException;
/*     */ import no.ugland.utransprod.dao.ApplicationParamDAO;
/*     */ import no.ugland.utransprod.model.ApplicationParam;
/*     */ import no.ugland.utransprod.service.ApplicationParamManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ApplicationParamManagerImpl extends ManagerImpl<ApplicationParam> implements ApplicationParamManager {
/*     */    public final String findByName(String paramName) {
/*  23 */       ApplicationParam param = ((ApplicationParamDAO)this.dao).findByName(paramName);
/*  24 */       if (param != null) {
/*  25 */          return param.getParamValue();
/*     */       } else {
/*  27 */          throw new ProTransRuntimeException("Fant ikke parameter " + paramName);
/*     */       }
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */    public final Multimap<String, String> getColliSetup() {
/*  34 */       ApplicationParam example = new ApplicationParam();
/*  35 */       example.setParamName("kolli_%");
/*  36 */       List<ApplicationParam> params = this.dao.findByExampleLike(example);
/*  37 */       Multimap<String, String> colliSetup = ArrayListMultimap.create();
/*     */ 
/*     */ 
/*  40 */       Iterator var6 = params.iterator();      while(var6.hasNext()) {         ApplicationParam param = (ApplicationParam)var6.next();
/*  41 */          String paramValue = param.getParamValue();
/*  42 */          String[] paramValueSplit = paramValue.split(";");
/*  43 */          colliSetup.put(paramValueSplit[0], paramValueSplit[1]);
/*     */       }
/*  45 */       return colliSetup;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final List<ApplicationParam> findByExampleLike(ApplicationParam param) {
/*  53 */       return this.dao.findByExampleLike(param);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final String findByNameSilent(String paramName) {
/*  60 */       ApplicationParam param = ((ApplicationParamDAO)this.dao).findByName(paramName);
/*  61 */       return param != null ? param.getParamValue() : null;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final List<ApplicationParam> findAll() {
/*  72 */       ApplicationParam example = new ApplicationParam();
/*  73 */       example.setUserAccessible(1);
/*  74 */       return this.dao.findByExample(example);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final List<ApplicationParam> findByObject(ApplicationParam object) {
/*  83 */       return this.dao.findByExampleLike(object);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void refreshObject(ApplicationParam object) {
/*  91 */       ((ApplicationParamDAO)this.dao).refreshObject(object);
/*     */ 
/*  93 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void removeObject(ApplicationParam object) {
/* 100 */       this.dao.removeObject(object.getParamId());
/*     */ 
/* 102 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void saveObject(ApplicationParam object) {
/* 109 */       this.dao.saveObject(object);
/*     */ 
/* 111 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void saveApplicationParam(ApplicationParam param) {
/* 118 */       this.dao.saveObject(param);
/*     */ 
/* 120 */    }
/*     */ 
/*     */    public ApplicationParam findParam(String paramName) {
/* 123 */       return ((ApplicationParamDAO)this.dao).findByName(paramName);
/*     */    }
/*     */ 
/*     */    public void lazyLoad(ApplicationParam object, Enum[] enums) {
/* 127 */    }
/*     */ 
/*     */ 
/*     */    protected Serializable getObjectId(ApplicationParam object) {
/* 131 */       return object.getParamId();
/*     */    }
/*     */ 
/*     */    public ApplicationParam merge(ApplicationParam object) {
/* 135 */       return (ApplicationParam)this.dao.merge(object);
/*     */    }
/*     */ }
