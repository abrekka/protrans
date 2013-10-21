package no.ugland.utransprod.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import no.ugland.utransprod.gui.ProTransMain;
import no.ugland.utransprod.gui.handlers.TransportPriceHandler;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.util.Util;

import com.google.inject.Inject;

public class ImportPostalCodesAction extends AbstractImport {
	
	private static final long serialVersionUID = 1L;

	@Inject
	public ImportPostalCodesAction(ManagerRepository aManagerRepository){
		super("Importer gyldige postnummer...",aManagerRepository,TransportPriceHandler.TransportCostActionEnum.IMPORT);
	}


}
