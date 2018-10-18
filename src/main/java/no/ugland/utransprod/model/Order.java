package no.ugland.utransprod.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import no.ugland.utransprod.gui.model.Packable;
import no.ugland.utransprod.gui.model.TextRenderable;
import no.ugland.utransprod.gui.model.TransportListable;
import no.ugland.utransprod.gui.model.Transportable;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.util.CommentTypeUtil;
import no.ugland.utransprod.util.Util;
import no.ugland.utransprod.util.YearWeek;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.google.common.collect.Lists;

/**
 * Klasse som representerer tabell ORDER.
 * 
 * @author atle.brekka
 */
public class Order extends AbstractTransportable
		implements TextRenderable, Articleable, IAssembly, TransportListable, Packable {
	private static final long serialVersionUID = 1L;

	private static final int MAX_CACHED_COMMENT = 4000;

	private Integer orderId;

	private String orderNr;

	private String deliveryAddress;

	private String postalCode;

	private String postOffice;

	private Customer customer;

	private ConstructionType constructionType;

	private Transport transport;

	private Integer doAssembly;

	private Date orderDate;

	private Set<OrderLine> orderLines;

	private Date changeDate;

	private Set<OrderCost> orderCosts;

	private Date invoiceDate;

	private Date sent;

	private Assembly assembly;

	private String info;

	private Set<Colli> collies;

	private String status;

	private Integer colliesDone;

	private Date orderReady;

	private Date orderComplete;

	private Date packageStarted;

	private Date agreementDate;

	private String specialConcern;

	private Set<PostShipment> postShipments;

	private Integer deliveryWeek;

	private String telephoneNr;

	private List<OfferAddress> offerAddresses;

	private Date packlistReady;

	private Set<ExternalOrder> externalOrders;

	private String packedBy;
	private String packedByTross;

	private String salesman;

	private Date paidDate;

	private Set<OrderComment> orderComments;

	private String cachedComment;

	private Integer garageColliHeight;

	private Integer hasMissingCollies;

	private ProductArea productArea;

	private Set<Deviation> deviations;

	private Date registrationDate;

	private Date productionDate;

	private Set<ProcentDone> procentDones;

	private ProcentDone lastProcentDone;

	private boolean lastProcentDoneFetched = false;
	private Integer takstolHeight;

	/**
	 * Brukes til info fra Visma
	 */

	private Cutting cutting;

	private Integer probability;

	private String telephoneNrSite;

	private BigDecimal ownProductionTakstol;

	private String trossDrawer;

	private Date trossReady;

	private Date trossStartDate;

	private Integer defaultColliesGenerated;

	private Integer maxTrossHeight;

	private Integer productionBasis;

	private Integer productionWeek;

	private BigDecimal packlistDuration;

	private String packlistDoneBy;

	private Date orderReadyWall;

	private Date orderReadyTross;

	private Date orderReadyPack;

	private String packedByPack;

	private BigDecimal estimatedTimeWall;

	private BigDecimal estimatedTimeGavl;

	private BigDecimal estimatedTimePack;

	private String receivedTrossDrawing;

	public static final Order UNKNOWN = new Order() {
		private static final long serialVersionUID = 1L;
	};

	private static final String MAX_HEIGTH_ATTRIBUTE = "Makshøyde";

	private static final String COST_TYPE_NAME_TAKSTOLER = "Takstoler";

	private static final String COST_UNIT_INTERNAL = "Intern";

	private String antallStandardvegger;
	private Date sentMail;

	private Date levert;

	public Order() {

	}

	/**
	 * @param orderId
	 */
	public Order(Integer orderId) {
		this.orderId = orderId;
	}

	/**
	 * @param orderNr
	 * @param deliveryAddress
	 * @param postalCode
	 * @param postOffice
	 * @param customer
	 * @param constructionType
	 * @param orderDate
	 * @param productArea
	 */
	public Order(String orderNr, String deliveryAddress, String postalCode, String postOffice, Customer customer,
			ConstructionType constructionType, Date orderDate, ProductArea productArea) {
		this(null, orderNr, deliveryAddress, postalCode, postOffice,

				customer, constructionType, null, null, orderDate, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
				productArea, null, null, null, null, null);
	}

	public Order(Integer orderId, String orderNr, String deliveryAddress, String postalCode, String postOffice,
			Customer customer, ConstructionType constructionType, Transport transport, Integer doAssembly,
			Date orderDate, Set<OrderLine> orderLines, Set<OrderCost> orderCosts, Date invoiceDate, Date sent,
			Assembly assembly, String info, Set<Colli> collies, String status, Integer colliesDone, Date orderReady,
			Date orderComplete, Date packageStarted, Date agreementDate, String specialConcern,
			Set<PostShipment> postShipments, Integer deliveryWeek, String telephoneNr, Date packlistReady,
			Set<ExternalOrder> externalOrders, String packedBy, String salesman, Date paidDate,
			Set<OrderComment> orderComments, String cachedComment, Integer garageColliHeight, Integer hasMissingCollies,
			ProductArea productArea, final Set<Deviation> someDeviations, final Date aRegistrationDate,
			final Date aProductionDate, final Set<ProcentDone> someProcentDones, final Integer aTakstolHeight) {
		super();
		this.orderId = orderId;
		this.orderNr = orderNr;
		this.deliveryAddress = deliveryAddress;
		this.postalCode = postalCode;
		this.postOffice = postOffice;
		this.customer = customer;
		this.constructionType = constructionType;
		this.transport = transport;
		this.doAssembly = doAssembly;
		this.orderDate = Util.cloneDate(orderDate);
		this.orderLines = orderLines;
		this.orderCosts = orderCosts;
		this.invoiceDate = Util.cloneDate(invoiceDate);
		this.sent = Util.cloneDate(sent);
		this.assembly = assembly;
		this.info = info;
		this.collies = collies;
		this.status = status;
		this.colliesDone = colliesDone;
		this.orderReady = Util.cloneDate(orderReady);
		this.orderComplete = Util.cloneDate(orderComplete);
		this.packageStarted = Util.cloneDate(packageStarted);
		this.agreementDate = Util.cloneDate(agreementDate);
		this.specialConcern = specialConcern;
		this.postShipments = postShipments;
		this.deliveryWeek = deliveryWeek;
		this.telephoneNr = telephoneNr;
		this.packlistReady = Util.cloneDate(packlistReady);
		this.externalOrders = externalOrders;
		this.packedBy = packedBy;
		this.salesman = salesman;
		this.paidDate = paidDate;
		this.orderComments = orderComments;
		this.cachedComment = cachedComment;
		this.garageColliHeight = garageColliHeight;
		this.hasMissingCollies = hasMissingCollies;
		this.productArea = productArea;
		this.deviations = someDeviations;
		this.registrationDate = aRegistrationDate;
		this.productionDate = aProductionDate;
		this.procentDones = someProcentDones;
		this.takstolHeight = aTakstolHeight;
	}

	public Date getSentMail() {
		return sentMail;
	}

	public void setSentMail(Date sentMail) {
		this.sentMail = sentMail;
	}

	/**
	 * @return garasjetype
	 */
	public ConstructionType getConstructionType() {
		return constructionType;
	}

	/**
	 * @param constructionType
	 */
	public void setConstructionType(ConstructionType constructionType) {
		this.constructionType = constructionType;
	}

	/**
	 * @return kunde
	 */
	public Customer getCustomer() {
		return customer;
	}

	/**
	 * @param customer
	 */
	public void setCustomer(Customer customer) {
		this.customer = customer;
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
	 * @return id
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
	 * @return nr
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
	 * @return poststed
	 */
	public String getPostOffice() {
		return postOffice;
	}

	/**
	 * @param postOffice
	 */
	public void setPostOffice(String postOffice) {
		this.postOffice = postOffice;
	}

	/**
	 * @return transport
	 */
	public Transport getTransport() {
		return transport;
	}

	/**
	 * @param transport
	 */
	public void setTransport(Transport transport) {
		this.transport = transport;
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof Order))
			return false;
		Order castOther = (Order) other;
		return new EqualsBuilder().append(orderNr, castOther.orderNr).isEquals();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(orderNr).toHashCode();
	}

	/**
	 * @return summering av ordre
	 */
	public String getOrderString() {
		String returnString = "";
		if (customer != null) {
			returnString = customer.toString() + " - " + orderNr + " - " + postalCode + " "
					+ (postOffice != null ? postOffice : "") + " " + constructionType.toString();
		}
		return returnString;
	}

	/**
	 * @return ordreinfo
	 */
	public String getOrderStringShort() {
		if (customer != null) {
			return String.format("%1$-30.30s", customer.toString()) + " - " + orderNr + " - " + postalCode + " "
					+ postOffice + " " + constructionType.toString();
		}
		return "";
	}

	/**
	 * @return summering av kundeinfo
	 */
	public String getCustomerString() {
		if (customer != null) {
			return customer.toString() + " " + deliveryAddress + " " + postalCode + " " + postOffice;
		}
		return "";
	}

	/**
	 * @return garasjetype
	 */
	public String getConstructionTypeString() {
		if (constructionType != null) {
			return constructionType.toString();
		}
		return "";
	}

	/**
	 * @see no.ugland.utransprod.gui.model.Transportable#getTransportString()
	 */
	public String getTransportString() {
		StringBuffer stringBuffer = new StringBuffer();
		if (customer != null) {
			stringBuffer.append(customer.toString());
		}
		stringBuffer.append(" - ").append(orderNr).append("\n").append(postalCode).append(" ").append(postOffice)
				.append(",").append(getConstructionTypeString()).append(",").append(info);

		return stringBuffer.toString();
	}

	/**
	 * @return transportdetaljer
	 */
	public String getTransportDetails() {
		if (transport != null) {
			return transport.toString();
		}
		return "";
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#toString()
	 */
	@Override
	public String toString() {
		if (customer != null) {
			return getOrderString();
		}
		return "";
	}

	/**
	 * Lager toString av ordre
	 * 
	 * @return streng
	 */
	public String orderLinesToString() {
		String lenght = null;
		String width = null;

		if (orderLines != null) {
			for (OrderLine orderLine : orderLines) {
				if (lenght != null && width != null) {
					break;
				}
				if (orderLine.getArticleType() == null && orderLine.getConstructionTypeArticle() == null) {
					Set<OrderLineAttribute> attributes = orderLine.getOrderLineAttributes();
					if (attributes != null) {
						for (OrderLineAttribute attribute : attributes) {
							if (attribute.getAttributeName().equalsIgnoreCase("Lengde")) {
								lenght = attribute.getAttributeValue();
								if (width != null) {
									break;
								}
							} else if (attribute.getAttributeName().equalsIgnoreCase("Bredde")) {
								width = attribute.getAttributeValue();
								if (lenght != null) {
									break;
								}
							}
						}
					}
				}
			}
		}
		if (lenght != null && width != null) {
			return width + "x" + lenght;
		}
		return "";
	}

	/**
	 * @return montering
	 */
	public Integer getDoAssembly() {
		return doAssembly;
	}

	/**
	 * @param doAssembly
	 */
	public void setDoAssembly(Integer doAssembly) {
		this.doAssembly = doAssembly;
	}

	/**
	 * @return monteringer
	 */
	public Assembly getAssembly() {
		return assembly;
	}

	/**
	 * @param assembly
	 */
	public void setAssembly(Assembly assembly) {
		this.assembly = assembly;
	}

	/**
	 * @return ordredato
	 */
	public Date getOrderDate() {
		return Util.cloneDate(orderDate);
	}

	/**
	 * @param orderDate
	 */
	public void setOrderDate(Date orderDate) {
		this.orderDate = Util.getShortDate(orderDate);
	}

	/**
	 * @return ordrelinjer
	 */
	public Set<OrderLine> getOrderLines() {
		return orderLines;
	}

	/**
	 * @see no.ugland.utransprod.model.IAssembly#getAssemblyOrderLines()
	 */
	public Set<OrderLine> getAssemblyOrderLines() {
		return getOrderLines();
	}

	/**
	 * @param orderLines
	 */
	public void setOrderLines(Set<OrderLine> orderLines) {
		this.orderLines = orderLines;
	}

	/**
	 * @return artikler
	 */
	public List<ArticleType> getArticles() {
		ArrayList<ArticleType> articleTypes = new ArrayList<ArticleType>();
		if (orderLines != null) {
			for (OrderLine orderLine : orderLines) {
				if (orderLine.getArticleType() != null) {
					articleTypes.add(orderLine.getArticleType());
				} else if (orderLine.getConstructionTypeArticle() != null) {
					articleTypes.add(orderLine.getConstructionTypeArticle().getArticleType());
				}
			}
		}
		return articleTypes;
	}

	/**
	 * @return oppdateringsdato
	 */
	public Date getChangeDate() {
		return Util.cloneDate(changeDate);
	}

	/**
	 * @param changeDate
	 */
	public void setChangeDate(Date changeDate) {
		this.changeDate = Util.cloneDate(changeDate);
	}

	/**
	 * @return kostnader
	 */
	public Set<OrderCost> getOrderCosts() {
		return orderCosts;
	}

	/**
	 * @param orderCosts
	 */
	public void setOrderCosts(Set<OrderCost> orderCosts) {
		this.orderCosts = orderCosts;
	}

	/**
	 * @return fakturadato
	 */
	public Date getInvoiceDate() {
		return Util.cloneDate(invoiceDate);
	}

	/**
	 * @param invoiceDate
	 */
	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = Util.cloneDate(invoiceDate);
	}

	/**
	 * @see no.ugland.utransprod.gui.model.Transportable#getSent()
	 */
	public Date getSent() {
		return Util.cloneDate(sent);
	}

	/**
	 * @see no.ugland.utransprod.gui.model.Transportable#setSent(java.util.Date)
	 */
	public void setSent(Date sent) {
		this.sent = Util.cloneDate(sent);
	}

	/**
	 * @return info
	 */
	public String getInfo() {
		return info;
	}

	/**
	 * @param info
	 */
	public void setInfo(String info) {
		this.info = info;
	}

	/**
	 * @see no.ugland.utransprod.gui.model.Transportable#getCollies()
	 */
	public Set<Colli> getCollies() {
		return collies;
	}

	/**
	 * @param collies
	 */
	public void setCollies(Set<Colli> collies) {
		this.collies = collies;
	}

	/**
	 * @return true dersom pakking er ferdig
	 */
	public Boolean isDonePackage() {
		if (orderLines != null) {
			for (OrderLine orderLine : orderLines) {
				if (orderLine.hasTopLevelArticle() && orderLine.getColli() == null && orderLine.hasArticle()
						&& orderLine.getPostShipment() == null) {
					return Boolean.FALSE;
				}
			}
		}
		return Boolean.TRUE;
	}

	/**
	 * @see no.ugland.utransprod.gui.model.Transportable#getSentBool()
	 */
	public Boolean getSentBool() {
		return sent != null;
	}

	/**
	 * @see no.ugland.utransprod.gui.model.Transportable#setSentBool(java.lang.Boolean)
	 */
	public void setSentBool(Boolean isSent) {
		if (isSent) {
			sent = Calendar.getInstance().getTime();
		} else {
			sent = null;
		}
	}

	/**
	 * @see no.ugland.utransprod.gui.model.Transportable#getStatus()
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @see no.ugland.utransprod.gui.model.Transportable#setStatus(java.lang.String)
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return 1 dersom kollier er ferdige
	 */
	public Integer getColliesDone() {
		return colliesDone;
	}

	/**
	 * @param colliesDone
	 */
	public void setColliesDone(Integer colliesDone) {
		this.colliesDone = colliesDone;
	}

	/**
	 * @see no.ugland.utransprod.gui.model.Transportable#getOrderComplete()
	 */
	public Date getOrderComplete() {
		return Util.cloneDate(orderComplete);
	}

	/**
	 * @param orderComplete
	 */
	public void setOrderComplete(Date orderComplete) {
		this.orderComplete = Util.cloneDate(orderComplete);
	}

	/**
	 * @see no.ugland.utransprod.gui.model.Transportable#getOrderReady()
	 */
	public Date getOrderReady() {
		return Util.cloneDate(orderReady);
	}

	/**
	 * @param orderReady
	 */
	public void setOrderReady(Date orderReady) {
		this.orderReady = Util.cloneDate(orderReady);
	}

	/**
	 * @see no.ugland.utransprod.gui.model.Transportable#getPackageStarted()
	 */
	public Date getPackageStarted() {
		return Util.cloneDate(packageStarted);
	}

	/**
	 * @param packageStarted
	 */
	public void setPackageStarted(Date packageStarted) {
		this.packageStarted = Util.cloneDate(packageStarted);
	}

	/**
	 * @param colli
	 * @return true dersom kolli ble fjernet
	 */
	public boolean removeColli(Colli colli) {
		if (collies != null) {
			return collies.remove(colli);
		}
		return false;
	}

	public boolean removeOrderCost(OrderCost orderCost) {
		if (orderCosts != null) {
			return orderCosts.remove(orderCost);
		}
		return false;
	}

	/**
	 * @see no.ugland.utransprod.gui.model.Transportable#getOrderLinesNotSent()
	 */
	public List<OrderLine> getOrderLinesNotSent() {
		ArrayList<OrderLine> linesNotSent = new ArrayList<OrderLine>();
		if (orderLines != null) {
			for (OrderLine line : orderLines) {
				if (line.hasTopLevelArticle() && line.hasArticle()
						&& (line.getColli() == null || line.getColli().getSent() == null)) {
					linesNotSent.add(line);
				}
			}
		}
		return linesNotSent;
	}

	/**
	 * @return avropsdato
	 */
	public Date getAgreementDate() {
		return Util.cloneDate(agreementDate);
	}

	/**
	 * @param agreementDate
	 */
	public void setAgreementDate(Date agreementDate) {
		this.agreementDate = Util.cloneDate(agreementDate);
	}

	/**
	 * @return info om spesielle hensyn
	 */
	public String getOrderLineSpecialConcerns() {
		StringBuffer buffer = new StringBuffer();
		if (orderLines != null) {
			for (OrderLine orderLine : orderLines) {
				buffer.append(orderLine.getSpecialConcerns());
				buffer.append("-");
			}
		}
		String returnString = buffer.toString();
		returnString = returnString.replaceAll("--", "");
		if (returnString.length() != 0 && !returnString.trim().equalsIgnoreCase("-")) {
			return returnString;
		}
		return null;
	}

	/**
	 * @see no.ugland.utransprod.gui.model.Transportable#getSpecialConcern()
	 */
	public String getSpecialConcern() {
		return specialConcern;
	}

	/**
	 * @param specialConcern
	 */
	public void setSpecialConcern(String specialConcern) {
		this.specialConcern = specialConcern;
	}

	/**
	 * @param orderLine
	 */
	public void addOrderLine(OrderLine orderLine) {
		if (orderLine != null) {
			if (orderLines == null) {
				orderLines = new HashSet<OrderLine>();
			}
			orderLine.setOrder(this);
			orderLines.add(orderLine);
		}
	}

	/**
	 * @see no.ugland.utransprod.gui.model.Transportable#getOrder()
	 */
	public Order getOrder() {
		return this;
	}

	/**
	 * @see no.ugland.utransprod.gui.model.Transportable#getPostShipments()
	 */
	public Set<PostShipment> getPostShipments() {
		return postShipments;
	}

	/**
	 * @param postShipments
	 */
	public void setPostShipments(Set<PostShipment> postShipments) {
		this.postShipments = postShipments;
	}

	/**
	 * @see no.ugland.utransprod.gui.model.Transportable#getPostShipment()
	 */
	public PostShipment getPostShipment() {
		return null;
	}

	/**
	 * @return ønsket leveringsuke
	 */
	public Integer getDeliveryWeek() {
		return deliveryWeek;
	}

	/**
	 * @param deliveryWeek
	 */
	public void setDeliveryWeek(Integer deliveryWeek) {
		this.deliveryWeek = deliveryWeek;
	}

	/**
	 * @return telefonnummer
	 */
	public String getTelephoneNr() {
		return telephoneNr;
	}

	/**
	 * @return formatert telefonnumre
	 */
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

	/**
	 * @param telephoneNr
	 */
	public void setTelephoneNr(String telephoneNr) {
		this.telephoneNr = telephoneNr;
	}

	/**
	 * @return adresse i superoffice
	 */
	public List<OfferAddress> getOfferAddresses() {
		return offerAddresses;
	}

	/**
	 * @param offerAddresses
	 */
	public void setOfferAddresses(List<OfferAddress> offerAddresses) {
		this.offerAddresses = offerAddresses;
	}

	/**
	 * @see no.ugland.utransprod.gui.model.Transportable#getMissingCollies()
	 */
	public List<OrderLine> getMissingCollies() {
		ArrayList<OrderLine> linesNotSent = new ArrayList<OrderLine>();
		if (orderLines != null) {
			for (OrderLine line : orderLines) {
				if (line.getPostShipment()==null&&(line.hasTopLevelArticle() && line.hasArticle() && line.getColli() == null)){
//						|| (line.getPostShipment() != null) && line.getPostShipment().getSent() == null)) {
					linesNotSent.add(line);
				}
			}
		}
		return linesNotSent;
	}

	/**
	 * @return true dersom garasje skal monteres
	 */
	public boolean doAssembly() {
		if (doAssembly != null && doAssembly == 1) {
			return true;
		}
		return false;
	}

	/**
	 * Fjerner orderlinje
	 * 
	 * @param orderLine
	 */
	public void removeOrderLine(OrderLine orderLine) {
		orderLines.remove(orderLine);
		orderLine.setOrder(null);
	}

	/**
	 * @return pakkliste klar
	 */
	public Date getPacklistReady() {
		return Util.cloneDate(packlistReady);
	}

	/**
	 * @param packlistReady
	 */
	public void setPacklistReady(Date packlistReady) {
		this.packlistReady = Util.cloneDate(packlistReady);
	}

	/**
	 * @return eksterne ordre
	 */
	public Set<ExternalOrder> getExternalOrders() {
		return externalOrders;
	}

	/**
	 * @param externalOrders
	 */
	public void setExternalOrders(Set<ExternalOrder> externalOrders) {
		this.externalOrders = externalOrders;
	}

	/**
	 * @return pakket av
	 */
	public String getPackedBy() {
		return packedBy;
	}

	/**
	 * @param packedBy
	 */
	public void setPackedBy(String packedBy) {
		this.packedBy = packedBy;
	}

	/**
	 * @see no.ugland.utransprod.gui.model.Transportable#getTransportReportString()
	 */
	public String getTransportReportString() {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(postalCode).append(" ").append(postOffice);
		if (customer != null) {
			stringBuffer.append(" - ").append(customer.getFullName());
		}
		stringBuffer.append(" - ").append(orderNr).append(",").append(getConstructionTypeString()).append(",")
				.append(info);

		return stringBuffer.toString();
	}

	/**
	 * @return antall kollier
	 */
	public Integer getNumberOfCollies() {
		if (collies != null) {
			return collies.size();
		}
		return 0;
	}

	public String getSalesman() {
		return salesman;
	}

	/**
	 * @param salesman
	 */
	public void setSalesman(String salesman) {
		this.salesman = salesman;
	}

	public BigDecimal getCost(String costTypeName, String costUnitName) {
		if (orderCosts != null) {
			for (OrderCost cost : orderCosts) {
				if (cost.getCostUnit() != null) {
					String orderCostTypeName = cost.getCostType().getCostTypeName();
					String orderCostUnitName = cost.getCostUnit().getCostUnitName();
					if (orderCostTypeName.equalsIgnoreCase(costTypeName)
							&& orderCostUnitName.equalsIgnoreCase(costUnitName)) {
						return cost.getCostAmount();
					}
				}
			}
		}
		return BigDecimal.ZERO;
	}

	public final BigDecimal getAssemblyCost() {
		BigDecimal cost = getCost("Montering", "Kunde");
		if (cost == null) {
			cost = BigDecimal.valueOf(0);
		}
		return cost;
	}

	public final BigDecimal getCraningCost() {
		BigDecimal cost = getCost("Kraning", "Kunde");
		if (cost == null) {
			cost = BigDecimal.valueOf(0);
		}
		return cost;
	}

	public final BigDecimal getJaLinjer() {
		BigDecimal cost = getCost("Jalinjer", "Kunde");
		if (cost == null) {
			cost = BigDecimal.valueOf(0);
		}
		return cost;
	}

	/**
	 * @return garasjeverdi
	 */
	public final BigDecimal getGarageValue() {
		BigDecimal cost = getCost("Egenproduksjon", "Kunde");
		if (cost == null) {
			cost = BigDecimal.valueOf(0);
		}
		return cost;
	}

	/**
	 * @return fraktverdi
	 */
	public BigDecimal getTransportValue() {
		BigDecimal cost = getCost("Frakt", "Kunde");
		if (cost == null) {
			cost = BigDecimal.valueOf(0);
		}
		return cost;
	}

	/**
	 * @return monteringsverdi
	 */
	public BigDecimal getAssemblyValue() {
		return getCost("Montering", "Kunde");
	}

	/**
	 * @return transportuke formatert
	 */
	public String getTransportWeekString() {
		if (transport != null) {
			return transport.getFormatedYearWeek();
		}
		return null;
	}

	/**
	 * @see no.ugland.utransprod.gui.model.Transportable#getPaidDate()
	 */
	public Date getPaidDate() {
		return paidDate;
	}

	/**
	 * @param paidDate
	 */
	public void setPaidDate(Date paidDate) {
		this.paidDate = paidDate;
	}

	/**
	 * @see no.ugland.utransprod.model.Articleable#getDeviation()
	 */
	public Deviation getDeviation() {
		return null;
	}

	/**
	 * @return kommentarer
	 */
	public Set<OrderComment> getOrderComments() {
		return orderComments;
	}

	/**
	 * @param orderComments
	 */
	public void setOrderComments(Set<OrderComment> orderComments) {
		this.orderComments = orderComments;
	}

	/**
	 * @see no.ugland.utransprod.gui.model.Transportable#getComment()
	 */
	public String getComment() {
		return getCachedComment();
	}

	/**
	 * @param orderComment
	 */
	public void addOrderComment(OrderComment orderComment) {
		if (orderComments == null) {
			orderComments = new HashSet<OrderComment>();
		}
		orderComment.setOrder(this);
		orderComments.add(orderComment);
	}

	/**
	 * @see no.ugland.utransprod.gui.model.Transportable#getTransportComments()
	 */
	public String getTransportComments() {
		StringBuilder builder = new StringBuilder();
		if (orderComments != null) {
			for (OrderComment comment : orderComments) {
				if (CommentTypeUtil.hasCommentType(comment.getCommentTypes(), "Transport")) {
					builder.append(comment.getComment()).append(" ");
				}
			}
		}
		if (builder.length() != 0) {
			return builder.toString();
		}
		return null;
	}

	public Integer getGarageColliHeight() {
		return garageColliHeight;
	}

	/**
	 * @see no.ugland.utransprod.model.IAssembly#getAssemblyDeviation()
	 */
	public Deviation getAssemblyDeviation() {
		return null;
	}

	/**
	 * @see no.ugland.utransprod.model.IAssembly#getAssemblyOrder()
	 */
	public Order getAssemblyOrder() {
		return this;
	}

	/**
	 * @return cached kommentarer
	 */
	public String getCachedComment() {
		return cachedComment;
	}

	/**
	 * @param cachedComment
	 */
	public void setCachedComment(String cachedComment) {
		this.cachedComment = cachedComment;
	}

	/**
	 * @see no.ugland.utransprod.gui.model.Transportable#cacheComments()
	 */
	public void cacheComments() {
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

	public void setGarageColliHeight(Integer garageColliHeight) {
		this.garageColliHeight = garageColliHeight;
	}

	/**
	 * @see no.ugland.utransprod.gui.model.Transportable#cacheGarageColliHeight()
	 */
	public void cacheGarageColliHeight() {
		Integer height = Integer.valueOf(0);
		if (collies != null) {
			for (Colli colli : collies) {
				if (colli.getColliName().equalsIgnoreCase("Garasjepakke")) {
					height = colli.getHeight();
					break;
				}
			}
		}
		if (height == null) {
			height = Integer.valueOf(0);
		}
		setGarageColliHeight(height);
	}

	/**
	 * @return 1 dersom ordre mangler kollier
	 */
	public Integer getHasMissingCollies() {
		return hasMissingCollies;
	}

	/**
	 * @param hasMissingCollies
	 */
	public void setHasMissingCollies(Integer hasMissingCollies) {
		this.hasMissingCollies = hasMissingCollies;
	}

	/**
	 * @return produktområde
	 */
	public ProductArea getProductArea() {
		return productArea;
	}

	/**
	 * @param productArea
	 */
	public void setProductArea(ProductArea productArea) {
		this.productArea = productArea;
	}

	/**
	 * @see no.ugland.utransprod.gui.model.Transportable#getAssemblyTeamName()
	 */
	public String getAssemblyTeamName() {
		if (assembly != null && assembly.getSupplier() != null) {
			return assembly.getSupplier().getSupplierName();
		}
		return null;
	}

	/**
	 * @return dato for gavl ferdig
	 */
	public String getGavlDone() {
		if (orderLines != null) {
			String gavlArticleName = Util.getGavlArticleName();
			for (OrderLine orderLine : orderLines) {
				if (orderLine.getArticleName().equalsIgnoreCase(gavlArticleName)) {
					if (orderLine.getProduced() != null) {
						return Util.SHORT_DATE_FORMAT.format(orderLine.getProduced());
					}
					return null;
				}
			}
		}
		return null;
	}

	/**
	 * @return dato for takstol ferdig produsert
	 */
	public String getTakstolDone() {
		if (orderLines != null) {
			String takstolArticleName = Util.getTakstolArticleName();
			for (OrderLine orderLine : orderLines) {
				if (orderLine.getArticleName().equalsIgnoreCase(takstolArticleName)) {
					if (orderLine.getProduced() != null) {
						return Util.SHORT_DATE_FORMAT.format(orderLine.getProduced());
					}
					return null;
				}
			}
		}
		return null;
	}

	/**
	 * @return dato for front ferdig
	 */
	public String getFrontDone() {
		if (orderLines != null) {
			String frontArticleName = Util.getFrontArticleName();
			for (OrderLine orderLine : orderLines) {
				if (orderLine.getArticleName().equalsIgnoreCase(frontArticleName)) {
					if (orderLine.getProduced() != null) {
						return Util.SHORT_DATE_FORMAT.format(orderLine.getProduced());
					}
					return null;
				}
			}
		}
		return null;
	}

	/**
	 * @return dato for vegg ferdig
	 */
	public String getVeggDone() {
		if (orderLines != null) {
			String veggArticleName = Util.getVeggArticleName();
			for (OrderLine orderLine : orderLines) {
				if (orderLine.getArticleName().equalsIgnoreCase(veggArticleName)) {
					if (orderLine.getProduced() != null) {
						return Util.SHORT_DATE_FORMAT.format(orderLine.getProduced());
					}
					return null;
				}
			}
		}
		return null;
	}

	/**
	 * @return true dersom ordre har minst en kolli med innhold
	 */
	public boolean hasColliWithContent() {
		if (collies != null && collies.size() != 0) {
			for (Colli colli : collies) {
				if (colli.getOrderLines() != null && colli.getOrderLines().size() != 0) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * @return pakkedato for takstol
	 */
	public String getTakstolPackaged() {
		if (collies != null) {
			String takstolColliName = Util.getTakstolColliName();
			for (Colli colli : collies) {
				if (colli.getColliName().equalsIgnoreCase(takstolColliName)) {
					if (colli.getPackageDate() != null) {
						return Util.SHORT_DATE_FORMAT.format(colli.getPackageDate());
					}
					return null;
				}
			}
		}
		return null;
	}

	/**
	 * @see no.ugland.utransprod.gui.model.Transportable#getProductAreaGroup()
	 */
	public ProductAreaGroup getProductAreaGroup() {
		return productArea != null ? productArea.getProductAreaGroup() : null;
	}

	public final void addOrderCost(OrderCost orderCost) {
		if (orderCosts == null) {
			orderCosts = new HashSet<OrderCost>();
		}
		orderCost.setOrder(this);
		orderCosts.add(orderCost);
	}

	public final OrderLine getOrderLine(final String articlePath) {
		List<OrderLine> lines = getOrderLineList(articlePath);
		Collections.sort(lines, new OrderLine.OrderLineNumberComparator());
		return lines != null && lines.size() != 0 ? lines.get(0) : OrderLine.UNKNOWN;
	}

	/**
	 * @param articlePath
	 * @return
	 */
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

	public final void addColli(final Colli colli) {
		if (collies == null) {
			collies = new HashSet<Colli>();
		}
		colli.setOrder(this);
		collies.add(colli);
	}

	public boolean hasTransportCostBasis() {
		if (orderCosts != null) {
			for (OrderCost orderCost : orderCosts) {
				if (orderCost.getTransportCostBasis() != null && orderCost.getPostShipment() == null) {
					return true;
				}
			}
		}
		return false;
	}

	public final void removePostShipment(final PostShipment postShipment) {
		if (postShipments != null) {
			postShipments.remove(postShipment);
			postShipment.setOrder(null);
		}
	}

	public final BigDecimal getContributionMargin() {
		BigDecimal ownProductionCostCustomer = getCost("Egenproduksjon", "Kunde");
		if (ownProductionCostCustomer != null && ownProductionCostCustomer.intValue() != 0) {
			BigDecimal ownProductionCostInternal = getCost("Egenproduksjon", "Intern");
			if (ownProductionCostInternal != null) {
				return ownProductionCostCustomer.subtract(ownProductionCostInternal);
			}
		}
		return BigDecimal.valueOf(0);
	}

	public final BigDecimal getContributionRate() {

		BigDecimal ownProductionCostCustomer = getCost("Egenproduksjon", "Kunde");
		if (ownProductionCostCustomer != null && ownProductionCostCustomer.intValue() != 0) {
			BigDecimal contributionMargin = getContributionMargin();

			// BigDecimal customerMinusInternalDivideCustomer =
			// contributionMargin
			// .divide(ownProductionCostCustomer, 2,
			// RoundingMode.HALF_EVEN);

			return contributionMargin.divide(ownProductionCostCustomer, 2, RoundingMode.HALF_EVEN);

			// return BigDecimal.valueOf(1).subtract(
			// customerMinusInternalDivideCustomer);

		}
		return BigDecimal.valueOf(0);

	}

	public final Set<Deviation> getDeviations() {
		return deviations;
	}

	public final void setDeviations(final Set<Deviation> someDeviations) {
		this.deviations = someDeviations;
	}

	public final Date getRegistrationDate() {
		return registrationDate;
	}

	public final void setRegistrationDate(final Date aRegistrationDate) {
		this.registrationDate = aRegistrationDate;
	}

	public Date getProductionDate() {
		return productionDate;
	}

	public void setProductionDate(Date productionDate) {
		this.productionDate = productionDate;
	}

	public Set<ProcentDone> getProcentDones() {
		return procentDones;
	}

	public void setProcentDones(Set<ProcentDone> procentDones) {
		this.procentDones = procentDones;
	}

	public ProcentDone getLastProcentDone() {
		if (!lastProcentDoneFetched) {

			if (procentDones != null && procentDones.size() != 0) {

				ProcentDone procentDone = (ProcentDone) CollectionUtils.get(procentDones.iterator(),
						procentDones.size() - 1);
				lastProcentDone = procentDone;
				lastProcentDoneFetched = true;
			}
		}
		return lastProcentDone;
	}

	public void addProcentDone(ProcentDone procentDone) {
		lastProcentDoneFetched = false;
		if (procentDones == null) {
			procentDones = new HashSet<ProcentDone>();
		}
		procentDone.setOrder(this);
		procentDones.add(procentDone);
	}

	public ProcentDone getProcentDone(ProcentDone procentDone) {
		if (procentDones != null) {
			List<ProcentDone> tmpList = new ArrayList<ProcentDone>(procentDones);
			if (tmpList.indexOf(procentDone) != -1) {
				return tmpList.get(tmpList.indexOf(procentDone));
			}
		}
		return null;
	}

	public void clearProcentDoneCache() {
		lastProcentDoneFetched = false;
	}

	public List<OrderComment> getAssemblyComments() {
		List<OrderComment> comments = new ArrayList<OrderComment>();
		if (orderComments != null) {
			for (OrderComment comment : orderComments) {
				if (CommentTypeUtil.hasCommentType(comment.getCommentTypes(), "Montering")) {
					comments.add(comment);
				}
			}
		}
		return comments;
	}

	/**
	 * @see no.ugland.utransprod.gui.model.Transportable#getMaxTakstolHeight()
	 */
	public Integer getTakstolHeight() {
		return takstolHeight;
	}

	public void setTakstolHeight(Integer height) {
		takstolHeight = height;
	}

	public void cacheTakstolHeight() {
		OrderLine takstol = getOrderLine(Util.getTakstolArticleName());
		String value = takstol.getAttributeValue(MAX_HEIGTH_ATTRIBUTE);
		if (StringUtils.isNumeric(value)) {
			setTakstolHeight(value.length() != 0 ? Integer.valueOf(value) : Integer.valueOf(0));
		}
	}

	public String getManagerName() {
		return OrderManager.MANAGER_NAME;
	}

	public final boolean hasTransportAfter(final YearWeek yearWeek) {
		return transport != null ? transport.isAfter(yearWeek) : false;
	}

	public Cutting getCutting() {
		return cutting;
	}

	public void setCutting(final Cutting aCutting) {
		cutting = aCutting;
	}

	public Integer getProbability() {
		return probability;
	}

	public void setProbability(Integer aProbability) {
		probability = aProbability;

	}

	public String getTelephoneNrSite() {
		return telephoneNrSite;
	}

	public void setTelephoneNrSite(String aTeleponeNr) {
		telephoneNrSite = aTeleponeNr;
	}

	public void setOwnProductionTakstol(BigDecimal aCstpr) {
		ownProductionTakstol = aCstpr;

	}

	public BigDecimal getOwnProductionTakstol() {
		return ownProductionTakstol;
	}

	public BigDecimal getInternalCostTakstol() {
		return getCost(COST_TYPE_NAME_TAKSTOLER, COST_UNIT_INTERNAL);
	}

	public void setTrossDrawer(String drawer) {
		trossDrawer = drawer;

	}

	public void setTrossReady(Date trossDate) {
		trossReady = trossDate;

	}

	public String getTrossDrawer() {
		return trossDrawer;
	}

	public Date getTrossReady() {
		return trossReady;
	}

	public void setTrossStart(Date aTrossStartDate) {
		trossStartDate = aTrossStartDate;

	}

	public Date getTrossStart() {
		return trossStartDate;

	}

	public OrderCost getOrderCost(String costTypeName, String costUnitName) {
		if (orderCosts != null) {
			for (OrderCost cost : orderCosts) {
				if (cost.getCostUnit() != null) {
					String orderCostTypeName = cost.getCostType().getCostTypeName();
					String orderCostUnitName = cost.getCostUnit().getCostUnitName();
					if (orderCostTypeName.equalsIgnoreCase(costTypeName)
							&& orderCostUnitName.equalsIgnoreCase(costUnitName)) {
						return cost;
					}
				}
			}
		}
		return null;
	}

	public Date getTrossStartDate() {
		return trossStartDate;
	}

	public void setTrossStartDate(Date trossStartDate) {
		this.trossStartDate = trossStartDate;
	}

	public Integer getDefaultColliesGenerated() {
		return defaultColliesGenerated;
	}

	public void setDefaultColliesGenerated(Integer defaultColliesGenerated) {
		this.defaultColliesGenerated = defaultColliesGenerated;
	}

	public List<Colli> getColliList() {
		return collies != null ? new ArrayList<Colli>(collies) : null;
	}

	public List<OrderLine> getOwnOrderLines() {
		return orderLines != null ? new ArrayList<OrderLine>(orderLines) : null;
	}

	public Transportable getTransportable() {
		return this;
	}

	public List<OrderLine> getOrderLineList() {
		return orderLines != null ? new ArrayList<OrderLine>(orderLines) : null;
	}

	public Integer getMaxTrossHeight() {
		return maxTrossHeight;
	}

	public void setMaxTrossHeight(Integer maxTrossHeight) {
		this.maxTrossHeight = maxTrossHeight;
	}

	public Collection<Colli> getColliesWithContent() {
		List<Colli> colliesWithContent = Lists.newArrayList();
		if (collies != null && collies.size() != 0) {
			for (Colli colli : collies) {
				if (colli.getOrderLines() != null && colli.getOrderLines().size() != 0) {
					colliesWithContent.add(colli);
				}
			}
		}
		return colliesWithContent;
	}

	public void addOrderCosts(Set<OrderCost> costs) {
		orderCosts = orderCosts != null ? orderCosts : new HashSet<OrderCost>();
		orderCosts.addAll(costs);

	}

	public void setProductionBasis(Integer productionBasis) {
		this.productionBasis = productionBasis;
	}

	public Integer getProductionBasis() {
		return productionBasis;
	}

	public Integer getProductionWeek() {
		return productionWeek;
	}

	public void setProductionWeek(Integer productionWeek) {
		this.productionWeek = productionWeek;
	}

	public void setPacklistDuration(BigDecimal packlistDuration) {
		this.packlistDuration = packlistDuration;

	}

	public BigDecimal getPacklistDuration() {
		return packlistDuration;
	}

	public void setPacklistDoneBy(String packlistDoneBy) {
		this.packlistDoneBy = packlistDoneBy;
	}

	public String getPacklistDoneBy() {
		return packlistDoneBy;
	}

	public Date getOrderReadyWall() {
		return orderReadyWall;
	}

	public void setOrderReadyWall(Date orderReady) {
		this.orderReadyWall = orderReady;
	}

	public Date getOrderReadyTross() {
		return this.orderReadyTross;
	}

	public void setOrderReadyTross(Date orderReady) {
		this.orderReadyTross = orderReady;

	}

	public String getPackedByTross() {
		return packedByTross;
	}

	public void setPackedByTross(String packedByTross) {
		this.packedByTross = packedByTross;
	}

	public Date getOrderReadyPack() {
		return orderReadyPack;
	}

	public void setOrderReadyPack(Date orderReadyPack) {
		this.orderReadyPack = orderReadyPack;
	}

	public String getPackedByPack() {
		return packedByPack;
	}

	public void setPackedByPack(String packedBy) {
		this.packedByPack = packedBy;
	}

	public BigDecimal getEstimatedTimeWall() {
		return estimatedTimeWall;
	}

	public void setEstimatedTimeWall(BigDecimal estimatedTimeWall) {
		this.estimatedTimeWall = estimatedTimeWall;
	}

	public BigDecimal getEstimatedTimeGavl() {
		return estimatedTimeGavl;
	}

	public void setEstimatedTimeGavl(BigDecimal estimatedTimeGavl) {
		this.estimatedTimeGavl = estimatedTimeGavl;
	}

	public BigDecimal getEstimatedTimePack() {
		return estimatedTimePack;
	}

	public void setEstimatedTimePack(BigDecimal estimatedTimePack) {
		this.estimatedTimePack = estimatedTimePack;
	}

	public String getReceivedTrossDrawing() {
		return receivedTrossDrawing;
	}

	public void setReceivedTrossDrawing(String receivedTrossDrawing) {
		this.receivedTrossDrawing = receivedTrossDrawing;
	}

	public String getAntallStandardvegger() {
		return antallStandardvegger;
	}

	public void setAntallStandardvegger(String antallStandardvegger) {
		this.antallStandardvegger = antallStandardvegger;
	}

	public void setLevert(Date levertDate) {
		levert = levertDate;
	}

	public Boolean getLevertBool() {
		return levert != null;
	}
	public Date getLevert() {
		return levert;
	}

	public List<OrderLine> getOrderLinesNotLevert() {
		ArrayList<OrderLine> linesNotLevert = new ArrayList<OrderLine>();
		if (orderLines != null) {
			for (OrderLine line : orderLines) {
				if (line.hasTopLevelArticle() && line.hasArticle()
						&& (line.getColli() == null || line.getColli().getLevert() == null)) {
					linesNotLevert.add(line);
				}
			}
		}
		return linesNotLevert;
	}
}
