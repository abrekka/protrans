/*    */ package no.ugland.utransprod.gui.model;
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
/*    */ 
/*    */ public enum WindowEnum {
/*    */    CUSTOMER("Kunder"),
/*    */    ORDER("Ordre");
/*    */ 
/*    */    private String title;
/*    */ 
/*    */    private WindowEnum(String aTitle) {
/* 27 */       this.title = aTitle;
/* 28 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public String getTitle() {
/* 34 */       return this.title;
/*    */    }
/*    */ }
