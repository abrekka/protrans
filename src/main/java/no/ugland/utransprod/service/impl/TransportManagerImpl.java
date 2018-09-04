package no.ugland.utransprod.service.impl;

import java.io.Serializable;
import java.util.List;

import no.ugland.utransprod.dao.TransportDAO;
import no.ugland.utransprod.model.Transport;
import no.ugland.utransprod.service.TransportManager;
import no.ugland.utransprod.service.enums.LazyLoadTransportEnum;
import no.ugland.utransprod.util.Periode;

/**
 * Implementasjon av manager for transport.
 * 
 * @author atle.brekka
 */
public class TransportManagerImpl extends ManagerImpl<Transport> implements TransportManager {

	/**
	 * @see no.ugland.utransprod.service.TransportManager#findAll()
	 */
	public final List<Transport> findAll() {
		return ((TransportDAO) dao).findAll();
	}

	/**
	 * @see no.ugland.utransprod.service.TransportManager#saveTransport(no.ugland.utransprod.model.Transport)
	 */
	public final void saveTransport(final Transport transport) {
		dao.saveObject(transport);

	}

	/**
	 * @see no.ugland.utransprod.service.TransportManager#
	 *      removeTransport(no.ugland.utransprod.model.Transport)
	 */
	public final void removeTransport(final Transport transport) {
		dao.removeObject(transport.getTransportId());

	}

	/**
	 * @see no.ugland.utransprod.service.TransportManager#findByYearAndWeek(java.lang.Integer,
	 *      java.lang.Integer)
	 */
	public final List<Transport> findByYearAndWeek(final Integer year, final Integer week) {
		return ((TransportDAO) dao).findByYearAndWeek(year, week);
	}

	/**
	 * Finner basert på eksempel.
	 * 
	 * @param transport
	 * @return liste
	 */
	public final List<Transport> findByTransport(final Transport transport) {
		return dao.findByExampleLike(transport);
	}

	/**
	 * @param object
	 * @return liste
	 * @see no.ugland.utransprod.service.OverviewManager#findByObject(java.lang.Object)
	 */
	public final List<Transport> findByObject(final Transport object) {
		return findByTransport(object);
	}

	/**
	 * @param transport
	 * @see no.ugland.utransprod.service.OverviewManager#refreshObject(java.lang.Object)
	 */
	public final void refreshObject(final Transport transport) {
		((TransportDAO) dao).refreshObject(transport);

	}

	/**
	 * @param object
	 * @see no.ugland.utransprod.service.OverviewManager#removeObject(java.lang.Object)
	 */
	public final void removeObject(final Transport object) {
		removeTransport(object);

	}

	/**
	 * @param object
	 * @see no.ugland.utransprod.service.OverviewManager#saveObject(java.lang.Object)
	 */
	public final void saveObject(final Transport object) {
		saveTransport(object);

	}

	/**
	 * @see no.ugland.utransprod.service.TransportManager#
	 *      lazyLoadTransport(no.ugland.utransprod.model.Transport,
	 *      no.ugland.utransprod.service.enums.LazyLoadTransportEnum[])
	 */
	public final void lazyLoadTransport(final Transport transport, final LazyLoadTransportEnum[] enums) {
		((TransportDAO) dao).lazyLoadTransport(transport, enums);

	}

	public final List<Transport> findBetweenYearAndWeek(final Integer year, final Integer fromWeek,
			final Integer toWeek, final String[] orderBy) {
		return ((TransportDAO) dao).findBetweenYearAndWeek(year, fromWeek, toWeek, orderBy);
	}

	/**
	 * @see no.ugland.utransprod.service.TransportManager#findNewTransports()
	 */
	public final List<Transport> findNewTransports() {
		return ((TransportDAO) dao).findNewTransports();
	}

	public final List<Transport> findByYearAndWeekAndProductAreaGroup(final Integer year, final Integer week,
			final boolean ikkeTaMedOpplastet) {
		// if (productAreaGroup == null) {
		// return ((TransportDAO) dao).findByYearAndWeek(year, week);
		// }
		return ((TransportDAO) dao).findByYearAndWeekAndProductAreaGroup(year, week, ikkeTaMedOpplastet);
	}

	public final List<Transport> findSentInPeriode(final Periode periode) {
		return ((TransportDAO) dao).findSentInPeriode(periode);
	}

	public final List<Transport> findInPeriode(final Periode periode, final String productAreaGroupName) {
		return ((TransportDAO) dao).findInPeriode(periode, productAreaGroupName);
	}

	public void lazyLoad(Transport object, Enum[] enums) {
		lazyLoadTransport(object, (LazyLoadTransportEnum[]) enums);

	}

	@Override
	protected Serializable getObjectId(Transport object) {
		return object.getTransportId();
	}

	public Transport findById(Integer id) {
		return ((TransportDAO) dao).getObject(id);
	}

}
