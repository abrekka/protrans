/*    */ package no.ugland.utransprod.gui.model;
/*    */ 
/*    */ import java.util.ArrayList;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum SketchEnum {
/*    */    DOBBEL_18_22("18-22 dobbel.jpg"),
/*    */    ENKEL_18_22("18-22 enkel.jpg"),
/*    */    DOBBEL_30_38_45("30-38-45 dobbel.jpg"),
/*    */    REKKE_5_ROMS_22_38("Rekke 5 roms 22-38.jpg");
/*    */ 
/*    */    private String fileName;
/*    */ 
/*    */    private SketchEnum(String aFileName) {
/* 39 */       this.fileName = aFileName;
/* 40 */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public String getFileName() {
/* 46 */       return this.fileName;
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public static SketchEnum getSketchEnum(String aFileName) {
/* 54 */       if (aFileName != null) {
/* 55 */          if (aFileName.equalsIgnoreCase("18-22 dobbel.jpg")) {
/* 56 */             return DOBBEL_18_22;         }
/* 57 */          if (aFileName.equalsIgnoreCase("18-22 enkel.jpg")) {
/* 58 */             return ENKEL_18_22;         }
/* 59 */          if (aFileName.equalsIgnoreCase("30-38-45 dobbel.jpg")) {
/* 60 */             return DOBBEL_30_38_45;         }
/* 61 */          if (aFileName.equalsIgnoreCase("Rekke 5 roms 22-38.jpg")) {
/* 62 */             return REKKE_5_ROMS_22_38;
/*    */          }      }
/*    */ 
/* 65 */       return null;
/*    */    }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */    public static List<SketchEnum> getSketchList() {
/* 72 */       ArrayList<SketchEnum> enumList = new ArrayList();
/* 73 */       enumList.add(ENKEL_18_22);
/* 74 */       enumList.add(DOBBEL_18_22);
/* 75 */       enumList.add(DOBBEL_30_38_45);
/* 76 */       enumList.add(REKKE_5_ROMS_22_38);
/* 77 */       return enumList;
/*    */    }
/*    */ }
