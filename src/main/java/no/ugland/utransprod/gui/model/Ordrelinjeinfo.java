/*    */ package no.ugland.utransprod.gui.model;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Ordrelinjeinfo {
/*    */    private String trInf3;
/*    */    private Integer prodTp2;
/*    */    private String articleName;
/*    */    private Integer numberOfItems;
/*    */    private String descr;
/*    */    private String attributeInfo;
/*    */ 
/*    */    public Ordrelinjeinfo(String trInf3, Integer prodTp2, String articleName, Integer numerOfItems, String descr, String attributeInfo) {
/* 14 */       this.trInf3 = trInf3;
/* 15 */       this.prodTp2 = prodTp2;
/* 16 */       this.articleName = articleName;
/* 17 */       this.numberOfItems = numerOfItems;
/* 18 */       this.descr = descr;
/* 19 */       this.attributeInfo = attributeInfo;
/* 20 */    }
/*    */ 
/*    */    public String getTrInf3() {
/* 23 */       return this.trInf3;
/*    */    }
/*    */ 
/*    */    public Integer getProdTp2() {
/* 27 */       return this.prodTp2;
/*    */    }
/*    */ 
/*    */    public String getArticleName() {
/* 31 */       return this.articleName;
/*    */    }
/*    */ 
/*    */    public Integer getNumberOfItems() {
/* 35 */       return this.numberOfItems;
/*    */    }
/*    */ 
/*    */    public String getDesc() {
/* 39 */       return this.descr;
/*    */    }
/*    */ 
/*    */    public String getAttributeInfo() {
/* 43 */       return this.attributeInfo;
/*    */    }
/*    */ }
