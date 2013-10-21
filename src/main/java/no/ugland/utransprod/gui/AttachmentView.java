package no.ugland.utransprod.gui;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import no.ugland.utransprod.gui.handlers.AttachmentViewHandler;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class AttachmentView {

    private JList listAttachments;
    private AttachmentViewHandler viewHandler;
    private JButton buttonAddAttachment;
    private JButton buttonShowAttachment;
    private JButton buttonDeleteAttachment;
    public AttachmentView(AttachmentViewHandler handler){
        viewHandler=handler;
    }

    public JPanel buildPanel(WindowInterface window) {
        initComponents(window);
        FormLayout layout = new FormLayout("fill:p:grow", "fill:p:grow,3dlu,p");
        PanelBuilder builder = new PanelBuilder(layout);
        CellConstraints cc = new CellConstraints();
        builder.add(new JScrollPane(listAttachments),cc.xy(1, 1));
        builder.add(ButtonBarFactory.buildCenteredBar(buttonAddAttachment,buttonShowAttachment,buttonDeleteAttachment),cc.xy(1, 3));
        return builder.getPanel();
    }

    private void initComponents(final WindowInterface window) {
        listAttachments=viewHandler.getListAttachments(window);
        buttonAddAttachment=viewHandler.getButtonAddAttachment(window);
        buttonShowAttachment=viewHandler.getButtonShowAttachment(window);
        buttonDeleteAttachment=viewHandler.getButtonDeleteAttachment(window);
    }

}
