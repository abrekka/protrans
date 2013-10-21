package no.ugland.utransprod.gui;

import java.awt.Component;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JScrollPane;

import no.ugland.utransprod.gui.handlers.OrderViewHandler;

import org.jdesktop.swingx.JXTable;

import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * Klasse som håndterer visning av nye ordre
 * @author atle.brekka
 */
public class OrderPanelView {
    private JXTable tableOrders;

    private OrderViewHandler viewHandler;

    private JButton buttonEditOrder;

    private SelectionInList orderSelectionList;

    private OrderPanelTypeEnum panelType;

    private String labelString;

    /**
     * @param handler
     * @param orderPanelType
     * @param label
     */
    public OrderPanelView(final OrderViewHandler handler,
            final OrderPanelTypeEnum orderPanelType, final String label) {
        viewHandler = handler;
        panelType = orderPanelType;
        labelString = label;
    }

    /**
     * @param window
     */
    private void initComponents(final WindowInterface window) {
        orderSelectionList = viewHandler
                .initAndGetOrderPanelSelectionList(panelType);
        tableOrders = viewHandler.getPanelTableOrders(panelType, window);
        buttonEditOrder = viewHandler.getButtonEditOrder(window);
    }

    /**
     * Initierer hendelsehåndtering
     * @param rightMouseClickHandler
     * @param window
     */
    private void initEventHandling(final MouseListener rightMouseClickHandler,
            final WindowInterface window) {
        orderSelectionList.addPropertyChangeListener(
                SelectionInList.PROPERTYNAME_SELECTION_EMPTY,
                new SelectionEmptyHandler());
        tableOrders.addMouseListener(viewHandler
                .getNewOrderDoubleClickHandler(window));
        if (rightMouseClickHandler != null) {
            tableOrders.addMouseListener(rightMouseClickHandler);
        }
    }

    public final Component buildPanel(final WindowInterface window,
            final String tableHeight,
            final MouseListener rightMouseClickHandler,
            final String tableWidth, final boolean showEditButton) {
        initComponents(window);
        initEventHandling(rightMouseClickHandler, window);
        FormLayout layout = new FormLayout(tableWidth + "dlu:grow",
                "p,3dlu,fill:" + tableHeight + ":grow,3dlu,p");
        PanelBuilder builder = new PanelBuilder(layout);
        CellConstraints cc = new CellConstraints();
        if (labelString != null) {
            builder.addLabel(labelString, cc.xy(1, 1));
        }
        builder.add(new JScrollPane(tableOrders), cc.xy(1, 3));
        if (showEditButton) {
            builder.add(ButtonBarFactory.buildCenteredBar(buttonEditOrder), cc
                    .xy(1, 5));
        }

        return builder.getPanel();
    }

    /**
     * Enabler/disabler knapper
     */
    final void updateButtons() {
        boolean hasSelection = orderSelectionList.hasSelection();
        buttonEditOrder.setEnabled(hasSelection);
    }

    /**
     * Klasse som håndrer valg i liste
     * @author atle.brekka
     */
    final class SelectionEmptyHandler implements PropertyChangeListener {

        /**
         * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
         */
        public void propertyChange(final PropertyChangeEvent arg0) {
            updateButtons();

        }

    }

}
