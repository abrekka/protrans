package no.ugland.utransprod.gui;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import no.ugland.utransprod.gui.handlers.AbstractViewHandler;
import no.ugland.utransprod.gui.handlers.TransportCostViewHandler;
import no.ugland.utransprod.gui.model.TransportCostBasisModel;
import no.ugland.utransprod.model.TransportCostBasis;

import com.jgoodies.forms.builder.ButtonStackBuilder;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.toedter.calendar.JYearChooser;

public class TransportCostView extends
        OverviewView<TransportCostBasis, TransportCostBasisModel> {
    public TransportCostView(
            final AbstractViewHandler<TransportCostBasis, TransportCostBasisModel> handler) {
        super(handler);
    }

    private JYearChooser yearChooser;

    private JComboBox comboBoxWeekFrom;

    private JComboBox comboBoxWeekTo;

    private JButton buttonGenerate;

    private JButton buttonReport;

    private JButton buttonSetInvoiceNr;

    @Override
    public final JPanel buildPanel(final WindowInterface window) {
        initComponents(window);
        FormLayout layout = new FormLayout("10dlu,150dlu:grow,10dlu",
                "10dlu,p,3dlu,fill:150dlu:grow,3dlu,p,3dlu");
        PanelBuilder builder = new PanelBuilder(layout);
        // PanelBuilder builder = new PanelBuilder(layout,new FormDebugPanel());
        CellConstraints cc = new CellConstraints();

        builder.add(buildConstraintPanel(), cc.xy(2, 2));

        builder.add(((TransportCostViewHandler) viewHandler).buildTabbedPane(
                buttonRemove, buttonReport, buttonSetInvoiceNr), cc.xy(2, 4));
        builder.add(ButtonBarFactory.buildCenteredBar(buttonCancel), cc
                .xy(2, 6));
        return builder.getPanel();
    }

    private JPanel buildConstraintPanel() {
        FormLayout layout = new FormLayout(
                "p,3dlu,30dlu,3dlu,p,3dlu,p,3dlu,p,3dlu,p", "p,3dlu,p");
        PanelBuilder builder = new PanelBuilder(layout);
        CellConstraints cc = new CellConstraints();

        builder.addLabel("År:", cc.xy(1, 1));
        builder.add(yearChooser, cc.xy(3, 1));
        builder.addLabel("Fra uke:", cc.xy(5, 1));
        builder.add(comboBoxWeekFrom, cc.xy(7, 1));
        builder.addLabel("Til uke:", cc.xy(9, 1));
        builder.add(comboBoxWeekTo, cc.xy(11, 1));
        builder.add(buildButtonPanelGenerate(), cc.xyw(7, 3, 5));

        return builder.getPanel();
    }

    protected final JPanel buildButtonPanelGenerate() {
        ButtonStackBuilder builder = new ButtonStackBuilder();
        builder.addGridded(buttonGenerate);

        return builder.getPanel();
    }

    @Override
    protected final void initComponents(final WindowInterface window) {
        super.initComponents(window);
        yearChooser = ((TransportCostViewHandler) viewHandler).getYearChooser();
        comboBoxWeekFrom = ((TransportCostViewHandler) viewHandler)
                .getComboBoxWeekFrom();
        comboBoxWeekTo = ((TransportCostViewHandler) viewHandler)
                .getComboBoxWeekTo();
        buttonGenerate = ((TransportCostViewHandler) viewHandler)
                .getButtonGenerate();
        buttonReport = ((TransportCostViewHandler) viewHandler)
                .getButtonReport(window);
        buttonSetInvoiceNr = ((TransportCostViewHandler) viewHandler)
                .getButtonSetInvoiceNr(window);

    }

    @Override
    protected final void updateActionEnablement() {
        boolean hasSelection = viewHandler// .getObjectSelectionList()
                .objectSelectionListHasSelection();
        if (buttonReport != null) {
            buttonReport.setEnabled(hasSelection);
        }
        if (buttonSetInvoiceNr != null) {
            buttonSetInvoiceNr.setEnabled(hasSelection);
        }
        if (viewHandler.hasWriteAccess()) {
            buttonRemove.setEnabled(hasSelection);
        }
    }
}
