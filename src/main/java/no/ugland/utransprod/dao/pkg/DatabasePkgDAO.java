package no.ugland.utransprod.dao.pkg;

import java.sql.Connection;

/**
 * Interface for DAO som henter databaseforbindelse
 * @author atle.brekka
 *
 */
public interface DatabasePkgDAO {
	/**
	 * Henter databaseforbindelse
	 * @return databaseforbindelse
	 */
	Connection getDbConnection();
}
