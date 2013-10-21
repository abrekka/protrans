package no.ugland.utransprod.gui.handlers;

import javax.swing.JButton;
import javax.swing.JTextArea;

import no.ugland.utransprod.gui.Closeable;
import no.ugland.utransprod.gui.IconEnum;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.buttons.CancelButton;
import no.ugland.utransprod.gui.model.FileModel;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.BasicComponentFactory;

public class FileViewHandler implements Closeable{
    private PresentationModel presentationModel;
    public FileViewHandler(String fileContent){
        presentationModel=new PresentationModel(new FileModel(fileContent));
    }

    public JTextArea getTextAreaFile() {
        JTextArea textArea = BasicComponentFactory.createTextArea(presentationModel.getModel(FileModel.PROPERTY_FILE_CONTENT));
        textArea.setName("TextAreaFile");
        textArea.setEnabled(false);
        return textArea;
    }

    public JButton getButtonCancel(final WindowInterface window) {
        JButton button = new CancelButton(window,this,false,"Ok",IconEnum.ICON_OK,null,true);
        button.setName("ButtonOk");
        return button;
    }

    public boolean canClose(String actionString, WindowInterface window) {
        return true;
    }

}
