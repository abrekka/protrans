package no.ugland.utransprod.gui;

import java.awt.event.KeyEvent;

import javax.swing.JMenu;

import com.google.inject.Inject;

import no.ugland.utransprod.gui.action.GulvsponPackageAction;
import no.ugland.utransprod.gui.action.IgarasjenPackageAction;
import no.ugland.utransprod.gui.action.MainPackageAction;
import no.ugland.utransprod.gui.action.TakstolPackageAction;

public class PackMenu extends ProTransMenu {

	private TakstolPackageAction takstolPackageAction;
	private MainPackageAction mainPackageAction;
	private GulvsponPackageAction gulvsponPackageAction;
	private IgarasjenPackageAction igarasjenPackageAction;

	@Inject
	public PackMenu(Login aLogin, final TakstolPackageAction aTakstolPackageAction,
			final MainPackageAction aMainPackageAction, final GulvsponPackageAction aGulvsponPackageAction,
			IgarasjenPackageAction igarasjenPackageAction) {
		super(aLogin);
		gulvsponPackageAction = aGulvsponPackageAction;
		mainPackageAction = aMainPackageAction;
		takstolPackageAction = aTakstolPackageAction;
		this.igarasjenPackageAction=igarasjenPackageAction;
	}

	@Override
	public JMenu buildMenu() {
		JMenu menuPackage = addMenu("Pakking", KeyEvent.VK_A);
		addMenuItem(menuPackage, takstolPackageAction, KeyEvent.VK_T, null, null, null, "Standard takstol", false);
		addMenuItem(menuPackage, mainPackageAction, KeyEvent.VK_G, null, null, null, "Garasjepakke", false);
		addMenuItem(menuPackage, gulvsponPackageAction, KeyEvent.VK_U, null, null, null, "Gulvspon", false);
		addMenuItem(menuPackage, igarasjenPackageAction, KeyEvent.VK_I, null, null, null, "Igarasjen", false);
		return menuPackage;
	}

}
