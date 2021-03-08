/*    */ package no.ugland.utransprod.service.impl;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import no.ugland.utransprod.model.ExternalOrderLine;
/*    */ import no.ugland.utransprod.service.ExternalOrderLineManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ExternalOrderLineManagerImpl extends ManagerImpl<ExternalOrderLine> implements ExternalOrderLineManager {
/*    */    protected Serializable getObjectId(ExternalOrderLine object) {
/* 16 */       return object.getExternalOrderLineId();
/*    */    }
/*    */ }
