package no.ugland.utransprod.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import no.ugland.utransprod.gui.model.Packable;
import no.ugland.utransprod.gui.model.TextRenderable;
import no.ugland.utransprod.gui.model.TransportListable;
import no.ugland.utransprod.gui.model.Transportable;
import no.ugland.utransprod.service.PostShipmentManager;
import no.ugland.utransprod.util.CommentTypeUtil;
import no.ugland.utransprod.util.Util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.Hibernate;

/**
 * Domeneklasse for tabell POST_SHIPMENT
 * 
 * @author atle.brekka
 */
public class PostShipment extends AbstractTransportable implements TextRenderable, TransportListable, Packable {

	private static final long serialVersionUID = 1L;

	private Integer postShipmentId;

	private Order order;

	private Transport transport;

	private Deviation deviation;

	private Date sent;

	private Set<Colli> collies;

	private Date postShipmentComplete;

	private Date postShipmentReady;

	private Set<OrderLine> orderLines;

	private PostShipment postShipmentRef;

	private Set<PostShipment> postShipmentRefs;

	private String status;

	private String packedBy;

	private Integer garageColliHeight;

	private String cachedComment;

	private OrderCost orderCost;

	/**
	 * Brukes til info fra Visma
	 */
	// private List<CustTr> custTrs;
	private Integer takstolHeight;

	private Integer defaultColliesGenerated;

	private Date levert;
	public static final PostShipment UNKNOWN = new PostShipment() {
	};

	public PostShipment() {

	}

	/**
	 * @param postShipmentId
	 * @param order
	 * @param postShipmentReady
	 */
	public PostShipment(Integer postShipmentId, Order order, Date postShipmentReady) {
		this.postShipmentId = postShipmentId;
		this.order = order;
		this.postShipmentReady = postShipmentReady;
	}

	/**
	 * @param postShipmentId
	 * @param postShipmentComplete
	 * @param order
	 * @param transport
	 * @param deviation
	 * @param sent
	 * @param postShipmentReady
	 */
	public PostShipment(Integer postShipmentId, Date postShipmentComplete, Order order, Transport transport,
			Deviation deviation, Date sent, Date postShipmentReady, String aStatus) {
		this.postShipmentId = postShipmentId;
		this.postShipmentComplete = postShipmentComplete;
		this.order = order;
		this.transport = transport;
		this.deviation = deviation;
		this.sent = sent;
		this.postShipmentReady = postShipmentReady;
		this.status = aStatus;

	}

	/**
	 * @param postShipmentId
	 * @param order
	 * @param transport
	 * @param deviation
	 * @param sent
	 * @param collies
	 * @param postShipmentComplete
	 * @param postShipmentReady
	 * @param orderLines
	 * @param postShipmentRef
	 * @param postShipmentRefs
	 * @param status
	 * @param packedBy
	 * @param garageColliHeight
	 * @param cachedComment
	 */
	public PostShipment(Integer postShipmentId, Order order, Transport transport, Deviation deviation, Date sent,
			Set<Colli> collies, Date postShipmentComplete, Date postShipmentReady, Set<OrderLine> orderLines,
			PostShipment postShipmentRef, Set<PostShipment> postShipmentRefs, String status, String packedBy,
			Integer garageColliHeight, String cachedComment, OrderCost aOrderCost, final Integer aTakstolHeight) {
		this.postShipmentId = postShipmentId;
		this.order = order;
		this.transport = transport;
		this.deviation = deviation;
		this.sent = sent;
		this.collies = collies;
		this.postShipmentComplete = postShipmentComplete;
		this.postShipmentReady = postShipmentReady;
		this.orderLines = orderLines;
		this.postShipmentRef = postShipmentRef;
		this.postShipmentRefs = postShipmentRefs;
		this.status = status;
		this.packedBy = packedBy;
		this.garageColliHeight = garageColliHeight;
		this.cachedComment = cachedComment;
		this.orderCost = aOrderCost;
		this.takstolHeight = aTakstolHeight;
	}

	/**
	 * @see no.ugland.utransprod.gui.model.Transportable#getOrder()
	 */
	public Order getOrder() {
		return order;
	}

