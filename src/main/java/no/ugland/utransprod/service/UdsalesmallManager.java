package no.ugland.utransprod.service;

import no.ugland.utransprod.model.Udsalesmall;

public interface UdsalesmallManager {

	String MANAGER_NAME = "udsalesmallManager";

	Udsalesmall findByOrderNr(String orderNr);

}
