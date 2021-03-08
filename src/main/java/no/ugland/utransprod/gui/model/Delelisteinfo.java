/*     */ package no.ugland.utransprod.gui.model;
/*     */ 
/*     */ import java.math.BigDecimal;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Delelisteinfo {
/*     */    private String ordrenummer;
/*     */    private BigDecimal antall;
/*     */    private Integer prodtp;
/*     */    private String txt;
/*     */    private Integer prodtp2;
/*     */    private String enhet;
/*     */    private String kundenavn;
/*     */    private String sted;
/*     */    private String garasjetype;
/*     */    private String beskrivelse;
/*     */    private String nummer;
/*     */    private String informasjon;
/*     */    private String prodno;
/*     */    private Integer prCatNo2;
/*     */    private Integer purcno;
/*     */    private Integer prodgr;
/*     */    private String lokasjon;
/*     */ 
/*     */    public Delelisteinfo(String ordrenummer, String kundenavn, String sted, String garasjetype, BigDecimal antall, Integer prodtp, String txt, Integer prodtp2, String enhet, String beskrivelse, String nummer, String informasjon, String prodno, Integer prCatNo2, Integer purcno, Integer prodgr, String lokasjon) {
/*  28 */       this.prCatNo2 = prCatNo2;
/*  29 */       this.prodno = prodno;
/*  30 */       this.informasjon = informasjon;
/*  31 */       this.nummer = nummer;
/*  32 */       this.beskrivelse = beskrivelse;
/*  33 */       this.ordrenummer = ordrenummer;
/*  34 */       this.kundenavn = kundenavn;
/*  35 */       this.sted = sted;
/*  36 */       this.antall = antall;
/*  37 */       this.prodtp = prodtp;
/*  38 */       this.txt = txt;
/*  39 */       this.prodtp2 = prodtp2;
/*  40 */       this.enhet = enhet;
/*  41 */       this.garasjetype = garasjetype;
/*  42 */       this.purcno = purcno;
/*  43 */       this.prodgr = prodgr;
/*  44 */       this.lokasjon = lokasjon;
/*  45 */    }
/*     */ 
/*     */    public String getLokasjon() {
/*  48 */       return this.lokasjon;
/*     */    }
/*     */ 
/*     */    public void setLokasjon(String lokasjon) {
/*  52 */       this.lokasjon = lokasjon;
/*  53 */    }
/*     */ 
/*     */    public Integer getPrCatNo2() {
/*  56 */       return this.prCatNo2;
/*     */    }
/*     */ 
/*     */    public String getProdno() {
/*  60 */       return this.prodno;
/*     */    }
/*     */ 
/*     */    public String getInformasjon() {
/*  64 */       return this.purcno == 0 || this.prodgr != 1440 && this.prodgr != 1430 && this.prodgr != 3000 && this.prodgr != 1410 && this.prodgr != 1415 && this.prodgr != 1420 ? this.informasjon : this.informasjon + this.purcno;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getNummer() {
/*  72 */       return this.nummer;
/*     */    }
/*     */ 
/*     */    public String getBeskrivelse() {
/*  76 */       return this.beskrivelse;
/*     */    }
/*     */ 
/*     */    public String getGarasjetype() {
/*  80 */       return this.garasjetype;
/*     */    }
/*     */ 
/*     */    public String getSted() {
/*  84 */       return this.sted;
/*     */    }
/*     */ 
/*     */    public String getKundenavn() {
/*  88 */       return this.kundenavn;
/*     */    }
/*     */ 
/*     */    public String getTxtGruppe() {
/*  92 */       return "" + this.prodtp2 + " [" + this.txt + "]";
/*     */    }
/*     */ 
/*     */    public String getTxt() {
/*  96 */       return this.txt;
/*     */    }
/*     */ 
/*     */    public Integer getProdtp2() {
/* 100 */       return this.prodtp2;
/*     */    }
/*     */ 
/*     */    public String getEnhet() {
/* 104 */       return this.enhet;
/*     */    }
/*     */ 
/*     */    public String getOrdrenummer() {
/* 108 */       return this.ordrenummer;
/*     */    }
/*     */ 
/*     */    public BigDecimal getAntall() {
/* 112 */       return this.antall;
/*     */    }
/*     */ 
/*     */    public Integer getProdtp() {
/* 116 */       return this.prodtp;
/*     */    }
/*     */ }
