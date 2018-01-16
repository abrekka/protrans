package no.ugland.utransprod.gui;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import no.ugland.utransprod.gui.handlers.IncomingOrderViewHandler;
import no.ugland.utransprod.gui.model.OrderModel;
import no.ugland.utransprod.model.Order;

import com.google.inject.Inject;
import com.jgoodies.forms.builder.ButtonStackBuilder;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * Vindu for ordre til avrop
 * @author atle.brekka
 */
public class IncomingOrderOverviewView extends OverviewView<Order, OrderModel> {

    private JComboBox comboBoxProductAreaGroup;
	private JButton buttonRefresh;

    /**
     * @param handler
     */
    @Inject
    public IncomingOrderOverviewView(
            final IncomingOrderViewHandler handler) {
        super(handler);
    }

    /**
     * @see no.ugland.utransprod.gui.OverviewView#initComponents(no.ugland.utransprod.gui.WindowInterface)
     */
    @Override
    protected final void initComponents(final WindowInterface window) {
        super.initComponents(window);
        buttonRefresh=((IncomingOrderViewHandler) viewHandler)
                .getButtonRefresh();
        buttonAdd.setText("Importer ordre...");
        comboBoxProductAreaGroup = ((IncomingOrderViewHandler) viewHandler)
                .getComboBoxProductAreaGroup();

    }

    /**
     * @see no.ugland.utransprod.gui.OverviewView#buildPanel(no.ugland.utransprod.gui.WindowInterface)
     */
    @Override
    public final JComponent buildPanel(final WindowInterface window) {
        initComponents(window);
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
        builder.add(scrollPaneTable, cc.xywh(2, 4, 5, 9));
        builder.add(buildButtonPanel(), cc.xywh(8, 5, 1, 10));
        builder.add(ButtonBarFactory.buildCenteredBar(buttonCancel,buttonRefresh), cc.xyw(2,
                15, 7));
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
        builder.addGridded(buttonSearch);
        return builder.getPanel();
    }

    /**
     * @see no.ugland.utransprod.gui.OverviewView#updateActionEnablement()
     */
    @Override
    protected final void updateActionEnablement() {
        if (viewHandler.hasWriteAccess()) {
            boolean hasSelection = viewHandler
                    .objectSelectionListHasSelection();
            buttonAdd.setEnabled(hasSelection);
        }
    }

}
