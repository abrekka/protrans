package no.ugland.utransprod.service.impl;

import java.io.Serializable;
import java.util.List;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.dao.AttributeChoiceDAO;
import no.ugland.utransprod.model.AttributeChoice;
import no.ugland.utransprod.service.AttributeChoiceManager;

public class AttributeChoiceManagerImpl extends ManagerImpl<AttributeChoice>implements AttributeChoiceManager {
    
    public List<AttributeChoice> findAll() {
        return dao.getObjects();
    }

    public List<AttributeChoice> findByObject(AttributeChoice object) {
        return dao.findByExampleLike(object);
    }

    public void lazyLoad(AttributeChoice object, Enum[] enums) {
    }

    public void refreshObject(AttributeChoice object) {
        dao.refreshObject(object, object.getAttributeChoiceId());
        
    }

    public void removeObject(AttributeChoice object) {
        dao.removeObject(object.getAttributeChoiceId());
        
    }

    public void saveObject(AttributeChoice object) throws ProTransException {
        dao.saveObject(object);
        
    }

    @Override
    protected Serializable getObjectId(AttributeChoice object) {
        return object.getAttributeChoiceId();
    }

}
