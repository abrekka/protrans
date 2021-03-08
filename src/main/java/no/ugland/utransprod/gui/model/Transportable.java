
// Warning: No line numbers available in class file
package no.ugland.utransprod.gui.model;

import java.util.Date;
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

public interface Transportable {

   String getStatus();

   Integer getDoAssembly();

   Transport getTransport();

   Boolean getSentBool();

   Date getOrderReady();

   void setOrderReady(Date var1);

   Date getOrderComplete();

   void setOrderComplete(Date var1);

   Date getPackageStarted();

   void setPackageStarted(Date var1);

   String getTransportString();

   String getComment();

   void setStatus(String var1);

   void setSentBool(Boolean var1);

   void setTransport(Transport var1);

   String getSpecialConcern();

   Date getSent();

   void setSent(Date var1);

   Set<Colli> getCollies();

   List<OrderLine> getOrderLinesNotSent();

   Order getOrder();

   Set<OrderLine> getOrderLines();

   void setOrderLines(Set<OrderLine> var1);

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

   OrderLine getOrderLine(String var1);

   boolean hasTransportCostBasis();

   Customer getCustomer();

   Assembly getAssembly();

   void setCustTrs(List<CustTr> var1);

   List<CustTr> getCustTrs();

   boolean isPaid();

   Date getProductionDate();

   ProcentDone getLastProcentDone();

   Deviation getDeviation();

   String getOrderNr();

   void setOrderNr(String var1);

   String getPackedBy();

   String getPackedByTross();

   String getPackedByPack();

   void setPackedBy(String var1);

   void setPackedByTross(String var1);

   void setPackedByPack(String var1);

   Set<OrderComment> getOrderComments();

   void setGarageColliHeight(Integer var1);

   Integer getColliesDone();

   void setColliesDone(Integer var1);

   Boolean isDonePackage();

   Integer getTakstolHeight();

   String getManagerName();

   void cacheTakstolHeight();

   List<OrderLine> getOrderLineList(String var1);

   Integer getProbability();

   String getTrossDrawer();

   Integer getMaxTrossHeight();

   Integer getProductionWeek();

   Date getOrderReadyWall();

   void setOrderReadyWall(Date var1);

   Date getOrderReadyTross();

   Date getOrderReadyPack();

   void setOrderReadyTross(Date var1);

   void setOrderReadyPack(Date var1);

   void setTakstolKjopOrd(Ord var1);

   Ord getTakstolKjopOrd();

   Boolean getLevertBool();

   void setLevert(Date var1);
}
