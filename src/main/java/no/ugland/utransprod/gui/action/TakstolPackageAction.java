package no.ugland.utransprod.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import no.ugland.utransprod.gui.ApplyListView;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.MenuBarBuilderInterface;
import no.ugland.utransprod.gui.TakstolPackageWindow;
import no.ugland.utransprod.model.PackableListItem;

import com.google.inject.Inject;

/**
 * Håndterer menyvalg Standard takstol...
 * 
 * @author atle.brekka
 */
public class TakstolPackageAction extends AbstractAction {
	/**
     *
     */
	private final MenuBarBuilderInterface menuBarBuilder;
	private static final long serialVersionUID = 1L;
	private TakstolPackageWindow takstolPackageWindow;
	private Login login;

	@Inject
	public TakstolPackageAction(
			final TakstolPackageWindow aTakstolPackageWindow,
			final MenuBarBuilderInterface aMenuBarBuilder, final Login aLogin) {
		super("Standard takstol...");
		login = aLogin;
		this.menuBarBuilder = aMenuBarBuilder;
		takstolPackageWindow = aTakstolPackageWindow;
	}

	/**
	 * Åpner kundevindu
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(final ActionEvent arg0) {
		takstolPackageWindow.setLogin(login);
		this.menuBarBuilder.openFrame(new ApplyListView<PackableListItem>(
				takstolPackageWindow.getViewHandler(), true));

	}
}