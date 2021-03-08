/*    */ package no.ugland.utransprod.service.enums;import java.util.Hashtable;import java.util.Map;
/*    */ public enum ProductAreaGroupEnum {
/*    */    TAKSTOL(300, "Takstol");
/*    */ 
/*    */    private Integer deptNo;
/*    */    private String productAreaGroupName;
/*    */    private static Map<Integer, ProductAreaGroupEnum> productAreaGroups = new Hashtable();
/*    */ 
/*    */    static {
/*    */       ProductAreaGroupEnum[] var0 = values();
/*    */       int var1 = var0.length;
/*    */ 
/* 13 */       for(int var2 = 0; var2 < var1; ++var2) {         ProductAreaGroupEnum productArea = var0[var2];
/* 14 */          productAreaGroups.put(productArea.getDeptNo(), productArea);
/*    */       }
/* 16 */    }
/*    */ 
/*    */    private ProductAreaGroupEnum(Integer deptno, String aProductAreaGroupName) {
/* 19 */       this.deptNo = deptno;
/* 20 */       this.productAreaGroupName = aProductAreaGroupName;
/* 21 */    }
/*    */ 
/*    */    public Integer getDeptNo() {
/* 24 */       return this.deptNo;
/*    */    }
/*    */ 
/*    */    public static ProductAreaGroupEnum getProductAreGroupByProdno(Integer productAreaNr) {
/* 28 */       return (ProductAreaGroupEnum)productAreaGroups.get(productAreaNr);
/*    */    }
/*    */ 
/*    */    public String getProductAreaGroupName() {
/* 32 */       return this.productAreaGroupName;
/*    */    }
/*    */ }
