package no.ugland.utransprod.gui.action;

import no.ugland.utransprod.gui.handlers.TransportPriceHandler;
import no.ugland.utransprod.service.ManagerRepository;

import com.google.inject.Inject;

public class ImportAreasAction extends AbstractImport {
	@Inject
	public ImportAreasAction(ManagerRepository aManagerRepository){
		super("Importer kommuner...",aManagerRepository,TransportPriceHandler.TransportCostActionEnum.AREA);
	}
}
