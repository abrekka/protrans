package no.ugland.utransprod.service;

import java.util.List;

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

    List<Transport> findAll();

    void saveTransport(Transport transport);

    void removeTransport(Transport transport);

    List<Transport> findByYearAndWeek(Integer year, Integer week);

    void lazyLoadTransport(Transport transport, LazyLoadTransportEnum[] enums);

    List<Transport> findBetweenYearAndWeek(Integer year, Integer fromWeek, Integer toWeek, String[] orderBy);

    List<Transport> findNewTransports();

    List<Transport> findByYearAndWeekAndProductAreaGroup(Integer year, Integer week);

    List<Transport> findSentInPeriode(Periode periode);

    List<Transport> findInPeriode(Periode periode, String productAreaGroupName);

    Transport findById(Integer id);
}
