package no.ugland.utransprod.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import no.ugland.utransprod.gui.MenuBarBuilderInterface;
import no.ugland.utransprod.gui.PaidView;
import no.ugland.utransprod.gui.PaidViewFactory;
import no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler;
import no.ugland.utransprod.gui.handlers.PaidViewHandler;
import no.ugland.utransprod.gui.model.PaidApplyList;
import no.ugland.utransprod.model.PaidV;
import no.ugland.utransprod.service.PaidVManager;

import com.google.inject.Inject;

/**
 * Håndterer menyvalg Forhåndsbetaling...
 * 
 * @author atle.brekka
 */
public class PaidAction extends AbstractAction {
	/**
	 * 
	 */
	private final MenuBarBuilderInterface menuBarBuilder;
	private static final long serialVersionUID = 1L;

	private PaidView paidView;

	/**
	 * @param aApplicationUser
	 * @param menuBarBuilderImpl
	 *            TODO
	 */
	@Inject
	public PaidAction(MenuBarBuilderInterface aMenuBarBuilder,
			PaidViewFactory paidViewFactory) {
		super("Forhåndsbetaling...");
		paidView = paidViewFactory.create(false);
		this.menuBarBuilder = aMenuBarBuilder;
	}

	/**
	 * Åpner kundevindu
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(final ActionEvent arg0) {
		menuBarBuilder.openFrame(paidView);

	}
}