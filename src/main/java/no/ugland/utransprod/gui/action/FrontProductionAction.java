package no.ugland.utransprod.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import no.ugland.utransprod.gui.ApplyListView;
import no.ugland.utransprod.gui.FrontProductionWindow;
import no.ugland.utransprod.gui.MenuBarBuilderInterface;
import no.ugland.utransprod.model.Produceable;

import com.google.inject.Inject;

/**
 * Håndterer menyvalg Front...
 * 
 * @author atle.brekka
 */
public class FrontProductionAction extends AbstractAction {
	/**
	 * 
	 */
	private final MenuBarBuilderInterface menuBarBuilder;
	private static final long serialVersionUID = 1L;
	private FrontProductionWindow frontProductionWindow;

	@Inject
	public FrontProductionAction(MenuBarBuilderInterface aMenuBarBuilder,FrontProductionWindow aFrontProductionWindow) {
		super("Front...");
		frontProductionWindow=aFrontProductionWindow;
		this.menuBarBuilder = aMenuBarBuilder;
	}

	/**
	 * Åpner kundevindu
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(final ActionEvent arg0) {
		menuBarBuilder.openFrame(new ApplyListView<Produceable>(frontProductionWindow
				.getViewHandler(), false));

	}
}