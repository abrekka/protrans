package no.ugland.utransprod.service.impl;

import no.ugland.utransprod.dao.IntelleVDAO;
import no.ugland.utransprod.model.IntelleV;
import no.ugland.utransprod.service.IntelleVManager;

public class IntelleVManagerImpl implements IntelleVManager{
	private IntelleVDAO dao;
	public IntelleV findByOrdreNr(String orderNr) {
		return dao.findByOrderNr(orderNr);
	}
	public void setIntelleVDAO(IntelleVDAO dao) {
		this.dao = dao;
	}

}
