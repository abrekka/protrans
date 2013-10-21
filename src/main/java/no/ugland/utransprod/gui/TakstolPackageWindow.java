package no.ugland.utransprod.gui;

import no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler;
import no.ugland.utransprod.gui.handlers.DeviationViewHandlerFactory;
import no.ugland.utransprod.gui.handlers.TableEnum;
import no.ugland.utransprod.gui.handlers.TakstolPackageViewHandler;
import no.ugland.utransprod.gui.model.TakstolPackageApplyList;
import no.ugland.utransprod.model.PackableListItem;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.TakstolPackageVManager;
import no.ugland.utransprod.service.VismaFileCreator;

import com.google.inject.Inject;
import com.google.inject.name.Named;

/**
 * Standalone vindu for takstolpakking
 * @author atle.brekka
 */
public class TakstolPackageWindow extends AbstractProductionPackageWindow<PackableListItem> {
    private String mainArticleName;
    private TakstolPackageApplyList takstolPackageApplyList;
    private ManagerRepository managerRepository;
    private DeviationViewHandlerFactory deviationViewHandlerFactory;

    @Inject
    public TakstolPackageWindow(//final VismaFileCreator aVismaFileCreator,
            final TakstolPackageVManager aTakstolPackageVManager,
            @Named(value = "takstol_article") final String aMainArticleName,
            TakstolPackageApplyList aTakstolPackageApplyList,final Login aLogin,ManagerRepository aManagerRepository,DeviationViewHandlerFactory aDeviationViewHandlerFactory) {
    	super(aLogin);
    	managerRepository=aManagerRepository;
    	deviationViewHandlerFactory=aDeviationViewHandlerFactory;
        login=aLogin;
        takstolPackageApplyList = aTakstolPackageApplyList;
        mainArticleName = aMainArticleName;
    }

    /**
     * @see no.ugland.utransprod.gui.AbstractProductionPackageWindow#getParamArticleName()
     */
    @Override
    protected String getParamArticleName() {
        return "takstol_artikkel";
    }

    /**
     * @see no.ugland.utransprod.gui.AbstractProductionPackageWindow#getWindowTitle()
     */
    @Override
    protected String getWindowTitle() {
        return "Pakking av standard takstol";
    }

    /**
     * @see no.ugland.utransprod.gui.AbstractProductionPackageWindow#getViewHandler()
     */
    @Override
    public AbstractProductionPackageViewHandler<PackableListItem> getViewHandler() {
        return new TakstolPackageViewHandler(takstolPackageApplyList,login, TableEnum.TABLEPACKAGETAKSTOL, mainArticleName,managerRepository,deviationViewHandlerFactory);
    }

    /**
     * @see no.ugland.utransprod.gui.AbstractProductionPackageWindow#usePrintButton()
     */
    @Override
    protected boolean usePrintButton() {
        return true;
    }

}
