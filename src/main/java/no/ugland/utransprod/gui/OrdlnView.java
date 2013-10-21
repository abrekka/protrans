package no.ugland.utransprod.gui;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.jdesktop.swingx.JXTable;

import no.ugland.utransprod.gui.handlers.OrdlnViewHandler;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class OrdlnView {
    private OrdlnViewHandler viewHandler;

    private JXTable tableOrderLines;

    private JButton buttonCancel;

    public OrdlnView(final OrdlnViewHandler handler) {
        viewHandler = handler;
    }

    public final JPanel buildPanel(final WindowInterface window) {
        initComponents(window);
        FormLayout layout = new FormLayout("10dlu,350dlu:grow,1dlu", "10dlu,200dlu:grow,3dlu,p");
        PanelBuilder builder = new PanelBuilder(layout);
        CellConstraints cc = new CellConstraints();

        builder.add(new JScrollPane(tableOrderLines), cc.xy(2, 2));
        builder.add(ButtonBarFactory.buildCenteredBar(buttonCancel), cc
                .xy(2, 4));

        return builder.getPanel();
    }

    private void initComponents(final WindowInterface window) {
        window.setName("OrdlnView");
        tableOrderLines = viewHandler.getTableOrderLines();
        buttonCancel = viewHandler.getButtonCancel(window);
    }
}
