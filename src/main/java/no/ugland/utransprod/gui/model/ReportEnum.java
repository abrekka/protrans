/*     */ package no.ugland.utransprod.gui.model;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import no.ugland.utransprod.gui.IconEnum;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum ReportEnum {
/*     */    GULVSPON("Gulvspon.jasper", IconEnum.ICON_IGLAND, "Gulvspon"),
/*     */    IGARASJEN("Gulvspon.jasper", IconEnum.ICON_IGLAND, "Igrasjen"),
/*     */    TAKSTOL("Takstol.jasper", IconEnum.ICON_IGLAND, "Takstol"),
/*     */    MONTERING("montering_bean.jasper", IconEnum.ICON_IGLAND, "Montering"),
/*     */    NOKKEl("Nokkeltall_bean.jasper", IconEnum.ICON_IGLAND, "Nøkkeltall - Salg/Drift/Transport"),
/*     */    NOKKEl_PRODUCTION("Nokkeltall_produksjon_alt_bean.jasper", IconEnum.ICON_IGLAND, "Nøkkeltall - Produksjon"),
/*     */    NOKKEl_ASSEMBLY("Nokkeltall_mont_ok_bean.jasper", IconEnum.ICON_IGLAND, "Nøkkeltall - Montering/Økonomi"),
/*     */    FAX("Order_fax.jasper", IconEnum.ICON_IGLAND, "Fax"),
/*     */    DEVIATION("deviation_report.jasper", IconEnum.ICON_IGLAND, "Avvik"),
/*     */    TRANSPORT_LETTER("fraktbrev.jasper", IconEnum.ICON_IGLAND, "Fraktbrev"),
/*     */    PACKLIST("Pakkliste.jasper", IconEnum.ICON_IGLAND, "Pakkliste"),
/*     */    TRANSPORT_COST("Transport_cost.jasper", IconEnum.ICON_IGLAND, "Transportkostnad"),
/*     */    ASSEMBLY("Assembly.jasper", IconEnum.ICON_IGLAND, "Montering"),
/*     */    ASSEMBLY_NY("assembly_ny_ny.jasper", IconEnum.ICON_IGLAND, "Montering"),
/*     */    ASSEMBLY_NY_SVENSK("assembly_ny_svensk.jasper", IconEnum.ICON_IGLAND_SE, "Montering"),
/*     */    ACCIDENT("accident.jasper", IconEnum.ICON_IGLAND, "Hendelse/ulykke"),
/*     */    TAKSTOL_INFO("Takstolinfo.jasper", IconEnum.ICON_JATAK, "Takstolinfo"),
/*     */    KORRIGERENDE_TILTAK("korrigerende_tiltak.jasper", IconEnum.ICON_IGLAND, "Korrigerende tiltak"),
/*     */    PRODUCTION_REPORT("produksjon.jasper", IconEnum.ICON_IGLAND, "Produksjon"),
/*     */    DELELISTE_KUNDE_REPORT("deleliste_kunde.jasper", IconEnum.ICON_IGLAND, "Deleliste kunde");
/*     */ 
/*     */    private String reportFileName;
/*     */    private String reportName;
/*     */    private IconEnum icon;
/*     */ 
/*     */    private ReportEnum(String aReportFileName, IconEnum iconEnum, String aReportName) {
/* 109 */       this.reportFileName = aReportFileName;
/* 110 */       this.icon = iconEnum;
/* 111 */       this.reportName = aReportName;
/* 112 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getReportFileName() {
/* 118 */       return this.reportFileName;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getReportName() {
/* 125 */       return this.reportName;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getImagePath() {
/* 132 */       return this.icon.getIconPath();
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public static List<ReportEnum> getKeyReports() {
/* 139 */       ArrayList<ReportEnum> reportList = new ArrayList();
/* 140 */       reportList.add(NOKKEl);
/* 141 */       reportList.add(NOKKEl_ASSEMBLY);
/* 142 */       reportList.add(NOKKEl_PRODUCTION);
/* 143 */       return reportList;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String toString() {
/* 151 */       return this.reportName;
/*     */    }
/*     */ }
