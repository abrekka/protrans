package no.ugland.utransprod.gui.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import no.ugland.utransprod.model.Assembly;
import no.ugland.utransprod.model.Colli;
import no.ugland.utransprod.model.ConstructionType;
import no.ugland.utransprod.model.CustTr;
import no.ugland.utransprod.model.Customer;
import no.ugland.utransprod.model.Deviation;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderComment;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.PostShipment;
import no.ugland.utransprod.model.ProcentDone;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.model.Transport;

/**
 * Interface for klasser som kan transporteres
 * @author atle.brekka
 */
public interface Transportable {
    /**
     * @return status
     */
    String getStatus();

    /**
     * @return om objekt skal monteres
     */
    Integer getDoAssembly();

    /**
     * @return transport
     */
    Transport getTransport();

    /**
     * @return om sendt
     */
    Boolean getSentBool();

    /**
     * @return om ordre er klar
     */
    Date getOrderReady();
    void setOrderReady(Date orderReady);

    /**
     * @return om ordre er komplett
     */
    Date getOrderComplete();
    void setOrderComplete(Date date);

    /**
     * @return n�r pakking er startet
     */
    Date getPackageStarted();
    void setPackageStarted(Date date);

    /**
     * @return transportstreng
     */
    String getTransportString();

    /**
     * @return kommentar
     */
    String getComment();

    /**
     * @param status
     */
    void setStatus(String status);

    /**
     * @param isSent
     */
    void setSentBool(Boolean isSent);

    /**
     * @param transport
     */
    void setTransport(Transport transport);

    /**
     * @return spesielle hensyn
     */
    String getSpecialConcern();

    /**
     * @return dato for sending
     */
    Date getSent();

    /**
     * @param sentDate
     */
    void setSent(Date sentDate);

    /**
     * @return kollier
     */
    Set<Colli> getCollies();

    /**
     * @return ordrelinjer som ikke er sendt
     */
    List<OrderLine> getOrderLinesNotSent();

    /**
     * @return ordre
     */
    Order getOrder();

    /**
     * @return ordrelinjer
     */
    Set<OrderLine> getOrderLines();
    void setOrderLines(Set<OrderLine> orderLines);

    /**
     * @return ettersendinger
     */
    Set<PostShipment> getPostShipments();

    /**
     * @return ettersending
     */
    PostShipment getPostShipment();

    /**
     * @return manglende kollier
     */
    List<OrderLine> getMissingCollies();

    /**
     * @return transportstreng for rapport
     */
    String getTransportReportString();

    /**
     * @return garasjetype
     */
    ConstructionType getConstructionType();

    /**
     * @return dato for betaling
     */
    Date getPaidDate();

    /**
     * @return transportkommentarer
     */
    String getTransportComments();

    /**
     * @return h�yde p� garasjekolli
     */
    BigDecimal getGarageColliHeight();

    /**
     * Cache kommentarer
     */
    void cacheComments();

    /**
     * Cache h�yde p� garasjekolli
     */
    void cacheGarageColliHeight();

    /**
     * @return monteringsteam
     */
    String getAssemblyTeamName();

    /**
     * @return dato for pakkliste
     */
    Date getPacklistReady();

    /**
     * @return produktomr�degruppe
     */
    ProductAreaGroup getProductAreaGroup();

    OrderLine getOrderLine(String articlePath);

    boolean hasTransportCostBasis();

    Customer getCustomer();

    Assembly getAssembly();
    void setCustTrs(List<CustTr> custTrs);
    List<CustTr> getCustTrs();
    boolean isPaid();
    Date getProductionDate();
    ProcentDone getLastProcentDone();
    Deviation getDeviation();
    String getOrderNr();
    void setOrderNr(String orderNr);
    String getPackedBy();
    void setPackedBy(String packedBy);
    Set<OrderComment> getOrderComments();
    void setGarageColliHeight(BigDecimal garageColliHeight);
    Integer getColliesDone();
    void setColliesDone(Integer done);
    Boolean isDonePackage();
    Integer getTakstolHeight();
    String getManagerName();
    void cacheTakstolHeight();
    
