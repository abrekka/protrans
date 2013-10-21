package no.ugland.utransprod.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.MenuBarBuilderInterface;
import no.ugland.utransprod.gui.OverviewView;
import no.ugland.utransprod.gui.handlers.ApplicationUserViewHandler;
import no.ugland.utransprod.gui.model.ApplicationUserModel;
import no.ugland.utransprod.model.ApplicationUser;
import no.ugland.utransprod.service.ApplicationUserManager;

import com.google.inject.Inject;

/**
 * Håndterer menyvalg Brukere...
 * 
 * @author atle.brekka
 */
public class ApplicationUserAction extends AbstractAction {
	/**
	 * 
	 */
	private final MenuBarBuilderInterface menuBarBuilder;
	private ApplicationUserManager applicationUserManager;
	private static final long serialVersionUID = 1L;
	private Login login;

	@Inject
	public ApplicationUserAction(MenuBarBuilderInterface aMenuBarBuilder,ApplicationUserManager aApplicationUserManager,Login aLogin) {
		super("Brukere...");
		login=aLogin;
		applicationUserManager=aApplicationUserManager;
		this.menuBarBuilder = aMenuBarBuilder;
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(final ActionEvent arg0) {

		menuBarBuilder.openFrame(new OverviewView<ApplicationUser, ApplicationUserModel>(
				new ApplicationUserViewHandler(login,applicationUserManager)));

	}
}