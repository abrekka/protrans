package no.ugland.utransprod.service.impl;

import java.io.Serializable;
import java.util.List;

import no.ugland.utransprod.model.Accident;
import no.ugland.utransprod.service.AccidentManager;

public class AccidentManagerImpl extends ManagerImpl<Accident> implements AccidentManager {
    public final void saveAccident(final Accident accident) {
        dao.saveObject(accident);

    }

    public final void removeAccident(final Accident accident) {
        dao.removeObject(accident.getAccidentId());

    }

    /**
     * @see no.ugland.utransprod.service.OverviewManager#findAll()
     */
    public final List<Accident> findAll() {
        return dao.getObjects();
    }

    public final List<Accident> findByObject(final Accident accident) {
        return dao.findByExampleLike(accident);
    }

    public final void removeObject(final Accident accident) {
        removeAccident(accident);

    }

    public final void saveObject(final Accident accident) {
        saveAccident(accident);

    }

    public final void refreshObject(final Accident accident) {
        dao.refreshObject(accident, accident.getAccidentId());

    }

    /*public void lazyLoad(Accident accident, LazyLoadAccidentEnum[] enums) {
        ((AccidentDAO)dao).lazyLoad(accident, enums);

    }*/

    /*public void lazyLoad(Accident object, Enum[] enums) {
        lazyLoad(object,(LazyLoadAccidentEnum[]) enums);
        
    }*/

    @Override
    protected Serializable getObjectId(Accident object) {
        return object.getAccidentId();
    }

    

}
