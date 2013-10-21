package no.ugland.utransprod.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import no.ugland.utransprod.gui.MenuBarBuilderInterface;
import no.ugland.utransprod.gui.OverviewView;
import no.ugland.utransprod.gui.handlers.SupplierTypeViewHandler;
import no.ugland.utransprod.gui.model.SupplierTypeModel;
import no.ugland.utransprod.model.SupplierType;
import no.ugland.utransprod.service.SupplierTypeManager;

import com.google.inject.Inject;

/**
 * Håndterer menyvalg Leverandørtype
 * 
 * @author atle.brekka
 */
public class SupplierTypeAction extends AbstractAction {
	/**
	 * 
	 */
	private final MenuBarBuilderInterface menuBarBuilder;
	private SupplierTypeManager supplierTypeManager;
	private static final long serialVersionUID = 1L;

	@Inject
	public SupplierTypeAction(MenuBarBuilderInterface aMenuBarBuilder,SupplierTypeManager aSupplierTypeManager) {
		super("Leverandørtyper...");
		supplierTypeManager=aSupplierTypeManager;
		this.menuBarBuilder = aMenuBarBuilder;
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(final ActionEvent arg0) {
		menuBarBuilder.openFrame(new OverviewView<SupplierType, SupplierTypeModel>(
				new SupplierTypeViewHandler(menuBarBuilder.getUserType(),supplierTypeManager)));

	}
}