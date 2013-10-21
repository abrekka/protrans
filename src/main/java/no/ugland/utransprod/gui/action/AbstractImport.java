package no.ugland.utransprod.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import no.ugland.utransprod.gui.ProTransMain;
import no.ugland.utransprod.gui.handlers.TransportPriceHandler;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.util.Util;

public abstract class AbstractImport extends AbstractAction {
	private ManagerRepository managerRepository;
	private TransportPriceHandler.TransportCostActionEnum transportCostActionEnum;
	
	public AbstractImport(String menuString,ManagerRepository aManagerRepository,TransportPriceHandler.TransportCostActionEnum aTransportCostActionEnum){
		super(menuString);
		transportCostActionEnum=aTransportCostActionEnum;
		managerRepository=aManagerRepository;
	}
	
	public void actionPerformed(ActionEvent e) {
		Util.createDialogAndRunInThread(new TransportPriceHandler(
				ProTransMain.PRO_TRANS_MAIN,
				transportCostActionEnum,managerRepository));
		
	}

}
