package no.ugland.utransprod.service.impl;

import java.sql.Connection;

import no.ugland.utransprod.dao.pkg.DatabasePkgDAO;
import no.ugland.utransprod.service.DatabaseManager;

/**
 * Implementasjon av databasemanager.
 * @author atle.brekka
 */
public class DatabaseManagerImpl implements DatabaseManager {
    private DatabasePkgDAO dao;

    public final void setDatabasePkgDAO(final DatabasePkgDAO aDao) {
        this.dao = aDao;
    }

    /**
     * @see no.ugland.utransprod.service.DatabaseManager#getDbConnection()
     */
    public final Connection getDbConnection() {
        return dao.getDbConnection();
    }

}
