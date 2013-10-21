package no.ugland.utransprod.service.impl;

import java.io.Serializable;
import java.util.List;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.dao.ProcentDoneDAO;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.ProcentDone;
import no.ugland.utransprod.service.ProcentDoneManager;

public class ProcentDoneManagerImpl extends ManagerImpl<ProcentDone>implements ProcentDoneManager {

    public ProcentDone findByYearWeekOrder(Integer year, Integer week, Order order) {
        return ((ProcentDoneDAO)dao).findByYearWeekOrder(year, week, order);
    }

    public List<ProcentDone> findAll() {
        return dao.getObjects();
    }

    public List<ProcentDone> findByObject(ProcentDone object) {
        return dao.findByExampleLike(object);
    }

    public void refreshObject(ProcentDone object) {
        dao.refreshObject(object, object.getProcentDoneId());
        
    }

    public void removeObject(ProcentDone object) {
        dao.removeObject(object.getProcentDoneId());
        
    }

    public void saveObject(ProcentDone object) throws ProTransException {
        dao.saveObject(object);
        
    }


    @Override
    protected Serializable getObjectId(ProcentDone object) {
        return object.getProcentDoneId();
    }

}
