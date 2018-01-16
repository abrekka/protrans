package no.ugland.utransprod.model;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class DeviationV extends BaseObject {
	private Integer deviationId;
	private Date registrationDate;
	private String userName;
	private Integer customerNr;
	private String customerName;
	private String orderNr;
	private String productArea;
	private String responsible;
	private String ownFunction;
	private String deviationFunction;
	private String functionCategoryName;
	private String deviationStatusName;
	private Integer registrationWeek;
	private Integer registrationYear;
	private BigDecimal internalCost;
	private BigDecimal externalCost;
	private String initiatedBy;
	private Date orderReady;
	// private String supplierName;
	private String csId;
	private Integer preventiveActionId;
	private String preventiveActionName;
	private Date dateClosed;
	private String lastChanged;
	private Integer postShipmentId;
	private String transportnavn;
	private Integer transportYear;
	private Integer transportWeek;
	private Integer doAssembly;
	private Integer assemblyYear;
	private Integer assemblyWeek;
	private String montoernavn;
	private Date transportSent;
	private Date assembliedDate;

	public DeviationV() {
		super();
	}

	public DeviationV(Integer deviationId, Date registrationDate, String userName, Integer customerNr,
			String customerName, String orderNr, String productArea, String responsible, String ownFunction,
			String deviationFunction, String functionCategoryName, String deviationStatusName, Integer registrationWeek,
			Integer registrationYear, BigDecimal internalCost, BigDecimal externalCost, Date orderReady,
			String supplierName) {
		super();
		this.orderReady = orderReady;
		this.deviationId = deviationId;
		this.registrationDate = registrationDate;
		this.userName = userName;
		this.customerNr = customerNr;
		this.customerName = customerName;
		this.orderNr = orderNr;
		this.productArea = productArea;
		this.responsible = responsible;
		this.ownFunction = ownFunction;
		this.deviationFunction = deviationFunction;
		this.functionCategoryName = functionCategoryName;
		this.deviationStatusName = deviationStatusName;
		this.registrationWeek = registrationWeek;
		this.registrationYear = registrationYear;
		this.internalCost = internalCost;
		this.externalCost = externalCost;
		// this.supplierName = supplierName;
	}

	public Date getTransportSent() {
		return transportSent;
	}
	public void setTransportSent(Date transportSent) {
		this.transportSent = transportSent;
	}
	public Date getAssembliedDate() {
		return assembliedDate;
	}
	public void setAssembliedDate(Date assembliedDate) {
		this.assembliedDate = assembliedDate;
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
	public Integer getAssemblyYear() {
		return assemblyYear;
	}
	public void setAssemblyYear(Integer assemblyYear) {
		this.assemblyYear = assemblyYear;
	}
	public String getMontoernavn() {
		return montoernavn;
	}
	public void setMontoernavn(String montoernavn) {
		this.montoernavn = montoernavn;
	}
	public Integer getTransportWeek() {
		return transportWeek;
	}

	public void setTransportWeek(Integer transportWeek) {
		this.transportWeek = transportWeek;
	}

	public Integer getTransportYear() {
		return transportYear;
	}

	public void setTransportYear(Integer transportYear) {
		this.transportYear = transportYear;
	}

	public String getTransportnavn() {
		return transportnavn;
	}

	public void setTransportnavn(String transportnavn) {
		this.transportnavn = transportnavn;
	}

	public Integer getPostShipmentId() {
		return postShipmentId;
	}

	public void setPostShipmentId(Integer postShipmentId) {
		this.postShipmentId = postShipmentId;
	}

	public String getLastChanged() {
		return lastChanged;
	}

	public void setLastChanged(String lastChanged) {
		this.lastChanged = lastChanged;
	}

	public Date getDateClosed() {
		return dateClosed;
	}

	public void setDateClosed(Date dateClosed) {
		this.dateClosed = dateClosed;
	}

	public Integer getPreventiveActionId() {
		return preventiveActionId;
	}

	public String getPreventiveActionName() {
		return preventiveActionName;
	}

	public void setPreventiveActionId(Integer preventiveActionId) {
		this.preventiveActionId = preventiveActionId;
	}

	public void setPreventiveActionName(String preventiveActionName) {
		this.preventiveActionName = preventiveActionName;
	}

	public String getCsId() {
		return csId;
	}

	public void setCsId(String csId) {
		this.csId = csId;
	}

	// public String getSupplierName() {
	// return supplierName;
	// }
	//
	// public void setSupplierName(String supplierName) {
	// this.supplierName = supplierName;
	// }

	public Date getOrderReady() {
		return orderReady;
	}

	public void setOrderReady(Date orderReady) {
		this.orderReady = orderReady;
	}

	public Integer getDeviationId() {
		return deviationId;
	}

	public void setDeviationId(Integer deviationId) {
		this.deviationId = deviationId;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getCustomerNr() {
		return customerNr;
	}

	public void setCustomerNr(Integer customerNr) {
		this.customerNr = customerNr;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getOrderNr() {
		return orderNr;
	}

	public void setOrderNr(String orderNr) {
		this.orderNr = orderNr;
	}

	public String getProductArea() {
		return productArea;
	}

	public void setProductArea(String productArea) {
		this.productArea = productArea;
	}

	public String getResponsible() {
		return responsible;
	}

	public void setResponsible(String responsible) {
		this.responsible = responsible;
	}

	public String getOwnFunction() {
		return ownFunction;
	}

	public void setOwnFunction(String ownFunction) {
		this.ownFunction = ownFunction;
	}

	public String getDeviationFunction() {
		return deviationFunction;
	}

	public void setDeviationFunction(String deviationFunction) {
		this.deviationFunction = deviationFunction;
	}

	public String getFunctionCategoryName() {
		return functionCategoryName;
	}

	public void setFunctionCategoryName(String functionCategoryName) {
		this.functionCategoryName = functionCategoryName;
	}

	public String getDeviationStatusName() {
		return deviationStatusName;
	}

	public void setDeviationStatusName(String deviationStatusName) {
		this.deviationStatusName = deviationStatusName;
	}

	public Integer getRegistrationWeek() {
		return registrationWeek;
	}

	public void setRegistrationWeek(Integer registrationWeek) {
		this.registrationWeek = registrationWeek;
	}

	public Integer getRegistrationYear() {
		return registrationYear;
	}

	public void setRegistrationYear(Integer registrationYear) {
		this.registrationYear = registrationYear;
	}

	public BigDecimal getInternalCost() {
		return internalCost;
	}

	public void setInternalCost(BigDecimal internalCost) {
		this.internalCost = internalCost;
	}

	public BigDecimal getExternalCost() {
		return externalCost;
	}

	public void setExternalCost(BigDecimal externalCost) {
		this.externalCost = externalCost;
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof DeviationV))
			return false;
		DeviationV castOther = (DeviationV) other;
		return new EqualsBuilder().append(deviationId, castOther.deviationId).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(deviationId).toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("deviationId", deviationId).append("registrationDate", registrationDate)
				.append("userName", userName).append("customerNr", customerNr).append("customerName", customerName)
				.append("orderNr", orderNr).append("productArea", productArea).append("responsible", responsible)
				.append("ownFunction", ownFunction).append("deviationFunction", deviationFunction)
				.append("functionCategoryName", functionCategoryName).append("deviationStatusName", deviationStatusName)
				.append("registrationWeek", registrationWeek).append("registrationYear", registrationYear)
				.append("internalCost", internalCost).append("externalCost", externalCost).toString();
	}

	public String getInitiatedBy() {
		return initiatedBy;
	}

	public void setInitiatedBy(String initiatedBy) {
		this.initiatedBy = initiatedBy;
	}
}
