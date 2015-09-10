package no.ugland.utransprod.service;

import java.util.List;

import no.ugland.utransprod.model.ApplicationUser;
import no.ugland.utransprod.model.ProductAreaGroup;

/**
 * Interface for manager for brukere.
 * 
 * @author atle.brekka
 */
public interface ApplicationUserManager extends OverviewManager<ApplicationUser> {
    public static final String MANAGER_NAME = "applicationUserManager";

    /**
     * Login bruker.
     * 
     * @param userName
     * @param password
     * @return bruker dersom bruker kan logges inn
     */
    ApplicationUser login(String userName, String password);

    /**
     * @see no.ugland.utransprod.service.OverviewManager#findAll()
     */
    List<ApplicationUser> findAll();

    /**
     * Finner alle brukere som ikke er gruppebrukere.
     * 
     * @return brukere
     */
    List<ApplicationUser> findAllNotGroup();

    /**
     * Finner alle pakkere.
     * 
     * @return pakkere
     */
    List<String> findAllPackers(ProductAreaGroup productAreaGroup);

    /**
     * Sjekker om bruker er funksjonsleder.
     * 
     * @param user
     * @return true dersom bruker er funksjonsleder
     */
    Boolean isUserFunctionManager(ApplicationUser user);

    /**
     * Lazy lasting av bruker.
     * 
     * @param applicationUser
     * @param enums
     */
    // void lazyLoad(ApplicationUser
    // applicationUser,LazyLoadApplicationUserEnum[] enums);

    /**
     * Finner alle navn på brukere som ikke er gruppebruker.
     * 
     * @return navn
     */
    List<String> findAllNamesNotGroup();

    /**
     * Lagrer bruker.
     * 
     * @param user
     */
    void saveApplicationUser(ApplicationUser user);
}
