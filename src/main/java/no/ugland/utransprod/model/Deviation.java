package no.ugland.utransprod.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import no.ugland.utransprod.service.DeviationManager;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Klasse for tabell DEVIATION
 * 
 * @author atle.brekka
 */
public class Deviation extends AbstractTransportable implements Articleable, IAssembly {
    private static final int MAX_CACHED_COMMENT = 4000;

    private static final long serialVersionUID = 1L;

    private Integer deviationId;

    private Date registrationDate;

    private String userName;

    private String description;

    private JobFunction ownFunction;

    private JobFunction deviationFunction;

    private Integer customerNr;

    private String customerName;

    private String orderNr;

    private DeviationStatus deviationStatus;

    private Date dateClosed;

    private String productName;

    private FunctionCategory functionCategory;

    private PreventiveAction preventiveAction;

    private Set<OrderCost> orderCosts;

    private PostShipment postShipment;

    private Set<OrderComment> orderComments;

    private ProductArea productArea;

    private Order order;

    private Set<OrderLine> orderLines;

    private Integer doAssembly;

    private Assembly assembly;

    private String responsible;

    /**
     * Brukes til søk
     */
    private Date dateFrom;

    /**
     * Brukes til søk
     */
    private Date dateTo;

    private Integer checked;

    private String initiatedBy;

    private String lastChanged;

    private String projectNr;

    private String cachedComment;
    private Date procedureCheck;

    private String csId;

    public static final Deviation UNKNOWN = new Deviation() {
	private static final long serialVersionUID = 1L;
    };

    public Deviation() {
	super();
    }

    public Deviation(final Integer aDeviationId, final Date aRegistrationDate, final String aUserName, final String aDescription,
	    final JobFunction aOwnFunction, final JobFunction aDeviationFunction, final Integer aCustomerNr, final String aOrderNr,
	    final DeviationStatus aDeviationStatus, final Date aDateClosed, final String aProductName, final FunctionCategory aFunctionCategory,
	    final PreventiveAction aPreventiveAction, final String aCustomerName, final PostShipment aPostShipment,
	    final Set<OrderComment> aOrderComments, final ProductArea aProductArea, final Order aOrder, final Set<OrderLine> someOrderLines,
	    final Set<OrderCost> someOrderCosts, final Integer shouldDoAssembly, final Assembly aAssembly, final String aResponsible,
	    final Integer isChecked, final String isInitiatedBy, final String isLastChanged, final String aProjectNr, final Date aProcedureCheck) {
	super();
	this.deviationId = aDeviationId;
	this.registrationDate = aRegistrationDate;
	this.userName = aUserName;
	this.description = aDescription;
	this.ownFunction = aOwnFunction;
	this.deviationFunction = aDeviationFunction;
	this.customerNr = aCustomerNr;
	this.orderNr = aOrderNr;
	this.deviationStatus = aDeviationStatus;
	this.dateClosed = aDateClosed;
	this.productName = aProductName;
	this.functionCategory = aFunctionCategory;
	this.preventiveAction = aPreventiveAction;
	this.customerName = aCustomerName;
	this.postShipment = aPostShipment;
	this.orderComments = aOrderComments;
	this.productArea = aProductArea;
	this.order = aOrder;
	this.orderLines = someOrderLines;
	this.orderCosts = someOrderCosts;
	this.doAssembly = shouldDoAssembly;
	this.assembly = aAssembly;
	this.responsible = aResponsible;
	this.checked = isChecked;
	this.initiatedBy = isInitiatedBy;
	this.lastChanged = isLastChanged;
	this.projectNr = aProjectNr;
	this.procedureCheck = aProcedureCheck;
    }

    /**
     * @return kundenummer
     */
    public final Integer getCustomerNr() {
	return customerNr;
    }

    /**
     * @param aCustomerNr
     */
    public final void setCustomerNr(final Integer aCustomerNr) {
	this.customerNr = aCustomerNr;
    }

