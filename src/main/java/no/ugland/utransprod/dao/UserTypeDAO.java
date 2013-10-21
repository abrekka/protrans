package no.ugland.utransprod.dao;

import no.ugland.utransprod.model.UserType;

/**
 * Interface for DAO mot tabell USER_TYPE
 * 
 * @author atle.brekka
 * 
 */
public interface UserTypeDAO extends DAO<UserType> {
	/**
	 * Lazy laster
	 * @param userType
	 * @param enums
	 */
	//void lazyLoad(UserType userType, LazyLoadUserTypeEnum[] enums);
	/**
	 * Finner antall brukere som bruker gitt type
	 * @param userType
	 * @return antall
	 */
	int getNumberOfUsers(UserType userType);
	/**
	 * Oppdaterer
	 * @param userType
	 */
	void refresh(UserType userType);
}
