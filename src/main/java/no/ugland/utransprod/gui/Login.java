package no.ugland.utransprod.gui;

import no.ugland.utransprod.model.ApplicationUser;
import no.ugland.utransprod.model.UserType;

import com.google.inject.Singleton;

public interface Login {

    /**
     * Sjekker om bruker er logget inn
     * 
     * @return true dersom bruker er autorisert
     */
    ApplicationUser getApplicationUser();

    UserType getUserType();
    void setUserType(UserType userType);
    void setApplicationUser(ApplicationUser applicationUser);

}