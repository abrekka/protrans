package no.ugland.utransprod.dao;

import java.util.List;

import no.ugland.utransprod.model.WindowAccess;

/**
 * Interface for DAO mot tabell WINDOW_ACCESS
 * @author atle.brekka
 *
 */
public interface WindowAccessDAO extends DAO<WindowAccess> {
    List<WindowAccess> findAllWithTableNames();
}
