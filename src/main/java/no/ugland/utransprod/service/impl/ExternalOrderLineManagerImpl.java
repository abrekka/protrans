package no.ugland.utransprod.service.impl;

import java.io.Serializable;

import no.ugland.utransprod.model.ExternalOrderLine;
import no.ugland.utransprod.service.ExternalOrderLineManager;

/**
 * Implementasjon av serviceklasse for tabell EXTERNAL_ORDER_LINE.
 * @author atle.brekka
 */
public class ExternalOrderLineManagerImpl extends ManagerImpl<ExternalOrderLine>implements ExternalOrderLineManager {

    @Override
    protected Serializable getObjectId(ExternalOrderLine object) {
        return object.getExternalOrderLineId();
    }

}
