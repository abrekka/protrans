package no.ugland.utransprod.gui;

import java.awt.Component;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;

import no.ugland.utransprod.ProtransModule;
import no.ugland.utransprod.ProtransUncaughtHandler;
import no.ugland.utransprod.model.UserRole;
import no.ugland.utransprod.model.UserType;
import no.ugland.utransprod.service.ApplicationUserManager;
import no.ugland.utransprod.service.ManagerRepositoryImpl;
import no.ugland.utransprod.service.enums.LazyLoadEnum;
import no.ugland.utransprod.service.impl.BaseManagerImpl;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Util;

import com.birosoft.liquid.LiquidLookAndFeel;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 * Oppstartsklasse for ProTrans
 * 
 * @author atle.brekka
 */
public final class MainClass {

    static String version = null;

    static boolean isTest = false;

    static JDialog loadDialog = null;
    private LoginImpl login;

    static {
	try {

	    UIManager.setLookAndFeel(LFEnum.LNF_LIQUID.getClassName());
	    JFrame.setDefaultLookAndFeelDecorated(true);

	    JDialog.setDefaultLookAndFeelDecorated(true);

	    LiquidLookAndFeel.setLiquidDecorations(true, "mac");

	    LiquidLookAndFeel.setPanelTransparency(false);

	} catch (Exception e) {
	    e.printStackTrace();
	}

	ResourceBundle configuration = ResourceBundle.getBundle("application");

	version = configuration.getString("version");
	isTest = Boolean.valueOf(configuration.getString("test")).booleanValue();

	String testString = "";

	if (isTest) {
	    testString += " test";
	}

	LoadView loadView = new LoadView(version + testString, "ProTrans - Grimstad Industrier");
	loadDialog = loadView.buildDialog();

	Util.locateOnScreenCenter(loadDialog);
	loadDialog.setVisible(true);

	BaseManagerImpl.setTest(isTest);

	initDesktopDll();
    }

    @Inject
    private MainClass(final LoginImpl aLogin) {
	login = aLogin;
    }

    private static void initDesktopDll() {
	MainClass.class.getClassLoader();
	URL dllUrl = ClassLoader.getSystemResource("jdic.dll");
	if (dllUrl == null) {
	    Util.showErrorDialog((Component) null, "Feil", "Finner ikke jdic.dll");
	} else {
	    System.loadLibrary("jdic");
	    // System.load(dllUrl.getPath());
	}
	// if (dllUrl != null) {
	// System.load(dllUrl.getPath());
	// }
    }

    /**
     * Oppstart av ProTrans
     * 
     * @param args
     */
    public static void main(final String[] args) {
	Thread.setDefaultUncaughtExceptionHandler(new ProtransUncaughtHandler());
	ToolTipManager.sharedInstance().setDismissDelay(Integer.MAX_VALUE);
	Injector injector = Guice.createInjector(new ProtransModule());
	MainClass mainClass = injector.getInstance(MainClass.class);
	mainClass.startUp(injector);
	loadDialog.dispose();

    }

    /**
     * Starter opp riktig vindu iht bruker
     * 
     * @param applicationUser
     */
    private void startUp(final Injector injector) {
	login.login();
	if (login.getApplicationUser() == null) {
	    System.exit(0);
	}

	ApplicationUserManager applicationUserManager = (ApplicationUserManager) ModelUtil.getBean(ApplicationUserManager.MANAGER_NAME);
	applicationUserManager.lazyLoad(login.getApplicationUser(), new LazyLoadEnum[][] { { LazyLoadEnum.USER_ROLES, LazyLoadEnum.NONE } });
	Set<UserRole> roles = login.getApplicationUser().getUserRoles();
	UserType userType;
	UserRole userRole = null;

	if (roles != null) {
	    if (roles.size() > 1) {
		userRole = (UserRole) JOptionPane.showInputDialog(null, "Velg rolle", "Velg rolle", JOptionPane.QUESTION_MESSAGE, null,
			roles.toArray(), null);
	    } else if (roles.size() == 1) {
		userRole = roles.iterator().next();
	    }
	}
	if (userRole != null) {
	    userType = userRole.getUserType();
	    login.setUserType(userType);
	    try {
		Class<?> windowClass = Class.forName(userType.getStartupWindow());
		MainWindow mainWindow = (MainWindow) injector.getInstance(windowClass);
		mainWindow.setLogin(login);

		mainWindow.buildMainWindow(new SystemReadyListener() {

		    public void systemReady() {
			loadDialog.dispose();

		    }

		}, injector.getInstance(ManagerRepositoryImpl.class));

	    } catch (Exception e) {

		e.printStackTrace();
		System.exit(0);
	    }
	} else {
	    System.exit(0);
	}
    }
}
