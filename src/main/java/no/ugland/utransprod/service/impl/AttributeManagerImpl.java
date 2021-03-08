/*     */ package no.ugland.utransprod.service.impl;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.List;
/*     */ import no.ugland.utransprod.dao.AttributeDAO;
/*     */ import no.ugland.utransprod.model.Attribute;
/*     */ import no.ugland.utransprod.service.AttributeManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AttributeManagerImpl extends ManagerImpl<Attribute> implements AttributeManager {
/*     */    public final Attribute findByName(String name) {
/*  19 */       Attribute att = new Attribute();
/*  20 */       att.setName(name);
/*  21 */       List<Attribute> list = this.dao.findByExample(att);
/*  22 */       return list != null && list.size() == 1 ? (Attribute)list.get(0) : null;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void removeAttribute(Attribute attribute) {
/*  33 */       this.dao.removeObject(attribute.getAttributeId());
/*     */ 
/*  35 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void saveAttribute(Attribute attribute) {
/*  41 */       this.dao.saveObject(attribute);
/*     */ 
/*  43 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final List<Attribute> findAll() {
/*  49 */       return this.dao.getObjects();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final List<Attribute> findByAttribute(Attribute attribute) {
/*  58 */       return this.dao.findByExampleLike(attribute);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final List<Attribute> findByObject(Attribute object) {
/*  67 */       return this.findByAttribute(object);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void refreshObject(Attribute object) {
/*  75 */       ((AttributeDAO)this.dao).refreshObject(object);
/*     */ 
/*  77 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void removeObject(Attribute object) {
/*  84 */       this.removeAttribute(object);
/*     */ 
/*  86 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void saveObject(Attribute object) {
/*  93 */       this.saveAttribute(object);
/*     */ 
/*  95 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    protected Serializable getObjectId(Attribute object) {
/* 113 */       return object.getAttributeId();
/*     */    }
/*     */ }
