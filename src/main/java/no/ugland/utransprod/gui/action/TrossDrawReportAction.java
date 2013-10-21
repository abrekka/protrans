package no.ugland.utransprod.gui.action;

import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import no.ugland.utransprod.gui.ExcelReportView;
import no.ugland.utransprod.gui.MenuBarBuilderInterface;
import no.ugland.utransprod.gui.handlers.ExcelReportViewHandler;
import no.ugland.utransprod.util.excel.ExcelReportEnum;

import com.google.inject.Inject;

public class TrossDrawReportAction extends AbstractAction{
	private final MenuBarBuilderInterface menuBarBuilder;
	@Inject
	public TrossDrawReportAction(MenuBarBuilderInterface aMenuBarBuilder){
		super("Takstoltegnerrapport...");
		this.menuBarBuilder = aMenuBarBuilder;
	}

	public void actionPerformed(ActionEvent e) {
		ExcelReportViewHandler excelReportViewHandler = new ExcelReportViewHandler(
				ExcelReportEnum.TAKSTOLTEGNER,new Dimension(320, 120));
		menuBarBuilder.openFrame(new ExcelReportView(excelReportViewHandler, false));
		
	}

}
