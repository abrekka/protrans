package no.ugland.utransprod.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import no.ugland.utransprod.gui.ApplyListView;
import no.ugland.utransprod.gui.FaktureringView;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.MenuBarBuilderInterface;
import no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler;
import no.ugland.utransprod.gui.handlers.InvoiceViewHandler;
import no.ugland.utransprod.gui.model.InvoiceApplyList;
import no.ugland.utransprod.model.FaktureringV;
import no.ugland.utransprod.service.AccidentManager;
import no.ugland.utransprod.service.ArticleTypeManager;
import no.ugland.utransprod.service.AttributeChoiceManager;
import no.ugland.utransprod.service.AttributeManager;
import no.ugland.utransprod.service.DeviationManager;
import no.ugland.utransprod.service.ExternalOrderManager;
import no.ugland.utransprod.service.FaktureringVManager;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.service.PreventiveActionManager;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Util;

import com.google.inject.Inject;

/**
 * Håndterer menyvalg Fakturering...
 * 
 * @author atle.brekka
 */
public class InvoiceAction extends AbstractAction {
	/**
	 * 
	 */
	private final MenuBarBuilderInterface menuBarBuilder;
	private static final long serialVersionUID = 1L;
	private InvoiceViewHandler invoiceViewHandler;

	/**
	 * @param aApplicationUser
	 * @param menuBarBuilderImpl TODO
	 */
	@Inject
	public InvoiceAction(MenuBarBuilderInterface aMenuBarBuilder,InvoiceViewHandler aInvoiceViewHandler) {
		super("Fakturering...");
		invoiceViewHandler=aInvoiceViewHandler;
		this.menuBarBuilder = aMenuBarBuilder;
		
	}

	/**
	 * Åpner kundevindu
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(final ActionEvent arg0) {
		/*AbstractProductionPackageViewHandler<FaktureringV> productionViewHandler = new InvoiceViewHandler(

		new InvoiceApplyList(menuBarBuilder.getUserType(), faktureringVManager),
				menuBarBuilder.getUserType(), menuBarBuilder.getApplicationUser(),accidentManager,deviationManager,preventiveActionManager,articleTypeManager,attributeManager,externalOrderManager,orderManager,attributeChoiceManager);
*/
		menuBarBuilder.openFrame(new FaktureringView(invoiceViewHandler));

	}
}