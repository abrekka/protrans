package no.ugland.utransprod.service;

import java.util.List;

import no.ugland.utransprod.model.WindowAccess;

/**
 * Interface for serviceklasse mot tabell WINDOW_ACCESS
 * @author atle.brekka
 */
public interface WindowAccessManager {
    /**
     * Finner alle
     * @return vindusakksess
     */
    List<WindowAccess> findAll();
    List<WindowAccess> findAllWithTableNames();
    
}
