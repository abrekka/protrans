package no.ugland.utransprod.gui.action;

import no.ugland.utransprod.gui.handlers.TransportPriceHandler;
import no.ugland.utransprod.service.ManagerRepository;

import com.google.inject.Inject;

public class ImportZoneAdditionAssemblyAction extends AbstractImport {

    private static final long serialVersionUID = 1L;

    @Inject
    public ImportZoneAdditionAssemblyAction(ManagerRepository aManagerRepository) {
	super("Importer sonetillegg for montering...", aManagerRepository, TransportPriceHandler.TransportCostActionEnum.ZONE_ADDITION_ASSEMBLY);
    }

}
