/**
 * 
 */
package no.ugland.utransprod.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.MenuBarBuilderInterface;
import no.ugland.utransprod.gui.edit.EditAccidentView;
import no.ugland.utransprod.gui.handlers.AccidentViewHandler;
import no.ugland.utransprod.gui.model.AccidentModel;
import no.ugland.utransprod.model.Accident;
import no.ugland.utransprod.service.AccidentManager;
import no.ugland.utransprod.service.ManagerRepository;

import com.google.inject.Inject;

public class RegisterAccidentAction extends AbstractAction {
    /**
     * 
     */
    private MenuBarBuilderInterface menuBarBuilder;
    private static final long serialVersionUID = 1L;
    private ManagerRepository  managerRepository;
    private Login login;

    @Inject
    public RegisterAccidentAction(MenuBarBuilderInterface aMenuBarBuilder,ManagerRepository aManagerRepository,Login aLogin) {
        super("Registrere ulykke/hendelse...");
        login=aLogin;
        managerRepository=aManagerRepository;
        menuBarBuilder = aMenuBarBuilder;
    }

    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(final ActionEvent arg0) {
        AccidentViewHandler accidentViewHandler = new AccidentViewHandler(login,managerRepository);

        AccidentModel accidentModel = new AccidentModel(new Accident());
        EditAccidentView editAccidentView = new EditAccidentView(false, accidentModel, accidentViewHandler);

        menuBarBuilder.openFrame(editAccidentView);
    }
}