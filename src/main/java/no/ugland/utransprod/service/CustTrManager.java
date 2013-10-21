package no.ugland.utransprod.service;

import java.util.List;

import no.ugland.utransprod.model.CustTr;

public interface CustTrManager {
	String MANAGER_NAME = "custTrManager";

	List<CustTr> findByOrderNr(String orderNr);
}
