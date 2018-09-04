package no.ugland.utransprod.dao;

import java.util.List;

import no.ugland.utransprod.model.Transport;
import no.ugland.utransprod.service.enums.LazyLoadTransportEnum;
import no.ugland.utransprod.util.Periode;

/**
 * Interface for DAO mot tabell TRANSPORT
 * 
 * @author atle.brekka
 */
public interface TransportDAO extends DAO<Transport> {
	void refreshObject(Transport transport);

	void lazyLoadTransport(Transport transport, LazyLoadTransportEnum[] enums);

	List<Transport> findAll();

	List<Transport> findByYearAndWeek(Integer year, Integer week);

	List<Transport> findBetweenYearAndWeek(Integer year, Integer fromWeek, Integer toWeek, String[] orderBy);

	List<Transport> findNewTransports();

	List<Transport> findByYearAndWeekAndProductAreaGroup(Integer year, Integer week, boolean ikkeTaMedOpplastet);

	List<Transport> findSentInPeriode(Periode periode);

	List<Transport> findInPeriode(Periode periode, String productAreaGroupName);
}
