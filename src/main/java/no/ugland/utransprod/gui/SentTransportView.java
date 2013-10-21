package no.ugland.utransprod.gui;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import no.ugland.utransprod.gui.edit.EditViewable;
import no.ugland.utransprod.gui.handlers.SentTransportViewHandler;

import org.jdesktop.swingx.JXTable;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * Vindus om brukes for å vise ordre for en transport som skal sendes
 * @author atle.brekka
 */
public class SentTransportView implements EditViewable {
    private SentTransportViewHandler viewHandler;

    private JXTable table;

    private JButton buttonOk;

    private JButton buttonShowCollies;

    private JButton buttonCancel;

    /**
     * @param handler
     */
    public SentTransportView(final SentTransportViewHandler handler) {
        viewHandler = handler;
    }

    /**
     * Initierer vinduskomponenter
     * @param window
     */
    private void initComponents(WindowInterface window) {
        window.setName("SentTransportView");
        table = viewHandler.getTableOrders();
        buttonOk = viewHandler.getButtonOk(window);
        buttonShowCollies = viewHandler.getButtonShowCollies();
        buttonCancel = viewHandler.getButtonCancel(window);
    }

    /**
     * Bygger panel
     * @param window
     * @return panel
     */
    public JPanel buildPanel(WindowInterface window) {
        initComponents(window);
        String tableWidth="220dlu";
        if(viewHandler.isCollies()){
            tableWidth="100dlu";
        }
        FormLayout layout = new FormLayout("10dlu,"+tableWidth+":grow,3dlu,p,10dlu",
                "10dlu,p,3dlu,100dlu,3dlu,p");
        PanelBuilder builder = new PanelBuilder(layout);
        CellConstraints cc = new CellConstraints();

        if(viewHandler.isCollies()){
            builder.addLabel("Kollier i transport", cc.xy(2, 2));
        }else{
        builder.addLabel("Ordre i transport", cc.xy(2, 2));
        }
        builder.add(new JScrollPane(table), cc.xy(2, 4));
        if (!viewHandler.isCollies()) {
            builder.add(buttonShowCollies, cc.xy(4, 4));
        }
        builder.add(ButtonBarFactory.buildCenteredBar(buttonOk, buttonCancel),
                cc.xyw(2, 6, 3));

        return builder.getPanel();
    }

    public String getDialogName() {
        return "SentTransportView";
    }

    public String getHeading() {
        return "Ordre";
    }

}
