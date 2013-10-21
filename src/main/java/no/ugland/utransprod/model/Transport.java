package no.ugland.utransprod.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import no.ugland.utransprod.gui.model.Transportable;
import no.ugland.utransprod.util.Util;
import no.ugland.utransprod.util.YearWeek;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.CompareToBuilder;

/**
 * Klasse som representerer tabell TRANSPORT
 * @author atle.brekka
 */
public class Transport extends BaseObject implements Comparable<Transport> {
    private static final long serialVersionUID = 1L;

    private Integer transportId;

    private Integer transportYear;

    private Integer transportWeek;

    private Date loadingDate;

    private Set<Order> orders;

    private String transportName;

    private Date sent;

    private Set<PostShipment> postShipments;

    private Supplier supplier;

    private Employee employee;

    private String loadTime;
    private String trolley;

	private String transportComment;
    public static final Transport UNKNOWN = new Transport() {};

    /**
     * 
     */
    public Transport() {
        super();
    }

    /**
     * @param transportId
     * @param transportYear
     * @param transportWeek
     * @param loadingDate
     * @param orders
     * @param transportName
     * @param sent
     * @param postShipments
     * @param supplier
     * @param employee
     * @param loadTime
     */
    public Transport(Integer transportId, Integer transportYear,
            Integer transportWeek, Date loadingDate, Set<Order> orders,
            String transportName, Date sent, Set<PostShipment> postShipments,
            Supplier supplier, Employee employee, String loadTime,final String aTrolley,String aTransportComment) {
        super();
        this.transportId = transportId;
        this.transportYear = transportYear;
        this.transportWeek = transportWeek;
        this.loadingDate = loadingDate;
        this.orders = orders;
        this.transportName = transportName;
        this.sent = sent;
        this.postShipments = postShipments;
        this.supplier = supplier;
        this.employee = employee;
        this.loadTime = loadTime;
        this.trolley=aTrolley;
        this.transportComment=aTransportComment;
    }

    /**
     * @return lastedato
     */
    public Date getLoadingDate() {
        return loadingDate;
    }

    /**
     * @param loadingDate
     */
    public void setLoadingDate(Date loadingDate) {
        this.loadingDate = loadingDate;
    }

    /**
     * @return id
     */
    public Integer getTransportId() {
        return transportId;
    }

    /**
     * @param transportId
     */
    public void setTransportId(Integer transportId) {
        this.transportId = transportId;
    }

    /**
     * @return uke
     */
    public Integer getTransportWeek() {
        return transportWeek;
    }

    /**
     * @param transportWeek
     */
    public void setTransportWeek(Integer transportWeek) {
        this.transportWeek = transportWeek;
    }

    /**
     * @return år
     */
    public Integer getTransportYear() {
        return transportYear;
    }

    /**
     * @return formatert transportuke
     */
    public String getFormatedYearWeek() {
        return String.format("%1$d%2$02d", transportYear, transportWeek);
    }

    /**
     * @param transportYear
     */
    public void setTransportYear(Integer transportYear) {
        this.transportYear = transportYear;
    }

    /**
     * @see no.ugland.utransprod.model.BaseObject#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof Transport))
            return false;
        Transport castOther = (Transport) other;
        return new EqualsBuilder().append(transportId, castOther.transportId)
                .isEquals();
    }

    /**
     * @see no.ugland.utransprod.model.BaseObject#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(transportId).toHashCode();
    }

    /**
     * @see no.ugland.utransprod.model.BaseObject#toString()
     */
    @Override
    public String toString() {
        return getTransportDetails();
    }

    public final String getTransportDetails() {
        return transportYear + " " + transportWeek + "-" + transportName;
    }

    /**
     * @return ordre
     */
    public Set<Order> getOrders() {
        return orders;
    }

    /**
     * @param orders
     */
    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    /**
     * @return navn
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
     * Fjerner ordre
     * @param aOrder
     */
    public void removeOrder(Order aOrder) {
        if (aOrder != null) {
            if (this.orders != null) {

                this.orders.remove(aOrder);
            }
            aOrder.setTransport(null);
        }

    }

    /**
     * Legger til ordre
     * @param order
     */
    public void addOrder(Order order) {
        if (this.orders == null) {
            this.orders = new HashSet<Order>();
        }
        order.setTransport(this);
        this.orders.add(order);
    }

    /**
     * @return sendt dato
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
     * @return etterleveringer
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
     * @return strengen "Sent" dersom sendt ellers ""
     */
    public String getSentString() {
        if (sent != null) {
            return "Sent";
        }
        return "";
    }

    /**
     * @return transportfirma
     */
    public Supplier getSupplier() {
        return supplier;
    }

    /**
     * @param supplier
     */
    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    /**
     * @return sjåfør
     */
    public Employee getEmployee() {
        return employee;
    }

    /**
     * @param employee
     */
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    /**
     * @return opplastningstidspunkt
     */
    public String getLoadTime() {
        return loadTime;
    }

    /**
     * @param loadTime
     */
    public void setLoadTime(String loadTime) {
        this.loadTime = loadTime;
    }

    public List<Order> getOrders(ProductArea productArea) {
        List<Order> orderList = new ArrayList<Order>();
        if (orders != null) {
            for (Order order : orders) {
                if (order.getProductArea().equals(productArea)) {
                    orderList.add(order);
                }
            }
        }
        return orderList;
    }

    public List<Order> getOrders(ProductAreaGroup productAreaGroup) {
        List<Order> orderList = new ArrayList<Order>();
        if (orders != null) {
            for (Order order : orders) {
                if (order.getProductArea() != null
                        && order.getProductArea().getProductAreaGroup().equals(
                                productAreaGroup)||productAreaGroup.getProductAreaGroupName().equals("Alle")) {
                    orderList.add(order);
                }
            }
        }
        return orderList;
    }

    public List<Transportable> getTransportables() {
        List<Transportable> transportables = new ArrayList<Transportable>();
        if (orders != null) {
            transportables.addAll(orders);
        }
        if (postShipments != null) {
            transportables.addAll(postShipments);
        }
        return transportables;
    }

    public int compareTo(final Transport other) {
        return new CompareToBuilder()
                .append(transportYear, other.transportYear).append(
                        transportWeek, other.transportWeek).append(
                        transportName, other.transportName).toComparison();
    }

    public String getTrolley() {
        return trolley;
    }

    public void setTrolley(String trolley) {
        this.trolley = trolley;
    }
    public final boolean isAfter(final YearWeek yearWeek){
        return Util.isAfter(new YearWeek(transportYear,transportWeek),yearWeek);
    }

	public String getTransportComment() {
		return transportComment;
	}

	public void setTransportComment(String aComment) {
		transportComment=aComment;
		
	}
}
