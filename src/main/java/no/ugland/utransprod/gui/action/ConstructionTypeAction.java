package no.ugland.utransprod.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import no.ugland.utransprod.gui.MenuBarBuilderInterface;
import no.ugland.utransprod.gui.OverviewView;
import no.ugland.utransprod.gui.handlers.ConstructionTypeViewHandler;
import no.ugland.utransprod.gui.handlers.ConstructionTypeViewHandlerFactory;
import no.ugland.utransprod.gui.model.ConstructionTypeModel;
import no.ugland.utransprod.model.ConstructionType;
import no.ugland.utransprod.service.ArticleTypeManager;
import no.ugland.utransprod.service.AttributeChoiceManager;
import no.ugland.utransprod.service.AttributeManager;
import no.ugland.utransprod.service.ConstructionTypeManager;

import com.google.inject.Inject;

/**
 * Klasse som h�ndterer opprettelse av vindu for grasjetype
 * 
 * @author atle.brekka
 */
public class ConstructionTypeAction extends AbstractAction {
	/**
	 * 
	 */
	private final MenuBarBuilderInterface menuBarBuilder;
	private static final long serialVersionUID = 1L;
	private ConstructionTypeViewHandler constructionTypeViewHandler;

	@Inject
	public ConstructionTypeAction(
			MenuBarBuilderInterface aMenuBarBuilder,
			ConstructionTypeViewHandlerFactory aConstructionTypeViewHandlerFactory) {
		super("Konstruksjonstyper...");
		constructionTypeViewHandler = aConstructionTypeViewHandlerFactory
				.create(false, false);
		this.menuBarBuilder = aMenuBarBuilder;
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(final ActionEvent arg0) {
		menuBarBuilder
				.openFrame(new OverviewView<ConstructionType, ConstructionTypeModel>(
						constructionTypeViewHandler));

	}
}