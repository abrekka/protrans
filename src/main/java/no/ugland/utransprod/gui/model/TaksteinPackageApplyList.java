package no.ugland.utransprod.gui.model;

import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.OrderLineManager;

import com.google.inject.Inject;

/**
 * Pakking av takstein
 * 
 * @author atle.brekka
 * 
 */
public class TaksteinPackageApplyList extends PackageApplyList {

	/**
	 * @param aUserType
	 * @param manager
	 */
	@Inject
	public TaksteinPackageApplyList(Login login,
			OrderLineManager manager,ManagerRepository aManagerRepository) {
		super(login, manager, "Takstein", "Takstein", null,null,aManagerRepository);
	}

}
