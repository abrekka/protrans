package no.ugland.utransprod.gui.action;

import java.awt.event.ActionEvent;
import java.util.Hashtable;
import java.util.Map;

import javax.swing.AbstractAction;

import no.ugland.utransprod.gui.MainPackageView;
import no.ugland.utransprod.gui.MenuBarBuilderInterface;
import no.ugland.utransprod.gui.checker.LagerProductionStatusChecker;
import no.ugland.utransprod.gui.checker.StatusCheckerInterface;
import no.ugland.utransprod.gui.handlers.MainPackageViewHandler;
import no.ugland.utransprod.gui.handlers.MainPackageViewHandlerFactory;
import no.ugland.utransprod.gui.handlers.OrderViewHandler;
import no.ugland.utransprod.gui.handlers.OrderViewHandlerFactory;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.service.ArticleTypeManager;
import no.ugland.utransprod.service.AttributeChoiceManager;
import no.ugland.utransprod.service.AttributeManager;
import no.ugland.utransprod.service.ColliManager;
import no.ugland.utransprod.service.DeviationManager;
import no.ugland.utransprod.service.ExternalOrderManager;
import no.ugland.utransprod.service.OrderLineManager;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.service.VismaFileCreator;
import no.ugland.utransprod.util.ApplicationParamUtil;
import no.ugland.utransprod.util.Util;

import com.google.inject.Inject;

/**
 * Håndterer menyvalg Garasjepakke...
 * 
 * @author atle.brekka
 */
public class MainPackageAction extends AbstractAction {
	/**
	 * 
	 */
	private final MenuBarBuilderInterface menuBarBuilder;
	private static final long serialVersionUID = 1L;

	private MainPackageViewHandlerFactory mainPackageViewHandlerFactory;

	@Inject
	public MainPackageAction(
			final MenuBarBuilderInterface aMenuBarBuilder,
			MainPackageViewHandlerFactory aMainPackageViewHandlerFactory) {
		super("Pakking...");
		mainPackageViewHandlerFactory = aMainPackageViewHandlerFactory;
		this.menuBarBuilder = aMenuBarBuilder;
	}

	/**
	 * Åpner grarasjepakking
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(final ActionEvent arg0) {
	String veggArticleName = ApplicationParamUtil
				.findParamByName("vegg_artikkel");

		String frontArticleName = ApplicationParamUtil
				.findParamByName("front_artikkel");

		String attributeName = ApplicationParamUtil
				.findParamByName("front_attributt_navn");
		String attributeValue = ApplicationParamUtil
				.findParamByName("front_attributt_verdi");

		Map<String, StatusCheckerInterface<OrderLine>> statusChekers = new Hashtable<String, StatusCheckerInterface<OrderLine>>();

		statusChekers.put(veggArticleName, new LagerProductionStatusChecker(
				veggArticleName, attributeName, attributeValue));
		statusChekers.put(frontArticleName, new LagerProductionStatusChecker(
				frontArticleName, attributeName, attributeValue));

		Map<String, String> colliSetup = ApplicationParamUtil.getColliSetup();

		MainPackageViewHandler mainPackageViewHandler = mainPackageViewHandlerFactory
				.create(colliSetup, statusChekers);

		menuBarBuilder.openFrame(new MainPackageView(mainPackageViewHandler));

	}
}