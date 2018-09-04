package no.ugland.utransprod.model;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import no.ugland.utransprod.gui.handlers.TransportLetterObject;
import no.ugland.utransprod.gui.model.TransportListable;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Klasse for tabell COLLI
 * 
 * @author atle.brekka
 */
public class Colli extends BaseObject implements Comparable<Colli>, TransportLetterObject, TransportListable {
	private static final long serialVersionUID = 1L;

	private Integer colliId;

	private Order order;

	private String colliName;

	private Transport transport;

	private Date sent;

	private Set<OrderLine> orderLines;

	private PostShipment postShipment;

	private Integer height;

	private Date packageDate;

	private Integer numberOfCollies;

	private Integer lenght;

	private Integer width;

	private Date levert;
	public static final Colli UNKNOWN = new Colli();
	public static final Colli DEFAULT_TAKSTOL = new Colli("Takstol");

	public Colli() {
		super();
	}

	public Colli(String aColliName) {
		colliName = aColliName;
	}

	/**
	 * @param colliId
	 * @param order
	 * @param colliName
	 * @param transport
	 * @param sent
	 * @param orderLines
	 * @param postShipment
	 * @param height
	 * @param packageDate
	 */
	public Colli(Integer colliId, Order order, String colliName, Transport transport, Date sent,
			Set<OrderLine> orderLines, PostShipment postShipment, Integer height, Date packageDate) {
		super();
		this.colliId = colliId;
		this.order = order;
		this.colliName = colliName;
		this.transport = transport;
		this.sent = sent;
		this.orderLines = orderLines;
		this.postShipment = postShipment;
		this.height = height;
		this.packageDate = packageDate;
	}

	/**
	 * @return id
	 */
	public Integer getColliId() {
		return colliId;
	}

	/**
	 * @param colliId
	 */
	public void setColliId(Integer colliId) {
		this.colliId = colliId;
	}

	/**
	 * @return kollinavn
	 */
	public String getColliName() {
		return colliName;
	}

	/**
	 * @param colliName
	 */
	public void setColliName(String colliName) {
		this.colliName = colliName;
	}

	/**
	 * @return dato for sendt
	 */
	public Date getSent() {
		return sent;
	}

