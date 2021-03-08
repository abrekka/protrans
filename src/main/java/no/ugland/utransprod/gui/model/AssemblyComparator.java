/*    */ package no.ugland.utransprod.gui.model;
/*    */ 
/*    */ import java.util.Comparator;
/*    */ import no.ugland.utransprod.model.Assembly;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AssemblyComparator implements Comparator<Assembly> {
/*    */    public int compare(Assembly o1, Assembly o2) {
/* 22 */       if (o1.getAssemblyId() > o2.getAssemblyId()) {
/* 23 */          return -1;      } else {
/* 24 */          return o1.getAssemblyId() < o2.getAssemblyId() ? 1 : 0;
/*    */       }
/*    */    }
/*    */ }
