package no.ugland.utransprod.gui.action;

import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import no.ugland.utransprod.gui.ExcelReportView;
import no.ugland.utransprod.gui.MenuBarBuilderInterface;
import no.ugland.utransprod.gui.handlers.ExcelReportViewHandler;
import no.ugland.utransprod.util.excel.ExcelReportEnum;

import com.google.inject.Inject;

public class SalesGoalReportAction extends AbstractAction{
	private final MenuBarBuilderInterface menuBarBuilder;
	@Inject
	public SalesGoalReportAction(MenuBarBuilderInterface aMenuBarBuilder){
		super("Salgsmålrapport...");
		this.menuBarBuilder = aMenuBarBuilder;
	}

	public void actionPerformed(ActionEvent e) {
		ExcelReportViewHandler excelReportViewHandler = new ExcelReportViewHandler(
				ExcelReportEnum.SALES_GOAL,new Dimension(240, 120));
		menuBarBuilder.openFrame(new ExcelReportView(excelReportViewHandler, false));
		
	}

}
