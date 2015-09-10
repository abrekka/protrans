package no.ugland.utransprod.model;

import java.math.BigDecimal;
import java.util.Date;

import com.google.common.base.Joiner;

public class AssemblyV {
    private Integer assemblyId;
    private Integer orderId;
    private Date sent;
    private Date assembliedDate;
    private Integer assemblyYear;
    private Integer assemblyWeek;
    private String supplierName;
    private String planned;
    private Date packlistReady;
    private String sentBase;
    private String orderNr;
    private String firstName;
    private String lastName;
    private String postalCode;
    private String postOffice;
    private String telephoneNr;
    private String productArea;
    private Integer productionWeek;
    private Integer transportYear;
    private Integer transportWeek;
    private String transportName;
    private String taksteinInfo;
    private BigDecimal assemblyCost;
    private String firstPlanned;
    private BigDecimal kraningCost;
    private Integer hasMissingCollies;
    private String constructionTypeName;
    private String info;
    private String specialConcern;

    public Integer getAssemblyId() {
	return assemblyId;
    }

    public void setAssemblyId(Integer assemblyId) {
	this.assemblyId = assemblyId;
    }

    public Integer getOrderId() {
	return orderId;
    }

    public void setOrderId(Integer orderId) {
	this.orderId = orderId;
    }

    public Date getSent() {
	return sent;
    }

    public void setSent(Date sent) {
	this.sent = sent;
    }

    public Date getAssembliedDate() {
	return assembliedDate;
    }

    public void setAssembliedDate(Date assembliedDate) {
	this.assembliedDate = assembliedDate;
    }

    public Integer getAssemblyYear() {
	return assemblyYear;
    }

    public void setAssemblyYear(Integer assemblyYear) {
	this.assemblyYear = assemblyYear;
    }

    public Integer getAssemblyWeek() {
	return assemblyWeek;
    }

    public void setAssemblyWeek(Integer assemblyWeek) {
	this.assemblyWeek = assemblyWeek;
    }

    public String getSupplierName() {
	return supplierName;
    }

    public void setSupplierName(String supplierName) {
	this.supplierName = supplierName;
    }

    public String getPlanned() {
	return planned;
    }

    public void setPlanned(String planned) {
	this.planned = planned;
    }

    public Date getPacklistReady() {
	return packlistReady;
    }

    public void setPacklistReady(Date packlistReady) {
	this.packlistReady = packlistReady;
    }

    public String getSentBase() {
	return sentBase;
    }

    public void setSentBase(String sentBase) {
	this.sentBase = sentBase;
    }

    public String getOrderNr() {
	return orderNr;
    }

    public void setOrderNr(String orderNr) {
	this.orderNr = orderNr;
    }

    public String getFirstName() {
	return firstName;
    }

    public void setFirstName(String firstName) {
	this.firstName = firstName;
    }

    public String getLastName() {
	return lastName;
    }

    public void setLastName(String lastName) {
	this.lastName = lastName;
    }

    public String getPostalCode() {
	return postalCode;
    }

    public void setPostalCode(String postalCode) {
	this.postalCode = postalCode;
    }

    public String getPostOffice() {
	return postOffice;
    }

    public void setPostOffice(String postOffice) {
	this.postOffice = postOffice;
    }

    public String getTelephoneNr() {
	return telephoneNr;
    }

    public void setTelephoneNr(String telephoneNr) {
	this.telephoneNr = telephoneNr;
    }

    public String getProductArea() {
	return productArea;
    }

    public void setProductArea(String productArea) {
	this.productArea = productArea;
    }

    public Integer getProductionWeek() {
	return productionWeek;
    }

    public void setProductionWeek(Integer productionWeek) {
	this.productionWeek = productionWeek;
    }

    public Integer getTransportYear() {
	return transportYear;
    }

    public void setTransportYear(Integer transportYear) {
	this.transportYear = transportYear;
    }

    public Integer getTransportWeek() {
	return transportWeek;
    }

    public void setTransportWeek(Integer transportWeek) {
	this.transportWeek = transportWeek;
    }

    public String getTransportName() {
	return transportName;
    }

    public void setTransportName(String transportName) {
	this.transportName = transportName;
    }

    public String getTaksteinInfo() {
	return taksteinInfo;
    }

    public void setTaksteinInfo(String taksteinInfo) {
	this.taksteinInfo = taksteinInfo;
    }

    public BigDecimal getAssemblyCost() {
	return assemblyCost;
    }

    public void setAssemblyCost(BigDecimal assemblyCost) {
	this.assemblyCost = assemblyCost;
    }

    public String getFirstPlanned() {
	return firstPlanned;
    }

    public void setFirstPlanned(String firstPlanned) {
	this.firstPlanned = firstPlanned;
    }

    public BigDecimal getKraningCost() {
	return kraningCost;
    }

    public void setKraningCost(BigDecimal kraningCost) {
	this.kraningCost = kraningCost;
    }

    public Integer getHasMissingCollies() {
	return hasMissingCollies;
    }

    public void setHasMissingCollies(Integer hasMissingCollies) {
	this.hasMissingCollies = hasMissingCollies;
    }

    public Boolean getSentBool() {
	return getSent() != null;
    }

    public Boolean getAssembliedBool() {
	return assembliedDate != null;
    }

    public String getAssemblyteamName() {
	return supplierName;
    }

    public String getTelephoneNrFormatted() {
	if (telephoneNr != null) {
	    String tmpNr = telephoneNr.replaceAll(";", ",").trim();
	    if (tmpNr.indexOf(",") == 0) {
		tmpNr = tmpNr.substring(1, tmpNr.length());
	    }

	    if (tmpNr.lastIndexOf(",") == tmpNr.length() - 1) {
		tmpNr = tmpNr.substring(0, tmpNr.length() - 1);
	    }
	    return tmpNr;
	}
	return "";
    }

    public String getTransportDetails() {
	return Joiner.on(" ").skipNulls().join(transportYear, transportWeek, transportName);
    }

    public String getConstructionTypeName() {
	return constructionTypeName;
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

    public String getSpecialConcern() {
	return specialConcern;
    }

    public void setSpecialConcern(String specialConcern) {
	this.specialConcern = specialConcern;
    }
}