    /**
     * @return dato lukket
     */
    public final Date getDateClosed() {
	return dateClosed;
    }

    /**
     * @param aDateClosed
     */
    public final void setDateClosed(final Date aDateClosed) {
	this.dateClosed = aDateClosed;
    }

    /**
     * @return beskrivelse
     */
    public final String getDescription() {
	return description;
    }

    /**
     * @param aDescription
     */
    public final void setDescription(final String aDescription) {
	this.description = aDescription;
    }

    /**
     * @return avviksfunksjon
     */
    public final JobFunction getDeviationFunction() {
	return deviationFunction;
    }

    /**
     * @param aDeviationFunction
     */
    public final void setDeviationFunction(final JobFunction aDeviationFunction) {
	this.deviationFunction = aDeviationFunction;
    }

    /**
     * @return id
     */
    public final Integer getDeviationId() {
	return deviationId;
    }

    /**
     * @param aDeviationId
     */
    public final void setDeviationId(final Integer aDeviationId) {
	this.deviationId = aDeviationId;
    }

    /**
     * @return brukernavn
     */
    public final String getUserName() {
	return userName;
    }

    /**
     * @param aUserName
     */
    public final void setUserName(final String aUserName) {
	this.userName = aUserName;
    }

    /**
     * @return ordrenummer
     */
    public final String getOrderNr() {
	return orderNr;
    }

    /**
     * @param aOrderNr
     */
    public final void setOrderNr(final String aOrderNr) {
	this.orderNr = aOrderNr;
    }

    /**
     * @return bruker sin funksjon
     */
    public final JobFunction getOwnFunction() {
	return ownFunction;
    }

    /**
     * @param aOwnFunction
     */
    public final void setOwnFunction(final JobFunction aOwnFunction) {
	this.ownFunction = aOwnFunction;
    }

    /**
     * @return registreringsdato
     */
    public final Date getRegistrationDate() {
	return registrationDate;
    }

    /**
     * @param aRegistrationDate
     */
    public final void setRegistrationDate(final Date aRegistrationDate) {
	this.registrationDate = aRegistrationDate;
    }

    /**
     * @return status
     */
    public final DeviationStatus getDeviationStatus() {
	return deviationStatus;
    }

    /**
     * @param aDeviationStatus
     */
    public final void setDeviationStatus(final DeviationStatus aDeviationStatus) {
	this.deviationStatus = aDeviationStatus;
    }

    /**
     * @return kategori
     */
    public final FunctionCategory getFunctionCategory() {
	return functionCategory;
    }

    /**
     * @param aFunctionCategory
     */
    public final void setFunctionCategory(final FunctionCategory aFunctionCategory) {
	this.functionCategory = aFunctionCategory;
    }

    /**
     * @return prevantivt tiltak
     */
    public final PreventiveAction getPreventiveAction() {
	return preventiveAction;
    }

    /**
     * @param aPreventiveAction
     */
    public final void setPreventiveAction(final PreventiveAction aPreventiveAction) {
	this.preventiveAction = aPreventiveAction;
    }

    /**
     * @return produktområde
     */
    public final String getProductName() {
	return productName;
    }

    /**
     * @param aProductName
     */
    public final void setProductName(final String aProductName) {
	this.productName = aProductName;
    }

    /**
     * @return kundenavn
     */
    public final String getCustomerName() {
	return customerName;
    }

    /**
     * @param aCustomerName
     */
    public final void setCustomerName(final String aCustomerName) {
	this.customerName = aCustomerName;
    }

    /**
     * @return ettersending
     */
    public final PostShipment getPostShipment() {
	return postShipment;
    }

    /**
     * @param aPostShipment
     */
    public final void setPostShipment(final PostShipment aPostShipment) {
	this.postShipment = aPostShipment;
    }

    /**
     * @return kommentarer
     */
    public final Set<OrderComment> getOrderComments() {
	return orderComments;
    }

