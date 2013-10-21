package no.ugland.utransprod.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import no.ugland.utransprod.gui.MenuBarBuilderInterface;
import no.ugland.utransprod.gui.OverviewView;
import no.ugland.utransprod.gui.handlers.ExternalOrderViewHandler;
import no.ugland.utransprod.gui.handlers.ExternalOrderViewHandlerFactory;
import no.ugland.utransprod.gui.model.ExternalOrderModel;
import no.ugland.utransprod.model.ExternalOrder;
import no.ugland.utransprod.service.ArticleTypeManager;
import no.ugland.utransprod.service.AttributeChoiceManager;
import no.ugland.utransprod.service.AttributeManager;
import no.ugland.utransprod.service.ExternalOrderLineManager;
import no.ugland.utransprod.service.ExternalOrderManager;

import com.google.inject.Inject;

/**
 * Håndterer menyvalg Bestillinger
 * 
 * @author atle.brekka
 */
public class ExternalOrderAction extends AbstractAction {

	/**
	 * 
	 */
	private final MenuBarBuilderInterface menuBarBuilder;
	private static final long serialVersionUID = 1L;
	private ExternalOrderViewHandler externalOrderViewHandler;

	@Inject
	public ExternalOrderAction(MenuBarBuilderInterface aMenuBarBuilder,
			ExternalOrderViewHandlerFactory externalOrderViewHandlerFactory) {
		super("Bestillinger...");
		externalOrderViewHandler = externalOrderViewHandlerFactory.create(null);
		this.menuBarBuilder = aMenuBarBuilder;

	}

	/**
	 * Åpner kundevindu
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(final ActionEvent arg0) {

		/*
		 * menuBarBuilder.openFrame(new OverviewView<ExternalOrder,
		 * ExternalOrderModel>( new ExternalOrderViewHandler(null,
		 * menuBarBuilder
		 * .getUserType(),externalOrderManager,externalOrderLineManager
		 * ,attributeManager,attributeChoiceManager,articleTypeManager), false,
		 * false));
		 */
		menuBarBuilder
				.openFrame(new OverviewView<ExternalOrder, ExternalOrderModel>(
						externalOrderViewHandler));

	}
}