
// Warning: No line numbers available in class file
package no.ugland.utransprod.gui.model;

import com.jgoodies.binding.PresentationModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import no.ugland.utransprod.gui.WindowInterface;

public interface ICommentViewHandler {
   JTextField getTextFieldUserName(PresentationModel var1);

   JTextArea getTextAreaComment(PresentationModel var1);

   JButton getButtonCancel(WindowInterface var1);

   JButton getButtonOk(WindowInterface var1);

   JCheckBox getCheckBoxTransport(PresentationModel var1);
}
