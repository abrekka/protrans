package no.ugland.utransprod.dao;

import no.ugland.utransprod.model.AssemblyOverdueV;

/**
 * Interface for DAO mot view ASSEMBLY_OVERDUE_V
 * @author atle.brekka
 *
 */
public interface AssemblyOverdueVDAO extends DAO<AssemblyOverdueV>{
	/**
	 * Henter eldste
	 * @return eldste
	 */
	AssemblyOverdueV getOldest();
}
