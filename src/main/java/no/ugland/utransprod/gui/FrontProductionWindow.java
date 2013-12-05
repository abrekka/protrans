package no.ugland.utransprod.gui;

import no.ugland.utransprod.gui.action.SetProductionUnitActionFactory;
import no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler;
import no.ugland.utransprod.gui.handlers.DeviationViewHandlerFactory;
import no.ugland.utransprod.gui.handlers.FrontProductionViewHandler;
import no.ugland.utransprod.gui.model.ProductionApplyList;
import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.Produceable;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.util.ApplicationParamUtil;

import com.google.inject.Inject;

/**
 * Klasse som h�ndeterer visning av frittst�ende vindu for frontproduksjon
 * 
 * @author atle.brekka
 * 
 */
public class FrontProductionWindow extends
		AbstractProductionPackageWindow<Produceable> {

	private ManagerRepository managerRepository;
	private DeviationViewHandlerFactory deviationViewHandlerFactory;
	private SetProductionUnitActionFactory setProductionUnitActionFactory;

	@Inject
	public FrontProductionWindow(Login login,
			ManagerRepository aManagerRepository,
			DeviationViewHandlerFactory aDeviationViewHandlerFactory,
			SetProductionUnitActionFactory aSetProductionUnitActionFactory) {
		super(login);
		setProductionUnitActionFactory = aSetProductionUnitActionFactory;
		managerRepository = aManagerRepository;
		deviationViewHandlerFactory = aDeviationViewHandlerFactory;
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
				.findByName(
						ApplicationParamUtil
								.findParamByName(getParamArticleName()));
		return new FrontProductionViewHandler(new ProductionApplyList(login,
				managerRepository.getFrontProductionVManager(), null, "Front",
				null, managerRepository), "Frontproduksjon", login,
				articleType, managerRepository, deviationViewHandlerFactory,
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