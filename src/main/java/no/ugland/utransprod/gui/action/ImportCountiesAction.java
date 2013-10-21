package no.ugland.utransprod.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import no.ugland.utransprod.gui.ProTransMain;
import no.ugland.utransprod.gui.handlers.TransportPriceHandler;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.util.Util;

import com.google.inject.Inject;

public class ImportCountiesAction extends AbstractImport {
	private static final long serialVersionUID = 1L;
	
	@Inject
	public ImportCountiesAction(ManagerRepository aManagerRepository){
		super("Importer fylker...",aManagerRepository,TransportPriceHandler.TransportCostActionEnum.COUNTY);
	}


}
