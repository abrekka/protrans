package no.ugland.utransprod.service;

import no.ugland.utransprod.model.TakstolV;

public interface TakstolVManager {
    TakstolV findByOrderNrAndOrderLineId(String orderNr,Integer orderLineId);
}