	/**
	 * @param order
	 */
	public void setOrder(Order order) {
		this.order = order;
	}

	/**
	 * @return id
	 */
	public Integer getPostShipmentId() {
		return postShipmentId;
	}

	/**
	 * @param postShipmentId
	 */
	public void setPostShipmentId(Integer postShipmentId) {
		this.postShipmentId = postShipmentId;
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#toString()
	 */
	@Override
	public String toString() {
		Customer customer = order.getCustomer();
		if (customer != null) {
			return customer.toString() + " " + order.getPostalCode() + " " + order.getPostOffice();
		}
		return "";
	}

	/**
	 * @see no.ugland.utransprod.gui.model.Transportable#getTransport()
	 */
	public Transport getTransport() {
		return transport;
	}

	/**
	 * @see no.ugland.utransprod.gui.model.Transportable#setTransport(no.ugland.utransprod.model.Transport)
	 */
	public void setTransport(Transport transport) {
		this.transport = transport;
	}

	/**
	 * @return avvik
	 */
	public Deviation getDeviation() {
		return deviation;
	}

	/**
	 * @param deviation
	 */
	public void setDeviation(Deviation deviation) {
		this.deviation = deviation;
	}

	/**
	 * @see no.ugland.utransprod.gui.model.Transportable#getDoAssembly()
	 */
	public Integer getDoAssembly() {
		return null;
	}

	/**
	 * @see no.ugland.utransprod.gui.model.Transportable#getOrderComplete()
	 */
	public Date getOrderComplete() {
		return getPostShipmentComplete();
	}

	/**
	 * @see no.ugland.utransprod.gui.model.Transportable#getOrderReady()
	 */
	public Date getOrderReady() {
		return getPostShipmentReady();
	}

	/**
	 * @see no.ugland.utransprod.gui.model.Transportable#getPackageStarted()
	 */
	public Date getPackageStarted() {
		return null;
	}

	/**
	 * @see no.ugland.utransprod.gui.model.Transportable#getSentBool()
	 */
	public Boolean getSentBool() {
		if (sent != null) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	/**
	 * @see no.ugland.utransprod.gui.model.Transportable#getStatus()
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @see no.ugland.utransprod.gui.model.Transportable#getTransportString()
	 */
	public String getTransportString() {
		StringBuffer stringBuffer = new StringBuffer();
		Customer customer = order.getCustomer();
		if (customer != null) {
			stringBuffer.append(customer.toString());
		}
		stringBuffer.append(" - ").append(order.getOrderNr()).append("\n").append(order.getPostalCode()).append(" ")
				.append(order.getPostOffice()).append(",").append(order.getConstructionTypeString()).append(",")
				.append(order.getInfo());

		return stringBuffer.toString();
	}

	/**
	 * @see no.ugland.utransprod.gui.model.Transportable#setStatus(java.lang.String)
	 */
	public void setStatus(String status) {
		this.status = status;

	}

	/**
	 * @see no.ugland.utransprod.gui.model.Transportable#getSent()
	 */
	public Date getSent() {
		return sent;
	}

	/**
	 * @see no.ugland.utransprod.gui.model.Transportable#setSent(java.util.Date)
	 */
	public void setSent(Date sent) {
		this.sent = sent;
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
	 * @return dato for etterlevering klar
	 */
	public Date getPostShipmentComplete() {
		return postShipmentComplete;
	}

	/**
	 * @param postShipmentComplete
	 */
	public void setPostShipmentComplete(Date postShipmentComplete) {
		this.postShipmentComplete = postShipmentComplete;
	}

	/**
	 * @see no.ugland.utransprod.gui.model.TextRenderable#getOrderString()
	 */
	public String getOrderString() {

		return getTransportString();
	}

	/**
	 * @return dato for etterlevering er klar
	 */
	public Date getPostShipmentReady() {
		return postShipmentReady;
	}

	/**
	 * @param postShipmentReady
	 */
	public void setPostShipmentReady(Date postShipmentReady) {
		this.postShipmentReady = postShipmentReady;
	}

	/**
	 * @see no.ugland.utransprod.gui.model.Transportable#getSpecialConcern()
	 */
	public String getSpecialConcern() {
		return null;
	}

	/**
	 * @see no.ugland.utransprod.gui.model.Transportable#getOrderLines()
	 */
	public Set<OrderLine> getOrderLines() {
		return orderLines;
	}

	/**
	 * @param orderLines
	 */
	public void setOrderLines(Set<OrderLine> orderLines) {
		this.orderLines = orderLines;
	}

	/**
	 * @param orderLine
	 *            legger til ordrelinje
	 */
	public void addOrderLine(OrderLine orderLine) {
		if (orderLine != null) {
			if (orderLines == null) {
				orderLines = new HashSet<OrderLine>();
			}
			orderLine.setPostShipment(this);
			orderLines.add(orderLine);
		}
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
	 * @return referanse til anne etterlevering
	 */
	public PostShipment getPostShipmentRef() {
		return postShipmentRef;
	}

	/**
	 * @param postShipmentRef
	 */
	public void setPostShipmentRef(PostShipment postShipmentRef) {
		this.postShipmentRef = postShipmentRef;
	}

	/**
	 * @return etterleveringer som refererer til gjeldende
	 */
	public Set<PostShipment> getPostShipmentRefs() {
		return postShipmentRefs;
	}

	/**
	 * @param postShipmentRefs
	 */
	public void setPostShipmentRefs(Set<PostShipment> postShipmentRefs) {
		this.postShipmentRefs = postShipmentRefs;
	}

	/**
	 * @see no.ugland.utransprod.gui.model.Transportable#getPostShipment()
	 */
	public PostShipment getPostShipment() {
		return this;
	}

	/**
	 * @see no.ugland.utransprod.gui.model.Transportable#getPostShipments()
	 */
	public Set<PostShipment> getPostShipments() {
		return getPostShipmentRefs();
	}

	/**
	 * @see no.ugland.utransprod.gui.model.Transportable#getMissingCollies()
	 */
	public List<OrderLine> getMissingCollies() {
		ArrayList<OrderLine> missing = new ArrayList<OrderLine>();
		if (orderLines != null) {
			for (OrderLine line : orderLines) {
				if (line.hasTopLevelArticle() && line.hasArticle() && (line.getColli() == null)) {
					missing.add(line);
				}
			}
		}
		return missing;
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

		stringBuffer.append(order.getPostalCode()).append(" ").append(order.getPostOffice());
		Customer customer = order.getCustomer();
		if (customer != null) {
			stringBuffer.append(" - ").append(customer.getFullName());
		}
		stringBuffer.append(" - ").append(order.getOrderNr()).append(",").append(order.getConstructionTypeString())
				.append(",").append(order.getInfo());

		return stringBuffer.toString();
	}

	/**
	 * @see no.ugland.utransprod.gui.model.Transportable#getConstructionType()
	 */
	public ConstructionType getConstructionType() {
		if (order != null) {
			return order.getConstructionType();
		}
		return null;
	}

	/**
	 * @see no.ugland.utransprod.gui.model.Transportable#getPaidDate()
	 */
	public Date getPaidDate() {
		return order.getPaidDate();
	}

	/**
	 * @see no.ugland.utransprod.gui.model.Transportable#getTransportComments()
	 */
	public String getTransportComments() {
		StringBuilder builder = new StringBuilder();
		if (deviation != null) {
			Set<OrderComment> comments = deviation.getOrderComments();
			if (comments != null) {
				for (OrderComment comment : comments) {
					if (CommentTypeUtil.hasCommentType(comment.getCommentTypes(), "Transport")) {
						// if
						// (Util.convertNumberToBoolean(comment.getForTransport()))
						// {
						builder.append(comment.getComment()).append(" ");
					}
				}
			}
			if (builder.length() != 0) {
				return builder.toString();
			}
		}
		return null;
	}

	/**
	 * @see no.ugland.utransprod.gui.model.Transportable#getGarageColliHeight()
	 */
	public Integer getGarageColliHeight() {
		return garageColliHeight;

	}

	/**
	 * @see no.ugland.utransprod.gui.model.Transportable#getComment()
	 */
	public String getComment() {
		return getCachedComment();
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
		Set<OrderComment> comments = order.getOrderComments();
		StringBuilder builder = new StringBuilder();
		if (comments != null && Hibernate.isInitialized(comments)) {
			for (OrderComment comment : comments) {
				if (comment.getDeviation() != null && comment.getDeviation().getPostShipment() != null
						&& comment.getDeviation().getPostShipment().equals(this)) {
					builder.append(comment.getComment()).append(";");
				}
			}
		}
		if (builder.length() > 0) {
			setCachedComment(builder.toString());
		} else {
			setCachedComment(null);
		}

	}

	/**
	 * @see no.ugland.utransprod.gui.model.Transportable#cacheGarageColliHeight()
	 */
	public void cacheGarageColliHeight() {
		Integer height = 0;
		if (collies != null) {
			for (Colli colli : collies) {
				if (colli.getColliName().equalsIgnoreCase("Garasjepakke")) {
					height = colli.getHeight();
				}
			}
		}
		if (height == null) {
			height = 0;
		}
		setGarageColliHeight(height);
	}

	/**
	 * @param garageColliHeight
	 */
	public void setGarageColliHeight(Integer garageColliHeight) {
		this.garageColliHeight = garageColliHeight;
	}

	/**
	 * @see no.ugland.utransprod.gui.model.Transportable#getAssemblyTeamName()
	 */
	public String getAssemblyTeamName() {
		if (deviation != null) {
			return deviation.getAssemblyTeamName();
		}
		return null;
	}

	/**
	 * @return true dersom etterlevering er komplett
	 */
	public final Boolean isDonePackage() {
		if (orderLines != null && orderLines.size() != 0) {
			return isAllOrderLinesDonePackage();
		}
		return Boolean.TRUE;
	}

	private Boolean isAllOrderLinesDonePackage() {
		for (OrderLine orderLine : orderLines) {
			if (orderLine.hasTopLevelArticle() && orderLine.getColli() == null && orderLine.hasArticle()) {

				return Boolean.FALSE;
			}
		}
		return Boolean.TRUE;
	}

	/**
	 * @see no.ugland.utransprod.gui.model.Transportable#getPacklistReady()
	 */
	public Date getPacklistReady() {
		return null;
	}

	/**
	 * @see no.ugland.utransprod.gui.model.Transportable#getProductAreaGroup()
	 */
	public ProductAreaGroup getProductAreaGroup() {
		if (order != null) {
			return order.getProductAreaGroup();
		}
		return null;
	}

	public OrderLine getOrderLine(final String articlePath) {
		if (orderLines != null) {
			for (OrderLine orderLine : orderLines) {
				if (orderLine.getArticlePath().equalsIgnoreCase(articlePath)) {
					return orderLine;
				}
			}
		}
		return OrderLine.UNKNOWN;
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

	public final void addColli(final Colli colli) {
		if (collies == null) {
			collies = new HashSet<Colli>();
		}
		colli.setPostShipment(this);
		collies.add(colli);
	}

	public OrderCost getOrderCost() {
		return orderCost;
	}

	public void setOrderCost(OrderCost orderCost) {
		this.orderCost = orderCost;
	}

	public boolean hasTransportCostBasis() {
		if (orderCost != null) {
			return true;
		}
		return false;
	}

	public final String getTransportDetails() {
		if (transport != null) {
			return transport.toString();
		}
		return "";
	}

	public final Customer getCustomer() {
		return order.getCustomer();
	}

	public final String getPacklistComments() {
		StringBuilder builder = new StringBuilder();
		if (deviation != null) {
			Set<OrderComment> comments = deviation.getOrderComments();
			if (comments != null) {
				for (OrderComment comment : comments) {
					if (CommentTypeUtil.hasCommentType(comment.getCommentTypes(), "Pakking")) {
						// if
						// (Util.convertNumberToBoolean(comment.getForPackage()))
						// {
						builder.append(comment.getComment()).append(" ");
					}
				}
			}
			if (builder.length() != 0) {
				return builder.toString();
			}
		}
		return null;
	}

	public final Assembly getAssembly() {
		if (deviation != null) {
			return deviation.getAssembly();
		}
		return null;
	}

	/*
	 * public final List<CustTr> getCustTrs() { return custTrs; }
	 * 
	 * public final void setCustTr(final List<CustTr> aCustTrs) { custTrs =
	 * aCustTrs;
	 * 
	 * }
	 * 
	 * public boolean isPaid() { if (custTr != null) { if
	 * (custTr.getRestAmount() != null && custTr.getRestAmount().intValue() ==
	 * 0) {
	 * 
	 * return true; } return false;
	 * 
	 * }
	 * 
	 * if(order!=null&&order.getPaidDate()!=null){ return true; } return false;
	 * }
	 */

	public Date getProductionDate() {
		return order.getProductionDate();
	}

	public ProcentDone getLastProcentDone() {
		return null;
	}

	public String getOrderNr() {
		return order.getOrderNr();
	}

	public void setOrderNr(String orderNr) {
	}

	public void setOrderReady(Date orderReady) {
		setPostShipmentReady(orderReady);

	}

	public void setOrderComplete(Date date) {
		setPostShipmentComplete(date);

	}

	public Set<OrderComment> getOrderComments() {
		return order.getOrderComments();
	}

	public void setPackageStarted(Date date) {
	}

	public Integer getColliesDone() {
		return Util.convertBooleanToNumber(isDonePackage());
	}

	public void setColliesDone(Integer done) {
	}

	public Integer getTakstolHeight() {
		return takstolHeight;
	}

	public void setTakstolHeight(Integer height) {
		takstolHeight = height;
	}

	public void cacheTakstolHeight() {
		OrderLine takstol = getOrderLine(Util.getTakstolArticleName());
		String value = takstol.getAttributeValue("Makshøyde");
		setTakstolHeight(
				value.length() != 0 && StringUtils.isNumeric(value) ? Integer.valueOf(value) : Integer.valueOf(0));
	}

	public String getManagerName() {
		return PostShipmentManager.MANAGER_NAME;
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof PostShipment))
			return false;
		PostShipment castOther = (PostShipment) other;
		return new EqualsBuilder().append(order, castOther.order).append(transport, castOther.transport)
				.append(sent, castOther.sent).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(order).append(transport).append(sent).toHashCode();
	}

	public Integer getProbability() {
		return null;
	}

	public String getTrossDrawer() {
		return order != null ? order.getTrossDrawer() : null;
	}

	public Integer getDefaultColliesGenerated() {
		return defaultColliesGenerated;
	}

	public void setDefaultColliesGenerated(Integer defaultColliesGenerated) {
		this.defaultColliesGenerated = defaultColliesGenerated;
	}

	public Integer getMaxTrossHeight() {
		return null;
	}

	public List<Colli> getColliList() {
		return collies != null ? new ArrayList<Colli>(collies) : null;
	}

	public List<OrderLine> getOrderLineList() {
		return orderLines != null ? new ArrayList<OrderLine>(orderLines) : null;
	}

	public List<OrderLine> getOwnOrderLines() {
		return orderLines != null ? new ArrayList<OrderLine>(orderLines) : null;
	}

	public Transportable getTransportable() {
		return this;
	}

	public boolean removeColli(Colli colli) {
		if (collies != null) {
			return collies.remove(colli);
		}
		return false;
	}

	public Integer getProductionWeek() {
		return order.getProductionWeek();
	}

	public Date getOrderReadyWall() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setOrderReadyWall(Date orderReady) {
		// TODO Auto-generated method stub

	}

	public Date getOrderReadyTross() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setOrderReadyTross(Date orderReady) {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub

	}

	public String getPackedByPack() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setPackedByPack(String packedBy) {
		// TODO Auto-generated method stub

	}

	public void setLevert(Date levertDate) {
		levert = levertDate;

	}

	public Date getLevert() {
		return levert;
	}

	public Boolean getLevertBool() {
		return levert != null;
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
