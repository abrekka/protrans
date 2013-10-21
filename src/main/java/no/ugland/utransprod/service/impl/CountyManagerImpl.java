package no.ugland.utransprod.service.impl;

import no.ugland.utransprod.dao.CountyDAO;
import no.ugland.utransprod.model.County;
import no.ugland.utransprod.service.AreaManager;
import no.ugland.utransprod.service.CountyManager;

public class CountyManagerImpl implements CountyManager {
    private CountyDAO dao;
    private AreaManager areaManager;

    /**
     * Setter dao-klasse.
     * @param aDao
     */
    public final void setCountyDAO(final CountyDAO aDao) {
        this.dao = aDao;
    }
    public final void setAreaManager(final AreaManager aAreaManager) {
        this.areaManager = aAreaManager;
    }

    public final County load(final String countyNr) {
        return dao.getObject(countyNr);
    }

    public final void removeAll() {
        areaManager.removeAll();
        dao.removeAll();

    }

    public final void saveCounty(final County county) {
        dao.saveObject(county);

    }
    

}