    /**
     * @param someOrderComments
     */
    public final void setOrderComments(final Set<OrderComment> someOrderComments) {
	this.orderComments = someOrderComments;
    }

    /**
     * @return kommentarer som en streng
     */
    public final String getDeviationCommentsString() {
	StringBuilder builder = new StringBuilder();
	if (orderComments != null) {
	    for (OrderComment comment : orderComments) {
		builder.append(comment.toString());
	    }
	}
	return builder.toString();
    }

    /**
     * @return produktområde
     */
    public final ProductArea getProductArea() {
	return productArea;
    }

    /**
     * @param aProductArea
     */
    public final void setProductArea(final ProductArea aProductArea) {
	this.productArea = aProductArea;
    }

    /**
     * @see no.ugland.utransprod.model.Articleable#getOrder()
     */
    public final Order getOrder() {
	return order;
    }

    /**
     * @param aOrder
     */
    public final void setOrder(final Order aOrder) {
	this.order = aOrder;
    }

    /**
     * @return ordrelinjer
     */
    public final Set<OrderLine> getOrderLines() {
	return orderLines;
    }

    /**
     * @see no.ugland.utransprod.model.IAssembly#getAssemblyOrderLines()
     */
    public final Set<OrderLine> getAssemblyOrderLines() {
	if (postShipment != null) {
	    return postShipment.getOrderLines();
	}
	return orderLines;
    }

    /**
     * @param someOrderLines
     */
    public final void setOrderLines(final Set<OrderLine> someOrderLines) {
	this.orderLines = someOrderLines;
    }

    /**
     * @see no.ugland.utransprod.model.BaseObject#toString()
     */
    @Override
    public final String toString() {
	return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append("deviationId", deviationId).append("registrationDate", registrationDate)
		.append("userName", userName).append("description", description).append("ownFunction", ownFunction)
		.append("deviationFunction", deviationFunction).append("customerNr", customerNr).append("customerName", customerName)
		.append("orderNr", orderNr).append("deviationStatus", deviationStatus).append("dateClosed", dateClosed)
		.append("productName", productName).append("functionCategory", functionCategory).append("preventiveAction", preventiveAction)
		.append("postShipment", postShipment).append("productArea", productArea).append("order", order).toString();
    }

    /**
     * @return kostnader
     */
    public final Set<OrderCost> getOrderCosts() {
	return orderCosts;
    }

    /**
     * @param someOrderCosts
     */
    public final void setOrderCosts(final Set<OrderCost> someOrderCosts) {
	this.orderCosts = someOrderCosts;
    }

    /**
     * @see no.ugland.utransprod.model.Articleable#getDeviation()
     */
    public final Deviation getDeviation() {
	return this;
    }

    /**
     * @return 'Ja' dersom har ettersending
     */
    public final String getIsPostShipment() {
	if (postShipment != null) {
	    return "Ja";
	}
	return "Nei";
    }

    /**
     * @return ordrelinjer
     */
    public final Set<OrderLine> getReportOrderLines() {
	if (postShipment != null) {
	    return postShipment.getOrderLines();
	}
	return getOrderLines();
    }

    /**
     * @param costUnitName
     * @return kostnad basert på enhetsnavn
     */
    public final BigDecimal getCost(final String costUnitName) {
	BigDecimal cost = BigDecimal.valueOf(0);
	if (orderCosts != null) {
	    for (OrderCost orderCost : orderCosts) {
		if (orderCost.getCostUnit().getCostUnitName().equalsIgnoreCase(costUnitName)) {
		    cost = cost.add(orderCost.getCostAmount());
		}
	    }
	}
	return cost;
    }

    /**
     * @see no.ugland.utransprod.gui.model.Transportable#getDoAssembly()
     */
    public final Integer getDoAssembly() {
	return doAssembly;
    }

