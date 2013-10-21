package no.ugland.utransprod.service;

import no.ugland.utransprod.model.AssemblyOverdueV;

/**
 * Interface for serviceklasse mot view ASSEMBLY_OVERDUE_V
 * 
 * @author atle.brekka
 */
public interface AssemblyOverdueVManager {
	String MANAGER_NAME = "assemblyOverdueVManager";

	/**
	 * Finner montering som er lengst på overtid
	 * 
	 * @return montering
	 */
	AssemblyOverdueV getAssemblyOverdueV();
}
