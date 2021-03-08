/*    */ package no.ugland.utransprod.gui.handlers;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
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
/*    */ public enum TableEnum {
/*    */    TABLEORDERS("TableOrders", new String[]{"Produktområde", "Sannsynlighet"}),
/*    */    TABLEPOSTSHIPMENTS("TablePostShipments", new String[]{"Produktområde"}),
/*    */    TABLETRANSPORTORDERS("TableTransportOrders", new String[]{"Transport", "Ikke sendt", "Komplett", "Klar", "Produktområde", "Sannsynlighet"}),
/*    */    TABLETRANSPORTORDERSLIST("TableTransportOrdersList", new String[]{"Ikke sendt", "Komplett", "Klar", "Produktområde", "Sannsynlighet"}),
/*    */    TABLEPRODUCTIONGAVL("TableProductionGavl", new String[]{"Produktområde"}),
/*    */    TABLEPRODUCTIONTAKSTOL("TableProductionTakstol", new String[]{"Produktområde", "Sannsynlighet"}),
/*    */    TABLEPRODUCTIONFRONT("TableProductionFront", new String[]{"Produktområde"}),
/*    */    TABLEINVOICE("TableInvoice", new String[]{"Produktområde"}),
/*    */    TABLEPACKLIST("TablePacklist", new String[]{"Produktområde"}),
/*    */    TABLEPACKAGEGULVSPON("TablePackageGulvspon", new String[]{"Produktområde"}),
/*    */    TABLEPACKAGEIGARASJEN("TablePackageIgarasjen", new String[]{"Produktområde"}),
/*    */    TABLEPACKAGESUTAK("TablePackageSutak", new String[]{"Produktområde"}),
/*    */    TABLEPAID("TablePaid", new String[]{"Produktområde"}),
/*    */    TABLETAKSTEIN("TableTakstein", new String[]{"Produktområde"}),
/*    */    TABLEPRODUCTIONVEGG("TableProductionVegg", new String[]{"Produktområde"}),
/*    */    TABLEPACKAGETAKSTOL("TablePackageTakstol", new String[]{"Produktområde", "Artikkel", "Komplett", "Sannsynlighet"}),
/*    */    TABLEPRODUCTIONOVERVIEW("TableProductionOverview", new String[]{"Komplett", "Klar", "Produktområde"});
/*    */ 
/*    */    private List<String> invisibleColumns;
/*    */    private String tableName;
/*    */ 
/*    */    private TableEnum(String aTableName, String[] invisibleColumnArray) {
/* 46 */       this.tableName = aTableName;
/* 47 */       this.invisibleColumns = Arrays.asList(invisibleColumnArray);
/* 48 */    }
/*    */ 
/*    */    public List<String> getInvisibleColumns() {
/* 51 */       return this.invisibleColumns;
/*    */    }
/*    */ 
/*    */    public String getTableName() {
/* 55 */       return this.tableName;
/*    */    }
/*    */ }