    /**
     * @param shoulDoAssembly
     */
    public final void setDoAssembly(final Integer shoulDoAssembly) {
	this.doAssembly = shoulDoAssembly;
    }

    /**
     * @return montering
     */
    public final Assembly getAssembly() {
	return assembly;
    }

    /**
     * @see no.ugland.utransprod.model.IAssembly#setAssembly(no.ugland.utransprod.model.Assembly)
     */
    public final void setAssembly(final Assembly aAssembly) {
	this.assembly = aAssembly;
    }

    /**
     * @see no.ugland.utransprod.model.IAssembly#getAssemblyDeviation()
     */
    public final Deviation getAssemblyDeviation() {
	return this;
    }

    /**
     * @see no.ugland.utransprod.model.IAssembly#getAssemblyOrder()
     */
    public final Order getAssemblyOrder() {
	return null;
    }

    /**
     * @see no.ugland.utransprod.gui.model.Transportable#getCollies()
     */
    public final Set<Colli> getCollies() {
	if (postShipment != null) {
	    return postShipment.getCollies();
	}
	return null;
    }

    /**
     * @see no.ugland.utransprod.gui.model.Transportable#getComment()
     */
    public final String getComment() {
	return getCachedComment();
    }

    /**
     * @see no.ugland.utransprod.gui.model.Transportable#getConstructionType()
     */
    public final ConstructionType getConstructionType() {
	return null;
    }

    /**
     * @see no.ugland.utransprod.gui.model.Transportable#getGarageColliHeight()
     */
    public final Integer getGarageColliHeight() {
	if (postShipment != null) {
	    return postShipment.getGarageColliHeight();
	}
	return null;
    }

    /**
     * @see no.ugland.utransprod.gui.model.Transportable#getMissingCollies()
     */
    public final List<OrderLine> getMissingCollies() {
	if (postShipment != null) {
	    return postShipment.getMissingCollies();
	}
	return null;
    }

    /**
     * @see no.ugland.utransprod.gui.model.Transportable#getOrderComplete()
     */
    public final Date getOrderComplete() {
	if (postShipment != null) {
	    return postShipment.getOrderComplete();
	}
	return null;
    }

    /**
     * @see no.ugland.utransprod.gui.model.Transportable#getOrderLinesNotSent()
     */
    public final List<OrderLine> getOrderLinesNotSent() {
	if (postShipment != null) {
	    return postShipment.getOrderLinesNotSent();
	}
	return null;
    }

    /**
     * @see no.ugland.utransprod.gui.model.Transportable#getOrderReady()
     */
    public final Date getOrderReady() {
	if (postShipment != null) {
	    return postShipment.getOrderReady();
	}
	return null;
    }

    /**
     * @see no.ugland.utransprod.gui.model.Transportable#getPackageStarted()
     */
    public final Date getPackageStarted() {
	if (postShipment != null) {
	    return postShipment.getPackageStarted();
	}
	return null;
    }

    /**
     * @see no.ugland.utransprod.gui.model.Transportable#getPaidDate()
     */
    public final Date getPaidDate() {
	if (postShipment != null) {
	    return postShipment.getPaidDate();
	}
	return null;
    }

    /**
     * @see no.ugland.utransprod.gui.model.Transportable#getPostShipments()
     */
    public final Set<PostShipment> getPostShipments() {
	if (postShipment != null) {
	    Set<PostShipment> postShipments = new HashSet<PostShipment>();
	    postShipments.add(postShipment);
	    return postShipments;
	}

	return null;
    }

    /**
     * @see no.ugland.utransprod.gui.model.Transportable#getSent()
     */
    public final Date getSent() {
	if (postShipment != null) {
	    return postShipment.getSent();
	}
	return null;
    }

    /**
     * @see no.ugland.utransprod.gui.model.Transportable#getSentBool()
     */
    public final Boolean getSentBool() {
	if (postShipment != null) {
	    return postShipment.getSentBool();
	}
	return null;
    }

