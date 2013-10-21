package no.ugland.utransprod.service.impl;

import no.ugland.utransprod.model.TransportCostAddition;
import no.ugland.utransprod.service.ITransportCostAddition;

public abstract class AbstractAddition implements ITransportCostAddition {
    TransportCostAddition transportCostAdditon;

    String articlePath;

    String info;

    public AbstractAddition(final TransportCostAddition addition,
            final String aArticlePath, final String someInfo) {
        transportCostAdditon = addition;
        articlePath = aArticlePath;
        info = someInfo;
    }

    public final String getArticlePath() {
        return articlePath;
    }

    public final String getInfo() {
        return info;
    }
}
