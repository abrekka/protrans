package no.ugland.utransprod.gui;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import no.ugland.utransprod.gui.handlers.FileViewHandler;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class FileView {
    private JTextArea textAreaFile;
    private FileViewHandler viewHandler;
    private JButton buttonCancel;
    
    public FileView(final FileViewHandler handler){
        viewHandler=handler;
    }
    public JPanel buildPanel(WindowInterface window){
        window.setName("FileView");
        initComponents(window);
        FormLayout layout = new FormLayout("10dlu,fill:500dlu,10dlu", "10dlu,fill:300dlu,3dlu,p");
        PanelBuilder builder = new PanelBuilder(layout);
        CellConstraints cc = new CellConstraints();
        builder.add(new JScrollPane(textAreaFile),cc.xy(2, 2));
        builder.add(ButtonBarFactory.buildCenteredBar(buttonCancel),cc.xy(2, 4));
        return builder.getPanel();
    }
    private void initComponents(WindowInterface window) {
        textAreaFile=viewHandler.getTextAreaFile();
        buttonCancel=viewHandler.getButtonCancel(window);
    }
}
