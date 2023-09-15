package no.ugland.utransprod.gui;

import no.ugland.utransprod.gui.action.SetProductionUnitActionFactory;
import no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler;
import no.ugland.utransprod.gui.handlers.DeviationViewHandlerFactory;
import no.ugland.utransprod.gui.handlers.FrontProductionViewHandler;
import no.ugland.utransprod.gui.model.FrontProductionApplyList;
import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.Produceable;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.VismaFileCreator;
import no.ugland.utransprod.util.ApplicationParamUtil;

import java.util.Arrays;

import com.google.inject.Inject;

/**
 * Klasse som håndeterer visning av frittstående vindu for frontproduksjon
 * 
 * @author atle.brekka
 * 
 */
public class FrontProductionWindow extends AbstractProductionPackageWindow<Produceable> {

	private ManagerRepository managerRepository;
	private DeviationViewHandlerFactory deviationViewHandlerFactory;
	private SetProductionUnitActionFactory setProductionUnitActionFactory;
	private VismaFileCreator vismaFileCreator;

	@Inject
	public FrontProductionWindow(Login login, ManagerRepository aManagerRepository,
			DeviationViewHandlerFactory aDeviationViewHandlerFactory,
			SetProductionUnitActionFactory aSetProductionUnitActionFactory, VismaFileCreator vismaFileCreator) {
		super(login);
		setProductionUnitActionFactory = aSetProductionUnitActionFactory;
		managerRepository = aManagerRepository;
		deviationViewHandlerFactory = aDeviationViewHandlerFactory;
		this.vismaFileCreator = vismaFileCreator;
	}

	/**
	 * @see no.ugland.utransprod.gui.AbstractProductionPackageWindow#getParamArticleName()
	 */
	@Override
	protected String getParamArticleName() {
		return "front_artikkel";
	}

	/**
	 * @see no.ugland.utransprod.gui.AbstractProductionPackageWindow#getWindowTitle()
	 */
	@Override
	protected String getWindowTitle() {
		return "Produksjon av front";
	}

	/**
	 * @see no.ugland.utransprod.gui.AbstractProductionPackageWindow#getViewHandler()
	 */
	@Override
	public AbstractProductionPackageViewHandler<Produceable> getViewHandler() {
		// FrontProductionVManager
		// frontProductionVManager=(FrontProductionVManager)ModelUtil.getBean("frontProductionVManager");
		// ArticleTypeManager
		// articleTypeManager=(ArticleTypeManager)ModelUtil.getBean("articleTypeManager");
		ArticleType articleType = managerRepository.getArticleTypeManager()
				.findByName(ApplicationParamUtil.findParamByName(getParamArticleName()));
		return new FrontProductionViewHandler(
				new FrontProductionApplyList(login, managerRepository.getFrontProductionVManager(), null, "Front", null,
						managerRepository, vismaFileCreator,
						Arrays.asList("Front", "Belistning", "Gesimsbord gavl pk", "Vannbord", "Vindski")),
				"Frontproduksjon", login, articleType, managerRepository, deviationViewHandlerFactory,
				setProductionUnitActionFactory);
	}

	/**
	 * @see no.ugland.utransprod.gui.AbstractProductionPackageWindow#usePrintButton()
	 */
	@Override
	protected boolean usePrintButton() {
		return false;
	}

}
