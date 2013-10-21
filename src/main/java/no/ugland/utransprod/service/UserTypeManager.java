package no.ugland.utransprod.service;

import no.ugland.utransprod.model.UserType;

/**
 * Interface for serviceklasse mot tabell USER_TYPE
 * @author atle.brekka
 */
public interface UserTypeManager extends OverviewManager<UserType> {

    /**
     * Lazy laster brukertype
     * @param userType
     * @param enums
     */
    //void lazyLoad(UserType userType, LazyLoadUserTypeEnum[] enums);

    String MANAGER_NAME = "userTypeManager";

	/**
     * Henter antall brukere for gitt type
     * @param userType
     * @return antall
     */
    int getNumberOfUsers(UserType userType);
}
