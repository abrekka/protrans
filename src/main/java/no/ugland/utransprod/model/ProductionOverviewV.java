package no.ugland.utransprod.model;

import java.math.BigDecimal;
import java.util.Date;

public class ProductionOverviewV {
    private ProductionOverviewVPK productionOverviewVPK;

    private String orderNr;
    private Date packlistReady;
    private Integer transportYear;
    private Integer transportWeek;
    private Date loadingDate;
    private String transportName;
    private String specialConcern;
    private String comment;
    private String firstName;
    private String lastName;
    private String postalCode;
    private String postOffice;
    private String constructionTypeName;
    private String info;

    private String packlistDoneBy;

    private BigDecimal packlistDuration;

    private String productArea;

    private String productAreaGroupName;

    private Integer productionWeek;

    private BigDecimal estimatedTimeWall;

    private BigDecimal wallRealProductionHours;

    private Date wallActionStarted;

    private Date wallProduced;

    private BigDecimal estimatedTimeGavl;

    private BigDecimal gavlRealProductionHours;

    private Date gavlActionStarted;

    private Date gavlProduced;

    private BigDecimal estimatedTimePack;

    private String status;

    private Integer doAssembly;

    private Integer assemblyWeek;

    private BigDecimal ownProduction;

    private BigDecimal ownProductionInternal;

    private BigDecimal deliveryCost;

    private BigDecimal assemblyCost;

    private String receivedTrossDrawing;

    private Integer trossDeldt;

    private Integer trossCfdeldt;

    private Date orderComplete;

    private Date orderReady;

    public ProductionOverviewVPK getProductionOverviewVPK() {
	return productionOverviewVPK;
    }

    public void setProductionOverviewVPK(ProductionOverviewVPK productionOverviewVPK) {
	this.productionOverviewVPK = productionOverviewVPK;
    }

    public String getOrderNr() {
	return orderNr;
    }

    public void setOrderNr(String orderNr) {
	this.orderNr = orderNr;
    }

    public Date getPacklistReady() {
	return packlistReady;
    }

    public void setPacklistReady(Date packlistReady) {
	this.packlistReady = packlistReady;
    }

    public Integer getTransportWeek() {
	return transportWeek;
    }

    public Integer getTransportYear() {
	return transportYear;
    }

    public void setTransportWeek(Integer transportweek) {
	this.transportWeek = transportweek;
    }

    public void setTransportYear(Integer transportYear) {
	this.transportYear = transportYear;
    }

    public Date getLoadingDate() {
	return loadingDate;
    }

    public void setLoadingDate(Date loadingDate) {
	this.loadingDate = loadingDate;
    }

    public String getTransportName() {
	return transportName;
    }

    public void setTransportName(String transportName) {
	this.transportName = transportName;
    }

    public String getSpecialConcern() {
	return specialConcern;
    }

    public void setSpecialConcern(String specialConcern) {
	this.specialConcern = specialConcern;
    }

    public String getComment() {
	return comment;
    }

    public void setComment(String comment) {
	this.comment = comment;
    }

    public boolean isPostShipment() {
	return productionOverviewVPK.getPostShipmentId() != -1;
    }

    public String getFirstName() {
	return firstName;
    }

    public String getLastName() {
	return lastName;
    }

    public void setFirstName(String firstName) {
	this.firstName = firstName;
    }

    public void setLastName(String lastName) {
	this.lastName = lastName;
    }

    public String getPostalCode() {
	return postalCode;
    }

    public String getPostOffice() {
	return postOffice;
    }

    public String getConstructionTypeName() {
	return constructionTypeName;
    }

    public void setPostalCode(String postalCode) {
	this.postalCode = postalCode;
    }

    public void setPostOffice(String postOffice) {
	this.postOffice = postOffice;
    }

    public void setConstructionTypeName(String constructionTypeName) {
	this.constructionTypeName = constructionTypeName;
    }

    public String getInfo() {
	return info;
    }

    public void setInfo(String info) {
	this.info = info;
    }

    public String getTransportString() {
	StringBuffer stringBuffer = new StringBuffer(firstName).append(" ").append(lastName);
	stringBuffer.append(" - ").append(orderNr).append("\n").append(postalCode).append(" ").append(postOffice).append(",")
		.append(constructionTypeName).append(",").append(info);

	return stringBuffer.toString();
    }

    public String getPacklistDoneBy() {
	return packlistDoneBy;
    }

    public void setPacklistDoneBy(String packlistDoneBy) {
	this.packlistDoneBy = packlistDoneBy;
    }

    public BigDecimal getPacklistDuration() {
	return packlistDuration;
    }

    public void setPacklistDuration(BigDecimal packlistDuration) {
	this.packlistDuration = packlistDuration;
    }

    public String getProductArea() {
	return productArea;
    }

