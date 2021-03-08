/*    */ package no.ugland.utransprod.gui.model;
/*    */ 
/*    */ import java.awt.Color;
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
/*    */ 
/*    */ 
/*    */ public enum ColorEnum {
/*    */    RED(new Color(241, 51, 3)),
/*    */    GREEN(new Color(113, 189, 94)),
/*    */    YELLOW(new Color(240, 245, 37)),
/*    */    BLUE(new Color(30, 43, 193)),
/*    */    GREY(new Color(129, 129, 129));
/*    */ 
/*    */    private Color color;
/*    */ 
/*    */    private ColorEnum(Color color) {
/* 34 */       this.color = color;
/* 35 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public Color getColor() {
/* 41 */       return this.color;
/*    */    }
/*    */ }
