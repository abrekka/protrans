package no.ugland.utransprod.gui.handlers;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.model.ApplicationUser;
import no.ugland.utransprod.model.Deviation;
import no.ugland.utransprod.model.UserType;
import no.ugland.utransprod.service.ArticleTypeManager;
import no.ugland.utransprod.service.AttributeChoiceManager;
import no.ugland.utransprod.service.AttributeManager;
import no.ugland.utransprod.service.ConstructionTypeManager;
import no.ugland.utransprod.service.DeviationManager;
import no.ugland.utransprod.service.ExternalOrderManager;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.service.PreventiveActionManager;

import com.google.inject.Inject;

/**
 * Registrering av avvik
 * 
 * @author atle.brekka
 */
class DeviationAction<T> extends AbstractAction {
	private static final long serialVersionUID = 1L;

	// private ApplicationUser applicationUser;
	// private UserType userType;
	private WindowInterface window;
	/*
	 * private DeviationManager deviationManager;
	 * 
	 * private ExternalOrderManager externalOrderManager;
	 * 
	 * private ArticleTypeManager articleTypeManager;
	 * 
	 * private OrderManager orderManager;
	 * 
	 * private PreventiveActionManager preventiveActionManager;
	 * 
	 * private AttributeManager attributeManager;
	 * 
	 * private AttributeChoiceManager attributeChoiceManager;
	 */

	private DeviationViewHandler2 deviationViewHandler;

	/**
	 * @param aWindow
	 */
	@Inject
	public DeviationAction(DeviationViewHandlerFactory deviationViewHandlerFactory) {
		super("Reg. avvik...");
		deviationViewHandler = deviationViewHandlerFactory.create(null, true, false, true, null, true, true);
		/*
		 * attributeChoiceManager=aAttributeChoiceManager; externalOrderManager
		 * =aExternalOrderManager;
		 * 
		 * articleTypeManager =aArticleTypeManager;
		 * 
		 * orderManager =aOrderManager;
		 * 
		 * preventiveActionManager =aPreventiveActionManager;
		 * 
		 * attributeManager =aAttributeManager;
		 * deviationManager=aDeviationManager; applicationUser=aApplicationUser;
		 * userType=aUserType;
		 */

	}

	public void setWindowInterface(WindowInterface aWindow) {
		window = aWindow;
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(final ActionEvent arg0) {
		// DeviationViewHandler deviationViewHandler = new
		// DeviationViewHandler(applicationUser, null,
		// userType, true, false, true, null,
		// true,deviationManager,preventiveActionManager,attributeManager,articleTypeManager,externalOrderManager,orderManager,attributeChoiceManager);
		deviationViewHandler.openEditView(new Deviation(), false, window, false);
	}
}