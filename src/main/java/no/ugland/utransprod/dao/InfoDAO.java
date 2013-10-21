package no.ugland.utransprod.dao;

import java.util.Date;
import java.util.List;

import no.ugland.utransprod.model.Info;

/**
 * Interface for DAO mot tabell INFO
 * @author atle.brekka
 *
 */
public interface InfoDAO extends DAO<Info> {
	/**
	 * Finner alle
	 * @return info
	 */
	List<Info> findAll();

	/**
	 * Oppdater objekt
	 * @param info
	 */
	void refreshObject(Info info);

	/**
	 * Finner info for gitt dato
	 * @param date
	 * @return info
	 */
	List<Info> findByDate(Date date);
}
