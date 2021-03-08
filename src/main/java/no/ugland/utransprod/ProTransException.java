/*    */ package no.ugland.utransprod;
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
/*    */ 
/*    */ 
/*    */ public class ProTransException extends RuntimeException {
/*    */    private static final long serialVersionUID = 1L;
/*    */ 
/*    */    public ProTransException(String msg) {
/* 23 */       super(msg);
/* 24 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public ProTransException(Throwable exception) {
/* 33 */       super(exception);
/* 34 */    }
/*    */ }