    /**
     * @see no.ugland.utransprod.gui.model.Transportable#getSpecialConcern()
     */
    public final String getSpecialConcern() {
	if (postShipment != null) {
	    return postShipment.getSpecialConcern();
	}
	return null;
    }

    /**
     * @see no.ugland.utransprod.gui.model.Transportable#getStatus()
     */
    public final String getStatus() {
	if (postShipment != null) {
	    return postShipment.getStatus();
	}
	return null;
    }

    /**
     * @see no.ugland.utransprod.gui.model.Transportable#getTransport()
     */
    public final Transport getTransport() {
	if (postShipment != null) {
	    return postShipment.getTransport();
	}
	return null;
    }

    /**
     * @see no.ugland.utransprod.gui.model.Transportable#getTransportComments()
     */
    public final String getTransportComments() {
	return getComment();
    }

    /**
     * @see no.ugland.utransprod.gui.model.Transportable#getTransportReportString()
     */
    public final String getTransportReportString() {
	if (postShipment != null) {
	    return postShipment.getTransportReportString();
	}
	return null;
    }

    /**
     * @see no.ugland.utransprod.gui.model.Transportable#getTransportString()
     */
    public final String getTransportString() {
	if (postShipment != null) {
	    return postShipment.getTransportString();
	}
	return getDeviationTransportString();
    }

    /**
     * @return transportinfo
     */
    public final String getDeviationTransportString() {
	StringBuffer stringBuffer = new StringBuffer();
	stringBuffer.append(customerNr + " " + customerName);

	stringBuffer.append(" - ").append(order.getOrderNr()).append("\n").append(order.getPostalCode()).append(" ").append(order.getPostOffice())
		.append(",").append(order.getConstructionTypeString()).append(",").append(order.getInfo());
	return stringBuffer.toString();
    }

    /**
     * @see no.ugland.utransprod.gui.model.Transportable#setSent(java.util.Date)
     */
    public final void setSent(final Date sentDate) {
	if (postShipment != null) {
	    postShipment.setSent(sentDate);
	}

    }

    /**
     * @see no.ugland.utransprod.gui.model.Transportable#setSentBool(java.lang.Boolean)
     */
    public final void setSentBool(final Boolean isSent) {
	if (postShipment != null) {
	    postShipment.setSentBool(isSent);
	}

    }

    /**
     * @see no.ugland.utransprod.gui.model.Transportable#setStatus(java.lang.String)
     */
    public final void setStatus(final String status) {
	if (postShipment != null) {
	    postShipment.setStatus(status);
	}

    }

    /**
     * @see no.ugland.utransprod.gui.model.Transportable#setTransport(no.ugland.utransprod.model.Transport)
     */
    public final void setTransport(final Transport transport) {
	if (postShipment != null) {
	    postShipment.setTransport(transport);
	}

    }

    /**
     * @see no.ugland.utransprod.model.BaseObject#equals(java.lang.Object)
     */
    @Override
    public final boolean equals(final Object other) {
	if (!(other instanceof Deviation)) {
	    return false;
	}
	Deviation castOther = (Deviation) other;
	return new EqualsBuilder().append(deviationId, castOther.deviationId).append(registrationDate, castOther.registrationDate)
		.append(userName, castOther.userName).append(description, castOther.description).append(ownFunction, castOther.ownFunction)
		.append(deviationFunction, castOther.deviationFunction).append(customerNr, castOther.customerNr)
		.append(customerName, castOther.customerName).append(orderNr, castOther.orderNr).append(deviationStatus, castOther.deviationStatus)
		.append(dateClosed, castOther.dateClosed).append(productName, castOther.productName)
		.append(functionCategory, castOther.functionCategory).append(preventiveAction, castOther.preventiveAction)
		.append(postShipment, castOther.postShipment).append(productArea, castOther.productArea).append(order, castOther.order)
		.append(doAssembly, castOther.doAssembly).append(assembly, castOther.assembly).isEquals();
    }

