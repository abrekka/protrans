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
 * Interface for klasser som kan listes opp i forbindelse med transport
 * @author atle.brekka
 */
public interface TransportListable {
    /**
     * @param sentDate
     */
    void setSent(Date sentDate);

    /**
     * @return dato for sending
     */
    Date getSent();

    /**
     * @return transport
     */
    Transport getTransport();

    /**
     * @return kollier
     */
    Set<Colli> getCollies();

    /**
     * @param isSent
     */
    void setSentBool(Boolean isSent);

    /**
     * @return ordrelinjer som ikke er sendt
     */
    List<OrderLine> getOrderLinesNotSent();

    /**
     * @return ordre
     */
    Order getOrder();
    ProductAreaGroup getProductAreaGroup();

    /**
     * @return om sendt
     */
    Boolean getSentBool();
    boolean isPaid();
    
    public static final TransportListable UNKNOWN = new TransportListable() {

        public Set<Colli> getCollies() {
            return new HashSet<Colli>();
        }

        public Order getOrder() {
            return Order.UNKNOWN;
        }

        public List<OrderLine> getOrderLinesNotSent() {
            return new ArrayList<OrderLine>();
        }

        public Date getSent() {
            return null;
        }

        public Boolean getSentBool() {
            return Boolean.FALSE;
        }

        public Transport getTransport() {
            return Transport.UNKNOWN;
        }

        public void setSent(Date sentDate) {}

        public void setSentBool(Boolean isSent) {}

        public ProductAreaGroup getProductAreaGroup() {
            return ProductAreaGroup.UNKNOWN;
        }

        public boolean isPaid() {
            return false;
        }

		public Integer getProbability() {
			// TODO Auto-generated method stub
			return null;
		}

		public void setLevert(Date levertDate) {
			// TODO Auto-generated method stub
			
		}

		public Boolean getLevertBool() {
			// TODO Auto-generated method stub
			return false;
		}

		public List<OrderLine> getOrderLinesNotLevert() {
			// TODO Auto-generated method stub
			return null;
		}
    };

	Integer getProbability();

	void setLevert(Date levertDate);

	Boolean getLevertBool();

	List<OrderLine> getOrderLinesNotLevert();
}
