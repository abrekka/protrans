package no.ugland.utransprod.service;

import no.ugland.utransprod.model.IntelleV;

public interface IntelleVManager {

	public static final String MANAGER_NAME = "intelleVManager";

	IntelleV findByOrdreNr(String orderNr);

}
