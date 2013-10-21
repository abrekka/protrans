package no.ugland.utransprod.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import no.ugland.utransprod.gui.MenuBarBuilderInterface;
import no.ugland.utransprod.gui.OverviewView;
import no.ugland.utransprod.gui.handlers.UserTypeViewHandler;
import no.ugland.utransprod.gui.model.UserTypeModel;
import no.ugland.utransprod.model.UserType;
import no.ugland.utransprod.service.UserTypeManager;

import com.google.inject.Inject;

/**
 * Håndterer manyvalg Brukertyper...
 * 
 * @author atle.brekka
 */
public class UserTypeAction extends AbstractAction {
	/**
	 * 
	 */
	private final MenuBarBuilderInterface menuBarBuilder;
	private UserTypeManager userTypeManager;
	private static final long serialVersionUID = 1L;

	@Inject
	public UserTypeAction(MenuBarBuilderInterface aMenuBarBuilder,UserTypeManager aUserTypeManager) {
		super("Brukertyper...");
		userTypeManager=aUserTypeManager;
		this.menuBarBuilder = aMenuBarBuilder;
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(final ActionEvent arg0) {

		menuBarBuilder.openFrame(new OverviewView<UserType, UserTypeModel>(
				new UserTypeViewHandler(menuBarBuilder.getUserType(),userTypeManager)));

	}
}