package no.ugland.utransprod.gui.handlers;

import java.awt.Dimension;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.service.ProductAreaGroupManager;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.MonthEnum;
import no.ugland.utransprod.util.Util;
import no.ugland.utransprod.util.excel.ExcelReportEnum;
import no.ugland.utransprod.util.excel.ExcelReportSetting;
import no.ugland.utransprod.util.excel.ExcelReportSettingDeviation;
import no.ugland.utransprod.util.excel.ExcelReportSettingOwnProduction;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.ComboBoxAdapter;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class ExcelReportViewHandlerOwnProduction extends ExcelReportViewHandler {

    private JComboBox comboBoxProductAreaGroup;
    private List<ProductAreaGroup> areas;

    public ExcelReportViewHandlerOwnProduction(ExcelReportEnum excelReportType) {
        super(excelReportType,new Dimension(250, 130));
        presentationModel = new PresentationModel(new ExcelReportSettingOwnProduction(excelReportType));

        ProductAreaGroupManager productAreaGroupManager = (ProductAreaGroupManager) ModelUtil
                .getBean("productAreaGroupManager");
        areas = productAreaGroupManager.findAll();
    }

    public JPanel buildConstraintPanel(WindowInterface window,boolean addEmpty) {
        initComponents(window,addEmpty);
        FormLayout layout = new FormLayout("p,3dlu,30dlu,3dlu,p:grow,3dlu,30dlu,3dlu,p,3dlu,30dlu",
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
        builder.addLabel("Produktområde:", cc.xyw(1, 5, 5));
        builder.add(comboBoxProductAreaGroup, cc.xyw(6, 5, 6));

        return builder.getPanel();
    }

    @Override
    protected void initComponents(WindowInterface window,boolean addEmpty) {
        super.initComponents(window,addEmpty);
        comboBoxProductAreaGroup = getComboBoxProductAreaGroup();
    }

    private JComboBox getComboBoxProductAreaGroup() {
        return new JComboBox(new ComboBoxAdapter(areas, presentationModel
                .getModel(ExcelReportSettingOwnProduction.PROPERTY_PRODUCT_AREA_GROUP)));
    }

    protected boolean checkParameters(ExcelReportSetting setting, WindowInterface window) {
        String errorString = null;
        if (excelReportEnum.useFrom() && setting.getWeekFrom() == null) {
            errorString = "Det må velges fra uke";
        } else if (excelReportEnum.useTo() && setting.getWeekTo() == null) {
            errorString = "Det må velges til uke";
        } else if (((ExcelReportSettingOwnProduction) setting).getProductAreaGroup() == null) {
            errorString = "Det må velges produktområde";
        }

        if (errorString != null) {
            Util.showErrorDialog(window, "Rapportparametre", errorString);
            return false;
        }
        return true;

    }
}
