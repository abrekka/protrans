package no.ugland.utransprod.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import no.ugland.utransprod.gui.GavlProductionView;
import no.ugland.utransprod.gui.GavlProductionWindow;
import no.ugland.utransprod.gui.MenuBarBuilderInterface;

import com.google.inject.Inject;

/**
 * Klasse som håndterer visning av gavlproduskjonvindu
 * 
 * @author atle.brekka
 */
public class GavlProductionAction extends AbstractAction {
	private final MenuBarBuilderInterface menuBarBuilder;
	private static final long serialVersionUID = 1L;
	private GavlProductionWindow gavlProductionWindow;

	@Inject
	public GavlProductionAction(MenuBarBuilderInterface aMenuBarBuilder,GavlProductionWindow aGavlProductionWindow) {
		super("Gavl...");
		gavlProductionWindow=aGavlProductionWindow;
		this.menuBarBuilder = aMenuBarBuilder;
	}

	public void actionPerformed(final ActionEvent arg0) {
		menuBarBuilder.openFrame(new GavlProductionView(gavlProductionWindow
				.getViewHandler()));

	}
}