package no.ugland.utransprod.dao;

import java.util.List;

import no.ugland.utransprod.model.ApplicationUser;
import no.ugland.utransprod.model.ProductAreaGroup;

/**
 * Interface for DAO klasse mot tabell APPLICATION_USER
 * @author atle.brekka
 */
public interface ApplicationUserDAO extends DAO<ApplicationUser> {
    /**
     * Finner alle brukere som ikke er grupperbruker
     * @return brukere
     */
    List<ApplicationUser> findAllNotGroup();

    /**
     * Oppdaterer objekt
     * @param applicationUser
     */
    void refreshObject(ApplicationUser applicationUser);

    /**
     * Finner bruker basert på brukernavn og passord
     * @param userName
     * @param password
     * @return brukere
     */
    List<ApplicationUser> findByUserNameAndPassword(String userName, String password);

    /**
     * Finner initialer til alle pakkere
     * @return initialer
     */
    List<String> findAllPackers(ProductAreaGroup productAreaGroup);

	ApplicationUser findByFullName(String fullName);

    /**
     * Lazy laster bruker
     * @param applicationUser
     * @param enums
     */
    //void lazyLoad(ApplicationUser applicationUser, LazyLoadApplicationUserEnum[] enums);
}
