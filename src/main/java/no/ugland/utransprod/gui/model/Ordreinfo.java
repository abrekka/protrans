/*    */ package no.ugland.utransprod.gui.model;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Ordreinfo {
/*    */    private String ordrenr;
/*    */    private Integer linjenr;
/*    */    private String beskrivelse;
/*    */    private Integer vegghoyde;
/*    */    private Integer murhoyde;
/*    */    private String prodno;
/*    */    private String type;
/*    */    private Integer purcno;
/*    */    private Integer prcatno;
/*    */    private Integer free4;
/*    */    private Integer prCatNo2;
/*    */    private String inf;
/*    */ 
/*    */    public Ordreinfo(String ordrenr, Integer linjenr, String beskrivelse, Integer vegghoyde, Integer murhoyde, String prodno, String type, Integer purcno, Integer prcatno, Integer free4, Integer prCatNo2, String inf) {
/* 20 */       this.ordrenr = ordrenr;
/* 21 */       this.linjenr = linjenr;
/* 22 */       this.beskrivelse = beskrivelse;
/* 23 */       this.vegghoyde = vegghoyde;
/* 24 */       this.murhoyde = murhoyde;
/* 25 */       this.prodno = prodno;
/* 26 */       this.type = type;
/* 27 */       this.purcno = purcno;
/* 28 */       this.prcatno = prcatno;
/* 29 */       this.free4 = free4;
/* 30 */       this.prCatNo2 = prCatNo2;
/* 31 */       this.inf = inf;
/* 32 */    }
/*    */    public String getInf() {
/* 34 */       return this.inf;
/*    */    }
/*    */ 
/*    */    public Integer getPrCatNo2() {
/* 38 */       return this.prCatNo2;
/*    */    }
/*    */ 
/*    */    public Integer getLinjenr() {
/* 42 */       return this.linjenr;
/*    */    }
/*    */ 
/*    */    public String getBeskrivelse() {
/* 46 */       return this.beskrivelse;
/*    */    }
/*    */ 
/*    */    public Integer getVegghoyde() {
/* 50 */       return this.vegghoyde;
/*    */    }
/*    */ 
/*    */    public Integer getMurhoyde() {
/* 54 */       return this.murhoyde;
/*    */    }
/*    */ 
/*    */    public String getProdno() {
/* 58 */       return this.prodno;
/*    */    }
/*    */ 
/*    */    public String getType() {
/* 62 */       return this.type;
/*    */    }
/*    */ 
/*    */    public Integer getPurcno() {
/* 66 */       return this.purcno;
/*    */    }
/*    */ 
/*    */    public Integer getPrcatno() {
/* 70 */       return this.prcatno;
/*    */    }
/*    */ 
/*    */    public Integer getFree4() {
/* 74 */       return this.free4;
/*    */    }
/*    */ }
