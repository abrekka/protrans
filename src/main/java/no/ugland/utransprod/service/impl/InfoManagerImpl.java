package no.ugland.utransprod.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import no.ugland.utransprod.dao.InfoDAO;
import no.ugland.utransprod.model.Info;
import no.ugland.utransprod.service.InfoManager;

/**
 * Implementasjons av serviceklasse for tabell INFO.
 * @author atle.brekka
 */
public class InfoManagerImpl extends ManagerImpl<Info>implements InfoManager {
    /**
     * @see no.ugland.utransprod.service.OverviewManager#findAll()
     */
    public final List<Info> findAll() {
        return ((InfoDAO)dao).findAll();
    }

    /**
     * Finner basert på info.
     * @param info
     * @return liste med info
     */
    public final List<Info> findByInfo(final Info info) {
        return dao.findByExampleLike(info);
    }

    /**
     * @param object
     * @return info
     * @see no.ugland.utransprod.service.OverviewManager#findByObject(java.lang.Object)
     */
    public final List<Info> findByObject(final Info object) {
        return findByInfo(object);
    }

    /**
     * @param object
     * @see no.ugland.utransprod.service.OverviewManager#refreshObject(java.lang.Object)
     */
    public final void refreshObject(final Info object) {
        ((InfoDAO)dao).refreshObject(object);

    }

    /**
     * Fjerner info.
     * @param info
     */
    public final void removeInfo(final Info info) {
        dao.removeObject(info.getInfoId());

    }

    /**
     * @param object
     * @see no.ugland.utransprod.service.OverviewManager#removeObject(java.lang.Object)
     */
    public final void removeObject(final Info object) {
        removeInfo(object);

    }

    /**
     * Lagrer info.
     * @param info
     */
    public final void saveInfo(final Info info) {
        dao.saveObject(info);

    }

    /**
     * @param object
     * @see no.ugland.utransprod.service.OverviewManager#saveObject(java.lang.Object)
     */
    public final void saveObject(final Info object) {
        saveInfo(object);

    }

    /**
     * @see no.ugland.utransprod.service.InfoManager#findByDate(java.util.Date)
     */
    public final Info findByDate(final Date date) {
        List<Info> list = ((InfoDAO)dao).findByDate(date);
        StringBuffer buffer = new StringBuffer();
        List<String> texts = new ArrayList<String>();
        if (list != null) {
            for (Info info : list) {
                buffer.append(info.getInfoText()).append("\n");
                texts.add(info.getInfoText());
            }
        }
        return new Info(null, buffer.toString(), null, null);
    }

    /**
     * @see no.ugland.utransprod.service.InfoManager#findListByDate(java.util.Date)
     */
    public final List<String> findListByDate(final Date date) {
        List<Info> list = ((InfoDAO)dao).findByDate(date);
        List<String> texts = new ArrayList<String>();
        if (list != null) {
            for (Info info : list) {
                texts.add(info.getInfoText());
            }
        }
        return texts;
    }

 
    @Override
    protected Serializable getObjectId(Info object) {
        return object.getInfoId();
    }

}
