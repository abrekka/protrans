package no.ugland.utransprod.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.MenuBarBuilderImpl;
import no.ugland.utransprod.gui.MenuBarBuilderInterface;
import no.ugland.utransprod.gui.TakstolApplyListView;
import no.ugland.utransprod.gui.TakstolProductionWindow;
import no.ugland.utransprod.gui.handlers.TakstolProductionViewHandler;

import com.google.inject.Inject;

/**
 * Klasse som håndterer visning av takstolproduksjonvindu
 * @author atle.brekka
 */
public class TakstolProductionAction extends AbstractAction {
    /**
     * 
     */
    private final MenuBarBuilderInterface menuBarBuilder;
    private static final long serialVersionUID = 1L;
    private TakstolProductionWindow takstolProductionWindow;
	private Login login;

    @Inject
    public TakstolProductionAction(final MenuBarBuilderInterface aMenuBarBuilder,final TakstolProductionWindow aTakstolProductionWindow,final Login aLogin) {
        super("Takstol...");
        login=aLogin;
        takstolProductionWindow=aTakstolProductionWindow;
        this.menuBarBuilder = aMenuBarBuilder;
    }

    /**
     * Åpner kundevindu
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(final ActionEvent arg0) {
        //TakstolProductionWindow takstolProductionWindow = new TakstolProductionWindow();
        takstolProductionWindow.setLogin(login);
        menuBarBuilder.openFrame(new TakstolApplyListView((TakstolProductionViewHandler)takstolProductionWindow
                .getViewHandler()));

    }
}