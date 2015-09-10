package no.ugland.utransprod.util;

import java.math.BigDecimal;

import no.ugland.utransprod.model.Colli;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;

/**
 * Brukes ved telling av antall klae ordre.
 * 
 * @author atle.brekka
 */
public class ReadyCount {
    private Order order;

    private Colli colli;

    private OrderLine missing;

    private int row;

    /**
     * @param aOrder
     * @param aColli
     * @param aOrderLine
     * @param aRow
     */
    public ReadyCount(final Order aOrder, final Colli aColli, final OrderLine aOrderLine, final int aRow) {
	order = aOrder;
	colli = aColli;
	missing = aOrderLine;
	row = aRow;
    }

    /**
     * Henter ordreinfo.
     * 
     * @return ordreinfo
     */
    public final String getOrderString() {
	// if (row != 1) {
	// return null;
	// }
	return order.getOrderString();
    }

    /**
     * Henter transport.
     * 
     * @return transportdato
     */
    public final String getTransport() {
	if (row != 1 || order.getTransport() == null) {
	    return null;
	}
	return order.getTransport().getFormatedYearWeek();

    }

    /**
     * Henter kollinavn.
     * 
     * @return kollinavn
     */
    public final String getColli() {
	if (colli != null) {
	    return colli.getColliName();
	}
	return null;
    }

    /**
     * Henter mangel.
     * 
     * @return artikkelnavn på mangel
     */
    public final String getMissing() {
	if (missing != null) {
	    return missing.getArticleName();
	}
	return null;
    }

    /**
     * Henter garasjeverdi.
     * 
     * @return garasjeverdi
     */
    public final BigDecimal getGarageValue() {
	if (row != 1) {
	    return null;
	}
	return order.getCost("Egenproduksjon", "Kunde");
    }

    public final BigDecimal getGarageValueInternal() {
	if (row != 1) {
	    return null;
	}
	return order.getCost("Egenproduksjon", "Intern");
    }

    /**
     * Henter transportverdi.
     * 
     * @return transportverdi
     */
    public final BigDecimal getTransportValue() {
	if (row != 1) {
	    return null;
	}
	return order.getCost("Frakt", "Kunde");
    }

    /**
     * Henter monteringsverdi.
     * 
     * @return monteringsverdi
     */
    public final BigDecimal getAssemblyValue() {
	if (row != 1) {
	    return null;
	}
	return order.getCost("Montering", "Kunde");
    }

    public String getOrderNr() {
	return order.getOrderNr();
    }
}
