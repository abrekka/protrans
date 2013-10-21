package no.ugland.utransprod.service.impl;

import no.ugland.utransprod.dao.AssemblyOverdueVDAO;
import no.ugland.utransprod.model.AssemblyOverdueV;
import no.ugland.utransprod.service.AssemblyOverdueVManager;

/**
 * Implementasjon av serviceklasse for view ASSEMBLY_OVERDUE_V.
 * @author atle.brekka
 */
public class AssemblyOverdueVManagerImpl implements AssemblyOverdueVManager {
    private AssemblyOverdueVDAO dao;

    public final void setAssemblyOverdueVDAO(final AssemblyOverdueVDAO aDao) {
        this.dao = aDao;
    }

    /**
     * @see no.ugland.utransprod.service.AssemblyOverdueVManager#getAssemblyOverdueV()
     */
    public final AssemblyOverdueV getAssemblyOverdueV() {
        return dao.getOldest();

    }

}
