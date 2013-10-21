package no.ugland.utransprod.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import no.ugland.utransprod.gui.MenuBarBuilderInterface;
import no.ugland.utransprod.gui.OverviewView;
import no.ugland.utransprod.gui.handlers.AttributeViewHandler;
import no.ugland.utransprod.gui.model.AttributeModel;
import no.ugland.utransprod.model.Attribute;
import no.ugland.utransprod.service.AttributeChoiceManager;
import no.ugland.utransprod.service.AttributeManager;

import com.google.inject.Inject;

/**
 * Håndterer menyvalg Attributter...
 * 
 * @author atle.brekka
 */
public class AttributeAction extends AbstractAction {
	/**
	 * 
	 */
	private final MenuBarBuilderInterface menuBarBuilder;
	private static final long serialVersionUID = 1L;
	private AttributeViewHandler attributeViewHandler;

	@Inject
	public AttributeAction(MenuBarBuilderInterface aMenuBarBuilder,
			AttributeViewHandler aAttributeViewHandler) {
		super("Attributter...");
		attributeViewHandler = aAttributeViewHandler;
		this.menuBarBuilder = aMenuBarBuilder;
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(final ActionEvent arg0) {
		/*
		 * menuBarBuilder.openFrame(new OverviewView<Attribute, AttributeModel>(
		 * new
		 * AttributeViewHandler(menuBarBuilder.getUserType(),attributeManager
		 * ,attributeChoiceManager)));
		 */
		menuBarBuilder.openFrame(new OverviewView<Attribute, AttributeModel>(
				attributeViewHandler));

	}
}