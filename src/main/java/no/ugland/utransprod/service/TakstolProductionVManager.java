package no.ugland.utransprod.service;

import java.util.List;

import no.ugland.utransprod.model.Produceable;
import no.ugland.utransprod.model.TakstolProductionV;

/**
 * Interface for serviceklasse mot view TAKSTOL_PRODUCTION_V
 * 
 * @author atle.brekka
 */
public interface TakstolProductionVManager extends
		IApplyListManager<Produceable>, TakstolVManager {

	String MANAGER_NAME = "takstolProductionVManager";

	List<Produceable> findApplyableByOrderNrAndArticleName(String orderNr,
			String articleName);

	List<TakstolProductionV> findByOrderNr(String string);

}
