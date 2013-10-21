package no.ugland.utransprod.gui;

import no.ugland.utransprod.gui.handlers.DeviationViewHandler;
import no.ugland.utransprod.gui.handlers.PreventiveActionViewHandler;
import no.ugland.utransprod.model.Deviation;
import no.ugland.utransprod.model.Order;

import com.google.inject.Inject;

public class DeviationOverviewViewFactoryImpl implements DeviationOverviewViewFactory{

	//private DeviationViewHandlerFactory deviationViewHandlerFactory;
	private PreventiveActionViewHandler preventiveActionViewHandler;
	
	

	@Inject
	public DeviationOverviewViewFactoryImpl(
			//DeviationViewHandlerFactory deviationViewHandlerFactory,
			PreventiveActionViewHandler preventiveActionViewHandler) {
		super();
		//this.deviationViewHandlerFactory = deviationViewHandlerFactory;
		this.preventiveActionViewHandler = preventiveActionViewHandler;
	}

	public DeviationOverviewView create(DeviationViewHandler deviationViewHandler,boolean useSearchButton, Order aOrder,
			boolean doSeeAll, boolean forOrderInfo, boolean isForRegisterNew,
			Deviation notDisplayDeviation, boolean isDeviationTableEditable) {
		return new DeviationOverviewView( preventiveActionViewHandler,deviationViewHandler, useSearchButton, aOrder, doSeeAll, forOrderInfo, isForRegisterNew, notDisplayDeviation, isDeviationTableEditable);
	}

}
