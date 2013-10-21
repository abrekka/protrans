package no.ugland.utransprod.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import no.ugland.utransprod.gui.MenuBarBuilderInterface;
import no.ugland.utransprod.gui.OverviewView;
import no.ugland.utransprod.gui.handlers.FunctionCategoryViewHandler;
import no.ugland.utransprod.gui.model.FunctionCategoryModel;
import no.ugland.utransprod.model.FunctionCategory;
import no.ugland.utransprod.service.FunctionCategoryManager;

import com.google.inject.Inject;

/**
 * Håndterer menyvalg Kategori...
 * 
 * @author atle.brekka
 */
public class FunctionCategoryAction extends AbstractAction {
	/**
	 * 
	 */
	private final MenuBarBuilderInterface menuBarBuilder;
	private FunctionCategoryManager functionCategoryManager;
	private static final long serialVersionUID = 1L;

	@Inject
	public FunctionCategoryAction(MenuBarBuilderInterface aMenuBarBuilder,FunctionCategoryManager aFunctionCategoryManager) {
		super("Kategori...");
		functionCategoryManager=aFunctionCategoryManager;
		this.menuBarBuilder = aMenuBarBuilder;
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(final ActionEvent arg0) {

		menuBarBuilder.openFrame(new OverviewView<FunctionCategory, FunctionCategoryModel>(
				new FunctionCategoryViewHandler(menuBarBuilder.getUserType(),functionCategoryManager)));

	}
}