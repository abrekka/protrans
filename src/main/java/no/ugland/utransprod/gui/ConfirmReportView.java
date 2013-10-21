package no.ugland.utransprod.gui;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import no.ugland.utransprod.gui.handlers.ConfirmReportViewHandler;
import no.ugland.utransprod.util.InternalFrameBuilder;

import org.jdesktop.swingx.JXTable;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.toedter.calendar.JYearChooser;

public class ConfirmReportView implements Viewer {
    private ConfirmReportViewHandler viewHandler;

    private JYearChooser yearChooser;

    private JComboBox comboBoxWeekFrom;
    private JComboBox comboBoxWeekTo;

    private JButton buttonGenerateReport;

    private JXTable tableResult;

    private JButton buttonCancel;

    private JButton buttonExcelResult;

    private JButton buttonExcelBasis;
    private JComboBox comboBoxProductAreaGroup;

    public ConfirmReportView(final ConfirmReportViewHandler handler) {
        viewHandler = handler;
    }

    public final JPanel buildPanel(final WindowInterface window) {
        initComponents(window);
        FormLayout layout = new FormLayout(
                "10dlu,center:200dlu:grow,1dlu,10dlu",
                "10dlu,p,3dlu,fill:300:grow,3dlu,p,3dlu");
        PanelBuilder builder = new PanelBuilder(layout);
        // PanelBuilder builder = new PanelBuilder(new FormDebugPanel(),
        // layout);
        CellConstraints cc = new CellConstraints();

        builder.add(buildContstraintPanel(), cc.xy(2, 2));

        builder.add(buildTabbedResultPanel(window), cc.xyw(2, 4, 2));
        builder.add(ButtonBarFactory.buildCenteredBar(buttonCancel), cc.xyw(2,
                6, 2));

        return builder.getPanel();
    }

    private JPanel buildContstraintPanel() {
        FormLayout layout = new FormLayout("p,3dlu,30dlu,3dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,p",
                "p,3dlu,p");
        PanelBuilder builder = new PanelBuilder(layout);
        CellConstraints cc = new CellConstraints();

        builder.addLabel("År:", cc.xy(1, 1));
        builder.add(yearChooser, cc.xy(3, 1));
        builder.addLabel("Fra uke:", cc.xy(5, 1));
        builder.add(comboBoxWeekFrom, cc.xy(7, 1));
        builder.addLabel("Til uke:", cc.xy(9, 1));
        builder.add(comboBoxWeekTo, cc.xy(11, 1));
        builder.addLabel("Produktområde:",cc.xy(13, 1));
        builder.add(comboBoxProductAreaGroup,cc.xy(15, 1));
        builder.add(ButtonBarFactory.buildCenteredBar(buttonGenerateReport), cc
                .xyw(1, 3, 15));

        return builder.getPanel();
    }

    private JPanel buildTabbedResultPanel(final WindowInterface window) {
        FormLayout layout = new FormLayout("200dlu:grow", "fill:200dlu:grow");
        PanelBuilder builder = new PanelBuilder(layout);

        CellConstraints cc = new CellConstraints();

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setName("TabbedPaneResult");
        tabbedPane.add("Resultat", buildResultPanel());
        tabbedPane.add("Grunnlag", buildBasisPanel(window));
        builder.add(tabbedPane, cc.xy(1, 1));

        return builder.getPanel();
    }

    private JPanel buildResultPanel() {
        FormLayout layout = new FormLayout("50dlu:grow",
                "fill:50dlu:grow,3dlu,p");
        PanelBuilder builder = new PanelBuilder(layout);
        CellConstraints cc = new CellConstraints();
        builder.add(new JScrollPane(tableResult), cc.xy(1, 1));
        builder.add(ButtonBarFactory.buildCenteredBar(buttonExcelResult), cc
                .xy(1, 3));
        return builder.getPanel();
    }

    private Component buildBasisPanel(final WindowInterface window) {
        FormLayout layout = new FormLayout("p:grow", "fill:p:grow,3dlu,p");
        PanelBuilder builder = new PanelBuilder(layout);
        CellConstraints cc = new CellConstraints();

        builder.add(viewHandler.getOrderPanelView().buildPanel(window, "200",
                null, "240", false), cc.xy(1, 1));
        builder.add(ButtonBarFactory.buildCenteredBar(buttonExcelBasis), cc.xy(
                1, 3));
        JPanel panel = builder.getPanel();
        panel.addComponentListener(viewHandler.getPanelBasisListener(window));
        return panel;
    }

    private void initComponents(final WindowInterface window) {
        yearChooser = viewHandler.getYearChooser();
        comboBoxWeekFrom = viewHandler.getComboBoxWeekFrom();
        comboBoxWeekTo = viewHandler.getComboBoxWeekTo();
        buttonGenerateReport = viewHandler.getButtonGenerateReport(window);
        tableResult = viewHandler.getTableResult();
        buttonCancel = viewHandler.getButtonCancel(window);
        buttonExcelResult = viewHandler.getButtonExcelResult(window);
        buttonExcelBasis = viewHandler.getButtonExcelBasis(window);
        comboBoxProductAreaGroup = viewHandler.getComboBoxProductAreaGroup();
    }

    public final WindowInterface buildWindow() {
        WindowInterface window = InternalFrameBuilder.buildInternalFrame(
                getTitle(), viewHandler.getWindowSize(), true);
        window.add(buildPanel(window), BorderLayout.CENTER);

        return window;
    }

    public void cleanUp() {

    }

    public final String getTitle() {
        return "Avropsrapport";
    }

    public void initWindow() {

    }

    public final boolean useDispose() {
        return true;
    }
}
