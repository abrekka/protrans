package no.ugland.utransprod.gui;

import java.awt.event.KeyEvent;

import javax.swing.JMenu;

import no.ugland.utransprod.gui.action.ApplicationParamViewAction;
import no.ugland.utransprod.gui.action.ApplicationUserAction;
import no.ugland.utransprod.gui.action.ColliSetupAction;
import no.ugland.utransprod.gui.action.DeviationManagerAction;
import no.ugland.utransprod.gui.action.HmsManagerAction;
import no.ugland.utransprod.gui.action.InfoAction;
import no.ugland.utransprod.gui.action.MailSetupAction;
import no.ugland.utransprod.gui.action.ProductionUnitAction;
import no.ugland.utransprod.gui.action.SystemSetupAction;
import no.ugland.utransprod.gui.action.TakstolSetupAction;
import no.ugland.utransprod.gui.action.UserTypeAction;

import com.google.inject.Inject;

public class AdminMenu extends ProTransMenu {
	private ApplicationUserAction applicationUserAction;
	private UserTypeAction userTypeAction;
	private InfoAction infoAction;
	private ProductionUnitAction productionUnitAction;

	private MailSetupAction mailSetupAction;
	private DeviationManagerAction deviationManagerAction;
	private SystemSetupAction systemSetupAction;
	private ColliSetupAction colliSetupAction;
	private TakstolSetupAction takstolSetupAction;
	private ApplicationParamViewAction applicationParamViewAction;
	private HmsManagerAction hmsManagerAction;

	@Inject
	public AdminMenu(final Login aLogin,
			final ApplicationUserAction aApplicationUserAction,
			final UserTypeAction aUserTypeAction, final InfoAction aInfoAction,
			final ProductionUnitAction aProductionUnitAction,
			final MailSetupAction aMailSetupAction,
			final DeviationManagerAction aDeviationManagerAction,
			final SystemSetupAction aSystemSetupAction,
			final ColliSetupAction aColliSetupAction,
			final TakstolSetupAction aTakstolSetupAction,
			final ApplicationParamViewAction aApplicationParamViewAction,final HmsManagerAction aHmsManagerAction) {
		super(aLogin);
		hmsManagerAction=aHmsManagerAction;
		takstolSetupAction = aTakstolSetupAction;
		colliSetupAction = aColliSetupAction;
		systemSetupAction = aSystemSetupAction;
		deviationManagerAction = aDeviationManagerAction;
		mailSetupAction = aMailSetupAction;
		productionUnitAction = aProductionUnitAction;
		infoAction = aInfoAction;
		userTypeAction = aUserTypeAction;
		applicationUserAction = aApplicationUserAction;
		this.applicationParamViewAction = aApplicationParamViewAction;
	}

	public JMenu buildMenu() {
		JMenu menuAdmin = addMenu("Admin", KeyEvent.VK_A);
		addMenuItem(menuAdmin, applicationUserAction, KeyEvent.VK_B, null,
				null, null, true);
		addMenuItem(menuAdmin, userTypeAction, KeyEvent.VK_B, null, null, null,
				true);
		addMenuItem(menuAdmin, infoAction, KeyEvent.VK_I, null, null, null,
				true);
		addMenuItem(menuAdmin, mailSetupAction, KeyEvent.VK_M, null, null,
				null, true);
		addMenuItem(menuAdmin, deviationManagerAction, KeyEvent.VK_V, null,
				null, null, true);
		addMenuItem(menuAdmin, systemSetupAction, KeyEvent.VK_V, null, null,
				null, true);
		addMenuItem(menuAdmin, productionUnitAction, KeyEvent.VK_V, null, null,
				null, true);
		addMenuItem(menuAdmin, colliSetupAction, KeyEvent.VK_K, null, null,
				null, true);
		addMenuItem(menuAdmin, takstolSetupAction, KeyEvent.VK_T, null, null,
				null, true);
		addMenuItem(menuAdmin, applicationParamViewAction, KeyEvent.VK_T, null,
				null, null, true);
		addMenuItem(menuAdmin, hmsManagerAction, KeyEvent.VK_V, null, null,
				null, true);
		return menuAdmin;
	}

}
