package no.ugland.utransprod.gui;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import no.ugland.utransprod.gui.handlers.AbstractViewHandler;
import no.ugland.utransprod.gui.handlers.OrderViewHandler;
import no.ugland.utransprod.gui.model.OrderModel;
import no.ugland.utransprod.model.Order;

import com.jgoodies.forms.builder.ButtonStackBuilder;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * Klasse som viser oversikt over ordre
 * @author atle.brekka
 */
public class OrderOverviewView extends OverviewView<Order, OrderModel> {
    private JButton buttonStatistics;

    private JLabel labelNumberOf;

    private boolean selectView;

    private JLabel labelFilterInfo;

    private JComboBox comboBoxProductAreaGroup;

    /**
     * @param handler
     */
    public OrderOverviewView(
            final AbstractViewHandler<Order, OrderModel> handler) {
        this(handler, false);
    }

    /**
     * @param handler
     * @param selectDialog
     */
    public OrderOverviewView(
            final AbstractViewHandler<Order, OrderModel> handler,
            final boolean selectDialog) {
        super(handler);
        selectView = selectDialog;
    }

    /**
     * @see no.ugland.utransprod.gui.OverviewView#initEventHandling(no.ugland.utransprod.gui.WindowInterface)
     */
    @Override
    protected final void initEventHandling(final WindowInterface window) {
        viewHandler.setSelectionEmptyHandler(new SelectionEmptyHandler());
        if (selectView) {
            table.addMouseListener(((OrderViewHandler) viewHandler)
                    .getMouseListenerSelect(window));
        } else {
            table.addMouseListener(viewHandler.getDoubleClickHandler(window));
        }
    }

    /**
     * @see no.ugland.utransprod.gui.OverviewView#initComponents(no.ugland.utransprod.gui.WindowInterface)
     */
    @Override
    protected final void initComponents(final WindowInterface window) {
        super.initComponents(window);
        if (selectView) {
            buttonCancel = ((OrderViewHandler) viewHandler).getOkButton(window);
        }
        buttonStatistics = ((OrderViewHandler) viewHandler)
                .getButtonStatistics(window);
        buttonExcel = ((OrderViewHandler) viewHandler).getButtonExcel(window);
        labelNumberOf = ((OrderViewHandler) viewHandler).getLabelNumberOf();

        labelFilterInfo = ((OrderViewHandler) viewHandler).getLabelFilterInfo();
        comboBoxProductAreaGroup = ((OrderViewHandler) viewHandler)
                .getComboBoxProductAreaGroup();

    }

    /**
     * @see no.ugland.utransprod.gui.OverviewView#buildPanel(no.ugland.utransprod.gui.WindowInterface)
     */
    @Override
    public final JComponent buildPanel(final WindowInterface window) {
        initComponents(window);
        super.buildPanel(window);

        FormLayout layout = new FormLayout("15dlu,50dlu,p,3dlu,70dlu,"
                + viewHandler.getTableWidth() + ":grow,3dlu,p,15dlu",
                "10dlu,p,3dlu,10dlu,p,3dlu,p,3dlu,p,3dlu,p,"
                        + viewHandler.getTableHeight()
                        + ":grow,p,fill:30dlu,p,5dlu");
        // PanelBuilder builder = new PanelBuilder(layout,new FormDebugPanel());
        PanelBuilder builder = new PanelBuilder(layout);
        JScrollPane scrollPaneTable = new JScrollPane(table);
        CellConstraints cc = new CellConstraints();
        scrollPaneTable.setBorder(Borders.EMPTY_BORDER);
        builder.add(labelHeading, cc.xy(2, 2));
        builder.addLabel("Produktområde:", cc.xy(3, 2));
        builder.add(comboBoxProductAreaGroup, cc.xy(5, 2));
        builder.add(new JScrollPane(labelFilterInfo), cc.xyw(2, 14, 5));
        builder.add(scrollPaneTable, cc.xywh(2, 4, 5, 9));
        builder.add(labelNumberOf, cc.xy(2, 13));
        if (!selectView) {
            builder.add(buildButtonPanel(), cc.xywh(8, 5, 1, 10));

            builder.add(ButtonBarFactory.buildCenteredBar(buttonExcel,
                    buttonCancel), cc.xyw(2, 15, 7));
        } else {
            builder.add(ButtonBarFactory.buildCenteredBar(buttonCancel), cc
                    .xyw(2, 15, 7));
        }

        return builder.getPanel();
    }

    /**
     * @see no.ugland.utransprod.gui.OverviewView#buildButtonPanel()
     */
    @Override
    protected final JPanel buildButtonPanel() {
        ButtonStackBuilder builder = new ButtonStackBuilder();
        builder.addGridded(buttonAdd);
        builder.addRelatedGap();
        builder.addGridded(buttonEdit);
        builder.addRelatedGap();
        builder.addGridded(buttonRemove);
        builder.addRelatedGap();
        builder.addGridded(buttonSearch);
        builder.addRelatedGap();
        builder.addGridded(buttonStatistics);
        return builder.getPanel();
    }
}
