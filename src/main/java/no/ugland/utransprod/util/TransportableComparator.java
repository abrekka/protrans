package no.ugland.utransprod.util;

import java.util.Comparator;

import org.apache.commons.lang.builder.CompareToBuilder;

import no.ugland.utransprod.gui.model.Transportable;
import no.ugland.utransprod.model.Transport;

public class TransportableComparator implements Comparator<Transportable> {

    public final int compare(final Transportable transportable1,
            final Transportable transportable2) {
        Transport transport1 = transportable1.getTransport();
        Transport transport2 = transportable2.getTransport();
        if (transport1 != null && transport2 != null) {
            return new CompareToBuilder().append(transport1.getTransportYear(),
                    transport2.getTransportYear()).append(
                    transport1.getTransportWeek(),
                    transport2.getTransportWeek()).append(
                    transport1.getLoadingDate(), transport2.getLoadingDate())
                    .append(transport1.getTransportName(),
                            transport2.getTransportName()).toComparison();

        } else if (transport1 != null) {
            return -1;
        } else if (transport2 != null) {
            return 1;
        }
        return 0;
    }

}
