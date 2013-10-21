package no.ugland.utransprod.service;

import java.math.BigDecimal;

import no.ugland.utransprod.gui.model.Transportable;
import no.ugland.utransprod.util.Periode;

public interface ITransportCostAddition {
    BigDecimal calculateAddition(BigDecimal basis, Transportable transportable,
            Periode period, boolean ignoreSent);

    String getArticlePath();

    String getInfo();
}