    public void setProductArea(String productArea) {
	this.productArea = productArea;
    }

    public String getProductAreaGroupName() {
	return productAreaGroupName;
    }

    public void setProductAreaGroupName(String productAreaGroupName) {
	this.productAreaGroupName = productAreaGroupName;
    }

    public Integer getProductionWeek() {
	return productionWeek;
    }

    public void setProductionWeek(Integer productionWeek) {
	this.productionWeek = productionWeek;
    }

    public BigDecimal getEstimatedTimeWall() {
	return estimatedTimeWall;
    }

    public void setEstimatedTimeWall(BigDecimal estimatedTimeWall) {
	this.estimatedTimeWall = estimatedTimeWall;
    }

    public BigDecimal getWallRealProductionHours() {
	return wallRealProductionHours;
    }

    public void setWallRealProductionHours(BigDecimal wallRealProductionHours) {
	this.wallRealProductionHours = wallRealProductionHours;
    }

    public Date getWallActionStarted() {
	return wallActionStarted;
    }

    public void setWallActionStarted(Date wallActionStarted) {
	this.wallActionStarted = wallActionStarted;
    }

    public Date getWallProduced() {
	return wallProduced;
    }

    public void setWallProduced(Date wallProduced) {
	this.wallProduced = wallProduced;
    }

    public BigDecimal getEstimatedTimeGavl() {
	return estimatedTimeGavl;
    }

    public void setEstimatedTimeGavl(BigDecimal estimatedTimeGavl) {
	this.estimatedTimeGavl = estimatedTimeGavl;
    }

    public BigDecimal getGavlRealProductionHours() {
	return gavlRealProductionHours;
    }

    public void setGavlRealProductionHours(BigDecimal gavlRealProductionHours) {
	this.gavlRealProductionHours = gavlRealProductionHours;
    }

    public Date getGavlActionStarted() {
	return gavlActionStarted;
    }

    public void setGavlActionStarted(Date gavlActionStarted) {
	this.gavlActionStarted = gavlActionStarted;
    }

    public Date getGavlProduced() {
	return gavlProduced;
    }

    public void setGavlProduced(Date gavlProduced) {
	this.gavlProduced = gavlProduced;
    }

    public BigDecimal getEstimatedTimePack() {
	return estimatedTimePack;
    }

    public void setEstimatedTimePack(BigDecimal estimatedTimePack) {
	this.estimatedTimePack = estimatedTimePack;
    }

    public String getStatus() {
	return status;
    }

    public void setStatus(String status) {
	this.status = status;
    }

    public Integer getDoAssembly() {
	return doAssembly;
    }

    public void setDoAssembly(Integer doAssembly) {
	this.doAssembly = doAssembly;
    }

    public Integer getAssemblyWeek() {
	return assemblyWeek;
    }

    public void setAssemblyWeek(Integer assemblyWeek) {
	this.assemblyWeek = assemblyWeek;
    }

    public BigDecimal getOwnProduction() {
	return ownProduction;
    }

    public void setOwnProduction(BigDecimal ownProduction) {
	this.ownProduction = ownProduction;
    }

    public BigDecimal getOwnProductionInternal() {
	return ownProductionInternal;
    }

    public void setOwnProductionInternal(BigDecimal ownProductionInternal) {
	this.ownProductionInternal = ownProductionInternal;
    }

    public BigDecimal getDeliveryCost() {
	return deliveryCost;
    }

    public void setDeliveryCost(BigDecimal deliveryCost) {
	this.deliveryCost = deliveryCost;
    }

    public BigDecimal getAssemblyCost() {
	return assemblyCost;
    }

    public void setAssemblyCost(BigDecimal assemblyCost) {
	this.assemblyCost = assemblyCost;
    }

    public String getReceivedTrossDrawing() {
	return receivedTrossDrawing;
    }

    public void setReceivedTrossDrawing(String receivedTrossDrawing) {
	this.receivedTrossDrawing = receivedTrossDrawing;
    }

    public Integer getTrossDeldt() {
	return trossDeldt;
    }

    public void setTrossDeldt(Integer trossDeldt) {
	this.trossDeldt = trossDeldt;
    }

    public Integer getTrossCfdeldt() {
	return trossCfdeldt;
    }

    public void setTrossCfdeldt(Integer trossCfdeldt) {
	this.trossCfdeldt = trossCfdeldt;
    }

    public Date getOrderComplete() {
	return orderComplete;
    }

    public void setOrderComplete(Date orderComplete) {
	this.orderComplete = orderComplete;
    }

    public Date getOrderReady() {
	return orderReady;
    }

    public void setOrderReady(Date orderReady) {
	this.orderReady = orderReady;
    }

    public Integer getPostshipmentId() {
	return productionOverviewVPK.getPostShipmentId();
    }

}
