/*    */ package no.ugland.utransprod.service.impl;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.List;
/*    */ import no.ugland.utransprod.ProTransException;
/*    */ import no.ugland.utransprod.model.AttributeChoice;
/*    */ import no.ugland.utransprod.service.AttributeChoiceManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AttributeChoiceManagerImpl extends ManagerImpl<AttributeChoice> implements AttributeChoiceManager {
/*    */    public List<AttributeChoice> findAll() {
/* 14 */       return this.dao.getObjects();
/*    */    }
/*    */ 
/*    */    public List<AttributeChoice> findByObject(AttributeChoice object) {
/* 18 */       return this.dao.findByExampleLike(object);
/*    */    }
/*    */ 
/*    */    public void lazyLoad(AttributeChoice object, Enum[] enums) {
/* 22 */    }
/*    */ 
/*    */    public void refreshObject(AttributeChoice object) {
/* 25 */       this.dao.refreshObject(object, object.getAttributeChoiceId());
/*    */ 
/* 27 */    }
/*    */ 
/*    */    public void removeObject(AttributeChoice object) {
/* 30 */       this.dao.removeObject(object.getAttributeChoiceId());
/*    */ 
/* 32 */    }
/*    */ 
/*    */    public void saveObject(AttributeChoice object) throws ProTransException {
/* 35 */       this.dao.saveObject(object);
/*    */ 
/* 37 */    }
/*    */ 
/*    */ 
/*    */    protected Serializable getObjectId(AttributeChoice object) {
/* 41 */       return object.getAttributeChoiceId();
/*    */    }
/*    */ }
