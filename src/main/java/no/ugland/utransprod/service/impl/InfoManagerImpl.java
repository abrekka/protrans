/*     */ package no.ugland.utransprod.service.impl;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import no.ugland.utransprod.dao.InfoDAO;
/*     */ import no.ugland.utransprod.model.Info;
/*     */ import no.ugland.utransprod.service.InfoManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InfoManagerImpl extends ManagerImpl<Info> implements InfoManager {
/*     */    public final List<Info> findAll() {
/*  21 */       return ((InfoDAO)this.dao).findAll();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final List<Info> findByInfo(Info info) {
/*  30 */       return this.dao.findByExampleLike(info);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final List<Info> findByObject(Info object) {
/*  39 */       return this.findByInfo(object);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void refreshObject(Info object) {
/*  47 */       ((InfoDAO)this.dao).refreshObject(object);
/*     */ 
/*  49 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void removeInfo(Info info) {
/*  56 */       this.dao.removeObject(info.getInfoId());
/*     */ 
/*  58 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void removeObject(Info object) {
/*  65 */       this.removeInfo(object);
/*     */ 
/*  67 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void saveInfo(Info info) {
/*  74 */       this.dao.saveObject(info);
/*     */ 
/*  76 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void saveObject(Info object) {
/*  83 */       this.saveInfo(object);
/*     */ 
/*  85 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final Info findByDate(Date date) {
/*  91 */       List<Info> list = ((InfoDAO)this.dao).findByDate(date);
/*  92 */       StringBuffer buffer = new StringBuffer();
/*  93 */       List<String> texts = new ArrayList();
/*  94 */       if (list != null) {
/*  95 */          Iterator var5 = list.iterator();         while(var5.hasNext()) {            Info info = (Info)var5.next();
/*  96 */             buffer.append(info.getInfoText()).append("\n");
/*  97 */             texts.add(info.getInfoText());
/*     */          }      }
/*     */ 
/* 100 */       return new Info((Integer)null, buffer.toString(), (Date)null, (Date)null);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final List<String> findListByDate(Date date) {
/* 107 */       List<Info> list = ((InfoDAO)this.dao).findByDate(date);
/* 108 */       List<String> texts = new ArrayList();
/* 109 */       if (list != null) {
/* 110 */          Iterator var4 = list.iterator();         while(var4.hasNext()) {            Info info = (Info)var4.next();
/* 111 */             texts.add(info.getInfoText());
/*     */          }      }
/*     */ 
/* 114 */       return texts;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */    protected Serializable getObjectId(Info object) {
/* 120 */       return object.getInfoId();
/*     */    }
/*     */ }
