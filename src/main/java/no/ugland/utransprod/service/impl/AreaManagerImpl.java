package no.ugland.utransprod.service.impl;

import no.ugland.utransprod.dao.AreaDAO;
import no.ugland.utransprod.dao.TransportCostDAO;
import no.ugland.utransprod.model.Area;
import no.ugland.utransprod.service.AreaManager;

public class AreaManagerImpl implements AreaManager {
    private AreaDAO dao;

    private TransportCostDAO transportCostDAO;

    /**
     * Setter dao-klasse.
     * @param aDao
     */
    public final void setAreaDAO(final AreaDAO aDao) {
        this.dao = aDao;
    }

    public final void setTransportCostDAO(final TransportCostDAO aDao) {
        this.transportCostDAO = aDao;
    }

    public final Area load(final String areaCode) {
        return dao.getObject(areaCode);
    }

    public final void removeAll() {
        transportCostDAO.removeAll();
        dao.removeAll();

    }

    public final void saveArea(final Area area) {
        dao.saveObject(area);

    }

	public Area findByAreaCode(String areaCode) {
		return dao.findByAreaCode(areaCode);
	}

}
