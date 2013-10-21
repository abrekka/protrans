package no.ugland.utransprod.gui.action;

import no.ugland.utransprod.gui.handlers.TransportPriceHandler;
import no.ugland.utransprod.service.ManagerRepository;

import com.google.inject.Inject;

public class ImportSnowloadAction extends AbstractImport {
	@Inject
	public ImportSnowloadAction(ManagerRepository aManagerRepository){
		super("Importer snølaster...",aManagerRepository,TransportPriceHandler.TransportCostActionEnum.SNOW_LOAD);
	}

}
