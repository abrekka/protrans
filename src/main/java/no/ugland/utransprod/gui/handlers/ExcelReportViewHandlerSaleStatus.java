package no.ugland.utransprod.gui.handlers;

import java.awt.Dimension;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.util.Util;
import no.ugland.utransprod.util.excel.ExcelReportEnum;
import no.ugland.utransprod.util.excel.ExcelReportSetting;
import no.ugland.utransprod.util.excel.ExcelReportSettingDeviation;
import no.ugland.utransprod.util.excel.ExcelReportSettingOwnProduction;
import no.ugland.utransprod.util.excel.ExcelReportSettingSaleStatus;

public class ExcelReportViewHandlerSaleStatus extends ExcelReportViewHandler {
    private JCheckBox checkBoxOrder;
    private JCheckBox checkBoxOffer;

    public ExcelReportViewHandlerSaleStatus() {
        super(ExcelReportEnum.SALE_STATUS_REPORT,new Dimension(320, 130));
        presentationModel = new PresentationModel(new ExcelReportSettingSaleStatus());
    }

    public JPanel buildConstraintPanel(WindowInterface window,boolean addEmpty) {
        initComponents(window,addEmpty);
        FormLayout layout = new FormLayout("p,3dlu,70dlu", "p,3dlu,p");
        PanelBuilder builder = new PanelBuilder(layout);
        // PanelBuilder builder = new PanelBuilder(new FormDebugPanel(),layout);
        CellConstraints cc = new CellConstraints();

        builder.add(checkBoxOffer, cc.xy(1, 1));
        builder.add(checkBoxOrder, cc.xy(3, 1));

        builder.addLabel("Produktområde:", cc.xy(1, 3));
        builder.add(comboBoxProductArea, cc.xy(3, 3));

        return builder.getPanel();
    }

    @Override
    protected void initComponents(WindowInterface window,boolean addEmpty) {
        super.initComponents(window,addEmpty);
        checkBoxOffer = getCheckBoxOffer();
        checkBoxOrder = getCheckBoxOrder();
    }

    private JCheckBox getCheckBoxOffer() {
        JCheckBox checkBox = BasicComponentFactory.createCheckBox(presentationModel
                .getModel(ExcelReportSettingSaleStatus.PROPERTY_USE_OFFER), "Tilbud");
        checkBox.setName("CheckBoxOffer");
        return checkBox;
    }

    private JCheckBox getCheckBoxOrder() {
        JCheckBox checkBox = BasicComponentFactory.createCheckBox(presentationModel
                .getModel(ExcelReportSettingSaleStatus.PROPERTY_USE_ORDER), "Ordre");
        checkBox.setName("CheckBoxOrder");
        return checkBox;
    }

    protected boolean checkParameters(ExcelReportSetting setting, WindowInterface window) {
        String errorString = !((ExcelReportSettingSaleStatus) setting).getUseOffer()
                && !((ExcelReportSettingSaleStatus) setting).getUseOrder() ? "Det må velges tilbud,ordre eller begge"
                : null;

        if (errorString != null) {
            Util.showErrorDialog(window, "Rapportparametre", errorString);
        }
        return errorString != null ? false : true;

    }

    /**
     * @see no.ugland.utransprod.gui.handlers.ExcelReportViewHandler#getWindowSize()
     */
    @Override
    public Dimension getWindowSize() {
        return new Dimension(250, 130);
    }
    
}
