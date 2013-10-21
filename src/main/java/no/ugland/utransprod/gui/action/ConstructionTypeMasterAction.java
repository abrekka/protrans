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
 * Klasse som håndterer opprettelse av vindu for master garasjetype
 * 
 * @author atle.brekka
 */
public class ConstructionTypeMasterAction extends AbstractAction {
	/**
	 * 
	 */
	private final MenuBarBuilderInterface menuBarBuilder;
	private static final long serialVersionUID = 1L;

	private ConstructionTypeViewHandler constructionTypeViewHandler;

	@Inject
	public ConstructionTypeMasterAction(
			MenuBarBuilderInterface aMenuBarBuilder,
			ConstructionTypeViewHandlerFactory constructionTypeViewHandlerFactory) {
		super("Master konstruksjonstyper...");
		constructionTypeViewHandler = constructionTypeViewHandlerFactory
				.create(true, true);
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