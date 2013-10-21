package no.ugland.utransprod.service;

import java.util.List;

import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.model.Transport;
import no.ugland.utransprod.service.enums.LazyLoadTransportEnum;
import no.ugland.utransprod.util.Periode;

/**
 * Interface for manager for transport
 * 
 * @author atle.brekka
 */
public interface TransportManager extends OverviewManager<Transport> {
	public static final String MANAGER_NAME = "transportManager";

	/**
	 * Hent alle
	 * 
	 * @return transportruter
	 */
	List<Transport> findAll();

	/**
	 * Lagrer transport
	 * 
	 * @param transport
	 */
	void saveTransport(Transport transport);

	/**
	 * Fjerner transport
	 * 
	 * @param transport
	 */
	void removeTransport(Transport transport);

	/**
	 * Finner transport for gitt år og uke
	 * 
	 * @param year
	 * @param week
	 * @return transportliste
	 */
	List<Transport> findByYearAndWeek(Integer year, Integer week);

	/**
	 * Lazy laster transport
	 * 
	 * @param transport
	 * @param enums
	 */
	void lazyLoadTransport(Transport transport, LazyLoadTransportEnum[] enums);

	/**
	 * Finner for gitt periode
	 * 
	 * @param year
	 * @param fromWeek
	 * @param toWeek
	 * @param orderBy
	 * @return transport
	 */
	List<Transport> findBetweenYearAndWeek(Integer year, Integer fromWeek,
			Integer toWeek, String[] orderBy);

	/**
	 * Finner transport som er fra i dag og fremover
	 * 
	 * @return transport
	 */
	List<Transport> findNewTransports();

	List<Transport> findByYearAndWeekAndProductAreaGroup(Integer year,
			Integer week, ProductAreaGroup productAreaGroup);

	List<Transport> findSentInPeriode(Periode periode);

	List<Transport> findInPeriode(Periode periode, String productAreaGroupName);

	Transport findById(Integer id);
}
