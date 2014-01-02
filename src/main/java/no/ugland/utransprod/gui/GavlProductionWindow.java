package no.ugland.utransprod.gui;

import no.ugland.utransprod.gui.action.SetProductionUnitActionFactory;
import no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler;
import no.ugland.utransprod.gui.handlers.DeviationViewHandlerFactory;
import no.ugland.utransprod.gui.handlers.GavlProductionViewHandler;
import no.ugland.utransprod.gui.model.ProductionApplyList;
import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.Produceable;
import no.ugland.utransprod.service.ArticleTypeManager;
import no.ugland.utransprod.service.GavlProductionVManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.util.ApplicationParamUtil;
import no.ugland.utransprod.util.ModelUtil;

import com.google.inject.Inject;

/**
 * Klasse som håndterer visning av vindu for produksjon av gavl
 * 
 * @author atle.brekka
 * 
 */
public class GavlProductionWindow extends AbstractProductionPackageWindow<Produceable> {

    private DeviationViewHandlerFactory deviationViewHandlerFactory;
    private ManagerRepository managerRepository;
    private SetProductionUnitActionFactory setProductionUnitActionFactory;

    @Inject
    public GavlProductionWindow(Login login, ManagerRepository aManagerRepository, DeviationViewHandlerFactory aDeviationViewHandlerFactory,
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
	return "gavl_artikkel";
    }

    /**
     * @see no.ugland.utransprod.gui.AbstractProductionPackageWindow#getWindowTitle()
     */
    @Override
    protected String getWindowTitle() {
	return "Produksjon av gavl";
    }

    /**
     * @see no.ugland.utransprod.gui.AbstractProductionPackageWindow#getViewHandler()
     */
    @Override
    public AbstractProductionPackageViewHandler<Produceable> getViewHandler() {
	GavlProductionVManager gavlProductionVManager = (GavlProductionVManager) ModelUtil.getBean("gavlProductionVManager");
	ArticleTypeManager articleTypeManager = (ArticleTypeManager) ModelUtil.getBean("articleTypeManager");
	ArticleType articleType = articleTypeManager.findByName(ApplicationParamUtil.findParamByName(getParamArticleName()));
	return new GavlProductionViewHandler(new ProductionApplyList(login, gavlProductionVManager, "Gavl", "Gavl", null, managerRepository),
		"Gavlproduksjon", login, articleType, managerRepository, deviationViewHandlerFactory, setProductionUnitActionFactory);
    }

    /**
     * @see no.ugland.utransprod.gui.AbstractProductionPackageWindow#usePrintButton()
     */
    @Override
    protected boolean usePrintButton() {
	return false;
    }

}
