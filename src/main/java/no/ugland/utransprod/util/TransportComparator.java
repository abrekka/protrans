package no.ugland.utransprod.util;

import java.util.Comparator;

import no.ugland.utransprod.model.Transport;

import org.apache.commons.lang.builder.CompareToBuilder;

public class TransportComparator implements Comparator<Transport> {
    /**
     * @param transport1
     * @param transport2
     * @return 1 dersom større, -1 dersom mindre og 0 dersom lik
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    public final int compare(final Transport transport1, final Transport transport2) {
        if (transport1 != null && transport2 != null) {
            return new CompareToBuilder().append(Util.convertDate(transport1.getLoadingDate(),Util.SHORT_DATE_FORMAT),
                    Util.convertDate(transport2.getLoadingDate(),Util.SHORT_DATE_FORMAT)).append(
                    transport1.getLoadTime(), transport2.getLoadTime()).append(
                    transport1.getTransportName(),
                    transport2.getTransportName()).toComparison();

        } else if (transport1 != null) {
            return -1;
        } else if (transport2 != null) {
            return 1;
        }
        return 0;
    }
}