package no.ugland.utransprod.gui;

import no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler;
import no.ugland.utransprod.gui.handlers.TakstolProductionViewHandler;
import no.ugland.utransprod.model.Produceable;

import com.google.inject.Inject;

/**
 * Standalone vindu for takstolproduksjon
 * 
 * @author atle.brekka
 */
public class TakstolProductionWindow extends
		AbstractProductionPackageWindow<Produceable> {

	private TakstolProductionViewHandler takstolProductionViewHandler;

	@Inject
	public TakstolProductionWindow(
			final Login aLogin,
			TakstolProductionViewHandler aTakstolProductionViewHandler) {
		super(aLogin);
		takstolProductionViewHandler = aTakstolProductionViewHandler;

	}

	/**
	 * @see no.ugland.utransprod.gui.AbstractProductionPackageWindow#getParamArticleName()
	 */
	@Override
	protected final String getParamArticleName() {
		return "takstol_artikkel";
	}

	/**
	 * @see no.ugland.utransprod.gui.AbstractProductionPackageWindow#getWindowTitle()
	 */
	@Override
	protected final String getWindowTitle() {
		return "Produksjon av takstol";
	}

	/**
	 * @see no.ugland.utransprod.gui.AbstractProductionPackageWindow#getViewHandler()
	 */
	@Override
	public final AbstractProductionPackageViewHandler<Produceable> getViewHandler() {
		/*
		 * return new TakstolProductionViewHandler(takstolProductionApplyList,
		 * login, articleType, managerRepository, deviationViewHandlerFactory);
		 */
		return takstolProductionViewHandler;
	}

	/**
	 * @see no.ugland.utransprod.gui.AbstractProductionPackageWindow#usePrintButton()
	 */
	@Override
	protected final boolean usePrintButton() {
		return false;
	}

}
