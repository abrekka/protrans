package no.ugland.utransprod.service;

import java.math.BigDecimal;
import java.util.Map;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.model.Transportable;
import no.ugland.utransprod.model.TransportCost;
import no.ugland.utransprod.model.TransportCostAddition;
import no.ugland.utransprod.util.Periode;

public interface TransportCostAdditionManager {
    TransportCostAddition findByDescription(String desc);

    void deleteAll();

    void saveTransportCostAddition(TransportCostAddition transportCostAddition);

    Map<ITransportCostAddition, BigDecimal> calculateCostAddition(
            Transportable transportable, TransportCost transportCost,
            Periode period) throws ProTransException;
}
