package no.ugland.utransprod.gui.action;

import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.google.inject.Inject;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.ExcelReportView;
import no.ugland.utransprod.gui.MenuBarBuilderInterface;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.handlers.ExcelReportViewHandler;
import no.ugland.utransprod.util.Util;
import no.ugland.utransprod.util.excel.ExcelReportEnum;
import no.ugland.utransprod.util.excel.ExcelReportSetting;

public class OrdrereserveTakstolReportAction extends AbstractAction{
	private final MenuBarBuilderInterface menuBarBuilder;
	@Inject
	public OrdrereserveTakstolReportAction(MenuBarBuilderInterface aMenuBarBuilder){
		super("Ordrereserve takstol...");
		this.menuBarBuilder = aMenuBarBuilder;
	}
	public void generateReport() {
		try {
			ExcelReportViewHandler excelReportViewHandler = new ExcelReportViewHandler(
					ExcelReportEnum.ORDRERESERVE_TAKSTOL,new Dimension(320, 110));
			ExcelReportSetting excelReportSetting=new ExcelReportSetting(ExcelReportEnum.ORDRERESERVE_TAKSTOL);
			excelReportViewHandler.generateExcel(null,excelReportSetting);
		} catch (ProTransException e) {
			Util.showErrorDialog((WindowInterface)null, "Feil", e.getMessage());
			e.printStackTrace();
		}
		
		
		/*ExcelReportViewHandler excelReportViewHandler = new ExcelReportViewHandler(
				ExcelReportEnum.ORDRERESERVE_TAKSTOL,new Dimension(320, 110));
		menuBarBuilder.openFrame(new ExcelReportView(excelReportViewHandler, false));*/
		
	}
	public void actionPerformed(ActionEvent e) {
		generateReport();
		
	}
	
	public void generateReportTest(ExcelReportSetting excelReportSetting) throws Exception{
		ExcelReportViewHandler excelReportViewHandler = new ExcelReportViewHandler(
				excelReportSetting.getExcelReportType(),new Dimension(320, 110));
		excelReportViewHandler.generateExcel(null,excelReportSetting);
	}

}
