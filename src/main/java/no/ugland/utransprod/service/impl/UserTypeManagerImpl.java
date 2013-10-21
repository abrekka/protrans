package no.ugland.utransprod.service.impl;

import java.io.Serializable;
import java.util.List;

import no.ugland.utransprod.dao.UserTypeDAO;
import no.ugland.utransprod.model.UserType;
import no.ugland.utransprod.service.UserTypeManager;

/**
 * Implementasjon av serviceklasse for tabell USER_TYPE.
 * @author atle.brekka
 */
public class UserTypeManagerImpl extends ManagerImpl<UserType>implements UserTypeManager {
    /**
     * @see no.ugland.utransprod.service.OverviewManager#findAll()
     */
    public final List<UserType> findAll() {
        return dao.getObjects("description");
    }

    /**
     * @see no.ugland.utransprod.service.UserTypeManager#
     * getNumberOfUsers(no.ugland.utransprod.model.UserType)
     */
    public final int getNumberOfUsers(final UserType userType) {
        return ((UserTypeDAO)dao).getNumberOfUsers(userType);
    }

    /**
     * Finner basert på eksempel.
     * @param userType
     * @return brukertyper
     */
    public final List<UserType> findByUserType(final UserType userType) {
        return dao.findByExample(userType);
    }

    /**
     * @param object
     * @return brukertyper
     * @see no.ugland.utransprod.service.OverviewManager#findByObject(java.lang.Object)
     */
    public final List<UserType> findByObject(final UserType object) {
        return findByUserType(object);
    }

    /**
     * @param userType
     * @see no.ugland.utransprod.service.OverviewManager#refreshObject(java.lang.Object)
     */
    public final void refreshObject(final UserType userType) {
        ((UserTypeDAO)dao).refresh(userType);

    }

    /**
     * Fjerner brukertype.
     * @param userType
     */
    public final void removeUserType(final UserType userType) {
        dao.removeObject(userType.getUserTypeId());

    }

    /**
     * @param object
     * @see no.ugland.utransprod.service.OverviewManager#removeObject(java.lang.Object)
     */
    public final void removeObject(final UserType object) {
        removeUserType(object);

    }

    /**
     * @param object
     * @see no.ugland.utransprod.service.OverviewManager#saveObject(java.lang.Object)
     */
    public final void saveObject(final UserType object) {
        dao.saveObject(object);

    }

    /*public void lazyLoad(UserType object, Enum[] enums) {
        lazyLoad(object,(LazyLoadUserTypeEnum[]) enums);
        
    }*/

    @Override
    protected Serializable getObjectId(UserType object) {
        return object.getUserTypeId();
    }

}
