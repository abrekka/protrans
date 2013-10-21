package no.ugland.utransprod.service;

import java.sql.Connection;

/**
 * Interface for databasemanager
 * @author atle.brekka
 */
public interface DatabaseManager {
    /**
     * Henter databaseforbindelse
     * @return databaseforbindelse
     */
    Connection getDbConnection();
}