    /**
     * @see no.ugland.utransprod.model.BaseObject#hashCode()
     */
    @Override
    public final int hashCode() {
	return new HashCodeBuilder().append(deviationId).append(registrationDate).append(userName).append(description).append(ownFunction)
		.append(deviationFunction).append(customerNr).append(customerName).append(orderNr).append(deviationStatus).append(dateClosed)
		.append(productName).append(functionCategory).append(preventiveAction).append(postShipment).append(productArea).append(order)
		.append(doAssembly).append(assembly).toHashCode();
    }

    /**
     * @return behandlingsansvarlig
     */
    public final String getResponsible() {
	return responsible;
    }

    /**
     * @param aResponsible
     */
    public final void setResponsible(final String aResponsible) {
	this.responsible = aResponsible;
    }

    /**
     * @return fra dato
     */
    public final Date getDateFrom() {
	return dateFrom;
    }

    /**
     * @param aDateFrom
     */
    public final void setDateFrom(final Date aDateFrom) {
	this.dateFrom = aDateFrom;
    }

    /**
     * @return til dato
     */
    public final Date getDateTo() {
	return dateTo;
    }

    /**
     * @param aDateTo
     */
    public final void setDateTo(final Date aDateTo) {
	this.dateTo = aDateTo;
    }

    /**
     * @return kontrollert
     */
    public final Integer getChecked() {
	return checked;
    }

    /**
     * @param isChecked
     */
    public final void setChecked(final Integer isChecked) {
	this.checked = isChecked;
    }

    /**
     * @return initiert av
     */
    public final String getInitiatedBy() {
	return initiatedBy;
    }

    /**
     * @param isInitiatedBy
     */
    public final void setInitiatedBy(final String isInitiatedBy) {
	this.initiatedBy = isInitiatedBy;
    }

    /**
     * @see no.ugland.utransprod.gui.model.Transportable#cacheComments()
     */
    public final void cacheComments() {
	StringBuilder builder = new StringBuilder();
	if (orderComments != null) {
	    for (OrderComment orderComment : orderComments) {
		builder.append(orderComment.getComment()).append(",");
	    }
	}
	if (builder.length() > MAX_CACHED_COMMENT) {
	    setCachedComment("kommentar for lang...");
	} else {
	    setCachedComment(builder.toString());
	}

    }

    /**
     * @see no.ugland.utransprod.gui.model.Transportable#cacheGarageColliHeight()
     */
    public final void cacheGarageColliHeight() {
    }

    /**
     * @see no.ugland.utransprod.gui.model.Transportable#getAssemblyTeamName()
     */
    public final String getAssemblyTeamName() {
	if (assembly != null) {
	    return assembly.getSupplier().getSupplierName();
	}
	return null;
    }

    /**
     * @see no.ugland.utransprod.gui.model.Transportable#getPacklistReady()
     */
    public final Date getPacklistReady() {
	return null;
    }

    /**
     * @see no.ugland.utransprod.gui.model.Transportable#getProductAreaGroup()
     */
    public final ProductAreaGroup getProductAreaGroup() {
	if (order != null) {
	    return order.getProductArea().getProductAreaGroup();
	}
	return null;
    }

    /**
     * @return sist endret
     */
    public final String getLastChanged() {
	return lastChanged;
    }

    /**
     * @param isLastChanged
     */
    public final void setLastChanged(final String isLastChanged) {
	this.lastChanged = isLastChanged;
    }

    public final String getProjectNr() {
	return projectNr;
    }

    public final void setProjectNr(final String aProjectNr) {
	this.projectNr = aProjectNr;
    }

    public final OrderLine getOrderLine(final String articlePath) {
	if (orderLines != null) {
	    for (OrderLine orderLine : orderLines) {
		if (orderLine.getArticlePath().equalsIgnoreCase(articlePath)) {
		    return orderLine;
		}
	    }
	}
	return null;
    }

