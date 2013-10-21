package no.ugland.utransprod.gui.handlers;

import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.service.ManagerRepository;

import com.google.inject.Inject;

public class ConstructionTypeViewHandlerFactoryImpl implements ConstructionTypeViewHandlerFactory{

	private Login login;
	private ManagerRepository managerRepository;


	@Inject
	public ConstructionTypeViewHandlerFactoryImpl(final Login aLogin,final ManagerRepository aManagerRepository) {
		login=aLogin;
		managerRepository=aManagerRepository;
	}
		
	
	public ConstructionTypeViewHandler create(boolean isMasterDialog,
			boolean masterOverview) {
		return new ConstructionTypeViewHandler(login,managerRepository,isMasterDialog,masterOverview);
	}

}
