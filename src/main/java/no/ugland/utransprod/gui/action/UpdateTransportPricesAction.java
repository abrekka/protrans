package no.ugland.utransprod.gui.action;

import no.ugland.utransprod.gui.handlers.TransportPriceHandler;
import no.ugland.utransprod.service.ManagerRepository;

import com.google.inject.Inject;

public class UpdateTransportPricesAction extends AbstractImport {
	@Inject
	public UpdateTransportPricesAction(ManagerRepository aManagerRepository){
		super("Oppdater transportpriser...",aManagerRepository,TransportPriceHandler.TransportCostActionEnum.UPDATE);
	}
}
