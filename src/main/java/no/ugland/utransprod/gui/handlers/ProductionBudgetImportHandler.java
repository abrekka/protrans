package no.ugland.utransprod.gui.handlers;

import java.awt.Component;

import javax.swing.JLabel;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.model.BudgetType;
import no.ugland.utransprod.service.BudgetManager;
import no.ugland.utransprod.util.FileExtensionFilter;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Threadable;
import no.ugland.utransprod.util.Util;

public class ProductionBudgetImportHandler implements Threadable {
    private Component parentComponent;

    private WindowInterface waitWindow;
    private String openDir;

	private BudgetType budgetType;
    public ProductionBudgetImportHandler(final Component parent,final String aOpenDir,final BudgetType aBudgetType){
    	budgetType=aBudgetType;
        parentComponent=parent;
        openDir=aOpenDir;
    }

    public void doWhenFinished(Object object) {
        if (object != null) {
            Util.showErrorDialog(parentComponent, "Feil", object.toString());
        }else{
            Util
            .showMsgDialog(parentComponent, "Importert",
                    "Alle budsjettall er importert");
        }
        if (waitWindow != null) {
            waitWindow.dispose();
        }
        
    }

    public Object doWork(Object[] params, JLabel labelInfo) {
        String errorMsg = null;
        try {
            if (params != null) {
                waitWindow = (WindowInterface) params[0];
            }
            labelInfo.setText("Importerer budsjettall...");
            importBudget(labelInfo,waitWindow);

        } catch (ProTransException e) {
            e.printStackTrace();
            errorMsg = e.getMessage();
        }
        return errorMsg;
    }

    public void enableComponents(boolean enable) {
        // TODO Auto-generated method stub
        
    }

    public final void importBudget(final JLabel label,final WindowInterface window) throws ProTransException {
        BudgetManager productionBudgetManager = (BudgetManager) ModelUtil
                .getBean(BudgetManager.MANAGER_NAME);
        productionBudgetManager.setLabelInfo(label);
        String excelFileName = Util.getFileName(parentComponent,
                new FileExtensionFilter("xls", "Excel"),openDir);
        
        if (excelFileName != null) {
            productionBudgetManager.importBudget(excelFileName,budgetType);

        }
    }
}
