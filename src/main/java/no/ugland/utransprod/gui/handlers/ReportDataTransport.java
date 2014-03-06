/**
 * 
 */
package no.ugland.utransprod.gui.handlers;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Holder på rapportdata
 * 
 * @author atle.brekka
 * 
 */
public class ReportDataTransport {
    private Integer numberOf;

    private Integer year;

    private Integer week;

    private Map<String, BigDecimal> costs;

    private static Set<String> costHeadings = new HashSet<String>();

    private Integer orderId;

    private String orderNr;

    private Integer customerNr;

    private String customerName;

    private String deliveryAddress;

    private String postalCode;

    private String transportName;

    private Boolean isPostShipment;
    private Integer productionWeek;

    /**
     * @param numberOf
     * @param year
     * @param week
     * @param costs
     * @param orderId
     * @param orderNr
     * @param customerNr
     * @param customerName
     * @param deliveryAddress
     * @param postalCode
     * @param transportName
     * @param isPostShipment
     */
    public ReportDataTransport(Integer numberOf, Integer year, Integer week, Map<String, BigDecimal> costs, Integer orderId, String orderNr,
	    Integer customerNr, String customerName, String deliveryAddress, String postalCode, String transportName, Boolean isPostShipment,
	    Integer productionWeek) {
	super();
	this.numberOf = numberOf;
	this.year = year;
	this.week = week;
	this.costs = costs;
	this.orderId = orderId;
	this.orderNr = orderNr;
	this.customerNr = customerNr;
	this.customerName = customerName;
	this.deliveryAddress = deliveryAddress;
	this.postalCode = postalCode;
	this.transportName = transportName;
	this.isPostShipment = isPostShipment;
	this.productionWeek = productionWeek;

    }

    /**
     * @param numberOf
     * @param aYear
     * @param aWeek
     * @param costs
     */
    public ReportDataTransport(Integer numberOf, Integer aYear, Integer aWeek, Map<String, BigDecimal> costs) {
	super();
	this.numberOf = numberOf;
	this.year = aYear;
	this.week = aWeek;
	this.costs = costs;

    }

    /**
	 * 
	 */
    public ReportDataTransport() {
	super();
    }

    /**
     * @return kostnader
     */
    public Map<String, BigDecimal> getCosts() {
	return costs;
    }

    /**
     * @param costs
     */
    public void setCosts(Map<String, BigDecimal> costs) {
	this.costs = costs;
    }

    /**
     * @return antall
     */
    public Integer getNumberOf() {
	return numberOf;
    }

    /**
     * @param numberOf
     */
    public void setCounter(Integer numberOf) {
	this.numberOf = numberOf;
    }

    /**
     * @return uke
     */
    public Integer getWeek() {
	return week;
    }

    /**
     * @param week
     */
    public void setWeek(Integer week) {
	this.week = week;
    }

    /**
     * @return år
     */
    public Integer getYear() {
	return year;
    }

    /**
     * @param year
     */
    public void setYear(Integer year) {
	this.year = year;
    }

    /**
     * @param numberOf
     */
    public void setNumberOf(Integer numberOf) {
	this.numberOf = numberOf;
    }

    /**
     * Legger til kostnadsoverskrift
     * 
     * @param costHeading
     */
    public static void addCostHeading(String costHeading) {
	costHeadings.add(costHeading);
    }

    /**
     * @return kostnadsoverskrifter
     */
    public Set<String> getCostHeadings() {
	return costHeadings;
    }

    /**
     * Rensker kostnadsoverskrifter
     */
    public static void clearCostHeading() {
	costHeadings.clear();
    }

    /**
     * @return kundenummer
     */
    public Integer getCustomerNr() {
	return customerNr;
    }

    /**
     * @param customerNr
     */
    public void setCustomerNr(Integer customerNr) {
	this.customerNr = customerNr;
    }

    /**
     * @return adresse
     */
    public String getDeliveryAddress() {
	return deliveryAddress;
    }

    /**
     * @param deliveryAddress
     */
    public void setDeliveryAddress(String deliveryAddress) {
	this.deliveryAddress = deliveryAddress;
    }

    /**
     * @return ordreid
     */
    public Integer getOrderId() {
	return orderId;
    }

    /**
     * @param orderId
     */
    public void setOrderId(Integer orderId) {
	this.orderId = orderId;
    }

    /**
     * @return ordrenummer
     */
    public String getOrderNr() {
	return orderNr;
    }

    /**
     * @param orderNr
     */
    public void setOrderNr(String orderNr) {
	this.orderNr = orderNr;
    }

    /**
     * @return postnummer
     */
    public String getPostalCode() {
	return postalCode;
    }

    /**
     * @param postalCode
     */
    public void setPostalCode(String postalCode) {
	this.postalCode = postalCode;
    }

    /**
     * @return transportnavn
     */
    public String getTransportName() {
	return transportName;
    }

    /**
     * @param transportName
     */
    public void setTransportName(String transportName) {
	this.transportName = transportName;
    }

    /**
     * @param costHeadings
     */
    public static void setCostHeadings(Set<String> costHeadings) {
	ReportDataTransport.costHeadings = costHeadings;
    }

    /**
     * @return kundenavn
     */
    public String getCustomerName() {
	return customerName;
    }

    /**
     * @param customerName
     */
    public void setCustomerName(String customerName) {
	this.customerName = customerName;
    }

    /**
     * @return true dersom etterlevering
     */
    public Boolean isPostShipment() {
	return isPostShipment;
    }

    /**
     * @param postShipment
     */
    public void setIsPostShipment(Boolean postShipment) {
	this.isPostShipment = postShipment;
    }

    public Integer getProductionWeek() {
	return productionWeek;
    }

    public void setProductionWeek(Integer productionWeek) {
	this.productionWeek = productionWeek;
    }

}