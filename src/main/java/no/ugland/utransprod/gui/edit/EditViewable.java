package no.ugland.utransprod.gui.edit;

import javax.swing.JComponent;

import no.ugland.utransprod.gui.WindowInterface;

public interface EditViewable {
	JComponent buildPanel(WindowInterface window);
    String getHeading();
    String getDialogName();
}
