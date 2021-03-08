/*    */ package no.ugland.utransprod.gui.handlers;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CheckObject {
/*    */    private boolean canContinue;
/*    */    private String msg;
/*    */    private Object refObject;
/*    */ 
/*    */    public CheckObject(String aMsg, boolean doCanContinue) {
/* 14 */       this(aMsg, doCanContinue, (Object)null);
/* 15 */    }
/*    */ 
/*    */    public CheckObject(String aMsg, boolean doCanContinue, Object aRefObject) {
/* 18 */       this.msg = aMsg;
/* 19 */       this.canContinue = doCanContinue;
/* 20 */       this.refObject = aRefObject;
/* 21 */    }
/*    */ 
/*    */    public final String getMsg() {
/* 24 */       return this.msg;
/*    */    }
/*    */ 
/*    */    public final boolean canContinue() {
/* 28 */       return this.canContinue;
/*    */    }
/*    */ 
/*    */    public Object getRefObject() {
/* 32 */       return this.refObject;
/*    */    }
/*    */ 
/*    */    public void setRefObject(Object refObject) {
/* 36 */       this.refObject = refObject;
/* 37 */    }
/*    */ }
