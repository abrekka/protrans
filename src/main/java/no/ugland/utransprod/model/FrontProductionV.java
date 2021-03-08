/*     */ package no.ugland.utransprod.model;
/*     */ 
/*     */ import java.math.BigDecimal;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import no.ugland.utransprod.gui.model.Applyable;
/*     */ import no.ugland.utransprod.gui.model.TextRenderable;
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
/*     */ public class FrontProductionV extends Production implements TextRenderable, Produceable {
/*     */    private static final long serialVersionUID = 1L;
/*     */    private String orderStatus;
/*     */    private Ordln ordln;
/*     */    private BigDecimal realProductionHours;
/*     */    private Integer productionWeek;
/*     */    private String doneBy;
/*     */ 
/*     */    public FrontProductionV() {
/*  75 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public FrontProductionV(Integer orderLineId, Integer customerNr, String someCustomerDetails, String orderNr, String address, String someInfo, String constructionTypeName, String articleName, String someAttributeInfo, Integer numberOfItems, Date loadingDate, String someTransportDetails, String comment, Integer transportYear, Integer transportWeek, String loadTime, Integer postShipmentId, String productAreaGroupName, Date dateActionStarted, Date producedDate, Date productionDate, String productionUnitName, String aOrderStatus, Colli aColli) {
/*  83 */       super(orderLineId, customerNr, someCustomerDetails, orderNr, address, someInfo, constructionTypeName, articleName, someAttributeInfo, numberOfItems, loadingDate, someTransportDetails, comment, transportYear, transportWeek, loadTime, postShipmentId, productAreaGroupName, dateActionStarted, producedDate, productionDate, productionUnitName, aColli);
/*     */ 
/*     */ 
/*     */ 
/*  87 */       this.orderStatus = aOrderStatus;
/*  88 */    }
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
/*     */    public String getOrderString() {
/* 378 */       StringBuffer buffer = new StringBuffer(this.customerDetails);
/*     */ 
/* 380 */       buffer.append(" - ").append(this.orderNr).append("\n").append(this.address).append(" ,").append(this.constructionTypeName).append(",").append(this.info);
/*     */ 
/* 382 */       return buffer.toString();
/*     */    }
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
/*     */    public String getOrderStatus() {
/* 403 */       return this.orderStatus;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setOrderStatus(String orderStatus) {
/* 410 */       this.orderStatus = orderStatus;
/* 411 */    }
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
/*     */    public Boolean isForPostShipment() {
/* 431 */       return this.postShipmentId != null ? Boolean.TRUE : Boolean.FALSE;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getProductAreaGroupName() {
/* 441 */       return this.productAreaGroupName;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public void setProductAreaGroupName(String productAreaGroupName) {
/* 448 */       this.productAreaGroupName = productAreaGroupName;
/* 449 */    }
/*     */ 
/*     */    public Date getActionStarted() {
/* 452 */       return this.actionStarted;
/*     */    }
/*     */ 
/*     */    public void setActionStarted(Date actionStarted) {
/* 456 */       this.actionStarted = actionStarted;
/* 457 */    }
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
/*     */    public Ordln getOrdln() {
/* 476 */       return this.ordln;
/*     */    }
/*     */ 
/*     */    public void setOrdln(Ordln ordln) {
/* 480 */       this.ordln = ordln;
/* 481 */    }
/*     */ 
/*     */ 
/*     */    public Colli getColli() {
/* 485 */       return null;
/*     */    }
/*     */ 
/*     */ 
/*     */    public List<Applyable> getRelatedArticles() {
/* 490 */       return null;
/*     */    }
/*     */ 
/*     */    public boolean isRelatedArticlesComplete() {
/* 494 */       return false;
/*     */    }
/*     */ 
/*     */    public void setColli(Colli colli) {
/* 498 */    }
/*     */ 
/*     */    public void setRelatedArticles(List<Applyable> relatedArticles) {
/* 501 */    }
/*     */ 
/*     */    public boolean isRelatedArticlesStarted() {
/* 504 */       return false;
/*     */    }
/*     */ 
/*     */    public Date getRelatedStartedDate() {
/* 508 */       return null;
/*     */    }
/*     */ 
/*     */    public void setProductionUnit(ProductionUnit productionUnit) {
/* 512 */    }
/*     */ 
/*     */    public Integer getProbability() {
/* 515 */       return null;
/*     */    }
/*     */ 
/*     */    public String getTrossDrawer() {
/* 519 */       return null;
/*     */    }
/*     */ 
/*     */    public BigDecimal getRealProductionHours() {
/* 523 */       return this.realProductionHours;
/*     */    }
/*     */ 
/*     */    public void setRealProductionHours(BigDecimal realProductionHours) {
/* 527 */       this.realProductionHours = realProductionHours;
/* 528 */    }
/*     */ 
/*     */    public Integer getProductionWeek() {
/* 531 */       return this.productionWeek;
/*     */    }
/*     */ 
/*     */    public void setProductionWeek(Integer productionWeek) {
/* 535 */       this.productionWeek = productionWeek;
/* 536 */    }
/*     */ 
/*     */    public String getDoneBy() {
/* 539 */       return this.doneBy;
/*     */    }
/*     */ 
/*     */    public void setDoneBy(String doneBy) {
/* 543 */       this.doneBy = doneBy;
/* 544 */    }
/*     */ }
