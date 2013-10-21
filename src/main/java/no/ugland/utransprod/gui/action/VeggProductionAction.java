package no.ugland.utransprod.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import no.ugland.utransprod.gui.ApplyListView;
import no.ugland.utransprod.gui.MenuBarBuilderInterface;
import no.ugland.utransprod.gui.VeggProductionWindow;
import no.ugland.utransprod.model.Produceable;

import com.google.inject.Inject;

/**
 * Håndterer menyvalg Vegg...
 * 
 * @author atle.brekka
 */
public class VeggProductionAction extends AbstractAction {
	/**
	 * 
	 */
	private final MenuBarBuilderInterface menuBarBuilder;
	private static final long serialVersionUID = 1L;
	private VeggProductionWindow veggProductionWindow;

	@Inject
	public VeggProductionAction(MenuBarBuilderInterface aMenuBarBuilder,VeggProductionWindow aVeggProductionWindow) {
		super("Vegg...");
		veggProductionWindow=aVeggProductionWindow;
		this.menuBarBuilder = aMenuBarBuilder;
	}

	/**
	 * Åpner kundevindu
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(final ActionEvent arg0) {
		//veggProductionWindow.setApplicationUser(applicationUser);
		menuBarBuilder.openFrame(new ApplyListView<Produceable>(veggProductionWindow
				.getViewHandler(), false));

	}
}