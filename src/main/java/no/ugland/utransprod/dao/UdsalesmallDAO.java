package no.ugland.utransprod.dao;

import no.ugland.utransprod.model.Udsalesmall;

public interface UdsalesmallDAO extends DAO<Udsalesmall> {

	Udsalesmall findByOrderNr(String orderNr);
    
}