	/**
	 * @param sent
	 */
	public void setSent(Date sent) {
		this.sent = sent;
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
	 * @return ordre
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
	 * @see no.ugland.utransprod.model.BaseObject#toString()
	 */
	@Override
	public String toString() {
		return colliName;
	}

	/**
	 * @return ordrelinjer
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
	 * @param isSent
	 */
	public void setSentBool(Boolean isSent) {
		if (isSent) {
			sent = Calendar.getInstance().getTime();
		} else {
			sent = null;
		}
	}

	/**
	 * @return true dersom sendt
	 */
	public Boolean getSentBool() {
		if (sent != null) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	/**
	 * @param orderLine
	 */
	public void addOrderLine(OrderLine orderLine) {
		if (orderLines == null) {
			orderLines = new HashSet<OrderLine>();
		}
		orderLine.setColli(this);
		orderLines.add(orderLine);
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.TransportLetterObject#getPostShipment()
	 */
	public PostShipment getPostShipment() {
		return postShipment;
	}

	/**
	 * @param postShipment
	 */
	public void setPostShipment(PostShipment postShipment) {
		this.postShipment = postShipment;
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof Colli))
			return false;
		Colli castOther = (Colli) other;
		return new EqualsBuilder().append(order, castOther.order).append(colliName, castOther.colliName)
				.append(postShipment, castOther.postShipment).append(colliId, castOther.colliId).isEquals();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(order).append(colliName).append(postShipment).append(colliId).toHashCode();
	}

	/**
	 * @return kollidetaljer
	 */
	public String getColliDetails() {
		StringBuilder builder = new StringBuilder();
		if (orderLines == null || orderLines.isEmpty()) {
			builder.append("Tom - sjekk denne.");
		} else {

			for (OrderLine orderLine : orderLines) {
				orderLine.generateDetails(builder);
			}
		}
		if (builder.length() != 0) {
			return builder.toString();
		}
		return null;
	}

	public String getColliDetailsWithoutNoAttributes() {
		StringBuilder builder = new StringBuilder();
		if (orderLines != null) {

			for (OrderLine orderLine : orderLines) {
				if (orderLine.generateDetailsWithoutNoAttributes(builder, getColliName())) {
					// builder.append("\n");
				}
			}
		}
		// if (builder.length() != 0) {
		// return builder.toString();
		// }
		return builder.toString();
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.TransportLetterObject#getDetails()
	 */
	public String getDetails() {
		String details = getColliDetailsWithoutNoAttributes();
		// if(colliName.equalsIgnoreCase("Garasjepakke")&&height!=null&&height.intValue()!=0){
		// details=details+" Pakkehøyde:"+height;
		// }
		return orderLines == null || orderLines.isEmpty() ? "Tom - sjekk denne." : details;
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.TransportLetterObject#getName()
	 */
	public String getName() {
		return getColliName();
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.TransportLetterObject#getTypeName()
	 */
	public String getTypeName() {
		return "Kolli";
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.TransportLetterObject#getLetterOrder()
	 */
	public Order getLetterOrder() {
		if (order != null) {
			return order;
		} else if (postShipment != null) {
			return postShipment.getOrder();
		}
		return null;
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.TransportLetterObject#isNotPostShipment()
	 */
	public Boolean isNotPostShipment() {
		if (postShipment != null) {
			return Boolean.FALSE;
		}

		return Boolean.TRUE;
	}

	/**
	 * @return kollihøyde
	 */
	public Integer getHeight() {
		return height;
	}

	/**
	 * @param height
	 */
	public void setHeight(Integer height) {
		this.height = height;
	}

	/**
	 * @return pakkedato
	 */
	public Date getPackageDate() {
		return packageDate;
	}

	/**
	 * @param packageDate
	 */
	public void setPackageDate(Date packageDate) {
		this.packageDate = packageDate;
	}

	/**
	 * @see no.ugland.utransprod.gui.model.TransportListable#getCollies()
	 */
	public Set<Colli> getCollies() {
		return null;
	}

	/**
	 * @see no.ugland.utransprod.gui.model.TransportListable#getOrderLinesNotSent()
	 */
	public List<OrderLine> getOrderLinesNotSent() {
		return null;
	}

	public ProductAreaGroup getProductAreaGroup() {
		return order.getProductAreaGroup();
	}

	public boolean isPaid() {
		return false;
	}

	public Integer getProbability() {
		return null;
	}

	public Integer getNumberOfCollies() {
		return numberOfCollies;
	}

	public void setNumberOfCollies(Integer numberOfCollies) {
		this.numberOfCollies = numberOfCollies;
	}

	public Integer getNumberOf() {
		return numberOfCollies == null ? 1 : numberOfCollies;
	}

	public void removeOrderLine(OrderLine orderLine) {
		if (orderLines != null && orderLine != null) {
			orderLines.remove(orderLine);
			orderLine.setColli(null);
		}

	}

	public int compareTo(final Colli other) {
		if ("Garasjepakke".equals(colliName)) {
			return -1;
		}
		return new CompareToBuilder().append(colliName, other.colliName).toComparison();
	}

	public Integer getLenght() {
		return lenght;
	}

	public Integer getWidht() {
		return width;
	}

	public void setLenght(Integer lenght) {
		this.lenght = lenght;
	}

	public void setWidht(Integer width) {
		this.width = width;
	}

	public Boolean getLevertBool() {
		if (sent != null) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public void setLevert(Date levertDate) {
		levert = levertDate;

	}

	public List<OrderLine> getOrderLinesNotLevert() {
		return null;
	}

	public Date getLevert() {
		return levert;
	}

}
