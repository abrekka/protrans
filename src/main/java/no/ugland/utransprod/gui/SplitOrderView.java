package no.ugland.utransprod.gui;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import no.ugland.utransprod.gui.edit.EditViewable;
import no.ugland.utransprod.gui.handlers.SplitOrderViewHandler;

import org.jdesktop.swingx.JXTreeTable;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class SplitOrderView implements EditViewable{
    private SplitOrderViewHandler viewHandler;
    private JXTreeTable treeTableOrderLines;
    private JButton buttonOk;
    private JButton buttonCancel;
    public SplitOrderView(final SplitOrderViewHandler handler){
        viewHandler=handler;
    }
    private void initComponents(WindowInterface window){
        treeTableOrderLines=viewHandler.getTreeTableTransportable();
        buttonOk=viewHandler.getButtonOk(window);
        buttonCancel=viewHandler.getButtonCancel(window);
    }
    public JPanel buildPanel(WindowInterface window){
        window.setTitle(getHeading());
        initComponents(window);
        FormLayout layout = new FormLayout("10dlu,150dlu,10dlu", "10dlu,200dlu,5dlu,p,3dlu");
        PanelBuilder builder = new PanelBuilder(layout);
        CellConstraints cc = new CellConstraints();
        
        builder.add(new JScrollPane(treeTableOrderLines),cc.xy(2, 2));
        builder.add(ButtonBarFactory.buildCenteredBar(buttonOk,buttonCancel),cc.xy(2, 4));
        
        return builder.getPanel();
    }
    public String getDialogName() {
        return "SplitOrderView";
    }
    public String getHeading() {
        return "Artikler";
    }
}
