package no.ugland.utransprod.gui.handlers;

import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.action.SetProductionUnitActionFactory;
import no.ugland.utransprod.gui.model.ApplyListInterface;
import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.Produceable;
import no.ugland.utransprod.service.ManagerRepository;

public class TaksteinSkarpnesViewHandler extends ProductionViewHandler {

    //private static DeviationViewHandlerFactory deviationViewHandlerFactory;

    
	public TaksteinSkarpnesViewHandler(
            ApplyListInterface<Produceable> productionInterface,
            Login login,ManagerRepository managerRepository,DeviationViewHandlerFactory deviationViewHandlerFactory,SetProductionUnitActionFactory setProductionUnitActionFactory,ArticleType articleType) {
        super(productionInterface, "Takstein", login,"bestilt",
                null,TableEnum.TABLETAKSTEIN,articleType,managerRepository,deviationViewHandlerFactory,setProductionUnitActionFactory);
    }

    @Override
    void initColumnWidthExt() {
        // Transportdato
        table.getColumnExt("Transport").setPreferredWidth(100);
        // Ordre
        table.getColumnExt("Ordre").setPreferredWidth(200);
        table.getColumnExt("Prod.dato").setPreferredWidth(60);
        // Antall
        table.getColumnExt("Antall").setPreferredWidth(50);

        // Spesifikasjon
        table.getColumnExt("Spesifikasjon").setPreferredWidth(400);

    }
  
}
