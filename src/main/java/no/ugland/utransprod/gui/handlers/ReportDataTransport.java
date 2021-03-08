/*     */ package no.ugland.utransprod.gui.handlers;
/*     */ 
/*     */ import java.math.BigDecimal;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
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
/*     */ public class ReportDataTransport {
/*     */    private Integer numberOf;
/*     */    private Integer year;
/*     */    private Integer week;
/*     */    private Map<String, BigDecimal> costs;
/*     */    private static Set<String> costHeadings = new HashSet();
/*     */    private Integer orderId;
/*     */    private String orderNr;
/*     */    private Integer customerNr;
/*     */    private String customerName;
/*     */    private String deliveryAddress;
/*     */    private String postalCode;
/*     */    private String transportName;
/*     */    private Boolean isPostShipment;
/*     */    private Integer productionWeek;
/*     */ 
/*     */    public ReportDataTransport(Integer numberOf, Integer year, Integer week, Map<String, BigDecimal> costs, Integer orderId, String orderNr, Integer customerNr, String customerName, String deliveryAddress, String postalCode, String transportName, Boolean isPostShipment, Integer productionWeek) {
/*  63 */       this.numberOf = numberOf;
/*  64 */       this.year = year;
/*  65 */       this.week = week;
/*  66 */       this.costs = costs;
/*  67 */       this.orderId = orderId;
/*  68 */       this.orderNr = orderNr;
/*  69 */       this.customerNr = customerNr;
/*  70 */       this.customerName = customerName;
/*  71 */       this.deliveryAddress = deliveryAddress;
/*  72 */       this.postalCode = postalCode;
/*  73 */       this.transportName = transportName;
/*  74 */       this.isPostShipment = isPostShipment;
/*  75 */       this.productionWeek = productionWeek;
/*     */ 
/*  77 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public ReportDataTransport(Integer numberOf, Integer aYear, Integer aWeek, Map<String, BigDecimal> costs) {
/*  87 */       this.numberOf = numberOf;
/*  88 */       this.year = aYear;
/*  89 */       this.week = aWeek;
/*  90 */       this.costs = costs;
/*     */ 
/*  92 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public ReportDataTransport() {
/*  99 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Map<String, BigDecimal> getCosts() {
/* 105 */       return this.costs;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setCosts(Map<String, BigDecimal> costs) {
/* 112 */       this.costs = costs;
/* 113 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Integer getNumberOf() {
/* 119 */       return this.numberOf;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setCounter(Integer numberOf) {
/* 126 */       this.numberOf = numberOf;
/* 127 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Integer getWeek() {
/* 133 */       return this.week;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setWeek(Integer week) {
/* 140 */       this.week = week;
/* 141 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Integer getYear() {
/* 147 */       return this.year;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setYear(Integer year) {
/* 154 */       this.year = year;
/* 155 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setNumberOf(Integer numberOf) {
/* 161 */       this.numberOf = numberOf;
/* 162 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public static void addCostHeading(String costHeading) {
/* 170 */       costHeadings.add(costHeading);
/* 171 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Set<String> getCostHeadings() {
/* 177 */       return costHeadings;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public static void clearCostHeading() {
/* 184 */       costHeadings.clear();
/* 185 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Integer getCustomerNr() {
/* 191 */       return this.customerNr;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setCustomerNr(Integer customerNr) {
/* 198 */       this.customerNr = customerNr;
/* 199 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getDeliveryAddress() {
/* 205 */       return this.deliveryAddress;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setDeliveryAddress(String deliveryAddress) {
/* 212 */       this.deliveryAddress = deliveryAddress;
/* 213 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Integer getOrderId() {
/* 219 */       return this.orderId;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setOrderId(Integer orderId) {
/* 226 */       this.orderId = orderId;
/* 227 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getOrderNr() {
/* 233 */       return this.orderNr;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setOrderNr(String orderNr) {
/* 240 */       this.orderNr = orderNr;
/* 241 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getPostalCode() {
/* 247 */       return this.postalCode;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setPostalCode(String postalCode) {
/* 254 */       this.postalCode = postalCode;
/* 255 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getTransportName() {
/* 261 */       return this.transportName;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setTransportName(String transportName) {
/* 268 */       this.transportName = transportName;
/* 269 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public static void setCostHeadings(Set<String> costHeadings) {
/* 275 */       costHeadings = costHeadings;
/* 276 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getCustomerName() {
/* 282 */       return this.customerName;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setCustomerName(String customerName) {
/* 289 */       this.customerName = customerName;
/* 290 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public Boolean isPostShipment() {
/* 296 */       return this.isPostShipment;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setIsPostShipment(Boolean postShipment) {
/* 303 */       this.isPostShipment = postShipment;
/* 304 */    }
/*     */ 
/*     */    public Integer getProductionWeek() {
/* 307 */       return this.productionWeek;
/*     */    }
/*     */ 
/*     */    public void setProductionWeek(Integer productionWeek) {
/* 311 */       this.productionWeek = productionWeek;
/* 312 */    }
/*     */ }
