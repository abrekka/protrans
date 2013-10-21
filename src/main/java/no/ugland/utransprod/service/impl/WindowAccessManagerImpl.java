package no.ugland.utransprod.service.impl;

import java.util.List;

import no.ugland.utransprod.dao.WindowAccessDAO;
import no.ugland.utransprod.model.WindowAccess;
import no.ugland.utransprod.service.WindowAccessManager;

/**
 * Implementasjon av serviceklasse for tabell WINDOW_ACCESS.
 * @author atle.brekka
 */
public class WindowAccessManagerImpl implements WindowAccessManager {
    private WindowAccessDAO dao;

    /**
     * @param aDao
     */
    public final void setWindowAccessDAO(final WindowAccessDAO aDao) {
        this.dao = aDao;
    }

    /**
     * @see no.ugland.utransprod.service.WindowAccessManager#findAll()
     */
    public final List<WindowAccess> findAll() {
        return dao.getObjects("windowName");
    }

    public List<WindowAccess> findAllWithTableNames() {
        return dao.findAllWithTableNames();
    }

}
