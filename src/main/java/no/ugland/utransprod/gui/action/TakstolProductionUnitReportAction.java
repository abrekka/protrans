package no.ugland.utransprod.gui.action;

import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import no.ugland.utransprod.gui.ExcelReportView;
import no.ugland.utransprod.gui.MenuBarBuilderInterface;
import no.ugland.utransprod.gui.handlers.ExcelReportViewHandler;
import no.ugland.utransprod.util.excel.ExcelReportEnum;

import com.google.inject.Inject;

public class TakstolProductionUnitReportAction extends AbstractAction{
	private final MenuBarBuilderInterface menuBarBuilder;

	@Inject
	public TakstolProductionUnitReportAction(MenuBarBuilderInterface aMenuBarBuilder){
		super("Jiggrapport...");
		this.menuBarBuilder = aMenuBarBuilder;
	}
	
	public void generateProductionUnitReport() {
		
		//hent alle ordre som har jigg satt og er produsert i periode
		//hent alle ordre med ordrenummer som er produsert og har jigg og knytt disse sammen
		//gå gjennom alle ordre og sjekk produsertdato
		ExcelReportViewHandler excelReportViewHandler = new ExcelReportViewHandler(
				ExcelReportEnum.JIGG_REPORT,new Dimension(320, 110));
		menuBarBuilder.openFrame(new ExcelReportView(excelReportViewHandler, false));
	}

	public void actionPerformed(ActionEvent e) {
		generateProductionUnitReport();
		
	}

}
