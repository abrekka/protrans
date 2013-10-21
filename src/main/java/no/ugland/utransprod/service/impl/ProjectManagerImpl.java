package no.ugland.utransprod.service.impl;

import no.ugland.utransprod.dao.ProjectDAO;
import no.ugland.utransprod.model.Project;
import no.ugland.utransprod.service.ProjectManager;

public class ProjectManagerImpl implements ProjectManager {
    private ProjectDAO dao;


    /**
     * @param aDao
     */
    public final void setProjectDAO(final ProjectDAO aDao) {
        this.dao = aDao;
    }


    public Project findByOrderNr(String orderNr) {
        return dao.findByOrderNr(orderNr);
    }


}