    public static final Transportable UNKNOWN = new Transportable() {

        public void cacheComments() {}

        public void cacheGarageColliHeight() {}

        public Assembly getAssembly() {
            return Assembly.UNKNOWN;
        }

        public String getAssemblyTeamName() {
            return "";
        }

        public Set<Colli> getCollies() {
            return new HashSet<Colli>();
        }

        public Integer getColliesDone() {
            return 0;
        }

        public String getComment() {
            return "";
        }

        public ConstructionType getConstructionType() {
            return ConstructionType.UNKNOWN;
        }

        public List<CustTr> getCustTrs() {
            return new ArrayList<CustTr>();
        }

        public Customer getCustomer() {
            return Customer.UNKNOWN;
        }

        public Deviation getDeviation() {
            return Deviation.UNKNOWN;
        }

        public Integer getDoAssembly() {
            return 0;
        }

        public BigDecimal getGarageColliHeight() {
            return BigDecimal.ZERO;
        }

        public ProcentDone getLastProcentDone() {
            return ProcentDone.UNKNOWN;
        }

        public List<OrderLine> getMissingCollies() {
            return new ArrayList<OrderLine>();
        }

        public Order getOrder() {
            return Order.UNKNOWN;
        }

        public Set<OrderComment> getOrderComments() {
            return new HashSet<OrderComment>();
        }

        public Date getOrderComplete() {
            return null;
        }

        public OrderLine getOrderLine(String articlePath) {
            return OrderLine.UNKNOWN;
        }

        public Set<OrderLine> getOrderLines() {
            return new HashSet<OrderLine>();
        }

        public List<OrderLine> getOrderLinesNotSent() {
            return new ArrayList<OrderLine>();
        }

        public String getOrderNr() {
            return "";
        }

        public Date getOrderReady() {
            return null;
        }

        public Date getPackageStarted() {
            return null;
        }

        public String getPackedBy() {
            return "";
        }

        public Date getPacklistReady() {
            return null;
        }

        public Date getPaidDate() {
            return null;
        }

        public PostShipment getPostShipment() {
            return PostShipment.UNKNOWN;
        }

        public Set<PostShipment> getPostShipments() {
            return new HashSet<PostShipment>();
        }

        public ProductAreaGroup getProductAreaGroup() {
            return ProductAreaGroup.UNKNOWN;
        }

        public Date getProductionDate() {
            return null;
        }

        public Date getSent() {
            return null;
        }

        public Boolean getSentBool() {
            return Boolean.FALSE;
        }

        public String getSpecialConcern() {
            return "";
        }

        public String getStatus() {
            return "";
        }

        public Transport getTransport() {
            return Transport.UNKNOWN;
        }

        public String getTransportComments() {
            return "";
        }

        public String getTransportReportString() {
            return "";
        }

        public String getTransportString() {
            return "";
        }

        public boolean hasTransportCostBasis() {
            return false;
        }

        public Boolean isDonePackage() {
            return Boolean.FALSE;
        }

        public boolean isPaid() {
            return false;
        }

        public void setColliesDone(Integer done) {}

        public void setCustTrs(List<CustTr> custTr) {}

        public void setGarageColliHeight(BigDecimal garageColliHeight) {}

        public void setOrderComplete(Date date) {}

        public void setOrderLines(Set<OrderLine> orderLines) {}

        public void setOrderNr(String orderNr) {}

        public void setOrderReady(Date orderReady) {}

        public void setPackageStarted(Date date) {}

        public void setPackedBy(String packedBy) {}

        public void setSent(Date sentDate) {}

        public void setSentBool(Boolean isSent) {}

        public void setStatus(String status) {}

        public void setTransport(Transport transport) {}

        public Integer getTakstolHeight() {
            return Integer.valueOf(0);
        }

        public String getManagerName() {
            return "";
        }

        public void cacheTakstolHeight() {
        }

        public List<OrderLine> getOrderLineList(String articleTypeName) {
            return null;
        }

		public Integer getProbability() {
			return null;
		}

		public String getTrossDrawer() {
			return null;
		}

		public Integer getMaxTrossHeight() {
			return null;
		}
        
    };

    List<OrderLine> getOrderLineList(String articleTypeName);

	Integer getProbability();

	String getTrossDrawer();

	Integer getMaxTrossHeight();
    
}