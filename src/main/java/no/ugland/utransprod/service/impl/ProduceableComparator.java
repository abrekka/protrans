/*    */ package no.ugland.utransprod.service.impl;
/*    */ 
/*    */ import java.util.Comparator;
/*    */ import no.ugland.utransprod.model.Produceable;
/*    */ import org.apache.commons.lang.builder.CompareToBuilder;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ProduceableComparator implements Comparator<Produceable> {
/*    */    public int compare(Produceable produceable1, Produceable produceable2) {
/* 12 */       return (new CompareToBuilder()).append(produceable1.getTransportYear(), produceable2.getTransportYear()).append(produceable1.getTransportWeek(), produceable2.getTransportWeek()).append(produceable1.getLoadingDate(), produceable2.getLoadingDate()).append(produceable1.getTransportDetails(), produceable2.getTransportDetails()).append(produceable1.getLoadTime(), produceable2.getLoadTime()).toComparison();
/*    */    }
/*    */ }
