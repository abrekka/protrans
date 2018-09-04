package no.ugland.utransprod.gui.model;

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
import no.ugland.utransprod.model.Ord;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderComment;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.PostShipment;
import no.ugland.utransprod.model.ProcentDone;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.model.Transport;

/**
 * Interface for klasser som kan transporteres
 * 
 * @author atle.brekka
 */
public interface Transportable {
    String getStatus();

    Integer getDoAssembly();

    Transport getTransport();

    Boolean getSentBool();

    Date getOrderReady();

    void setOrderReady(Date orderReady);

    Date getOrderComplete();

    void setOrderComplete(Date date);

    Date getPackageStarted();

    void setPackageStarted(Date date);

    String getTransportString();

    String getComment();

    void setStatus(String status);

    void setSentBool(Boolean isSent);

    void setTransport(Transport transport);

    String getSpecialConcern();

    Date getSent();

    void setSent(Date sentDate);

    Set<Colli> getCollies();

    List<OrderLine> getOrderLinesNotSent();

    Order getOrder();

    Set<OrderLine> getOrderLines();

    void setOrderLines(Set<OrderLine> orderLines);

    Set<PostShipment> getPostShipments();

    PostShipment getPostShipment();

    List<OrderLine> getMissingCollies();

    String getTransportReportString();

    ConstructionType getConstructionType();

    Date getPaidDate();

    String getTransportComments();

    Integer getGarageColliHeight();

    void cacheComments();

    void cacheGarageColliHeight();

    String getAssemblyTeamName();

    Date getPacklistReady();

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

    String getPackedByTross();

    String getPackedByPack();

    void setPackedBy(String packedBy);

    void setPackedByTross(String packedBy);

    void setPackedByPack(String packedBy);

    Set<OrderComment> getOrderComments();

    void setGarageColliHeight(Integer garageColliHeight);

    Integer getColliesDone();

    void setColliesDone(Integer done);

    Boolean isDonePackage();

    Integer getTakstolHeight();

    String getManagerName();

    void cacheTakstolHeight();

    public static final Transportable UNKNOWN = new Transportable() {

	public void cacheComments() {
	}

	public void cacheGarageColliHeight() {
	}

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

	public Integer getGarageColliHeight() {
	    return 0;
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

	public void setColliesDone(Integer done) {
	}

	public void setCustTrs(List<CustTr> custTr) {
	}

	public void setGarageColliHeight(Integer garageColliHeight) {
	}

	public void setOrderComplete(Date date) {
	}

	public void setOrderLines(Set<OrderLine> orderLines) {
	}

	public void setOrderNr(String orderNr) {
	}

	public void setOrderReady(Date orderReady) {
	}

	public void setPackageStarted(Date date) {
	}

	public void setPackedBy(String packedBy) {
	}

	public void setSent(Date sentDate) {
	}

	public void setSentBool(Boolean isSent) {
	}

	public void setStatus(String status) {
	}

	public void setTransport(Transport transport) {
	}

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

	public Date getActionStarted() {
	    // TODO Auto-generated method stub
	    return null;
	}

	public Date getProduced() {
	    // TODO Auto-generated method stub
	    return null;
	}

	public Integer getProductionWeek() {
	    // TODO Auto-generated method stub
	    return null;
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

	public void setTakstolKjopOrd(Ord ord) {
	    // TODO Auto-generated method stub

	}

	public Ord getTakstolKjopOrd() {
	    // TODO Auto-generated method stub
	    return null;
	}

	public Object getLevertBool() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setLevert(Date levertDate) {
		// TODO Auto-generated method stub
		
	}

    };

    List<OrderLine> getOrderLineList(String articleTypeName);

    Integer getProbability();

    String getTrossDrawer();

    Integer getMaxTrossHeight();

    Integer getProductionWeek();

    Date getOrderReadyWall();

    void setOrderReadyWall(Date orderReady);

    Date getOrderReadyTross();

    Date getOrderReadyPack();

    void setOrderReadyTross(Date orderReady);

    void setOrderReadyPack(Date orderReady);

    void setTakstolKjopOrd(Ord ord);

    Ord getTakstolKjopOrd();

	Object getLevertBool();

	void setLevert(Date levertDate);

}
