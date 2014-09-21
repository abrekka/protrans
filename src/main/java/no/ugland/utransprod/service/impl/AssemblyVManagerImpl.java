package no.ugland.utransprod.service.impl;

import java.util.List;

import no.ugland.utransprod.dao.AssemblyVDAO;
import no.ugland.utransprod.model.AssemblyV;
import no.ugland.utransprod.service.AssemblyVManager;

public class AssemblyVManagerImpl implements AssemblyVManager {

    private AssemblyVDAO dao;

    public final void setAssemblyVDAO(final AssemblyVDAO aDao) {
	this.dao = aDao;
    }

    public List<AssemblyV> findByYear(int aar) {
	return ((AssemblyVDAO) dao).findByYear(aar);
    }

}
