package no.ugland.utransprod.gui.buttons;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import no.ugland.utransprod.gui.Updateable;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.util.Util;

/**
 * Generell klasse fom lagrehandling
 * @author atle.brekka
 *
 */
public class SaveAction extends AbstractAction {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * 
     */
    private Updateable updateable;
    /**
     * 
     */
    private WindowInterface window;

    /**
     * @param updateable 
     * @param window 
     * 
     */
    public SaveAction(Updateable updateable,WindowInterface window) {
    	super("Lagre");
        this.updateable = updateable;
        this.window = window;
    }

    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {
        Util.setWaitCursor(window.getComponent());
    	updateable.doSave(window);
    	Util.setDefaultCursor(window.getComponent());
    }


}
