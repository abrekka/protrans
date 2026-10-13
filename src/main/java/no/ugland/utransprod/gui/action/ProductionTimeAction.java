package no.ugland.utransprod.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.google.inject.Inject;

import no.ugland.utransprod.gui.MenuBarBuilderInterface;
import no.ugland.utransprod.gui.ProductionTimeView;

public class ProductionTimeAction extends AbstractAction {
	/**
	 * 
	 */
	private final MenuBarBuilderInterface menuBarBuilder;
	private static final long serialVersionUID = 1L;


	@Inject
	public ProductionTimeAction(final MenuBarBuilderInterface aMenuBarBuilderInterface) {
		super("Produksjonstid...");
		this.menuBarBuilder = aMenuBarBuilderInterface;

	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(final ActionEvent arg0) {
		menuBarBuilder.openFrame(new ProductionTimeView());

	}
}