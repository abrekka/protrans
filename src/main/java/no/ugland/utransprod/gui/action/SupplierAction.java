package no.ugland.utransprod.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.MenuBarBuilderInterface;
import no.ugland.utransprod.gui.SupplierView;
import no.ugland.utransprod.service.ManagerRepository;

import com.google.inject.Inject;

/**
 * Håndterer menyvalg Leverandører
 * 
 * @author atle.brekka
 */
public class SupplierAction extends AbstractAction {
	/**
	 * 
	 */
	private final MenuBarBuilderInterface menuBarBuilder;
	private Login login;
	private ManagerRepository manageRepository;
	private static final long serialVersionUID = 1L;

	@Inject
	public SupplierAction(MenuBarBuilderInterface aMenuBarBuilder,
Login aLogin,
			ManagerRepository aManagerRepository) {
		super("Leverandører...");
		login = aLogin;
		manageRepository = aManagerRepository;
		this.menuBarBuilder = aMenuBarBuilder;
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(final ActionEvent arg0) {
		menuBarBuilder.openFrame(new SupplierView(login, manageRepository));

	}
}