package no.ugland.utransprod.gui;

import java.awt.Color;

import javax.swing.UIManager;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.model.ApplicationUser;
import no.ugland.utransprod.model.UserType;
import no.ugland.utransprod.service.ApplicationUserManager;
import no.ugland.utransprod.util.ModelUtil;

import org.jdesktop.swingx.auth.LoginService;

import com.google.inject.Singleton;

/**
 * Klasse som håndterer innlogging
 * @author atle.brekka
 */
@Singleton
public class LoginImpl extends LoginService implements Login {

    /**
	 * 
	 */
    private ApplicationUser applicationUser;
    private UserType userType;

    public LoginImpl() {

    }
    
    public LoginImpl(ApplicationUser aApplicationUser,UserType aUserType) {
    	applicationUser=aApplicationUser;
    	userType=aUserType;
    }

    /**
	 * 
	 */
    public void login() {
        String userName = System.getProperty("user.name");

        applicationUser = checkLogin(userName, null);
        if (applicationUser == null) {
            showLoginDialog();
        }
    }

    /*
     * (non-Javadoc)
     * @see no.ugland.utransprod.gui.Login#getApplicationUser()
     */
    public ApplicationUser getApplicationUser() {
        return applicationUser;
    }
    public void setApplicationUser(final ApplicationUser aUser){
        applicationUser=aUser;
    }

    /**
     * Viser login dialog dersom default bruker ikke ble autorisert
     */
    private void showLoginDialog() {
        UIManager.put("JXLoginPane.banner.foreground", Color.WHITE);
        UIManager.put("JXLoginPane.banner.darkBackground", Color.BLACK);
        UIManager.put("JXLoginPane.banner.lightBackground", Color.GREEN);
        /*
         * UIManager.put("no.ica.generalnota.gui.swingx.JXLoginPanel.nameString",
         * "Brukernavn");
         */
        UIManager.put("JXLoginPane.banner.image", IconEnum.ICON_UGLAND2.getIcon().getImage());
        UIManager.put("JXLoginPane.banner.image.x", 350);
        UIManager.put("JXLoginPane.banner.image.y", 5);
        UIManager.put("JXLoginPane.nameString", "Brukernavn");
        UIManager.put("JXLoginPane.passwordString", "Passord");
        UIManager.put("JXLoginPane.titleString", "Innlogging :: Protrans");
        UIManager.put("JXLoginPane.cancelString", "Avbryt");
        UIManager.put("JXLoginPane.cancelLogin", "Stopp innlogging");
        UIManager.put("JXLoginPane.bannerString", "Innlogging");
        UIManager.put("JXLoginPane.pleaseWait", "Vennligst vent, logger inn...");
        UIManager
                .put(
                        "JXLoginPane.errorMessage",
                        "<html><b>Kunne ikke logge inn</b><br><br>Sjekk brukernavn og passord. Sjekk om Caps Lock er skrudd på.</html>");

        LoginPanel.Status status = LoginPanel.showLoginDialog(null, this);

        if (status == LoginPanel.Status.SUCCEEDED) {
            System.out.println("Login Succeeded!");
        } else {
            System.out.println("Login Failed: " + status);
        }

    }

    /**
     * Prøver å autorisere bruker
     * @param userName
     * @param password
     * @return true dersom bruker ble autorisert
     */
    private ApplicationUser checkLogin(String userName, String password) {
        ApplicationUserManager applicationUserManager = (ApplicationUserManager) ModelUtil
                .getBean("applicationUserManager");
        return applicationUserManager.login(userName, password);
    }

   

    /**
     * @see org.jdesktop.swingx.auth.LoginService#authenticate(java.lang.String,
     *      char[], java.lang.String)
     */
    @Override
    public boolean authenticate(String userName, char[] password, String server) throws ProTransException {
        applicationUser = checkLogin(userName, new String(password));
        if (applicationUser != null) {
            return true;
        }
        return false;

    }

    public void setUserType(final UserType aUserType) {
        userType = aUserType;
    }

    public UserType getUserType() {
        return userType;
    }

}
