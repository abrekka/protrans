package no.ugland.utransprod.gui.handlers;

import no.ugland.utransprod.gui.DeviationOverviewViewFactory;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.model.Deviation;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.service.ManagerRepository;

import com.google.inject.Inject;

public class DeviationViewHandlerFactoryImpl implements
		DeviationViewHandlerFactory {
	private Login login;
	private ManagerRepository managerRepository;
	private PreventiveActionViewHandler preventiveActionViewHandler;
	//private DeviationOverviewViewFactory deviationOverviewViewFactory;

	@Inject
	public DeviationViewHandlerFactoryImpl(Login aLogin,
			ManagerRepository aManagerRepository,
			PreventiveActionViewHandler aPreventiveActionViewHandler
			//,DeviationOverviewViewFactory aDeviationOverviewViewFactory
			) {
		//deviationOverviewViewFactory = aDeviationOverviewViewFactory;
		preventiveActionViewHandler = aPreventiveActionViewHandler;
		login = aLogin;
		managerRepository = aManagerRepository;
	}

	public DeviationViewHandler2 create(Order aOrder, boolean doSeAll,
			boolean forOrderInfo, boolean isForRegisterNew,
			Deviation notDisplayDeviation, boolean isDeviationTableEditable) {
		return new DeviationViewHandler2(login, managerRepository,
				preventiveActionViewHandler, 
				//deviationOverviewViewFactory,
				aOrder, doSeAll, forOrderInfo, isForRegisterNew,
				notDisplayDeviation, isDeviationTableEditable);
	}

}
