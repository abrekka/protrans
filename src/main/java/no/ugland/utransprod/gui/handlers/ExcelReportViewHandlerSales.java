package no.ugland.utransprod.gui.handlers;

import java.awt.Dimension;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.util.excel.ExcelReportEnum;
import no.ugland.utransprod.util.excel.ExcelReportSettingSales;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.debug.FormDebugPanel;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class ExcelReportViewHandlerSales extends ExcelReportViewHandler {
    private JCheckBox checkBoxSalesMan;
    public ExcelReportViewHandlerSales(ExcelReportEnum excelReportType) {
        super(excelReportType,new Dimension(320, 130));
        presentationModel = new PresentationModel(new ExcelReportSettingSales(excelReportType));
    }
    
    public JPanel buildConstraintPanel(WindowInterface window,boolean addEmpty) {
       initComponents(window,addEmpty);
        FormLayout layout = new FormLayout(
                "p,3dlu,30dlu,3dlu,p:grow,3dlu,30dlu,3dlu,p,3dlu,30dlu,10dlu",
                "p,3dlu,p,3dlu,p");
        PanelBuilder builder = new PanelBuilder(layout);
        // PanelBuilder builder = new PanelBuilder(new FormDebugPanel(),layout);
        CellConstraints cc = new CellConstraints();

        if (excelReportEnum.useFrom()) {
            builder.addLabel("År:", cc.xy(1, 1));
            builder.add(yearChooser, cc.xy(3, 1));
            if (excelReportEnum.useTo()) {
                builder.addLabel("Fra uke:", cc.xy(5, 1));
                builder.addLabel("Til uke:", cc.xy(9, 1));
                builder.add(comboBoxWeekTo, cc.xy(11, 1));
            } else {
                builder.addLabel("Uke:", cc.xy(5, 1));
            }
            builder.add(comboBoxWeekFrom, cc.xy(7, 1));
        }

        if (comboBoxReportType != null) {
            builder.addLabel("Rapport:", cc.xyw(1, 3, 3));
            builder.add(comboBoxReportType, cc.xyw(5, 3, 7));
        }
        if (excelReportEnum.useProductArea()) {
        builder.addLabel("Produktområde:", cc.xyw(1, 5, 5));
        builder.add(comboBoxProductArea, cc.xyw(6, 5, 4));
        }
        builder.add(checkBoxSalesMan,cc.xyw(11, 5,2));

        return builder.getPanel();
    }

    @Override
    protected void initComponents(WindowInterface window,boolean addEmpty) {
        super.initComponents(window,addEmpty);
        checkBoxSalesMan = getCheckBoxSalesman();
    }

    private JCheckBox getCheckBoxSalesman() {
        JCheckBox checkBox=BasicComponentFactory.createCheckBox(presentationModel
                .getModel(ExcelReportSettingSales.PROPERTY_SALESMAN), "Selger");
        checkBox.setName("CheckBoxSalesman");
        return checkBox;
    }

}
