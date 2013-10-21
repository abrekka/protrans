package no.ugland.utransprod.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.MenuBarBuilderInterface;
import no.ugland.utransprod.gui.OverviewView;
import no.ugland.utransprod.gui.handlers.ApplicationParamViewHandler;
import no.ugland.utransprod.gui.model.ApplicationParamModel;
import no.ugland.utransprod.model.ApplicationParam;
import no.ugland.utransprod.service.ApplicationParamManager;

import com.google.inject.Inject;

public class ApplicationParamViewAction extends AbstractAction {
	private final MenuBarBuilderInterface menuBarBuilder;
	private Login login;
	private ApplicationParamManager applicationParamManager;
	@Inject
	public ApplicationParamViewAction(MenuBarBuilderInterface aMenuBarBuilder,ApplicationParamManager aApplicationParamManager,Login aLogin) {
		super("Parametre...");
		login=aLogin;
		this.applicationParamManager=aApplicationParamManager;
		this.menuBarBuilder = aMenuBarBuilder;
	}
	public void actionPerformed(final ActionEvent arg0) {

		menuBarBuilder.openFrame(new OverviewView<ApplicationParam, ApplicationParamModel>(
				new ApplicationParamViewHandler("Parametre",applicationParamManager,login.getUserType())));

	}
}