    public final List<OrderLine> getOrderLineList(final String articlePath) {
	List<OrderLine> orderLineList = new ArrayList<OrderLine>();
	if (orderLines != null) {
	    for (OrderLine orderLine : orderLines) {
		if (orderLine.getArticlePath().equalsIgnoreCase(articlePath)) {
		    orderLineList.add(orderLine);
		}
	    }
	}
	Collections.sort(orderLineList, new OrderLine.OrderLineNumberComparator());
	return orderLineList;
    }

    public final boolean hasTransportCostBasis() {
	return false;
    }

    public final void removeOrderCost(final OrderCost orderCost) {
	if (orderCosts != null) {
	    orderCosts.remove(orderCost);
	    orderCost.setDeviation(null);
	}
    }

    public final Customer getCustomer() {
	if (order != null) {
	    return order.getCustomer();
	} else if (postShipment != null) {
	    return postShipment.getCustomer();
	}
	return null;
    }

    /**
     * @return cached kommentarer
     */
    public final String getCachedComment() {
	return cachedComment;
    }

    /**
     * @param aCachedComment
     */
    public final void setCachedComment(final String aCachedComment) {
	this.cachedComment = aCachedComment;
    }

    public Date getProductionDate() {
	if (order != null) {
	    return order.getProductionDate();
	}
	return null;
    }

    public ProcentDone getLastProcentDone() {
	return null;
    }

    public String getPackedBy() {
	return null;
    }

    public void setGarageColliHeight(Integer garageColliHeight) {
    }

    public void setOrderComplete(Date date) {
    }

    public void setOrderReady(Date orderReady) {
    }

    public void setPackedBy(String packedBy) {
    }

    public Integer getColliesDone() {
	return null;
    }

    public Boolean isDonePackage() {
	return null;
    }

    public void setColliesDone(Integer done) {
    }

    public void setPackageStarted(Date date) {
    }

    public final Integer getTakstolHeight() {
	if (postShipment != null) {
	    return postShipment.getTakstolHeight();
	}
	return null;
    }

    public String getManagerName() {
	return DeviationManager.MANAGER_NAME;
    }

    public void cacheTakstolHeight() {
    }

    public Date getProcedureCheck() {
	return procedureCheck;
    }

    public void setProcedureCheck(Date procedureCheck) {
	this.procedureCheck = procedureCheck;
    }

    public Integer getProbability() {
	return null;
    }

    public String getTrossDrawer() {
	return order != null ? order.getTrossDrawer() : null;
    }

    public Integer getMaxTrossHeight() {
	return null;
    }

    public Integer getProductionWeek() {
	if (order != null) {
	    return order.getProductionWeek();
	}
	return null;
    }

    public Date getOrderReadyWall() {
	if (postShipment != null) {
	    return postShipment.getOrderReadyWall();
	}
	return null;
    }

    public void setOrderReadyWall(Date orderReady) {
    }

    public Date getOrderReadyTross() {
	if (postShipment != null) {
	    return postShipment.getOrderReadyTross();
	}
	return null;
    }

    public void setOrderReadyTross(Date orderReady) {
    }

    public String getPackedByTross() {
	// TODO Auto-generated method stub
	return null;
    }

    public void setPackedByTross(String packedBy) {
	// TODO Auto-generated method stub

    }

    public Date getOrderReadyPack() {
	// TODO Auto-generated method stub
	return null;
    }

    public void setOrderReadyPack(Date orderReady) {
    }

    public String getPackedByPack() {
	// TODO Auto-generated method stub
	return null;
    }

    public void setPackedByPack(String packedBy) {
	// TODO Auto-generated method stub

    }

    public String getCsId() {
	return csId;
    }

    public void setCsId(String csId) {
	this.csId = csId;
    }
}
