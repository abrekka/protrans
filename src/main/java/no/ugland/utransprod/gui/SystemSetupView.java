package no.ugland.utransprod.gui;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import no.ugland.utransprod.gui.edit.EditViewable;
import no.ugland.utransprod.gui.handlers.SystemSetupViewHandler;

import com.jgoodies.forms.builder.ButtonStackBuilder;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class SystemSetupView implements EditViewable{
    
    private SystemSetupViewHandler viewHandler;
    private JComboBox comboBoxWindows;
    private JComboBox comboBoxTables;
    private JComboBox comboBoxProductAreaGroup;
    private JButton buttonCancel;
    private JButton buttonSave;
    private JList listColumns;
    private JButton buttonAddColumn;
    private JButton buttonRemoveColumn;
    
    public SystemSetupView(SystemSetupViewHandler handler){
        viewHandler=handler;
    }

    public JPanel buildPanel(WindowInterface window){
        initComponents(window);
        FormLayout layout = new FormLayout("10dlu,70dlu,3dlu,80dlu,10dlu", "10dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,p");
        PanelBuilder builder = new PanelBuilder(layout);
        CellConstraints cc = new CellConstraints();
        
        builder.addLabel("Dialog:",cc.xy(2, 2));
        builder.add(comboBoxWindows,cc.xy(4, 2));
        builder.addLabel("Tabell:",cc.xy(2, 4));
        builder.add(comboBoxTables,cc.xy(4, 4));
        builder.addLabel("Produktområde:",cc.xy(2, 6));
        builder.add(comboBoxProductAreaGroup,cc.xy(4, 6));
        builder.addLabel("Usynlige kolonner:",cc.xy(2, 8));
        builder.add(new JScrollPane(listColumns),cc.xyw(2, 10, 1));
        builder.add(buildButtonPanel(),cc.xy(4, 10));
        builder.add(ButtonBarFactory.buildCenteredBar(buttonSave,buttonCancel),cc.xyw(2, 12, 3));
        return builder.getPanel();
    }
    
    private JPanel buildButtonPanel(){
        ButtonStackBuilder builder = new ButtonStackBuilder();
        builder.addGridded(buttonAddColumn);
        builder.addGlue();
        builder.addGridded(buttonRemoveColumn);
        return builder.getPanel();
    }
    
    private void initComponents(WindowInterface window){
        comboBoxWindows=viewHandler.getComboBoxWindows();
        comboBoxTables=viewHandler.getComboBoxTables();
        comboBoxProductAreaGroup=viewHandler.getComboBoxProductAreaGroup();
        buttonCancel=viewHandler.getButtonCancel(window);
        buttonSave=viewHandler.getButtonSave(window);
        listColumns=viewHandler.getListColumns();
        buttonAddColumn=viewHandler.getButtonAddColumn(window);
        buttonRemoveColumn=viewHandler.getButtonRemoveColumn();
    }

    public String getDialogName() {
        return "SystemSetupView";
    }

    public String getHeading() {
        return "Oppsett";
    }
}
