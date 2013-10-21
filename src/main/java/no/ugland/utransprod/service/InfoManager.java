package no.ugland.utransprod.service;

import java.util.Date;
import java.util.List;

import no.ugland.utransprod.model.Info;

/**
 * Interface for serviceklasse mot tabell INFO
 * @author atle.brekka
 */
public interface InfoManager extends OverviewManager<Info> {
    String MANAGER_NAME = "infoManager";

	/**
     * Finn basert på dato
     * @param date
     * @return info
     */
    Info findByDate(Date date);

    /**
     * Finner basert på dato
     * @param date
     * @return info
     */
    List<String> findListByDate(Date date);
}
