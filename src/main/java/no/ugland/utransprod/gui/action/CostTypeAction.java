package no.ugland.utransprod.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.MenuBarBuilderInterface;
import no.ugland.utransprod.gui.OverviewView;
import no.ugland.utransprod.gui.handlers.CostTypeViewHandler;
import no.ugland.utransprod.gui.model.CostTypeModel;
import no.ugland.utransprod.model.CostType;
import no.ugland.utransprod.service.CostTypeManager;

import com.google.inject.Inject;

/**
 * Klasse som håndterer opprettelse av vindu for kosnadstype
 * 
 * @author atle.brekka
 */
public class CostTypeAction extends AbstractAction {
	/**
	 * 
	 */
	private final MenuBarBuilderInterface menuBarBuilder;
	private CostTypeManager costTypeManager;
	private Login login;
	private static final long serialVersionUID = 1L;

	@Inject
	public CostTypeAction(MenuBarBuilderInterface aMenuBarBuilder,CostTypeManager aCostTypeManager,Login aLogin) {
		super("Kostnadstyper...");
		login=aLogin;
		costTypeManager=aCostTypeManager;
		this.menuBarBuilder = aMenuBarBuilder;
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(final ActionEvent arg0) {
		menuBarBuilder.openFrame(new OverviewView<CostType, CostTypeModel>(
				new CostTypeViewHandler(login,costTypeManager)));

	}
}