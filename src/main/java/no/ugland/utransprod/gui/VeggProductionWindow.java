package no.ugland.utransprod.gui;

import no.ugland.utransprod.gui.action.SetProductionUnitActionFactory;
import no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler;
import no.ugland.utransprod.gui.handlers.DeviationViewHandlerFactory;
import no.ugland.utransprod.gui.handlers.VeggProductionViewHandler;
import no.ugland.utransprod.gui.model.ProductionApplyList;
import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.Produceable;
import no.ugland.utransprod.service.ArticleTypeManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.VeggProductionVManager;
import no.ugland.utransprod.util.ApplicationParamUtil;
import no.ugland.utransprod.util.ModelUtil;

import com.google.inject.Inject;

/**
 * Håndterer visning av egen dialog for veggproduksjon
 * 
 * @author atle.brekka
 * 
 */
public class VeggProductionWindow extends
		AbstractProductionPackageWindow<Produceable> {

	private ManagerRepository managerRepository;
	private DeviationViewHandlerFactory deviationViewHandlerFactory;
	private SetProductionUnitActionFactory setProductionUnitActionFactory;
	
	@Inject
	public VeggProductionWindow(Login login,ManagerRepository aManagerRepository,DeviationViewHandlerFactory aDeviationViewHandlerFactory,SetProductionUnitActionFactory aSetProductionUnitActionFactory){
		super(login);
		setProductionUnitActionFactory=aSetProductionUnitActionFactory;
		managerRepository=aManagerRepository;
		deviationViewHandlerFactory=aDeviationViewHandlerFactory;
	}

	/**
	 * @see no.ugland.utransprod.gui.AbstractProductionPackageWindow#getParamArticleName()
	 */
	@Override
	protected String getParamArticleName() {
		return "vegg_artikkel";
	}

	/**
	 * @see no.ugland.utransprod.gui.AbstractProductionPackageWindow#getWindowTitle()
	 */
	@Override
	protected String getWindowTitle() {
		return "Produksjon av vegg";
	}

	/**
	 * @see no.ugland.utransprod.gui.AbstractProductionPackageWindow#getViewHandler()
	 */
	@Override
	public AbstractProductionPackageViewHandler<Produceable> getViewHandler() {
		VeggProductionVManager veggProductionVManager = (VeggProductionVManager) ModelUtil
				.getBean("veggProductionVManager");
        ArticleTypeManager articleTypeManager=(ArticleTypeManager)ModelUtil.getBean("articleTypeManager");
        ArticleType articleType = articleTypeManager.findByName(ApplicationParamUtil.findParamByName(getParamArticleName()));
		return new VeggProductionViewHandler(
				new ProductionApplyList(
						login,veggProductionVManager, null, "Vegg", null,managerRepository), "Veggproduksjon",
				login,articleType,managerRepository,
				deviationViewHandlerFactory,setProductionUnitActionFactory);
	}

	/**
	 * @see no.ugland.utransprod.gui.AbstractProductionPackageWindow#usePrintButton()
	 */
	@Override
	protected boolean usePrintButton() {
		return false;
	}

}
