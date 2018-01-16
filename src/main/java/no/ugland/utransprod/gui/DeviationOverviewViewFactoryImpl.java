package no.ugland.utransprod.gui;

import no.ugland.utransprod.gui.handlers.DeviationViewHandler;
import no.ugland.utransprod.gui.handlers.DeviationViewHandler2;
import no.ugland.utransprod.gui.handlers.PreventiveActionViewHandler;
import no.ugland.utransprod.model.Deviation;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.service.ManagerRepository;

import com.google.inject.Inject;

public class DeviationOverviewViewFactoryImpl implements DeviationOverviewViewFactory {

	// private DeviationViewHandlerFactory deviationViewHandlerFactory;
	private PreventiveActionViewHandler preventiveActionViewHandler;
	private ManagerRepository managerRepository;

	@Inject
	public DeviationOverviewViewFactoryImpl(
			// DeviationViewHandlerFactory deviationViewHandlerFactory,
			PreventiveActionViewHandler preventiveActionViewHandler, ManagerRepository managerRepository) {
		super();
		this.managerRepository = managerRepository;
		// this.deviationViewHandlerFactory = deviationViewHandlerFactory;
		this.preventiveActionViewHandler = preventiveActionViewHandler;
	}

	public DeviationOverviewView2 create(DeviationViewHandler2 deviationViewHandler, boolean useSearchButton,
			Order aOrder, boolean doSeeAll, boolean forOrderInfo, boolean isForRegisterNew,
			Deviation notDisplayDeviation, boolean isDeviationTableEditable) {
		return new DeviationOverviewView2(preventiveActionViewHandler, deviationViewHandler, useSearchButton, aOrder,
				doSeeAll, forOrderInfo, isForRegisterNew, notDisplayDeviation, isDeviationTableEditable,
				managerRepository);
	}

}
