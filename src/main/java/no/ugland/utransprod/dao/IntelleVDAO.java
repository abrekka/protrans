package no.ugland.utransprod.dao;

import no.ugland.utransprod.model.IntelleV;

public interface IntelleVDAO {

	IntelleV findByOrderNr(String orderNr);

}
