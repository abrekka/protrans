/**
 * 
 */
package no.ugland.utransprod.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.MenuBarBuilderInterface;
import no.ugland.utransprod.gui.OverviewView;
import no.ugland.utransprod.gui.handlers.AccidentViewHandler;
import no.ugland.utransprod.gui.model.AccidentModel;
import no.ugland.utransprod.model.Accident;
import no.ugland.utransprod.service.AccidentManager;
import no.ugland.utransprod.service.ManagerRepository;

import com.google.inject.Inject;

public class AdminAccidentAction extends AbstractAction {
	/**
	 * 
	 */
	private final MenuBarBuilderInterface menuBarBuilder;
	private ManagerRepository managerRepository;
	private static final long serialVersionUID = 1L;
	private Login login;

	@Inject
	public AdminAccidentAction(MenuBarBuilderInterface aMenuBarBuilder,ManagerRepository aManagerRepository,Login aLogin) {
		super("Administrer hendelser/ulykker...");
		login=aLogin;
		managerRepository=aManagerRepository;
		this.menuBarBuilder = aMenuBarBuilder;
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(final ActionEvent arg0) {

		menuBarBuilder.openFrame(new OverviewView<Accident, AccidentModel>(
				new AccidentViewHandler(login,managerRepository)));

	}
}