package no.ugland.utransprod.service.impl;

import java.io.Serializable;
import java.util.List;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.dao.DAO;
import no.ugland.utransprod.service.Manager;
import no.ugland.utransprod.service.enums.LazyLoadEnum;

public abstract class ManagerImpl<T> implements Manager<T> {
    protected DAO<T> dao;

    public void setDao(DAO<T> aDao) {
        dao = aDao;
    }

    protected abstract Serializable getObjectId(T object);

    public List<T> findAll() {
        return dao.getObjects();
    }

    public List<T> findByObject(T object) {
        return dao.findByExampleLike(object);
    }

    public void refreshObject(T object) {
        dao.refreshObject(object, getObjectId(object));

    }

    public void removeObject(T object) {
        dao.removeObject(getObjectId(object));

    }

    public void saveObject(T object) throws ProTransException {
        dao.saveObject(object);

    }

    public void lazyLoad(T object, LazyLoadEnum[][] enums) {
        if (object != null) {
            dao.lazyLoad(object, getObjectId(object), enums);
        }
    }
}
